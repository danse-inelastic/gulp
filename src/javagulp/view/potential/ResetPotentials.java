package javagulp.view.potential;

import java.io.Serializable;

import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ResetPotentials extends TitledPanel implements Serializable {

	private static final long serialVersionUID = 3973999468513730548L;

	private final G g = new G();

	private final JLabel lblNewMaxCutoff = new JLabel(g.html("new max cutoff (must be less than normal cutoff) (" + g.ang + ")"));
	private final JLabel lblTaperForm = new JLabel("taper form");
	private final JLabel lblTaperRange = new JLabel(g.html("taper range (" + g.ang + ")"));
	private final JTextField txtNewMaxCutoff = new JTextField();
	private final JTextField txtTaperRange = new JTextField();
	private final String[] taperForms = { "polynomial", "cosine", "voter" };
	private final JComboBox cboTaperForm = new JComboBox(taperForms);

	public ResetPotentials() {
		super();
		setTitle("reset interatomic potential cutoffs");
		setBounds(0, 55, 375, 115);

		txtNewMaxCutoff.setBounds(281, 29, 84, 20);
		add(txtNewMaxCutoff);
		lblNewMaxCutoff.setBounds(7, 22, 268, 33);
		add(lblNewMaxCutoff);
		lblTaperForm.setBounds(8, 57, 110, 23);
		add(lblTaperForm);
		txtTaperRange.setBackground(Back.grey);
		txtTaperRange.setBounds(160, 87, 84, 20);
		add(txtTaperRange);
		lblTaperRange.setBounds(7, 89, 147, 15);
		add(lblTaperRange);
		cboTaperForm.setBounds(124, 58, 120, 21);
		add(cboTaperForm);
	}

	public String writeCutp() {
		String lines = "";
		if (!txtNewMaxCutoff.getText().equals("")) {
			Double.parseDouble(txtNewMaxCutoff.getText());
			lines += "cutp " + txtNewMaxCutoff.getText() + " "
			+ cboTaperForm.getSelectedItem();
			if (!txtTaperRange.getText().equals("")) {
				lines += " " + txtTaperRange.getText();
			}
			lines += Back.newLine;
		}
		return lines;
	}
}
