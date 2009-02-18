package javagulp.view.md;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

	class TimeLengths extends JPanel {

		private static final long serialVersionUID = 7793603346713580397L;
		private JTextField txtTimestep = new JTextField();
		private JTextField txtSample = new JTextField();
		private JTextField txtProduction = new JTextField();
		private JTextField txtEquilibration = new JTextField();

		private JLabel lblTimestep = new JLabel("timestep length (ps)");
		private JLabel lblEquilibrationTime = new JLabel("equilibration time (ps)");
		private JLabel lblProductionTime = new JLabel("production time (ps)");
		private JLabel lblSampleFrequency = new JLabel("sample frequency (ps)");

		TimeLengths() {
			setBorder(new TitledBorder(null, "time lengths",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			setLayout(null);
			txtTimestep.setBounds(156, 19, 63, 19);
			add(txtTimestep);
			lblTimestep.setBounds(10, 21, 130, 15);
			add(lblTimestep);
			lblEquilibrationTime.setBounds(10, 42, 140, 15);
			add(lblEquilibrationTime);
			lblEquilibrationTime.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
			lblProductionTime.setBounds(10, 63, 130, 15);
			add(lblProductionTime);
			lblProductionTime.setToolTipText("Specifies the simulation time to be spent collecting production data for subsequent analysis.");
			lblSampleFrequency.setBounds(10, 84, 140, 15);
			add(lblSampleFrequency);
			lblSampleFrequency.setToolTipText("Controls how often the properties of the molecular dynamics run are to be sampled and output to the standard output channel. Averaged properties are also based on these samples.");
			txtSample.setBounds(156, 82, 63, 19);
			add(txtSample);
			txtProduction.setBounds(156, 61, 63, 19);
			add(txtProduction);
			txtEquilibration.setBounds(156, 40, 63, 19);
			add(txtEquilibration);
			txtEquilibration.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
		}

		String writeTime() {
			// can be integer or float; see documentation
			// TODO add unit combo boxes like in OutputFormats
			String lines = "";
			try {
				if (!txtTimestep.getText().equals("")) {
					Double.parseDouble(txtTimestep.getText());
					lines += "timestep " + txtTimestep.getText() + Back.newLine;
				}
				if (!txtEquilibration.getText().equals("")) {
					Double.parseDouble(txtEquilibration.getText());
					lines += "equilibration " + txtEquilibration.getText()
							+ Back.newLine;
				}
				if (!txtProduction.getText().equals("")) {
					Double.parseDouble(txtProduction.getText());
					lines += "production " + txtProduction.getText() + Back.newLine;
				}
				if (!txtSample.getText().equals("")) {
					Double.parseDouble(txtSample.getText());
					lines += "sample " + txtSample.getText() + Back.newLine;
				}
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter numbers for MD time lengths.");
			}
			return lines;
		}
	}
