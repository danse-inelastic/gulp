package javagulp.model;

import java.awt.event.MouseEvent;

import javagulp.view.Back;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javagulp.model.SerialMouseAdapter;

public abstract class CoordinateTable extends JTable {

	public CoordinatesTableModel getTableModel() {
		return ctm;
	}
	public abstract String getData();
	protected CoordinatesTableModel ctm;
	private boolean[][] selections;
	
	@Override
	public boolean isCellSelected(int row, int column) {
		if (selections.length < getRowCount()) {
			selections = new boolean[getRowCount()][getColumnCount()];
		}
		return selections[row][column];
	}
	
	private SerialMouseAdapter keyMouse = new SerialMouseAdapter() {
		private static final long serialVersionUID = -3862775803812225199L;
		@Override
		public void mousePressed(MouseEvent e) {
			if (!e.isControlDown() && !e.isShiftDown())
				clear();
			if (!e.isControlDown()) {
				int[] rows = getSelectedRows();
				int[] cols = getSelectedColumns();
				for (int i=0; i < rows.length; i++) {
					for (int j=0; j < cols.length; j++) {
						toggle(rows[i], cols[j]);
						Back.getStructure().atomicCoordinates.setValue(
								rows[i], cols[j], ctm.getValueAt(rows[i], cols[j]));
					}
				}
				repaint();
			} else {
				int row = rowAtPoint(e.getPoint());
				int col = columnAtPoint(e.getPoint());
				toggle(row, col);
				repaint();
				Back.getStructure().atomicCoordinates.setValue(
						row, col, ctm.getValueAt(row, col));
			}
		}
	};
	
	public void clear() {
		for (int i=0; i < selections.length; i++) {
			boolean[] a = selections[i];
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
		for (int i=0; i < selections.length; i++) {
			boolean[] a = selections[i];
			for (int j=0; j < a.length; j++) {
				if (a[j])
					a[j] = false;
				else
					a[j] = true;
			}
		}
		repaint();
	}
	
	public CoordinateTable(CoordinatesTableModel ctm) {
		super();
		
		this.ctm = ctm;
		this.setModel(ctm);
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selections = new boolean[getRowCount()][getColumnCount()];
		this.addMouseListener(keyMouse);
	}
}
