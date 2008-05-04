package segal.impl;

import segal.ISolver;

/**
 * User: danielpenkin
 */
public abstract class AbstractSolver implements ISolver {

    protected final int myR;
    protected final int myB;
    protected final int mySigma;

    protected AbstractSolver(int sigma, int b, int r) {
        myR = r;
        myB = b;
        mySigma = sigma;
    }
}
