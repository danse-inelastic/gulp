package javagulp.view.fit;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javagulp.model.SerialListener;

public class FitPanelHolder extends TitledPanel implements Serializable {
	private static final long serialVersionUID = 1259654196602548550L;

	public DefaultListModel fitListModel = new DefaultListModel();
	public JList listOfDataToFit = new JList(fitListModel);
	private JButton btnAddFit = new JButton("add fit");
	public ArrayList<AbstractFit> fitPanelsForGulpInputFile = new ArrayList<AbstractFit>();

	public JScrollPane scrollFit = new JScrollPane();
	
	private FitListener fitMouseListener = new FitListener();
	
	final String[] expDataTypes = { "energy", "elastic constants",
			"bulk modulus (Reuss def.)", "bulk modulus (Voigt def.)",
			"bulk modulus (Hill def.)", "shear modulus (Reuss def.)",
			"shear modulus (Voigt def.)", "shear modulus (Hill def.)",
			"static dielectric constants",
			"high frequency dielectric constants", "static refractive index",
			"high frequency refractive index", "piezoelectric constants",
			"monopole charges", "Born effective charges", "phonon frequencies",
			"entropies", "<html>heat capacity C<sub>V</sub></html>" };
	
	private String[] classNames = { "FitEnergy", "ElasticConstant",
			"BulkModulus", "BulkModulus", "BulkModulus", "ShearModulus",
			"ShearModulus", "ShearModulus", "Sdlc", "Hfdlc",
			"SRefractiveIndex", "HfRefractiveIndex", "Piezoelectric",
			"Monopole", "Born", "FitFrequency", "FitEntropy", "FitCv" };
	
	private AbstractFit[] listOfFitPanels = new AbstractFit[classNames.length];

	final JComboBox cmboBoxOfDataTypes = new JComboBox(expDataTypes);
	
	private class FitListener implements ListSelectionListener, Serializable {
		
		private static final long serialVersionUID = -6840590771901821697L;

		public void valueChanged(ListSelectionEvent e) {
			int index = listOfDataToFit.getSelectedIndex();
			if (fitListModel.getSize() > 0 && index != -1) {
				AbstractFit p = getPanel(index);
				scrollFit.setViewportView(p);
			}
		}
	};
	
	private SerialListener keyCombo = new SerialListener() {
		
		private static final long serialVersionUID = 6116061552027522383L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = cmboBoxOfDataTypes.getSelectedIndex();
			scrollFit.setViewportView(getPanel(index));
			String item = (String) cmboBoxOfDataTypes.getSelectedItem();

			// TODO check logic here
			Back.getKeys().putOrRemoveKeyword(item.equals("bulk modulus (Hill def.)"), "hill");
			Back.getKeys().putOrRemoveKeyword(item.equals("bulk modulus (Voigt def.)"), "voigt");
			Back.getKeys().putOrRemoveKeyword(item.equals("shear modulus (Hill def.)"), "hill");
			Back.getKeys().putOrRemoveKeyword(item.equals("shear modulus (Voigt def.)"), "voigt");
		}
	};
	private SerialListener keyAddFit = new SerialListener() {
		private static final long serialVersionUID = -6433532870901806139L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = cmboBoxOfDataTypes.getSelectedIndex();
			try {
				AbstractFit panel = getPanel(selectedIndex);
				panel.writeFitPanel();// I think this is done not to actually write something, but to catch any errors
				
				fitListModel.addElement(expDataTypes[selectedIndex]);//add a description of data to the visual list
				fitPanelsForGulpInputFile.add(panel); //add the panel (with its data) to the file writing list
				// "zero out" the fit panel list
				listOfFitPanels[selectedIndex] = null;
				// reset it with the panel corresponding to the selected index
				scrollFit.setViewportView(getPanel(selectedIndex));
				//scrollFit.getViewport().repaint();
				//scrollFit.setViewportView(getPanel(0));
				//getPanel(selectedIndex);
				//repaint();
			} catch (IncompleteOptionException ioe) {
				ioe.displayErrorAsPopup();
			} catch (NumberFormatException nfe) {
				if (nfe.getMessage().startsWith("Please enter a numeric value for "))
					JOptionPane.showMessageDialog(null, nfe.getMessage());
				else
					JOptionPane.showMessageDialog(null, "Please enter numeric values");
			}
		}
	};

	public FitPanelHolder() {
		super();
		setTitle("fit variables associated with different species");

		add(btnAddFit);
		btnAddFit.setBounds(321, 25, 154, 25);
		btnAddFit.addActionListener(keyAddFit);

		scrollFit.setBounds(10, 64, 557, 172);
		add(scrollFit);
		
		listOfDataToFit.addListSelectionListener(fitMouseListener);

		JLabel experimentalDataLabel = new JLabel("fit to");
		experimentalDataLabel.setBounds(10, 27, 56, 20);
		add(experimentalDataLabel);

		cmboBoxOfDataTypes.setBounds(72, 25, 243, 25);
		cmboBoxOfDataTypes.setMaximumRowCount(100000);
		add(cmboBoxOfDataTypes);
		scrollFit.setViewportView(getPanel(0));
		cmboBoxOfDataTypes.addActionListener(keyCombo);
	}

	public String writeFitPanels() {
		String lines = "";
		for (int i = 0; i < fitPanelsForGulpInputFile.size(); i++)
			lines += fitPanelsForGulpInputFile.get(i).gulpFileLines;
		if (!lines.equals(""))
			lines = "observables" + Back.newLine + lines + "end" + Back.newLine;
		return lines;
	}
	
	private AbstractFit getPanel(int index) {
		String pkg = "javagulp.view.fit.";
			
		if (listOfFitPanels[index] == null) {
			try {
				Class c = Class.forName(pkg + classNames[index]);
				listOfFitPanels[index] = (AbstractFit) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return listOfFitPanels[index];
	}
}