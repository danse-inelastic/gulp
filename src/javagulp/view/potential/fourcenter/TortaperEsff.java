package javagulp.view.potential.fourcenter;

import java.awt.Color;
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
import javax.swing.JTextField;

import javagulp.model.G;

public class TortaperEsff extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8793421483932344282L;

	private G g = new G();
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private JTextField txtN = new JTextField();
	private JTextField txtRTaper = new JTextField();

	private PPP K1 = new PPP(g.html("k<sub>1</sub> (eV)"));
	private PPP K2 = new PPP("<html>k<sub>2</sub> (eV)</html>");
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblRTaper = new JLabel("<html>r<sub>taper</sub> (" + g.ang + ")</html>");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblN = new JLabel(g.html("n"));
	private JLabel lblFourBodyEq = new JLabel(g.html("E = [k<sub>1</sub> sin<sup>2</sup>"
		+ g.theta + "<sub>123</sub> sin<sup>2</sup>" + g.theta
		+ "<sub>234</sub> + isign k<sub>2</sub> sin<sup>n</sup>"
		+ g.theta + "<sub>123</sub> sin<sup>n</sup>"
		+ g.theta + "<sub>234</sub> cos(n " + g.phi
		+ ")] f(r<sub>12</sub>) f(r<sub>23</sub>) f(r<sub>34</sub>)<br>"
		+ "where f(r) = cosine tapering function"));

	public TortaperEsff() {
		super(4);
		setTitle("torsional potential with tapered cutoffs");

		lblFourBodyEq.setOpaque(false);
		lblFourBodyEq.setBackground(Color.white);
		lblFourBodyEq.setBounds(15, 24, 549, 58);
		add(lblFourBodyEq);
		lblN.setBounds(15, 151, 35, 15);
		add(lblN);
		txtN.setBounds(96, 149, 70, 20);
		add(txtN);
		K1.setBounds(10, 95, 225, 25);
		add(K1);
		K2.setBounds(10, 120, 225, 25);
		add(K2);
		lblImage.setBounds(457, 95, 190, 75);
		add(lblImage);
		lblRTaper.setBounds(14, 175, 56, 20);
		add(lblRTaper);
		lblUnits.setBounds(14, 200, 40, 21);
		add(lblUnits);
		txtRTaper.setBounds(96, 175, 70, 20);
		add(txtRTaper);
		cboUnits.setBounds(96, 200, 70, 21);
		add(cboUnits);
		radii = new Radii(new String[] {"12", "23", "34", "41"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{K1, K2};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		JTextField[] fields = { txtN, txtRTaper};
		String[] descriptions = { "n", "rtaper"};
		Potential pot = Back.getPanel().getPotential();
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);
		
		String lines = "tortaper " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += "esff " + Back.newLine	+ pot.getAtomCombos() + Back.concatFields(params) + " "
				+ Back.concatFields(fields) + " " + radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		return new TortaperEsff();
	}
}