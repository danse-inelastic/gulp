package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javagulp.view.Back;

import javax.swing.table.AbstractTableModel;

import javagulp.model.G;

public class TdExForceTableModel extends AbstractTableModel implements
		Serializable {

	private static final long serialVersionUID = 2941977560557191547L;

	String[] COLUMN_NAMES = { "atom and position", "force direction",
			new G().html("A (eV/&Aring;)"), "B (1/s)", "C [0-1]" };
	// change everything to a hash table in future with array indices as key
	Vector<String[]> data = new Vector<String[]>();

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	public int getRowCount() {
		return data.size();
	}

	// public final Object[] longValues = {"5", "configuration.bas","5.627"};

	public Object getValueAt(final int row, final int column) {
		final String[] values = data.elementAt(row);
		return values[column];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (col > 0)
			return true;
		else
			return false;
	}

	public void printTable() {
		System.out.println("New value of data:");
		for (int i = 0; i < data.size(); i++)
			System.out.println(data.elementAt(i)[0] + ", "
					+ data.elementAt(i)[1]);
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final String[] pair = data.elementAt(row);
		pair[col] = (String) value;
		fireTableCellUpdated(row, col);
	}

	public void updateRows(final ArrayList<String> newList) {
		// strategy:
		// 1) take each new list entry and look for entries in old
		// list that correspond to it. Build up the replacement vector from
		// these old
		// entries that have matches in the new list.
		// 2) add the new entries without a corresponding entry to the list
		// 3) remake data vector from replacment vector list
		// String[] newList =
		// javagulp.view.CrystalStructure.getSortedUniqueAtomList();
		final Vector<String[]> replacementList = new Vector<String[]>();
		for (final String element : newList) {
			boolean notFound = true;
			for (int j = 0; j < data.size(); j++) {
				final String[] values = data.elementAt(j);
				if (element.equals(values[0])) {
					replacementList.add(values);
					notFound = false;
					break;
				}
			}
			if (notFound)
				replacementList.add(new String[] { element, "", "", "", "" });
		}
		data.clear();
		data.addAll(replacementList);
		fireTableDataChanged();
	}

	public String writeTdExternalForce() {
		// no error checking for now--gulp will do that for us
		// for now does all atoms--not just those in asymetric unit as it
		// is supposed to
		String lines = "";
		for (int i = 0; i < data.size(); i++) {
			final String[] values = data.elementAt(i);
			if (!values[1].equals(""))
				lines += "td_external_force " + (i + 1) + " " + values[1] + " "
						+ values[2] + " " + values[3] + " " + values[4] + Back.newLine;
		}
		return lines;
	}

}
