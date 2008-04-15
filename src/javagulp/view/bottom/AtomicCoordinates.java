package javagulp.view.bottom;

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
import javagulp.view.Back;
import javagulp.view.KeywordListener;
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

import utility.misc.SerialListener;
import cseo.jodaf.client.FilePackage;

public class AtomicCoordinates extends JPanel implements Serializable {

	private static final long serialVersionUID = 7252161274170437579L;

	private class Translation extends TitledPanel implements Serializable {
		private static final long serialVersionUID = 3789836754963176945L;
		
		private JLabel lblTranslateMarkedAtoms = new JLabel("translate marked atoms to");
		private JLabel lblFractionalCoordinates = new JLabel("Use fractional coordinates.");

		private JTextField txtX = new JTextField();
		private JTextField txtY = new JTextField();
		private JTextField txtZ = new JTextField();
		private JTextField txtPoints = new JTextField();
		
		private JCheckBox chkDoNotInclude = new JCheckBox("do not include the first point, or structure");

		private KeywordListener keyDoNotInclude = new KeywordListener(chkDoNotInclude, "nofirst_point");
		
		private Translation() {
			super();
			
			setTitle("translation vector");
			lblTranslateMarkedAtoms.setBounds(10, 23, 170, 15);
			add(lblTranslateMarkedAtoms);
			txtY.setBounds(242, 21, 50, 20);
			add(txtY);
			txtZ.setBounds(298, 21, 50, 20);
			add(txtZ);
			txtX.setBounds(186, 21, 50, 20);
			add(txtX);
			lblSampleAt.setBounds(354, 21, 95, 19);
			add(lblSampleAt);
			txtPoints.setBounds(455, 21, 34, 20);
			add(txtPoints);
			lblTranslationPoints.setBounds(495, 23, 286, 15);
			add(lblTranslationPoints);
			chkDoNotInclude.setBounds(450, 44, 286, 25);
			chkDoNotInclude.addActionListener(keyDoNotInclude);
			add(chkDoNotInclude);
			lblFractionalCoordinates.setBounds(185, 44, 200, 25);
			add(lblFractionalCoordinates);
		}
		
		private String writeTranslate() throws IncompleteOptionException {
			String lines = "";
			JTextField[] fields = {txtX, txtY, txtZ, txtPoints};
			if (Back.checkAnyNonEmpty(fields)) {
				String[] descriptions = {"translate x", "translate y", "translate y", "translate number"};
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = "translate " + Back.concatFields(fields) + Back.newLine;
			}
			return lines;
		}
	}
	
	private String coordinateTypes[] =
		{"3d fractional", "cartesian", "contents", "2d fractional", "1d fractional"};
	private JComboBox cboCoordinateType = new JComboBox(coordinateTypes);

	private Fractional3dTable fractional3dTable = new Fractional3dTable();
	private CartesianTable cartesianTable = new CartesianTable();
	private ContentsTable contentsTable = new ContentsTable();

	private JTextField txtNumberOfAtoms = new JTextField("0");

	private JLabel lblNumberOfAtoms = new JLabel("number of atoms");
	private JLabel lblCoordinateType = new JLabel("coordinate type");
	private JLabel lblTranslationPoints = new JLabel("additional equidistant intermediate points");
	private JLabel lblSampleAt = new JLabel("and sample at");

	private JButton btnSet = new JButton("set");
	private JButton btnImportCoordinates = new JButton("import coordinates");
	private JButton btnSaveCoordinates = new JButton("save coordinates");

	private JScrollPane scrollPane = new JScrollPane();

	public JTextField txtName = new JTextField();
	private JLabel lblName = new JLabel("Name");
	private JButton btnSetValue = new JButton("Set selected cells to current value");
	private JButton btnSelectValue = new JButton("Select cells with current value");
	private JButton btnInvertSelection = new JButton("Invert Selection");
	private JButton btnClearSelection = new JButton("Clear Selection");
	private JTextField txtValue = new JTextField();
	private JLabel lblValue = new JLabel("Current Value");
	private JPanel pnlMassSelect = new JPanel();
	
	private Translation pnlTranslation = new Translation();
	
