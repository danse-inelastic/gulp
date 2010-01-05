package javagulp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class CgiCommunicate {

	String data = "";
	String appName = "AtomSim";
	String cgihome;
	private URLConnection conn;
	public CgiCommunicate(String cgihome){
		this.cgihome = cgihome;
		int timeout = 0;
		try {
			final URL url = new URL(cgihome);
			conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(3);
			timeout = conn.getConnectTimeout();
		} catch (final MalformedURLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		}
		//System.out.print(timeout);
	}

	public void setCgiParams(Map<String,String> cgiMap){

		for (final String key : cgiMap.keySet()) {
			final String val = cgiMap.get(key);
			String pair = null;
			try {
				pair = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
			} catch (final Exception e) {
				System.out.println("Had trouble encoding the following key value pair: ("+ key+", "+val+").\n");
				e.printStackTrace();
			}
			if(data==""){
				data = pair;
			}else{
				data += "&" + pair;
			}
		}
	}

	public JSONObject postAndGetJSONObject(){
		//NOTE: A string cannot be used for the response because the quotation escape
		//characters get replaced by two backslashes (i.e. \" goes to \\")
		//StringBuffer response = new StringBuffer();
		//String response = "";
		StringBuilder response = new StringBuilder();
		try {
			OutputStreamWriter wr;
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();

//			response = (String) conn.getContent();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				//response = response + line;//+"\n";
			}
			rd.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		JSONObject obj = null;
		try {
			//This next part converts a json string into proper json form...this is necessary
			//because we used a json string rather than a json stream.
			String newString = response.toString().replaceAll("\"", "");
			String fixedResponse = newString.replaceAll("\\\\", "\"");
			
			//response = response.deleteCharAt(response.length()-1);
			//response = response.deleteCharAt(0);
			
			//String convertedResponse = response.toString();
			//String convertedResponse = new String(response);
			
//			String convertedResponse = response.toString();
//			String fixedResponse = fixEscapeCharProblem(convertedResponse);
			//System.out.println(convertedResponse);
			obj = new JSONObject(fixedResponse);//response was StringBuilder so have to convert
			//obj = new JSONObject(response);
		} catch (final JSONException e) {
			JOptionPane.showMessageDialog(null, formatQuery(response.toString()));
			e.printStackTrace();
		}
		return obj;
	}

	public JSONArray postAndGetJSONArray() throws SocketTimeoutException{
		final StringBuffer response = new StringBuffer();
		try {
			OutputStreamWriter wr;
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line+"\n");
			}
			rd.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		//		//since this is a python list (of one item), remove the outer brackets
		//		response = response.deleteCharAt(response.length()-1);
		//		response = response.deleteCharAt(0);
		JSONArray obj = null;
		//System.out.println(response);
		try {
			obj = new JSONArray(response.toString());
		} catch (final JSONException e) {
			JOptionPane.showMessageDialog(null, formatQuery(response.toString()));
			e.printStackTrace();
		}
		return obj;
	}

	private String formatQuery(String response){
		System.out.print(response);
		String base = this.appName+" is unable to read the response\n" +
		//response + "\n" +
		"which it tried to retrieve from\n"+
		this.cgihome + "\n" +
		"using key, value query pairs\n";
		final String[] splitData = this.data.split("&");
		for (final String piece : splitData) {
			base += piece + "\n";
		}
		return base;
	}

	public String postAndGetString(){
		String response = "";
		try {
			OutputStreamWriter wr;
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();

//			response = (String) conn.getContent();
			// Get the response
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response+=line+"\n";
			}
			rd.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		String newString = response.toString().replaceAll("\"", "");
		return newString;
	}
	
}
