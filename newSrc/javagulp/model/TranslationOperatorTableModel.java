package javagulp.model;

import java.io.Serializable;

import javax.swing.table.AbstractTableModel;

public class TranslationOperatorTableModel extends AbstractTableModel implements
		Serializable {

	private static final long serialVersionUID = -2319627465799138686L;

	public String[][] transInitial = new String[][] { { "0" }, { "0" }, { "0" } };
	public String[][] transOps = new String[][] { { "0" }, { "0" }, { "0" } };

	public int getColumnCount() {
		return 1;
	}

	public int getRowCount() {
		return 3;
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return transOps[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return true;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		transOps[row][col] = (String) value;
	}

}
