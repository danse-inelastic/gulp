package javagulp.view.potential;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

public class NoPotential extends PotentialPanel implements Serializable {
	public NoPotential() {
		super(1);
	}
	
	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "";
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		return new NoPotential();
	}
}