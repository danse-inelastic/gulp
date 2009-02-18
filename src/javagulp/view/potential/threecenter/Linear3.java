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

public class Linear3 extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8957084132159101672L;

	private JComboBox cboISign = new JComboBox(new String[] { "+", "-" });
	private JComboBox cboUnits = new JComboBox(new String[] { "kjmol", "kcal" });

	private G g = new G();

	private PPP k = new PPP(g.html("k (eV)"));
	private PPP n = new PPP(g.html("n"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblISign = new JLabel("isign");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = k(isign cos(n" + g.theta + ") + 1)"));

	public Linear3() {
		super(3);
		setTitle("linear three body");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 18, 381, 40);
		add(lblThreeBodyEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		n.setBounds(10, 95, 225, 25);
		add(n);
		n.chk.setVisible(false);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		cboISign.setBounds(90, 120, 85, 20);
		add(cboISign);
		cboUnits.setBounds(90, 145, 70, 21);
		add(cboUnits);
		lblISign.setBounds(10, 120, 35, 15);
		add(lblISign);
		lblUnits.setBounds(10, 145, 40, 21);
		add(lblUnits);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[] {k, n};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();

		String lines = "lin3 " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + k.txt.getText() + " ";
		if (cboISign.getSelectedIndex() == 1)
			lines += "- ";
		lines += n.txt.getText();
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
		Linear3 l = new Linear3();
		l.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		l.cboISign.setSelectedIndex(this.cboISign.getSelectedIndex());
		return super.clone(l);
	}
}