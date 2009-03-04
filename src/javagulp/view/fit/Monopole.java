package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Monopole extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 2328871287402381280L;

	private JTextField txtAtomNumber = new JTextField();
	private JTextField txtConstant = new JTextField();
	private JTextField txtWeight = new JTextField();

	private JLabel lblConstant = new JLabel("monopole charge (au)");
	private JLabel lblAtomNumber = new JLabel("atom number");
	private JLabel lblWeight = new JLabel("weight");

	public String gulpFileLines;

	public Monopole() {
		super();

		txtAtomNumber.setBounds(94, 20, 33, 20);
		add(txtAtomNumber);
		lblAtomNumber.setBounds(9, 20, 86, 20);
		add(lblAtomNumber);
		txtConstant.setBounds(288, 20, 69, 20);
		add(txtConstant);
		lblConstant.setBounds(133, 20, 149, 20);
		add(lblConstant);
		lblWeight.setBounds(224, 46, 43, 20);
		add(lblWeight);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(287, 49, 70, 21);
		add(txtWeight);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		JTextField[] fields = { txtAtomNumber, txtConstant };
		String[] descriptions = { "the atom number", "the monopole charge" };
		Back.checkAllNonEmpty(fields, descriptions);
		gulpFileLines = "monopoleq " + Back.concatFields(fields);
		if (!txtWeight.getText().equals(""))
			gulpFileLines += " " + txtWeight.getText();
		return gulpFileLines + Back.newLine;
	}
	
}
