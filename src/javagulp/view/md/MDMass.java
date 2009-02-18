package javagulp.view.md;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

	public class MDMass extends TitledPanel implements Serializable {

		private static final long serialVersionUID = 3863537565231540679L;

		private JLabel lblAssignShells = new JLabel("shell/core mass ratio");
		private JLabel lblSpecies = new JLabel("species");

		private JTextField txtShellmassRatio = new JTextField();

		public JComboBox cboShellmassSpecies = new JComboBox();

		MDMass() {
			setTitle("MD with shells with mass");
			lblAssignShells.setBounds(10, 49, 135, 15);
			add(lblAssignShells);
			txtShellmassRatio.setBounds(151, 47, 88, 20);
			add(txtShellmassRatio);
			cboShellmassSpecies.setBounds(151, 18, 88, 25);
			add(cboShellmassSpecies);
			lblSpecies.setBounds(10, 23, 55, 15);
			add(lblSpecies);
		}

		String writeShellMassRatio() throws InvalidOptionException,
				IncompleteOptionException {
			String lines = "";
			// TODO check for incomplete options
			// TODO put choices in cboShellmassSpecies
			if (!txtShellmassRatio.getText().equals("")) {
				if (cboShellmassSpecies.getSelectedItem() == null
						|| cboShellmassSpecies.getSelectedItem().equals(""))
					throw new IncompleteOptionException("Please select a species for shell/core mass ratio.");
				try {
					double ratio = Double.parseDouble(txtShellmassRatio.getText());
					if (ratio < 0 || ratio > 1)
						throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for MD shell mass ratio.");
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for MD shell mass ratio.");
				}
				lines = "shellmass" + Back.newLine + cboShellmassSpecies.getSelectedItem()
						+ " " + txtShellmassRatio.getText() + Back.newLine;
			}
			return lines;
		}
	}
