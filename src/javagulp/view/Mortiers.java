package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Mortiers extends JPanel implements Serializable {

	private static final long serialVersionUID = 4691139004878029021L;

	private G g = new G();

	private JLabel lblmuev = new JLabel(g.html(g.mu + " (eV)"));
	private JLabel lblchiev = new JLabel(g.html(g.chi + " (eV)"));
	private JLabel lblAtom = new JLabel("atom");

	private JTextField txtchi = new JTextField();
	private JTextField txtmu = new JTextField();

	private JCheckBox chkchi = new JCheckBox("fit");
	private JCheckBox chkmu = new JCheckBox("fit");
	private JCheckBox chkMortiers = new JCheckBox("use Mortiers electronegativity equalization to determine charges");

	public JComboBox cboeematom = new JComboBox();

	private KeywordListener keyMortiers = new KeywordListener(chkMortiers, "eem");
	
	public Mortiers() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(469, 105));

		lblchiev.setBounds(141, 56, 35, 15);
		add(lblchiev);
		txtchi.setBounds(183, 54, 61, 20);
		add(txtchi);
		lblmuev.setBounds(314, 56, 35, 15);
		add(lblmuev);
		txtmu.setBackground(Back.grey);
		txtmu.setBounds(355, 54, 61, 18);
		add(txtmu);
		chkchi.setBounds(249, 51, 40, 25);
		add(chkchi);
		chkmu.setBounds(423, 51, 40, 25);
		add(chkmu);
		lblAtom.setBounds(15, 56, 35, 15);
		add(lblAtom);
		cboeematom.setBounds(56, 50, 64, 26);
		add(cboeematom);

		chkMortiers.addActionListener(keyMortiers);
		chkMortiers.setBounds(5, 1, 430, 25);
		add(chkMortiers);
	}

	public String writeElectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (chkMortiers.isSelected()) {
			if (cboeematom.getSelectedItem() == null || cboeematom.getSelectedItem() == "")
				throw new IncompleteOptionException("Please enter an atom for mortiers electrostatics.");
			if (txtchi.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for chi in mortiers electrostatics.");
			Double.parseDouble(txtchi.getText());
			lines = "electronegativity " + cboeematom.getSelectedItem() + " " + txtchi.getText();
			if (!txtmu.getText().equals("")) {
				lines += " " + txtmu.getText();
				Double.parseDouble(txtmu.getText());
			}
			JCheckBox[] boxes = {chkchi, chkmu};
			lines += Back.writeFits(boxes) + Back.newLine;
		}
		return lines;
	}
}