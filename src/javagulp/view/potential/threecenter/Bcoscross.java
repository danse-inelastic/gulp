package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Bcoscross extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -6339900779260933429L;

	private final G g = new G();

	private final PPP k = new PPP(g.html("k (eV/" + g.ang + "<sup>2</sup>)"));
	private final PPP b = new PPP("b");
	private final PPP m = new PPP("m");
	private final PPP n = new PPP("n");
	private final PPP r12 = new PPP("<html>r<sub>12</sub><sup>0</sup> (" + g.ang + ")</html>");
	private final PPP r13 = new PPP("<html>r<sub>13</sub><sup>0</sup> (" + g.ang + ")</html>");

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private final JLabel lblUnits = new JLabel("units");
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private final JLabel lblThreeBodyEq = new JLabel(g.html("E = k(1 + b cos<sup>m</sup>(n " + g.theta + "))(r<sub>12</sub> - "
			+ "r<sub>12</sub><sup>0</sup>) (r<sub>13</sub> - r<sub>13</sub><sup>0</sup>)"));

	public Bcoscross() {
		super(3);
		setTitle("bond-bond cross term with cosine angle dependence");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 18, 381, 40);
		add(lblThreeBodyEq);
		k.setBounds(10, 70, 225, 20);
		add(k);
		b.setBounds(10, 95, 225, 20);
		add(b);
		m.setBounds(10, 120, 225, 20);
		add(m);
		m.chk.setVisible(false);
		n.setBounds(10, 145, 225, 20);
		add(n);
		n.chk.setVisible(false);
		r12.setBounds(10, 170, 225, 25);
		add(r12);
		r13.setBounds(10, 195, 225, 25);
		add(r13);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(270, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		cboUnits.setBounds(91, 220, 70, 21);
		add(cboUnits);
		lblUnits.setBounds(19, 220, 40, 21);
		add(lblUnits);
		params = new PPP[] {k, b, m, n, r12, r13};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "bcoscross " + pot.threeAtomBondingOptions.getAll();
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

	@Override
	public PotentialPanel clone() {
		final Bcoscross b = new Bcoscross();
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}
}