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
    static final double START_X = 1;
    static final double START_Y = 1;
    static final double START_Z = 1;


    Map<Double, Vector> calculate();
}
