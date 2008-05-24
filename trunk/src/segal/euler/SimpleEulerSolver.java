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
public class SimpleEulerSolver extends AbstractSolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);
    private Vector derivatives = EquationSystem.getDerivatives(variables);

    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            variables = variables.add(derivatives.multiply(DELTA));
            myAndrewBannedThisName.put(t, variables);
            derivatives = EquationSystem.getDerivatives(variables);
            t += DELTA;
        } while (t < END);
        return Collections.unmodifiableMap(myAndrewBannedThisName);
    }

    /*package-local*/
    static Vector calculateStep(Vector vars, Vector d) {
        return vars.add(d.multiply(DELTA));
    }
}
