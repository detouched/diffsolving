package segal.rungecut;

import segal.AbstractSolver;
import segal.EquationSystem;
import segal.tools.Vector;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: sergeygindin
 */
public class RungeKutt extends AbstractSolver {

    private Vector variables = new Vector(START_X, START_Y, START_Z);

    private double k1, k2, k3, k4;
    private double l1, l2, l3, l4;
    private double m1, m2, m3, m4;
    private double addX, addY, addZ;

    private final Map<Double, Vector> myAndrewBannedThisName = new TreeMap<Double, Vector>();

    public Map<Double, Vector> calculate() {
        double t = BEG;
        do {
            k1 = EquationSystem.getDX(variables) * DELTA;
            l1 = EquationSystem.getDY(variables) * DELTA;
            m1 = EquationSystem.getDZ(variables) * DELTA;

            Vector tempVars = new Vector(variables.getX() + k1 / 2, variables.getY() + l1 / 2, variables.getZ() + m1 / 2);

            k2 = EquationSystem.getDX(tempVars) * DELTA;
            l2 = EquationSystem.getDY(tempVars) * DELTA;
            m2 = EquationSystem.getDZ(tempVars) * DELTA;

            tempVars = new Vector(variables.getX() + k2 / 2, variables.getY() + l2 / 2, variables.getZ() + m2 / 2);

            k3 = EquationSystem.getDX(tempVars) * DELTA;
            l3 = EquationSystem.getDY(tempVars) * DELTA;
            m3 = EquationSystem.getDZ(tempVars) * DELTA;

            tempVars = new Vector(variables.getX() + k3, variables.getY() + l3, variables.getZ() + m3);

            k4 = EquationSystem.getDX(tempVars) * DELTA;
            l4 = EquationSystem.getDY(tempVars) * DELTA;
            m4 = EquationSystem.getDZ(tempVars) * DELTA;

            addX = (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            addY = (l1 + 2 * l2 + 2 * l3 + l4) / 6;
            addZ = (m1 + 2 * m2 + 2 * m3 + m4) / 6;

            variables = new Vector(variables.getX() + addX, variables.getY() + addY, variables.getZ() + addZ);
            myAndrewBannedThisName.put(t, variables);

            t += DELTA;
        } while (t < END);
        return Collections.unmodifiableMap(myAndrewBannedThisName);
    }
}
