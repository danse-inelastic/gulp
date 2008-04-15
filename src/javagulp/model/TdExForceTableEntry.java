package javagulp.model;

import java.io.Serializable;

public class TdExForceTableEntry implements Serializable {

	private static final long serialVersionUID = -5974409441024059922L;

	protected String atom, atomLocation, forceDirection, A, B, C;

	protected int atomindex;

	public TdExForceTableEntry(final String atom, final String atomLocation,
			final int atomindex, final String forceDirection, final String A,
			final String B, final String C) {
		this.atom = atom;
		this.atomLocation = atomLocation;
		this.atomindex = atomindex;
		this.forceDirection = forceDirection;
		this.A = A;
		this.B = B;
		this.C = C;
	}

	@Override
	public String toString() {
		return this.atom + " " + this.atomLocation + " " + this.forceDirection
				+ " " + this.A + " " + this.B + " " + this.C;
	}

}