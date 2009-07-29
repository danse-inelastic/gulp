package javagulp.view.phonons;

import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel {

	private JTextField txtDos_2;
	private JTextField txtDos_1;
	private static final long serialVersionUID = -8425306576850279775L;


	private JTextField txtDos;
	private JTextField textField_1;

	public OutputFormats() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
		{
			final JCheckBox chkDos = new JCheckBox("write phonon DOS / dispersions (if any)");
			chkDos.setBounds(10, 23, 395, 22);
			add(chkDos);
		}
		{
			txtDos = new JTextField();
			txtDos.setBounds(411, 24, 260, 21);
			add(txtDos);
			txtDos.setColumns(10);
		}
		{
			final JCheckBox chckbxWriteDlpolyHistory = new JCheckBox("write energy and force constants for QM/MM");
			chckbxWriteDlpolyHistory.setBounds(10, 107, 395, 22);
			add(chckbxWriteDlpolyHistory);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(411, 108, 260, 21);
			add(textField_1);
		}

		{
			final JCheckBox chkDos = new JCheckBox();
			chkDos.setText("write frequencies for Gruneisen parameter");
			chkDos.setBounds(10, 51, 395, 22);
			add(chkDos);
		}

		{
			txtDos_1 = new JTextField();
			txtDos_1.setColumns(10);
			txtDos_1.setBounds(411, 51, 260, 21);
			add(txtDos_1);
		}

		{
			final JCheckBox chkDos = new JCheckBox();
			chkDos.setText("write oscillator strengths for phonon modes");
			chkDos.setBounds(10, 79, 395, 22);
			add(chkDos);
		}

		{
			txtDos_2 = new JTextField();
			txtDos_2.setColumns(10);
			txtDos_2.setBounds(411, 78, 260, 21);
			add(txtDos_2);
		}
	}
	

	public String writeOutputFormats() {
		String lines = "output phonon dos.dens" + Back.newLine;
		
//		if (!txtWrite.getText().equals("")) {
//			if (cboUnits.getSelectedItem().equals("timesteps")) {
//				try {
//					Integer.parseInt(txtWrite.getText());
//				} catch (final NumberFormatException nfe) {
//					throw new NumberFormatException("Please enter an integer for MD status write frequency.");
//				}
//				lines = "write " + txtWrite.getText() + Back.newLine;
//			} else {
//				try {
//					Double.parseDouble(txtWrite.getText());
//				} catch (final NumberFormatException nfe) {
//					throw new NumberFormatException("Please enter a number for MD status write frequency.");
//				}
//				lines = "write " + txtWrite.getText() + " "
//				+ cboUnits.getSelectedItem() + Back.newLine;
//			}
//		}
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
