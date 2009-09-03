package javagulp.view.potentialOptions;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Qeq extends JPanel implements Serializable {

	private static final long serialVersionUID = 7473602267117789109L;

	private final G g = new G();

	private final JLabel lblmuev = new JLabel("<html>&#956; (eV)</html>");
	private final JLabel lblXev = new JLabel("<html>&#967; (eV)</html>");
	private final JLabel lblAtom = new JLabel("atom");
	private final JLabel lblMaximumIterations = new JLabel("maximum number of iterations");
	private final JLabel lblMaximumRadius = new JLabel(g.html("maximum radius for slater-type coloumb calculation; <br>becomes inverse distance beyond this (ang)"));
	private final JLabel lblToleranceOfCharge = new JLabel("tolerance of charge change between iterations (electrons)");

	private final JTextField txtMaxNumOfIterations = new JTextField("20");
	private final String radiusDefault = "15.0";
	private final JTextField txtradius = new JTextField(radiusDefault);
	private final String tolDefault = "0.000001";
	private final JTextField txttol = new JTextField(tolDefault);
	private final JTextField txtchi = new JTextField();
	private final JTextField txtmu = new JTextField();

	private final DefaultComboBoxModel uniqueAtomList = new DefaultComboBoxModel();
	public JComboBox cboatom = new JComboBox(uniqueAtomList);

	private final JCheckBox chkchi = new JCheckBox("fit");
	private final JCheckBox chkmu = new JCheckBox("fit");


	public Qeq() {
		super();
		setLayout(null);

		lblToleranceOfCharge.setBounds(14, 89, 420, 15);
		add(lblToleranceOfCharge);
		txttol.setBounds(440, 87, 87, 20);
		add(txttol);
		lblMaximumRadius.setBounds(14, 110, 420, 37);
		add(lblMaximumRadius);
		txtradius.setBounds(440, 111, 87, 20);
		add(txtradius);
		lblMaximumIterations.setBounds(14, 153, 265, 15);
		add(lblMaximumIterations);
		txtMaxNumOfIterations.setBounds(285, 153, 66, 20);
		add(txtMaxNumOfIterations);
		cboatom.setBounds(55, 52, 78, 26);
		add(cboatom);
		txtchi.setBounds(216, 56, 61, 20);
		add(txtchi);
		txtmu.setBackground(Back.grey);
		txtmu.setBounds(406, 56, 61, 20);
		add(txtmu);
		chkchi.setBounds(282, 53, 53, 25);
		add(chkchi);
		chkmu.setBounds(474, 53, 53, 25);
		add(chkmu);
		lblXev.setBounds(160, 58, 49, 15);
		add(lblXev);
		lblmuev.setBounds(341, 58, 59, 15);
		add(lblmuev);
		lblAtom.setBounds(14, 58, 35, 15);
		add(lblAtom);
	}

	private String writeQeqradius() throws InvalidOptionException {
		String line = "";
		if (!txtradius.getText().equals("")
				&& !txtradius.getText().equals(radiusDefault)) {
			try {
				final double tolerance = Double.parseDouble(txtradius.getText());
				if (tolerance < 0)
					throw new InvalidOptionException("Please enter a number > 0 for Electrostatics qeq radius.");
			} catch (final NumberFormatException nfe) {
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
				final double tolerance = Double.parseDouble(txttol.getText());
				if (tolerance < 0)
					throw new InvalidOptionException("Please enter a small number > 0 for Electrostatics qeq tolerance.");
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a small number > 0 for Electrostatics qeq tolerance.");
			}
			line = "qeqtol " + txttol.getText() + Back.newLine;
		}
		return line;
	}

	private String writeQelectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (Back.getCurrentRun().getElectrostatics().chkqeq.isSelected()) {
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
			final JCheckBox[] boxes = {chkchi, chkmu};
			lines += Back.writeFits(boxes) + Back.newLine;
		}
		return lines;
	}

	public String writeQeq() throws InvalidOptionException, IncompleteOptionException {
		return writeQeqtol() + writeQeqradius() + writeQelectronegativity();
	}
}