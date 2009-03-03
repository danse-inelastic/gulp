package javagulp.view.potential;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Spring extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 3039970034200519518L;

	private PPP k2 = new PPP("<html>k<sub>2</sub> (eV/&Aring;<sup>2</sup>)</html>");
	private PPP k4 = new PPP("<html>k<sub>4</sub> (eV/&Aring;<sup>4</sup>)</html>");
	
	private JTextField txtCutoffDistance = new JTextField("0.6");

	private TitledPanel pnlCoreShell = new TitledPanel();
	
//	private JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
//			new String[] {"kjmol", "kcal"}));

//	private JLabel lblUnits = new JLabel("units");
	private JLabel lblCutoffDistnce = new JLabel("<html>(&Aring;)</html>");
	private JLabel lblSpringEq = new JLabel("<html>E = 1/2 k<sub>2</sub>r<sup>2</sup> + 1/24 k<sub>4</sub>r<sup>4</sup></html>");

	public Spring() {
		super(1);
		setTitle("spring between the core and shell");

		lblSpringEq.setOpaque(false);
		lblSpringEq.setBounds(23, 26, 160, 25);
		add(lblSpringEq);
		
		k2.setBounds(10, 75, 250, 25);
		add(k2);
		k4.txt.setText("0.0");
		k4.txt.setBackground(Back.grey);
		k4.setBounds(10, 100, 250, 25);
		add(k4);
//		lblUnits.setBounds(10, 200, 70, 21);
//		add(lblUnits);
		//cboUnits.setBounds(100, 200, 85, 21);
		//add(cboUnits);
		
		pnlCoreShell.setTitle("core-shell interaction cutoff distance");
		pnlCoreShell.setBounds(7, 140, 251, 52);
		add(pnlCoreShell);
		txtCutoffDistance.setBounds(25, 21, 81, 20);
		pnlCoreShell.add(txtCutoffDistance);
		lblCutoffDistnce.setBounds(114, 18, 80, 25);
		pnlCoreShell.add(lblCutoffDistnce);
		
		params = new PPP[] {k2, k4};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		if (k2.txt.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for k2");
		Double.parseDouble(k2.txt.getText());

		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		String lines = "spring ";
//		if (cboUnits.getSelectedIndex() != 0)
//			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + k2.txt.getText();
		if (!k4.txt.getText().equals("") && !k4.txt.getText().equals("0.0")) {
			Double.parseDouble(k4.txt.getText());
			lines += " " + k4.txt.getText();
		}
		JCheckBox[] boxes = { k2.chk };
		lines += Back.writeFits(boxes);
		if (!k4.txt.getText().equals("") && !k4.txt.getText().equals("0.0")) {
			boxes = new JCheckBox[] {k4.chk};
			lines += Back.writeFits(boxes);
		}
		lines += Back.newLine;
		if (!txtCutoffDistance.getText().equals("")
				&& !txtCutoffDistance.getText().equals("0.6")) {
			Double.parseDouble(txtCutoffDistance.getText());
			lines += "cuts " + txtCutoffDistance.getText() + Back.newLine;
		}
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		return new Spring();
	}
}