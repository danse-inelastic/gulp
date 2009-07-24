package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ElasticConstant extends AbstractFit implements Serializable {

	private static final long serialVersionUID = 876873876083648134L;

	private final JLabel lblConstant = new JLabel("E(i,j) (GPa)");
	private final JLabel lblElastici = new JLabel("i");
	private final JLabel lblWeight = new JLabel("weight");
	private final JLabel lblElasticj = new JLabel("j");

	private final JTextField txtConstant = new JTextField();
	private final JTextField txtJ = new JTextField();
	private final JTextField txtI = new JTextField();
	private final JTextField txtWeight = new JTextField();

	//public String gulpFileLines;

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
		final JTextField[] fields = { txtI, txtJ, txtConstant };
		final String[] descriptions = { "i", "j", "the elastic constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		gulpFileLines = "elastic " + Back.newLine + Back.concatFields(fields);
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}