package javagulp.view.structPredict;

import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

public class Configurations extends TitledPanel implements Serializable {
	private static final long serialVersionUID = 4730400389192705976L;

	private final JComboBox cboStepSize = new JComboBox(new String[] { "0", "2" });

	private final JLabel lblNumber = new JLabel("number of configurations (must be even)");
	private final JLabel lblMax = new JLabel("maximum configurations");
	private final JLabel lblCombo = new JLabel("step size");

	private final JTextField txtNumber = new JTextField("10");
	private final JTextField txtMax = new JTextField("10");

	Configurations() {
		super();

		txtNumber.setBounds(275, 17, 54, 20);
		add(txtNumber);
		lblNumber.setBounds(9, 19, 260, 15);
		add(lblNumber);
		lblMax.setBounds(9, 37, 160, 15);
		add(lblMax);
		txtMax.setBackground(Back.grey);
		txtMax.setBounds(275, 38, 54, 20);
		add(txtMax);
		cboStepSize.setSelectedItem("2");
		cboStepSize.setBounds(275, 60, 54, 20);
		add(cboStepSize);
		lblCombo.setBounds(9, 58, 65, 15);
		add(lblCombo);
	}

	String writeConfigurations() throws IncompleteOptionException, InvalidOptionException {
		String lines = "";
		if (!txtNumber.getText().equals("10") || !txtMax.getText().equals("10")) {
			if (txtNumber.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for genetic algorithm configurations number");
			final int number = Integer.parseInt(txtNumber.getText());
			if (number % 2 != 0)
				throw new InvalidOptionException("genetic algorithm configurations number must be even");
			lines = "configurations " + txtNumber.getText();
			//TODO check that number < max ?
			if (!txtMax.getText().equals("") && !txtMax.getText().equals("10")) {
				Integer.parseInt(txtMax.getText());
				lines += " " + txtMax.getText();
			}
			lines += " " + cboStepSize.getSelectedItem() + Back.newLine;
		}
		return lines;
	}
}
