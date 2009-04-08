package javagulp.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;



public class CgiCommunicate {

	String data = "";
	private URLConnection conn;
	public CgiCommunicate(String cgihome){
		try {
			URL url = new URL(cgihome);
			conn = url.openConnection();
			conn.setDoOutput(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "GulpUi is unable to connect to the vnf database.");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "GulpUi is unable to connect to the vnf database.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "GulpUi is unable to connect to the vnf database.");
		}
	}

	public void setCgiParams(Map<String,String> cgiMap){
		
		for (String key : cgiMap.keySet()) {
			String val = cgiMap.get(key);
			try {
				String pair = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
				if(data==""){
					data = pair;
				}else{
					data += "&" + pair;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
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

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response+=line+"\n";
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public JSONObject postAndGetJSON(){
		StringBuffer response = new StringBuffer();
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
				response.append(line);//+"\n";
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//since this is a python list (of one item), remove the outer brackets
		response = response.deleteCharAt(response.length()-1);
		response = response.deleteCharAt(0);
		JSONObject obj = null;
		try {
			obj = new JSONObject(response.toString());
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "GulpUi is unable to read the material selected.");
			e.printStackTrace();
		}
		return obj;
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
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		
//					// URL connection channel.
//					try {
//						urlConn = url.openConnection();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
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
//						// TODO Auto-generated catch block
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
