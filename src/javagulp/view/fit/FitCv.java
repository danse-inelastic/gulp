package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class FitCv extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -7112920960813223133L;

	private G g = new G();

	private JTextField txtConstant = new JTextField();
	private JTextField txtWeight = new JTextField();

	private JLabel lblEnergy = new JLabel(g.html("heat capacity C<sub>V</sub>"));
	private JLabel lblWeight = new JLabel("weight");

	private JComboBox cboUnits = new JComboBox(new String[] { "eV/(Kmol)",
			"J/(Kmol)" });

	public String gulpFileLines = "Cv " + txtConstant.getText();

	public FitCv() {
		super();

		lblEnergy.setBounds(19, 40, 130, 25);
		add(lblEnergy);
		lblWeight.setBounds(19, 85, 44, 25);
		add(lblWeight);
		txtConstant.setBounds(130, 40, 70, 20);
		add(txtConstant);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(76, 87, 72, 20);
		add(txtWeight);
		cboUnits.setBounds(200, 40, 90, 20);
		add(cboUnits);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		// From the documentation: Remember that it is important to set the
		// temperature for the structure otherwise the heat capacity will be
		// zero. Also you should ensure that sufficient K points are sampled to
		// give an accurate value. Remember that the value should be per mole of
		// primitive unit cells, not just per mole when Z does not equal 1.
		if (txtConstant.getText().equals(""))
			throw new IncompleteOptionException("Please enter the heat capacity constant");
		Double.parseDouble(txtConstant.getText());

		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		if (cboUnits.getSelectedIndex() != 0)
			gulpFileLines += " j/kmol";
		return gulpFileLines + Back.newLine;
	}
	
}