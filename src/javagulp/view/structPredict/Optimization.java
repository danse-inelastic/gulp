package javagulp.view.structPredict;

import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

public class Optimization extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -1060521026503135675L;

	private final JLabel lblIterationsInterval = new JLabel("candidates per");
	private final JLabel lblSaveTheBest = new JLabel("save the best");
	private final JLabel lblComboIterations = new JLabel("iterations");

	private final JTextField txtSaveTheBest = new JTextField("2");
	private final JTextField txtIterationsInterval = new JTextField();

	private final JCheckBox chkOnly = new JCheckBox("<html>Save a <i>total</i> of n candidates for <i>all</i> iterations</html>");

	Optimization() {
		super();

		lblSaveTheBest.setBounds(14, 24, 112, 15);
		add(lblSaveTheBest);
		txtSaveTheBest.setBounds(132, 22, 51, 20);
		add(txtSaveTheBest);
		lblIterationsInterval.setBounds(189, 24, 125, 15);
		add(lblIterationsInterval);
		txtIterationsInterval.setBounds(320, 20, 60, 23);
		add(txtIterationsInterval);
		lblComboIterations.setBounds(386, 24, 60, 15);
		add(lblComboIterations);
		chkOnly.setBounds(533, 24, 350, 15);
		add(chkOnly);
	}

	String writeBest() throws IncompleteOptionException {
		String lines = "";
		final String best = txtSaveTheBest.getText(), iter = txtIterationsInterval.getText();
		if (!best.equals("2") || !iter.equals("") || chkOnly.isSelected()) {
			lines = "best";
			if (best.equals(""))
				throw new IncompleteOptionException("Please enter a value for genetic algorithm candidates");
			Integer.parseInt(best);
			lines += " " + best;
			if (!iter.equals("")) {
				Integer.parseInt(iter);
				lines += " " + iter;
			}
			if (chkOnly.isSelected())
				lines += "only";
			lines += Back.newLine;
		}
		return lines;
	}
}
