package javagulp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;

import javagulp.controller.GulpFileWriter;
import javagulp.controller.IncompleteOptionException;
import javagulp.model.Keywords;
import javagulp.view.Structures.Structure;
import javagulp.view.potential.PPP;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Back {

	public static final JFrame frame = new JFrame("GulpUI 0.5");
	public static final GulpFileWriter writer = new GulpFileWriter();
	//No access modifier (default access), so access is limited to the same package.
	static JTabbedPane tabs = new JTabbedPane();
	public static final PCMenu pcmenu = new PCMenu(frame);
	public static final Color grey = new Color(229, 229, 229);
	public static final String newLine = System.getProperty("line.separator");

	public Back() {
		initializeFrame(null);
	}
	
	public Back(String[] simulationParams) {
		initializeFrame(simulationParams);
	}
	
	private void initializeFrame(String[] simulationParams){
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setJMenuBar(pcmenu);

		frame.setSize(1280, 700);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
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

	public void addTab(String[] simulationParams) {
		tabs.add("" + (tabs.getTabCount() + 1), new GulpRun(simulationParams));
		getPanel().getPotential().createLibrary.cboCoreShellSpring.setSelectedIndex(0);
		tabs.setSelectedIndex(tabs.getTabCount() - 1);
	}

	
	public static String getFileContents(File file) {
		StringBuffer contents = new StringBuffer();
		Scanner in = null;
		try {
			in = new Scanner(file);
			while (in.hasNext()) {
				contents.append(in.nextLine());
				contents.append(newLine);
			}
		} catch (Exception e) {
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
	public static GulpRun getPanel() {
		return (GulpRun) tabs.getComponent(tabs.getSelectedIndex());
	}
	
	public static Structure getStructure() {
		return (Structure) getPanel().getStructures().tabs.getSelectedComponent();
	}

	/**
	 * This method returns the Keywords object of the tab that is currently visible.
	 * 
	 * @return the current instance of Keywords
	 */
	public static Keywords getKeys() {
		return getPanel().getKeywords();
	}
	
	/**
	 * This method replaces the current tab and Keywords with new instances.
	 */
	public static void clearTab() {
		tabs.setComponentAt(Back.tabs.getSelectedIndex(), new GulpRun(null));
		getPanel().getPotential().createLibrary.cboCoreShellSpring.setSelectedIndex(0);
	}
	
	/**
	 * This method returns true if any of the JTextFields are non-empty, else false.
	 * 
	 * @param	an array of JTextFields to be checked
	 * @return	false if all are empty, else true
	 */
	@Deprecated
	public static boolean checkAnyNonEmpty(JTextField[] fields) {
		for (JTextField field: fields)
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
		for (PPP field: fields)
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
		for (PPP field: fields) {
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
		double[] values = new double[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].getText().equals("")) {
					values[i] = Double.parseDouble(fields[i].getText());
				}
			} catch (NumberFormatException e) {
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
		double[] values = new double[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].txt.getText().equals("")) {
					values[i] = Double.parseDouble(fields[i].txt.getText());
				}
			} catch (NumberFormatException e) {
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
		int[] values = new int[fields.length];
		for (int i = 0; i < fields.length; i++) {
			try {
				if (!fields[i].getText().equals("")) {
					values[i] = Integer.parseInt(fields[i].getText());
				}
			} catch (NumberFormatException e) {
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
		for (int i = 0; i < fields.length; i++) {
			lines += fields[i].getText() + " ";
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
		for (int i = 0; i < fields.length; i++) {
			lines += fields[i].txt.getText() + " ";
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
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i].isVisible()) {
					if (boxes[i].isSelected())
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
	 */
	public static String writeFits(PPP[] boxes) {
		String lines = "";
		if (Back.getKeys().containsKeyword("fit")) {
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i].chk.isVisible()) {
					if (boxes[i].chk.isSelected())
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
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
}