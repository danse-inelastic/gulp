package javagulp.view.phonons;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class GammaPointCorrection extends JPanel implements Serializable {

	private static final long serialVersionUID = -647635381542740743L;

	private class GammaApproach extends TitledPanel {

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

		private GammaApproach() {
			super();

			lblkx.setBounds(12, 130, 15, 25);
			txtkx.setBounds(33, 130, 50, 20);
			txtky.setBounds(110, 130, 50, 20);
			txtkz.setBounds(187, 130, 50, 20);
			lblky.setBounds(89, 130, 15, 25);
			lblkz.setBounds(166, 130, 15, 25);
			lblDirectionOfApproach.setBounds(6, 4, 307, 120);

			add(lblkx);
			add(lblky);
			add(lblkz);
			add(txtkx);
			add(txtky);
			add(txtkz);
			add(lblDirectionOfApproach);
		}

		private String writeGammaApproach() throws IncompleteOptionException,
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

	private G g = new G();

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

	private JTabbedPane paneNonanalyticCorrection = new JTabbedPane();
	private TitledPanel pnlAngularPoints = new TitledPanel();
	private GammaApproach pnlGammaApproach = new GammaApproach();

	// angular points fields
	private JLabel lblNumberOfAngular = new JLabel(g.html("Number of points for averaging the nonanalytic correction to the dynamical matrix"));
	private final String stepsnumDefault = "0";
	private JTextField txtgammastepsnum = new JTextField(stepsnumDefault);

	public GammaPointCorrection() {
		super();
		setBorder(border);
		// border.setTitle("nonanalytic Gamma-point correction");
		setLayout(null);

		add(paneNonanalyticCorrection);
		paneNonanalyticCorrection.setBounds(8, 29, 326, 252);
		paneNonanalyticCorrection.add(pnlAngularPoints, "angular points");
		paneNonanalyticCorrection.add(pnlGammaApproach, "gamma point approach");

		pnlAngularPoints.setBounds(315, 156, 367, 64);
		pnlAngularPoints.add(lblNumberOfAngular);
		pnlAngularPoints.add(txtgammastepsnum);
		lblNumberOfAngular.setBounds(5, 9, 276, 52);
		txtgammastepsnum.setBounds(66, 41, 49, 20);

		pnlGammaApproach.setBounds(294, 117, 364, 104);
	}

	private String writeGammaAngularSteps() throws InvalidOptionException {
		String line = "";
		if (!txtgammastepsnum.getText().equals("")
				&& !txtgammastepsnum.getText().equals(stepsnumDefault)) {
			try {
				int steps = Integer.parseInt(txtgammastepsnum.getText());
				if (steps < 0)
					throw new InvalidOptionException("Phonon gamma angular steps must be > 0.");
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter an iteger for Phonon gamma angular steps.");
			}
			line = "gamma_angular_steps " + txtgammastepsnum.getText() + Back.newLine;
		}
		return line;
	}

	public String writeGammaPointCorrection() throws InvalidOptionException,
			IncompleteOptionException {
		return writeGammaAngularSteps() + pnlGammaApproach.writeGammaApproach();
	}
}