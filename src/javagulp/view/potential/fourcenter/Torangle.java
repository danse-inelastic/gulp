package javagulp.view.potential.fourcenter;

import java.awt.Color;
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

public class Torangle extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 4407426076307095091L;

	private G g = new G();
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblSketch = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblFourBodyEq = new JLabel(g.html("E = k cos(" + g.phi
			+ ")(" + g.theta + "<sub>123</sub> - " + g.theta
			+ "<sub>123</sub><sup>0</sup>)" + "(" + g.theta
			+ "<sub>234</sub> - " + g.theta + "<sub>234</sub><sup>0</sup>)"));
	
	private PPP K = new PPP(g.html("k (eV/rad<sup>2</sup>)"));
	private PPP Theta123 = new PPP(g.html(g.theta
			+ "<sub>123</sub><sup>0</sup> (deg)"));
	private PPP Theta234 = new PPP("<html>&#952;<sub>234</sub><sup>0</sup> (deg)</html>");

	public Torangle() {
		super(4);
		setTitle("torsional-angle cross potential");
		enabled = new boolean[] { false, true, true };
		
		lblFourBodyEq.setOpaque(false);
		lblFourBodyEq.setBackground(Color.white);
		lblFourBodyEq.setBounds(15, 24, 381, 33);
		add(lblFourBodyEq);
		K.setBounds(10, 70, 225, 25);
		add(K);
		Theta123.setBounds(10, 95, 225, 25);
		add(Theta123);
		Theta234.setBounds(10, 120, 225, 25);
		add(Theta234);
		lblUnits.setBounds(10, 145, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 145, 70, 21);
		add(cboUnits);
		lblSketch.setBounds(460, 24, 190, 75);
		add(lblSketch);
		radii = new Radii(new String[] {"12", "13", "23"});
		radii.setBounds(240, 70, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{K, Theta123, Theta234};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		
		String lines = "torangle " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " "
				+ radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		Torangle t = new Torangle();
		t.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(t);
	}
}