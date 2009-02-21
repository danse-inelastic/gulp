package javagulp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


class CgiCommunicate {

	public static void main(String[] args){//String id) {
		try {
	        // Construct data
	        //String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
	        //data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
	        String data = "";
	    
	        // Send data
	        URL url = new URL("http://trueblue.caltech.edu/cgi-bin/vnf/main.cgi");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        wr.close();
	    
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	        	System.out.println (line);// Process line...
	        }
	        
	        rd.close();
			
//			URL                 url;
//			URLConnection   urlConn;
//			DataOutputStream    printout;
//			DataInputStream     input;
//			// URL of CGI-Bin script.
//
//			url = new URL ("http://trueblue.caltech.edu/cgi-bin/vnf/main.cgi");
//
//			// URL connection channel.
//			urlConn = url.openConnection();
//			// Let the run-time system (RTS) know that we want input.
//			urlConn.setDoInput (true);
//			// Let the RTS know that we want to do output.
//			urlConn.setDoOutput (true);
//			// No caching, we want the real thing.
//			urlConn.setUseCaches (false);
//			// Specify the content type.
//			urlConn.setRequestProperty
//			("Content-Type", "application/x-www-form-urlencoded");
//			// Send POST output.
//			printout = new DataOutputStream (urlConn.getOutputStream ());
//			String content =
//				"name=" + URLEncoder.encode ("Buford Early") +
//				"&email=" + URLEncoder.encode ("buford@known-space.com");
//			printout.writeBytes (content);
//			printout.flush ();
//			printout.close ();
//			// Get response data.
//			input = new DataInputStream (urlConn.getInputStream ());
//			String str;
//			while (null != ((str = input.readLine())))
//			{
//				System.out.println (str);
//			}
//			input.close ();
//
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
