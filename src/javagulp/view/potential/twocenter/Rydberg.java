package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class Rydberg extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7705418488227439909L;

	private G g = new G();

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEq = new JLabel(g.html("E = -A [1 + B(r/r<sub>0</sub> - 1)]exp(-B(r/r<sub>0</sub> - 1))"));

	private PPP A = new PPP("A (eV)");
	private PPP B = new PPP("B");
	private PPP r0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	public Rydberg() {
		super(2);
		setTitle("rydberg");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBounds(7, 17, 274, 37);
		add(lblEq);
		A.setBounds(10, 55, 225, 25);
		add(A);
		B.setBounds(10, 80, 225, 25);
		add(B);
		r0.setBounds(10, 105, 225, 25);
		add(r0);
		lblUnits.setBounds(10, 130, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 130, 70, 21);
		add(cboUnits);
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{A, B, r0};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Potential pot = Back.getPanel().getPotential();
		Back.checkAndParseD(params);
		
		String lines = "rydberg " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
						+ Back.concatFields(params) + " " + radii.writeRadii() + Back.writeFits(params);
		return lines + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		Rydberg r = new Rydberg();
		r.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(r);
	}
}