	private SerialListener keySet = new SerialListener() {
		private static final long serialVersionUID = 629883991482096848L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int rows = Integer.parseInt(txtNumberOfAtoms.getText());
				if (rows >= 0) {
					getTableModel().updateRows(rows);
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null,
					"Please enter a positive integer for the number of atoms.");
			}
		}
	};
	private SerialListener keyCoordinateType = new SerialListener() {
		private static final long serialVersionUID = 4015131483745640916L;
		@Override
		public void actionPerformed(ActionEvent e) {
			pnlTranslation.lblFractionalCoordinates.setText("Use fractional coordinates.");
			CoordinateTable t = getTable();
			scrollPane.setViewportView((JTable) t);
			if (t == cartesianTable)
				pnlTranslation.lblFractionalCoordinates.setText("Use cartesian coordinates.");
			txtNumberOfAtoms.setText(getTableModel().getRowCount() + "");
		}
	};
	private SerialListener keyImportCoordinates = new SerialListener() {
		private static final long serialVersionUID = -8627501403384935426L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.reqboxLocal == null) {
				JFileChooser fileDialog = new JFileChooser();
				fileDialog.setMultiSelectionEnabled(true);
				fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
				if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
					File[] files = fileDialog.getSelectedFiles();
					Back.getPanel().getStructures().importStructures(files);
				}
			} else {
				FilePackage file_package = new FilePackage(Back.frame, "*.*");
				FilePackage[] file_package_array = Back.reqboxLocal.fileOpenDialog(file_package);
				if ((file_package_array == null)
						|| (file_package_array.length == 0))
					return;
				
				if (file_package_array.length == 1) {
					getTableModel().importCoordinates(file_package_array[0].getFileAsString());
					txtNumberOfAtoms.setText(getTableModel().getRowCount() + "");
				} else {
					Back.getPanel().getStructures().importStructures(file_package_array);
				}
			}
		}
	};
	private SerialListener keySaveCoordinates = new SerialListener() {
		private static final long serialVersionUID = -2238532372348902025L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.reqboxLocal == null) {
				JFileChooser fileDialog = new JFileChooser();
				fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
				if (JFileChooser.APPROVE_OPTION == fileDialog.showSaveDialog(Back.frame)) {
					String data = getTable().getData();
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(fileDialog.getSelectedFile()));
						out.write(data);
						out.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			} else {
				FilePackage file_package = new FilePackage(Back.frame, "*.*");
				String data = getTable().getData();
				file_package.setByteArray(data.getBytes());
				String file_id = Back.reqboxLocal.fileSaveAsDialog(file_package).getFileID();
				file_package.setFileID(file_id);
			}
		}
	};
	
	private SerialListener keySelect = new SerialListener() {
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
	private SerialListener keySelectRows = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
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
	private SerialListener keyInvertSelection = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
		public void actionPerformed(ActionEvent e) {
			getTable().invertSelection();
		}
	};
	private SerialListener keyClearSelection = new SerialListener() {
		private static final long serialVersionUID = 7531889272969288457L;
		public void actionPerformed(ActionEvent e) {
			getTable().clear();
		}
	};

	public AtomicCoordinates() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(1140, 413));

		scrollPane.setBounds(0, 34, 920, 291);
		add(scrollPane);
		scrollPane.setViewportView(fractional3dTable);

		lblNumberOfAtoms.setBounds(249, 11, 110, 15);
		add(lblNumberOfAtoms);
		txtNumberOfAtoms.setBounds(365, 9, 60, 19);
		add(txtNumberOfAtoms);
		btnSet.setBounds(434, 7, 56, 21);
		btnSet.addActionListener(keySet);
		add(btnSet);
		lblCoordinateType.setBounds(0, 11, 107, 15);
		add(lblCoordinateType);
		cboCoordinateType.setBounds(108, 9, 111, 19);
		add(cboCoordinateType);
		cboCoordinateType.addActionListener(keyCoordinateType);
		btnImportCoordinates.setBounds(498, 6, 125, 25);
		btnImportCoordinates.setMargin(new Insets(0, 0, 0, 0));
		add(btnImportCoordinates);
		btnImportCoordinates.addActionListener(keyImportCoordinates);
		btnSaveCoordinates.setBounds(629, 6, 115, 25);
		btnSaveCoordinates.setMargin(new Insets(0, 0, 0, 0));
		add(btnSaveCoordinates);
		btnSaveCoordinates.addActionListener(keySaveCoordinates);

		pnlTranslation.setBounds(0, 331, 910, 82);
		// removed for paper
		add(pnlTranslation);
		
		add(pnlMassSelect);
		pnlMassSelect.setLayout(null);
		pnlMassSelect.setBounds(924, 35, 300, 154);
		add(lblName);
		lblName.setBounds(749, 7, 49, 28);
		add(txtName);
		txtName.setBounds(798, 7, 119, 28);
		pnlMassSelect.add(lblValue);
		lblValue.setBounds(0, 7, 105, 28);
		pnlMassSelect.add(txtValue);
		txtValue.setBounds(105, 7, 105, 28);
		pnlMassSelect.add(btnSetValue);
		btnSetValue.setBounds(0, 42, 250, 28);
		btnSetValue.addActionListener(keySelect);
		pnlMassSelect.add(btnSelectValue);
		btnSelectValue.setBounds(0, 77, 250, 28);
		btnSelectValue.addActionListener(keySelectRows);
		pnlMassSelect.add(btnInvertSelection);
		btnInvertSelection.setBounds(0, 112, 122, 28);
		btnInvertSelection.setMargin(new Insets(0, 0, 0, 0));
		btnInvertSelection.addActionListener(keyInvertSelection);
		pnlMassSelect.add(btnClearSelection);
		btnClearSelection.setBounds(128, 112, 122, 28);
		btnClearSelection.setMargin(new Insets(0, 0, 0, 0));
		btnClearSelection.addActionListener(keyClearSelection);
	}

	public CoordinatesTableModel getTableModel() {
		return getTable().getTableModel();
	}
	
	public CoordinateTable getTable() {
		String s = (String) cboCoordinateType.getSelectedItem();
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
}