package segal.gui;

import segal.AbstractSolver;
import segal.EquationSystem;
import segal.adams.ComplicatedAdamsSolver;
import segal.adams.SimpleAdamsSolver;
import segal.euler.ComplicatedEulerSolver;
import segal.euler.SimpleEulerSolver;
import segal.rungecut.RungeKutt;
import segal.tools.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * User: danielpenkin
 */
public class MainGUI extends JFrame {

    public final static int MAX_SCALE_LEVEL = 50;
    private final JRadioButton simpleEulerButton = new JRadioButton(new MyChangeAction("Simple Euler"));
    private final JRadioButton complicatedEulerButton = new JRadioButton(new MyChangeAction("Complicated Euler"));
    private final JRadioButton simpleAdamsButton = new JRadioButton(new MyChangeAction("Simple Adams"));
    private final JRadioButton complicatedAdamsButton = new JRadioButton(new MyChangeAction("Complicated Adams"));
    private final JRadioButton rungeKuttButton = new JRadioButton(new MyChangeAction("Runge-Kutt"));
    private final Graphic graphPanel;
    private final JSlider bSlider = new JSlider(JSlider.HORIZONTAL, 0, 499, (int) (EquationSystem.getB() * 10));
    private final JSlider rSlider = new JSlider(JSlider.HORIZONTAL, 0, 499, (int) (EquationSystem.getR() * 10));
    private final JSlider sigmaSlider = new JSlider(JSlider.HORIZONTAL, 0, 499, (int) (EquationSystem.getSigma() * 10));
    private final JSlider stepSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, (int) (AbstractSolver.getDelta() * 1000));
    private final JLabel bLabel = new JLabel("B: " + EquationSystem.getB());
    private final JLabel rLabel = new JLabel("R: " + EquationSystem.getR());
    private final JLabel sigmaLabel = new JLabel("Sigma: " + EquationSystem.getSigma());
    private final JLabel stepLabel = new JLabel("Step: " + AbstractSolver.getDelta());

    private final JLabel zoomLabel;
    private final JSlider zoomSlider = new JSlider(JSlider.VERTICAL, 1, MAX_SCALE_LEVEL, 5);

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
        addComponentsToPanel(controlPanel,
                simpleEulerButton,
                complicatedEulerButton,
                simpleAdamsButton,
                complicatedAdamsButton,
                rungeKuttButton
        );

        JPanel adjustPanel = new JPanel();
        AdjustmentsChangedListener slidersListener = new AdjustmentsChangedListener();
        addListenerToSliders(slidersListener,
                bSlider,
                rSlider,
                sigmaSlider,
                stepSlider);
        addComponentsToPanel(adjustPanel,
                bLabel,
                bSlider,
                rLabel,
                rSlider,
                sigmaLabel,
                sigmaSlider,
                stepLabel,
                stepSlider
        );

        zoomSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                graphPanel.setScaleFactor(zoomSlider.getValue());
                zoomLabel.setText("Zoom: " + zoomSlider.getValue());
            }
        });
        zoomLabel = new JLabel("Zoom: " + zoomSlider.getValue());
        graphPanel = new Graphic(zoomSlider.getValue());
        graphPanel.setData(new SimpleEulerSolver().calculate());

        JPanel zoomPanel = new JPanel(new BorderLayout());
        zoomPanel.add(zoomLabel, BorderLayout.NORTH);
        zoomPanel.add(zoomSlider, BorderLayout.CENTER);
        zoomPanel.setPreferredSize(new Dimension(60, 0));

        JPanel wholePanel = new JPanel(new BorderLayout());
        wholePanel.add(controlPanel, BorderLayout.NORTH);
        wholePanel.add(graphPanel, BorderLayout.CENTER);
        wholePanel.add(adjustPanel, BorderLayout.SOUTH);
        wholePanel.add(zoomPanel, BorderLayout.WEST);
        wholePanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10));

        getContentPane().add(wholePanel);
        pack();

    }

    private void addListenerToSliders(ChangeListener listener, JSlider... sliders) {
        for (JSlider slider : sliders)
            slider.addChangeListener(listener);
    }

    private void addComponentsToPanel(JPanel panel, JComponent... components) {
        for (JComponent comp : components)
            panel.add(comp);
    }

    private final class MyChangeAction extends AbstractAction {

        public MyChangeAction(String text) {
            super(text);
        }

        public void actionPerformed(ActionEvent e) {
            redrawGraphic();
        }
    }

    private final class AdjustmentsChangedListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            bLabel.setText("B: " + bSlider.getValue() / 10.0);
            rLabel.setText("R: " + rSlider.getValue() / 10.0);

            sigmaLabel.setText("Sigma: " + sigmaSlider.getValue() / 10.0);
            stepLabel.setText("Step: " + (stepSlider.getValue() / 1000.0));

            EquationSystem.setB(bSlider.getValue() / 10.0);
            EquationSystem.setR(rSlider.getValue() / 10.0);

            EquationSystem.setSigma(sigmaSlider.getValue() / 10.0);
            AbstractSolver.setDelta((stepSlider.getValue() / 1000.0));
            redrawGraphic();
        }

    }

    public String getSliderTrueValue(JSlider slider, double podgon) {
        String textValue = String.valueOf(slider.getValue() / podgon);

        return textValue;

    }

    private void redrawGraphic() {
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
