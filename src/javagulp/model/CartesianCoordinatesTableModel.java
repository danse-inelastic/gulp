package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.event.TableModelEvent;

public class CartesianCoordinatesTableModel extends CoordinatesTableModelBase  implements
Serializable {

	private static final long serialVersionUID = -1423446402094629018L;


	public CartesianCoordinatesTableModel(String[] columnNames, String Keyword,
			int[] Indices) {
		super(columnNames, Keyword, Indices);
		// TODO Auto-generated constructor stub
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
			row[indices[2]] = ""+mat.cartesianCoordinatesVec[i*3 + 0];
			row[indices[3]] = ""+mat.cartesianCoordinatesVec[i*3 + 1];
			row[indices[4]] = ""+mat.cartesianCoordinatesVec[i*3 + 2];
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
