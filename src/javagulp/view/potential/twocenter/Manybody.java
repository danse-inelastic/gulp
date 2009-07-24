package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

public class Manybody extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -6482150694120852003L;

	public Manybody() {
		super(2);
		setTitle("manybody");

		radii = new Radii(true);
		radii.setBounds(7, 55, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		return radii.writeRadii() + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		return super.clone(new Manybody());
	}
}