package javagulp.view.fit;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FitEnergy extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 4113828552611838086L;

	private JTextField txtWeight = new JTextField("1.0");

	private JLabel lblWeight = new JLabel("weight");

	private JComboBox cboUnits = new JComboBox(new String[] { "eV", "kcal",
			"au", "kjmol-1" });

	public FitEnergy() {
		super();

		lblWeight.setBounds(185, 17, 50, 25);
		add(lblWeight);
		txtWeight.setBounds(240, 19, 72, 20);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);
		cboUnits.setBounds(315, 19, 90, 20);
		add(cboUnits);
	}

	@Override
	public String writeFit() {
		String lines = "energy_of_configuration";
		if (cboUnits.getSelectedIndex() != 0)
			lines += " " + cboUnits.getSelectedItem();
		if (!txtWeight.getText().equals("")
				&& !txtWeight.getText().equals("1.0")) {
			Double.parseDouble(txtWeight.getText());
			lines += " " + txtWeight.getText();
		}
		return lines + Back.newLine;
	}
	
}