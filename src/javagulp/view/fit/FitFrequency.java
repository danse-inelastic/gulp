package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FitFrequency extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 6388487079221677793L;

	private final JTextField txtConstant = new JTextField();
	private final JTextField txtCstFrequency = new JTextField();
	private final JTextField txtWeight = new JTextField("1.0");
	private final JTextField txtKPoint = new JTextField();

	private final G g = new G();

	private final JLabel lblConstant = new JLabel("mode position (in order of increasing frequency)");
	private final JLabel lblWeight = new JLabel("weight");
	private final JLabel lblCstFrequency = new JLabel(g.html("frequency (cm<sup>-1</sup>)"));
	private final JLabel lblKPoint = new JLabel("k point number");

	public String gulpFileLines = "";

	public FitFrequency() {
		super();

		lblConstant.setBounds(19, 19, 313, 20);
		add(lblConstant);
		lblWeight.setBounds(19, 72, 44, 25);
		add(lblWeight);
		txtConstant.setBounds(338, 19, 73, 20);
		add(txtConstant);
		txtWeight.setBounds(69, 75, 72, 20);
		add(txtWeight);
		lblCstFrequency.setBounds(19, 45, 293, 20);
		add(lblCstFrequency);
		txtCstFrequency.setBounds(131, 48, 73, 20);
		add(txtCstFrequency);
		add(lblKPoint);
		lblKPoint.setBounds(21, 105, 134, 28);
		add(txtKPoint);
		txtKPoint.setBounds(169, 112, 63, 21);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		if (txtCstFrequency.getText().equals(""))
			throw new IncompleteOptionException("Please enter the frequency");
		gulpFileLines += "frequency";
		if (!txtConstant.getText().equals(""))
			gulpFileLines += " " + txtConstant.getText();
		gulpFileLines += " " + txtCstFrequency.getText();
		if (!txtKPoint.getText().equals(""))
			gulpFileLines += " " + txtKPoint.getText();
		if (!txtWeight.getText().equals("")
				&& !txtWeight.getText().equals("1.0"))
			gulpFileLines += " " + txtWeight.getText();
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}