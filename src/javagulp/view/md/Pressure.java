package javagulp.view.md;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JTextField;

public class Pressure extends TitledPanel {

	private static final long serialVersionUID = 7736994189677354195L;

	private final String TXT_PRESSURE = "0.0";
	private final JTextField txtPressure = new JTextField(TXT_PRESSURE);

	public Pressure() {
		setTitle("pressure (GPa)");
		txtPressure.setBounds(10, 19, 98, 19);
		add(txtPressure);
	}

	public String writePressure() {
		String lines = "";
		if (!txtPressure.getText().equals("")
				&& !txtPressure.getText().equals(TXT_PRESSURE)) {
			try {
				Double.parseDouble(txtPressure.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for MD pressure.");
			}
			lines = "pressure " + txtPressure.getText() + Back.newLine;
		}
		return lines;
	}
}