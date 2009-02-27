package javagulp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.JCopy;
import javagulp.model.SerialListener;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.libs.PotentialLibs;

import javax.swing.JButton;

import javax.swing.JList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Potential extends JPanel {

	private static final long serialVersionUID = 4991943378742898078L;
	//private String libraryPath = "src/javagulp/view/potentialLibraries";
//	private String libraryPath = "";
	private JList libraryList;
	private DefaultListModel potentialListModel = new DefaultListModel();
	ListSelectionModel listSelectionModel;
	public String librarySelected = "";
	public String libraryContents = "";
	public CreateLibrary createLibrary = new CreateLibrary();
	public JPanel useLibrary = new JPanel();
	final JScrollPane scrollPane = new JScrollPane();
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

		//add(scrollPane, BorderLayout.NORTH);

		//final JPanel panel_1 = new JPanel();
		
		
		splitPane.setRightComponent(scrollPane);
		scrollPane.setViewportView(libraryDisplay);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane.setLeftComponent(panel);
		panel.setPreferredSize(new Dimension(0, 40));

		final JButton importButton = new JButton();
		importButton.setText("import");
		importButton.setEnabled(false);
		panel.add(importButton, BorderLayout.SOUTH);
		importButton.addActionListener(keyLibrary);
		
		libraryList = new JList();

	    String[] potentials = new PotentialLibs().getPotentials();
	    
	    for (int i=0; i<potentials.length; i++) {
	    	// Get filename of file or directory
	    	potentialListModel.addElement(potentials[i]);
	    }
	    libraryList.setModel(potentialListModel);
	    listSelectionModel = libraryList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new LibraryListener());
		panel.add(libraryList);
	}
	
	private class LibraryListener implements
	ListSelectionListener, Serializable {
	private static final long serialVersionUID = -2720144256318780471L;

	public void valueChanged(ListSelectionEvent e) {
		librarySelected = (String) libraryList.getSelectedValue();

		//String libraryContents = getURLContentAsString(libURL);
		libraryContents = new PotentialLibs().getFileContents(librarySelected);
		libraryDisplay.setText(libraryContents);
	}
};

private LibraryListener listMouseListener = new LibraryListener();
	
	private SerialListener keyLibrary = new SerialListener() {
		private static final long serialVersionUID = 8698926269816312994L;

		@Override
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
					librarySelected = removeDotSomething(newLocation.getName());
			}
		}
	};

	private String removeDotSomething(final String name) {
		final String[] newName = name.split("\\.");
		return newName[0];
	}

	public String writeLibrary() throws IncompleteOptionException {
		String lines = "";
		if (librarySelected!="")
			lines = "library " + librarySelected;
		if (Back.getPanel().getPotentialOptions().chkDoNotInclude.isSelected())
			lines += " nodump";
		if (lines!="")
			lines += Back.newLine;
		return lines;
	}
	
	
	

}
