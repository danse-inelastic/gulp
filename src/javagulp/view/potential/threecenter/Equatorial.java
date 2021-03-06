package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Equatorial extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7032127834638013804L;

	private final G g = new G();

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private final JLabel lblUnits = new JLabel("units");
	private final JLabel lblEquation = new JLabel(g.html("E = (2K / n<sup>2</sup>)(1 - " +
			"cos(n * " + g.theta + ")) + 2K * exp(-" + g.beta + "(r<sub>13</sub> - r<sub>0</sub>))"));

	private final PPP k = new PPP("K");
	private final PPP n = new PPP("n");
	private final PPP beta = new PPP(g.html(g.beta));
	private final PPP r0 = new PPP("r0");

	public Equatorial() {
		super(3);
		setTitle("equatorial");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 400, 21);
		add(k);
		k.setBounds(10, 70, 225, 25);
		add(n);
		n.setBounds(10, 95, 225, 25);
		add(beta);
		beta.setBounds(10, 120, 225, 25);
		add(r0);
		r0.setBounds(10, 145, 225, 25);
		r0.chk.setVisible(false);
		add(lblUnits);
		lblUnits.setBounds(10, 170, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(90, 170, 70, 21);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[] {k, n, beta, r0};
	}

	@Override
	public PotentialPanel clone() {
		final Equatorial b = new Equatorial();
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "equatorial " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + getAtoms() + Back.concatFields(params);
		if (!pot.threeAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}

	@Override
	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
	}

}
