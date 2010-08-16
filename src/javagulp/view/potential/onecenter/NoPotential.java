package javagulp.view.potential.onecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.potential.PotentialPanel;

public class NoPotential extends PotentialPanel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970310028185663795L;

	public NoPotential() {
		super(1);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		final String lines = "";
		return lines;
	}

	@Override
	public PotentialPanel clone() {
		return new NoPotential();
	}
}