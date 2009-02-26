package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class HfRefractiveIndex extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -4747054786357938059L;

	private JTextField txtConstant = new JTextField();
	private JTextField txtiValue = new JTextField();
	private JTextField txtWeight = new JTextField();

	private JLabel lblConstant = new JLabel("high frequency refractive index");
	private JLabel lbli = new JLabel("i");
	private JLabel lblWeight = new JLabel("weight");

	public HfRefractiveIndex() {
		super();
		setLayout(null);

		txtiValue.setBounds(30, 20, 25, 20);
		add(txtiValue);

		lbli.setBounds(12, 20, 12, 20);
		add(lbli);

		txtConstant.setBounds(363, 21, 70, 21);
		add(txtConstant);

		lblConstant.setBounds(132, 21, 225, 21);
		add(lblConstant);

		lblWeight.setBounds(12, 70, 43, 20);
		add(lblWeight);

		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(61, 70, 72, 20);
		add(txtWeight);
	}

	// TODO check format
	@Override
	public String writeFit() throws IncompleteOptionException {
		JTextField[] fields = { txtiValue, txtConstant };
		String[] descriptions = { "i", "the refractive index" };
		Back.checkAllNonEmpty(fields, descriptions);
		String lines = Back.concatFields(fields);
		if (!txtWeight.getText().equals(""))
			lines += " " + txtWeight.getText();
		return lines + Back.newLine;
	}
	
}
