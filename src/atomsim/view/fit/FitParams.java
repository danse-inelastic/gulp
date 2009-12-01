package javagulp.view.fit;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javagulp.model.SerialListener;
import javagulp.view.potential.PPP;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FitParams extends JFrame implements Serializable {
	private static final long serialVersionUID = 6614798981469880622L;

	public JButton btnUpdate = new JButton("Update Parameters");

	private final String[] colsParam = new String[] { "Parameter Type",
			"Initial Value", "Final Value", "% Change"};
	private final String[] colsError = new String[] { "Observable", "Calculated",
			"Residual", "Error %" };

	private final JScrollPane scrollParam = new JScrollPane();
	public DefaultTableModel dtmParam;
	private final JTable tableParam;

	private final JScrollPane scrollError = new JScrollPane();
	public DefaultTableModel dtmError;
	private final JTable tableError;

	private final JTextField txtAvgError = new JTextField();
	private final JTextField txtAvgResidual = new JTextField();

	private final JLabel lblAvgError = new JLabel("Average % Error");
	private final JLabel lblAvgResidual = new JLabel("Average Residual");

	public double[] params;

	private final ArrayList<PPP> ppps;

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

		final DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(15);
		params = new double[newParams.size()];
		for (int i=0; i < oldParams.size(); i++) {
			Scanner sc = new Scanner(oldParams.get(i));
			sc.next();
			final String oldValue = sc.next();
			final String type = sc.nextLine();
			sc.close();
			sc = new Scanner(newParams.get(i));
			sc.next();
			final String newValue = sc.next();
			params[i] = Double.parseDouble(newValue);

			dtmParam.setValueAt(type, i, 0);
			dtmParam.setValueAt(oldValue, i, 1);
			dtmParam.setValueAt(newValue, i, 2);
			final double a = Math.abs(Double.parseDouble(oldValue));
			final double b = Math.abs(Double.parseDouble(newValue));
			dtmParam.setValueAt(Math.abs((a-b)/a) * 100.0, i, 3);
			//TODO check % change formula
		}
		double residualTotal = 0, errorTotal = 0;
		for (int i=0; i < error.size(); i++) {
			final Scanner scan = new Scanner(error.get(i));
			scan.next();
			scan.next();
			final double observable = scan.nextDouble();
			final double calculated = scan.nextDouble();
			scan.close();
			final double difference = calculated-observable;
			dtmError.setValueAt(observable, i, 0);
			dtmError.setValueAt(calculated, i, 1);
			//Calculate (rather than use gulp's output) residual and error % for more accuracy.
			final double residual = difference*difference;
			final double errorPercent = difference/observable;
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

	private final SerialListener keyUpdate = new SerialListener() {
		private static final long serialVersionUID = 3809688819591367994L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tableParam.getSelectedRow() != -1) {
				final int[] indices = tableParam.getSelectedRows();
				for (final int indice : indices)
					ppps.get(indice).txt.setText((String) dtmParam.getValueAt(indice, 2));
			} else {
				for (int i=0; i < dtmParam.getRowCount(); i++)
					ppps.get(i).txt.setText((String) dtmParam.getValueAt(i, 2));
			}
		}
	};
}
