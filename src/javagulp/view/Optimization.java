package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class Optimization extends JPanel implements Serializable {

	private static final long serialVersionUID = 7736128778704157390L;

	private G g = new G();

	private String[] optimizationChoicesDefault = { "bfgs (broyden)",
			"limited memory bfgs", "Davidon-Fletcher-Powell",
			"conjugate gradient" };
	private String[] stoppingCriterion = { "cycles", "gradient norm" };
	private String[] optimizationChoices = { "bfgs (broyden)",
			"rational function optimization", "bfgs start w/unit Hessian",
			"bfgs start w/numerical diagonal Hessian", "conjugate gradient" };

	private JComboBox cboOptimization = new JComboBox(optimizationChoicesDefault);
	private JComboBox cboSwitch_minimiserStoppingCriterion = new JComboBox(stoppingCriterion);
	private JComboBox cboSwitchOptimization = new JComboBox(optimizationChoices);

	private SerialListener keyOptimization = new SerialListener() {
		
		private static final long serialVersionUID = -6450781303665298416L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(false, "dfp");
			Back.getKeys().putOrRemoveKeyword(false, "conjugate");
			Back.getKeys().putOrRemoveKeyword(false, "lfbgs");
			if (cboOptimization.getSelectedIndex() != 0)
				Back.getKeys().putOrRemoveKeyword(true,
						(String) cboOptimization.getSelectedItem());
		}
	};

	private JCheckBox chkAllowOnlyIsotropicRadioButton = new JCheckBox("allow only isotropic expansion/contraction");
	private JCheckBox chkUseNumerical = new JCheckBox("use numerical estimates of the diagonal elements as a starting point for the Hessian");
	private JCheckBox chkUseUnit = new JCheckBox("use unit diagonal matrix as starting point for hessian");
	private JCheckBox chklinmin = new JCheckBox("print details of line minimization");
	private JCheckBox chkopti = new JCheckBox("perform an optimization with an exact Hessian (default)");
	private JCheckBox chkoptimisefitShells = new JCheckBox("optimize only shells (optical calculation)");
	private JCheckBox chkoutputDetailsOf = new JCheckBox("output details of the Hessian matrix");
	private JCheckBox chkpositive = new JCheckBox(g.html("ensure that the Hessian always behaves as positive definite during Newton-Raphson <br>by ensuring the search vector has the same sign as the gradient vector"));
	private JCheckBox chkuseImaginaryPhonon = new JCheckBox("use imaginary phonon modes to lower structural symmetry");

	private KeywordListener keyuseImaginaryPhonon = new KeywordListener(chkuseImaginaryPhonon, "lower_symmetry");
	private KeywordListener keyAllowOnlyIsotropicRadioButton = new KeywordListener(chkAllowOnlyIsotropicRadioButton, "isotropic");
	private KeywordListener keyUseNumerical = new KeywordListener(chkUseNumerical, "numdiag");
	private KeywordListener keyUseUnit = new KeywordListener(chkUseUnit, "unit");
	private KeywordListener keyoutputDetailsOf = new KeywordListener(chkoutputDetailsOf, "hessian");
	private KeywordListener keypositive = new KeywordListener(chkpositive,
			"positive");
	private TaskKeywordListener keyopti = new TaskKeywordListener(chkopti, "optimise");
	private KeywordListener keylinmin = new KeywordListener(chklinmin, "linmin");
	private KeywordListener keyoptimisefitShells = new KeywordListener(chkoptimisefitShells, "shell");

	private JLabel lblAfter = new JLabel("after");
	private JLabel lblmaximumNumberOf = new JLabel("maximum number of points");
	private JLabel lblMaximumFunctionalChange = new JLabel("<html>maximum functional change per minimisation step before Hessian is recalculated (eV)</html>");
	private JLabel lblMaximumNumberOf = new JLabel("<html>maximum number of cycles of Hessian updating before exact calculation is performed</html>");
	private JLabel lblScaleImaginaryMode = new JLabel("scale imaginary mode eigenvectors by");
	private JLabel lblStopIfCell = new JLabel(g.html("stop optimization if cell parameter falls below (&Aring;)"));

	private TitledPanel pnlParameterTolerance = new TitledPanel();
	private TitledPanel pnlMaxStepSize = new TitledPanel();
	private TitledPanel pnlMaxIndividualGradient = new TitledPanel();
	private TitledPanel pnlBFGS = new TitledPanel();
	private TitledPanel pnleigenvector = new TitledPanel();
	private TitledPanel pnlfunctiontolerance = new TitledPanel();
	private TitledPanel pnlgradienttolerance = new TitledPanel();
	private TitledPanel pnlLineMinimisationOptions = new TitledPanel();
	private TitledPanel pnlmaximumcycles = new TitledPanel();
	private TitledPanel pnlotheroptions = new TitledPanel();
	private TitledPanel pnlprimaryoptimizer = new TitledPanel();
	private TitledPanel pnlswitchoptimizer = new TitledPanel();

	private JPanel pnlHessianOptions = new JPanel();

	private JTextField txtdelf = new JTextField();
	private JTextField txtftol = new JTextField("0.00001");
	private JTextField txtgmax = new JTextField("0.001");
	private JTextField txtgtol = new JTextField("0.0001");
	private JTextField txtlbfgs_order = new JTextField("10");
	private final String TXT_LINE = "10";
	private JTextField txtline = new JTextField(TXT_LINE);
	private final String TXT_MAX_CYCOPT = "1000";
	private JTextField txtmaxcycopt = new JTextField(TXT_MAX_CYCOPT);
	private final String TXT_MIN_CELL = "0.5";
	private JTextField txtmincell = new JTextField(TXT_MIN_CELL);
	private final String TXT_SLOWER = "0.001";
	private JTextField txtslower = new JTextField(TXT_SLOWER);
	private final String TXT_STEP_MXOPT = "1.0";
	private JTextField txtstepmxopt = new JTextField(TXT_STEP_MXOPT);
	private JTextField txtSwitch_minimiserStoppingCriterionNum = new JTextField();
	private final String TXT_UPDATE = "10";
	private JTextField txtupdate = new JTextField(TXT_UPDATE);
	private JTextField txtxtolopt = new JTextField("0.00001");

	public Optimization() {
		super();
		setLayout(null);

		// jTextArea4.setDisabledTextColor(new Color(204, 204, 204));
		// jTextArea4.setBackground(new Color(204, 204, 204));

		pnlParameterTolerance.setTitle("parameter tolerance");
		pnlParameterTolerance.setBounds(660, 57, 236, 48);
		add(pnlParameterTolerance);
		txtxtolopt.setBounds(9, 18, 80, 21);
		pnlParameterTolerance.add(txtxtolopt);

		pnlMaxStepSize.setTitle("maximum step size");
		pnlMaxStepSize.setBounds(961, 1, 167, 50);
		add(pnlMaxStepSize);
		txtstepmxopt.setBounds(22, 19, 80, 21);
		pnlMaxStepSize.add(txtstepmxopt);

		pnleigenvector.setTitle("imaginary eigenvector following");
		pnleigenvector.setBounds(660, 162, 468, 83);
		add(pnleigenvector);
		chkuseImaginaryPhonon.addActionListener(keyuseImaginaryPhonon);
		chkuseImaginaryPhonon.setBounds(10, 23, 448, 25);
		pnleigenvector.add(chkuseImaginaryPhonon);
		lblScaleImaginaryMode.setBounds(10, 54, 311, 15);
		pnleigenvector.add(lblScaleImaginaryMode);
		txtslower.setBounds(327, 52, 87, 20);
		pnleigenvector.add(txtslower);

		pnlotheroptions.setTitle("other options");
		pnlotheroptions.setBounds(660, 251, 468, 78);
		add(pnlotheroptions);
		lblStopIfCell.setBounds(10, 23, 378, 15);
		pnlotheroptions.add(lblStopIfCell);
		txtmincell.setBounds(394, 21, 64, 20);
		pnlotheroptions.add(txtmincell);
		chkAllowOnlyIsotropicRadioButton.addActionListener(keyAllowOnlyIsotropicRadioButton);
		chkAllowOnlyIsotropicRadioButton.setBounds(10, 43, 385, 25);
		pnlotheroptions.add(chkAllowOnlyIsotropicRadioButton);

		pnlmaximumcycles.setTitle("maximum number of cycles");
		pnlmaximumcycles.setBounds(660, 335, 215, 51);
		add(pnlmaximumcycles);
		txtmaxcycopt.setBounds(10, 22, 90, 20);
		pnlmaximumcycles.add(txtmaxcycopt);

		pnlBFGS.setTitle("order of limited-memory BFGS");
		pnlBFGS.setBounds(881, 335, 247, 51);
		add(pnlBFGS);
		txtlbfgs_order.setBounds(10, 22, 90, 20);
		pnlBFGS.add(txtlbfgs_order);

		pnlgradienttolerance.setTitle("gradient tolerance");
		pnlgradienttolerance.setBounds(932, 111, 196, 45);
		add(pnlgradienttolerance);
		txtgtol.setBounds(6, 17, 80, 21);
		pnlgradienttolerance.add(txtgtol);

		pnlMaxIndividualGradient.setTitle("maximum individual gradient");
		pnlMaxIndividualGradient.setBounds(660, 111, 266, 45);
		add(pnlMaxIndividualGradient);
		txtgmax.setBounds(7, 17, 80, 21);
		pnlMaxIndividualGradient.add(txtgmax);

		pnlfunctiontolerance.setTitle("function tolerance");
		pnlfunctiontolerance.setBounds(902, 57, 226, 48);
		add(pnlfunctiontolerance);
		txtftol.setBounds(9, 19, 80, 21);
		pnlfunctiontolerance.add(txtftol);

		pnlprimaryoptimizer.setTitle("primary optimizer");
		pnlprimaryoptimizer.setBounds(0, 32, 268, 69);
		add(pnlprimaryoptimizer);
		cboOptimization.setBounds(10, 22, 197, 20);
		pnlprimaryoptimizer.add(cboOptimization);
		cboOptimization.addActionListener(keyOptimization);

		pnlswitchoptimizer.setTitle("switch optimizer to");
		pnlswitchoptimizer.setBounds(274, 32, 380, 70);
		add(pnlswitchoptimizer);
		cboSwitchOptimization.setBounds(10, 18, 285, 20);
		pnlswitchoptimizer.add(cboSwitchOptimization);
		lblAfter.setBounds(10, 44, 81, 20);
		pnlswitchoptimizer.add(lblAfter);
		txtSwitch_minimiserStoppingCriterionNum.setBounds(97, 44, 72, 21);
		pnlswitchoptimizer.add(txtSwitch_minimiserStoppingCriterionNum);
		cboSwitch_minimiserStoppingCriterion.setBounds(175, 44, 120, 20);
		pnlswitchoptimizer.add(cboSwitch_minimiserStoppingCriterion);

		pnlHessianOptions.setBorder(new TitledBorder(null, "Hessian options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlHessianOptions.setLayout(null);
		pnlHessianOptions.setBounds(0, 107, 654, 243);
		add(pnlHessianOptions);
		chkUseNumerical.setBounds(10, 23, 634, 25);
		pnlHessianOptions.add(chkUseNumerical);
		chkUseNumerical.addActionListener(keyUseNumerical);
		chkUseUnit.setBounds(10, 54, 634, 25);
		pnlHessianOptions.add(chkUseUnit);
		chkUseUnit.addActionListener(keyUseUnit);
		chkoutputDetailsOf.setBounds(10, 80, 634, 25);
		pnlHessianOptions.add(chkoutputDetailsOf);
		chkoutputDetailsOf.addActionListener(keyoutputDetailsOf);
		chkpositive.setBounds(10, 105, 634, 38);
		pnlHessianOptions.add(chkpositive);
		chkpositive.addActionListener(keypositive);
		lblMaximumNumberOf.setBounds(10, 150, 548, 33);
		pnlHessianOptions.add(lblMaximumNumberOf);
		txtupdate.setBounds(564, 149, 80, 20);
		pnlHessianOptions.add(txtupdate);
		lblMaximumFunctionalChange.setBounds(10, 188, 548, 42);
		pnlHessianOptions.add(lblMaximumFunctionalChange);
		txtdelf.setBounds(564, 191, 80, 21);
		pnlHessianOptions.add(txtdelf);

		chkopti.setName("optOrNot");
		chkopti.setBounds(0, 1, 654, 25);
		add(chkopti);
		chkopti.addActionListener(keyopti);
		chkopti.setActionCommand("CALTYP_OPT");
		chkopti.setToolTipText("Minimize the energy with respect to geometrical variables");

		pnlLineMinimisationOptions.setTitle("line minimisation options");
		pnlLineMinimisationOptions.setBounds(660, 1, 295, 51);
		add(pnlLineMinimisationOptions);
		txtline.setBounds(235, 24, 50, 20);
		pnlLineMinimisationOptions.add(txtline);
		lblmaximumNumberOf.setBounds(10, 26, 219, 15);
		pnlLineMinimisationOptions.add(lblmaximumNumberOf);

		chklinmin.addActionListener(keylinmin);
		chklinmin.setBounds(0, 356, 320, 25);
		add(chklinmin);
		chkoptimisefitShells.addActionListener(keyoptimisefitShells);
		chkoptimisefitShells.setBounds(326, 356, 328, 25);
		add(chkoptimisefitShells);
	}

	private String writeDelf() {
		String line = "";
		if (!txtdelf.getText().equals("")) {
			line = "delf " + txtdelf.getText() + Back.newLine;
		}
		return line;
	}

	private String writeLbfgs_order() {
		String lines = "";
		String s = txtlbfgs_order.getText();
		if (!s.equals("") && !s.equals("10")) {
			Integer.parseInt(s);
			lines = "lbfgs_order " + s + Back.newLine;
		}
		return lines;
	}

	private String writeTol() {
		String line = "";
		// check that both fit and opt are not both used
		if (!txtftol.getText().equals("")
				&& !txtftol.getText().equals("0.00001")) {
			Double.parseDouble(txtftol.getText());
			line += "ftol opt " + txtftol.getText() + Back.newLine;
		}
		if (!txtgtol.getText().equals("")
				&& !txtgtol.getText().equals("0.0001")) {
			Double.parseDouble(txtgtol.getText());
			line += "gtol opt " + txtgtol.getText() + Back.newLine;
		}
		if (!txtgmax.getText().equals("") && !txtgmax.getText().equals("0.001")) {
			Double.parseDouble(txtgmax.getText());
			line += "gmax opt " + txtgmax.getText() + Back.newLine;
		}
		return line;
	}

	private String writeLine() {
		String line = "";
		if (!txtline.getText().equals("")
				&& !txtline.getText().equals(TXT_LINE)) {
			line = "line " + txtline.getText() + Back.newLine;
		}
		return line;
	}

	private String writeMaxcyc() {
		String lines = "";
		// check that both fit and opt are not both used
		if (!txtmaxcycopt.getText().equals("")
				&& !txtmaxcycopt.getText().equals(TXT_MAX_CYCOPT)) {
			lines = "maxcyc opt " + txtmaxcycopt.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMincell() {
		String line = "";
		if (!txtmincell.getText().equals("")
				&& !txtmincell.getText().equals(TXT_MIN_CELL)) {
			line = "mincell " + txtmincell.getText() + Back.newLine;
		}
		return line;
	}

	public String writeOptimization() throws IncompleteOptionException {
		return writeUpdate() + writeSlower() + writeSwitch_minimiser()
				+ writeStepMaxOpt() + writeMincell() + writeTolerance()
				+ writeLine() + writeLbfgs_order() + writeTol() + writeDelf()
				+ writeMaxcyc();
	}

	private String writeSlower() {
		String line = "";
		if (!txtslower.getText().equals("")
				&& !txtslower.getText().equals(TXT_SLOWER)) {
			line = "slower " + txtslower.getText() + Back.newLine;
		}
		return line;
	}

	private String writeStepMaxOpt() {
		String line = "";
		if (!txtstepmxopt.getText().equals("")
				&& !txtstepmxopt.getText().equals(TXT_STEP_MXOPT)) {
			line = "stepmx opt " + txtstepmxopt.getText() + Back.newLine;
		}
		return line;
	}

	private String writeSwitch_minimiser() {
		String lines = "";
		String s = txtSwitch_minimiserStoppingCriterionNum.getText();
		if (!s.equals("")) {
			Double.parseDouble(s);
			lines = "switch_minimiser "
					+ cboSwitchOptimization.getSelectedItem()
					+ cboSwitch_minimiserStoppingCriterion.getSelectedItem()
					+ s + Back.newLine;
		}
		return lines;
	}

	private String writeTolerance() {
		String lines = "", xtol = txtxtolopt.getText();
		if (!xtol.equals("") && !xtol.equals("0.00001")) {
			Double.parseDouble(xtol);
			lines += "xtol opt " + xtol + Back.newLine;
		}
		return lines;
	}

	private String writeUpdate() {
		String line = "";
		if (!txtupdate.getText().equals("")
				&& !txtupdate.getText().equals(TXT_UPDATE)) {
			line = "update " + txtupdate.getText() + Back.newLine;
		}
		return line;
	}
}