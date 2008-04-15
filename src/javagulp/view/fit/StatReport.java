package javagulp.view.fit;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javagulp.view.potential.PPP;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class StatReport extends JFrame {
private static final long serialVersionUID = 6614798981469880622L;
	
	private JScrollPane scrollStats = new JScrollPane();
	private DefaultTableModel dtmStats;
	private JTable tableStats;
	
	private JScrollPane scrollValues = new JScrollPane();
	private DefaultTableModel dtmValues;
	private JTable tableValues;
	
	public class ColorTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -8523018862617537969L;
		
		private HashMap<Integer, Color> c = new HashMap<Integer, Color>();
		
		public void setColor(Color color, int row) {
			c.put(row, color);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			Color temp = c.get(row);
			if (temp != null) {
				cell.setForeground(temp);
			}
			return cell;
		}
	}
	
	public StatReport(ArrayList<double[]> values, ArrayList<PPP> panels, Stats[] stats) {
		super();
		setLayout(null);
		setSize(1024, 768);
		
		String[] cols = new String[values.size()];
		for (int i=0; i < cols.length; i++) {
			cols[i] = panels.get(i).lbl.getText();
		}
		
		dtmStats = new DefaultTableModel(cols, 4);
		tableStats = new JTable(dtmStats);
		dtmValues = new DefaultTableModel(cols, values.get(0).length);
		tableValues = new JTable(dtmValues);
		
		TableColumnModel tcm = tableStats.getColumnModel();
		ColorTableCellRenderer ctcr = new ColorTableCellRenderer();
		for (int i=0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(ctcr);
		}
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(6);
		
		try {
			for (int i=0; i < values.size(); i++) {
				double[] d = values.get(i);
				for (int j=0; j < d.length; j++) {
					dtmValues.setValueAt(df.format(d[j]), j, i);
				}
				dtmStats.setValueAt(panels.get(i).min, 0, i);
				((ColorTableCellRenderer) tableStats.getCellRenderer(0, i)).setColor(Color.yellow, 0);
				dtmStats.setValueAt(panels.get(i).txt.getText(), 1, i);
				((ColorTableCellRenderer) tableStats.getCellRenderer(1, i)).setColor(Color.green, 1);
				dtmStats.setValueAt(panels.get(i).max, 2, i);
				((ColorTableCellRenderer) tableStats.getCellRenderer(2, i)).setColor(Color.blue, 2);
				String temp = "";
				for (int j=0; j < stats[i].pops.size(); j++)
					temp += stats[i].pops.get(j).size() + " ";
				dtmStats.setValueAt(temp, 3, i);
				((ColorTableCellRenderer) tableStats.getCellRenderer(3, i)).setColor(Color.lightGray, 3);
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		add(scrollStats);
		scrollStats.setViewportView(tableStats);
		scrollStats.setBounds(7, 5, 1014, 95);
		add(scrollValues);
		scrollValues.setViewportView(tableValues);
		scrollValues.setBounds(7, 105, 1014, 628);
		setVisible(true);
	}
}
