package javagulp.model;

import javagulp.view.Back;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import javagulp.model.G;

public class CartesianTable extends CoordinateTable {

	private static final long serialVersionUID = 5454202123615799893L;
	
	private static G g = new G();
	private static final String[] cols = new String[] { "region", "rigid",
			"relax in", "symbol", "atom type", g.html("x (" + g.ang + ")"),
			g.html("y (" + g.ang + ")"), g.html("z (" + g.ang + ")"), "charge",
			g.html("occupation<br>probability"),
			g.html("shell-core<br>radius (" + g.ang + ")"),
			"fit x", "fit y", "fit z",
			g.html("translate/<br>growth slice"), "fix positions" };
	static int[] indices = { 3, 4, 5, 6, 7 };
	
	public CartesianTable() {
		super(new CoordinatesTableModel(cols, "cartesian", indices));
		
		
		//this.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TableColumnModel tcm = this.getColumnModel();
		String[] noyes = {"", "no", "yes"};
		setUpComboBoxColumn(tcm.getColumn(0),
				new String[] { "", "1", "2", "3" });
		setUpComboBoxColumn(tcm.getColumn(1), noyes);
		setUpComboBoxColumn(tcm.getColumn(2),
				new String[] { "", "x", "y", "z", "xy", "yz", "xz", "xyz" });
		setUpComboBoxColumn(tcm.getColumn(4),
				new String[] { "", "core", "shell" });
		setUpComboBoxColumn(tcm.getColumn(11), noyes);
		setUpComboBoxColumn(tcm.getColumn(12), noyes);
		setUpComboBoxColumn(tcm.getColumn(13), noyes);
		setUpComboBoxColumn(tcm.getColumn(14),
				new String[] { "", "translate", "slice" });
		setUpComboBoxColumn(tcm.getColumn(15), noyes);
		
		IconHeaderRenderer ihr = new IconHeaderRenderer();
		tcm.getColumn(9).setHeaderRenderer(ihr);
		tcm.getColumn(10).setHeaderRenderer(ihr);
		tcm.getColumn(14).setHeaderRenderer(ihr);
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
}