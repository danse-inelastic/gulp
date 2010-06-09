package javagulp.view.phonons;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

class KpointsMesh extends TitledPanel {

	private static final long serialVersionUID = -109851516210611103L;

	private final JTextField txtshrinkix = new JTextField();
	private final JTextField txtshrinkiy = new JTextField();
	private final JTextField txtshrinkiz = new JTextField();

	private final JLabel lblX = new JLabel("x");
	private final JLabel lblY = new JLabel("y");
	private final JLabel lblZ = new JLabel("z");

	//private final G g = new G();
	private final JCheckBox chkDoNotUse = new JCheckBox("<html>do not use symmetry when reducing kpoints</html>");

	private final KeywordListener keyDoNotUse = new KeywordListener(chkDoNotUse, "noksymmetry");
	final boolean vnfMode = Back.getVnfmode();

	KpointsMesh() {
		super();

		txtshrinkix.setBounds(25, 10, 38, 20);
		add(txtshrinkix);
		txtshrinkiy.setBounds(84, 10, 38, 20);
		add(txtshrinkiy);
		txtshrinkiz.setBounds(150, 10, 38, 20);
		add(txtshrinkiz);
		lblX.setBounds(7, 9, 16, 23);
		add(lblX);
		lblY.setBounds(71, 9, 16, 23);
		add(lblY);
		lblZ.setBounds(128, 9, 16, 23);
		add(lblZ);
		chkDoNotUse.addActionListener(keyDoNotUse);
		if(vnfMode){
			chkDoNotUse.setSelected(true);
			chkDoNotUse.setEnabled(false);
		}
		chkDoNotUse.setBounds(7, 36, 372, 60);
		add(chkDoNotUse);
	}

	String writeShrink() throws IncompleteOptionException,
	InvalidOptionException {
		boolean state = chkDoNotUse.isSelected();
		Back.getKeys().putOrRemoveKeyword(state, "noksymmetry");
		String lines = "";
		if (!txtshrinkix.getText().equals("")
				|| !txtshrinkiy.getText().equals("")
				|| !txtshrinkiz.getText().equals("")) {
			if (!txtshrinkix.getText().equals("")
					&& !txtshrinkiy.getText().equals("")
					&& !txtshrinkiz.getText().equals("")) {
				try {
					final int shrinkix = Integer.parseInt(txtshrinkix.getText());
					final int shrinkiy = Integer.parseInt(txtshrinkiy.getText());
					final int shrinkiz = Integer.parseInt(txtshrinkiz.getText());
					if (shrinkix <= 0)
						throw new InvalidOptionException("Phonon mesh number x must be >= 0");
					if (shrinkiy <= 0)
						throw new InvalidOptionException("Phonon mesh number y must be >= 0");
					if (shrinkiz <= 0)
						throw new InvalidOptionException("Phonon mesh number z must be >= 0");
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for the Phonon mesh.");
				}
				lines = "shrink " + txtshrinkix.getText() + " "
				+ txtshrinkiy.getText() + " "
				+ txtshrinkiz.getText() + Back.newLine;
			} else {
				throw new IncompleteOptionException("Missing one or more Phonon mesh options.");
			}
		}
		return lines;
	}
}
