package javagulp.view.structures;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.CartesianTable;
import javagulp.model.ContentsTable;
import javagulp.model.CoordinateTable;
import javagulp.model.CoordinatesTableModel;
import javagulp.model.Fractional3dTable;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class AtomicCoordinates extends JPanel implements Serializable {

	private JComboBox cboRelax;
	private JLabel allowToRelaxLabel;
	private JButton addButton;
	private JCheckBox chkBoxRigid;
	private TitledPanel pnlCoordinateType;
	private TitledPanel pnlRegion;
	private JComboBox cboRigid;
	private JComboBox cboRegion;
	private static final long serialVersionUID = 7252161274170437579L;



	private final String coordinateTypes[] =
	{"3d fractional", "cartesian", "contents", "2d fractional", "1d fractional"};
	private final JComboBox cboCoordinateType = new JComboBox(coordinateTypes);

	private final Fractional3dTable fractional3dTable = new Fractional3dTable();
	private final CartesianTable cartesianTable = new CartesianTable();
	private final ContentsTable contentsTable = new ContentsTable();

	private final JTextField txtNumberOfAtoms = new JTextField("0");

	private final JLabel lblNumberOfAtoms = new JLabel("number of atoms");

	private final JButton btnSet = new JButton("set");
	private final JButton btnImportCoordinates = new JButton("import xyz file");
	private final JButton btnSaveCoordinates = new JButton("export as xyz file");

	private final JScrollPane scrollPane = new JScrollPane();

	public JTextField txtName = new JTextField();
	private final JLabel lblName = new JLabel("structure name");
	private final JButton btnSetValue = new JButton("Set selected cells to current value");
	private final JButton btnSelectValue = new JButton("Select cells with current value");
	private final JButton btnInvertSelection = new JButton("Invert Selection");
	private final JButton btnClearSelection = new JButton("Clear Selection");
	private final JTextField txtValue = new JTextField();
	private final JLabel lblValue = new JLabel("quick fill");
	private final JPanel pnlMassSelect = new JPanel();

	private final Translation pnlTranslation = new Translation();

	private final SerialListener keySet = new SerialListener() {
		private static final long serialVersionUID = 629883991482096848L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				final int rows = Integer.parseInt(txtNumberOfAtoms.getText());
				if (rows >= 0) {
					getTableModel().updateRows(rows);
				}
			} catch (final NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,
				"Please enter a positive integer for the number of atoms.");
			}
		}
	};
	private final SerialListener keyCoordinateType = new SerialListener() {
		private static final long serialVersionUID = 4015131483745640916L;
		@Override
		public void actionPerformed(ActionEvent e) {
			pnlTranslation.lblFractionalCoordinates.setText("Use fractional coordinates.");
			final CoordinateTable t = getTable();
			scrollPane.setViewportView(t);
			if (t == cartesianTable){
				pnlTranslation.lblFractionalCoordinates.setText("Use cartesian coordinates.");
				activateRegionPanel(true);
			}else
				activateRegionPanel(false);
			txtNumberOfAtoms.setText(getTableModel().getRowCount() + "");
		}
	};
	
	private void activateRegionPanel(boolean trueFalse){
		cboRegion.setEnabled(trueFalse);
		chkBoxRigid.setEnabled(trueFalse);
		addButton.setEnabled(trueFalse);
		cboRelax.setEnabled(trueFalse);
	}

	private SerialListener keyImportCoordinates = new SerialListener() {
		private static final long serialVersionUID = -8627501403384935426L;
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setMultiSelectionEnabled(true);
			fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				File[] files = fileDialog.getSelectedFiles();
				Back.getCurrentRun().getStructures().importStructures(files);
			}
		}
	};

	private final SerialListener keySaveCoordinates = new SerialListener() {
		private static final long serialVersionUID = -2238532372348902025L;
		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fileDialog = new JFileChooser();
			fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showSaveDialog(Back.frame)) {
				final String data = getTable().getData();
				try {
					final BufferedWriter out = new BufferedWriter(new FileWriter(fileDialog.getSelectedFile()));
					out.write(data);
					out.close();
				} catch (final IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	};

	private final SerialListener keySelect = new SerialListener() {
		private static final long serialVersionUID = -9031521764407465530L;
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < ((JTable) getTable()).getRowCount(); i ++) {
				for (int j=0; j < ((JTable) getTable()).getColumnCount(); j++) {
					if (getTable().isCellSelected(i, j))
						getTableModel().setValueAt(txtValue.getText(), i, j);
				}
			}
		}
	};
	private final SerialListener keySelectRows = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
		@Override
		public void actionPerformed(ActionEvent e) {
			getTable().clear();
			for (int i = 0; i < ((JTable) getTable()).getRowCount(); i ++) {
				for (int j=0; j < ((JTable) getTable()).getColumnCount(); j++) {
					if ((getTableModel().getValueAt(i, j)).equals(txtValue.getText()))
						getTable().toggle(i, j);
				}
			}
			getTable().repaint();
		}
	};
	private final SerialListener keyInvertSelection = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
		@Override
		public void actionPerformed(ActionEvent e) {
			getTable().invertSelection();
		}
	};
	private final SerialListener keyClearSelection = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
		@Override
		public void actionPerformed(ActionEvent e) {
			getTable().clear();
		}
	};

	public AtomicCoordinates() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(1230, 500));

		scrollPane.setBounds(0, 85, 1055, 443);
		add(scrollPane);
		scrollPane.setViewportView(fractional3dTable);
		btnImportCoordinates.setBounds(599, 34, 225, 21);
		btnImportCoordinates.setMargin(new Insets(0, 0, 0, 0));
		//btnImportCoordinates.setEnabled(false);
		add(btnImportCoordinates);
		btnImportCoordinates.addActionListener(keyImportCoordinates);
		btnSaveCoordinates.setBounds(830, 34, 225, 21);
		btnSaveCoordinates.setMargin(new Insets(0, 0, 0, 0));
		add(btnSaveCoordinates);
		btnSaveCoordinates.addActionListener(keySaveCoordinates);

		pnlTranslation.setBounds(0, 534, 1055, 82);
		// removed for paper
		add(pnlTranslation);

		//add(pnlMassSelect);
		pnlMassSelect.setLayout(null);
		pnlMassSelect.setBounds(10, 452, 970, 89);
		add(lblName);
		lblName.setBounds(599, 4, 148, 28);
		add(txtName);
		txtName.setBackground(Back.grey);
		txtName.setBounds(753, 10, 302, 19);
		add(getPnlRegion());
		add(getPnlCoordinateType());
		pnlMassSelect.add(lblValue);
		lblValue.setBounds(10, 12, 105, 28);
		pnlMassSelect.add(txtValue);
		txtValue.setBounds(10, 46, 105, 19);
		pnlMassSelect.add(btnSetValue);
		btnSetValue.setBounds(120, 41, 276, 28);
		btnSetValue.addActionListener(keySelect);
		pnlMassSelect.add(btnSelectValue);
		btnSelectValue.setBounds(402, 41, 276, 28);
		btnSelectValue.addActionListener(keySelectRows);
		pnlMassSelect.add(btnInvertSelection);
		btnInvertSelection.setBounds(684, 41, 137, 28);
		btnInvertSelection.setMargin(new Insets(0, 0, 0, 0));
		btnInvertSelection.addActionListener(keyInvertSelection);
		pnlMassSelect.add(btnClearSelection);
		btnClearSelection.setBounds(827, 42, 133, 28);
		btnClearSelection.setMargin(new Insets(0, 0, 0, 0));
		btnClearSelection.addActionListener(keyClearSelection);
	}

	public CoordinatesTableModel getTableModel() {
		return getTable().getTableModel();
	}

	public CoordinateTable getTable() {
		final String s = (String) cboCoordinateType.getSelectedItem();
		if (s.equals(coordinateTypes[0])) {
			return fractional3dTable;
		} else if (s.equals(coordinateTypes[1])) {
			return cartesianTable;
		} else if (s.equals(coordinateTypes[2])) {
			return contentsTable;
		} else if (s.equals(coordinateTypes[3])){
			//sfractional
			return fractional3dTable;
		} else {
			//pfractional
			return fractional3dTable;
		}
	}

	public void setTable(String s) {
		cboCoordinateType.setSelectedItem(s);
	}

	public String writeAtomicCoordinates() {
		return getTableModel().writeTable();
	}

	public String writeTranslate() throws IncompleteOptionException {
		return pnlTranslation.writeTranslate();
	}

	public void setValue(int row, int column, String value) {
		txtValue.setText(value);
	}
	/**
	 * @return
	 */
	protected JComboBox getCboRegion() {
		if (cboRegion == null) {
			cboRegion = new JComboBox(new String[]{"","1","2","3"});
			cboRegion.setBounds(10, 22, 78, 19);
			cboRegion.setEnabled(false);
		}
		return cboRegion;
	}

	protected TitledPanel getPnlRegion() {
		if (pnlRegion == null) {
			pnlRegion = new TitledPanel();
			pnlRegion.setBounds(319, 4, 260, 75);
			pnlRegion.setTitle("assign region");
			pnlRegion.add(getCboRegion());
			pnlRegion.add(getChkBoxRigid());
			pnlRegion.add(getAddButton());
			pnlRegion.add(getAllowToRelaxLabel());
			pnlRegion.add(getCboRelax());
		}
		return pnlRegion;
	}

	protected TitledPanel getPnlCoordinateType() {
		if (pnlCoordinateType == null) {
			pnlCoordinateType = new TitledPanel();
			pnlCoordinateType.setTitle("coordinate type");
			pnlCoordinateType.setBounds(0, 4, 313, 75);
			btnSet.addActionListener(keySet);
			btnSet.setBounds(227, 44, 75, 21);
			pnlCoordinateType.add(btnSet);
			cboCoordinateType.setBounds(10, 19, 211, 19);
			pnlCoordinateType.add(cboCoordinateType);
			cboCoordinateType.addActionListener(keyCoordinateType);

			lblNumberOfAtoms.setBounds(10, 47, 145, 15);
			pnlCoordinateType.add(lblNumberOfAtoms);
			txtNumberOfAtoms.setBounds(161, 45, 60, 19);
			pnlCoordinateType.add(txtNumberOfAtoms);
		}
		return pnlCoordinateType;
	}
	/**
	 * @return
	 */
	protected JCheckBox getChkBoxRigid() {
		if (chkBoxRigid == null) {
			chkBoxRigid = new JCheckBox();
			chkBoxRigid.setText("rigid");
			chkBoxRigid.setBounds(94, 20, 69, 23);
		}
		return chkBoxRigid;
	}
	/**
	 * @return
	 */
	protected JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("add");
			addButton.setBounds(169, 19, 69, 21);
		}
		return addButton;
	}
	/**
	 * @return
	 */
	protected JLabel getAllowToRelaxLabel() {
		if (allowToRelaxLabel == null) {
			allowToRelaxLabel = new JLabel();
			allowToRelaxLabel.setText("allow to relax in");
			allowToRelaxLabel.setBounds(10, 50, 138, 15);
		}
		return allowToRelaxLabel;
	}
	/**
	 * @return
	 */
	protected JComboBox getCboRelax() {
		if (cboRelax == null) {
			cboRelax = new JComboBox(new String[]{"", "x", "y", "z", "xy", "yz", "xz", "xyz"});
			cboRelax.setBounds(154, 49, 78, 19);
		}
		return cboRelax;
	}

}