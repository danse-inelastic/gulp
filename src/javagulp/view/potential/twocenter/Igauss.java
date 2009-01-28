package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;
import javagulp.view.top.Potential;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Igauss extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 5670985001006470351L;
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEquation = new JLabel("<html>E = -A * exp(-b(r - r<sub>0</sub>)<sup>2</sup>)</html>");
	
	private PPP a = new PPP("A");
	private PPP b = new PPP("b");
	private PPP r0 = new PPP("r0");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	public Igauss() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };

		setTitle("inverted gaussian");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 225, 21);
		add(a);
		a.setBounds(10, 55, 225, 21);
		add(b);
		b.setBounds(10, 90, 225, 21);
		add(r0);
		r0.setBounds(10, 125, 225, 21);
		add(lblUnits);
		lblUnits.setBounds(10, 160, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(90, 160, 70, 21);
		radii = new Radii(true);
		radii.setBounds(240, 90, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{a, b ,r0};
	}

	@Override
	public PotentialPanel clone() {
		Igauss ig = new Igauss();
		ig.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(ig);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();
		
		String lines = "igauss " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos()
				+ Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
}
