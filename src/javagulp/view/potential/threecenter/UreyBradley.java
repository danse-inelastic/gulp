package javagulp.view.potential.threecenter;

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

public class UreyBradley extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 1491576400219135159L;

	private final G g = new G();

	private final PPP k = new PPP(g.html("k (eV/" + g.ang + "<sup>2</sup>)"));
	private final PPP r0 = new PPP(g.html("r<sub>0</sub> (" + g.ang + ")"));

	private final JLabel lblEq = new JLabel(g.html("E = 1/2 k(r<sub>23</sub> - r<sub>23</sub><sup>0</sup>)<sup>2</sup>"));
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	private final JLabel lblUnits = new JLabel("units");

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public UreyBradley() {
		super(3);
		setTitle("urey-bradley");

		lblEq.setBounds(10, 18, 384, 40);
		add(lblEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		r0.setBounds(10, 95, 225, 25);
		add(r0);
		lblUnits.setBounds(10, 120, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(111, 120, 70, 21);
		add(cboUnits);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);

		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{k, r0};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "urey-bradley " + pot.threeAtomBondingOptions.getAll();
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
		final UreyBradley u = new UreyBradley();
		u.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(u);
	}
}
