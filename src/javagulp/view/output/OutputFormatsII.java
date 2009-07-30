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

	private JCheckBox chckbxDerivatives;
	private JTextField txtDerivatives;
	private JTextField txtCif;
	private JCheckBox chckbxCif;
	private JTextField txtMarvin;
	private static final long serialVersionUID = -8425306576850279775L;



	private JTextField txtSiesta;
	private JTextField txtCrystal98;
	private JCheckBox chkSiesta = new JCheckBox("write SIESTA (.fdf) input file");
	private JCheckBox chckbxCrystal98 = new JCheckBox("write CRYSTAL98 (.str) input file");
	private JCheckBox chckbxMarvin = new JCheckBox();

	public OutputFormatsII() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
		{
			chkSiesta.setBounds(10, 24, 278, 22);
			add(chkSiesta);
		}
		{
			txtSiesta = new JTextField();
			txtSiesta.setBounds(364, 25, 206, 21);
			add(txtSiesta);
			txtSiesta.setColumns(10);
		}
		{
			chckbxCrystal98.setBounds(10, 52, 278, 22);
			add(chckbxCrystal98);
		}
		{
			txtCrystal98 = new JTextField();
			txtCrystal98.setColumns(10);
			txtCrystal98.setBounds(364, 53, 206, 21);
			add(txtCrystal98);
		}

		{
			chckbxMarvin.setText("write Marvin input file");
			chckbxMarvin.setBounds(10, 108, 278, 22);
			add(chckbxMarvin);
		}

		{
			txtMarvin = new JTextField();
			txtMarvin.setColumns(10);
			txtMarvin.setBounds(364, 109, 206, 21);
			add(txtMarvin);
		}

		{
			chckbxCif = new JCheckBox();
			chckbxCif.setText("write cif file");
			chckbxCif.setBounds(10, 80, 278, 22);
			add(chckbxCif);
		}

		{
			txtCif = new JTextField();
			txtCif.setColumns(10);
			txtCif.setBounds(364, 80, 206, 21);
			add(txtCif);
		}

		{
			txtDerivatives = new JTextField();
			txtDerivatives.setColumns(10);
			txtDerivatives.setBounds(364, 136, 206, 21);
			add(txtDerivatives);
		}

		{
			chckbxDerivatives = new JCheckBox();
			chckbxDerivatives.setText("write energy and derivatives for QM/MM");
			chckbxDerivatives.setBounds(10, 136, 348, 22);
			add(chckbxDerivatives);
		}
	}

	public String writeOutputFormats() throws IncompleteOptionException {
		String lines = "";
		if (chkSiesta.isSelected() && txtSiesta.getText().equals(""))
			throw new IncompleteOptionException("Please enter an xyz output filename");
		if (chkSiesta.isSelected()) {
			lines += "output movie xyz "
			+ txtSiesta.getText() + Back.newLine;
		}
		if (chckbxCrystal98.isSelected() && txtCrystal98.getText().equals(""))
			throw new IncompleteOptionException("Please enter a history file name");
		if (chckbxCrystal98.isSelected()) {
			lines += "output his "
			+ txtCrystal98.getText() + Back.newLine;
		}
		if (chckbxMarvin.isSelected() && txtMarvin.getText().equals(""))
			throw new IncompleteOptionException("Please enter a pressure file name");
		if (chckbxMarvin.isSelected()) {
			lines += "output pressure "
			+ txtMarvin.getText() + Back.newLine;
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
