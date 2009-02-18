package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Qeq extends JPanel implements Serializable {

	private static final long serialVersionUID = 7473602267117789109L;

	private G g = new G();

	private JLabel lblmuev = new JLabel("<html>&#956; (eV)</html>");
	private JLabel lblXev = new JLabel("<html>&#967; (eV)</html>");
	private JLabel lblAtom = new JLabel("atom");
	private JLabel lblMaximumIterations = new JLabel("maximum number of iterations");
	private JLabel lblMaximumRadius = new JLabel(g.html("maximum radius for slater-type coloumb calculation; <br>becomes inverse distance beyond this (ang)"));
	private JLabel lblToleranceOfCharge = new JLabel("tolerance of charge change between iterations (electrons)");

	private JTextField txtMaxNumOfIterations = new JTextField("20");
	private final String radiusDefault = "15.0";
	private JTextField txtradius = new JTextField(radiusDefault);
	private final String tolDefault = "0.000001";
	private JTextField txttol = new JTextField(tolDefault);
	private JTextField txtchi = new JTextField();
	private JTextField txtmu = new JTextField();

	private DefaultComboBoxModel uniqueAtomList = new DefaultComboBoxModel();
	public JComboBox cboatom = new JComboBox(uniqueAtomList);

	private JCheckBox chkchi = new JCheckBox("fit");
	private JCheckBox chkmu = new JCheckBox("fit");
	private JCheckBox chkqeq = new JCheckBox("use QEQ electronegativity equalization to determine charges");
	
	private KeywordListener keyqeq = new KeywordListener(chkqeq, "qeq");

	public Qeq() {
		super();
		setLayout(null);

		chkqeq.addActionListener(keyqeq);
		chkqeq.setBounds(5, 1, 419, 25);
		add(chkqeq);
		lblToleranceOfCharge.setBounds(14, 89, 360, 15);
		add(lblToleranceOfCharge);
		txttol.setBounds(380, 87, 87, 20);
		add(txttol);
		lblMaximumRadius.setBounds(14, 110, 359, 37);
		add(lblMaximumRadius);
		txtradius.setBounds(380, 113, 87, 20);
		add(txtradius);
		lblMaximumIterations.setBounds(14, 153, 195, 15);
		add(lblMaximumIterations);
		txtMaxNumOfIterations.setBounds(216, 153, 66, 20);
		add(txtMaxNumOfIterations);
		cboatom.setBounds(55, 52, 64, 26);
		add(cboatom);
		txtchi.setBounds(182, 56, 61, 20);
		add(txtchi);
		txtmu.setBackground(Back.grey);
		txtmu.setBounds(360, 56, 61, 20);
		add(txtmu);
		chkchi.setBounds(249, 53, 40, 25);
		add(chkchi);
		chkmu.setBounds(427, 53, 40, 25);
		add(chkmu);
		lblXev.setBounds(139, 58, 35, 15);
		add(lblXev);
		lblmuev.setBounds(315, 58, 35, 15);
		add(lblmuev);
		lblAtom.setBounds(14, 58, 35, 15);
		add(lblAtom);
	}

	private String writeQeqradius() throws InvalidOptionException {
		String line = "";
		if (!txtradius.getText().equals("")
				&& !txtradius.getText().equals(radiusDefault)) {
			try {
				double tolerance = Double.parseDouble(txtradius.getText());
				if (tolerance < 0)
					throw new InvalidOptionException("Please enter a number > 0 for Electrostatics qeq radius.");
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number > 0 for Electrostatics qeq radius.");
			}
			line = "qeqradius " + txtradius.getText() + Back.newLine;
		}
		return line;
	}

	private String writeQeqtol() throws InvalidOptionException {
		String line = "";
		if (!txttol.getText().equals("")
				&& !txttol.getText().equals(tolDefault)) {
			try {
				double tolerance = Double.parseDouble(txttol.getText());
				if (tolerance < 0)
					throw new InvalidOptionException("Please enter a small number > 0 for Electrostatics qeq tolerance.");
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a small number > 0 for Electrostatics qeq tolerance.");
			}
			line = "qeqtol " + txttol.getText() + Back.newLine;
		}
		return line;
	}

	private String writeQelectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (chkqeq.isSelected()) {
			if (cboatom.getSelectedItem() == null || cboatom.getSelectedItem() == "")
				throw new IncompleteOptionException("Please enter an atom for qeq electrostatics.");
			if (txtchi.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for chi in qeq electrostatics.");
			Double.parseDouble(txtchi.getText());
			lines = "qelectronegativity " + cboatom.getSelectedItem() + " " + txtchi.getText();
			if (!txtmu.getText().equals("")) {
				lines += " " + txtmu.getText();
				Double.parseDouble(txtmu.getText());
			}
			JCheckBox[] boxes = {chkchi, chkmu};
			lines += Back.writeFits(boxes) + Back.newLine;
		}
		return lines;
	}

	public String writeQeq() throws InvalidOptionException, IncompleteOptionException {
		return writeQeqtol() + writeQeqradius() + writeQelectronegativity();
	}
}