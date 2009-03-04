package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Hfdlc extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -955875921962168869L;

	private G g = new G();

	private JLabel lblConstant = new JLabel(g.html(g.epsilon + "<sub>"
			+ g.infinity + "</sub>"));
	private JLabel lblI = new JLabel("i");
	private JLabel lblJ = new JLabel("j");
	private JLabel lblWeight = new JLabel("weight");

	private JTextField txtConstant = new JTextField();
	private JTextField txtI = new JTextField();
	private JTextField txtJ = new JTextField();
	private JTextField txtWeight = new JTextField();

	//public String gulpFileLines;

	public Hfdlc() {
		super();

		txtJ.setBounds(28, 21, 28, 21);
		add(txtJ);
		lblI.setBounds(14, 21, 7, 21);
		add(lblI);
		txtI.setBounds(105, 21, 28, 21);
		add(txtI);
		txtConstant.setBounds(245, 21, 70, 21);
		add(txtConstant);
		lblWeight.setBounds(350, 21, 49, 21);
		add(lblWeight);
		add(lblJ);
		lblJ.setBounds(91, 14, 7, 28);
		add(lblConstant);
		lblConstant.setBounds(175, 14, 63, 28);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(406, 21, 70, 21);
		add(txtWeight);
	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		JTextField[] fields = { txtJ, txtI, txtConstant };
		String[] descriptions = { "i", "j", "the dielectric constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		gulpFileLines = "hfdlc " + Back.newLine + Back.concatFields(fields);
		if (!txtWeight.getText().equals("")) {
			Double.parseDouble(txtWeight.getText());
			gulpFileLines += " " + txtWeight.getText();
		}
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}
	
}