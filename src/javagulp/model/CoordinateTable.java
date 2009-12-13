package javagulp.model;

import java.awt.event.MouseEvent;
import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public abstract class CoordinateTable extends JTable implements Serializable {

	private static final long serialVersionUID = -8458210070246655604L;

	public CoordinatesTableModel getTableModel() {
		return ctm;
	}
	protected CoordinatesTableModel ctm;

	private static G g = new G();
	protected static final String[] cols = new String[] { "symbol",
		"atom type", g.html("x (" + g.ang + ")"),
		g.html("y (" + g.ang + ")"), g.html("z (" + g.ang + ")"), "charge",
		g.html("occupation<br>probability"),
		g.html("shell-core<br>radius"),
		"fit/opt x", "fit/opt y", "fit/opt z",
		g.html("translate/<br>growth slice"), "fix positions" };
	static int[] indices = { 0, 1, 2, 3, 4 };

	//public abstract String getData();
	private boolean[][] selections;

	@Override
	public boolean isCellSelected(int row, int column) {
		if (selections.length < getRowCount()) {
			selections = new boolean[getRowCount()][getColumnCount()];
		}
		return selections[row][column];
	}

	private final SerialMouseAdapter keyMouse = new SerialMouseAdapter() {
		private static final long serialVersionUID = -3862775803812225199L;
		@Override
		public void mousePressed(MouseEvent e) {
			if (!e.isControlDown() && !e.isShiftDown())
				clear();
			if (!e.isControlDown()) {
				final int[] rows = getSelectedRows();
				final int[] cols = getSelectedColumns();
				for (final int row : rows) {
					for (final int col : cols) {
						toggle(row, col);
						Back.getStructure().atomicCoordinates.setValue(
								row, col, ctm.getValueAt(row, col));
					}
				}
				repaint();
			} else {
				final int row = rowAtPoint(e.getPoint());
				final int col = columnAtPoint(e.getPoint());
				toggle(row, col);
				repaint();
				Back.getStructure().atomicCoordinates.setValue(
						row, col, ctm.getValueAt(row, col));
			}
		}
	};

	public void clear() {
		for (final boolean[] selection : selections) {
			final boolean[] a = selection;
			for (int j=0; j < a.length; j++) {
				a[j] = false;
			}
		}
		repaint();
	}
	public void toggle(int row, int column) {
		if (selections[row][column])
			selections[row][column] = false;
		else
			selections[row][column] = true;
	}
	public void invertSelection() {
		for (final boolean[] selection : selections) {
			final boolean[] a = selection;
			for (int j=0; j < a.length; j++) {
				if (a[j])
					a[j] = false;
				else
					a[j] = true;
			}
		}
		repaint();
	}


	protected void setUpComboBoxColumn(TableColumn fixColumn, String[] comboBoxItems) {
		// Set up the editor for the combo box cells.
		final JComboBox comboBox = new JComboBox(comboBoxItems);
		fixColumn.setCellEditor(new DefaultCellEditor(comboBox));
		// Set up tool tips for the combobox cells.
		final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		fixColumn.setCellRenderer(renderer);
	}

	//@Override
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

	public CoordinateTable(){//CoordinatesTableModel ctm) {
		super();

		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selections = new boolean[getRowCount()][getColumnCount()];
		this.addMouseListener(keyMouse);

	}

	public abstract String writeTable();
}
