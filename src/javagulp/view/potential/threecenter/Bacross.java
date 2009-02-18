package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.Potential;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class Bacross extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -605630037212105116L;

	private G g = new G();
	
	private PPP k1 = new PPP(g.html("k<sub>1</sub> (eV/" + g.ang + "<sup>2</sup>)"));
	private PPP k2 = new PPP(g.html("k<sub>2</sub> (eV/" + g.ang + "<sup>2</sup>)"));
	private PPP r12 = new PPP("<html>r<sub>12</sub><sup>0</sup> (" + g.ang + ")</html>");
	private PPP r23 = new PPP("<html>r<sub>13</sub><sup>0</sup> (" + g.ang + ")</html>");
	private PPP theta = new PPP(g.html(g.theta + "<sup>0</sup> (deg)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblthreeBodyEq = new JLabel(g.html("E = [k<sub>1</sub> (r<sub>12</sub> - r<sub>12</sub><sup>0</sup>) + "
			+ "k<sub>2</sub> (r<sub>13</sub> - r<sub>13</sub><sup>0</sup>)]("
			+ g.theta + " - " + g.theta + "<sup>0</sup>)"));
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	
	public Bacross() {
		super(3);
		setTitle("bond-bond-angle cross term");

		lblthreeBodyEq.setOpaque(false);
		lblthreeBodyEq.setBounds(9, 21, 381, 40);
		add(lblthreeBodyEq);
		
		k1.setBounds(10, 70, 225, 25);
		add(k1);
		k2.setBounds(10, 95, 225, 25);
		add(k2);
		r12.setBounds(10, 120, 225, 25);
		add(r12);
		r23.setBounds(10, 145, 225, 25);
		add(r23);
		theta.setBounds(10, 170, 225, 25);
		add(theta);
		
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		cboUnits.setBounds(91, 195, 70, 21);
		add(cboUnits);
		lblUnits.setBounds(20, 195, 40, 21);
		add(lblUnits);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[] {k1, k2, r12, r23, theta};
	}

	@Override
	public String writePotential() throws IncompleteOptionException,
			NumberFormatException {
		Potential pot = Back.getPanel().getPotential();
		Back.checkAndParseD(params);

		String lines = "bacross " + pot.threeAtomBondingOptions.getAll();
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
		Bacross b = new Bacross();
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}
}