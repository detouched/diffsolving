package segal;

import segal.tools.Vector;

/**
 * User: danielpenkin
 */
public class EquationSystem {

    private static double B = 1.3;
    private static double R = 1.5;
    private static double SIGMA = 1.0;

    public static double getDX(Vector variables) {
        return SIGMA * (variables.getY() - variables.getX());
    }

    public static double getDY(Vector variables) {
        return R * variables.getX() - variables.getX() * variables.getZ() - variables.getY();
    }

    public static double getDZ(Vector variables) {
        return variables.getX() * variables.getY() - B * variables.getZ();
    }

    public static Vector getDerivatives(Vector variables) {
        return new Vector(getDX(variables), getDY(variables), getDZ(variables));
    }

    public static void setB(double b) {
        B = b;
    }

    public static void setR(double r) {
        R = r;
    }

    public static void setSigma(double sigma) {
        SIGMA = sigma;
    }

    public static double getB() {
        return B;
    }

    public static double getR() {
        return R;
    }

    public static double getSigma() {
        return SIGMA;
    }
}
