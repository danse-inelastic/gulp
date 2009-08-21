package javagulp.model;

import java.awt.event.MouseEvent;

import javagulp.view.Back;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public abstract class CoordinateTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458210070246655604L;

	public CoordinatesTableModel getTableModel() {
		return ctm;
	}
	protected CoordinatesTableModel ctm;
	
	public abstract String getData();
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

	public CoordinateTable(CoordinatesTableModelBase ctm) {
		super();

		this.ctm = ctm;
		this.setModel(ctm);
		
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selections = new boolean[getRowCount()][getColumnCount()];
		this.addMouseListener(keyMouse);
	}
}
