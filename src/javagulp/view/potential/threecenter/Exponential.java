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

public class Exponential extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 9078746011183258181L;

	private G g = new G();
	
	private PPP k = new PPP(g.html("k (eV)"));
	private PPP rho1 = new PPP(g.html(g.rho + "<sub>1</sub> (" + g.ang
			+ "<sup>-1</sup>)"));
	private PPP rho2 = new PPP(g.html(g.rho + "<sub>2</sub> (" + g.ang
			+ "<sup>-1</sup>)"));
	private PPP rho3 = new PPP("<html>&#961;<sub>3</sub> (" + g.ang
			+ "<sup>-1</sup>)</html>");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = k exp( -" + g.rho
			+ "<sub>1</sub> r<sub>12</sub>)" + " exp( -" + g.rho
			+ "<sub>2</sub> r<sub>13</sub>) exp( -" + g.rho
			+ "<sub>2</sub> r<sub>23</sub>)"));

	public Exponential() {
		super(3);
		setTitle("exponential");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 18, 381, 40);
		add(lblThreeBodyEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		rho1.setBounds(10, 95, 225, 25);
		add(rho1);
		rho2.setBounds(10, 120, 225, 25);
		add(rho2);
		rho3.setBounds(10, 145, 225, 25);
		add(rho3);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		cboUnits.setBounds(90, 170, 70, 21);
		add(cboUnits);
		lblUnits.setBounds(10, 170, 40, 21);
		add(lblUnits);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[] {k, rho1, rho2, rho3};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;

		String lines = "exponential " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
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
		Exponential e = new Exponential();
		e.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(e);
	}
}