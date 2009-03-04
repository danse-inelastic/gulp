package javagulp.view.fit;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BulkModulus extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -5936266634534615464L;

	private JTextField txtWeight = new JTextField();
	private JLabel lblWeight = new JLabel("weight");

	public String gulpFileLines = "";

	private JTextField txtBulkModulus;

	public BulkModulus() {
		super();

		lblWeight.setBounds(333, 21, 70, 21);
		add(lblWeight);
		txtWeight.setBounds(409, 22, 70, 20);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);

		txtBulkModulus = new JTextField();
		txtBulkModulus.setBounds(189, 22, 90, 20);
		add(txtBulkModulus);

		final JLabel bulkModulusLabel = new JLabel();
		bulkModulusLabel.setText("bulk modulus (GPa)");
		bulkModulusLabel.setBounds(21, 24, 162, 15);
		add(bulkModulusLabel);
	}

	@Override
	public String writeFitPanel() {
		if (!txtBulkModulus.getText().equals("")) {
			Double.parseDouble(txtBulkModulus.getText());
			gulpFileLines += "bulk_modulus" + Back.newLine + txtBulkModulus.getText();
		}
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}