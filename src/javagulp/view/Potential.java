package javagulp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.CgiCommunicate;
import javagulp.controller.IncompleteOptionException;
import javagulp.model.JCopy;
import javagulp.model.SerialListener;
import javagulp.view.potential.ChargesElementsBonding;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.Electrostatics;
import javagulp.view.potential.EwaldOptions;
import javagulp.view.potential.PotentialOptions;
import javagulp.view.potential.PotentialUploadDialog;
import javagulp.view.potential.libs.PotentialLibs;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.net.SocketTimeoutException;

import org.json.JSONArray;

public class Potential extends JPanel {

	private static final long serialVersionUID = 4991943378742898078L;
	//private String libraryPath = "src/javagulp/view/potentialLibraries";
	//	private String libraryPath = "";
	private final JList libraryList;
	private final DefaultListModel potentialListModel = new DefaultListModel();
	ListSelectionModel listSelectionModel;
	public String potentialSelected = "none";
	public String libraryContents = "";
	public CreateLibrary createLibrary = new CreateLibrary();
	public JPanel useLibrary = new JPanel();
	public PotentialOptions potentialOptions = new PotentialOptions();
	public ChargesElementsBonding chargesElementsBonding = new ChargesElementsBonding();
	public Electrostatics electrostatics = new Electrostatics();
	public EwaldOptions ewaldOptions = new EwaldOptions();
	final JScrollPane scrollPane = new JScrollPane();
	public JTextPane libraryDisplay = new JTextPane();
	private final PotentialLibs potentialLibs = new PotentialLibs();
	
	final JButton uploadButton = new JButton();

	public Potential() {
		super();
		setLayout(new BorderLayout());

		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);

		useLibrary.setLayout(new BorderLayout());
		tabbedPane.addTab("use library", null, useLibrary, null);
		tabbedPane.addTab("create library (experimental)", null, createLibrary, null);
		tabbedPane.addTab("potential options", null, potentialOptions, null);
		tabbedPane.addTab("charges, elements and bonding", null, chargesElementsBonding, null);
		tabbedPane.addTab("electrostatics", null, electrostatics, null);
		tabbedPane.addTab("ewald options", null, ewaldOptions, null);
		
		final JSplitPane splitPane = new JSplitPane();
		useLibrary.add(splitPane);

		splitPane.setRightComponent(scrollPane);
		scrollPane.setViewportView(libraryDisplay);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane.setLeftComponent(panel);
		panel.setPreferredSize(new Dimension(0, 40));

		uploadButton.setText("upload");
		//importButton.setEnabled(false);
		panel.add(uploadButton, BorderLayout.SOUTH);
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new PotentialUploadDialog(Back.frame);
			}
		});
		libraryList = new JList();
		panel.add(libraryList);
	}

	public void populatePotentialList(){
		//remove any previous entries--start from scratch
		this.potentialListModel.clear();
		Object[] potentialNames= null;
		//check to see if we're launching webstart
		final Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		if (cgiMap.containsKey("cgihome")){
			potentialListModel.addElement("none");
			//try to get the potential names from the db
			try {
				potentialNames = (Object[])getPotentialNamesFromDb();
			} catch(final Exception e){
				//if can't get them, just get their names from list
				potentialNames = (Object[])potentialLibs.potentials;
			}
		}else{
			//otherwise just get the potential names from the list
			potentialNames = (Object[])potentialLibs.potentials;
		}
		for (final Object potentialName : potentialNames) {
			// Get filename of file or directory
			potentialListModel.addElement(potentialName);
		}
		libraryList.setModel(potentialListModel);
		listSelectionModel = libraryList.getSelectionModel();
		listSelectionModel.addListSelectionListener(new LibraryListener());
		libraryList.setSelectedValue("none", true);
		boolean vnfmode = Back.getVnfmode();
		if(!vnfmode){
			uploadButton.setEnabled(false);
		}
	}

	private Object[] getPotentialNamesFromDb() throws Exception{ //ArrayList<String>
		final Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		final String cgihome = cgiMap.get("cgihome");
		final CgiCommunicate cgiCom = new CgiCommunicate(cgihome);

		final Map<String, String> getPotentialNamesQuery = new HashMap<String, String>();
		getPotentialNamesQuery.put("cgihome", cgihome);
		getPotentialNamesQuery.put("actor", "dbObjToWeb");
		getPotentialNamesQuery.put("routine", "getPotentialNames");
		//getPotentialNamesQuery.put("routine", "get");
		//getPotentialNamesQuery.put("dbObjToWeb.tables", "gulppotential");
		//getPotentialNamesQuery.put("dbObjToWeb.columns", "id");
		//getPotentialNamesQuery.put("dbObjToWeb.creator", "everyone");
		cgiCom.setCgiParams(getPotentialNamesQuery);
		final JSONArray potentialNamesAsJSONArray = cgiCom.postAndGetJSONArray();
		return potentialNamesAsJSONArray.getArrayList();
	}

	private class LibraryListener implements
	ListSelectionListener, Serializable {
		private static final long serialVersionUID = -2720144256318780471L;

		public void valueChanged(ListSelectionEvent e) {
			potentialSelected = (String) libraryList.getSelectedValue();
			if(potentialSelected==null){
				potentialSelected="none";
			}
			//String libraryContents = getURLContentAsString(libURL);
			try {
				libraryContents = potentialLibs.getFileContents(potentialSelected);
			} catch (SocketTimeoutException e1) {
				//if you can't get them from the database, assume their in the jar
				//libraryContents = potentialLibs.getJarredFileContents(potentialSelected);
			}
			potentialLibs.potentialContents.put(potentialSelected, libraryContents);
			libraryDisplay.setText(libraryContents);
		}
	};

	private final LibraryListener listMouseListener = new LibraryListener();

	private final SerialListener keyLibrary = new SerialListener() {
		private static final long serialVersionUID = 8698926269816312994L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser findLibrary = new JFileChooser();
			findLibrary.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == findLibrary.showOpenDialog(getParent())) {
				final File newLocation = new File(Back.getCurrentRun().getWD() + "/"
						+ findLibrary.getSelectedFile().getName());
				try {
					new JCopy().copy(findLibrary.getSelectedFile(), newLocation);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
				potentialSelected = removeDotSomething(newLocation.getName());
			}
		}
	};

	private String removeDotSomething(final String name) {
		final String[] newName = name.split("\\.");
		return newName[0];
	}
	
//	public PotentialOptions getPotentialOptions() {
//	return (PotentialOptions) getTopPanel(2);
//}
//
//public ChargesElementsBonding getChargesElementsBonding() {
//	return (ChargesElementsBonding) getTopPanel(3);
//}
//
//public Electrostatics getElectrostatics() {
//	return (Electrostatics) getTopPanel(4);
//}
//
//public EwaldOptions getEwaldOptions() {
//	return (EwaldOptions) getTopPanel(5);
//}

	public String writeLibrary() throws IncompleteOptionException {
		String lines = "";
		if (potentialSelected!="none")
			lines = "library " + potentialSelected;
		if (potentialOptions.chkDoNotInclude.isSelected())
			lines += " nodump";
		if (lines!="")
			lines += Back.newLine;
		return lines;
	}

}
