package javagulp.model;

import java.io.Serializable;

import javax.swing.event.TableModelEvent;

public class CartesianTableModel extends CoordinatesTableModel  implements
Serializable {

	private static final long serialVersionUID = -1423446402094629018L;

	public String region = ""; // this is the 0th region
	public String rigidQualifier = "";
	public String relaxDirection = "";

	public CartesianTableModel(String[] columnNames, int[] Indices) {
		super(columnNames, "cartesian", Indices);

	}

	public void setCoordinates(Material mat) {
		// loads the coordinates from a Material object
		data.clear();
		data.ensureCapacity(mat.atomSymbols.length);

		if(mat.cartesianCoordinatesVec.length>0){
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
		}
		fireTableChanged(new TableModelEvent(this));
		updateAllAtomicLists();
	}

}
