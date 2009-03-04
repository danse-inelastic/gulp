package javagulp.view.fit;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class Piezoelectric extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -1658508382738062180L;

	private G g = new G();

	private JTextField txtdValue = new JTextField();;
	private JTextField txtWeight = new JTextField();;

	private JComboBox cboiValue = new JComboBox(new String[] { "1", "2", "3",
			"4", "5", "6" });
	private JComboBox cboStressStrain = new JComboBox(new String[] { "stress",
			"strain" });
	private JComboBox cboXyz = new JComboBox(new String[] { "x", "y", "z" });

	private JPanel pnlEquationBackdrop = new JPanel();
	private JPanel pnlStressType = new JPanel();
	private JPanel pnlStrainType = new JPanel();
	private JPanel pnlLabelBackdrop = new JPanel();
	private JPanel pnlStrainTypeLabel = new JPanel();
	private JPanel pnlStressTypeLabel = new JPanel();

	private JLabel lbleEquation = new JLabel("<html>e<sub>&#945;i</sub> = &#8706;P<sub>&#945;</sub>/&#8706;"
					+ g.epsilon + "<sub>i</sub></html>");
	private JLabel lbldValue = new JLabel("<html>d<sub>&#945;i</sub></html>");
	private JLabel lbleValue = new JLabel("<html>e<sub>&#945;i</sub></html>");
	private JLabel lbldEquation = new JLabel(g.html("d<sub>" + g.alpha
			+ "i</sub> = " + g.part + "P<sub>" + g.alpha + "</sub>/" + g.part
			+ g.sigma + "<sub>i</sub>"));
	private JLabel lblConstant = new JLabel();
	private JLabel lbliValue = new JLabel("i");
	private JLabel lblWeight = new JLabel("weight");
	private JLabel lblAlpha = new JLabel(g.html(g.alpha));
	
	private SerialListener keyCombo = new SerialListener() {
				private static final long serialVersionUID = -7564113212285211444L;

				@Override
		public void actionPerformed(ActionEvent e) {
			if (cboStressStrain.getSelectedItem().equals("stress")) {
				((CardLayout) pnlEquationBackdrop.getLayout()).show(pnlEquationBackdrop, pnlStressType.getName());
				((CardLayout) pnlLabelBackdrop.getLayout()).show(pnlLabelBackdrop, pnlStressTypeLabel.getName());
			} else if (cboStressStrain.getSelectedItem().equals("strain")) {
				((CardLayout) pnlEquationBackdrop.getLayout()).show(pnlEquationBackdrop, pnlStrainType.getName());
				((CardLayout) pnlLabelBackdrop.getLayout()).show(pnlLabelBackdrop, pnlStrainTypeLabel.getName());
			}
		}
	};

	public String gulpFileLines = "";

	public Piezoelectric() {
		super();
		setLayout(null);

		lbliValue.setBounds(84, 63, 14, 21);
		add(lbliValue);
		txtdValue.setBounds(196, 58, 69, 20);
		add(txtdValue);
		lblConstant.setBounds(104, 20, 64, 20);
		add(lblConstant);
		lblWeight.setBounds(271, 58, 43, 20);
		add(lblWeight);
		txtWeight.setBackground(Back.grey);
		txtWeight.setBounds(322, 56, 70, 21);
		add(txtWeight);
		cboiValue.setBounds(93, 58, 44, 21);
		add(cboiValue);
		lblAlpha.setBounds(7, 63, 21, 21);
		add(lblAlpha);
		cboXyz.setBounds(35, 56, 42, 21);
		add(cboXyz);

		pnlEquationBackdrop.setLayout(new CardLayout());
		pnlEquationBackdrop.setBounds(133, 19, 99, 30);
		add(pnlEquationBackdrop);

		pnlStressType.setName("pnlStressType");
		pnlEquationBackdrop.add(pnlStressType, pnlStressType.getName());

		pnlStrainType.setName("pnlStrainType");
		pnlEquationBackdrop.add(pnlStrainType, pnlStrainType.getName());
		pnlStrainType.add(lbleEquation);

		pnlLabelBackdrop.setLayout(new CardLayout());
		pnlLabelBackdrop.setBounds(154, 56, 36, 33);
		add(pnlLabelBackdrop);

		pnlStrainTypeLabel.setName("pnlStrainTypeLabel");
		pnlLabelBackdrop.add(pnlStrainTypeLabel, pnlStrainTypeLabel.getName());

		pnlLabelBackdrop.add(pnlStressTypeLabel, "pnlStressTypeLabel");
		pnlStressTypeLabel.setName("pnlStressTypeLabel");

		pnlStressTypeLabel.add(lbldValue);

		pnlStrainTypeLabel.add(lbleValue);

		add(lbldEquation);
		lbldEquation.setBounds(259, 21, 308, 21);

		add(cboStressStrain);
		cboStressStrain.addActionListener(keyCombo);
		cboStressStrain.setBounds(35, 21, 84, 21);

	}

	@Override
	public String writeFitPanel() throws IncompleteOptionException {
		if (txtdValue.getText().equals(""))
			if (cboStressStrain.getSelectedIndex() == 1)
				throw new IncompleteOptionException("Please enter the value for e");
			else
				throw new IncompleteOptionException("Please enter the value for d");
		gulpFileLines += "piezoelectric";
		if (cboStressStrain.getSelectedIndex() == 1)
			gulpFileLines += " strain";
		gulpFileLines += " " + cboiValue.getSelectedItem();
		if (cboXyz.getSelectedIndex() != 0)
			gulpFileLines += " " + cboXyz.getSelectedItem();
		gulpFileLines += " " + txtdValue.getText();
		if (!txtWeight.getText().equals(""))
			gulpFileLines += " " + txtWeight.getText();
		gulpFileLines += Back.newLine;
		return gulpFileLines;
	}
	
}
