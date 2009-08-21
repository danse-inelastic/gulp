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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class AtomicCoordinates extends JPanel implements Serializable {

	private static final long serialVersionUID = 7252161274170437579L;



	private final String coordinateTypes[] =
	{"3d fractional", "cartesian", "contents", "2d fractional", "1d fractional"};
	private final JComboBox cboCoordinateType = new JComboBox(coordinateTypes);

	private final Fractional3dTable fractional3dTable = new Fractional3dTable();
	private final CartesianTable cartesianTable = new CartesianTable();
	private final ContentsTable contentsTable = new ContentsTable();

	private final JTextField txtNumberOfAtoms = new JTextField("0");

	private final JLabel lblNumberOfAtoms = new JLabel("number of atoms");
	private final JLabel lblCoordinateType = new JLabel("coordinate type");

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
			if (t == cartesianTable)
				pnlTranslation.lblFractionalCoordinates.setText("Use cartesian coordinates.");
			txtNumberOfAtoms.setText(getTableModel().getRowCount() + "");
		}
	};
	
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
		this.setPreferredSize(new java.awt.Dimension(1230, 500));

		scrollPane.setBounds(0, 65, 1055, 381);
		add(scrollPane);
		scrollPane.setViewportView(fractional3dTable);

		lblNumberOfAtoms.setBounds(10, 37, 177, 15);
		add(lblNumberOfAtoms);
		txtNumberOfAtoms.setBounds(213, 35, 60, 19);
		add(txtNumberOfAtoms);
		btnSet.setBounds(279, 34, 98, 21);
		btnSet.addActionListener(keySet);
		add(btnSet);
		lblCoordinateType.setBounds(10, 11, 136, 15);
		add(lblCoordinateType);
		cboCoordinateType.setBounds(168, 9, 209, 19);
		add(cboCoordinateType);
		cboCoordinateType.addActionListener(keyCoordinateType);
		btnImportCoordinates.setBounds(599, 34, 225, 25);
		btnImportCoordinates.setMargin(new Insets(0, 0, 0, 0));
		//btnImportCoordinates.setEnabled(false);
		add(btnImportCoordinates);
		btnImportCoordinates.addActionListener(keyImportCoordinates);
		btnSaveCoordinates.setBounds(830, 34, 225, 25);
		btnSaveCoordinates.setMargin(new Insets(0, 0, 0, 0));
		add(btnSaveCoordinates);
		btnSaveCoordinates.addActionListener(keySaveCoordinates);

		pnlTranslation.setBounds(0, 452, 1055, 82);
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
		return getTable().getTableModel().writeTable();
	}

	public String writeTranslate() throws IncompleteOptionException {
		return pnlTranslation.writeTranslate();
	}

	public void setValue(int row, int column, String value) {
		txtValue.setText(value);
	}
}