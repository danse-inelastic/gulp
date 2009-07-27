package javagulp.view.phonons;

import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel {

	private static final long serialVersionUID = -8425306576850279775L;

	private final JLabel lblWriteFrequencyps = new JLabel("write md status every");

	private final JTextField txtWrite = new JTextField();

	private final JComboBox cboUnits = new JComboBox(new String[] { "ps", "ns",
			"fs", "s", "timesteps" });
	private JTextField textField;
	private JTextField textField_1;

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
			final JCheckBox chkXYZTrajectory = new JCheckBox("write xyz trajectory");
			chkXYZTrajectory.setBounds(7, 47, 163, 22);
			add(chkXYZTrajectory);
		}
		{
			textField = new JTextField();
			textField.setBounds(182, 48, 294, 21);
			add(textField);
			textField.setColumns(10);
		}
		{
			final JCheckBox chckbxWriteDlpolyHistory = new JCheckBox("write dlpoly history file");
			chckbxWriteDlpolyHistory.setBounds(7, 81, 198, 22);
			add(chckbxWriteDlpolyHistory);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(217, 82, 260, 21);
			add(textField_1);
		}
	}
	

	public String writeOutputFormats() {
		String lines = "output phonon dos.dens" + Back.newLine;
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
