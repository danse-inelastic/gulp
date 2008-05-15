package javagulp.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javagulp.controller.JavaGULP;

public class LaunchWithStructure {

	public LaunchWithStructure(){
		// assume the lattice and the atoms are
		// located in
		// three separate files
		String [] lattice=getFileContents("lattice.txt");

		String[] fileAndLattice = new String[] {};
		fileAndLattice[0]="atoms.txt";
		fileAndLattice[]

		JavaGULP.main(fileAndLattice);
	}

	// public ArrayList<double[]> getFileContents(String fileName) {
	// ArrayList<double[]> contents = new ArrayList<double[]>();
	// String newLine = System.getProperty("line.separator");
	// URL url = new URL(fileName);
	// BufferedReader input = new BufferedReader(new
	// InputStreamReader(url.openConnection().getInputStream()));
	// String line = null;
	// while ((line = input.readLine()) != null){
	// String[] coordsRaw = line.split("\w");
	// double[3] coords
	// for (int i=0;i<3;i++){
	// coords[i]=Double.valueOf(coordsRaw[i])
	// }
	// contents.append(coords);
	// contents.append(newLine);
	// }
	// input.close();
	// return contents;
	// }

	public String[] getFileContents(String fileName) {
		String[] contents = new String(){};
		URL url = new URL(fileName);
		BufferedReader input = new BufferedReader(new InputStreamReader(url
				.openConnection().getInputStream()));
		String[] coords;
		for (int i = 0; i < 3; i++) {
			String line = input.readLine();
			coords = line.split(" ");
		}
		contents.append(coords);
		input.close();
		return contents;
	}

	public static void main(String[] args) {
		new PlainWebStartLaunch(args[1]);
	}

	// String db = "vnf";
	// String url = "jdbc:postgresql://trueblue.caltech.edu:5432/" + db;
	// try{
	// Class.forName("org.postgresql.Driver");
	// Connection con = DriverManager.getConnection(url);
	// Statement stmt = con.createStatement();
	// String txtQuery = "SELECT "+sampleId+" FROM sample";
	// ResultSet rs = stmt.executeQuery(txtQuery);
	// } catch (Exception e1) {
	// // throw an exception if the current experiment does not have a sample
	// JOptionPane.showMessageDialog(null, "Your vnf experiment does not have a
	// sample yet. Please add one before continuing.");
	//	}	

}