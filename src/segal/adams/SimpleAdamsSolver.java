package segal.adams;

import segal.AbstractSolver;
import segal.EquationSystem;
import segal.tools.Vector;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: danielpenkin
 */
public class SimpleAdamsSolver extends AbstractSolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);
    private Vector derivatives = EquationSystem.getDerivatives(variables);
    private Vector derivatives1 = EquationSystem.getDerivatives(variables);
    private Vector derivatives2 = EquationSystem.getDerivatives(variables);
    private Vector derivatives3 = EquationSystem.getDerivatives(variables);

    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            Vector temp = derivatives.multiply(55).add(derivatives1.multiply(-59)).add(derivatives2.multiply(37)).add(derivatives3.multiply(-9));
            variables = variables.add(temp.multiply(DELTA / 24));
            myAndrewBannedThisName.put(t, variables);
            derivatives3 = derivatives2;
            derivatives2 = derivatives1;
            derivatives1 = derivatives;
            derivatives = EquationSystem.getDerivatives(variables);
            t += DELTA;
        } while (t < END);
        return Collections.unmodifiableMap(myAndrewBannedThisName);
    }

    /*package-local*/
    static Vector calculateStep(Vector vars, Vector d, Vector d1, Vector d2, Vector d3) {
        Vector temp = d.multiply(55).add(d1.multiply(-59)).add(d2.multiply(37)).add(d3.multiply(-9));
        temp = temp.multiply(DELTA / 24);
        return vars.add(temp);
    }
}
