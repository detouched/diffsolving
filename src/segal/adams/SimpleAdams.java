package segal.adams;

import segal.impl.AbstractSolver;
import segal.tools.Vector;

import java.util.Map;

/**
 * User: danielpenkin
 */
public class SimpleAdams extends AbstractSolver {



    public SimpleAdams(int sigma, int b, int r) {
        super(sigma, b, r);
    }

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {



            t += DELTA;
        } while (t < END);
    }
}
