package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;
import javagulp.view.top.Potential;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class Bcoscross extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -6339900779260933429L;

	private G g = new G();
	
	private PPP k = new PPP(g.html("k (eV/" + g.ang + "<sup>2</sup>)"));
	private PPP b = new PPP("b");
	private PPP m = new PPP("m");
	private PPP n = new PPP("n");
	private PPP r12 = new PPP("<html>r<sub>12</sub><sup>0</sup> (" + g.ang + ")</html>");
	private PPP r13 = new PPP("<html>r<sub>13</sub><sup>0</sup> (" + g.ang + ")</html>");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = k(1 + b cos<sup>m</sup>(n " + g.theta + "))(r<sub>12</sub> - "
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
		Potential pot = Back.getPanel().getPotential();

		String lines = "bcoscross " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
		if (!pot.threeAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}

	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
	}
	
	@Override
	public PotentialPanel clone() {
		Bcoscross b = new Bcoscross();
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}
}