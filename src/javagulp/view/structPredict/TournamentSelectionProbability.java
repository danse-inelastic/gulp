package javagulp.view.structPredict;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TournamentSelectionProbability extends TitledPanel implements
Serializable {

	private static final long serialVersionUID = -6288129306797651735L;

	private final int TOUR_MIN = 0, TOUR_MAX = 100, TOUR_INIT = 80;

	private final JSlider sldInitial = new JSlider(SwingConstants.HORIZONTAL, TOUR_MIN,
			TOUR_MAX, TOUR_INIT);
	private final JLabel lblInitial = new JLabel("initial");
	private final JSlider sldFinal = new JSlider(SwingConstants.HORIZONTAL, TOUR_MIN,
			TOUR_MAX, TOUR_INIT);
	private final JLabel lblFinal = new JLabel("final");
	private final JLabel lblStepSize = new JLabel("step size");
	private final JTextField txtStepSize = new JTextField("0.0");

	TournamentSelectionProbability() {
		super();

		sldInitial.setValue(80);
		sldInitial.setMajorTickSpacing(20);
		sldInitial.setPaintTicks(true);
		sldInitial.setPaintLabels(true);
		sldInitial.setBounds(7, 42, 134, 42);
		add(sldInitial);
		lblInitial.setBounds(14, 21, 42, 14);
		add(lblInitial);
		sldFinal.setValue(80);
		sldFinal.setMajorTickSpacing(20);
		sldFinal.setPaintTicks(true);
		sldFinal.setPaintLabels(true);
		sldFinal.setBounds(159, 43, 134, 40);
		add(sldFinal);
		lblFinal.setBounds(159, 22, 44, 15);
		add(lblFinal);
		lblStepSize.setBounds(333, 21, 66, 15);
		add(lblStepSize);
		txtStepSize.setBounds(333, 42, 66, 22);
		add(txtStepSize);
	}

	String writeTournament() throws IncompleteOptionException {
		String lines = "";
		final String step = txtStepSize.getText();
		final double init = sldInitial.getValue(), finl = sldFinal.getValue();
		if (init != 80 || finl != 80 || !step.equals("0.0")) {
			lines = "tournament " + (init/100);
			if (finl != 80) {
				lines += " " + (finl/100);
			}
			if (!step.equals("0.0")) {
				Double.parseDouble(step);
				lines += " " + step;
			}
			lines += Back.newLine;
		}
		return lines;
	}


}
