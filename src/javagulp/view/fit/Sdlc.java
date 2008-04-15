package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

import utility.misc.G;

public class Sdlc extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -3600946579130559583L;

	private G g = new G();

	private JLabel lblConstant = new JLabel(g.html(g.epsilon + "<sub>s</sub>"));
	private JLabel lblSdlci = new JLabel("i");
	private JLabel lblWeight = new JLabel("weight");
	private JLabel lblSdlcj = new JLabel("j");

	private JTextField txtConstant = new JTextField();
	private JTextField txtSdlcj = new JTextField();
	private JTextField txtSdlci = new JTextField();
	private JTextField txtSdlcWeight = new JTextField();

	public Sdlc() {
		super();
		setLayout(null);

		txtSdlci.setBounds(28, 21, 28, 21);
		add(txtSdlci);

		lblSdlci.setBounds(14, 21, 7, 21);
		add(lblSdlci);

		txtSdlcj.setBounds(105, 21, 28, 21);
		add(txtSdlcj);

		txtConstant.setBounds(245, 21, 70, 21);
		add(txtConstant);

		lblWeight.setBounds(350, 21, 49, 21);
		add(lblWeight);

		add(lblSdlcj);
		lblSdlcj.setBounds(91, 14, 7, 28);

		add(lblConstant);
		lblConstant.setBounds(175, 14, 63, 28);

		txtSdlcWeight.setBackground(Back.grey);
		txtSdlcWeight.setBounds(406, 21, 70, 21);
		add(txtSdlcWeight);
	}

	@Override
	public String writeFit() throws IncompleteOptionException {
		JTextField[] fields = { txtSdlci, txtSdlcj, txtConstant };
		String[] descriptions = { "i", "j", "the dielectric constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		String lines = "sdlc " + Back.concatFields(fields);
		if (!txtSdlcWeight.getText().equals(""))
			lines += " " + txtSdlcWeight.getText();
		return lines+ Back.newLine;
	}
	
}
