package javagulp.view.top;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.GenericTableModel;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//import utility.function.Atom;
//import utility.function.Value;
import javagulp.model.SerialListener;
//import utility.parsers.WorkspaceParser;

public class ChargesElementsBonding extends JPanel implements Serializable {

	private static final long serialVersionUID = -8200954639125886210L;

	private class BondLengthAnalysis extends JPanel {

		private static final long serialVersionUID = 3242056442664144666L;

		private JComboBox cboMaxForceBonds = new JComboBox();
		private JComboBox cboMinExcludeBond = new JComboBox();
		private JComboBox cboMinForceBond = new JComboBox();
		private JComboBox cboMaxExcludeBond = new JComboBox();

		private JCheckBox chkPrintBondLength = new JCheckBox("print bond length analysis at beginning and end of run");
		private JCheckBox chkPrintAverageBondLengths = new JCheckBox("print average bond lengths between pairs of species");
		private JCheckBox chkExcludeBondsBetween = new JCheckBox("exclude bonds between");
		private JCheckBox chkForceBondFormation = new JCheckBox("force bond formation between");

		private JLabel lblAnd = new JLabel("and");
		private JLabel lblAnd_2 = new JLabel("and");

		private JButton btnAddExcludeBonds = new JButton("add");
		private JButton btnAddForceBonds = new JButton("add");
		
		private KeywordListener keyPrintBondLength = new KeywordListener(chkPrintBondLength, "bond");
		private KeywordListener keyPrintAverageBondLengths = new KeywordListener(chkPrintAverageBondLengths, "average");
		private KeywordListener keyExcludeBondsBetween = new KeywordListener(chkExcludeBondsBetween, "nobond");
		private KeywordListener keyForceBondFormation = new KeywordListener(chkForceBondFormation, "connect");

		private SerialListener keyAddExcludeBonds = new SerialListener() {
			private static final long serialVersionUID = 5615035249529681764L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] entry = new String[] {
						(String) cboMinExcludeBond.getSelectedItem(),
						(String) cboMaxExcludeBond.getSelectedItem() };
				nobond.add(entry);
			}
		};
		
		private TreeSet<String[]> nobond = new TreeSet<String[]>();


		private BondLengthAnalysis() {
			super();
			setLayout(null);

			chkPrintBondLength.setBounds(0, 0, 381, 30);
			add(chkPrintBondLength);
			chkPrintBondLength.addActionListener(keyPrintBondLength);
			chkPrintAverageBondLengths.setBounds(10, 25, 355, 24);
			add(chkPrintAverageBondLengths);
			chkPrintAverageBondLengths.addActionListener(keyPrintAverageBondLengths);
			chkExcludeBondsBetween.setBounds(10, 48, 175, 25);
			add(chkExcludeBondsBetween);
			chkExcludeBondsBetween.addActionListener(keyExcludeBondsBetween);
			cboMinExcludeBond.setBounds(246, 49, 61, 22);
			add(cboMinExcludeBond);
			lblAnd.setBounds(321, 50, 25, 20);
			add(lblAnd);
			cboMaxExcludeBond.setBounds(352, 49, 60, 22);
			add(cboMaxExcludeBond);
			btnAddExcludeBonds.setMargin(new Insets(0, 0, 0, 0));
			btnAddExcludeBonds.addActionListener(keyAddExcludeBonds);
			btnAddExcludeBonds.setBounds(419, 48, 40, 25);
			add(btnAddExcludeBonds);
			chkForceBondFormation.addActionListener(keyForceBondFormation);
			chkForceBondFormation.setBounds(10, 74, 215, 25);
			add(chkForceBondFormation);
			cboMinForceBond.setBounds(246, 75, 61, 22);
			add(cboMinForceBond);
			add(lblAnd_2);
			lblAnd_2.setBounds(319, 76, 25, 20);
			cboMaxForceBonds.setBounds(352, 75, 61, 22);
			add(cboMaxForceBonds);
			btnAddForceBonds.setMargin(new Insets(0, 0, 0, 0));
			btnAddForceBonds.setBounds(419, 74, 40, 25);
			add(btnAddForceBonds);
			// TODO add action listener and do something
		}

		private String writeNoBond() {
			String lines = "";
			for (String[] pair: nobond)
				lines += "nobond " + pair[0] + " " + pair[1] + Back.newLine;
			return lines;
		}
	}

	private JScrollPane speciesScrollpane = new JScrollPane();
	private JTable speciesTable = new JTable();
	public GenericTableModel speciesTableModel = new GenericTableModel(new String[] { "atom types", "charges" }, 0);
	private GenericTableModel elementsTableModel = new GenericTableModel();
	private JTextField txtBondLengthTolerance = new JTextField();

	private BondLengthAnalysis pnlBondLengthAnalysis = new BondLengthAnalysis();

	private JCheckBox chkPrintDistanceAnalysis = new JCheckBox("print distance analysis at the beginning and end of a run");
	private JCheckBox chkApplyUniformNeutralizing = new JCheckBox("apply uniform neutralizing background charge");

	private KeywordListener keyPrintDistanceAnalysis = new KeywordListener(chkPrintDistanceAnalysis, "distance");
	private KeywordListener keyApplyUniformNeutralizing = new KeywordListener(chkApplyUniformNeutralizing, "qok");

	private JButton btnImportCharges = new JButton("Import Charges");
	
