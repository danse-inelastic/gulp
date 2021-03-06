package javagulp.model;

////////////////////////////////////////////////////////
// Bare Bones Browser Launch                          //
// Version 1.1                                        //
// July 8, 2005                                       //
// Supports: Mac OS X, GNU/Linux, Unix, Windows XP    //
// Example Usage:                                     //
//    String url = "http://www.centerkey.com/";       //
//    BareBonesBrowserLaunch.openURL(url);            //
// Public Domain Software -- Free to Use as You Like  //
////////////////////////////////////////////////////////

import java.lang.reflect.Method;

import javagulp.view.Back;

import javax.swing.JOptionPane;

public class BareBonesBrowserLaunch {

	public static void openURL(String url) {
		final String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				final Class macUtils = Class.forName("com.apple.mrj.MRJFileUtils");
				final Method openURL = macUtils.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { // assume Unix or Linux
				final String[] browsers = { "firefox", "opera", "konqueror", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser.");
				else
					Runtime.getRuntime().exec(new String[] { browser, url });
			}
		} catch (final Exception e) {
			final String errMsg = "Error attempting to launch web browser";
			JOptionPane.showMessageDialog(null, errMsg + ":" + Back.newLine + e.getLocalizedMessage());
		}
	}
}