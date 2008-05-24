package segal.gui;

import segal.tools.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Map;

/**
 * User: danielpenkin
 */
public class Graphic extends JPanel {

    private int[] myX;
    private int[] myY;
    private int[] myZ;
    private int[] myT;

    private int myStartX;
    private int myStartY;

    private Map<Double, Vector> myData;

    private double myScaleFactor = 2;
    private int myDX;
    private int myDY;

    public Graphic(double zoomFactor) {
        myScaleFactor = zoomFactor;
        JPanel leftPanel = new JPanel();
        add(leftPanel, BorderLayout.WEST);
        setPreferredSize(new Dimension(800, 500));
        MyMouseListener a = new MyMouseListener();
        addMouseMotionListener(a);
        addMouseListener(a);
        myStartX = 0;
        myStartY = 250;

    }

    public void setData(Map<Double, Vector> graphData) {
        myData = graphData;
        initialize();
        repaint();
    }

    private void initialize() {
        myX = new int[myData.size()];
        myY = new int[myData.size()];
        myZ = new int[myData.size()];
        myT = new int[myData.size()];

        int i = 0;

        for (Map.Entry<Double, Vector> entry : myData.entrySet()) {
            myT[i] = myStartX + (int) (myScaleFactor * i / 10);
            Vector currentPoint = entry.getValue();
            myX[i] = (int) (myStartY - (1 - currentPoint.getX()) * myScaleFactor * 100);
            myY[i] = (int) (myStartY - (1 - currentPoint.getY()) * myScaleFactor * 100);
            myZ[i] = (int) (myStartY - (1 - currentPoint.getZ()) * myScaleFactor * 100);
            i++;
        }
    }

    private void processData() {
        if (myData == null) {
            return;
        }
        int[] newX = new int[myData.size()];
        int[] newY = new int[myData.size()];
        int[] newZ = new int[myData.size()];
        int[] newT = new int[myData.size()];

        for (int i = 0; i < myData.size(); i++) {
            newT[i] = myT[i] + myDX;
            newX[i] = myX[i] + myDY;
            newY[i] = myY[i] + myDY;
            newZ[i] = myZ[i] + myDY;
        }

        myX = newX;
        myY = newY;
        myZ = newZ;
        myT = newT;

        //myStartX = myT[0];
        //myStartY = myZ[0] - 2;
    }

    public void setZoom(double zoomFactor) {
        myScaleFactor = zoomFactor;
        initialize();
        repaint();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D image = (Graphics2D) g;
        image.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (myT != null) {

            image.setColor(Color.RED);
            //noinspection SuspiciousNameCombination
            image.drawPolyline(myT, myX, myT.length);
            image.setColor(Color.BLUE);
            image.drawPolyline(myT, myY, myT.length);
            image.setColor(Color.GREEN);
            image.drawPolyline(myT, myZ, myT.length);
        }
    }

    private final class MyMouseListener extends MouseAdapter implements MouseMotionListener {
        private int myX;
        private int myY;

        public void mouseDragged(MouseEvent e) {

            myDX = (e.getX() - myX);
            myDY = (e.getY() - myY);

            myStartY = e.getY();
            myStartX = e.getX();

            myX = myStartX;
            myY = myStartY;
            processData();
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            myStartY = e.getY();
            myStartX = e.getX();

        }
    }
}
