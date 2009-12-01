package javagulp.model;

import java.io.Serializable;
import java.util.Vector;

import javagulp.view.Back;

import javax.swing.table.DefaultTableModel;

public class GenericTableModel extends DefaultTableModel implements Serializable {
	private static final long serialVersionUID = 5368945172593371885L;

	public int[] uneditableColumns = null;
	public int[] requiredColumns = null;

	public GenericTableModel() {
		super();
	}

	public GenericTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public GenericTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public GenericTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public GenericTableModel(Vector<String> columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public GenericTableModel(Vector<String> data, Vector<String> columnNames) {
		super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (uneditableColumns == null)
			return true;
		for (final int uneditableColumn : uneditableColumns) {
			if (uneditableColumn == col)
				return false;
		}
		return true;
	}

	public String writeTable(String prefix) {
		final StringBuffer lines = new StringBuffer();
		for (int i = 0; i < dataVector.size(); i++) {
			boolean good = true;
			for (final int requiredColumn : requiredColumns) {
				final Object value = getValueAt(i, requiredColumn);
				if (value == null || value.equals(""))
					good = false;
			}
			if (good) {
				lines.append(prefix);
				for (int j = 0; j < columnIdentifiers.size(); j++) {
					lines.append(getValueAt(i, j));
					lines.append(" ");
				}
				lines.append(Back.newLine);
			}
		}
		return lines.toString();
	}
}
