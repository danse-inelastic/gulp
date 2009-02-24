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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javagulp.model.Material;
import javagulp.view.Back;

public class LaunchGulpUi {

	private static Back b = null;
	
	
	public LaunchGulpUi(String[] simulationParams) {

		if (b == null)
			b = new Back(simulationParams);
		else
			b.addTab(simulationParams);

	}
	

	public static void main(String[] args){
		//try simple iron as 9TAL9D

			new LaunchGulpUi(args);			
	}

}