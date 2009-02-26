package javagulp.view.md;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

	public class Interpolation extends TitledPanel {

		private static final long serialVersionUID = -1545757803614526779L;

		private JTextField txtPotentialInterpolation = new JTextField();

		private JLabel lblUse = new JLabel("use");
		private JLabel lblInterpolationPoints = new JLabel("interpolation points");
		private JLabel lblAccelerate = new JLabel("(i.e. 100000) to accelerate the calculation");

		public Interpolation() {
			setTitle("potential interpolation");
			txtPotentialInterpolation.setBounds(78, 22, 85, 20);
			add(txtPotentialInterpolation);
			lblUse.setBounds(10, 21, 62, 20);
			add(lblUse);
			lblInterpolationPoints.setBounds(169, 23, 172, 15);
			add(lblInterpolationPoints);
			lblAccelerate.setBounds(10, 47, 331, 15);
			add(lblAccelerate);
		}

		public String writePotentialInterpolation() {
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
