package javagulp.view;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RestartFile extends TitledPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821486223095885255L;
	private final JCheckBox chkAfterEvery = new JCheckBox("after every");
	private final JCheckBox chkOutputConstraints = new JCheckBox("output constraints");
	private final JCheckBox chkProduceRestartFile = new JCheckBox("produce fitting/optimization dumpfile");

	private final JLabel lblCycles = new JLabel("cycle(s)");

	private final JTextField txtDumpEvery = new JTextField("1");
	private final JTextField txtFort12 = new JTextField("fort.12");

	public RestartFile() {
		setTitle("restart file");
		setBounds(586, 7, 443, 121);
		txtFort12.setBackground(Back.grey);
		txtFort12.setBounds(304, 24, 118, 20);
		add(txtFort12);
		txtDumpEvery.setBackground(Back.grey);
		lblCycles.setBounds(236, 55, 53, 25);
		add(lblCycles);
		chkOutputConstraints.addActionListener(keyOutputConstraints);
		chkOutputConstraints.setBounds(67, 88, 173, 25);
		add(chkOutputConstraints);
		chkProduceRestartFile.setBounds(10, 21, 288, 25);
		add(chkProduceRestartFile);
		txtDumpEvery.setBounds(182, 58, 48, 20);
		add(txtDumpEvery);
		chkAfterEvery.setBounds(67, 52, 109, 30);
		add(chkAfterEvery);
	}

	private final KeywordListener keyOutputConstraints = new KeywordListener(chkOutputConstraints, "outcon");


	public String writeOption() {
		String lines = "";
		if (chkProduceRestartFile.isSelected()) {
			lines = "dump";
			if (chkAfterEvery.isSelected()
					&& !txtDumpEvery.getText().equals("1")) {
				Integer.parseInt(txtDumpEvery.getText());

				lines += " every " + txtDumpEvery.getText();
			}
			if (!txtFort12.getText().equals("fort.12")) {
				lines += " " + txtFort12.getText();
			}
			lines += Back.newLine;
		}
		return lines;
	}


}
