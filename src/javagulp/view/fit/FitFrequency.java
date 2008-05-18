package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class FitFrequency extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 6388487079221677793L;

	private JTextField txtConstant = new JTextField();
	private JTextField txtCstFrequency = new JTextField();
	private JTextField txtWeight = new JTextField("1.0");
	private JTextField txtKPoint = new JTextField();

	private G g = new G();

	private JLabel lblConstant = new JLabel("mode position (in order of increasing frequency)");
	private JLabel lblWeight = new JLabel("weight");
	private JLabel lblCstFrequency = new JLabel(g.html("frequency (cm<sup>-1</sup>)"));
	private JLabel lblKPoint = new JLabel("k point number");

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
	public String writeFit() throws IncompleteOptionException {
		// TODO documentation is VERY ambiguous. This is most likely not
		// correct.
		String lines = "";
		if (txtCstFrequency.getText().equals(""))
			throw new IncompleteOptionException("Please enter the frequency");
		lines += "frequency";
		if (!txtConstant.getText().equals(""))
			lines += " " + txtConstant.getText();
		lines += " " + txtCstFrequency.getText();
		if (!txtKPoint.getText().equals(""))
			lines += " " + txtKPoint.getText();
		if (!txtWeight.getText().equals("")
				&& !txtWeight.getText().equals("1.0"))
			lines += " " + txtWeight.getText();
		lines += Back.newLine;
		return lines;
	}
	
}