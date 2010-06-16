package javagulp.model;

import java.lang.String;

public class Atom {
	
	public String symbol = "H";
	public double[] p = new double[]{0.0, 0.0, 0.0};
	double charge= 0.0;

	public Atom(String symbol0, double[] p0, double charge0){
		p = p0;
		charge=charge0;
		symbol=symbol0;
	}
	

}
