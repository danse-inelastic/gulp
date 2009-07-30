package javagulp.view.structPredict;

import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

public class Grid extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -3709125169034847513L;

	private final JTextField txtMin = new JTextField("6");
	private final JTextField txtMax = new JTextField("6");
	private final JTextField txtNumber = new JTextField("20");

	private final JLabel lblNumber = new JLabel("increase the grid side length after");
	private final JLabel lblMin = new JLabel("from a minimum of (2");
	private final JLabel lblMax = new JLabel("<html>)<sup>3</sup> by 1 until (2</html>");
	private final JLabel lblMaxPower = new JLabel("<html>)<sup>3</sup></html>");
	private final JLabel lblIterations = new JLabel("iterations");

	Grid() {
		super();

		txtMin.setBounds(164, 43, 33, 19);
		add(txtMin);
		txtMax.setBackground(Back.grey);
		txtMax.setBounds(313, 43, 33, 19);
		add(txtMax);
		lblNumber.setBounds(10, 21, 260, 15);
		add(lblNumber);
		txtNumber.setBackground(Back.grey);
		txtNumber.setBounds(276, 19, 39, 19);
		add(txtNumber);
		lblMin.setBounds(10, 52, 148, 15);
		add(lblMin);
		lblMaxPower.setBounds(357, 47, 33, 20);
		add(lblMaxPower);
		lblIterations.setBounds(321, 21, 80, 15);
		add(lblIterations);
		lblMax.setBounds(203, 47, 112, 20);
		add(lblMax);
	}

	String writeGrid() throws IncompleteOptionException {
		String lines = "";
		final String min = txtMin.getText(), max = txtMax.getText(), n = txtNumber.getText();
		if (!min.equals("6") || !max.equals("6") || !n.equals("20")) {
			if (min.equals(""))
				throw new IncompleteOptionException("Please enter a value for genetic algorithm grid min");
			Integer.parseInt(min);
			lines = "grid " + min;
			if (n.equals("")) {
				if (max.equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm grid max");
				Integer.parseInt(max);
				Integer.parseInt(n);
				lines += " " + max + " " + n;
			} else {
				if (!max.equals("")) {
					Integer.parseInt(max);
					lines += " " + max;
				}
			}
			lines += Back.newLine;
		}
		return lines;
	}

	
}
