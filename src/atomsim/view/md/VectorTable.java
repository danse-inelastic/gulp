package javagulp.view.md;

import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VectorTable extends TitledPanel {

	private static final long serialVersionUID = -5890258072124019041L;

	private final JCheckBox chkStoreVectors = new JCheckBox("store a table of interatomic vectors to speed up calculation");

	private final KeywordListener keyStoreVectors = new KeywordListener(chkStoreVectors, "storevectors");

	private final JLabel lblUpdateFrequency = new JLabel("update the interatomic vector table every (steps)");
	private final JLabel lblExtraAmount = new JLabel("<html>extra amount to add to global cutoff<br> when deciding which vectors to store</html>");

	private final String TXT_RESET_VECTORS = "1";
	private final JTextField txtResetVectors = new JTextField(TXT_RESET_VECTORS);
	private final String TXT_EXTRA_CUTOFF = "0.0";
	private final JTextField txtExtraCutoff = new JTextField(TXT_EXTRA_CUTOFF);

	public VectorTable() {
		setTitle("vector table");
		chkStoreVectors.setBounds(8, 18, 458, 25);
		add(chkStoreVectors);
		chkStoreVectors.addActionListener(keyStoreVectors);
		lblUpdateFrequency.setBounds(10, 46, 371, 15);
		add(lblUpdateFrequency);
		txtResetVectors.setBounds(387, 44, 79, 20);
		add(txtResetVectors);
		lblExtraAmount.setToolTipText("This allows for atoms to move across the boundary between updates.");
		lblExtraAmount.setBounds(10, 65, 371, 35);
		add(lblExtraAmount);
		txtExtraCutoff.setBounds(387, 70, 79, 20);
		add(txtExtraCutoff);
	}

	public String writeResetvectors() {
		String lines = "";
		if (!txtResetVectors.getText().equals("")
				&& !txtResetVectors.getText().equals(TXT_RESET_VECTORS)) {
			try {
				Integer.parseInt(txtResetVectors.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter an integer for MD reset vectors.");
			}
			lines = "resetvectors " + txtResetVectors.getText() + Back.newLine;
		}
		return lines;
	}

	public String writeExtracutoff() {
		String lines = "";
		// units: angstroms
		if (!txtExtraCutoff.getText().equals("")
				&& !txtExtraCutoff.getText().equals(TXT_EXTRA_CUTOFF)) {
			try {
				Double.parseDouble(txtExtraCutoff.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for MD extra cutoff.");
			}
			lines = "extracutoff " + txtExtraCutoff.getText() + Back.newLine;
		}
		return lines;
	}
}