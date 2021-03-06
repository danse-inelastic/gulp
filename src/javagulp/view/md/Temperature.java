package javagulp.view.md;

import java.io.Serializable;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Temperature extends TitledPanel implements Serializable {
	private static final long serialVersionUID = 3138964864357670253L;

	private final JLabel lblTemperature = new JLabel("temperature (K)");
	private final JLabel lblIncrement = new JLabel("increment");
	private final JLabel lblNumSteps = new JLabel("number of steps");
	private final JLabel lblTimeStep = new JLabel("m");
	private final JLabel txtTempScaling = new JLabel("<html>do not begin temperature <br>scaling until the mth time step</html>");

	private final JTextField txtTemp = new JTextField();
	private final JTextField txtIncrement = new JTextField();
	private final JTextField txtNumSteps = new JTextField("0");
	public JTextField txtFirstStep = new JTextField();

	private final int defaultTemp;

	public Temperature(int defaultTemp) {
		super();
		setTitle("temperature");
		this.defaultTemp = defaultTemp;
		txtTemp.setText("" + defaultTemp);

		lblTemperature.setBounds(10, 19, 136, 20);
		add(lblTemperature);
		txtTemp.setBounds(152, 20, 108, 19);
		add(txtTemp);
		lblIncrement.setBounds(10, 45, 136, 15);
		add(lblIncrement);
		txtIncrement.setBackground(Back.grey);
		txtIncrement.setBounds(152, 43, 108, 19);
		add(txtIncrement);
		txtNumSteps.setBackground(Back.grey);
		txtNumSteps.setBounds(152, 68, 108, 19);
		add(txtNumSteps);
		lblNumSteps.setBounds(10, 70, 136, 15);
		add(lblNumSteps);
		txtTempScaling.setBackground(Back.grey);
		txtTempScaling.setBounds(10, 99, 235, 47);
		add(txtTempScaling);
		txtFirstStep.setBackground(Back.grey);
		txtFirstStep.setBounds(48, 158, 46, 19);
		add(txtFirstStep);
		txtFirstStep.setEnabled(false);
		lblTimeStep.setBounds(10, 158, 26, 15);
		add(lblTimeStep);
	}

	public String writeTemperature() {
		String lines = "";
		final JTextField[] fields = { txtTemp, txtIncrement, txtNumSteps, txtFirstStep };
		final String[] descriptions = { "the temperature", "the increment", "the # of steps", "m" };
		if (!txtTemp.getText().equals("") && !txtTemp.getText().equals("" + defaultTemp)) {
			Back.parseFieldsD(fields, descriptions);//NFE?
			lines += "temperature " + txtTemp.getText();
			if (!txtIncrement.getText().equals(""))
				lines += " " + txtIncrement.getText();
			if (!txtNumSteps.getText().equals("") && !txtNumSteps.getText().equals("0"))
				lines += " " + txtNumSteps.getText();
			if (!txtFirstStep.getText().equals(""))
				lines += " " + txtFirstStep.getText();
			lines += Back.newLine;
		}
		return lines;
	}
}
