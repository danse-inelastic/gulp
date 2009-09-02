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

public class ContentsTable extends CoordinateTable implements
Serializable {

	private static final long serialVersionUID = 4136087844875448131L;

	private static G g = new G();
	private static final String[] contentsColumns = new String[] { "symbol", "type",
		g.html("x (" + g.ang + ")"), g.html("y (" + g.ang + ")"),
		g.html("z (" + g.ang + ")"), "charge",
		g.html("average<br>coordination"), "fix positions" };
	static int[] indices = { 0, 1, 2, 3, 4 };

	public ContentsTable() {
		// this line is probably wrong--probably need separate table model
		//super(new FractionalCoordinatesTableModel(contentsColumns, "contents", indices));
		super();
		
		this.ctm = new FractionalTableModel(contentsColumns, "contents", indices);
		this.setModel((TableModel) this.ctm);
		
		final TableColumnModel tcm = this.getColumnModel();
		final String[] noyes = {"", "no", "yes"};
		setUpComboBoxColumn(tcm.getColumn(7), noyes);

		final IconHeaderRenderer ihr = new IconHeaderRenderer();
		tcm.getColumn(6).setHeaderRenderer(ihr);
	}

	@Override
	public String getData() {
		String data = ctm.getRowCount() + Back.newLine + Back.newLine;
		for (int i = 0; i < ctm.getRowCount(); i++) {
			data += ctm.getValueAt(i, indices[0]) + "\t";
			data += ctm.getValueAt(i, indices[2]) + "\t";
			data += ctm.getValueAt(i, indices[3]) + "\t";
			data += ctm.getValueAt(i, indices[4]) + Back.newLine;
		}
		return data;
	}
	
	public String writeTable() {
		//TODO: this outputs in the fractional coordinates format--it should be a special format for contents
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

//	private void setUpComboBoxColumn(TableColumn fixColumn, String[] comboBoxItems) {
//		// Set up the editor for the combo box cells.
//		final JComboBox comboBox = new JComboBox(comboBoxItems);
//		fixColumn.setCellEditor(new DefaultCellEditor(comboBox));
//		// Set up tool tips for the combobox cells.
//		final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//		renderer.setToolTipText("Click for combo box");
//		fixColumn.setCellRenderer(renderer);
//	}
}