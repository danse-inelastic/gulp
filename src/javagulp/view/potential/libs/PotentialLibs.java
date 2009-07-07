package javagulp.view.potential.libs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.CgiCommunicate;
import javagulp.view.Back;

import org.json.JSONArray;

public class PotentialLibs {
	
	private String[] potentials = new String[]{"none","bush.lib","dreiding.lib","lewis.lib","vashishta.lib",
			"carbonate.lib","dreiding_ms.lib","streitzmintmire.lib",
			"catlow.lib","finnissinclair.lib","suttonchen.lib",
			"clerirosato.lib","garofalini.lib","tersoff.lib"};
	
	private Map<String,String> potentialContents = new HashMap<String,String>();
	
	public PotentialLibs(){
		//TODO: eventually cache potentials in .gulpUi or something like that
		//for now just instantiate each time from scratch
		//potentialContents
	}

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
		return potentials;
	}

	public String getFileContents(String potentialName) {
		//first see if the library is in the local cache
		//libraryContents = new PotentialLibs().getFileContents(librarySelected);
		if (potentialName=="none"){
			return "";			
		} else {
			//if the ui already has the potential contents loaded, display them
			if(potentialContents.containsKey(potentialName)){
				return potentialContents.get(potentialName);
			}else{
				//if not, see if they're "on disk"
				try{
					InputStream potentialStream = this.getClass().getResourceAsStream(potentialName);
					return convertStreamToString(potentialStream);
				//if not on disk, get them from the db
				}catch(Exception e){
					return getPotentialContentsFromDb(potentialName);
				}
			}
		}
	}

	private String getPotentialContentsFromDb(String potentialName) {
		Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		String cgihome = cgiMap.get("cgihome");
		CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		Map<String, String> getPotentialContentsQuery = new HashMap<String, String>();
		getPotentialContentsQuery.put("actor", "directdb");
		getPotentialContentsQuery.put("routine", "getAssociatedData");
		getPotentialContentsQuery.put("directdb.tables", "gulppotential");
		//getPotentialContentsQuery.put("directdb.columns", "potential_name");
		getPotentialContentsQuery.put("directdb.creator", "everyone");
		getPotentialContentsQuery.put("directdb.where", "potential_name='"+potentialName+"'");
		//getPotentialContentsQuery.putAll(cgiMap);
		cgiCom.setCgiParams(getPotentialContentsQuery);
		JSONArray potentialNamesAsJSONArray = cgiCom.postAndGetJSONArray();	
		Object[] potentialContentAsArray = potentialNamesAsJSONArray.getArrayList();
		return (String)potentialContentAsArray[0];
	}

	public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will be appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}


//	public String getFileContents2(String potentialName) {
//		URL potentialsDir = this.getClass().getResource(potentialName);
//		URI pUri = null;
//		try {
//			pUri = potentialsDir.toURI();
//		} catch (URISyntaxException e1) {
//			e1.printStackTrace();
//		}
//		File potential = new File(pUri);
//		//File aFile = new File("libs/"+potential);
//		StringBuilder contents = new StringBuilder();
//
//		try {
//			//use buffering, reading one line at a time
//			//FileReader always assumes default encoding is OK!
//			BufferedReader input =  new BufferedReader(new FileReader(potential));
//			try {
//				String line = null; //not declared within while loop
//				/*
//				 * readLine is a bit quirky :
//				 * it returns the content of a line MINUS the newline.
//				 * it returns null only for the END of the stream.
//				 * it returns an empty String if two newlines appear in a row.
//				 */
//				while (( line = input.readLine()) != null){
//					contents.append(line);
//					contents.append(System.getProperty("line.separator"));
//				}
//			}
//			finally {
//				input.close();
//			}
//		}
//		catch (IOException ex){
//			ex.printStackTrace();
//		}
//
//		return contents.toString();
//	}

}
