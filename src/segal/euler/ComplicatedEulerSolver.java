package segal.euler;

import segal.AbstractSolver;
import segal.EquationSystem;
import segal.tools.Vector;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: danielpenkin
 */
public class ComplicatedEulerSolver extends AbstractSolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);
    private Vector derivatives;
    private Vector newVariables;
    private Vector newDerivatives;
    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            // Here goes the true formula from Amosov, Dubinsky p.437
            // Y[n+1] = Y[n]        + { F'(Y[n]) + F'(Y[n] + F'(Y[n]) * DELTA) } * DELTA
            //          [variables] + { derivatives      +     newDerivatives  } * DELTA
            derivatives = EquationSystem.getDerivatives(variables);
            newVariables = variables.add(derivatives.multiply(DELTA));
            newDerivatives = EquationSystem.getDerivatives(newVariables);
            newDerivatives = newDerivatives.add(derivatives);
            variables = variables.add(newDerivatives.multiply(DELTA / 2.0));
            myAndrewBannedThisName.put(t, variables);
            t += DELTA;
        } while (t < END);
        return Collections.unmodifiableMap(myAndrewBannedThisName);
    }

}
