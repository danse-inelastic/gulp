package javagulp.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javagulp.model.BareBonesBrowserLaunch;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javagulp.model.SerialListener;

public class PCMenu extends JMenuBar implements Serializable {
	private static final long serialVersionUID = -4653899304957946949L;

	
	private JMenu FileMenu = new JMenu("File");
	private JMenuItem openGUI = new JMenuItem("Open saved GUI");
	private JMenuItem saveGUI = new JMenuItem("Save GUI");
	private JMenuItem clearGUI = new JMenuItem("Clear GUI");
	
//	private JMenu TypeOfRun = new JMenu("Type of Run");
//	private JMenuItem MolecularDynamics = new JMenuItem("MolecularDynamics");
//	private JMenuItem MonteCarlo = new JMenuItem("Monte Carlo");
//	private JMenuItem EnergeticsMatProp = new JMenuItem("Energetics and Material Properties");
//	private JMenuItem Optimization = new JMenuItem("Optimization");
//	private JMenuItem Constraints = new JMenuItem("Constraints");
//	private JMenuItem Fit = new JMenuItem("Fit");
//	private JMenuItem Phonons = new JMenuItem("Phonons");
//	private JMenuItem FreeEnergy = new JMenuItem("Free Energy");
//	private JMenuItem TransitionState = new JMenuItem("Transition State");
//	private JMenuItem StructurePrediction = new JMenuItem("Structure Prediction");
//	private JMenuItem Surface = new JMenuItem("Surface");
//	private JMenuItem ExternalForce = new JMenuItem("External Force");
//	
//	private JMenu RunInformation = new JMenu("Run Information");
//	private JMenuItem Structure = new JMenuItem("Structure");
//	private JMenuItem Potential = new JMenuItem("Potential");	
//	private JMenuItem PotentialOptions = new JMenuItem("Potential Options");	
//	private JMenuItem ChargesElementsBonding = new JMenuItem("Charges, Elements, and Bonding");	
//	private JMenuItem Electrostatics = new JMenuItem("Electrostatics");	
//	private JMenuItem EwaldOptions = new JMenuItem("Ewald Options");	
//	private JMenuItem Output = new JMenuItem("Output");	
//	private JMenuItem Execution = new JMenuItem("Execution");	
	
	private JMenu HelpMenu = new JMenu("Help");
	private JMenuItem QuickGuide = new JMenuItem("Quick Guide");
	private JMenuItem About = new JMenuItem("About");
	
	private JFrame frame;
	
	private SerialListener keyOpenGUI = new SerialListener() {
		private static final long serialVersionUID = 7826064524030933462L;
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(frame)) {
				try {
					ObjectInput oi = new ObjectInputStream(new FileInputStream(fileDialog.getSelectedFile()));
					// Back.tabs = (JTabbedPane) oi.readObject();
					Back.tabs.removeAll();
					int index = oi.readInt();
					for (int i = 0; i < index; i++) {
						Back.tabs.add("" + (i + 1), (GulpRun) oi.readObject());
					}
					oi.close();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};
	private SerialListener keySaveGUI = new SerialListener() {
		private static final long serialVersionUID = -4868482161567224549L;
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showSaveDialog(frame)) {
				try {
					ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(fileDialog.getSelectedFile()));
					// oo.writeObject(Back.tabs);
					int index = Back.tabs.getTabCount();
					oo.writeInt(index);
					for (int i = 0; i < index; i++) {
						oo.writeObject(Back.tabs.getComponent(i));
					}
					oo.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};
	private SerialListener keyGulpWebsite = new SerialListener() {
		private static final long serialVersionUID = 6123813471182449558L;
		@Override
		public void actionPerformed(ActionEvent e) {
			BareBonesBrowserLaunch.openURL("http://www.ivec.org/GULP/");
		}
	};
	private SerialListener keyAbout = new SerialListener() {
		private static final long serialVersionUID = 8134905013562805703L;
		@Override
		public void actionPerformed(ActionEvent e) {
			AboutBox dlg = new AboutBox();
			Dimension dlgSize = dlg.getPreferredSize();
			Dimension frmSize = getParent().getSize();
			Point loc = getParent().getLocation();
			dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
					(frmSize.height - dlgSize.height) / 2 + loc.y);
			dlg.setSize(new java.awt.Dimension(250, 100));
			dlg.setVisible(true);
		}
	};
	private SerialListener keyClearGUI = new SerialListener() {
		
		private static final long serialVersionUID = -7231887101581949066L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
					"Are you sure you want to clear the GUI?")) {
				Back.clearTab();
			}
		}
	};
	
	
	public PCMenu(JFrame frame) {
		super();
		this.frame = frame;

		openGUI.addActionListener(keyOpenGUI);
		FileMenu.add(openGUI);
		saveGUI.addActionListener(keySaveGUI);
		FileMenu.add(saveGUI);
		clearGUI.addActionListener(keyClearGUI);
		FileMenu.add(clearGUI);
		add(FileMenu);

		QuickGuide.addActionListener(keyGulpWebsite);
		About.addActionListener(keyAbout);
		add(HelpMenu);
		HelpMenu.add(QuickGuide);
		HelpMenu.add(About);
	}
}
