package javagulp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
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
	String appName = "GulpUi";
	String cgihome;
	private URLConnection conn;
	public CgiCommunicate(String cgihome){
		this.cgihome = cgihome;
		try {
			final URL url = new URL(cgihome);
			conn = url.openConnection();
			conn.setDoOutput(true);
		} catch (final MalformedURLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		} catch (final Exception e) {
			JOptionPane.showMessageDialog(null, appName+" is unable to connect to the vnf database.");
		}
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

	public String postAndGetString(){
		String response = "";
		try {
			OutputStreamWriter wr;

			wr = new OutputStreamWriter(conn.getOutputStream());

			wr.write(data);
			wr.flush();
			wr.close();

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
		return response;
	}

	public JSONObject postAndGetJSONObject(){
		StringBuffer response = new StringBuffer();
		try {
			OutputStreamWriter wr;

			wr = new OutputStreamWriter(conn.getOutputStream());

			wr.write(data);
			wr.flush();
			wr.close();

			// Get the response

			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);//+"\n";
			}
			rd.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		//since this is a python list (of one item), remove the outer brackets
		response = response.deleteCharAt(response.length()-1);
		response = response.deleteCharAt(0);
		JSONObject obj = null;
		try {
			obj = new JSONObject(response.toString());
		} catch (final JSONException e) {
			JOptionPane.showMessageDialog(null, formatQuery(response.toString()));
			e.printStackTrace();
		}
		return obj;
	}

	public JSONArray postAndGetJSONArray(){
		final StringBuffer response = new StringBuffer();
		try {
			OutputStreamWriter wr;

			wr = new OutputStreamWriter(conn.getOutputStream());

			wr.write(data);
			wr.flush();
			wr.close();

			// Get the response

			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

	public static void main(String[] args){
		//					URL                 url = null;
		//					URLConnection   urlConn = null;
		//					DataOutputStream    printout;
		//					DataInputStream     input;
		//					// URL of CGI-Bin script.
		//
		//					try {
		//						url = new URL ("http://trueblue.caltech.edu/cgi-bin/vnf/main.cgi");
		//					} catch (MalformedURLException e) {
		//						e.printStackTrace();
		//					}
		//
		//					// URL connection channel.
		//					try {
		//						urlConn = url.openConnection();
		//					} catch (IOException e) {
		//						e.printStackTrace();
		//					}
		//					// Let the run-time system (RTS) know that we want input.
		//					urlConn.setDoInput (true);
		//					// Let the RTS know that we want to do output.
		//					urlConn.setDoOutput (true);
		//					// No caching, we want the real thing.
		//					urlConn.setUseCaches (false);
		//					// Specify the content type.
		//					urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//					// Send POST output.
		//					try {
		//						printout = new DataOutputStream (urlConn.getOutputStream ());
		//					} catch (IOException e) {
		//						e.printStackTrace();
		//					}
		//					String content =
		//						"name=" + URLEncoder.encode ("Buford Early") +
		//						"&email=" + URLEncoder.encode ("buford@known-space.com");
		//					printout.writeBytes (content);
		//					printout.flush ();
		//					printout.close ();
		//					// Get response data.
		//					input = new DataInputStream (urlConn.getInputStream ());
		//					String str;
		//					while (null != ((str = input.readLine())))
		//					{
		//						System.out.println (str);
		//					}
		//					input.close ();

	}

}
