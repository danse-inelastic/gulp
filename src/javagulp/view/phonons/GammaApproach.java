package javagulp.view.phonons;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

class GammaApproach extends TitledPanel {

	private static final long serialVersionUID = -6445256347926029723L;
	private JLabel lblkx = new JLabel("<html>k<sub>x</sub></html>");
	private JLabel lblky = new JLabel("<html>k<sub>y</sub></html>");
	private JLabel lblkz = new JLabel("<html>k<sub>z</sub></html>");
	private final String kxdefault = "1.0";
	private final String kydefault = "1.0";
	private final String kzdefault = "1.0";
	private JTextField txtkx = new JTextField(kxdefault);
	private JTextField txtky = new JTextField(kydefault);
	private JTextField txtkz = new JTextField(kzdefault);
	private G g = new G();
	private JLabel lblDirectionOfApproach = new JLabel(g.html("direction of approach to the gamma point for the calculation of the nonanalytic correction to the dynamical matrix due to the LO/TO splitting resulting from the electric field in the crystal. option allows the frequencies to be calculated for a particular crystal orientation."));


		GammaApproach() {
			super();
			setTitle("gamma point approach");
			//setName("GammaApproach");
			
			lblkx.setBounds(12, 130, 15, 25);
			txtkx.setBounds(33, 130, 50, 20);
			txtky.setBounds(110, 130, 50, 20);
			txtkz.setBounds(187, 130, 50, 20);
			lblky.setBounds(89, 130, 15, 25);
			lblkz.setBounds(166, 130, 15, 25);
			lblDirectionOfApproach.setBounds(6, 22, 342, 102);

			add(lblkx);
			add(lblky);
			add(lblkz);
			add(txtkx);
			add(txtky);
			add(txtkz);
			add(lblDirectionOfApproach);
		}

		String writeGammaApproach() throws IncompleteOptionException,
				InvalidOptionException {
			String lines = "", skx = txtkx.getText(), sky = txtky.getText(), skz = txtkz.getText();
			if ((!skx.equals("") && !skx.equals(kxdefault))
					|| (!sky.equals("") && !sky.equals(kydefault))
					|| (!skz.equals("") && !skz.equals(kzdefault))) {
				if (skx.equals("") || sky.equals("") || skz.equals(""))
					throw new IncompleteOptionException("Please enter something for phonon gamma approach coordinates");
				try {
					double kx = Double.parseDouble(txtkx.getText());
					double ky = Double.parseDouble(txtky.getText());
					double kz = Double.parseDouble(txtkz.getText());
					if (kx > 1 || kx < 0 || ky < 0 || ky > 1 || kz < 0 || kz > 1)
						throw new InvalidOptionException("Phonon gamma approach fractional coordinates must  be > 0 and < 1");
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Phonon gamma approach.");
				}
				lines = "gamma_direction_of_approach " + skx + " " + sky + " " + skz + Back.newLine;

			}
			return lines;
		}
	}
