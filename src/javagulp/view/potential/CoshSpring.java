package javagulp.view.potential;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class CoshSpring extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -3167709441178868192L;
	
	private PPP k2 = new PPP("k2");
	private PPP d = new PPP("d");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	//TODO check this equation
	private JLabel lblEquation = new JLabel("<html>E = 1/2 * k<sub>2</sub>d<sup>2</sup></html>");
	private JLabel lblUnits = new JLabel("units");

	public CoshSpring() {
		super(1);
		setTitle("core-shell spring");
		add(k2);
		k2.setBounds(10, 49, 225, 21);
		add(d);
		d.setBounds(10, 84, 225, 21);
		add(lblEquation);
		lblEquation.setBounds(10, 20, 150, 21);
		lblUnits.setBounds(10, 110, 70, 21);
		add(lblUnits);
		cboUnits.setBounds(100, 110, 85, 21);
		add(cboUnits);
		
		params = new PPP[] {k2, d};
	}

	@Override
	public PotentialPanel clone() {
		return new CoshSpring();
	}
	
	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		
		String lines = "cosh-spring";
		if (cboUnits.getSelectedIndex() != 0)
			lines += " " + cboUnits.getSelectedItem();
		lines += Back.newLine + Back.getPanel().getPotential().getAtomCombos() + Back.fieldsAndFits(params);
		return lines + Back.newLine;
	}
}
