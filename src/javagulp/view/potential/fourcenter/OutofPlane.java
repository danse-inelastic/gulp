package javagulp.view.potential.fourcenter;

import java.awt.Color;
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

public class OutofPlane extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -5227769822538560738L;

	private G g = new G();
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEq = new JLabel(g.html("E = kd<sup>2</sup> + k<sub>4</sub>d<sup>4</sup> where d represents the distance of atom 1 out of the plane of atoms 2-3-4"));
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	
	private PPP K = new PPP(g.html("k (eV/" + g.ang + "<sup>2</sup>)"));
	private PPP K4 = new PPP(g.html("k<sub>4</sub> (eV/" + g.ang + "<sup>4</sup>)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public OutofPlane() {
		super(4);
		setTitle("out of plane");

		lblEq.setOpaque(false);
		lblEq.setBackground(Color.white);
		lblEq.setBounds(17, 24, 640, 25);
		add(lblEq);
		K.setBounds(10, 70, 225, 25);
		add(K);
		K4.setBounds(10, 95, 225, 25);
		add(K4);
		K4.txt.setBackground(Back.grey);
		lblUnits.setBounds(10, 120, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 120, 70, 21);
		add(cboUnits);
		lblImage.setBounds(518, 55, 190, 75);
		add(lblImage);
		radii = new Radii(new String[] {"12", "13", "14"});
		radii.setBounds(240, 70, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{K, K4};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);

		Potential pot = Back.getPanel().getPotential();
		String lines = "outofplane " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + K.txt.getText() + " ";
		if (!K4.txt.getText().equals("") && !K4.txt.getText().equals("0.0")) {
			Double.parseDouble(K4.txt.getText());
			lines += K4.txt.getText() + " ";
		}
		lines += radii.writeRadii() + Back.writeFits(new PPP[]{K});
		if (!K4.txt.getText().equals("") && !K4.txt.getText().equals("0.0"))
			lines += Back.writeFits(new PPP[]{K4});
		return lines + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		OutofPlane o = new OutofPlane();
		o.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(o);
	}
}