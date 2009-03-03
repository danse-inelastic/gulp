package javagulp.view.phonons;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class DispersionOptions extends JPanel {

	private static final long serialVersionUID = 1389393007405803508L;

	private final String TXT_BROADEN_PEAKS = "2.0";
	private JTextField txtBroadenPeaks = new JTextField(TXT_BROADEN_PEAKS);
	private JTextField txtboxvalue = new JTextField();

	private JCheckBox chkBroadenPeaks = new JCheckBox("broaden the peaks by factor");

	private JComboBox cboBoxType = new JComboBox(new String[] {
			"dispersion curves", "density of states" });
	private JComboBox cboNumBoxes = new JComboBox(new String[] {
			"number of boxes", "box size (1/cm)" });

	private JLabel lblChangeThe = new JLabel("change the");
	private JLabel lblFor = new JLabel("for");

	public DispersionOptions() {
		super();
		setLayout(null);

		setBorder(new TitledBorder(null,
				"density of states and dispersion curve options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		add(txtBroadenPeaks);
		txtBroadenPeaks.setBackground(Back.grey);
		txtBroadenPeaks.setBounds(211, 23, 63, 20);
		chkBroadenPeaks.setBounds(3, 20, 202, 25);
		add(chkBroadenPeaks);
		lblChangeThe.setBounds(11, 80, 70, 15);
		add(lblChangeThe);
		cboNumBoxes.setBounds(87, 77, 139, 21);
		add(cboNumBoxes);
		lblFor.setBounds(9, 50, 20, 20);
		add(lblFor);
		cboBoxType.setBounds(37, 50, 139, 21);
		add(cboBoxType);
		txtboxvalue.setBounds(232, 78, 77, 19);
		add(txtboxvalue);
	}

	private String writeBox() throws IncompleteOptionException {
		String lines = "";
		if (!txtboxvalue.getText().equals("")) {
			try {
				Integer.parseInt(txtboxvalue.getText());
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Please enter an integer for the number of boxes in Phonons");
			}
			lines = "box " + cboBoxType.getSelectedItem() + " "
			+ cboNumBoxes.getSelectedItem() + " "
			+ txtboxvalue.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeBroaden() throws IncompleteOptionException,
	InvalidOptionException {
		String line = "";
		if (chkBroadenPeaks.isSelected()) {
			if (!txtBroadenPeaks.getText().equals("")
					&& !txtBroadenPeaks.getText().equals(TXT_BROADEN_PEAKS)) {
				try {
					double broaden = Double.parseDouble(txtBroadenPeaks.getText());
					// and > 1?
					if (broaden < 0)
						throw new InvalidOptionException("Phonon broadening must be greater than 0");
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Phonon broadening.");
				}
				line = "broaden_dos " + txtBroadenPeaks.getText() + Back.newLine;
			} else {
				throw new IncompleteOptionException("Please enter something for Phonon broadening.");
			}
		}
		return line;
	}

	public String writeOptions() throws IncompleteOptionException,
	InvalidOptionException {
		return writeBox() + writeBroaden();
	}
}
