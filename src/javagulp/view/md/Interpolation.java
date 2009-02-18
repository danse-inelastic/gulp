package javagulp.view.md;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

	class Interpolation extends TitledPanel {

		private static final long serialVersionUID = -1545757803614526779L;

		private JTextField txtPotentialInterpolation = new JTextField();

		private JLabel lblUse = new JLabel("use");
		private JLabel lblInterpolationPoints = new JLabel("interpolation points");
		private JLabel lblAccelerate = new JLabel("(i.e. 100000) to accelerate the calculation");

		Interpolation() {
			setTitle("potential interpolation");
			txtPotentialInterpolation.setBounds(41, 21, 85, 20);
			add(txtPotentialInterpolation);
			lblUse.setBounds(10, 21, 25, 20);
			add(lblUse);
			lblInterpolationPoints.setBounds(132, 21, 125, 15);
			add(lblInterpolationPoints);
			lblAccelerate.setBounds(10, 47, 255, 15);
			add(lblAccelerate);
		}

		String writePotentialInterpolation() {
			String lines = "";
			// integer, typically > 100000
			if (!txtPotentialInterpolation.getText().equals("")) {
				try {
					Integer.parseInt(txtPotentialInterpolation.getText());
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a large integer for MD potential interpolation.");
				}
				lines = "potential_interpolation "
						+ txtPotentialInterpolation.getText() + Back.newLine;
			}
			return lines;
		}
	}
