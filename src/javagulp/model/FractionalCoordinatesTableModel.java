package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.event.TableModelEvent;

public class FractionalCoordinatesTableModel extends CoordinatesTableModel 
implements Serializable {

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

	
	
}
