package javagulp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javagulp.model.Material;
import javagulp.view.Back;

public class LaunchFromCgi {

	private static Back b = null;
	
	public LaunchFromCgi(String id) {
		Material mat = getMaterial(id);
		if (b == null)
			b = new Back();
		else
			b.addTab();
		Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(mat);
		Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(mat);
	}
	
	Material getMaterial(String id){
		String query = "SELECT * FROM polycrystals WHERE id = '"+id+"' UNION "+
		"SELECT * FROM singlecrystals WHERE id = '"+id+"' UNION "+
		"SELECT * FROM disordered WHERE id = '"+id+"'";
		//Array latticeArray = null;
		double[] latticeVec = null;
		//Array fractionalCoordinatesArray = null;
		double[] fractionalCoordinatesVec = null;
		String[] atomSymbols = null;
		// get the material parameters from the db (eventually use ORM tool)
		try {
			Properties props = new Properties();
	//		props.setProperty("user","linjiao");
//			props.setProperty("password","4OdACm#");
//			String url = "jdbc:postgresql://localhost:54321/vnf";
			props.setProperty("user","vnf");
			props.setProperty("password","A4*gl8D");
			String url = "jdbc:postgresql://vnf.caltech.edu:5432/vnf";
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url,props);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			///latticeArray = rs.getArray("cartesian_lattice");
			latticeVec = (double[])rs.getArray("cartesian_lattice").getArray();
			fractionalCoordinatesVec = (double[])rs.getArray("fractional_coordinates").getArray();
			atomSymbols = (String[])rs.getArray("atom_symbols").getArray();
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		Material mat = new Material();
		mat.latticeVec = latticeVec;
		mat.fractionalCoordinatesVec = fractionalCoordinatesVec;
		mat.atomSymbols = atomSymbols;
		return mat;
	}

	public String getURLContentAsString(String urlString) {
		String content = "";
	    try {
	        // Create a URL for the desired page
	        URL url = new URL(urlString);
	        // Read all the text returned by the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String str;
	        while ((str = in.readLine()) != null) {
	            // str is one line of text; readLine() strips the newline character(s)
	        	content+=str;
	        	content+=System.getProperty("line.separator");
	        }
	        in.close();
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    }
		return content;
	}

	public static void main(String[] args){//String id) {
		//System.out.print(args[0]);
		//if (args.length==1){
		
		//try simple iron as 9TAL9D
		
		if (args.length==0){
			if (b == null)
				b = new Back();
			else
				b.addTab();
		} else{
			new LaunchFromCgi(args[0]);			
		}
	}

}