package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;

import javagulp.view.Back;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CartesianTable extends CoordinateTable  implements
Serializable {

	private static final long serialVersionUID = 5454202123615799893L;

	//ArrayList<String[]> data = null;
	ArrayList<CartesianTableModel> coordinatesTableModels = null;
	
	public void createNewTableModel(int region){
		coordinatesTableModels.set(region, new CartesianTableModel(cols, indices));
		//CoordinatesTableModel cartesianCoordinatesTableModel = new CartesianCoordinatesTableModel(cols, "cartesian", indices);
		setModel((TableModel) coordinatesTableModels.get(region));
	}

	public CartesianTable() {
		//super(new CartesianCoordinatesTableModel(cols, "cartesian", indices));
		super();

		// create the 0th region
		createNewTableModel(0);
		
		final TableColumnModel tcm = this.getColumnModel();
		//String[] noyes = {"", "no", "yes"};
		final String[] noyes = {"", "no reference", "fit reference", "optimise", "fix"};
		setUpComboBoxColumn(tcm.getColumn(1), new String[] { "", "core", "shell" });
		setUpComboBoxColumn(tcm.getColumn(8), noyes);
		setUpComboBoxColumn(tcm.getColumn(9), noyes);
		setUpComboBoxColumn(tcm.getColumn(10), noyes);
		setUpComboBoxColumn(tcm.getColumn(12), noyes);

		final IconHeaderRenderer ihr = new IconHeaderRenderer();
		tcm.getColumn(6).setHeaderRenderer(ihr);
		tcm.getColumn(7).setHeaderRenderer(ihr);
		tcm.getColumn(11).setHeaderRenderer(ihr);	

		//this.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//		final TableColumnModel tcm = this.getColumnModel();
		//		final String[] noyes = {"", "no", "yes"};
		//		setUpComboBoxColumn(tcm.getColumn(4),
		//				new String[] { "", "core", "shell" });
		//		setUpComboBoxColumn(tcm.getColumn(11), noyes);
		//		setUpComboBoxColumn(tcm.getColumn(12), noyes);
		//		setUpComboBoxColumn(tcm.getColumn(13), noyes);
		//		setUpComboBoxColumn(tcm.getColumn(14),
		//				new String[] { "", "translate", "slice" });
		//		setUpComboBoxColumn(tcm.getColumn(15), noyes);
		//
		//		final IconHeaderRenderer ihr = new IconHeaderRenderer();
		//		tcm.getColumn(9).setHeaderRenderer(ihr);
		//		tcm.getColumn(10).setHeaderRenderer(ihr);
		//		tcm.getColumn(14).setHeaderRenderer(ihr);
	}

	public String writeTable() {// this outputs in the wrong format
		final StringBuffer lines = new StringBuffer();

		// first go through regions and write them
		// assume the absence of a region is the "0th" region

		for(CartesianTableModel model : coordinatesTableModels){
			ArrayList<String[]> data = model.data;

			// write the header if necessary
			if (data.size() > 0 && lines.length()==0) {
				if(model.region.equals("")){//i.e. the 0th region
					lines.append(model.keyword + " " + data.size() + Back.newLine);
				}else{
					lines.append(model.keyword + " region " + model.rigidQualifier + Back.newLine);
				}
			}
			
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

		}
		return lines.toString();
	}

}