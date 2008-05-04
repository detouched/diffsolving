package segal.tools;

/**
 * User: danielpenkin
 */
public class Vector {
    private final double myX;
    private final double myY;
    private final double myZ;

    public Vector(double x, double y, double z) {
        myX = x;
        myY = y;
        myZ = z;
    }

    public double getMyX() {
        return myX;
    }

    public double getMyY() {
        return myY;
    }

    public double getMyZ() {
        return myZ;
    }
}
