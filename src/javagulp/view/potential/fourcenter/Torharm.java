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

public class Torharm extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8336235161420161412L;

	private final G g = new G();

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private final JLabel lblUnits = new JLabel("units");
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private final JLabel lblFourBodyEq = new JLabel(g.html("E = 1/2 k (" + g.phi
			+ " - " + g.phi + "<sub>0</sub>)<sup>2</sup>"));

	private final PPP K = new PPP(g.html("k (eV/rad<sup>2</sup>)"));
	private final PPP Phi0 = new PPP(g.html(g.phi + "<sub>0</sub> (deg)"));

	public Torharm() {
		super(4);
		setTitle("harmonic torsional potential");

		lblFourBodyEq.setOpaque(false);
		lblFourBodyEq.setBackground(Color.white);
		lblFourBodyEq.setBounds(15, 24, 381, 33);
		add(lblFourBodyEq);
		K.setBounds(10, 70, 225, 25);
		add(K);
		Phi0.setBounds(10, 95, 225, 25);
		add(Phi0);
		lblUnits.setBounds(10, 120, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 120, 70, 21);
		add(cboUnits);
		lblImage.setBounds(460, 24, 190, 75);
		add(lblImage);
		radii = new Radii(new String[] {"12", "23", "34"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{K, Phi0};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "torharm " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += Back.newLine + getAtoms() + Back.concatFields(params) + " "
		+ radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}

	@Override
	public PotentialPanel clone() {
		final Torharm t = new Torharm();
		t.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(t);
	}
}