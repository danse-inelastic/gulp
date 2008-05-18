package javagulp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import com.sshtools.j2ssh.util.Base64.InputStream;

public class LaunchWithStructure {

	public LaunchWithStructure() {
		// assume the lattice and the atoms are
		// located in "lattice.txt" and "atoms.txt"
		String base = "http://trueblue.caltech.edu/java";//System.getProperty("user.dir");
		String[] args = new String[10];
		String atomsContent = getURLContentAsString(base + File.separatorChar
				+ "atoms.html");
		String latticeContent = getURLContentAsString(base + File.separatorChar
				+ "lattice.html");
		//put atoms in args
		args[0] = atomsContent;
		//put lattice in args
		Pattern p = Pattern.compile("\n");
		String[] coordLines = p.split(latticeContent);
		p = Pattern.compile("\\s");
		String[] coords = p.split(coordLines[0]);
		args[1] = coords[0];
		args[2] = coords[1];
		args[3] = coords[2];
		coords = p.split(coordLines[1]);
		args[4] = coords[0];
		args[5] = coords[1];
		args[6] = coords[2];
		coords = p.split(coordLines[2]);
		args[7] = coords[0];
		args[8] = coords[1];
		args[9] = coords[2];

		JavaGULP.main(args);
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

	public static void main(String[] args) {
		new LaunchWithStructure();
	}

}