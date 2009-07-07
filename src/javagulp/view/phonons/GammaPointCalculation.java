package javagulp.view.phonons;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.TitledPanel;
import javax.swing.ButtonGroup;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GammaPointCalculation extends JPanel implements Serializable {

	private ButtonGroup btnGrpNonAnalyticCorrection = new ButtonGroup();
	private JRadioButton specifyDirectionOfRadioButton;
	private JRadioButton averageRadioButton;
	private TitledPanel pnlNonAnalyticCorrectionRadioButtons;
	private static final long serialVersionUID = -3092558433728257906L;

	private TitledPanel pnlFrequencyOptions = new TitledPanel("calculate frequency");

	private JLabel lblInitialFrequency = new JLabel("initial frequency");
	private JLabel lblfrequencystep = new JLabel("frequency step");
	private JLabel lblNumSteps = new JLabel("no. of steps");
	private JLabel lblFinalFrequency = new JLabel("final frequency");
	private JLabel lblFinalFreq = new JLabel("");
	private JLabel lblRelatedProperties = new JLabel("related properties over frequency range ");

	private JTextField txtomegafrequency = new JTextField();
	private JTextField txtomegafrequency_step = new JTextField();
	private JTextField txtomegano_of_steps = new JTextField();

	private TitledPanel pnlDirections = new TitledPanel("frequency-dependent in/out directions");

	private JLabel lblIn = new JLabel("in");
	private JLabel lblOut = new JLabel("out");
	private JComboBox cboCoordinates = new JComboBox(new String[] {"cartesian", "fractional" });

	private JTextField txtodirinx = new JTextField();
	private JTextField txtodiriny = new JTextField();
	private JTextField txtodirinz = new JTextField();
	private JTextField txtodiroutx = new JTextField();
	private JTextField txtodirouty = new JTextField();
	private JTextField txtodiroutz = new JTextField();

	private JTextField txtomega_damping = new JTextField();

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

	//	private FrequencyOptions pnlFrequencyOptions = new FrequencyOptions();
	private TitledPanel pnlFrequencyDamping = new TitledPanel();
	//	private Directions pnlDirections = new Directions();

	private G g = new G();

	private TitledPanel pnlAngularPoints = new TitledPanel();
	private GammaApproach pnlGammaApproach = new GammaApproach();

	// angular points fields
	private JLabel lblNumberOfAngular = new JLabel(g.html("Number of points for averaging the nonanalytic correction to the dynamical matrix"));
	private final String stepsnumDefault = "0";
	private JTextField txtgammastepsnum = new JTextField(stepsnumDefault);
	
	private JPanel pnlCorrections = new JPanel();
			
	public GammaPointCalculation() {
		super();
		setBorder(border);
		// border.setTitle("Gamma-point options");
		setLayout(null);
		pnlGammaApproach.setName("GammaApproach");
		pnlGammaApproach.setBounds(10, 10, 449, 180);

		lblInitialFrequency.setBounds(10, 39, 168, 14);
		lblfrequencystep.setBounds(10, 65, 168, 14);
		lblNumSteps.setBounds(10, 91, 168, 14);
		lblFinalFrequency.setBounds(10, 118, 168, 14);
		lblFinalFreq.setBorder(new LineBorder(Color.black, 1, false));
		lblFinalFreq.setBounds(195, 115, 59, 20);
		lblRelatedProperties.setBounds(10, 18, 295, 15);

		txtomegafrequency.setBounds(195, 37, 59, 20);
		txtomegafrequency_step.setBounds(195, 63, 59, 20);
		txtomegano_of_steps.setBounds(195, 89, 59, 20);

		pnlFrequencyOptions.add(lblInitialFrequency);
		pnlFrequencyOptions.add(lblfrequencystep);
		pnlFrequencyOptions.add(lblNumSteps);
		pnlFrequencyOptions.add(lblFinalFrequency);
		pnlFrequencyOptions.add(lblFinalFreq);
		pnlFrequencyOptions.add(lblRelatedProperties);

		pnlFrequencyOptions.add(txtomegafrequency_step);
		pnlFrequencyOptions.add(txtomegano_of_steps);
		pnlFrequencyOptions.add(txtomegafrequency);

		pnlFrequencyOptions.setBounds(10, 10, 315, 149);
		pnlFrequencyOptions.setTitle("calculate frequency");
		add(pnlFrequencyOptions);
		add(pnlFrequencyDamping);

		pnlFrequencyDamping.setTitle("frequency damping factor");
		pnlFrequencyDamping.setBounds(331, 10, 299, 53);
		pnlFrequencyDamping.add(txtomega_damping);
		txtomega_damping.setBounds(10, 23, 120, 20);

		txtodirinx.setBounds(36, 20, 30, 20);
		pnlDirections.add(txtodirinx);
		add(pnlDirections);
		txtodiriny.setBounds(71, 20, 30, 20);
		pnlDirections.add(txtodiriny);
		txtodirinz.setBounds(107, 20, 30, 20);
		pnlDirections.add(txtodirinz);
		txtodiroutx.setBounds(36, 45, 30, 20);
		pnlDirections.add(txtodiroutx);
		txtodirouty.setBounds(72, 45, 30, 20);
		pnlDirections.add(txtodirouty);
		txtodiroutz.setBounds(108, 45, 30, 20);
		pnlDirections.add(txtodiroutz);
		lblIn.setBounds(7, 19, 33, 23);
		pnlDirections.add(lblIn);
		lblOut.setBounds(7, 42, 33, 23);
		pnlDirections.add(lblOut);
		cboCoordinates.setBounds(147, 28, 122, 28);
		pnlDirections.add(cboCoordinates);
		pnlDirections.setTitle("frequency-dependent in/out directions");
		pnlDirections.setBounds(331, 69, 299, 90);
		
		pnlCorrections.setBounds(10, 229, 368, 180);
		pnlCorrections.setLayout(new CardLayout());

		
		pnlAngularPoints.setTitle("angular points");
		pnlAngularPoints.setName("AngularPoints");
		pnlAngularPoints.add(lblNumberOfAngular);
		pnlAngularPoints.add(txtgammastepsnum);
		lblNumberOfAngular.setBounds(10, 21, 348, 49);
		txtgammastepsnum.setBounds(10, 76, 102, 20);
		
		pnlCorrections.add(pnlAngularPoints, pnlAngularPoints.getName());
		pnlCorrections.add(pnlGammaApproach, pnlGammaApproach.getName());
		add(pnlCorrections);
		
		add(getPanel());

	}

	private void setFinalFrequency() {
		double no_of_steps = 0.0, start = 0.0, step = 0.0;
		if ((start != 0.0) && (step != 0.0) && (no_of_steps != 0.0)) {
			lblFinalFreq.setText(String.valueOf(start + no_of_steps * step));
		}
	}

	private String writeOmega() throws IncompleteOptionException,
	InvalidOptionException {
		String lines = "";
		if (!txtomegafrequency.getText().equals("")
				|| !txtomegafrequency_step.getText().equals("")
				|| !txtomegano_of_steps.getText().equals("")) {
			if (!txtomegafrequency.getText().equals("")
					&& !txtomegafrequency_step.getText().equals("")
					&& !txtomegano_of_steps.getText().equals("")) {
				try {
					double frequency = Double.parseDouble(txtomegafrequency.getText());
					double stepsize = Double.parseDouble(txtomegafrequency_step.getText());
					int steps = Integer.parseInt(txtomegano_of_steps.getText());
					if (frequency < 0)
						throw new InvalidOptionException("Phonon omega frequency must be >= 0");
					if (stepsize < 0)
						throw new InvalidOptionException("Phonon omega frequency increment must be >= 0");
					if (steps < 0)
						throw new InvalidOptionException("Phonon omega number of steps must be >= 0");
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Phonon omega frequency.");
				}
				lines = "omega " + txtomegafrequency.getText() + " "
				+ txtomegafrequency_step.getText() + " "
				+ txtomegano_of_steps.getText() + Back.newLine;
			} else {
				throw new IncompleteOptionException("Missing one or more Phonon omega frequency options.");
			}
		}
		return lines;
	}

	private String writeInOutDirections() throws IncompleteOptionException {
		String lines = "", type = "";
		if (cboCoordinates.getSelectedItem().toString().equals("fractional")) {
			type = "frac ";
		}
		if (!txtodirinx.getText().equals("")
				|| !txtodiriny.getText().equals("")
				|| !txtodirinz.getText().equals("")
				|| !txtodiroutx.getText().equals("")
				|| !txtodirouty.getText().equals("")
				|| !txtodiroutz.getText().equals("")) {
			if (!txtodirinx.getText().equals("")
					&& !txtodiriny.getText().equals("")
					&& !txtodirinz.getText().equals("")
					&& !txtodiroutx.getText().equals("")
					&& !txtodirouty.getText().equals("")
					&& !txtodiroutz.getText().equals("")) {
				// TODO check if > 0?
				lines = "odirection " + type + txtodirinx.getText() + " "
				+ txtodiriny.getText() + " " + txtodirinz.getText()
				+ " " + txtodiroutx.getText() + " "
				+ txtodirouty.getText() + " "
				+ txtodiroutz.getText() + Back.newLine;
			} else {
				throw new IncompleteOptionException("Missing one or more Phonon direction options.");
			}
		}
		return lines;
	}	
	
	private String writeOmegaDamping() throws InvalidOptionException {
		String line = "";
		if (!txtomega_damping.getText().equals("")) {
			try {
				double damping = Double.parseDouble(txtomega_damping.getText());
				if (damping < .000001)
					throw new InvalidOptionException("Phonon omega damping must be > .000001");
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Phonon omega damping.");
			}
			line = "omega_damping " + txtomega_damping.getText() + Back.newLine;
		}
		return line;
	}

	public String writeGammaPointOptions() throws IncompleteOptionException,
	InvalidOptionException {
		return writeInOutDirections()
		+ writeOmega() + writeOmegaDamping();
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
	/**
	 * @return
	 */
	protected TitledPanel getPanel() {
		if (pnlNonAnalyticCorrectionRadioButtons == null) {
			pnlNonAnalyticCorrectionRadioButtons = new TitledPanel();
			pnlNonAnalyticCorrectionRadioButtons.setTitle("nonanalytic correction to LO/TO splitting");
			pnlNonAnalyticCorrectionRadioButtons.setBounds(10, 165, 368, 58);
			pnlNonAnalyticCorrectionRadioButtons.add(getAverageRadioButton());
			pnlNonAnalyticCorrectionRadioButtons.add(getSpecifyDirectionOfRadioButton());
		}
		return pnlNonAnalyticCorrectionRadioButtons;
	}
	/**
	 * @return
	 */
	protected JRadioButton getAverageRadioButton() {
		if (averageRadioButton == null) {
			averageRadioButton = new JRadioButton();
			averageRadioButton.setSelected(true);
			averageRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (averageRadioButton.isSelected()){
						((CardLayout) pnlCorrections.getLayout()).show(pnlCorrections, pnlAngularPoints.getName());
					}
				}
			});
			btnGrpNonAnalyticCorrection.add(averageRadioButton);
			averageRadioButton.setText("average");
			averageRadioButton.setBounds(10, 25, 103, 23);
		}
		return averageRadioButton;
	}
	/**
	 * @return
	 */
	protected JRadioButton getSpecifyDirectionOfRadioButton() {
		if (specifyDirectionOfRadioButton == null) {
			specifyDirectionOfRadioButton = new JRadioButton();
			specifyDirectionOfRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (specifyDirectionOfRadioButton.isSelected()){
						((CardLayout) pnlCorrections.getLayout()).show(pnlCorrections, pnlGammaApproach.getName());
					}
				}
			});
			btnGrpNonAnalyticCorrection.add(specifyDirectionOfRadioButton);
			specifyDirectionOfRadioButton.setText("specify direction of approach");
			specifyDirectionOfRadioButton.setBounds(119, 25, 239, 23);
		}
		return specifyDirectionOfRadioButton;
	}


	
}