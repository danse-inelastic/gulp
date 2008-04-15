package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ElasticConstant extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 876873876083648134L;

	private JLabel lblConstant = new JLabel("E(i,j) (GPa)");
	private JLabel lblElastici = new JLabel("i");
	private JLabel lblWeight = new JLabel("weight");
	private JLabel lblElasticj = new JLabel("j");

	private JTextField txtConstant = new JTextField();
	private JTextField txtJ = new JTextField();
	private JTextField txtI = new JTextField();
	private JTextField txtWeight = new JTextField();

	public ElasticConstant() {
		super();

		txtI.setBounds(28, 21, 28, 21);
		add(txtI);
		lblElastici.setBounds(14, 21, 7, 21);
		add(lblElastici);
		txtJ.setBounds(105, 21, 28, 21);
		add(txtJ);
		txtConstant.setBounds(245, 21, 70, 21);
		add(txtConstant);
		lblWeight.setBounds(350, 21, 49, 21);
		add(lblWeight);
		add(lblElasticj);
		lblElasticj.setBounds(91, 14, 7, 28);
		add(lblConstant);
		lblConstant.setBounds(175, 14, 63, 28);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(406, 21, 70, 21);
		add(txtWeight);
	}

	@Override
	public String writeFit() throws IncompleteOptionException {
		JTextField[] fields = { txtI, txtJ, txtConstant };
		String[] descriptions = { "i", "j", "the elastic constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		String lines = "elastic " + Back.concatFields(fields);
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			lines += " " + txtWeight.getText();
		}
		return lines + Back.newLine;
	}
	
}