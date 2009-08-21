package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.event.TableModelEvent;

public class FractionalCoordinatesTableModel extends CoordinatesTableModelBase 
implements CoordinatesTableModel, Serializable {

	private static final long serialVersionUID = 1531588341846396429L;

	public FractionalCoordinatesTableModel(String[] columnNames,
			String Keyword, int[] Indices) {
		super(columnNames, Keyword, Indices);
	}

	public void setCoordinates(Material mat) {
		// loads the coordinates from a Material object
		data.clear();
		data.ensureCapacity(mat.atomSymbols.length);

		// add rows manually for speed
		for (int i = 0; i < mat.atomSymbols.length; i++) {
			final String[] row = new String[COLUMN_NAMES.length];
			for (int j = 0; j < row.length; j++)
				row[j] = "";
			row[indices[0]] = ""+mat.atomSymbols[i];
			row[indices[2]] = ""+mat.fractionalCoordinatesVec[i*3 + 0];
			row[indices[3]] = ""+mat.fractionalCoordinatesVec[i*3 + 1];
			row[indices[4]] = ""+mat.fractionalCoordinatesVec[i*3 + 2];
			data.add(row);
		}
		fireTableChanged(new TableModelEvent(this));
		updateAllAtomicLists();
	}
	
	
	public String writeTable() {// this outputs in the wrong format
		final StringBuffer lines = new StringBuffer();
		if (data.size() > 0)
			lines.append(keyword + " " + data.size() + Back.newLine);
		final boolean fit = Back.getKeys().containsKeyword("fit");
		for (int i = 0; i < data.size(); i++) {
			final String[] row = data.get(i);
			for (int j = 0; j < row.length - 1; j++) {//minus 1 so we don't write out tether
				if (!row[j].equals("")) {
					String value = "";
					if (row[j].equals("yes")) {
						if (fit)
							value = "1 ";
					} else if (row[j].equals("no")) {
						if (fit)
							value = "0 ";
					} else if (row[j].equals("reference")||row[j].equals("optimise")) {
						value = "1 ";
					} else if (row[j].equals("no reference")||row[j].equals("fix")) {
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
	
	
}
