package javagulp.view.potential;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PotentialLibs {
	
	public String[] getPotentials(){
//		URL potentialsDir = this.getClass().getResource("libs");
//		//File dir = new File("potentials");
//		//alternate approach is to grab it from a jar like so:
//		//URL potentialsDir = getClass().getResource("/potentials");
//		URI pUri = null;
//		try {
//			pUri = potentialsDir.toURI();
//		} catch (URISyntaxException e1) {
//			e1.printStackTrace();
//		}
////	    String p = pUri.getPath();
////	    String rp = pUri.getRawPath();
//		String[] potentials = new File(pUri).list();
		String[] potentials = new String[]{"bush.lib","dreiding.lib","lewis.lib","vashishta.lib",
				"carbonate.lib","dreiding_ms.lib","streitzmintmire.lib",
				"catlow.lib","finnissinclair.lib","suttonchen.lib",
				"clerirosato.lib","garofalini.lib","tersoff.lib"};
		return potentials;
	}
	
	public String getFileContents(String potentialName) {
		URL potentialsDir = this.getClass().getResource("libs/"+potentialName);
		URI pUri = null;
		try {
			pUri = potentialsDir.toURI();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		File potential = new File(pUri);
		//File aFile = new File("libs/"+potential);
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(potential));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }

}
