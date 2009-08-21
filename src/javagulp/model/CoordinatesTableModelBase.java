package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

import javagulp.view.Back;
import javagulp.view.GulpRun;
import javagulp.view.MolecularDynamics;
import javagulp.view.SurfaceOptions;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class CoordinatesTableModelBase extends AbstractTableModel implements
Serializable, CoordinatesTableModel {

	private static final long serialVersionUID = -4493355230672725945L;

	public final String[] COLUMN_NAMES;
	protected final String keyword;
	protected final int[] indices;

	protected ArrayList<String[]> data = null;

	public CoordinatesTableModelBase(String[] columnNames, String Keyword,
			int[] Indices) {
		COLUMN_NAMES = columnNames;
		keyword = Keyword;
		indices = Indices;
		data = new ArrayList<String[]>();
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getAtoms()
	 */
	public TreeSet<String> getAtoms() {
		final TreeSet<String> atoms = new TreeSet<String>();
		for (int i = 0; i < data.size(); i++) {
			final String[] row = data.get(i);
			if (row[indices[1]].equals(""))
				atoms.add(row[indices[0]]);
			else
				atoms.add(row[indices[0]] + " " + row[indices[1]]);
		}
		return atoms;
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getAtomsAndSpace()
	 */
	public TreeSet<String> getAtomsAndSpace() {
		final TreeSet<String> atoms = getAtoms();
		atoms.add("");
		return atoms;
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getCoordinates()
	 */
	public ArrayList<double[]> getCoordinates() {
		final ArrayList<double[]> xyz = new ArrayList<double[]>();
		for (int i=0; i < data.size(); i++) {
			final double[] d = new double[3];
			d[0] = Double.parseDouble(data.get(i)[indices[2]]);
			d[1] = Double.parseDouble(data.get(i)[indices[3]]);
			d[2] = Double.parseDouble(data.get(i)[indices[4]]);
			xyz.add(d);
		}
		return xyz;
	};

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getAllAtoms()
	 */
	public ArrayList<String> getAllAtoms() {
		final ArrayList<String> xyz = new ArrayList<String>(data.size());
		for (int i=0; i < data.size(); i++) {
			xyz.add(data.get(i)[indices[0]]);
		}
		return xyz;
	};

	private ArrayList<String> getAtomsAndPositions() {
		final ArrayList<String> atomsAndPositions = new ArrayList<String>(data.size());
		for (int i = 0; i < data.size(); i++) {
			final String[] row = data.get(i);
			final String line = row[indices[0]] + " " + row[indices[1]] + " "
			+ row[indices[2]] + " " + row[indices[3]] + " "
			+ row[indices[4]] + Back.newLine;// may not need newline
			atomsAndPositions.add(line);
		}
		return atomsAndPositions;
	}

	private TreeSet<String> getCores() {
		final TreeSet<String> atoms = new TreeSet<String>();
		for (int i = 0; i < data.size(); i++) {
			final String[] row = data.get(i);
			if (!row[indices[1]].equals("shell")) {
				atoms.add(row[indices[0]]);
			}
		}
		return atoms;
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return this.COLUMN_NAMES.length;
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int columnIndex) {
		return this.COLUMN_NAMES[columnIndex];
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getRowCount()
	 */
	public int getRowCount() {
		return data.size();
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#getValueAt(int, int)
	 */
	public String getValueAt(final int row, final int column) {
		final String[] pair = data.get(row);
		return pair[column];
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(final int row, final int col) {
		return true;
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final String[] pair = data.get(row);
		pair[col] = (String) value;
		fireTableCellUpdated(row, col);
		updateAllAtomicLists();
	}



	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#importCoordinates(java.util.ArrayList, java.util.ArrayList)
	 */
	public void importCoordinates(ArrayList<String> species, ArrayList<double[]> coordinates) {
		data.clear();
		data.ensureCapacity(species.size());

		// add rows manually for speed
		for (int i = 0; i < species.size(); i++) {
			final String[] row = new String[COLUMN_NAMES.length];
			for (int j = 0; j < row.length; j++)
				row[j] = "";
			row[indices[0]] = ""+species.get(i);
			row[indices[2]] = ""+coordinates.get(i)[0];
			row[indices[3]] = ""+coordinates.get(i)[1];
			row[indices[4]] = ""+coordinates.get(i)[2];
			data.add(row);
		}
		fireTableChanged(new TableModelEvent(this));
		updateAllAtomicLists();
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#importCoordinates(java.lang.String)
	 */
	public void importCoordinates(String fileContents) {
		// this method expects coordinates to be in xyz format!!
		final Scanner in = new Scanner(fileContents);
		final int numOfLines = in.nextInt();
		in.nextLine();
		in.nextLine();
		data.clear();
		data.ensureCapacity(numOfLines);

		// add rows manually for speed
		for (int i = 0; i < numOfLines; i++) {
			final String[] row = new String[COLUMN_NAMES.length];
			for (int j = 0; j < row.length; j++)
				row[j] = "";
			row[indices[0]] = in.next();
			row[indices[2]] = in.next();
			row[indices[3]] = in.next();
			row[indices[4]] = in.next();
			data.add(row);
		}
		fireTableChanged(new TableModelEvent(this));
		updateAllAtomicLists();
		in.close();
	}

	protected void updateAllAtomicLists() {
		final GulpRun gr = Back.getCurrentRun();
		gr.getExternalForce().tdExForceTableModel.updateRows(getAtomsAndPositions());
		gr.getExternalForce().exForceTableModel.updateRows(getAtomsAndPositions());
		((SurfaceOptions)gr.getSelectedRunTypePanel("surface calc/optimize")).coordList.update(getAtomsAndPositions());
		refreshRows(gr.getPotentialOptions().polarisabilityTableModel, getCores());
		refreshRows(gr.getChargesElementsBonding().speciesTableModel, getAtoms());
		final Vector<String> v = new Vector<String>(getAtomsAndSpace());
		gr.getPotentialOptions().cboSpecies.setModel(new DefaultComboBoxModel(v));
		for (final JComboBox element : gr.getPotential().createLibrary.pnlAtom.cboAtom)
			element.setModel(new DefaultComboBoxModel(v));
		((MolecularDynamics)gr.getSelectedRunTypePanel("molecular dynamics")).pnlMDmass.cboShellmassSpecies.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().pnlMortiers.cboeematom.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().pnlqeq.cboatom.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().pnlSnm.cbosmatom.setModel(new DefaultComboBoxModel(v));

		// fire the selection changed
		final int number = gr.getPotential().createLibrary.getCurrentPotential().potentialNumber;
		if (number == 1) {
			gr.getPotential().createLibrary.cboOneBodyPotential.setSelectedIndex(gr.getPotential().createLibrary.cboOneBodyPotential.getSelectedIndex());
		} else if (number == 2) {
			gr.getPotential().createLibrary.cboTwoBodyPotential.setSelectedIndex(gr.getPotential().createLibrary.cboTwoBodyPotential.getSelectedIndex());
		} else if (number == 3) {
			gr.getPotential().createLibrary.cboThreeBodyPotential.setSelectedIndex(gr.getPotential().createLibrary.cboThreeBodyPotential.getSelectedIndex());
		} else if (number == 4) {
			gr.getPotential().createLibrary.cboFourBodyPotential.setSelectedIndex(gr.getPotential().createLibrary.cboFourBodyPotential.getSelectedIndex());
		} else
			;// error
	}

	/**
	 * This method adds the unique part of newList (those elements not already in gtm)
	 * 
	 * @param gtm
	 * @param newList
	 */
	private void refreshRows(GenericTableModel gtm, TreeSet<String> newList) {
		final Vector<Vector<String>> replacementList = new Vector<Vector<String>>();
		for (final String element : newList) {
			boolean notFound = true;
			for (int j = 0; j < gtm.getRowCount(); j++) {
				final String pair = (String) gtm.getValueAt(j, 0);
				if (element.equals(pair)) {
					final Vector<String> row = new Vector<String>();
					for (int i = 0; i < gtm.getColumnCount(); i++) {
						row.add((String) gtm.getValueAt(j, i));
					}
					replacementList.add(row);
					notFound = false;
					break;
				}
			}
			if (notFound) {
				final Vector<String> v = new Vector<String>();
				v.add(element);
				replacementList.add(v);
			}
		}
		final Vector<String> columnNames = new Vector<String>();
		for (int i = 0; i < gtm.getColumnCount(); i++) {
			columnNames.add(gtm.getColumnName(i));
		}
		gtm.setDataVector(replacementList, columnNames);
	}

	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#updateRows(int)
	 */
	final public void updateRows(final int newRowNum) {
		// this.numRows = newRowNum;
		final int currentSize = data.size();
		// chop rows off
		if (currentSize > newRowNum) {
			for (int i = currentSize; i > newRowNum; i--)
				data.remove(i - 1); // to offset the 0 index
			fireTableRowsDeleted(newRowNum, currentSize - 1);
			// do nothing
		} else if (currentSize == newRowNum) {
			// add rows on
		} else if (currentSize < newRowNum) {
			for (int i = currentSize; i < newRowNum; i++) {
				final String[] blanks = new String[COLUMN_NAMES.length];
				for (int j = 0; j < COLUMN_NAMES.length; j++)
					blanks[j] = "";
				data.add(blanks);
			}
			fireTableRowsInserted(currentSize, newRowNum - 1);
		}
	}


	/* (non-Javadoc)
	 * @see javagulp.model.CoordinatesTableModel#writeTether()
	 */
	public String writeTether() {
		String lines = "";
		for (int i = 0; i < data.size(); i++) {
			final String value = data.get(i)[COLUMN_NAMES.length - 1];
			if (value.equals("yes"))
				lines += (i + 1) + ",";
		}
		if (!lines.equals(""))
			return "tether " + lines.substring(0, lines.length() - 1) + Back.newLine;
		return lines;
	}
}