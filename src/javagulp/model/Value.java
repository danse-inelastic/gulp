package javagulp.model;

import java.io.Serializable;

public class Value implements Comparable<Value>, Serializable, Cloneable {
	private static final long serialVersionUID = 7842436751267328655L;
	
	public double x;
	public double[] y;
	
	public Value() {
		y = new double[1];
		//store one value by default
	}
	
	public Value(int length) {
		y = new double[length];
	}
	
	public int compareTo(Value o) {
		return new Double(x).compareTo(new Double(o.x));
	}

	@Override
	public Value clone() {
		Value v = new Value(this.y.length);
		v.x = this.x;
		for (int i=0; i < this.y.length; i++)
			v.y[i] = this.y[i];
		return v;
	}
}
