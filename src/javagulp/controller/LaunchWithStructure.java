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
		String[] args = new String[10];
		args=getFileContents("lattice.txt",args);
		JavaGULP.main(args);
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

	public String[] getFileContents(String fileName, String[] args){
		URL url = null;
		try {
			url = new URL(fileName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(url
					.openConnection().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line = null;
		try {
			line = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] coords = line.split(" ");
		args[1]=coords[0];
		args[2]=coords[1];
		args[3]=coords[2];
		try {
			line = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		coords = line.split(" ");		
		args[4]=coords[0];
		args[5]=coords[1];
		args[6]=coords[2];
		try {
			line = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		coords = line.split(" ");		
		args[7]=coords[0];
		args[8]=coords[1];
		args[9]=coords[2];
//		for (int i = 0; i < 3; i++) {
//			String line = input.readLine();
//			coords = line.split(" ");
//			args[0]
//		}
//		contents.append(coords);
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return args;
	}

	public static void main(String[] args) {
		new LaunchWithStructure();
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