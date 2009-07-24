package javagulp.view.md;

import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class TimeLengths extends JPanel {

	private static final long serialVersionUID = 7793603346713580397L;
	private final JTextField txtTimestep = new JTextField();//"0.001");
	private final JTextField txtSample = new JTextField();//"0.01");
	private final JTextField txtProduction = new JTextField();//"100");
	private final JTextField txtEquilibration = new JTextField();//"0");

	private final JLabel lblTimestep = new JLabel("timestep length (ps)");
	private final JLabel lblEquilibrationTime = new JLabel("equilibration time (ps)");
	private final JLabel lblProductionTime = new JLabel("production time (ps)");
	private final JLabel lblSampleFrequency = new JLabel("sample frequency (ps)");

	public TimeLengths() {
		setBorder(new TitledBorder(null, "time lengths",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
		txtTimestep.setBounds(189, 19, 63, 19);
		add(txtTimestep);
		lblTimestep.setBounds(10, 21, 173, 15);
		add(lblTimestep);
		lblEquilibrationTime.setBounds(10, 42, 173, 15);
		add(lblEquilibrationTime);
		lblEquilibrationTime.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
		lblProductionTime.setBounds(10, 63, 173, 15);
		add(lblProductionTime);
		lblProductionTime.setToolTipText("Specifies the simulation time to be spent collecting production data for subsequent analysis.");
		lblSampleFrequency.setBounds(10, 84, 173, 15);
		add(lblSampleFrequency);
		lblSampleFrequency.setToolTipText("Controls how often the properties of the molecular dynamics run are to be sampled and output to the standard output channel. Averaged properties are also based on these samples.");
		txtSample.setBounds(189, 82, 63, 19);
		add(txtSample);
		txtProduction.setBounds(189, 61, 63, 19);
		add(txtProduction);
		txtEquilibration.setBounds(189, 40, 63, 19);
		add(txtEquilibration);
		txtEquilibration.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
	}

	public String writeTime() {
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
		} catch (final NumberFormatException nfe) {
			throw new NumberFormatException("Please enter numbers for MD time lengths.");
		}
		return lines;
	}
}
