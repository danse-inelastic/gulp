package javagulp.view.potential.fourcenter;

import java.awt.Color;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TortaperEsff extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8793421483932344282L;

	private final G g = new G();

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private final JTextField txtN = new JTextField();
	private final JTextField txtRTaper = new JTextField();

	private final PPP K1 = new PPP(g.html("k<sub>1</sub> (eV)"));
	private final PPP K2 = new PPP("<html>k<sub>2</sub> (eV)</html>");

	private final JLabel lblUnits = new JLabel("units");
	private final JLabel lblRTaper = new JLabel("<html>r<sub>taper</sub> (" + g.ang + ")</html>");
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private final JLabel lblN = new JLabel(g.html("n"));
	private final JLabel lblFourBodyEq = new JLabel(g.html("E = [k<sub>1</sub> sin<sup>2</sup>"
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
		final JTextField[] fields = { txtN, txtRTaper};
		final String[] descriptions = { "n", "rtaper"};
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		String lines = "tortaper " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += "esff " + Back.newLine	+ getAtoms() + Back.concatFields(params) + " "
		+ Back.concatFields(fields) + " " + radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}

	@Override
	public PotentialPanel clone() {
		return new TortaperEsff();
	}
}