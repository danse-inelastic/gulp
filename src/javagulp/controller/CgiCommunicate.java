package javagulp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;



public class CgiCommunicate {

	String data = "";
	private URLConnection conn;
	public CgiCommunicate(){
		try {
			URL url = new URL("http://trueblue.caltech.edu/cgi-bin/vnf/main.cgi");
			conn = url.openConnection();
			conn.setDoOutput(true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

	public String post(){
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

	public static void main(String[] args){

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
	}

}
