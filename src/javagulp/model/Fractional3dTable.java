package javagulp.model;

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
		
		this.ctm = new FractionalCoordinatesTableModel(cols, "fractional", indices);
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

}