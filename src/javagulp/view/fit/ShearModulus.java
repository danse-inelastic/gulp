package javagulp.view.fit;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ShearModulus extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -4906544212537436483L;

	private JTextField txtWeight = new JTextField();
	private JLabel lblWeight = new JLabel("weight (GPa)");

	public ShearModulus() {
		super();

		lblWeight.setBounds(240, 21, 80, 21);
		add(lblWeight);
		txtWeight.setBounds(329, 21, 70, 21);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);
	}

	@Override
	public String writeFit() {
		String lines = "shear_modulus";
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			lines += " " + txtWeight.getText();
		}
		return lines + Back.newLine;
	}
	
}