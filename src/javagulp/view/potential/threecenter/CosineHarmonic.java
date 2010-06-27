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

import javax.swing.JLabel;

public class CosineHarmonic extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 5592680936519533158L;

	private final G g = new G();

	private final PPP k = new PPP(g.html("k (eV rad<sup>-2</sup>)"));
	private final PPP theta = new PPP(g.html(g.theta + "<sub>0</sub> (deg)"));

	private final JLabel lblThreeBodyEq = new JLabel(g.html("E = k(cos " + g.theta
			+ " - cos " + g.theta + "<sub>0</sub>)<sup>2</sup>"));
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));

	public CosineHarmonic() {
		super(3);
		setTitle("cosine harmonic");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 18, 381, 40);
		add(lblThreeBodyEq);
		k.setBounds(10, 70, 225, 25);
		add(k);
		theta.setBounds(10, 95, 225, 25);
		add(theta);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);

		params = new PPP[] {k, theta};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "three cosine " + pot.threeAtomBondingOptions.getAll()
		+ Back.newLine + getAtoms() + Back.concatFields(params);
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
		final CosineHarmonic c = new CosineHarmonic();
		return super.clone(c);
	}
}