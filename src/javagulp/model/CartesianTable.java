package javagulp.model;

import java.io.Serializable;

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

	public CartesianTable() {
		//super(new CartesianCoordinatesTableModel(cols, "cartesian", indices));
		super();
		
		this.ctm = new CartesianCoordinatesTableModel(cols, "cartesian", indices);
		//CoordinatesTableModel cartesianCoordinatesTableModel = new CartesianCoordinatesTableModel(cols, "cartesian", indices);
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

}