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

	private G g = new G();

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

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
		
		pnlAngularPoints.setTitle("angular points");
		pnlAngularPoints.setBounds(10, 196, 449, 107);
		pnlAngularPoints.add(lblNumberOfAngular);
		pnlAngularPoints.add(txtgammastepsnum);
		lblNumberOfAngular.setBounds(10, 9, 306, 61);
		txtgammastepsnum.setBounds(10, 76, 102, 20);
		add(pnlAngularPoints);
		add(pnlGammaApproach);

		pnlGammaApproach.setBounds(10, 10, 449, 180);
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
				throw new NumberFormatException("Please enter an integer for Phonon gamma angular steps.");
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