package javagulp.view.md;

import java.io.Serializable;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Temperature extends TitledPanel implements Serializable {
	private static final long serialVersionUID = 3138964864357670253L;
	
	private JLabel lblTemperature = new JLabel("temperature (K)");
	private JLabel lblIncrement = new JLabel("increment");
	private JLabel lblNumSteps = new JLabel("number of steps");
	private JLabel lblTimeStep = new JLabel("m");
	private JLabel txtTempScaling = new JLabel("<html>do not begin temperature <br>scaling until the mth time step</html>");

	private JTextField txtTemp = new JTextField();
	private JTextField txtIncrement = new JTextField();
	private JTextField txtNumSteps = new JTextField("0");
	public JTextField txtFirstStep = new JTextField();
	
	private int defaultTemp;

	public Temperature(int defaultTemp) {
		super();
		setTitle("temperature");
		this.defaultTemp = defaultTemp;
		txtTemp.setText("" + defaultTemp);
		
		lblTemperature.setBounds(10, 19, 136, 20);
		add(lblTemperature);
		txtTemp.setBounds(152, 20, 63, 19);
		add(txtTemp);
		lblIncrement.setBounds(10, 45, 136, 15);
		add(lblIncrement);
		txtIncrement.setBackground(Back.grey);
		txtIncrement.setBounds(152, 43, 63, 19);
		add(txtIncrement);
		txtNumSteps.setBackground(Back.grey);
		txtNumSteps.setBounds(152, 68, 63, 19);
		add(txtNumSteps);
		lblNumSteps.setBounds(10, 70, 136, 15);
		add(lblNumSteps);
		txtTempScaling.setBackground(Back.grey);
		txtTempScaling.setBounds(240, 15, 236, 43);
		add(txtTempScaling);
		txtFirstStep.setBackground(Back.grey);
		txtFirstStep.setBounds(272, 65, 46, 19);
		add(txtFirstStep);
		txtFirstStep.setEnabled(false);
		lblTimeStep.setBounds(240, 67, 26, 15);
		add(lblTimeStep);
	}

	public String writeTemperature() {
		String lines = "";
		JTextField[] fields = { txtTemp, txtIncrement, txtNumSteps, txtFirstStep };
		String[] descriptions = { "the temperature", "the increment", "the # of steps", "m" };
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
