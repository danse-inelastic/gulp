package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class CovExp extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 3996470017561018146L;

	private final JLabel lblUnits = new JLabel ("units");
	private final JLabel lblEquation = new JLabel(
	"<html>E = -D * exp(-a(r - r<sub>0</sub>)<sup>2</sup> / (2r))</html>");

	private final PPP d = new PPP("D");
	private final PPP a = new PPP("a");
	private final PPP r0 = new PPP("r0");

	private final JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});
	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public CovExp() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };

		setTitle("covalent-exponential");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 225, 21);
		add(d);
		d.setBounds(10, 55, 225, 21);
		add(a);
		a.setBounds(10, 90, 225, 21);
		add(r0);
		r0.setBounds(10, 125, 225, 21);
		add(lblUnits);
		lblUnits.setBounds(10, 160, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(90, 160, 70, 21);
		add(cboEnerGra);
		cboEnerGra.setBounds(10, 195, 100, 21);
		radii = new Radii(true);
		radii.setBounds(240, 90, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{d, a, r0};
	}

	@Override
	public PotentialPanel clone() {
		final CovExp co  = new CovExp();
		co.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		co.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		return super.clone(co);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "covexp " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad ";
		else
			lines += "ener ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + getAtoms()
		+ Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
}
