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

public class ThreeBody extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 3976613670175852991L;

	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private G g = new G();

	private PPP k = new PPP(g.html("k (eV rad<sup>-2</sup>)"));
	private PPP k3 = new PPP(g.html("k<sub>3</sub>&nbsp;(eV&nbsp;rad<sup>-3</sup>)"));
	private PPP k4 = new PPP(g.html("k<sub>4</sub>&nbsp;(eV&nbsp;rad<sup>-4</sup>)"));
	private PPP Theta = new PPP(g.html(g.theta + "<sub>0</sub> (deg)"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = 1/2 k(" + g.theta
			+ " - " + g.theta + "<sub>0</sub>)<sup>2</sup>"
			+ "+ 1/6 k<sub>3</sub>(" + g.theta + " - " + g.theta
			+ "<sub>0</sub>)<sup>3</sup>" + "+ 1/12 k<sub>4</sub>(" + g.theta
			+ " - " + g.theta + "<sub>0</sub>)<sup>4</sup>"));

	public ThreeBody() {
		super(3);
		setTitle("three body");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(7, 21, 378, 42);
		add(lblThreeBodyEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		k3.setBounds(10, 95, 225, 25);
		add(k3);
		k3.txt.setBackground(Back.grey);
		k3.txt.setText("0.0");
		k4.setBounds(10, 120, 225, 25);
		add(k4);
		k4.txt.setBackground(Back.grey);
		k4.txt.setText("0.0");
		Theta.setBounds(10, 145, 225, 25);
		add(Theta);
		lblUnits.setBounds(10, 170, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 170, 70, 21);
		add(cboUnits);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		getParams();
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		getParams();
		Back.checkAndParseD(params);
		
		String lines = "three ";
		// TODO check documentation for proper format w.r.t. k3 and k4
		if (K3())
			lines += "k3 ";
		if (K4())
			lines += "k4 ";
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		lines += pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
		if (!pot.threeAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
	
	private boolean K3() {
		return !k3.txt.getText().equals("") && !k3.txt.getText().equals("0.0");
	}
	
	private boolean K4() {
		return !k4.txt.getText().equals("") && !k4.txt.getText().equals("0.0");
	}
	
	private void getParams() {
		if (K3()) {
			if (K4()) {
				params = new PPP[]{k, k3, k4, Theta};
			} else {
				params = new PPP[]{k, k3, Theta};
			}
		} else {
			if (K4()) {
				params = new PPP[]{k, k4, Theta};
			} else {
				params = new PPP[]{k, Theta};
			}
		}
	}

	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
	}
	
	@Override
	public PotentialPanel clone() {
		getParams();
		ThreeBody t = new ThreeBody();
		t.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(t);
	}
}
