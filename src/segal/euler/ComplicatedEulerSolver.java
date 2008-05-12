package segal.euler;

import segal.EquationSystem;
import segal.ISolver;
import segal.tools.Vector;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: danielpenkin
 */
public class ComplicatedEulerSolver implements ISolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);
    private Vector newDerivatives;
    private Vector derivatives = EquationSystem.getDerivatives(variables);

    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            newDerivatives = SimpleEulerSolver.calculateStep(variables, derivatives);
            variables = variables.add(newDerivatives.multiply(DELTA));
            myAndrewBannedThisName.put(t, variables);
            derivatives = EquationSystem.getDerivatives(variables);
            t += DELTA;
        } while (t < END);
        return Collections.unmodifiableMap(myAndrewBannedThisName);
    }
}
