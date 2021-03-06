package javagulp.view.fit;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Sdlc extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -3600946579130559583L;

	private final G g = new G();

	private final JLabel lblConstant = new JLabel(g.html(g.epsilon + "<sub>s</sub>"));
	private final JLabel lblSdlci = new JLabel("i");
	private final JLabel lblWeight = new JLabel("weight");
	private final JLabel lblSdlcj = new JLabel("j");

	private final JTextField txtConstant = new JTextField();
	private final JTextField txtSdlcj = new JTextField();
	private final JTextField txtSdlci = new JTextField();
	private final JTextField txtSdlcWeight = new JTextField();

	//public String gulpFileLines;

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
	public String writeFitPanel() throws IncompleteOptionException {
		final JTextField[] fields = { txtSdlci, txtSdlcj, txtConstant };
		final String[] descriptions = { "i", "j", "the dielectric constant" };
		Back.checkAllNonEmpty(fields, descriptions);
		gulpFileLines = "sdlc " + Back.newLine + Back.concatFields(fields);
		if (!txtSdlcWeight.getText().equals(""))
			gulpFileLines += " " + txtSdlcWeight.getText();
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}

}
