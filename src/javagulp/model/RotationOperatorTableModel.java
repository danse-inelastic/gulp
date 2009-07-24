package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.table.AbstractTableModel;

public class RotationOperatorTableModel extends AbstractTableModel implements
Serializable {

	private static final long serialVersionUID = -2311186260198580908L;

	public String[][] rotInitial = new String[][] { { "1", "0", "0" },
			{ "0", "1", "0" }, { "0", "0", "1" } };

	public String[][] rotOps = new String[][] { { "1", "0", "0" },
			{ "0", "1", "0" }, { "0", "0", "1" } };

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return 3;
	}

	public String getValueAt(final int rowIndex, final int columnIndex) {
		return rotOps[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return true;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		rotOps[row][col] = (String) value;
	}

	private boolean opsAreEqual(final String[][] rOp1, final String[][] tOp1,
			final String[][] rOp2, final String[][] tOp2) {
		boolean areEqual = true;
		for (int i = 0; i < 3; i++) {
			if (!tOp1[i][0].equals(tOp2[i][0])) {
				areEqual = false;
				break;
			}
			for (int j = 0; j < 3; j++) {
				if (!rOp1[i][j].equals(rOp2[i][j])) {
					areEqual = false;
					break;
				}
			}
		}
		return areEqual;
	}

	public String writeSymOpOption(TranslationOperatorTableModel t) {
		if (!opsAreEqual(rotOps, t.transOps, rotInitial, t.transInitial)) {
			String line = "symmetry_operator ";
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					line += rotOps[i][j] + " ";
				}
				line += t.transOps[i][0] + " ";
			}
			return line.trim() + Back.newLine;
		} else
			return "";
	}
}
