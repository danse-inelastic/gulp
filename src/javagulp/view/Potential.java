package javagulp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.CgiCommunicate;
import javagulp.controller.IncompleteOptionException;
import javagulp.model.JCopy;
import javagulp.model.SerialListener;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.libs.PotentialLibs;

import javax.swing.JButton;

import javax.swing.JList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;

public class Potential extends JPanel {

	private static final long serialVersionUID = 4991943378742898078L;
	//private String libraryPath = "src/javagulp/view/potentialLibraries";
	//	private String libraryPath = "";
	private JList libraryList;
	private DefaultListModel potentialListModel = new DefaultListModel();
	ListSelectionModel listSelectionModel;
	public String librarySelected = "none";
	public String libraryContents = "";
	public CreateLibrary createLibrary = new CreateLibrary();
	public JPanel useLibrary = new JPanel();
	final JScrollPane scrollPane = new JScrollPane();
	private JTextPane libraryDisplay = new JTextPane();
	private PotentialLibs potentialLibs = new PotentialLibs();

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
		
		splitPane.setRightComponent(scrollPane);
		scrollPane.setViewportView(libraryDisplay);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane.setLeftComponent(panel);
		panel.setPreferredSize(new Dimension(0, 40));

		final JButton importButton = new JButton();
		importButton.setText("import");
		//importButton.setEnabled(false);
		panel.add(importButton, BorderLayout.SOUTH);
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				uploadPotential();
			}
		});
		libraryList = new JList();
		panel.add(libraryList);
	}
	
	public void populatePotentialList(){
		//remove any previous entries--start from scratch
		potentialListModel.clear();
		//get the potential names from the db
		Object[] potentialNames = getPotentialNamesFromDb();
		for (int i=0; i<potentialNames.length; i++) {
			// Get filename of file or directory
			potentialListModel.addElement(potentialNames[i]);
		}
		libraryList.setModel(potentialListModel);
		listSelectionModel = libraryList.getSelectionModel();
		listSelectionModel.addListSelectionListener(new LibraryListener());
		libraryList.setSelectedValue("none", true);
	}
	
	private Object[] getPotentialNamesFromDb(){ //ArrayList<String>
		Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		String cgihome = cgiMap.get("cgihome");
		CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		
		Map<String, String> getPotentialNamesQuery = new HashMap<String, String>();
		Back.getCurrentRun().putInAuthenticationInfo(getPotentialNamesQuery);
		getPotentialNamesQuery.put("actor", "directdb");
		getPotentialNamesQuery.put("routine", "get");
		getPotentialNamesQuery.put("directdb.tables", "gulppotential");
		getPotentialNamesQuery.put("directdb.columns", "potential_name");
		getPotentialNamesQuery.put("directdb.creator", "everyone");
		cgiCom.setCgiParams(getPotentialNamesQuery);
		JSONArray potentialNamesAsJSONArray = cgiCom.postAndGetJSONArray();	
		return potentialNamesAsJSONArray.getArrayList();
	}
	
	private void uploadPotential(){
		// look for a potential on the user's machine
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//fileDialog.setCurrentDirectory(new File(txtWorkingDirectory.getText()));
		String contents;
		File potentialFile;
		if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
			potentialFile = fileDialog.getSelectedFile();//.getPath();
			//read the file
			contents = getContents(potentialFile);
		} else return;

		Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;

		String cgihome = cgiMap.get("cgihome");
		CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		Map<String, String> uploadPotentialPost = new HashMap<String, String>();
		uploadPotentialPost.put("actor", "gulpsimulationwizard");
		uploadPotentialPost.put("actor.librarycontent", contents);
		uploadPotentialPost.put("actor.libraryname", potentialFile.getName());
		uploadPotentialPost.put("actor.runtype", Back.getRunTypeKeyword());
		uploadPotentialPost.put("routine", "storePotential");
		uploadPotentialPost.putAll(cgiMap);

		// resync the list of potentials (and when coding first time, change the way the potentials
		// are treated so that it grabs the list from the server rather than keeps them in jar
		// and change the way it puts the job on the server so it doesn't put the potential library as well

		// eventually put a status bar at the bottom of the ui and report progress on it
		// getTxtVnfStatus().setText("Computation "+cgiMap.get("simulationId")+" is being submitted to vnf....");
		cgiCom.setCgiParams(uploadPotentialPost);
		String response = cgiCom.postAndGetString();
		if (response.trim().equals("success")){
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" has been successfully uploaded.");
		}else{
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" was not successfully uploaded.  Please report this to jbrkeith@gmail.com with the file attached.");
		}
		populatePotentialList();
	}

	private class LibraryListener implements
	ListSelectionListener, Serializable {
		private static final long serialVersionUID = -2720144256318780471L;

		public void valueChanged(ListSelectionEvent e) {
			librarySelected = (String) libraryList.getSelectedValue();

			//String libraryContents = getURLContentAsString(libURL);
			libraryContents = potentialLibs.getFileContents(librarySelected);
			libraryDisplay.setText(libraryContents);
		}
	};

	private LibraryListener listMouseListener = new LibraryListener();

	private SerialListener keyLibrary = new SerialListener() {
		private static final long serialVersionUID = 8698926269816312994L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser findLibrary = new JFileChooser();
			findLibrary.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == findLibrary.showOpenDialog(getParent())) {
				final File newLocation = new File(Back.getCurrentRun().getWD() + "/"
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
		if (librarySelected!="none")
			lines = "library " + librarySelected;
		if (Back.getCurrentRun().getPotentialOptions().chkDoNotInclude.isSelected())
			lines += " nodump";
		if (lines!="")
			lines += Back.newLine;
		return lines;
	}
	
	/**
	 * Fetch the entire contents of a text file, and return it in a String.
	 * This style of implementation does not throw Exceptions to the caller.
	 *
	 * @param aFile is a file which already exists and can be read.
	 */
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

}
