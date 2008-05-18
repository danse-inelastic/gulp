package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

import javagulp.view.Back;
import javagulp.view.GulpRun;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class CoordinatesTableModel extends AbstractTableModel implements
		Serializable {

	private static final long serialVersionUID = -4493355230672725945L;

	public final String[] COLUMN_NAMES;
	private final String keyword;
	private final int[] indices;

	private ArrayList<String[]> data = null;

	public CoordinatesTableModel(String[] columnNames, String Keyword,
			int[] Indices) {
		COLUMN_NAMES = columnNames;
		keyword = Keyword;
		indices = Indices;
		data = new ArrayList<String[]>();
	}

	public TreeSet<String> getAtoms() {
		TreeSet<String> atoms = new TreeSet<String>();
		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			if (row[indices[1]].equals(""))
				atoms.add(row[indices[0]]);
			else
				atoms.add(row[indices[0]] + " " + row[indices[1]]);
		}
		return atoms;
	}

	public TreeSet<String> getAtomsAndSpace() {
		TreeSet<String> atoms = getAtoms();
		atoms.add("");
		return atoms;
	}
	
	public ArrayList<double[]> getCoordinates() {
		ArrayList<double[]> xyz = new ArrayList<double[]>();
		for (int i=0; i < data.size(); i++) {
			double[] d = new double[3];
			d[0] = Double.parseDouble(data.get(i)[indices[2]]);
			d[1] = Double.parseDouble(data.get(i)[indices[3]]);
			d[2] = Double.parseDouble(data.get(i)[indices[4]]);
			xyz.add(d);
		}
		return xyz;
	};
	
	public ArrayList<String> getAllAtoms() {
		ArrayList<String> xyz = new ArrayList<String>(data.size());
		for (int i=0; i < data.size(); i++) {
			xyz.add(data.get(i)[indices[0]]);
		}
		return xyz;
	};

	private ArrayList<String> getAtomsAndPositions() {
		ArrayList<String> atomsAndPositions = new ArrayList<String>(data.size());
		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			String line = row[indices[0]] + " " + row[indices[1]] + " "
					+ row[indices[2]] + " " + row[indices[3]] + " "
					+ row[indices[4]] + Back.newLine;// may not need newline
			atomsAndPositions.add(line);
		}
		return atomsAndPositions;
	}

	private TreeSet<String> getCores() {
		TreeSet<String> atoms = new TreeSet<String>();
		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			if (!row[indices[1]].equals("shell")) {
				atoms.add(row[indices[0]]);
			}
		}
		return atoms;
	}

	public int getColumnCount() {
		return this.COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return this.COLUMN_NAMES[columnIndex];
	}

	public int getRowCount() {
		return data.size();
	}

	public String getValueAt(final int row, final int column) {
		String[] pair = data.get(row);
		return pair[column];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return true;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		String[] pair = data.get(row);
		pair[col] = (String) value;
		fireTableCellUpdated(row, col);
		updateAllAtomicLists();
	}
	
	public void importCoordinates(ArrayList<String> species, ArrayList<double[]> coordinates) {
		data.clear();
		data.ensureCapacity(species.size());

		// add rows manually for speed
		for (int i = 0; i < species.size(); i++) {
			String[] row = new String[COLUMN_NAMES.length];
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

	public void importCoordinates(String fileContents) {
		// this method expects coordinates to be in xyz format!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Scanner in = new Scanner(fileContents);
		int numOfLines = in.nextInt();
		in.nextLine();
		in.nextLine();
		data.clear();
		data.ensureCapacity(numOfLines);

		// add rows manually for speed
		for (int i = 0; i < numOfLines; i++) {
			String[] row = new String[COLUMN_NAMES.length];
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

	private void updateAllAtomicLists() {
		GulpRun gr = Back.getPanel();
		gr.getExternalForce().tdExForceTableModel.updateRows(getAtomsAndPositions());
		gr.getExternalForce().exForceTableModel.updateRows(getAtomsAndPositions());
		gr.getSurface().coordList.update(getAtomsAndPositions());
		refreshRows(gr.getPotentialOptions().polarisabilityTableModel, getCores());
		refreshRows(gr.getChargesElementsBonding().speciesTableModel, getAtoms());
		Vector<String> v = new Vector<String>(getAtomsAndSpace());
		gr.getPotentialOptions().cboSpecies.setModel(new DefaultComboBoxModel(v));
		for (int i=0; i < gr.getPotential().pnlAtom.cboAtom.length; i++)
			gr.getPotential().pnlAtom.cboAtom[i].setModel(new DefaultComboBoxModel(v));
		gr.getMd().pnlMDmass.cboShellmassSpecies.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().pnlMortiers.cboeematom.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().pnlqeq.cboatom.setModel(new DefaultComboBoxModel(v));
		gr.getElectrostatics().snm.cbosmatom.setModel(new DefaultComboBoxModel(v));

		// fire the selection changed
		int number = gr.getPotential().getCurrentPotential().potentialNumber;
		if (number == 1) {
			gr.getPotential().cboCoreShellSpring.setSelectedIndex(gr.getPotential().cboCoreShellSpring.getSelectedIndex());
		} else if (number == 2) {
			gr.getPotential().cboGeneralPotential.setSelectedIndex(gr.getPotential().cboGeneralPotential.getSelectedIndex());
		} else if (number == 3) {
			gr.getPotential().cboThreeBody.setSelectedIndex(gr.getPotential().cboThreeBody.getSelectedIndex());
		} else if (number == 4) {
			gr.getPotential().cboTorsion.setSelectedIndex(gr.getPotential().cboTorsion.getSelectedIndex());
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
		Vector<Vector<String>> replacementList = new Vector<Vector<String>>();
		for (final String element : newList) {
			boolean notFound = true;
			for (int j = 0; j < gtm.getRowCount(); j++) {
				String pair = (String) gtm.getValueAt(j, 0);
				if (element.equals(pair)) {
					Vector<String> row = new Vector<String>();
					for (int i = 0; i < gtm.getColumnCount(); i++) {
						row.add((String) gtm.getValueAt(j, i));
					}
					replacementList.add(row);
					notFound = false;
					break;
				}
			}
			if (notFound) {
				Vector<String> v = new Vector<String>();
				v.add(element);
				replacementList.add(v);
			}
		}
		Vector<String> columnNames = new Vector<String>();
		for (int i = 0; i < gtm.getColumnCount(); i++) {
			columnNames.add(gtm.getColumnName(i));
		}
		gtm.setDataVector(replacementList, columnNames);
	}

	final public void updateRows(final int newRowNum) {
		// this.numRows = newRowNum;
		int currentSize = data.size();
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
				String[] blanks = new String[COLUMN_NAMES.length];
				for (int j = 0; j < COLUMN_NAMES.length; j++)
					blanks[j] = "";
				data.add(blanks);
			}
			fireTableRowsInserted(currentSize, newRowNum - 1);
		}
	}

	public String writeTable() {// this outputs in the wrong format
		StringBuffer lines = new StringBuffer();
		if (data.size() > 0)
			lines.append(keyword + " " + data.size() + Back.newLine);
		boolean fit = Back.getKeys().containsKeyword("fit");
		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			for (int j = 0; j < row.length - 1; j++) {//minus 1 so we don't write out tether
				if (!row[j].equals("")) {
					String value = "";
					if (row[j].equals("yes")) {
						if (fit)
							value = "1 ";
					} else if (row[j].equals("no")) {
						if (fit)
							value = "0 ";
					} else {
						value = row[j] + " ";
					}
					lines.append(value);
				}
			}
			lines.append(Back.newLine);
		}
		return lines.toString();
	}

	public String writeTether() {
		String lines = "";
		for (int i = 0; i < data.size(); i++) {
			String value = data.get(i)[COLUMN_NAMES.length - 1];
			if (value.equals("yes"))
				lines += (i + 1) + ",";
		}
		if (!lines.equals(""))
			return "tether " + lines.substring(0, lines.length() - 1) + Back.newLine;
		return lines;
	}
}