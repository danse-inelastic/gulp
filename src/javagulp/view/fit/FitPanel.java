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

import utility.misc.SerialListener;

public class FitPanel extends TitledPanel implements Serializable {
	private static final long serialVersionUID = 1259654196602548550L;

	public DefaultListModel fitListModel = new DefaultListModel();
	public JList fitList = new JList(fitListModel);
	private JButton btnAddFit = new JButton("Add Fit");
	public ArrayList<AbstractFit> fits = new ArrayList<AbstractFit>();

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
	
	private AbstractFit[] panels = new AbstractFit[classNames.length];

	final JComboBox comboBox = new JComboBox(expDataTypes);
	
	private class FitListener implements ListSelectionListener, Serializable {
		
		private static final long serialVersionUID = -6840590771901821697L;

		public void valueChanged(ListSelectionEvent e) {
			int index = fitList.getSelectedIndex();
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
			int index = comboBox.getSelectedIndex();
			scrollFit.setViewportView(getPanel(index));
			String item = (String) comboBox.getSelectedItem();

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
			int index = comboBox.getSelectedIndex();
			try {
				AbstractFit panel = getPanel(index);
				panel.writeFit();
				
				fitListModel.addElement(expDataTypes[index]);
				fits.add(panel);
				panels[index] = null;
				getPanel(index);
			} catch (IncompleteOptionException ioe) {
				ioe.displayErrorAsPopup();
			} catch (NumberFormatException nfe) {
				if (nfe.getMessage().startsWith("Please enter a numeric value for "))
					JOptionPane.showMessageDialog(null, nfe.getMessage());
				else
					JOptionPane.showMessageDialog(null,
							"Please enter numeric values");
			}
		}
	};

	public FitPanel() {
		super();
		setTitle("fit variables associated with different species");

		add(btnAddFit);
		btnAddFit.setBounds(300, 20, 154, 28);
		btnAddFit.addActionListener(keyAddFit);

		scrollFit.setBounds(10, 53, 483, 160);
		add(scrollFit);
		
		fitList.addListSelectionListener(fitMouseListener);

		JLabel experimentalDataLabel = new JLabel("fit to");
		experimentalDataLabel.setBounds(10, 27, 32, 20);
		add(experimentalDataLabel);

		comboBox.setBounds(48, 27, 243, 25);
		comboBox.setMaximumRowCount(100000);
		add(comboBox);
		scrollFit.setViewportView(getPanel(0));
		comboBox.addActionListener(keyCombo);
	}

	public String writeFitPanels() {
		String lines = "";
		for (int i = 0; i < fits.size(); i++)
			lines += fits.get(i);
		return lines;
	}
	
	private AbstractFit getPanel(int index) {
		String pkg = "javagulp.view.fit.";
			
		if (panels[index] == null) {
			try {
				Class c = Class.forName(pkg + classNames[index]);
				panels[index] = (AbstractFit) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return panels[index];
	}
}