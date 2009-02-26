package javagulp.view.phonons;

import java.awt.Color;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GammaPointOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -3092558433728257906L;

	private class FrequencyOptions extends TitledPanel {

		private static final long serialVersionUID = 5841559513554068929L;

		private JLabel lblInitialFrequency = new JLabel("initial frequency");
		private JLabel lblfrequencystep = new JLabel("frequency step");
		private JLabel lblNumSteps = new JLabel("no. of steps");
		private JLabel lblFinalFrequency = new JLabel("final frequency");
		private JLabel lblFinalFreq = new JLabel("");
		private JLabel lblRelatedProperties = new JLabel("related properties over frequency range ");

		private JTextField txtomegafrequency = new JTextField();
		private JTextField txtomegafrequency_step = new JTextField();
		private JTextField txtomegano_of_steps = new JTextField();

		private FrequencyOptions() {
			super();

			setTitle("Calculate frequency");

			lblInitialFrequency.setBounds(10, 39, 168, 14);
			lblfrequencystep.setBounds(10, 59, 168, 14);
			lblNumSteps.setBounds(10, 79, 168, 14);
			lblFinalFrequency.setBounds(10, 99, 168, 14);
			lblFinalFreq.setBorder(new LineBorder(Color.black, 1, false));
			lblFinalFreq.setBounds(195, 99, 59, 15);
			lblRelatedProperties.setBounds(10, 18, 355, 15);

			txtomegafrequency.setBounds(195, 37, 59, 18);
			txtomegafrequency_step.setBounds(195, 57, 59, 18);
			txtomegano_of_steps.setBounds(195, 77, 59, 18);

			add(lblInitialFrequency);
			add(lblfrequencystep);
			add(lblNumSteps);
			add(lblFinalFrequency);
			add(lblFinalFreq);
			add(lblRelatedProperties);

			add(txtomegafrequency_step);
			add(txtomegano_of_steps);
			add(txtomegafrequency);
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
	}

	private class Directions extends TitledPanel {

		private static final long serialVersionUID = -1752558655230250694L;

		private JLabel lblIn = new JLabel("in");
		private JLabel lblOut = new JLabel("out");
		private JComboBox cboCoordinates = new JComboBox(new String[] {
				"cartesian", "fractional" });

		private JTextField txtodirinx = new JTextField();
		private JTextField txtodiriny = new JTextField();
		private JTextField txtodirinz = new JTextField();
		private JTextField txtodiroutx = new JTextField();
		private JTextField txtodirouty = new JTextField();
		private JTextField txtodiroutz = new JTextField();

		private Directions() {
			super();

			setTitle("frequency-dependent in/out directions");
			txtodirinx.setBounds(36, 20, 30, 20);
			add(txtodirinx);
			txtodiriny.setBounds(71, 20, 30, 20);
			add(txtodiriny);
			txtodirinz.setBounds(107, 20, 30, 20);
			add(txtodirinz);
			txtodiroutx.setBounds(36, 45, 30, 20);
			add(txtodiroutx);
			txtodirouty.setBounds(72, 45, 30, 20);
			add(txtodirouty);
			txtodiroutz.setBounds(108, 45, 30, 20);
			add(txtodiroutz);
			lblIn.setBounds(7, 19, 33, 23);
			add(lblIn);
			lblOut.setBounds(7, 42, 33, 23);
			add(lblOut);
			cboCoordinates.setBounds(147, 28, 98, 28);
			add(cboCoordinates);
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
	}

	private JTextField txtomega_damping = new JTextField();

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

	private JTabbedPane paneGammaPointOptions = new JTabbedPane();
	private FrequencyOptions pnlFrequencyOptions = new FrequencyOptions();
	private TitledPanel pnlFrequencyDamping = new TitledPanel();
	private Directions pnlDirections = new Directions();

	public GammaPointOptions() {
		super();
		setBorder(border);
		// border.setTitle("Gamma-point options");
		setLayout(null);

		add(paneGammaPointOptions);
		paneGammaPointOptions.setBounds(10, 29, 344, 194);
		paneGammaPointOptions.add(pnlFrequencyOptions, "frequency range");
		paneGammaPointOptions.add(pnlFrequencyDamping, "damping");
		paneGammaPointOptions.add(pnlDirections, "in/out directions");

		pnlFrequencyOptions.setBounds(291, 11, 413, 76);

		pnlFrequencyDamping.setTitle("frequency damping factor");
		pnlFrequencyDamping.setBounds(659, 201, 194, 47);
		pnlFrequencyDamping.add(txtomega_damping);
		txtomega_damping.setBounds(9, 19, 77, 20);

		pnlDirections.setBounds(291, 146, 352, 75);
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
		return pnlDirections.writeInOutDirections()
				+ pnlFrequencyOptions.writeOmega() + writeOmegaDamping();
	}
}