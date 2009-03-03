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

		private JTextField txtshrinkix = new JTextField();
		private JTextField txtshrinkiy = new JTextField();
		private JTextField txtshrinkiz = new JTextField();

		private JLabel lblX = new JLabel("x");
		private JLabel lblY = new JLabel("y");
		private JLabel lblZ = new JLabel("z");

		private G g = new G();
		private JCheckBox chkDoNotUse = new JCheckBox("<html>do not use symmetry when reducing the number of kpoints</html>");

		private KeywordListener keyDoNotUse = new KeywordListener(chkDoNotUse, "noksymmetry");

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
			chkDoNotUse.setBounds(7, 36, 462, 56);
			add(chkDoNotUse);
		}

		String writeShrink() throws IncompleteOptionException,
				InvalidOptionException {
			String lines = "";
			if (!txtshrinkix.getText().equals("")
					|| !txtshrinkiy.getText().equals("")
					|| !txtshrinkiz.getText().equals("")) {
				if (!txtshrinkix.getText().equals("")
						&& !txtshrinkiy.getText().equals("")
						&& !txtshrinkiz.getText().equals("")) {
					try {
						int shrinkix = Integer.parseInt(txtshrinkix.getText());
						int shrinkiy = Integer.parseInt(txtshrinkiy.getText());
						int shrinkiz = Integer.parseInt(txtshrinkiz.getText());
						if (shrinkix <= 0)
							throw new InvalidOptionException("Phonon mesh number x must be >= 0");
						if (shrinkiy <= 0)
							throw new InvalidOptionException("Phonon mesh number y must be >= 0");
						if (shrinkiz <= 0)
							throw new InvalidOptionException("Phonon mesh number z must be >= 0");
					} catch (NumberFormatException nfe) {
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
