package javagulp.view.potential.threecenter;

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

import javagulp.model.G;

public class Equatorial extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7032127834638013804L;
	
	private G g = new G();
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEquation = new JLabel(g.html("E = (2K / n<sup>2</sup>)(1 - " +
			"cos(n * " + g.theta + ")) + 2K * exp(-" + g.beta + "(r<sub>13</sub> - r<sub>0</sub>))"));
	
	private PPP k = new PPP("K");
	private PPP n = new PPP("n");
	private PPP beta = new PPP(g.html(g.beta));
	private PPP r0 = new PPP("r0");
	
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
		Equatorial b = new Equatorial();
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();
		
		String lines = "equatorial " + pot.threeAtomBondingOptions.getAll();
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

}
