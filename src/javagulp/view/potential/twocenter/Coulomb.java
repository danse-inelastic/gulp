package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JLabel;

public class Coulomb extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -6193872858085266829L;

	private final JLabel lblEquation = new JLabel(
			"<html>E = -scale * q<sub>i</sub>q<sub>j</sub> / r<sub>ij</sub></html>");

	private final PPP scale = new PPP("scale");

	public Coulomb() {
		super(2);
		setTitle("coulomb");
		enabled = new boolean[] { true, true, true, true, true, true };

		add(lblEquation);
		lblEquation.setBounds(10, 20, 147, 21);
		add(scale);
		scale.setBounds(10, 55, 225, 21);
		scale.txt.setText("1.0");
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{scale};
	}

	@Override
	public PotentialPanel clone() {
		final Coulomb c = new Coulomb();
		return super.clone(c);
	}

	@Override
	public String writePotential() throws IncompleteOptionException,InvalidOptionException {
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "coulomb " + pot.twoAtomBondingOptions.getAll() + Back.newLine + pot.getAtomCombos();
		if (!scale.txt.getText().equals("") && !scale.txt.getText().equals("1.0"))
			lines += " " + scale.txt.getText();
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
}
