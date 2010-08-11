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
	
	public Atom(String[] atomInfo){
		symbol = atomInfo[0];
		p[0] = Double.valueOf(atomInfo[1]).doubleValue();
		p[1] = Double.valueOf(atomInfo[2]).doubleValue();
		p[2] = Double.valueOf(atomInfo[3]).doubleValue();
		if (atomInfo.length==5)
			charge=Double.valueOf(atomInfo[4]).doubleValue();
	}
	

}
