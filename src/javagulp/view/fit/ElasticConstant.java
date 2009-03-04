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

	public String gulpFileLines;

	public ElasticConstant() {
		super();

		txtI.setBounds(27, 21, 58, 21);
		add(txtI);
		lblElastici.setBounds(10, 21, 15, 21);
		add(lblElastici);
		txtJ.setBounds(112, 21, 58, 21);
		add(txtJ);
		txtConstant.setBounds(257, 21, 70, 21);
		add(txtConstant);
		lblWeight.setBounds(356, 21, 58, 21);
		add(lblWeight);
		add(lblElasticj);
		lblElasticj.setBounds(91, 17, 15, 28);
		add(lblConstant);
		lblConstant.setBounds(188, 17, 63, 28);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(420, 21, 70, 21);
		add(txtWeight);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		JTextField[] fields = { txtI, txtJ, txtConstant };
		String[] descriptions = { "i", "j", "the elastic constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		gulpFileLines = "elastic " + Back.concatFields(fields);
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		return gulpFileLines + Back.newLine;
	}
	
}