package segal;

import segal.tools.Vector;

/**
 * User: danielpenkin
 */
public class EquationSystem {

    private static final double B = 13;
    private static final double R = 15;
    private static final double SIGMA = 10;

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
}
