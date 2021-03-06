package javagulp.view.potential.twocenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.model.GenericTableModel;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Spline extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 6413833934403141616L;

	private final G g = new G();

	private final JButton btnSet = new JButton("set");

	private final JComboBox cboSplineType = new JComboBox(new String[] { "rational", "cubic" });
	private final JComboBox cboUnits = new JComboBox(new String[] { "kjmol", "kcal" });

	private final GenericTableModel energyDistanceTableModel = new GenericTableModel(new String[] { "interatomic distance (ang)", "energy (eV)" }, 0);
	private final JTable energyDistanceTable = new JTable(energyDistanceTableModel);
	private final JScrollPane scrollPane3 = new JScrollPane(energyDistanceTable);

	private final JLabel lblNumberOfEnergies = new JLabel("number of energies");
	private final JLabel lblSplineType = new JLabel("spline type");
	private final JLabel lblUnits = new JLabel("units");

	private final PPP shift = new PPP("shift");

	private final JTextField txtNumEn = new JTextField();

	private final SerialListener keySet = new SerialListener() {
		private static final long serialVersionUID = -3780821019273150654L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				final int rowCount = Integer.parseInt(txtNumEn.getText());
				energyDistanceTableModel.setRowCount(rowCount);
			} catch (final NumberFormatException e1) {
				JOptionPane.showMessageDialog(null,
				"Please enter a positive integer");
			}
		}
	};

	public Spline() {
		super(2);
		setTitle("spline");
		enabled = new boolean[] { true, true, true, true, false, false };

		lblUnits.setBounds(394, 130, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(495, 130, 70, 21);
		add(cboUnits);
		scrollPane3.setBounds(24, 68, 346, 230);
		add(scrollPane3);
		cboSplineType.setBounds(383, 30, 92, 22);
		add(cboSplineType);
		lblSplineType.setBounds(307, 34, 70, 15);
		add(lblSplineType);
		shift.setBounds(515, 34, 225, 15);
		add(shift);
		shift.txt.setText("0.0");
		btnSet.setBounds(216, 31, 56, 20);
		add(btnSet);
		lblNumberOfEnergies.setBounds(21, 34, 125, 15);
		add(lblNumberOfEnergies);
		txtNumEn.setBounds(152, 32, 58, 20);
		add(txtNumEn);
		btnSet.addActionListener(keySet);
		radii = new Radii(true);
		radii.setBounds(394, 84, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{shift};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		if (energyDistanceTableModel.getRowCount() == 0)
			throw new IncompleteOptionException("Please enter one or more rows");
		Back.checkAndParseD(params);

		String lines = "spline ";
		if (cboSplineType.getSelectedIndex() == 1)
			lines += "cubic ";
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		lines += pot.twoAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += Back.newLine + getAtoms() + Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += radii.writeRadii();
		}
		lines += Back.writeFits(params) + " ";
		for (int i = 0; i < energyDistanceTableModel.getRowCount(); i++) {
			lines += energyDistanceTableModel.getValueAt(i, 1) + " "
			+ energyDistanceTableModel.getValueAt(i, 0) + " ";
		}
		return lines + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		final Spline s = new Spline();
		s.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		s.cboSplineType.setSelectedIndex(this.cboSplineType.getSelectedIndex());
		s.txtNumEn.setText(this.txtNumEn.getText());
		//TODO clone table
		return super.clone(s);
	}
}