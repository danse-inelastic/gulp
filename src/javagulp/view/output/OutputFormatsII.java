package javagulp.view.output;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormatsII extends JPanel {

	private JTextField txtPressure;
	private static final long serialVersionUID = -8425306576850279775L;



	private JTextField txtXyz;
	private JTextField txtHis;
	private JCheckBox chkXYZTrajectory = new JCheckBox("write SIESTA input file");
	private JCheckBox chckbxWriteDlpolyHistory = new JCheckBox("write dlpoly history file");
	private JCheckBox chckbxWritePressure = new JCheckBox();

	public OutputFormatsII() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
		{
			chkXYZTrajectory.setBounds(10, 24, 251, 22);
			add(chkXYZTrajectory);
		}
		{
			txtXyz = new JTextField();
			txtXyz.setBounds(267, 25, 206, 21);
			add(txtXyz);
			txtXyz.setColumns(10);
		}
		{
			chckbxWriteDlpolyHistory.setBounds(10, 52, 198, 22);
			add(chckbxWriteDlpolyHistory);
		}
		{
			txtHis = new JTextField();
			txtHis.setColumns(10);
			txtHis.setBounds(267, 52, 206, 21);
			add(txtHis);
		}

		{
			chckbxWritePressure.setText("write pressure file");
			chckbxWritePressure.setBounds(7, 115, 198, 22);
			add(chckbxWritePressure);
		}

		{
			txtPressure = new JTextField();
			txtPressure.setColumns(10);
			txtPressure.setBounds(216, 116, 260, 21);
			add(txtPressure);
		}
	}

	public String writeOutputFormats() throws IncompleteOptionException {
		String lines = "";
		if (chkXYZTrajectory.isSelected() && txtXyz.getText().equals(""))
			throw new IncompleteOptionException("Please enter an xyz output filename");
		if (chkXYZTrajectory.isSelected()) {
			lines += "output movie xyz "
			+ txtXyz.getText() + Back.newLine;
		}
		if (chckbxWriteDlpolyHistory.isSelected() && txtHis.getText().equals(""))
			throw new IncompleteOptionException("Please enter a history file name");
		if (chckbxWriteDlpolyHistory.isSelected()) {
			lines += "output his "
			+ txtHis.getText() + Back.newLine;
		}
		if (chckbxWritePressure.isSelected() && txtPressure.getText().equals(""))
			throw new IncompleteOptionException("Please enter a pressure file name");
		if (chckbxWritePressure.isSelected()) {
			lines += "output pressure "
			+ txtPressure.getText() + Back.newLine;
		}
		return lines;
	}

	//		public String writeMDarchive() {
	//			String lines = "";
	//			if (!txtMdarchive.getText().equals("")) {
	//				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
	//			}
	//			return lines;
	//		}
}
