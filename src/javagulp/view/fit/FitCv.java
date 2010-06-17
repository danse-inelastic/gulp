package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FitCv extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -7112920960813223133L;

	private final G g = new G();

	private final JTextField txtConstant = new JTextField();
	private final JTextField txtWeight = new JTextField();

	private final JLabel lblEnergy = new JLabel(g.html("heat capacity C<sub>V</sub>"));
	private final JLabel lblWeight = new JLabel("weight");

	private final JComboBox cboUnits = new JComboBox(new String[] { "eV/(Kmol)",
	"J/(Kmol)" });

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
		cboUnits.setBounds(217, 39, 90, 20);
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
                gulpFileLines = "Cv " + txtConstant.getText();
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