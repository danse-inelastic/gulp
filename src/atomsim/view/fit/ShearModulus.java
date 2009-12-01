package javagulp.view.fit;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ShearModulus extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -4906544212537436483L;

	private final JTextField txtWeight = new JTextField();
	private final JLabel lblWeight = new JLabel("weight (GPa)");

	public String gulpFileLines = "";

	private final JTextField txtShearModulus;

	public ShearModulus() {
		super();

		lblWeight.setBounds(336, 21, 99, 21);
		add(lblWeight);
		txtWeight.setBounds(441, 21, 70, 21);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);

		txtShearModulus = new JTextField();
		txtShearModulus.setBounds(189, 22, 90, 20);
		add(txtShearModulus);

		final JLabel shearModulusLabel = new JLabel();
		shearModulusLabel.setText("shear modulus (GPa)");
		shearModulusLabel.setBounds(21, 24, 162, 15);
		add(shearModulusLabel);
	}

	@Override
	public String writeFitPanel() {
		if (!txtShearModulus.getText().equals("")) {
			Double.parseDouble(txtShearModulus.getText());
			gulpFileLines += "shear_modulus" + Back.newLine + txtShearModulus.getText();
		}
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += "shear_modulus " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}