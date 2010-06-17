package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class SRefractiveIndex extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -4036905312114577631L;

	private final JTextField txtConstant = new JTextField();
	private final JTextField txtiValue = new JTextField();
	private final JTextField txtWeight = new JTextField();

	private final JLabel lblConstant = new JLabel("static refractive index");
	private final JLabel lbli = new JLabel("i");
	private final JLabel lblWeight = new JLabel("weight");

	//public String gulpFileLines;

	public SRefractiveIndex() {
		super();
		setLayout(null);

		txtiValue.setBounds(30, 20, 25, 20);
		add(txtiValue);

		lbli.setBounds(12, 20, 12, 20);
		add(lbli);

		txtConstant.setBounds(279, 20, 69, 20);
		add(txtConstant);

		lblConstant.setBounds(98, 21, 168, 21);
		add(lblConstant);

		lblWeight.setBounds(12, 70, 43, 20);
		add(lblWeight);

		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(61, 70, 72, 20);
		add(txtWeight);
	}

	// TODO check the format
	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		final JTextField[] fields = { txtiValue, txtConstant };
		final String[] descriptions = { "i", "the refractive index" };
		Back.checkAllNonEmpty(fields, descriptions);
		gulpFileLines = Back.concatFields(fields);
		if (!txtWeight.getText().equals(""))
			gulpFileLines += " " + txtWeight.getText();
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}
