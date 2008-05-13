package javagulp.controller;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javagulp.controller.JavaGULP;

public class PlainWebStartLaunch {

	public PlainWebStartLaunch(){
		// first try to get the sample structure from the current experiment
		String db = "vnf";
		String url = "jdbc:postgresql://trueblue.caltech.edu:5432/" + db;
		try{
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();
			String txtQuery = "";
			ResultSet rs = stmt.executeQuery(txtQuery);	
		} catch (Exception e1) {
		// throw an exception if the current experiment does not have a sample
			JOptionPane.showMessageDialog(null, "Your vnf experiment does not have a sample yet.  Please add one before continuing.");
		}	
			ArrayList<ArrayList<String>> species = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<double[]>> coordinates = new ArrayList<ArrayList<double[]>>();
			ArrayList<String> names = new ArrayList<String>();
			
			for (int i=0; i < indices.length; i++) {
				try {
					//download file
					fileName = "http://fireball.phys.wvu.edu/cod/" + db + "/" + fileNumbers[indices[i]] + ".cif";
					StringBuffer contents=Main.getFileContents(fileName);
					names.add(fileNumbers[indices[i]]);
					//System.out.println(fileName);
					
					//parse it and generate atoms
					CifParser cp = new CifParser(contents.toString());
					cp.parseCoordinates("_atom_site_fract_x");
					cp.parseOperators();
					cp.removeDuplicates();
					
					//add parameters
					String[] params = new String[7];
					for (int j=1; j < params.length; j++)
						params[j] = "" + tblResult.getValueAt(indices[i], j-1);
					args.add(params);
					
					if (cp.newSpecies.size() == 0)
						species.add(cp.species);
					else
						species.add(cp.newSpecies);
					if (cp.newCoordinates.size() == 0)
						coordinates.add(cp.coordinates);
					else
						coordinates.add(cp.newCoordinates);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, db + " does not contain a cif file for this entry (" + fileName+").  Please try another.");
					e1.printStackTrace();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, db + "Error encountered while processing " + fileName);
					e2.printStackTrace();
				}
			}
			JavaGULP.main(species, coordinates, args, names);
}
	
	public static void main(String[] args) {
		new PlainWebStartLaunch();
	}

}