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

    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
    }

    public double getZ() {
        return myZ;
    }

    public Vector add(Vector vector) {
        return new Vector(myX + vector.getX(), myY + vector.getY(), myZ + vector.getZ());
    }

    public Vector subtract(Vector vector) {
        return new Vector(myX - vector.getX(), myY - vector.getY(), myZ - vector.getZ());
    }

    public Vector multiply(Vector vector) {
        return new Vector(myX * vector.getX(), myY * vector.getY(), myZ * vector.getZ());
    }

    public Vector multiply(double value) {
        return new Vector(myX * value, myY * value, myZ * value);
    }

    public String toString() {
        return "[" + myX + ", " + myY + ", " + myZ + "]";
    }
}

