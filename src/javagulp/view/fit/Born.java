package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Born extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 3420150316171297777L;

	private final G g = new G();

	private final JComboBox cboTensorComponent = new JComboBox(new String[] { "",
			"xx", "yy", "zz", "xy", "xz", "yz" });

	private final JTextField txtAtomNum = new JTextField();
	private final JTextField txtConstant = new JTextField();
	private final JTextField txtWeight = new JTextField();

	private final JLabel lblConstant = new JLabel(g.html("Z<sub>eff</sub> (au)"));
	private final JLabel lblTensorComponent = new JLabel("tensor component");
	private final JLabel lblWeight = new JLabel("weight");
	private final JLabel lblAtomNum = new JLabel("atom number");
	private final JLabel lblEquation = new JLabel(g.html("q<sup>Born</sup> = "
			+ g.part + g.mu + "<sub>" + g.alpha + "</sub>/" + g.part + g.beta
			+ " &nbsp&nbsp " + g.alpha + ", " + g.beta + g.in + " x, y, z"));

	//public String gulpFileLines = "";

	public Born() {
		super();

		txtAtomNum.setBounds(99, 90, 33, 20);
		add(txtAtomNum);
		lblAtomNum.setBounds(5, 90, 86, 20);
		add(lblAtomNum);
		txtConstant.setBounds(205, 90, 69, 20);
		add(txtConstant);
		lblConstant.setBounds(145, 93, 56, 20);
		add(lblConstant);
		lblWeight.setBounds(281, 90, 43, 20);
		add(lblWeight);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(330, 90, 60, 20);
		add(txtWeight);
		lblTensorComponent.setBounds(205, 46, 119, 20);
		add(lblTensorComponent);
		cboTensorComponent.setBounds(330, 46, 52, 20);
		add(cboTensorComponent);
		lblEquation.setBounds(15, 21, 183, 65);
		add(lblEquation);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		// From the documentation: Note: Born effective charges cannot
		// currently be calculated with electronegativity equalization.
		if (txtAtomNum.getText().equals(""))
			throw new IncompleteOptionException("Please enter the atom number");
		if (txtConstant.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for Z effective");
		Double.parseDouble(txtAtomNum.getText());
		Double.parseDouble(txtConstant.getText());
		gulpFileLines += "bornq " + txtAtomNum.getText();
		if (cboTensorComponent.getSelectedIndex() != 0)
			gulpFileLines += " " + cboTensorComponent.getSelectedItem();
		gulpFileLines += " " + txtConstant.getText();
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}