package segal;

import segal.tools.Vector;

import java.util.Map;

/**
 * User: danielpenkin
 */
public interface ISolver {
    static final double DELTA = 0.005;
    static final double BEG = 0;
    static final double END = 10;

    Map<Double, Vector> calculate();
}
