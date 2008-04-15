package javagulp.view.fit;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javagulp.view.potential.PPP;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import utility.misc.SerialListener;

public class FitParams extends JFrame implements Serializable {
	private static final long serialVersionUID = 6614798981469880622L;
	
	public JButton btnUpdate = new JButton("Update Parameters");
	
	private final String[] colsParam = new String[] { "Parameter Type",
			"Initial Value", "Final Value", "% Change"};
	private final String[] colsError = new String[] { "Observable", "Calculated",
			"Residual", "Error %" };
	
	private JScrollPane scrollParam = new JScrollPane();
	public DefaultTableModel dtmParam;
	private JTable tableParam;
	
	private JScrollPane scrollError = new JScrollPane();
	public DefaultTableModel dtmError;
	private JTable tableError;
	
	private JTextField txtAvgError = new JTextField();
	private JTextField txtAvgResidual = new JTextField();
	
	private JLabel lblAvgError = new JLabel("Average % Error");
	private JLabel lblAvgResidual = new JLabel("Average Residual");
	
	public double[] params;
	
	private ArrayList<PPP> ppps;
	
	public FitParams(ArrayList<PPP> ppps, ArrayList<String> oldParams, ArrayList<String> newParams,
			ArrayList<String> error) {
		super();
		setLayout(null);
		setSize(800, 600);
		
		this.ppps = ppps;
		
		dtmParam = new DefaultTableModel(colsParam, newParams.size());
		tableParam = new JTable(dtmParam);
		dtmError = new DefaultTableModel(colsError, error.size());
		tableError = new JTable(dtmError);
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(15);
		params = new double[newParams.size()];
		for (int i=0; i < oldParams.size(); i++) {
			Scanner sc = new Scanner(oldParams.get(i));
			sc.next();
			String oldValue = sc.next();
			String type = sc.nextLine();
			sc.close();
			sc = new Scanner(newParams.get(i));
			sc.next();
			String newValue = sc.next();
			params[i] = Double.parseDouble(newValue);
			
			dtmParam.setValueAt(type, i, 0);
			dtmParam.setValueAt(oldValue, i, 1);
			dtmParam.setValueAt(newValue, i, 2);
			double a = Math.abs(Double.parseDouble(oldValue));
			double b = Math.abs(Double.parseDouble(newValue));
			dtmParam.setValueAt(Math.abs((a-b)/a) * 100.0, i, 3);
			//TODO check % change formula
		}
		double residualTotal = 0, errorTotal = 0;
		for (int i=0; i < error.size(); i++) {
			Scanner scan = new Scanner(error.get(i));
			scan.next();
			scan.next();
			double observable = scan.nextDouble();
			double calculated = scan.nextDouble();
			scan.close();
			double difference = calculated-observable;
			dtmError.setValueAt(observable, i, 0);
			dtmError.setValueAt(calculated, i, 1);
			//Calculate (rather than use gulp's output) residual and error % for more accuracy.
			double residual = difference*difference;
			double errorPercent = difference/observable;
			residualTotal += residual;
			errorTotal += Math.abs(errorPercent);
			dtmError.setValueAt(df.format(residual), i, 2);
			dtmError.setValueAt(df.format(errorPercent), i, 3);
		}
		txtAvgResidual.setText(df.format(residualTotal / error.size()));
		txtAvgError.setText(df.format(errorTotal / error.size()));
		
		
		add(btnUpdate);
		btnUpdate.setBounds(7, 7, 154, 28);
		btnUpdate.addActionListener(keyUpdate);

		add(scrollParam);
		scrollParam.setViewportView(tableParam);
		scrollParam.setBounds(7, 42, 780, 95);
		add(scrollError);
		scrollError.setViewportView(tableError);
		scrollError.setBounds(7, 140, 780, 425);
		add(lblAvgResidual);
		lblAvgResidual.setBounds(231, 7, 126, 28);
		add(txtAvgResidual);
		txtAvgResidual.setBounds(357, 7, 147, 28);
		add(lblAvgError);
		lblAvgError.setBounds(511, 7, 126, 28);
		add(txtAvgError);
		txtAvgError.setBounds(637, 7, 147, 28);
	}
	
	private SerialListener keyUpdate = new SerialListener() {
		private static final long serialVersionUID = 3809688819591367994L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tableParam.getSelectedRow() != -1) {
				int[] indices = tableParam.getSelectedRows();
				for (int i=0; i < indices.length; i++)
					ppps.get(indices[i]).txt.setText((String) dtmParam.getValueAt(indices[i], 2));
			} else {
				for (int i=0; i < dtmParam.getRowCount(); i++)
					ppps.get(i).txt.setText((String) dtmParam.getValueAt(i, 2));
			}
		}
	};
}
