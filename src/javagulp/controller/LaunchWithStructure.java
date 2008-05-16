package javagulp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Pattern;


import javagulp.controller.JavaGULP;

public class LaunchWithStructure {

	public LaunchWithStructure(){
		// assume the lattice and the atoms are
		// located in "lattice.txt" and "atoms.txt"
		String curDir = System.getProperty("user.dir");
		String[] args = new String[10];
		args[0]=curDir+File.separatorChar+"atoms.txt";
		try {
			args=putLatticeInArgs(curDir+File.separatorChar+"lattice.txt",args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JavaGULP.main(args);
	}
	
	
	public String[] putLatticeInArgs(String fileName, String[] args) throws IOException{
		File file = new File(fileName);
//		URL url = null;
//		try {
//			url = new URL(fileName);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		FileReader fis = new FileReader(file);
		BufferedReader input = new BufferedReader(fis);
//		DataInputStream input = new DataInputStream(bis);
//		input = new BufferedReader(new InputStreamReader(url
//					.openConnection().getInputStream()));
		String line = null;
		line = input.readLine();
        Pattern p = Pattern.compile("\\s");
        String[] coords = p.split(line);
		args[1]=coords[0];
		args[2]=coords[1];
		args[3]=coords[2];
		line = input.readLine();
		coords = p.split(line);		
		args[4]=coords[0];
		args[5]=coords[1];
		args[6]=coords[2];
		line = input.readLine();
		coords = p.split(line);		
		args[7]=coords[0];
		args[8]=coords[1];
		args[9]=coords[2];
		input.close();
		return args;
	}

	public static void main(String[] args) {
		new LaunchWithStructure();
	}
	

}