package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class Sw3 extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 6274382726012875190L;

	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private G g = new G();

	private PPP k = new PPP(g.html("k (eV)"));
	private PPP theta = new PPP(g.html(g.theta + "<sub>0</sub> (deg)"));
	private PPP rho1 = new PPP(g.html(g.rho + "<sub>1</sub> (" + g.ang + ")"));
	private PPP rho2 = new PPP(g.html(g.rho + "<sub>2</sub> (" + g.ang + ")"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lbThreeBodyEq = new JLabel(g.html("E = k (" + g.rho
		+ "<sub>1</sub>/(r<sub>12</sub> - r<sub>12</sub><sup>cutoff</sup>) + " + g.rho
		+ "<sub>2</sub>/(r<sub>13</sub> - r<sub>13</sub><sup>cutoff</sup>))"
		+ "(cos " + g.theta + " - cos " + g.theta + "<sub>0</sub>)<sup>2</sup>"));

	public Sw3() {
		super(3);
		setTitle("stillinger-weber 3-body");

		lbThreeBodyEq.setOpaque(false);
		lbThreeBodyEq.setBounds(9, 18, 421, 40);
		add(lbThreeBodyEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		theta.setBounds(10, 95, 225, 25);
		add(theta);
		rho1.setBounds(10, 120, 225, 25);
		add(rho1);
		rho2.setBounds(10, 145, 225, 25);
		add(rho2);
		
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		lblUnits.setBounds(10, 170, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 170, 70, 21);
		add(cboUnits);
		lblImage.setBounds(438, 14, 122, 80);
		add(lblImage);
		
		params = new PPP[]{k, theta, rho1, rho2};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		String lines = "sw3 " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " " 
						+ radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		Sw3 e = new Sw3();
		e.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(e);
	}
}
