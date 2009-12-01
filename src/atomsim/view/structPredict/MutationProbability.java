package javagulp.view.structPredict;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class MutationProbability extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -2625966816110087416L;

	private final JTextField txtInitial = new JTextField();
	private final JTextField txtFinal = new JTextField();
	private final JTextField txtIncrease = new JTextField("0.0");

	private final JLabel lblInitial = new JLabel("initial");
	private final JLabel lblFinal = new JLabel("final");
	private final JLabel lblIncrease = new JLabel("<html>increase every 20 iterations by</html>");

	MutationProbability() {
		super();

		txtInitial.setBounds(76, 25, 75, 19);
		add(txtInitial);
		lblFinal.setBounds(175, 27, 61, 15);
		add(lblFinal);
		txtFinal.setBackground(Back.grey);
		txtFinal.setBounds(242, 25, 75, 20);
		add(txtFinal);
		txtIncrease.setBounds(252, 48, 65, 20);
		add(txtIncrease);
		lblIncrease.setBounds(5, 50, 231, 15);
		add(lblIncrease);
		lblInitial.setBounds(5, 25, 65, 15);
		add(lblInitial);
	}

	String writeMutation() throws IncompleteOptionException {
		String lines = "";
		//TODO add a listener to discretation and have it set initial and final to 1/discretation
		if (!txtInitial.getText().equals("") || !txtFinal.getText().equals("") || !txtIncrease.getText().equals("0.0")) {
			if (txtInitial.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for genetic algorithm mutation initial");
			Double.parseDouble(txtInitial.getText());
			lines = "mutation " + txtInitial.getText();
			if (!txtIncrease.getText().equals("0.0")) {
				if (txtFinal.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm mutation final");
				Double.parseDouble(txtFinal.getText());
				Double.parseDouble(txtIncrease.getText());
				lines += " " + txtFinal.getText() + " " + txtIncrease.getText();
			} else {
				if (!txtFinal.getText().equals("")) {
					Double.parseDouble(txtFinal.getText());
					lines += " " + txtFinal.getText();
				}
			}
			lines += Back.newLine;
		}
		return lines;
	}
}
