package javagulp.model;

import java.util.ArrayList;

import javagulp.view.Back;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class Fractional3dTable extends CoordinateTable {

	private static final long serialVersionUID = 7445311180824627710L;

	public Fractional3dTable() {
		//super(new FractionalCoordinatesTableModel(cols, "fractional", indices));
		super();
		
		this.ctm = new FractionalTableModel(cols, "fractional", indices);
		this.setModel((TableModel) this.ctm);
		
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

	}
	
	
	
	public String writeTable() {// this outputs in the wrong format
		final StringBuffer lines = new StringBuffer();
		
		CoordinatesTableModel model = (CoordinatesTableModel)getModel();
		ArrayList<String[]> data = model.data;
		
		if (data.size() > 0)
			lines.append(model.keyword + " " + data.size() + Back.newLine);
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