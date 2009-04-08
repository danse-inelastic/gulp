package javagulp.model;

import java.util.ArrayList;

import org.json.JSONArray;

public class Material {
	
	public Object[] latticeVec;
	public Object[] fractionalCoordinatesVec;
	public double[][] lattice = new double[][]{{1.0, 0.0, 0.0},{0.0, 1.0, 0.0},{0.0, 0.0, 1.0}};
	public double[][] fractionalCoordinates;
	public Object[] atomSymbols;
	
//	public Material(){
//	}

//	public static void main(String[] args) {
//		Material mat = new Material();
//
//		for (double[] vec : mat.lattice) {
//			System.out.print(vec[0]+" ");
//			System.out.print(vec[1]+" ");
//			System.out.print(vec[2]+" ");
//			System.out.println();
//		}
//	}
	
}
