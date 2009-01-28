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

public class AxilrodTeller extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8281169465229269453L;

	private G g = new G();
	
	private PPP k = new PPP(g.html("k (eV/" + g.ang + "<sup>9</sup>)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private JLabel lblThreeBodyEq = new JLabel(g.html("E = k(1 + 3 cos(" + g.theta + "<sub>123</sub>) cos(" + g.theta + "<sub>231</sub>) cos(" + g.theta
			+ "<sub>312</sub>))/(r<sub>12</sub><sup>3</sup> r<sub>13</sub><sup>3</sup> r<sub>23</sub><sup>3</sup>)"));

	public AxilrodTeller() {
		super(3);
		setTitle("axilrod-teller");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 18, 381, 40);
		add(lblThreeBodyEq);
		cboUnits.setBounds(90, 100, 70, 21);
		add(cboUnits);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 100, radii.getWidth(), radii.getHeight());
		add(radii);
		lblImage.setBounds(436, 16, 122, 80);
		add(lblImage);
		k.setBounds(10, 75, 225, 25);
		add(k);
		lblUnits.setBounds(10, 100, 40, 21);
		add(lblUnits);
		params = new PPP[] {k};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();

		String lines = "axilrod-teller "+ pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
		if (!pot.threeAtomBondingOptions.Bond())
			lines += " " + radii.writeRadii();
		return lines + Back.writeFits(params) + Back.newLine;
	}

	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
	}
	
	@Override
	public PotentialPanel clone() {
		AxilrodTeller a = new AxilrodTeller();
		a.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(a);
	}
}