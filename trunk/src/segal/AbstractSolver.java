package segal;

/**
 * User: danielpenkin
 */
public abstract class AbstractSolver implements ISolver {

    protected static double DELTA = 0.005;
    protected static final double BEG = 0;
    protected static final double END = 10;
    protected static final double START_X = 1;
    protected static final double START_Y = 1;
    protected static final double START_Z = 1;

    public static void setDelta(double delta) {
        DELTA = delta;
    }

    public static double getDelta() {
        return DELTA;
    }
}
