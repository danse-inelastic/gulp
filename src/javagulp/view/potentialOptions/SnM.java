package javagulp.view.potentialOptions;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SnM extends JPanel implements Serializable {
	// Who can take ___...
	private static final long serialVersionUID = -1793228478407003415L;

	private final G g = new G();


	private final JCheckBox chkchi = new JCheckBox("fit");
	private final JCheckBox chkmu = new JCheckBox("fit");
	private final JCheckBox chkzeta = new JCheckBox("fit");
	private final JCheckBox chkZnuc = new JCheckBox("fit");

	private final JLabel lblmuev = new JLabel("<html>&#956; (eV)</html>");
	private final JLabel lblXev = new JLabel("<html>&#967; (eV)</html>");
	private final JLabel lblAtom = new JLabel("atom");
	private final JLabel lblzeta = new JLabel(g.html(g.zeta + " (" + g.ang + "<sup>-1</sup>)"));
	private final JLabel lblznuc = new JLabel(g.html("Z<sub>nuc</sub> (au)"));

	private final JTextField txtchi = new JTextField();
	private final JTextField txtmu = new JTextField();
	private final JTextField txtzeta = new JTextField();
	private final JTextField txtZnuc = new JTextField();

	private final DefaultComboBoxModel uniqueAtomListSM = new DefaultComboBoxModel();
	public JComboBox cbosmatom = new JComboBox(uniqueAtomListSM);

	public SnM() {
		super();
		setLayout(null);



		cbosmatom.setBounds(56, 49, 78, 26);
		add(cbosmatom);
		lblzeta.setBounds(5, 126, 60, 15);
		add(lblzeta);
		lblznuc.setBounds(246, 126, 78, 20);
		add(lblznuc);
		txtchi.setBounds(90, 91, 78, 20);
		add(txtchi);
		txtmu.setBounds(330, 91, 78, 20);
		add(txtmu);
		txtzeta.setBounds(90, 124, 78, 20);
		add(txtzeta);
		txtZnuc.setBounds(330, 124, 78, 20);
		add(txtZnuc);
		lblXev.setBounds(10, 93, 60, 15);
		add(lblXev);
		lblmuev.setBounds(246, 93, 78, 15);
		add(lblmuev);
		chkchi.setBounds(174, 88, 51, 25);
		add(chkchi);
		chkzeta.setBounds(174, 121, 51, 25);
		add(chkzeta);
		chkmu.setBounds(414, 88, 51, 25);
		add(chkmu);
		chkZnuc.setBounds(414, 121, 51, 25);
		add(chkZnuc);
		lblAtom.setBounds(5, 55, 45, 15);
		add(lblAtom);
	}

	public String writeSmelectronegativity() throws IncompleteOptionException {
		String lines = "";
		if (Back.getCurrentRun().getElectrostatics().chkStreitzAndMintmire.isSelected()) {
			if (cbosmatom.getSelectedItem() == null || cbosmatom.getSelectedItem() == "")
				throw new IncompleteOptionException("Please enter an atom for mortiers electrostatics.");
			final JTextField[] fields = {txtchi, txtmu, txtzeta, txtZnuc};
			final String[] descriptions = {"Streitz and Mintmire chi", "Streitz and Mintmire mu", "Streitz and Mintmire zeta", "Streitz and Mintmire Znuc"};
			Back.checkAllNonEmpty(fields, descriptions);
			final JCheckBox[] boxes = { chkchi, chkmu, chkzeta, chkZnuc };
			lines = "smelectronegativity " + cbosmatom.getSelectedItem() + " "
			+ Back.concatFields(fields) + Back.writeFits(boxes) + Back.newLine;
		}
		return lines;
	}
}