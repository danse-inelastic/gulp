package javagulp.view.potentialOptions;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Mortiers extends JPanel implements Serializable {

	private static final long serialVersionUID = 4691139004878029021L;

	private final G g = new G();

	private final JLabel lblmuev = new JLabel(g.html(g.mu + " (eV)"));
	private final JLabel lblchiev = new JLabel(g.html(g.chi + " (eV)"));
	private final JLabel lblAtom = new JLabel("atom");

	private final JTextField txtchi = new JTextField();
	private final JTextField txtmu = new JTextField();

	private final JCheckBox chkchi = new JCheckBox("fit");
	private final JCheckBox chkmu = new JCheckBox("fit");


	public JComboBox cboeematom = new JComboBox();

	public Mortiers() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(469, 105));

		lblchiev.setBounds(141, 56, 54, 15);
		add(lblchiev);
		txtchi.setBounds(201, 54, 61, 20);
		add(txtchi);
		lblmuev.setBounds(336, 56, 52, 15);
		add(lblmuev);
		txtmu.setBackground(Back.grey);
		txtmu.setBounds(394, 54, 61, 18);
		add(txtmu);
		chkchi.setBounds(268, 51, 40, 25);
		add(chkchi);
		chkmu.setBounds(461, 51, 52, 25);
		add(chkmu);
		lblAtom.setBounds(15, 56, 35, 15);
		add(lblAtom);
		cboeematom.setBounds(56, 50, 79, 26);
		add(cboeematom);


	}

	public String writeElectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (Back.getCurrentRun().getElectrostatics().chkMortiers.isSelected()) {
			//			if (cboeematom.getSelectedItem() == null || cboeematom.getSelectedItem() == "")
			//				throw new IncompleteOptionException("Please enter an atom for mortiers electrostatics.");
			//			if (txtchi.getText().equals(""))
			//				throw new IncompleteOptionException("Please enter a value for chi in mortiers electrostatics.");

			if (!txtchi.getText().equals("")) {
				Double.parseDouble(txtchi.getText());
				lines = "electronegativity " + cboeematom.getSelectedItem() + " " + txtchi.getText();
				if (!txtmu.getText().equals("")) {
					lines += " " + txtmu.getText();
					Double.parseDouble(txtmu.getText());
				}
				final JCheckBox[] boxes = {chkchi, chkmu};
				lines += Back.writeFits(boxes) + Back.newLine;
			}
		}
		return lines;
	}
}