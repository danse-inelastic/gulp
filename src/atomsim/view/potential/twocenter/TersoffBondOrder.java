package javagulp.view.potential.twocenter;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class TersoffBondOrder extends JPanel implements Serializable {

	private static final long serialVersionUID = -5094836799383225017L;

	private final G g = new G();

	private final JCheckBox chkButton = new JCheckBox();
	private final JCheckBox chkAlpha = new JCheckBox("fit");
	private final JCheckBox chkN = new JCheckBox("fit");
	private final JCheckBox chkLambda = new JCheckBox("fit");
	private final JCheckBox chkC = new JCheckBox("fit");
	private final JCheckBox chkD = new JCheckBox("fit");
	private final JCheckBox chkH = new JCheckBox("fit");

	public JTextField txtAlpha = new JTextField();
	public JTextField txtAtom = new JTextField();
	public JTextField txtC = new JTextField();
	public JTextField txtD = new JTextField();
	public JTextField txtH = new JTextField();
	public JTextField txtLambda = new JTextField();
	public JTextField txtM = new JTextField();
	public JTextField txtN = new JTextField();

	public JComboBox cboAtomType = new JComboBox(new String[] { "core", "shell" });

	public JPanel pnlTheta = new JPanel();

	private final JLabel lblAtom = new JLabel("atom");
	private final JLabel lblH = new JLabel("h");
	private final JLabel lblD = new JLabel("d");
	private final JLabel lblC = new JLabel("c");
	private final JLabel lblLambda = new JLabel(g.html(g.lambda));
	private final JLabel lblN = new JLabel("n");
	private final JLabel lblM = new JLabel("m");
	private final JLabel lblAlpha = new JLabel(g.html(g.alpha));

	private final SerialListener keyButton = new SerialListener() {
		private static final long serialVersionUID = -6539090813357848327L;
		@Override
		public void actionPerformed(ActionEvent e) {
			txtC.setEnabled(chkButton.isSelected());
			txtD.setEnabled(chkButton.isSelected());
			txtH.setEnabled(chkButton.isSelected());
			chkC.setEnabled(chkButton.isSelected());
			chkD.setEnabled(chkButton.isSelected());
			chkH.setEnabled(chkButton.isSelected());
		}
	};

	public TersoffBondOrder(String name) {
		super();
		setLayout(null);
		setBorder(new TitledBorder(null, name,
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		this.setPreferredSize(new java.awt.Dimension(329, 98));

		final TitledBorder tb = new TitledBorder(null, null, TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, null, null);
		tb.setTitle("       theta option");
		pnlTheta.setBorder(tb);

		txtC.setEnabled(false);
		txtD.setEnabled(false);
		txtH.setEnabled(false);
		chkButton.setBounds(3, -3, 25, 25);
		pnlTheta.setLayout(null);
		pnlTheta.add(chkButton);
		chkButton.setMargin(new Insets(0, 0, 0, 0));
		chkButton.setFont(new Font("Sans", Font.BOLD, 9));
		chkButton.setBounds(3, 1, 21, 17);
		txtAlpha.setBounds(7, 14, 56, 18);
		add(txtAlpha);
		txtM.setBounds(7, 35, 56, 18);
		add(txtM);
		txtN.setBounds(7, 56, 56, 18);
		add(txtN);
		txtLambda.setBounds(7, 77, 56, 21);
		add(txtLambda);
		lblAlpha.setBounds(70, 14, 20, 20);
		add(lblAlpha);
		chkAlpha.setBounds(90, 14, 40, 20);
		add(chkAlpha);
		lblM.setBounds(70, 35, 20, 20);
		add(lblM);
		lblN.setBounds(70, 56, 20, 20);
		add(lblN);
		chkN.setBounds(90, 56, 40, 20);
		add(chkN);
		lblLambda.setBounds(70, 77, 20, 20);
		add(lblLambda);
		chkLambda.setBounds(90, 77, 40, 20);
		add(chkLambda);
		chkButton.addActionListener(keyButton);
		pnlTheta.setBounds(133, 35, 196, 63);
		add(pnlTheta);
		txtC.setBounds(7, 17, 35, 18);
		pnlTheta.add(txtC);
		txtD.setBounds(98, 17, 35, 18);
		pnlTheta.add(txtD);
		lblC.setBounds(45, 17, 15, 18);
		pnlTheta.add(lblC);
		chkC.setBounds(60, 17, 40, 18);
		pnlTheta.add(chkC);
		lblD.setBounds(137, 17, 15, 18);
		pnlTheta.add(lblD);
		chkD.setBounds(150, 17, 40, 18);
		pnlTheta.add(chkD);
		txtH.setBounds(7, 38, 35, 18);
		pnlTheta.add(txtH);
		lblH.setBounds(45, 38, 15, 21);
		pnlTheta.add(lblH);
		chkH.setBounds(60, 38, 40, 21);
		pnlTheta.add(chkH);
		cboAtomType.setBounds(252, 14, 63, 21);
		add(cboAtomType);
		lblAtom.setBounds(147, 14, 35, 21);
		add(lblAtom);
		txtAtom.setBounds(189, 14, 56, 21);
		add(txtAtom);
	}

	public String writeBondOrder() throws IncompleteOptionException {
		final JTextField[] fields1 = { txtAlpha, txtM, txtN, txtLambda };
		final String[] descriptions1 = { "alpha", "m", "n", "lambda" };
		Back.checkAllNonEmpty(fields1, descriptions1);
		Back.parseFieldsD(fields1, descriptions1);

		String lines = "";
		if (chkButton.isSelected())
			lines += " theta";
		lines += Back.newLine + txtAtom.getText() + " " + Back.concatFields(fields1);
		if (chkButton.isSelected()) {
			final JTextField[] fields2 = { txtC, txtD, txtH };
			final String[] descriptions2 = { "c", "d", "h" };
			Back.checkAllNonEmpty(fields2, descriptions2);
			Back.parseFieldsD(fields2, descriptions2);
			lines += " " + Back.concatFields(fields2);
		}
		final JCheckBox[] boxes1 = { chkAlpha, chkN, chkLambda };
		lines += Back.writeFits(boxes1);
		if (chkButton.isSelected()) {
			final JCheckBox[] boxes2 = { chkC, chkD, chkH };
			lines += Back.writeFits(boxes2);
		}
		return lines + Back.newLine;
	}
}