package javagulp.view.fit;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BulkModulus extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -5936266634534615464L;

	private JTextField txtWeight = new JTextField();
	private JLabel lblWeight = new JLabel("weight (GPa)");

	public String gulpFileLines = "bulk_modulus";

	public BulkModulus() {
		super();

		lblWeight.setBounds(240, 21, 80, 21);
		add(lblWeight);
		txtWeight.setBounds(329, 21, 70, 21);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);
	}

	@Override
	public String writeFitPanel() {
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		return gulpFileLines + Back.newLine;
	}

}