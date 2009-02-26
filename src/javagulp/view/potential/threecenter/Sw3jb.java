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

public class Sw3jb extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8513788731565650171L;

	private G g = new G();
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	private PPP k = new PPP(g.html("k (eV)"));
	private PPP theta = new PPP(g.html(g.theta + "<sub>0</sub> (deg)"));
	private PPP rho1 = new PPP(g.html(g.rho + "<sub>1</sub> (" + g.ang + ")"));
	private PPP rho2 = new PPP(g.html(g.rho + "<sub>2</sub> (" + g.ang + ")"));
	private PPP q = new PPP(g.html("Q (eV)"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = k F<sub>12</sub> F<sub>13</sub> exp(" + g.rho
		+ "<sub>1</sub>/(r<sub>12</sub> - r<sub>12</sub><sup>cutoff</sup>) + " + g.rho
		+ "<sub>2</sub>/(r<sub>13</sub> - r<sub>13</sub><sup>cutoff</sup>))"
		+ "(cos " + g.theta + " - cos " + g.theta + "<sub>0</sub>)<sup>2</sup><br>"
		+ "F<sub>12</sub>(q<sub>1</sub>, q<sub>2</sub>) = exp(1/Q)exp(1/(q<sub>1</sub> + q<sub>2</sub> - Q)) if q<sub>1</sub> + q<sub>2</sub> &lt; Q <br>"
		+ "F<sub>12</sub>(q<sub>1</sub>, q<sub>2</sub>) = 0 if q<sub>1</sub> + q<sub>2</sub> &gt; Q <br>"
		+ "F<sub>13</sub>(q<sub>1</sub>, q<sub>3</sub>) = exp(1/Q)exp(1/(q<sub>1</sub> + q<sub>3</sub> - Q)) if q<sub>1</sub> + q<sub>3</sub> &lt; Q <br>"
		+ "F<sub>13</sub>(q<sub>1</sub>, q<sub>3</sub>) = 0 if q<sub>1</sub> + q<sub>3</sub> &gt; Q"));

	public Sw3jb() {
		super(3);
		setTitle("stillinger-weber 3-body (Jiang & Brown, Chem. Eng. Sci., 49, 2991 (2000))");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(14, 21, 504, 126);
		add(lblThreeBodyEq);
		k.setBounds(10, 170, 225, 25);
		add(k);
		theta.setBounds(10, 195, 225, 25);
		add(theta);
		rho1.setBounds(10, 220, 225, 25);
		add(rho1);
		rho2.setBounds(10, 245, 225, 25);
		add(rho2);
		q.setBounds(10, 270, 225, 25);
		add(q);
		
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(270, 170, radii.getWidth(), radii.getHeight());
		add(radii);
		
		lblUnits.setBounds(325, 265, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(385, 265, 70, 21);
		add(cboUnits);
		lblImage.setBounds(532, 35, 119, 77);
		add(lblImage);
		
		params = new PPP[]{k, theta, rho1, rho2, q};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		String lines = "sw3jb " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " " 
				+ radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		Sw3jb s = new Sw3jb();
		s.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(s);
	}
}