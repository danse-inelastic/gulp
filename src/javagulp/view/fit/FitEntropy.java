package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FitEntropy extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 4224801003945809227L;

	private final JTextField txtWeight = new JTextField();
	private final JTextField txtConstant = new JTextField();

	private final JLabel lblEnergy = new JLabel("entropy");
	private final JLabel lblWeight = new JLabel("weight");

	private final JComboBox cboUnits = new JComboBox(new String[] { "eV/(Kmol)",
	"J/(Kmol)" });

	//public String gulpFileLines = "";

	public FitEntropy() {
		super();

		lblEnergy.setBounds(19, 19, 65, 20);
		add(lblEnergy);
		lblWeight.setBounds(19, 45, 65, 25);
		add(lblWeight);
		txtConstant.setBounds(85, 19, 72, 20);
		add(txtConstant);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(85, 47, 72, 20);
		add(txtWeight);
		cboUnits.setBounds(160, 19, 90, 20);
		add(cboUnits);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		// From the documentation: Remember that it is important to set the
		// temperature for the structure otherwise the entropy will be zero.
		// Also you should ensure that sufficient K points are sampled to give
		// an accurate value. Remember that the value should be per mole of
		// primitive unit cells, not just per mole when Z does not equal 1.
		if (txtConstant.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for entropy");
		Double.parseDouble(txtConstant.getText());
		gulpFileLines = "entropy " + txtConstant.getText();
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		if (cboUnits.getSelectedIndex() != 0)
			gulpFileLines += " j/kmol";
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}