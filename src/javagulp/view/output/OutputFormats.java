package javagulp.view.output;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel {

	private JTextField txtXyzFile;
	private JCheckBox chckbxXyzFile;
	private JCheckBox chckbxDerivatives;
	private JTextField txtDerivatives;
	private JTextField txtCif;
	private JCheckBox chckbxCif;
	private JTextField txtMarvin;
	private static final long serialVersionUID = -8425306576850279775L;



	private JTextField txtSiesta;
	private JTextField txtCrystal98;
	private final JCheckBox chkSiesta = new JCheckBox("write SIESTA (.fdf) input file");
	private final JCheckBox chckbxCrystal98 = new JCheckBox("write CRYSTAL98 (.str) input file");
	private final JCheckBox chckbxMarvin = new JCheckBox();

	public OutputFormats() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		chkSiesta.setBounds(10, 24, 278, 22);
		add(chkSiesta);

		txtSiesta = new JTextField();
		txtSiesta.setBounds(364, 25, 206, 21);
		add(txtSiesta);
		txtSiesta.setColumns(10);

		chckbxCrystal98.setBounds(10, 52, 278, 22);
		add(chckbxCrystal98);

		txtCrystal98 = new JTextField();
		txtCrystal98.setColumns(10);
		txtCrystal98.setBounds(364, 53, 206, 21);
		add(txtCrystal98);

		chckbxMarvin.setText("write Marvin input file");
		chckbxMarvin.setBounds(10, 108, 278, 22);
		add(chckbxMarvin);

		txtMarvin = new JTextField();
		txtMarvin.setColumns(10);
		txtMarvin.setBounds(364, 109, 206, 21);
		add(txtMarvin);

		chckbxCif = new JCheckBox();
		chckbxCif.setText("write cif file");
		chckbxCif.setBounds(10, 80, 278, 22);
		add(chckbxCif);

		txtCif = new JTextField();
		txtCif.setColumns(10);
		txtCif.setBounds(364, 80, 206, 21);
		add(txtCif);

		txtDerivatives = new JTextField();
		txtDerivatives.setColumns(10);
		txtDerivatives.setBounds(364, 136, 206, 21);
		add(txtDerivatives);

		chckbxDerivatives = new JCheckBox();
		chckbxDerivatives.setText("write energy and derivatives for QM/MM");
		chckbxDerivatives.setBounds(10, 136, 348, 22);
		add(chckbxDerivatives);

		chckbxXyzFile = new JCheckBox();
		chckbxXyzFile.setText("write final configuration file in xyz format");
		chckbxXyzFile.setBounds(10, 164, 348, 22);
		add(chckbxXyzFile);

		txtXyzFile = new JTextField();
		txtXyzFile.setColumns(10);
		txtXyzFile.setBounds(364, 163, 206, 21);
		add(txtXyzFile);

	}

	public String writeOutputFormats() throws IncompleteOptionException {
		String lines = "";
		lines += writeLabeledCheckbox(chkSiesta, txtSiesta, "fdf", "Please enter a Siesta input filename");
		lines += writeLabeledCheckbox(chckbxCrystal98, txtCrystal98, "str", "Please enter a crystal 98 input filename");
		lines += writeLabeledCheckbox(chckbxCif, txtCif, "cif", "Please enter a cif filename");
		lines += writeLabeledCheckbox(chckbxMarvin, txtMarvin, "marvin", "Please enter a Marvin filename");
		lines += writeLabeledCheckbox(chckbxDerivatives, txtDerivatives, "drv", "Please enter a QM/MM energy and derivatives filename");
		lines += writeLabeledCheckbox(chckbxXyzFile, txtXyzFile, "xyz", "Please enter an xyz configuration filename");
		//		if (chkSiesta.isSelected() && txtSiesta.getText().equals(""))
		//			throw new IncompleteOptionException("Please enter a Siesta input filename");
		//		if (chkSiesta.isSelected()) {
		//			lines += "output fdf "
		//			+ txtSiesta.getText() + Back.newLine;
		//		}
		//		if (chckbxCrystal98.isSelected() && txtCrystal98.getText().equals(""))
		//			throw new IncompleteOptionException("Please enter a crystal 98 input file name");
		//		if (chckbxCrystal98.isSelected()) {
		//			lines += "output str "
		//			+ txtCrystal98.getText() + Back.newLine;
		//		}
		//		if (chckbxCif.isSelected() && txtCif.getText().equals(""))
		//			throw new IncompleteOptionException("Please enter a cif file name");
		//		if (chckbxMarvin.isSelected()) {
		//			lines += "output cif "
		//			+ txtMarvin.getText() + Back.newLine;
		//		}
		//		if (chckbxMarvin.isSelected() && txtMarvin.getText().equals(""))
		//			throw new IncompleteOptionException("Please enter a Marvin file name");
		//		if (chckbxMarvin.isSelected()) {
		//			lines += "output marvin "
		//			+ txtMarvin.getText() + Back.newLine;
		//		}
		return lines;
	}

	private String writeLabeledCheckbox(JCheckBox jCheckBox, JTextField jTextField, String format, String warning) throws IncompleteOptionException {
		String line = "";
		if (jCheckBox.isSelected() && jTextField.getText().equals(""))
			throw new IncompleteOptionException(warning);
		if (jCheckBox.isSelected()) {
			line = "output "+format+" "
			+ jTextField.getText() + Back.newLine;
		}
		return line;
	}

}
