package javagulp.view.structures;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class TwoDCellParameters extends JPanel {

	private final G g = new G();

	private static final long serialVersionUID = 6224150055887463745L;
	private final JTextField txtA = new JTextField();
	private final JTextField txtB = new JTextField();
	private final JTextField txtAlpha = new JTextField();

	private final JCheckBox chkA = new JCheckBox("fit");
	private final JCheckBox chkB = new JCheckBox("fit");
	private final JCheckBox chkAlpha = new JCheckBox("fit");

	private final JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
	private final JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
	private final JLabel lblAlpha = new JLabel(g.html(g.alpha + " (deg)"));

	public TwoDCellParameters() {
		super();

		setLayout(null);
		lblA.setBounds(4, 9, 45, 15);
		add(lblA);
		txtA.setBounds(55, 6, 57, 20);
		add(txtA);
		lblB.setBounds(4, 32, 45, 15);
		add(lblB);
		txtB.setBounds(55, 29, 57, 20);
		add(txtB);
		txtAlpha.setBounds(55, 53, 57, 20);
		add(txtAlpha);
		lblAlpha.setBounds(4, 53, 45, 15);
		add(lblAlpha);
		chkA.setBounds(118, 4, 40, 25);
		add(chkA);
		chkB.setBounds(118, 27, 40, 25);
		add(chkB);
		chkAlpha.setBounds(118, 51, 40, 25);
		add(chkAlpha);
	}

	public String writeScell() throws IncompleteOptionException {
		final JTextField[] fields = { txtA, txtB, txtAlpha };
		final String[] descriptions = { "a cell parameter", "b cell parameter",
		"alpha cell parameter" };
		boolean text = false;
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].getText().equals(""))
				text = true;
		}
		String lines = "";
		if (text) {
			Back.checkAllNonEmpty(fields, descriptions);
			Back.parseFieldsD(fields, descriptions);
			lines = Back.concatFields(fields) + " ";
		}
		final JCheckBox[] boxes = { chkA, chkB, chkAlpha };
		if (lines.equals(""))
			return "";
		else
			return "scell" + Back.newLine + lines + Back.writeFits(boxes) + Back.newLine;
	}
}
