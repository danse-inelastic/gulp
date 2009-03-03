package javagulp.view.phonons;

import javagulp.view.Back;
import javagulp.view.KeywordListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class ExplicitKpoints extends JPanel {

	private JLabel kxKyKzLabel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private static final long serialVersionUID = -6773882634142073544L;

	private JTextField txtexplicitkx = new JTextField();
	private JTextField txtexplicitky = new JTextField();
	private JTextField txtexplicitkz = new JTextField();
	public final String TXT_EXPLICT_WEIGHT = "1.0";
	private JTextField txtexplicitWeight = new JTextField(TXT_EXPLICT_WEIGHT);

	private JLabel lblexplicitkx = new JLabel("<html>k<sub>x</sub></html>");
	private JLabel lblexplicitky = new JLabel("<html>k<sub>y</sub></html>");
	private JLabel lblexplicitkz = new JLabel("<html>k<sub>z</sub></html>");
	private JLabel lblWeight = new JLabel("weight");

	private JCheckBox chkKpointsAreFor = new JCheckBox("<html>kpoints are for centered cell rather than primitive</html>");

	private KeywordListener keyKpointsAreFor = new KeywordListener(chkKpointsAreFor, "kfull");

	ExplicitKpoints() {
		super();
		setLayout(null);

		txtexplicitkx.setBounds(33, 21, 63, 19);
		add(txtexplicitkx);
		txtexplicitky.setBounds(124, 21, 63, 19);
		add(txtexplicitky);
		txtexplicitkz.setBounds(215, 21, 63, 19);
		add(txtexplicitkz);
		lblexplicitkx.setBounds(11, 22, 16, 23);
		add(lblexplicitkx);
		lblexplicitky.setBounds(102, 22, 16, 23);
		add(lblexplicitky);
		lblexplicitkz.setBounds(193, 22, 16, 23);
		add(lblexplicitkz);
		txtexplicitWeight.setBackground(Back.grey);
		txtexplicitWeight.setBounds(329, 21, 48, 19);
		add(txtexplicitWeight);
		lblWeight.setBounds(284, 23, 45, 15);
		add(lblWeight);
		chkKpointsAreFor.addActionListener(keyKpointsAreFor);
		chkKpointsAreFor.setBounds(5, 242, 372, 39);
		add(chkKpointsAreFor);
		add(getScrollPane());
		add(getKxKyKzLabel());
	}

	String writeKpoints() {
		String lines = "";
		// TODO check documentation for proper format
		if (!txtexplicitkx.getText().equals("")
				&& !txtexplicitky.getText().equals("")
				&& !txtexplicitkz.getText().equals("")) {
			lines = "kpoints " + txtexplicitkx.getText() + " "
			+ txtexplicitky.getText() + " "
			+ txtexplicitkz.getText();
			if (!txtexplicitWeight.getText().equals("")
					&& !txtexplicitkx.getText().equals(TXT_EXPLICT_WEIGHT)) {
				lines += " " + txtexplicitWeight.getText();
			}
			lines += Back.newLine;
		}
		return lines;
	}
	/**
	 * @return
	 */
	protected JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	/**
	 * @return
	 */
	protected JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(11, 106, 340, 112);
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	/**
	 * @return
	 */
	protected JLabel getKxKyKzLabel() {
		if (kxKyKzLabel == null) {
			kxKyKzLabel = new JLabel();
			kxKyKzLabel.setText("kx ky kz weight (i.e. 0.15 0.54 0.34 0.1)");
			kxKyKzLabel.setBounds(11, 76, 340, 15);
		}
		return kxKyKzLabel;
	}
}
