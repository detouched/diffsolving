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
    private final static int PLOT_HEIGHT = 500;
    private final static int PLOT_WIDTH = 1000;

    private int[] funcX;
    private int[] funcY;
    private int[] funcZ;
    private int[] coordOX;

    private int coordStartX;
    private int coordStartY;

    private Map<Double, Vector> myData;

    private double scaleFactor = 2.0;
    Vector averageValues = new Vector(0, 0, 0);
    private int myDX;
    private int myDY;


    public Graphic(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        JPanel leftPanel = new JPanel();
        add(leftPanel, BorderLayout.WEST);
        setPreferredSize(new Dimension(PLOT_WIDTH, PLOT_HEIGHT));
        MyMouseListener a = new MyMouseListener();
        addMouseMotionListener(a);
        addMouseListener(a);
        coordStartX = 50;
        coordStartY = PLOT_HEIGHT / 2;
    }

    public void setData(Map<Double, Vector> graphData) {
        myData = graphData;
        updateView();
        repaint();
    }

    private void initialize() {
        funcX = new int[myData.size()];
        funcY = new int[myData.size()];
        funcZ = new int[myData.size()];
        coordOX = new int[myData.size()];
    }


    private void updateAverageValues() {
        if (myData == null) {
            return;
        }
        final int numberOfPoints = myData.size();

        double averageX = 0;
        double averageY = 0;
        double averageZ = 0;
        for (Map.Entry<Double, Vector> entry : myData.entrySet()) {
            Vector currentPoint = entry.getValue();
            averageX += currentPoint.getX();
            averageY += currentPoint.getY();
            averageZ += currentPoint.getZ();
        }

        averageX /= numberOfPoints * 1.0;
        averageY /= numberOfPoints * 1.0;
        averageZ /= numberOfPoints * 1.0;

        averageValues.updateValues(averageX, averageY, averageZ);

    }

    private void calculateCoordinates() {
        int i = 0;

        for (Map.Entry<Double, Vector> entry : myData.entrySet()) {
            Vector currentPoint = entry.getValue();

            coordOX[i] = (int) (coordStartX + myDX + getXscaling(i));
            Vector tempCoords = new Vector(currentPoint.getX(), currentPoint.getY(), currentPoint.getZ());
            Vector relativeCoords = tempCoords.subtract(averageValues);
            funcX[i] = (int) (coordStartY + myDY + relativeCoords.getX() * scaleFactor);
            funcY[i] = (int) (coordStartY + myDY + relativeCoords.getY() * scaleFactor);
            funcZ[i] = (int) (coordStartY + myDY + relativeCoords.getZ() * scaleFactor);
            i++;
        }
    }

    private double getXscaling(int i) {
        double halfSize = 1.0 * PLOT_WIDTH / 2.0;
        double plusAdin = (i - halfSize) / (halfSize);

        double linear = (1.0 * i * PLOT_WIDTH / myData.size());
        return linear + 10 * scaleFactor * plusAdin;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        updateView();
        repaint();
    }

    private void updateView() {
        initialize();
        updateAverageValues();
        calculateCoordinates();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D image = (Graphics2D) g;
        image.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (coordOX != null) {

            image.setColor(Color.RED);
            //noinspection SuspiciousNameCombination
            image.drawPolyline(coordOX, funcX, coordOX.length);
            image.setColor(Color.BLUE);
            image.drawPolyline(coordOX, funcY, coordOX.length);
            image.setColor(Color.GREEN);
            image.drawPolyline(coordOX, funcZ, coordOX.length);
        }
    }

    private final class MyMouseListener extends MouseAdapter implements MouseMotionListener {

        private int myX;
        private int myY;

        public void mouseDragged(MouseEvent e) {
            myDX = (e.getX() - myX);
            myDY = (e.getY() - myY);
            updateView();
            repaint();
        }

        public void mousePressed(MouseEvent e) {
            myX = e.getX();
            myY = e.getY();
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public void mouseReleased(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
            coordStartY += myDY;
            coordStartX += myDX;
            myDY = 0;
            myDX = 0;
        }

        public void mouseMoved(MouseEvent e) {}
    }
}
