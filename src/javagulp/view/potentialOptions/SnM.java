package javagulp.view.potentialOptions;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.KeywordListener;

public class SnM extends JPanel implements Serializable {
	// Who can take ___...
	private static final long serialVersionUID = -1793228478407003415L;

	private G g = new G();

	private JCheckBox chkStreitzAndMintmire = new JCheckBox("Streitz and Mintmire electronegativity equalization to determine charges");
	
	private JCheckBox chkchi = new JCheckBox("fit");
	private JCheckBox chkmu = new JCheckBox("fit");
	private JCheckBox chkzeta = new JCheckBox("fit");
	private JCheckBox chkZnuc = new JCheckBox("fit");

	private JLabel lblmuev = new JLabel("<html>&#956; (eV)</html>");
	private JLabel lblXev = new JLabel("<html>&#967; (eV)</html>");
	private JLabel lblAtom = new JLabel("atom");
	private JLabel lblzeta = new JLabel(g.html(g.zeta + " (" + g.ang + "<sup>-1</sup>)"));
	private JLabel lblznuc = new JLabel(g.html("Z<sub>nuc</sub> (au)"));

	private JTextField txtchi = new JTextField();
	private JTextField txtmu = new JTextField();
	private JTextField txtzeta = new JTextField();
	private JTextField txtZnuc = new JTextField();

	private DefaultComboBoxModel uniqueAtomListSM = new DefaultComboBoxModel();
	public JComboBox cbosmatom = new JComboBox(uniqueAtomListSM);

	private KeywordListener keyStreitzAndMintmire = new KeywordListener(chkStreitzAndMintmire, "sm");

	public SnM() {
		super();
		setLayout(null);

		chkStreitzAndMintmire.addActionListener(keyStreitzAndMintmire);
		chkStreitzAndMintmire.setBounds(5, 1, 478, 25);
		add(chkStreitzAndMintmire);
		
		cbosmatom.setBounds(56, 49, 66, 26);
		add(cbosmatom);
		lblzeta.setBounds(114, 85, 60, 15);
		add(lblzeta);
		lblznuc.setBounds(303, 85, 55, 20);
		add(lblznuc);
		txtchi.setBounds(180, 53, 61, 20);
		add(txtchi);
		txtmu.setBounds(364, 53, 61, 20);
		add(txtmu);
		txtzeta.setBounds(180, 73, 61, 20);
		add(txtzeta);
		txtZnuc.setBounds(364, 73, 61, 20);
		add(txtZnuc);
		lblXev.setBounds(139, 55, 35, 15);
		add(lblXev);
		lblmuev.setBounds(323, 55, 35, 15);
		add(lblmuev);
		chkchi.setBounds(247, 50, 40, 25);
		add(chkchi);
		chkzeta.setBounds(247, 80, 40, 25);
		add(chkzeta);
		chkmu.setBounds(431, 50, 40, 25);
		add(chkmu);
		chkZnuc.setBounds(431, 80, 40, 25);
		add(chkZnuc);
		lblAtom.setBounds(15, 55, 35, 15);
		add(lblAtom);
	}

	public String writeSmelectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (chkStreitzAndMintmire.isSelected()) {
			if (cbosmatom.getSelectedItem() == null || cbosmatom.getSelectedItem() == "")
				throw new IncompleteOptionException("Please enter an atom for mortiers electrostatics.");
			JTextField[] fields = {txtchi, txtmu, txtzeta, txtZnuc};
			String[] descriptions = {"Streitz and Mintmire chi", "Streitz and Mintmire mu", "Streitz and Mintmire zeta", "Streitz and Mintmire Znuc"};
			Back.checkAllNonEmpty(fields, descriptions);
			JCheckBox[] boxes = { chkchi, chkmu, chkzeta, chkZnuc };
			lines = "smelectronegativity " + cbosmatom.getSelectedItem() + " "
					+ Back.concatFields(fields) + Back.writeFits(boxes) + Back.newLine;
		}
		return lines;
	}
}