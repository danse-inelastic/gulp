package javagulp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

import javagulp.model.Material;
import javagulp.view.Back;

import com.sshtools.j2ssh.util.Base64.InputStream;

public class LaunchFromMaterialId {

	private static Back b = null;
	
	public LaunchFromMaterialId(String id) {
		Material mat = getMaterial(id);
		if (b == null)
			b = new Back();
		else
			b.addTab();
		Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(mat);
		Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(mat);
	}
	
	Material getMaterial(String id){
		String query = "SELECT * FROM polycrystals WHERE id = "+id+" UNION "+
		"SELECT * FROM singlecrystals WHERE id = "+id+" UNION "+
		"SELECT * FROM disordered WHERE id = "+id;
		double[] latticeVec = null;
		double[] fractionalCoordinatesVec = null;
		String[] atomSymbols = null;
		// get the material parameters from the db (eventually use ORM tool)
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:54321/vnf";
			Connection con = DriverManager.getConnection(url);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			latticeVec = (double[]) rs.getObject(0);
			fractionalCoordinatesVec = (double[])rs.getObject("fractional_coordinates");
			atomSymbols = (String[])rs.getObject("atom_symbols");
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

	public static void main(String id) {
		if (id==null){
			new LaunchFromMaterialId("794VFC");
		} else{
			new LaunchFromMaterialId(id);			
		}
	}

}