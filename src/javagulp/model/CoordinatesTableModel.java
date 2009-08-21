package javagulp.model;

import java.util.ArrayList;
import java.util.TreeSet;

public interface CoordinatesTableModel {

	public abstract TreeSet<String> getAtoms();

	public abstract TreeSet<String> getAtomsAndSpace();

	public abstract ArrayList<double[]> getCoordinates();

	public abstract ArrayList<String> getAllAtoms();

	public abstract int getColumnCount();

	public abstract String getColumnName(final int columnIndex);

	public abstract int getRowCount();

	public abstract String getValueAt(final int row, final int column);

	public abstract boolean isCellEditable(final int row, final int col);

	public abstract void setValueAt(final Object value, final int row,
			final int col);

	public abstract void importCoordinates(ArrayList<String> species,
			ArrayList<double[]> coordinates);

	public abstract void importCoordinates(String fileContents);

	public abstract void updateRows(final int newRowNum);

	public abstract String writeTether();

}