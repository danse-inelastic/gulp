package javagulp.view.potential.libs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.CgiCommunicate;
import javagulp.view.Back;

import org.json.JSONArray;

public class PotentialLibs {

	public final String[] potentials = new String[]{"none","bush.lib","dreiding.lib","lewis.lib","vashishta.lib",
			"carbonate.lib","dreiding_ms.lib","streitzmintmire.lib",
			"catlow.lib","finnissinclair.lib","suttonchen.lib",
			"clerirosato.lib","garofalini.lib","tersoff.lib"};

	public Map<String,String> potentialContents = new HashMap<String,String>();

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

	public String getFileContents(String potentialName) throws SocketTimeoutException {
		//first see if the library is in the local cache
		//libraryContents = new PotentialLibs().getFileContents(librarySelected);
		if (potentialName=="none"){
			return "";
		} else {
			//if the ui already has the potential contents loaded, display them
			if(potentialContents.containsKey(potentialName)){
				return potentialContents.get(potentialName);
			}else{
				//if not, see if they're prepackaged in the jar
				try{
//					final InputStream potentialStream = this.getClass().getResourceAsStream(potentialName+".lib");
//					return convertStreamToString(potentialStream);
					return getJarredFileContents(potentialName);
					//if not on disk, get them from the db
				}catch(final Exception e){
					Back.getCurrentRun().getPotential().libraryDisplay.setText("Downloading library from server...please wait...");
					return getPotentialContentsFromDb(potentialName);
				}
			}
		}
	}
	
	public String getJarredFileContents(String potentialName){
		final InputStream potentialStream = this.getClass().getResourceAsStream(potentialName+".lib");
		return convertStreamToString(potentialStream);
	}

	private String getPotentialContentsFromDb(String potentialName) throws SocketTimeoutException {
		final Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		final String cgihome = cgiMap.get("cgihome");
		final CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		final Map<String, String> getPotentialContentsQuery = new HashMap<String, String>();
		getPotentialContentsQuery.put("actor", "dbObjToWeb");
		getPotentialContentsQuery.put("routine", "getAssociatedData");
		getPotentialContentsQuery.put("dbObjToWeb.tables", "gulppotential");
		//getPotentialContentsQuery.put("dbObjToWeb.columns", "id");
		getPotentialContentsQuery.put("dbObjToWeb.creator", "everyone");
		getPotentialContentsQuery.put("dbObjToWeb.where", "id='"+potentialName+"'");
		//getPotentialContentsQuery.put("dbObjToWeb.filename_variable", "id='"+potentialName+"'");
		//Back.getCurrentRun().putInAuthenticationInfo(getPotentialContentsQuery);
		cgiCom.setCgiParams(getPotentialContentsQuery);
		final JSONArray potentialNamesAsJSONArray = cgiCom.postAndGetJSONArray();
		final Object[] potentialContentAsArray = potentialNamesAsJSONArray.getArrayList();
		return (String)potentialContentAsArray[0];
	}

	public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will be appended to a StringBuilder
		 * and returned as String.
		 */
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (final IOException e) {
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
