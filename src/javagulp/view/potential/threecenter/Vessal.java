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

public class Vessal extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 474151611420105906L;

	private final G g = new G();

	private final PPP lblk = new PPP(g.html("k (eV rad<sup>-2</sup>)"));
	private final PPP lblTheta0 = new PPP(g.html(g.theta + "<sub>0</sub> (deg)"));
	private final PPP lblrho1 = new PPP(g.html(g.rho + "<sub>1</sub> (" + g.ang + ")"));
	private final PPP lblrho2 = new PPP(g.html(g.rho + "<sub>2</sub> (" + g.ang + ")"));

	private final String b = "B = (" + g.theta + "<sub>0</sub> - " + g.pi
	+ ")<sup>2</sup> - (" + g.theta + " - " + g.pi + ")<sup>2</sup>";
	private final JLabel lblThreeBodyEq = new JLabel(g.html("E = 1/4*A*B<sup>2</sup>" + " exp( -r<sub>12</sub>/"
			+ g.rho + "<sub>1</sub>) exp( -r<sub>13</sub>/" + g.rho
			+ "<sub>2</sub>)<br>A = k/(2*(" + g.theta + "<sub>0</sub> - " + g.pi + ")<sup>2</sup>) " + b));
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));

	public Vessal() {
		super(3);
		setTitle("vessal");

		lblThreeBodyEq.setOpaque(false);
		lblThreeBodyEq.setBounds(9, 15, 381, 55);
		add(lblThreeBodyEq);
		lblk.setBounds(10, 70, 225, 25);
		add(lblk);
		lblTheta0.setBounds(10, 95, 225, 25);
		add(lblTheta0);
		lblrho1.setBounds(10, 120, 225, 25);
		add(lblrho1);
		lblrho2.setBounds(10, 145, 225, 25);
		add(lblrho2);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		radii = new Radii(false, new String[] {"12", "13", "23"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[] { lblk, lblTheta0, lblrho1, lblrho2 };
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "three vessal " + pot.threeAtomBondingOptions.getAll()
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
		final Vessal v = new Vessal();
		return super.clone(v);
	}
}
