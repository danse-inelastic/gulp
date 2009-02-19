package javagulp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.JCopy;
import javagulp.model.SerialListener;
import javagulp.view.potential.CreateLibrary;
import javax.swing.JButton;

import javax.swing.JList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Potential extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4991943378742898078L;
	private JList libraryList;
	private DefaultListModel potentialListModel = new DefaultListModel();
	private String library;
	public CreateLibrary createLibrary = new CreateLibrary();
	public JPanel useLibrary = new JPanel();
	private JTextPane libraryDisplay = new JTextPane();
	public Potential() {
		super();
		setLayout(new BorderLayout());

		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);

		useLibrary.setLayout(new BorderLayout());
		tabbedPane.addTab("use library", null, useLibrary, null);
		tabbedPane.addTab("create library", null, createLibrary, null);

		final JSplitPane splitPane = new JSplitPane();
		useLibrary.add(splitPane);

		splitPane.setRightComponent(libraryDisplay);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane.setLeftComponent(panel);
		panel.setPreferredSize(new Dimension(0, 40));

		final JButton importButton = new JButton();
		importButton.setText("import");
		panel.add(importButton, BorderLayout.SOUTH);
		importButton.addActionListener(keyLibrary);
		
		libraryList = new JList();
		String libraryPath = "src/javagulp/view/potentialLibraries";
		//java.net.URL libURL = getClass().getResource(path);
		
	    File dir = new File(libraryPath);
	    //File dir = new File(".");
	    
	    String[] children = dir.list();
	    for (int i=0; i<children.length; i++) {
	    	// Get filename of file or directory
	    	potentialListModel.addElement(children[i]);
	    }
	    libraryList.setModel(potentialListModel);
		
		panel.add(libraryList);
	}
	
	private class LibraryListener implements
	ListSelectionListener, Serializable {
	private static final long serialVersionUID = -2720144256318780471L;

	public void valueChanged(ListSelectionEvent e) {
		String librarySelected = (String) libraryList.getSelectedValue();
		//display the contents of the selected library
        // Open the file that is the first 
        // command line parameter
		String path = "potentialLibraries/"+librarySelected;
		java.net.URL libURL = getClass().getResource(path);

		String libraryContents = getURLContentAsString(libURL);
		libraryDisplay.setText(libraryContents);
	}
};

public String getURLContentAsString(URL url) {
	String content = "";
    try {
        // Create a URL for the desired page
//        URL url = new URL(urlString);
        // Read all the text returned by the server
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        while ((str = in.readLine()) != null) {
            // str is one line of text; readLine() strips the newline character(s)
        	content+=str;
        	content+=System.getProperty("line.separator");
        }
        in.close();
    } catch (MalformedURLException e) {
    } catch (IOException e) {
    }
	return content;
}

	public String getContents(File aFile) {
    //...checks on aFile are elided
    StringBuilder contents = new StringBuilder();
    
    try {
      //use buffering, reading one line at a time
      //FileReader always assumes default encoding is OK!
      BufferedReader input =  new BufferedReader(new FileReader(aFile));
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


private LibraryListener listMouseListener = new LibraryListener();
	
	private SerialListener keyLibrary = new SerialListener() {
		private static final long serialVersionUID = 8698926269816312994L;

		public void actionPerformed(ActionEvent e) {
				JFileChooser findLibrary = new JFileChooser();
				findLibrary.setCurrentDirectory(new File(Back.getPanel().getWD()));
				if (JFileChooser.APPROVE_OPTION == findLibrary.showOpenDialog(getParent())) {
					final File newLocation = new File(Back.getPanel().getWD() + "/"
									+ findLibrary.getSelectedFile().getName());
					try {
						new JCopy().copy(findLibrary.getSelectedFile(), newLocation);
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
					library = removeDotSomething(newLocation.getName());
			}
		}
	};
	
	

	private String removeDotSomething(final String name) {
		final String[] newName = name.split("\\.");
		return newName[0];
	}

	public String writeLibrary() throws IncompleteOptionException {
		String lines = "";
		lines = "library " + library;
		if (Back.getPanel().getPotentialOptions().chkDoNotInclude.isSelected())
			lines += " nodump";
		lines += Back.newLine;
		return lines;
	}
	
	
	

}
