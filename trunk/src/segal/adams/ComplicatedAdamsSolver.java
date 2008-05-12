package segal.adams;

import segal.EquationSystem;
import segal.ISolver;
import segal.tools.Vector;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: danielpenkin
 */
public class ComplicatedAdamsSolver implements ISolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);
    private Vector newDerivatives;
    private Vector derivatives = EquationSystem.getDerivatives(variables);
    private Vector derivatives1 = EquationSystem.getDerivatives(variables);
    private Vector derivatives2 = EquationSystem.getDerivatives(variables);
    private Vector derivatives3 = EquationSystem.getDerivatives(variables);

    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            newDerivatives = SimpleAdamsSolver.calculateStep(variables, derivatives, derivatives1, derivatives2, derivatives3);
            Vector temp = newDerivatives.multiply(9).add(derivatives.multiply(19).add(derivatives1.multiply(-5)).add(derivatives2));
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
}
