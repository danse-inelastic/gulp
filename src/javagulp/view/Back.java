package javagulp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

import javagulp.controller.GulpFileWriter;
import javagulp.controller.IncompleteOptionException;
import javagulp.model.Keywords;
import javagulp.model.TaskKeywords;
import javagulp.view.Structures.Structure;
import javagulp.view.potential.PPP;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Back {

	public static final String version = "AtomSim 0.5";
	public static final JFrame frame = new JFrame(version);
	public static final GulpFileWriter writer = new GulpFileWriter();
	//No access modifier (default access), so access is limited to the same package.
	static JTabbedPane tabs = new JTabbedPane();
	public static final PCMenu pcmenu = new PCMenu(frame);
	public static final Color grey = new Color(229, 229, 229);
	public static final String newLine = System.getProperty("line.separator");
	public static final String fileSeparator = System.getProperty("file.separator");
	public static Map<String, String> properties = new HashMap<String,String>();

	public Back() {
		getUserSettings();
		initializeFrame(null);
		//default configuration
		// Hack--until we can rip out multiple gulp runs
		//		Back.getTaskKeywords().putTaskKeywords("md");
	}

	public Back(String[] simulationParams) {
		getUserSettings();
		initializeFrame(simulationParams);
	}

	private void getUserSettings(){
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(System.getProperty("user.home") + Back.fileSeparator+".atomsim");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				String[] props = strLine.split(" ");
				properties.put(props[0],props[1]);
				//	    	    	if (props[0]=="executablePath")
				//	    	    		properties.put("executablePath")
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
		}
	}

	private void writeUserSettings(){
		try{
			// Create file 
			FileWriter fstream = new FileWriter(System.getProperty("user.home") + Back.fileSeparator+".atomsim");
			BufferedWriter out = new BufferedWriter(fstream);
			for (final String key : properties.keySet()) {
				out.write(key+" "+properties.get(key));
			}
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void initializeFrame(String[] simulationParams){
		//frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setJMenuBar(pcmenu);

		frame.setSize(1180, 800);
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		frame.add(tabs);
		addTab(simulationParams);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e) {
				writeUserSettings();
				frame.dispose();
				//        ActionListener task = new ActionListener() {
				//            boolean alreadyDisposed = false;
				//            public void actionPerformed(ActionEvent e) {
				//                if (frame.isDisplayable()) {
				//                    alreadyDisposed = true;
				//                    frame.dispose();
				//                }
				//            }
				//        };
			}

			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		tabs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete this node?") == JOptionPane.YES_OPTION) {
						tabs.remove(tabs.getSelectedIndex());
					}
				}
			}
		});
		tabs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					addTab(null);
				}
			}
		});
		frame.setVisible(true);
	}

	public void windowClosing(WindowEvent e) {
		writeUserSettings();
		frame.dispose();
		//        ActionListener task = new ActionListener() {
		//            boolean alreadyDisposed = false;
		//            public void actionPerformed(ActionEvent e) {
		//                if (frame.isDisplayable()) {
		//                    alreadyDisposed = true;
		//                    frame.dispose();
		//                }
		//            }
		//        };
	}

	public void addTab(String[] simulationParams) {
		final GulpRun gulpRun = new GulpRun();
		tabs.add("" + (tabs.getTabCount() + 1), gulpRun);
		getCurrentRun().getPotential().createLibrary.cboOneBodyPotential.setSelectedIndex(0);
		tabs.setSelectedIndex(tabs.getTabCount() - 1);
		gulpRun.processArguments(simulationParams);
		gulpRun.getPotential().populatePotentialList();
	}


	public static String getFileContents(File file) {
		final StringBuffer contents = new StringBuffer();
		Scanner in = null;
		try {
			in = new Scanner(file);
			while (in.hasNext()) {
				contents.append(in.nextLine());
				contents.append(newLine);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		in.close();
		return contents.toString();
	}

	/**
	 * This method returns the GulpRun panel of the tab that is currently visible.
	 * 
	 * @return the current instance of GulpRun
	 */
	public static GulpRun getCurrentRun() {
		return (GulpRun) tabs.getComponent(tabs.getSelectedIndex());
	}

	public static String getRunTypeKeyword() {
		return getCurrentRun().getRunTypeKeyword();
	}

	public static JPanel getSelectedRunTypePanel(String type) {
		return getCurrentRun().getSelectedRunTypePanel(type);
	}

	public static Structure getStructure() {
		return (Structure) getCurrentRun().getStructures().tabs.getSelectedComponent();
	}

	public static boolean getVnfmode() {
		return getCurrentRun().vnfMode;
	}

	/**
	 * This method returns the Keywords object of the tab that is currently visible.
	 * 
	 * @return the current instance of Keywords
	 */
	public static Keywords getKeys() {
		return getCurrentRun().getKeywords();
	}

	public static TaskKeywords getTaskKeywords() {
		return getCurrentRun().getTaskKeywords();
	}

	/**
	 * This method replaces the current tab and Keywords with new instances.
	 */
	public static void clearTab() {
		tabs.setComponentAt(Back.tabs.getSelectedIndex(), new GulpRun());
		getCurrentRun().getPotential().createLibrary.cboOneBodyPotential.setSelectedIndex(0);
	}

	/**
	 * This method returns true if any of the JTextFields are non-empty, else false.
	 * 
	 * @param	an array of JTextFields to be checked
	 * @return	false if all are empty, else true
	 */
	@Deprecated
	public static boolean checkAnyNonEmpty(JTextField[] fields) {
		for (final JTextField field: fields)
			if (!field.getText().equals(""))
				return true;
		return false;
	}

	/**
	 * This method returns true if any of the JTextFields are non-empty, else false.
	 * 
	 * @param	an array of JTextFields to be checked
	 * @return	false if all are empty, else true
	 */
	public static boolean checkAnyNonEmpty(PPP[] fields) {
		for (final PPP field: fields)
			if (!field.txt.getText().equals(""))
				return true;
		return false;
	}

	/**
	 * This method checks that every JTextField contains something, else it
	 * throws an exception with the associated description.
	 * 
	 * @param fields an array of JTextFields to be checked
	 * @param descriptions an array of corresponding descriptions
	 * @throws IncompleteOptionException
	 */
	@Deprecated
	public static void checkAllNonEmpty(JTextField[] fields, String[] descriptions)
	throws IncompleteOptionException {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for " + descriptions[i]);
		}
	}

	/**
	 * This method checks that every JTextField contains something, else it
	 * throws an exception with the associated JLabel text.
	 * 
	 * @param fields an array of PPPs to be checked
	 * @throws IncompleteOptionException
	 */
	public static void checkAllNonEmpty(PPP[] fields)
	throws IncompleteOptionException {
		for (final PPP field: fields) {
			if (field.txt.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for " + field.lbl.getText());
		}
	}

	/**
	 * This method simply calls checkAllNonEmpty and parseFieldsD
	 * 
	 * @param fields
	 * @return an array of doubles
	 * @throws IncompleteOptionException
	 */
	public static double[] checkAndParseD(PPP[] fields) throws IncompleteOptionException {
		Back.checkAllNonEmpty(fields);
		return Back.parseFieldsD(fields);
	}

	/**
	 * This method will parse the contents of an array of JTextFields and return
	 * an array of doubles.
	 * 
	 * @param fields an array of JTextFields to be parsed
	 * @param descriptions an array of corresponding descriptions
	 * @throws NumberFormatException if any of the strings do not parse
	 * @return an array of parsed double values
	 */
	@Deprecated
	public static double[] parseFieldsD(JTextField[] fields, String[] descriptions) {
		final double[] values = new double[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].getText().equals("")) {
					values[i] = Double.parseDouble(fields[i].getText());
				}
			} catch (final NumberFormatException e) {
				throw new NumberFormatException("Please enter a numeric value for " + descriptions[i]);
			}
		}
		return values;
	}

	/**
	 * This method will parse the contents of an array of JTextFields and return
	 * an array of doubles.
	 * 
	 * @param fields an array of JTextFields to be parsed
	 * @throws NumberFormatException if any of the strings do not parse
	 * @return an array of parsed double values
	 */
	public static double[] parseFieldsD(PPP[] fields) {
		final double[] values = new double[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].txt.getText().equals("")) {
					values[i] = Double.parseDouble(fields[i].txt.getText());
				}
			} catch (final NumberFormatException e) {
				throw new NumberFormatException("Please enter a numeric value for " + fields[i].lbl.getText());
			}
		}
		return values;
	}

	/**
	 * This method will parse the contents of an array of JTextFields and return
	 * an array of ints.
	 * 
	 * @param fields an array of JTextFields to be parsed
	 * @param descriptions an array of corresponding descriptions
	 * @throws NumberFormatException if any of the strings do not parse
	 * @return an array of parsed Integer values
	 */
	@Deprecated
	public static int[] parseFieldsI(JTextField[] fields, String[] descriptions) {
		final int[] values = new int[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].getText().equals("")) {
					values[i] = Integer.parseInt(fields[i].getText());
				}
			} catch (final NumberFormatException e) {
				throw new NumberFormatException("Please enter an integer value for " + descriptions[i]);
			}
		}
		return values;
	}

	/**
	 * This method concatenates the contents of an array of JTextFields.
	 * The contents are separated by spaces.
	 * 
	 * @param fields the array of JTextFields to be concatenated
	 * @return the contents of the fields
	 */
	public static String concatFields(JTextField[] fields) {
		String lines = "";
		for (final JTextField field : fields) {
			lines += field.getText() + " ";
		}
		return lines.trim();
	}



	/**
	 * This method concatenates the contents of an array of JTextFields.
	 * The contents are separated by spaces.
	 * 
	 * @param fields the array of JTextFields to be concatenated
	 * @return the contents of the fields
	 */
	public static String concatFields(PPP[] fields) {
		String lines = "";
		for (final PPP field : fields) {
			lines += field.txt.getText() + " ";
		}
		return lines.trim();
	}

	/**
	 * This method simply calls concatFields and writeFits
	 * 
	 * @param fields
	 * @return
	 */
	public static String fieldsAndFits(PPP[] fields) {
		return concatFields(fields) + writeFits(fields);
	}

	/**
	 * This method produces a string of 1s and 0s (separated by spaces)
	 * corresponding the state of a JCheckBox, with a leading space. The string
	 * is only returned if the "fit" keyword is selected.
	 * 
	 * @param boxes an array of JCheckBoxes
	 * @return a string of 1s and 0s representing the state of JCheckBoxes
	 */
	@Deprecated
	public static String writeFits(JCheckBox[] boxes) {
		String lines = "";
		if (Back.getKeys().containsKeyword("fit")) {
			for (final JCheckBox boxe : boxes) {
				if (boxe.isVisible()) {
					if (boxe.isSelected())
						lines += " 1";
					else
						lines += " 0";
				}
			}
		}
		return lines;
	}

	/**
	 * This method produces a string of 1s and 0s (separated by spaces)
	 * corresponding the state of a JCheckBox, with a leading space. The string
	 * is only returned if the "fit" keyword is selected.
	 * 
	 * @param boxes an array of JCheckBoxes
	 * @return a string of 1s and 0s representing the state of JCheckBoxes
	 * @throws IncompleteOptionException
	 */
	@Deprecated
	public static String writeFlags(JComboBox[] boxes) throws IncompleteOptionException {
		//error checking
		//		boolean noneSelected = true;
		//		boolean atLeastOneUnselected = true;
		//		boolean someSelectedSomeUnselected = false;
		//		for (JComboBox box: boxes){
		//			if(!box.getSelectedItem().equals(""))
		//				noneSelected = false;
		//		}
		//		for (JComboBox box: boxes){
		//			if(box.getSelectedItem().equals(""))
		//				atLeastOneUnselected = true;break;
		//		}
		//		if (noneSelected==false && atLeastOneUnselected==true) someSelectedSomeUnselected = true;
		//		if(someSelectedSomeUnselected)
		//			throw new IncompleteOptionException("Must indicate fitting/optimization status of either all cell parameters / atomic positions or none of them.");
		//write out flags
		String lines = "";
		for (final JComboBox box: boxes){
			if (box.isVisible()) {
				if(box.getSelectedItem().equals("fit reference") || box.getSelectedItem().equals("optimise"))
					lines += " 1";
				else
					lines += " 0";
			}
		}
		//if (Back.getKeys().containsKeyword("fit")) {
		//}
		return lines;
	}

	/**
	 * This method produces a string of 1s and 0s (separated by spaces)
	 * corresponding the state of a JCheckBox, with a leading space. The string
	 * is only returned if the "fit" keyword is selected.
	 * 
	 * @param boxes an array of JCheckBoxes
	 * @return a string of 1s and 0s representing the state of JCheckBoxes
	 */
	public static String writeFits(PPP[] boxes) {
		String lines = "";
		if (Back.getKeys().containsKeyword("fit")) {
			for (final PPP boxe : boxes) {
				if (boxe.chk.isVisible()) {
					if (boxe.chk.isSelected())
						lines += " 1";
					else
						lines += " 0";
				}
			}
		}
		return lines;
	}

	public static boolean deleteDirectory(File path) {
		if (path.isDirectory()) {
			final File[] files = path.listFiles();
			for (final File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		return (path.delete());
	}
}