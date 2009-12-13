package javagulp.view.md;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel {

	private JTextField txtPressure;
	private static final long serialVersionUID = -8425306576850279775L;

	private final JLabel lblWriteFrequencyps = new JLabel("write md status every");

	private final JTextField txtWrite = new JTextField();

	private final JComboBox cboUnits = new JComboBox(new String[] { "ps", "ns",
			"fs", "s", "timesteps" });
	private JTextField txtXyz;
	private JTextField txtHis;
	private final JCheckBox chkXYZTrajectory = new JCheckBox("write xyz trajectory");
	private final JCheckBox chckbxWriteDlpolyHistory = new JCheckBox("write dlpoly history file");
	private final JCheckBox chckbxWritePressure = new JCheckBox();

	public OutputFormats() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		lblWriteFrequencyps.setBounds(7, 21, 183, 14);
		add(lblWriteFrequencyps);
		txtWrite.setBounds(202, 18, 111, 21);
		add(txtWrite);
		cboUnits.setBounds(319, 18, 92, 21);
		add(cboUnits);
		{
			chkXYZTrajectory.setBounds(7, 47, 163, 22);
			add(chkXYZTrajectory);
		}
		{
			txtXyz = new JTextField();
			txtXyz.setBounds(182, 48, 294, 21);
			add(txtXyz);
			txtXyz.setColumns(10);
		}
		{
			chckbxWriteDlpolyHistory.setBounds(7, 81, 198, 22);
			add(chckbxWriteDlpolyHistory);
		}
		{
			txtHis = new JTextField();
			txtHis.setColumns(10);
			txtHis.setBounds(217, 82, 260, 21);
			add(txtHis);
		}

		{
			chckbxWritePressure.setText("write pressure file");
			chckbxWritePressure.setBounds(7, 115, 198, 22);
			add(chckbxWritePressure);
		}

		{
			txtPressure = new JTextField();
			txtPressure.setColumns(10);
			txtPressure.setBounds(216, 116, 260, 21);
			add(txtPressure);
		}
	}

	public String writeOutputFormats() throws IncompleteOptionException {
		String lines = "";
		if (!txtWrite.getText().equals("")) {
			if (cboUnits.getSelectedItem().equals("timesteps")) {
				try {
					Integer.parseInt(txtWrite.getText());
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter an integer for MD status write frequency.");
				}
				lines = "write " + txtWrite.getText() + Back.newLine;
			} else {
				try {
					Double.parseDouble(txtWrite.getText());
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for MD status write frequency.");
				}
				lines = "write " + txtWrite.getText() + " "
				+ cboUnits.getSelectedItem() + Back.newLine;
			}
		}
		if (chkXYZTrajectory.isSelected() && txtXyz.getText().equals(""))
			throw new IncompleteOptionException("Please enter an xyz output filename");
		if (chkXYZTrajectory.isSelected()) {
			lines += "output movie xyz "
				+ txtXyz.getText() + Back.newLine;
		}
		if (chckbxWriteDlpolyHistory.isSelected() && txtHis.getText().equals(""))
			throw new IncompleteOptionException("Please enter a history file name");
		if (chckbxWriteDlpolyHistory.isSelected()) {
			lines += "output his "
				+ txtHis.getText() + Back.newLine;
		}
		if (chckbxWritePressure.isSelected() && txtPressure.getText().equals(""))
			throw new IncompleteOptionException("Please enter a pressure file name");
		if (chckbxWritePressure.isSelected()) {
			lines += "output pressure "
				+ txtPressure.getText() + Back.newLine;
		}
		return lines;
	}

	//		public String writeMDarchive() {
	//			String lines = "";
	//			if (!txtMdarchive.getText().equals("")) {
	//				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
	//			}
	//			return lines;
	//		}
}
