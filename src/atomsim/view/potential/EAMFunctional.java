package javagulp.view.potential;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class EAMFunctional extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -2833967923111767868L;

	private final JTextField txtBSmithF1 = new JTextField();
	private final JTextField txtBSmithRho = new JTextField();
	private final JTextField txtBSmithF0 = new JTextField();
	private final JTextField txtBSmithN = new JTextField();
	private final JTextField txtPowerA = new JTextField();
	private final JTextField txtPowerN = new JTextField();
	private final JTextField txtSqA = new JTextField();

	private final JCheckBox chkFitSqA = new JCheckBox();
	private final JCheckBox chkFitPowerN = new JCheckBox();
	private final JCheckBox chkFitPowerA = new JCheckBox();
	private final JCheckBox chkFitBSmithN = new JCheckBox();
	private final JCheckBox chkFitBSmithF0 = new JCheckBox();
	private final JCheckBox chkFitBSmithF1 = new JCheckBox();
	private final JCheckBox chkFitRHo = new JCheckBox();

	private final JTabbedPane pane = new JTabbedPane();
	private final G g = new G();

	private final JPanel pnlSquareRoot = new JPanel();
	private final JPanel pnlPower = new JPanel();
	private final JPanel pnlBanerjeaSmith = new JPanel();

	public EAMFunctional() {
		super(1);

		pane.setBounds(0, 0, 721, 310);
		add(pane);

		pnlSquareRoot.setLayout(null);
		pane.add(pnlSquareRoot, "Square Root");
		final JLabel lblSquareRootEquation = new JLabel("<html>E = -sum<sub>i</sub> A<sub>i</sub> &#961;<sub>i</sub><sup>1/2</sup></html>");
		pnlSquareRoot.add(lblSquareRootEquation);
		lblSquareRootEquation.setBounds(8, 13, 300, 38);
		lblSquareRootEquation.setOpaque(false);
		final JLabel lblSqA = new JLabel("A (eV)");
		lblSqA.setBounds(17, 81, 40, 20);
		pnlSquareRoot.add(lblSqA);
		pnlSquareRoot.add(chkFitSqA);
		txtSqA.setBounds(63, 84, 84, 21);
		chkFitSqA.setBounds(152, 84, 20, 21);
		pnlSquareRoot.add(txtSqA);
		pnlPower.setLayout(null);
		pane.add(pnlPower, "Power");
		final JLabel lblPowerEquation = new JLabel(g.html("E = -sum<sub>i</sub> A<sub>i</sub> " + g.rho
				+ "<sub>i</sub><sup>1/n</sup> "));
		pnlPower.add(lblPowerEquation);
		lblPowerEquation.setBounds(8, 13, 300, 38);
		lblPowerEquation.setOpaque(false);
		final JLabel lblPowerN = new JLabel("n");
		lblPowerN.setBounds(17, 70, 29, 28);
		pnlPower.add(lblPowerN);
		txtPowerN.setBounds(63, 75, 83, 20);
		chkFitPowerN.setBounds(151, 75, 20, 21);
		pnlPower.add(chkFitPowerN);
		pnlPower.add(txtPowerN);
		final JLabel lblPowerA = new JLabel("A (eV)");
		lblPowerA.setBounds(17, 111, 40, 15);
		pnlPower.add(lblPowerA);
		txtPowerA.setBounds(63, 109, 83, 20);
		chkFitPowerA.setBounds(151, 109, 20, 21);
		pnlPower.add(chkFitPowerA);
		pnlPower.add(txtPowerA);
		pnlBanerjeaSmith.setLayout(null);
		pane.add(pnlBanerjeaSmith, "Banerjea Smith");
		final JLabel lblBSmithEquation = new JLabel(g.html("E = -sum<sub>i</sub> F<sub>0</sub> [1 - 1/n ln(r)] r<sup>1/n</sup> + F<sub>1</sub>r &nbsp; &nbsp; where r = "
				+ g.rho + "<sub>i</sub>/" + g.rho + "<sub>i</sub><sup>0</sup>"));
		pnlBanerjeaSmith.add(lblBSmithEquation);
		lblBSmithEquation.setBounds(7, 14, 469, 35);
		lblBSmithEquation.setOpaque(false);
		final JLabel bsNLabel = new JLabel("n");
		bsNLabel.setBounds(8, 70, 29, 28);
		pnlBanerjeaSmith.add(bsNLabel);
		txtBSmithN.setBounds(59, 75, 83, 20);
		chkFitBSmithN.setBounds(147, 75, 20, 21);
		pnlBanerjeaSmith.add(chkFitBSmithN);
		pnlBanerjeaSmith.add(txtBSmithN);
		txtBSmithF0.setBounds(59, 115, 83, 20);
		chkFitBSmithF0.setBounds(147, 115, 20, 21);
		pnlBanerjeaSmith.add(chkFitBSmithF0);
		pnlBanerjeaSmith.add(txtBSmithF0);
		final JLabel lblBSmithF0 = new JLabel(g.html("F<sub>0</sub> (eV)"));
		lblBSmithF0.setBounds(8, 122, 40, 15);
		pnlBanerjeaSmith.add(lblBSmithF0);
		final JLabel lblBSmithF1 = new JLabel(g.html("F<sub>1</sub> (eV)"));
		lblBSmithF1.setBounds(190, 80, 50, 15);
		pnlBanerjeaSmith.add(lblBSmithF1);
		txtBSmithF1.setBounds(241, 75, 83, 20);
		chkFitBSmithF1.setBounds(329, 75, 20, 21);
		pnlBanerjeaSmith.add(chkFitBSmithF1);
		pnlBanerjeaSmith.add(txtBSmithF1);
		txtBSmithRho.setBounds(241, 115, 83, 20);
		chkFitRHo.setBounds(329, 115, 20, 21);
		pnlBanerjeaSmith.add(chkFitRHo);
		pnlBanerjeaSmith.add(txtBSmithRho);
		final JLabel lblBSmithRho = new JLabel(g.html(g.rho + "<sub>i</sub><sup>0</sup>"));
		lblBSmithRho.setBounds(190, 115, 36, 28);
		pnlBanerjeaSmith.add(lblBSmithRho);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "eam_functional ";
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		// TODO Documentation allows for multiple atoms / values where we only
		// allow for one. As a workaround, the user may simply enter this
		// potential any number of times.
		if (pane.getSelectedIndex() == 0) {
			if (txtSqA.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for A");
			Double.parseDouble(txtSqA.getText());
			lines += "square_root" + Back.newLine + pot.getAtomCombos()
			+ txtSqA.getText();
			if (chkFitSqA.isSelected())
				lines += " 1";
		} else if (pane.getSelectedIndex() == 1) {
			if (txtPowerN.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for n");
			if (txtPowerA.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for A");
			Double.parseDouble(txtPowerN.getText());
			Double.parseDouble(txtPowerA.getText());
			lines += "power " + txtPowerN.getText() + Back.newLine
			+ pot.getAtomCombos() + txtPowerA.getText();
			if (chkFitPowerA.isSelected() || chkFitPowerN.isSelected())
				lines += " " + chkSelected(chkFitPowerA) + " " + chkSelected(chkFitPowerN);
		} else if (pane.getSelectedIndex() == 2) {
			if (txtBSmithN.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for n");
			Double.parseDouble(txtBSmithN.getText());
			final JTextField[] fields3 = { txtBSmithF0, txtBSmithF1, txtBSmithRho };
			final String[] descriptions3 = { "F0", "F1", "Rho0" };
			Back.checkAllNonEmpty(fields3, descriptions3);
			Back.parseFieldsD(fields3, descriptions3);
			lines += "banerjea_smith " + txtBSmithN.getText() + Back.newLine
			+ pot.getAtomCombos() + Back.concatFields(fields3);
			if (chkFitBSmithF0.isSelected() || chkFitBSmithF1.isSelected() || chkFitBSmithN.isSelected()
					|| chkFitRHo.isSelected())
				lines += " " + chkSelected(chkFitBSmithF0) + " " + chkSelected(chkFitBSmithF1) + " " +
				chkSelected(chkFitRHo) + " " + chkSelected(chkFitBSmithN);
		}
		return lines + Back.newLine;
	}

	private int chkSelected(JCheckBox chkFit)
	{
		int value = 0;
		if (chkFit.isSelected())
			value = 1;
		return value;
	}

	@Override
	public PotentialPanel clone() {
		return new EAMFunctional();
	}

	@Override
	public int currentParameterCount() {
		return 0;
		//TODO add fitting checkboxes to GUI and update this method
	}

	@Override
	public void setParameter(int i, String value) {
		//TODO add fitting checkboxes to GUI and update this method
	}
}