package javagulp.view.structPredict;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

public class CrossoverProbability extends TitledPanel {

	private static final long serialVersionUID = -3210983757540993050L;

	private final JTextField txtMin = new JTextField("0.4");
	private final JTextField txtMax = new JTextField("0.4");
	private final JTextField txtIncrease = new JTextField("0.0");

	private final JLabel lblMin = new JLabel("from");
	private final JLabel lblMax = new JLabel("to");
	private final JLabel lblIncrease = new JLabel("increase crossover probability by");

	CrossoverProbability() {
		super();

		txtMin.setBounds(70, 46, 46, 20);
		add(txtMin);
		lblMin.setBounds(9, 48, 55, 15);
		add(lblMin);
		lblMax.setBounds(122, 47, 33, 15);
		add(lblMax);
		txtMax.setBackground(Back.grey);
		txtMax.setBounds(161, 45, 49, 21);
		add(txtMax);
		txtIncrease.setBackground(Back.grey);
		txtIncrease.setBounds(271, 19, 49, 21);
		add(txtIncrease);
		lblIncrease.setBounds(9, 22, 256, 15);
		add(lblIncrease);
	}

	String writeCrossover() throws IncompleteOptionException {
		String lines = "";
		final String min = txtMin.getText(), max = txtMax.getText(), inc = txtIncrease.getText();
		if (!min.equals("0.4") && !max.equals("0.4") && inc.equals("0.0")) {
			if (min.equals("") || max.equals("") || inc.equals(""))
				throw new IncompleteOptionException("Please enter a values for crossover probability.");
			Double.parseDouble(min);
			Double.parseDouble(max);
			Double.parseDouble(inc);
			lines = "crossover " + txtMin.getText();
			if (!inc.equals("0.0")) {
				lines += " " + max + " " + inc;
			} else {
				if (!max.equals("0.4"))
					lines += " " + max;
			}
			lines += Back.newLine;
		}
		return lines;
	}
	/**
	 * @return
	 */
}
