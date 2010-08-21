package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.event.TableModelEvent;

import org.json.JSONArray;

public class FractionalTableModel extends CoordinatesTableModel
implements Serializable {

	private static final long serialVersionUID = 1531588341846396429L;

	public FractionalTableModel(String[] columnNames,
			String Keyword, int[] Indices) {
		super(columnNames, Keyword, Indices);
		
		// put material info into table if it exists
		final Material mat = new Material();
		String atomPositionsRaw = Back.atomSimProps.getProperty("atomPositions", "");
		String[] atomPositions = atomPositionsRaw.split(" +"); //this splits just on white space
		mat.fractionalCoordinatesVec = (Object[])atomPositions;
		String atomSymbolsRaw = Back.atomSimProps.getProperty("atomSymbols", "");
		String[] atomSymbols = atomSymbolsRaw.split(" +"); //this splits just on white space
		mat.atomSymbols = (Object[])atomSymbols;
		this.setCoordinates(mat);
	}

	public void setCoordinates(Material mat) {
		// loads the coordinates from a Material object
		data.clear();
		data.ensureCapacity(mat.atomSymbols.length);

		if(mat.fractionalCoordinatesVec.length>0){
		// add rows manually for speed
		for (int i = 0; i < mat.atomSymbols.length; i++) {
			final String[] row = new String[COLUMN_NAMES.length];
			for (int j = 0; j < row.length; j++)
				row[j] = "";
			row[indices[0]] = ""+mat.atomSymbols[i];
			
			JSONArray fracCoords = (JSONArray)mat.fractionalCoordinatesVec[i];
			row[indices[2]] = ""+fracCoords.optString(0);
			row[indices[3]] = ""+fracCoords.optString(1);
			row[indices[4]] = ""+fracCoords.optString(2);
			
//			row[indices[2]] = ""+mat.fractionalCoordinatesVec[i*3 + 0];
//			row[indices[3]] = ""+mat.fractionalCoordinatesVec[i*3 + 1];
//			row[indices[4]] = ""+mat.fractionalCoordinatesVec[i*3 + 2];
			data.add(row);
		}
		}
		fireTableChanged(new TableModelEvent(this));
		updateAllAtomicLists();
	}



}
