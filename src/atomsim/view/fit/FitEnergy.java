package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FitEnergy extends AbstractFit implements Serializable {

	private JTextField txtEnergy;
	private JLabel energyLabel;
	private static final long serialVersionUID = 4113828552611838086L;

	private final JTextField txtWeight = new JTextField("1.0");

	private final JLabel lblWeight = new JLabel("weight");

	private final JComboBox cboUnits = new JComboBox(new String[] { "eV", "kcal",
			"au", "kjmol-1" });

	public String gulpFileLines = "";

	public FitEnergy() {
		super();

		lblWeight.setBounds(196, 17, 69, 25);
		add(lblWeight);
		txtWeight.setBounds(279, 20, 72, 20);
		txtWeight.setBackground(Back.grey);
		add(txtWeight);
		cboUnits.setBounds(357, 19, 90, 20);
		add(cboUnits);
		add(getEnergyLabel());
		add(getTxtEnergy());
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		if (getTxtEnergy().getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for the energy");

		if (cboUnits.getSelectedIndex() != 0)
			gulpFileLines += "energy " + cboUnits.getSelectedItem() + Back.newLine;
		Double.parseDouble(getTxtEnergy().getText());
		gulpFileLines = "energy " + getTxtEnergy().getText();
		if (!txtWeight.getText().equals("") && !txtWeight.getText().equals("1.0")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}
	/**
	 * @return
	 */
	protected JLabel getEnergyLabel() {
		if (energyLabel == null) {
			energyLabel = new JLabel();
			energyLabel.setText("energy");
			energyLabel.setBounds(10, 22, 69, 15);
		}
		return energyLabel;
	}
	/**
	 * @return
	 */
	protected JTextField getTxtEnergy() {
		if (txtEnergy == null) {
			txtEnergy = new JTextField();
			txtEnergy.setBounds(85, 20, 90, 19);
		}
		return txtEnergy;
	}

}