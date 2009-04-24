package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class SW2 extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 7171895788167028302L;

	private G g = new G();

	private JLabel lblEq = new JLabel(g.html("E = A exp(" + g.rho
			+ "/(r - r<sub>cutoff</sub>))(B/r<sup>4</sup> - 1)"));
	private JLabel lblUnits = new JLabel("units");

	private PPP A = new PPP("A (eV)");
	private PPP B = new PPP(g.html("B (&Aring;<sup>4</sup>)"));
	private PPP rho = new PPP(g.html(g.rho + " (&Aring;)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	public SW2() {
		super(2);
		setTitle("stillinger-weber two body");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBounds(10, 20, 274, 37);
		add(lblEq);
		A.setBounds(10, 55, 225, 25);
		add(A);
		rho.setBounds(10, 80, 225, 25);
		add(rho);
		B.setBounds(10, 105, 225, 25);
		add(B);
		lblUnits.setBounds(10, 130, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 130, 85, 21);
		add(cboUnits);
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		Back.checkAndParseD(params);
		
		String lines = "sw2 " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos()
				+ Back.concatFields(params) + " " + radii.writeRadii()
				+ Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		SW2 sw = new SW2();
		sw.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(sw);
	}
}