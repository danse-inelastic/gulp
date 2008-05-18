package javagulp.model;

import javagulp.view.Back;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import javagulp.model.G;

public class ContentsTable extends CoordinateTable {
	
	private static final long serialVersionUID = 4136087844875448131L;
	
	private static G g = new G();
	private static final String[] contentsColumns = new String[] { "symbol", "type",
			g.html("x (" + g.ang + ")"), g.html("y (" + g.ang + ")"),
			g.html("z (" + g.ang + ")"), "charge",
			g.html("average<br>coordination"), "fix positions" };
	static int[] indices = { 0, 1, 2, 3, 4 };
	
	public ContentsTable() {
		super(new CoordinatesTableModel(contentsColumns, "contents", indices));
		
		TableColumnModel tcm = this.getColumnModel();
		String[] noyes = {"", "no", "yes"};
		setUpComboBoxColumn(tcm.getColumn(7), noyes);
		
		IconHeaderRenderer ihr = new IconHeaderRenderer();
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
	
	private void setUpComboBoxColumn(TableColumn fixColumn, String[] comboBoxItems) {
		// Set up the editor for the combo box cells.
		JComboBox comboBox = new JComboBox(comboBoxItems);
		fixColumn.setCellEditor(new DefaultCellEditor(comboBox));
		// Set up tool tips for the combobox cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		fixColumn.setCellRenderer(renderer);
	}
}