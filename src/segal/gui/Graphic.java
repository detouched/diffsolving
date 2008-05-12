package segal.gui;

import segal.tools.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

/**
 * User: danielpenkin
 */
public class Graphic extends JPanel {

    private int[] myX;
    private int[] myY;
    private int[] myZ;
    private int[] myT;
    private Map<Double, Vector> myData;

    private double myScaleFactor = 1;
    private int myDX;
    private int myDY;

    public Graphic() {
        setPreferredSize(new Dimension(800, 500));
        addMouseWheelListener(new WheelListener());
        MyMouseListener a = new MyMouseListener();
        addMouseMotionListener(a);
        addMouseListener(a);
    }

    public void setData(Map<Double, Vector> graphData) {
        myData = graphData;
        processData();
        repaint();
    }

    private void processData() {
        if (myData == null) {
            return;
        }
        myX = new int[myData.size()];
        myY = new int[myData.size()];
        myZ = new int[myData.size()];
        myT = new int[myData.size()];

        int i = 0;
        for (Map.Entry<Double, Vector> entry : myData.entrySet()) {
            myT[i] = myDX + (int) (i * myScaleFactor - getWidth() / 2);
            myX[i] = myDY + (int) (myScaleFactor * entry.getValue().getX());
            myY[i] = myDY + (int) (myScaleFactor * entry.getValue().getY());
            myZ[i] = myDY + (int) (myScaleFactor * entry.getValue().getZ());
            i++;
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D image = (Graphics2D) g;
        if (myT != null) {
            image.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            image.setColor(Color.RED);
            image.drawPolyline(myT, myX, myT.length);
            image.setColor(Color.BLUE);
            image.drawPolyline(myT, myY, myT.length);
            image.setColor(Color.GREEN);
            image.drawPolyline(myT, myZ, myT.length);
        }
    }

    private final class WheelListener implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            myScaleFactor += e.getWheelRotation() * 0.6;
            if (myScaleFactor < 1) {
                myScaleFactor = 1;
            } else if (myScaleFactor > 10) {
                myScaleFactor = 10;
            }
            processData();
            repaint();
        }
    }

    private final class MyMouseListener extends MouseAdapter implements MouseMotionListener {
        private int myX;
        private int myY;

        public void mouseDragged(MouseEvent e) {
            myDX += e.getX() - myX;
            myDY += e.getY() - myY;

            myX = e.getX();
            myY = e.getY();

            processData();
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            myX = e.getX();
            myY = e.getY();
        }
    }
}
