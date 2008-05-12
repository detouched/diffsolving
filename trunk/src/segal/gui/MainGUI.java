package segal.gui;

import segal.adams.ComplicatedAdamsSolver;
import segal.adams.SimpleAdamsSolver;
import segal.euler.ComplicatedEulerSolver;
import segal.euler.SimpleEulerSolver;
import segal.rungecut.RungeKutt;
import segal.tools.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * User: danielpenkin
 */
public class MainGUI extends JFrame {

    private final JRadioButton simpleEulerButton = new JRadioButton(new MyChangeAction("Simple Euler"));
    private final JRadioButton complicatedEulerButton = new JRadioButton(new MyChangeAction("Complicated Euler"));
    private final JRadioButton simpleAdamsButton = new JRadioButton(new MyChangeAction("Simple Adams"));
    private final JRadioButton complicatedAdamsButton = new JRadioButton(new MyChangeAction("Complicated Adams"));
    private final JRadioButton rungeKuttButton = new JRadioButton(new MyChangeAction("Runge-Kutt"));
    private final Graphic graphPanel = new Graphic();

    public MainGUI() {
        super("Differential system solving toolkit");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(simpleEulerButton);
        buttonGroup.add(complicatedEulerButton);
        buttonGroup.add(simpleAdamsButton);
        buttonGroup.add(complicatedAdamsButton);
        buttonGroup.add(rungeKuttButton);

        simpleEulerButton.setSelected(true);
        controlPanel.add(simpleEulerButton);
        controlPanel.add(complicatedEulerButton);
        controlPanel.add(simpleAdamsButton);
        controlPanel.add(complicatedAdamsButton);
        controlPanel.add(rungeKuttButton);

        graphPanel.setData(new SimpleEulerSolver().calculate());

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(graphPanel, BorderLayout.CENTER);
        pack();
    }

    private final class MyChangeAction extends AbstractAction {

        public MyChangeAction(String text) {
            super(text);
        }

        public void actionPerformed(ActionEvent e) {
            Map<Double, Vector> myData;
            if (simpleEulerButton.isSelected()) {
                myData = new SimpleEulerSolver().calculate();
            } else if (complicatedEulerButton.isSelected()) {
                myData = new ComplicatedEulerSolver().calculate();
            } else if (simpleAdamsButton.isSelected()) {
                myData = new SimpleAdamsSolver().calculate();
            } else if (complicatedAdamsButton.isSelected()) {
                myData = new ComplicatedAdamsSolver().calculate();
            } else {
                myData = new RungeKutt().calculate();
            }

            graphPanel.setData(myData);
        }
    }

}
