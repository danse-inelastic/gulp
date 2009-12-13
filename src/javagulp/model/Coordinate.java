package javagulp.model;

import java.io.Serializable;

public class Coordinate implements Serializable {
	private static final long serialVersionUID = -6437627881112344889L;

	public double x, y, z;

	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double[] toArray() {
		return new double[]{x, y, z};
	}

	public void makeUnitLength() {
		final double magnitude = magnitude();
		if (magnitude != 0) {
			x /= magnitude;
			y /= magnitude;
			z /= magnitude;
		}
	}

	public void scalarMultiply(double d) {
		x *= d;
		y *= d;
		z *= d;
	}

	public void add(Coordinate c) {
		x += c.x;
		y += c.y;
		z += c.z;
	}

	public void set(Coordinate c) {
		x = c.x;
		y = c.y;
		z = c.z;
	}

	public double magnitude() {
		return Math.sqrt(x*x+y*y+z*z);
	}

	public double magnitudeSquared() {
		return x*x+y*y+z*z;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

	public double angle(Coordinate c) {
		return Math.acos((this.dotProduct(c))/(this.magnitude()*c.magnitude()));
	}

	public double dotProduct(Coordinate c) {
		return x*c.x + y*c.y + z*c.z;
	}

	@Override
	public boolean equals(Object obj) {
		final Coordinate c = (Coordinate) obj;
		return this.x == c.x && this.y == c.y && this.z == c.z;
	}
}