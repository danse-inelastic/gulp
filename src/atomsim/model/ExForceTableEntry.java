package javagulp.model;

public class ExForceTableEntry {

	protected String atom, atomLocation, fx, fy, fz;

	protected int atomindex;

	public ExForceTableEntry(final String atom, final String atomLocation,
			final int atomindex, final String fx, final String fy,
			final String fz) {
		this.atom = atom;
		this.atomLocation = atomLocation;
		this.atomindex = atomindex;
		this.fx = fx;
		this.fy = fy;
		this.fz = fz;
	}

	@Override
	public String toString() {
		return this.atom + " " + this.atomLocation + " " + this.fx + " "
		+ this.fy + " " + this.fz;
	}

}