//	private SerialListener keyImportCharges = new SerialListener() {
//		private static final long serialVersionUID = -2724023630471511865L;
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			JFileChooser fileDialog = new JFileChooser();
//			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
//			fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
//			
//			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
//				WorkspaceParser wp = new WorkspaceParser(fileDialog.getSelectedFile().getParentFile());
//				ArrayList<Value> netCharges = wp.parseNetCharges();
//				ArrayList<Atom> atoms = wp.readPositions().get(0);
//				
//				//average charges over all timesteps and atom types
//				for (int i=0; i < speciesTableModel.getRowCount(); i++) {
//					double charge = 0, numAtoms = 0;
//					String element = (String) speciesTableModel.getValueAt(i, 0);
//					for (int j=0; j < netCharges.size(); j++) {
//						Value v = netCharges.get(j);
//						for (int k=0; k < v.y.length; k++) {
//							if (atoms.get(k).symbol.equals(element)) {
//								charge += v.y[k];
//								numAtoms++;
//							}
//						}
//					}
//					speciesTableModel.setValueAt((charge/numAtoms)+"", i, 1);
//				}
//			}
//		}
//	};
	
	public ChargesElementsBonding() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(1008, 392));

		JLabel lblChangeSymbolMass = new JLabel("change elemental information:");
		lblChangeSymbolMass.setBounds(632, 8, 210, 20);
		add(lblChangeSymbolMass);
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(630, 35, 371, 350);
		add(scrollPane);
		elementsTableModel.setDataVector(data, COLUMN_NAMES);
		final JTable table = new JTable(elementsTableModel);
		table.setCellSelectionEnabled(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(table);
		JLabel lblAtomicCharges = new JLabel("set atomic charges:");
		lblAtomicCharges.setBounds(457, 8, 139, 20);
		add(lblAtomicCharges);
		lblAtomicCharges.setToolTipText(
				"<html>This table will set atomic charges for each type of atom.<br>"
				+ "Charges for individual atoms can be entered in the table on the structures tab.<br>"
				+ "Charges should sum to approximately zero.</html>");
		btnImportCharges.setBounds(457, 130, 169, 25);
		add(btnImportCharges);
		btnImportCharges.setEnabled(false);
		//btnImportCharges.addActionListener(keyImportCharges);
		speciesScrollpane.setBounds(457, 34, 169, 95);
		add(speciesScrollpane);
		speciesTableModel.uneditableColumns = new int[] { 0 };
		speciesTableModel.requiredColumns = new int[] { 1 };
		speciesTable.setModel(speciesTableModel);
		speciesScrollpane.setViewportView(speciesTable);
		chkPrintDistanceAnalysis.addActionListener(keyPrintDistanceAnalysis);
		chkPrintDistanceAnalysis.setBounds(5, 68, 380, 25);
		add(chkPrintDistanceAnalysis);
		chkApplyUniformNeutralizing.addActionListener(keyApplyUniformNeutralizing);
		chkApplyUniformNeutralizing.setBounds(5, 99, 322, 30);
		add(chkApplyUniformNeutralizing);

		final TitledPanel pnlbondLengthTolerance = new TitledPanel();
		pnlbondLengthTolerance.setTitle("bond length tolerance when deciding if two atoms are bonded");
		pnlbondLengthTolerance.setBounds(4, 9, 447, 53);
		add(pnlbondLengthTolerance);
		txtBondLengthTolerance.setBounds(301, 23, 75, 18);
		pnlbondLengthTolerance.add(txtBondLengthTolerance);
		JLabel lblBondLengthTolerance = new JLabel("scale factor multiplying sum of covalent radii");
		lblBondLengthTolerance.setBounds(10, 25, 285, 15);
		pnlbondLengthTolerance.add(lblBondLengthTolerance);
		lblBondLengthTolerance.setToolTipText("Bond length tolerance when deciding if two atoms are bonded. Number multiplies the sum of the covalent radii.");
		pnlBondLengthAnalysis.setBounds(5, 135, 621, 107);
		// TODO fix this and reinstate
		// add(pnlBondLengthAnalysis);
	}

	public String writeChargesElementsBonding()
			throws IncompleteOptionException {
		return writeSpecies() + writeElement()
				+ pnlBondLengthAnalysis.writeNoBond() + writeRtol();
	}

	private String writeSpecies() {
		String s = speciesTableModel.writeTable("");
		if (!s.equals("")) {
			return "species " + s.split(Back.newLine).length + Back.newLine + s;
		} else
			return "";
	}

	private String writeElement() {
		String lines = "";
		for (int i = 0; i < elementsTableModel.getRowCount(); i++) {
			Object value;
			String symbol = (String) elementsTableModel.getValueAt(i, 0);
			if (!symbol.equals(data[i][0]))
				lines += "symbol " + (i + 1) + " " + symbol + Back.newLine;
			value = elementsTableModel.getValueAt(i, 1);
			if (!value.equals(data[i][1]))
				lines += "mass " + symbol + " " + value + Back.newLine;
			value = elementsTableModel.getValueAt(i, 2);
			if (!value.equals(data[i][2]))
				lines += "covalent " + (i + 1) + " " + value + Back.newLine;
			value = elementsTableModel.getValueAt(i, 3);
			if (!value.equals(data[i][3]))
				lines += "ionic " + (i + 1) + " " + value + Back.newLine;
			value = elementsTableModel.getValueAt(i, 4);
			if (!value.equals(data[i][4]))
				lines += "vdw " + (i + 1) + " " + value + Back.newLine;
		}
		if (lines.equals(""))
			return "";
		else
			return "element" + Back.newLine + lines + "end" + Back.newLine;
	}

	private String writeRtol() {
		String lines = "";
		if (!txtBondLengthTolerance.getText().equals(""))
			lines = "rtol " + txtBondLengthTolerance.getText() + Back.newLine;
		return lines;
	}

	private final String[] COLUMN_NAMES = new String[] { "symbol", "mass",
			"covalent radius", "ionic radius", "vdw radius" };

	private final String[][] data = { { "H", "1.01", "0.37", "-0.24", "1.08" },
			{ "He", "4.", "0.00", "0.00", "1." },
			{ "Li", "6.94", "0.68", "0.9", "1.8" },
			{ "Be", "9.01", "0.35", "0.59", "0.52" },
			{ "B", "10.81", "0.83", "0.25", "1.7" },
			{ "C", "12.01", "0.77", "0.3", "1.53" },
			{ "N", "14.01", "0.75", "1.32", "1.48" },
			{ "O", "16.", "0.73", "1.26", "1.36" },
			{ "F", "19.", "0.71", "1.19", "1.3" },
			{ "Ne", "20.18", "0.00", "0.00", "0.00" },
			{ "Na", "22.99", "0.97", "1.16", "2.3" },
			{ "Mg", "24.31", "1.1", "0.86", "1.64" },
			{ "Al", "26.98", "1.35", "0.675", "2.05" },
			{ "Si", "28.09", "1.2", "0.4", "2.1" },
			{ "P", "30.97", "1.05", "0.31", "1.75" },
			{ "S", "32.07", "1.02", "1.7", "1.7" },
			{ "Cl", "35.45", "0.99", "1.67", "1.65" },
			{ "Ar", "39.95", "0.00", "0.00", "0.00" },
			{ "K", "39.1", "1.33", "1.52", "2.8" },
			{ "Ca", "40.08", "0.99", "1.14", "2.75" },
			{ "Sc", "44.96", "1.44", "0.885", "2.15" },
			{ "Ti", "47.88", "1.47", "0.745", "2.19" },
			{ "V", "50.94", "1.33", "0.68", "1.99" },
			{ "Cr", "52.", "1.35", "0.755", "2.01" },
			{ "Mn", "54.94", "1.35", "0.67", "2.01" },
			{ "Fe", "55.85", "1.34", "0.69", "2." },
			{ "Co", "58.93", "1.33", "0.79", "1.99" },
			{ "Ni", "58.69", "1.5", "0.83", "1.81" },
			{ "Cu", "63.55", "1.52", "0.87", "1.54" },
			{ "Zn", "65.39", "1.45", "0.88", "2.16" },
			{ "Ga", "69.72", "1.22", "0.76", "1.82" },
			{ "Ge", "72.61", "1.17", "0.67", "1.75" },
			{ "As", "74.92", "1.21", "0.72", "2.2" },
			{ "Se", "78.96", "1.22", "1.84", "2." },
			{ "Br", "79.9", "1.21", "1.82", "1.8" },
			{ "Kr", "83.8", "1.89", "0.00", "2.82" },
			{ "Rb", "85.47", "1.47", "1.66", "2.19" },
			{ "Sr", "87.62", "1.12", "1.32", "1.67" },
			{ "Y", "88.91", "1.78", "1.04", "2.66" },
			{ "Zr", "91.22", "1.56", "0.86", "2.33" },
			{ "Nb", "92.91", "1.48", "0.78", "2.21" },
			{ "Mo", "95.94", "1.47", "0.73", "2.19" },
			{ "Tc", "98.", "1.35", "0.7", "2.01" },
			{ "Ru", "101.07", "1.4", "0.705", "2.09" },
			{ "Rh", "102.91", "1.45", "0.69", "2.16" },
			{ "Pd", "106.42", "1.5", "0.755", "2.24" },
			{ "Ag", "107.87", "1.59", "1.29", "2.37" },
			{ "Cd", "112.41", "1.69", "1.09", "2.52" },
			{ "In", "114.82", "1.63", "0.94", "2.43" },
			{ "Sn", "118.71", "1.46", "0.83", "2.18" },
			{ "Sb", "121.75", "1.46", "0.74", "2.17" },
			{ "Te", "127.6", "1.47", "2.07", "2.2" },
			{ "I", "126.91", "1.4", "2.06", "2.05" },
			{ "Xe", "131.29", "0.00", "0.00", "0.00" },
			{ "Cs", "132.91", "1.67", "1.81", "2.49" },
			{ "Ba", "137.33", "1.34", "1.49", "2." },
			{ "La", "138.91", "1.87", "1.172", "2.79" },
			{ "Ce", "140.12", "1.83", "1.01", "2.73" },
			{ "Pr", "140.91", "1.82", "1.13", "2.72" },
			{ "Nd", "144.24", "1.81", "1.123", "2.7" },
			{ "Pm", "145.", "1.8", "1.11", "2.69" },
			{ "Sm", "150.36", "1.8", "1.098", "2.69" },
			{ "Eu", "151.97", "1.99", "1.087", "2.97" },
			{ "Gd", "157.25", "1.79", "1.078", "2.67" },
			{ "Tb", "158.93", "1.76", "1.063", "2.63" },
			{ "Dy", "162.5", "1.75", "1.052", "2.61" },
			{ "Ho", "164.93", "1.74", "1.041", "2.6" },
			{ "Er", "167.26", "1.73", "1.03", "2.58" },
			{ "Tm", "168.93", "1.72", "1.02", "2.57" },
			{ "Yb", "173.04", "1.94", "1.008", "2.9" },
			{ "Lu", "174.97", "1.72", "1.001", "2.57" },
			{ "Hf", "178.49", "1.57", "0.85", "2.34" },
			{ "Ta", "180.95", "1.43", "0.78", "2.13" },
			{ "W", "183.85", "1.37", "0.65", "2.04" },
			{ "Re", "186.21", "1.35", "0.67", "2.01" },
			{ "Os", "190.2", "1.37", "0.53", "2.04" },
			{ "Ir", "192.22", "1.32", "0.71", "1.97" },
			{ "Pt", "195.08", "1.5", "0.71", "1.97" },
			{ "Au", "196.97", "1.5", "0.71", "1.85" },
			{ "Hg", "200.59", "1.7", "1.16", "1.9" },
			{ "Tl", "204.38", "1.55", "1.025", "2.31" },
			{ "Pb", "207.2", "1.54", "1.33", "2.3" },
			{ "Bi", "208.98", "1.54", "1.17", "2.3" },
			{ "Po", "209.", "1.68", "0.81", "2.51" },
			{ "At", "210.", "0.00", "0.76", "0.00" },
			{ "Rn", "222.", "0.00", "0.00", "0.00" },
			{ "Fr", "223.", "0.00", "1.94", "0.00" },
			{ "Ra", "226.03", "1.9", "1.62", "2.84" },
			{ "Ac", "227.03", "1.88", "1.26", "2.81" },
			{ "Th", "232.04", "1.79", "0.00", "2.67" },
			{ "Pa", "231.04", "1.61", "0.92", "2.4" },
			{ "U", "238.03", "1.58", "1.03", "2.36" },
			{ "Np", "237.05", "1.55", "0.85", "2.31" },
			{ "Pu", "244.", "1.53", "0.85", "2.28" },
			{ "Am", "243.", "1.51", "1.35", "2.25" },
			{ "Cm", "247.", "0.00", "0.99", "0.00" },
			{ "Bk", "247.", "0.00", "0.97", "0.00" },
			{ "Cf", "251.", "0.00", "0.961", "0.00" },
			{ "Es", "252.", "0.00", "0.00", "0.00" },
			{ "Fm", "257.", "0.00", "0.00", "0.00" },
			{ "Md", "258.", "0.00", "0.00", "0.00" },
			{ "No", "259.", "0.00", "1.24", "1.24" },
			{ "Lr", "260.", "0.00", "0.00", "0.00" },
			{ "Rf", "261.", "0.00", "0.00", "0.00" },
			{ "Ha", "260.", "0.00", "0.00", "0.00" },
			{ "D", "2.01", "0.37", "-0.24", "1.17" },
			{ "X", "0.00", "0.00", "0.00", "0.00" } };
}