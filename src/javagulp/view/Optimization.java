package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.constraints.ExternalFieldConstraints;
import javagulp.view.optimization.OutputFormats;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Optimization extends JPanel implements Serializable {

	private static final long serialVersionUID = 7736128778704157390L;

	private final G g = new G();

	private final String[] optimizationChoicesDefault = { "bfgs (broyden)",
			"limited memory bfgs", "Davidon-Fletcher-Powell",
	"conjugate gradient" };
	private final String[] stoppingCriterion = { "cycles", "gradient norm" };
	private final String[] stoppingCriterionChoices = { "cycle", "gnorm" };
	private final String[] optimizationChoices = { "bfgs (broyden)",
			"rational function optimization", "bfgs start w/unit Hessian",
			"bfgs start w/numerical diagonal Hessian", "conjugate gradient" };
	private final String[] optimizationChoiceValues = { "bfgs", "rfo", "unit",
			"nume", "conj" };

	private final JComboBox cboOptimization = new JComboBox(optimizationChoicesDefault);
	private final JComboBox cboSwitch_minimiserStoppingCriterion = new JComboBox(stoppingCriterion);
	private final JComboBox cboSwitchOptimization = new JComboBox(optimizationChoices);

	private final SerialListener keyOptimization = new SerialListener() {

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

	private final JCheckBox chkAllowOnlyIsotropicRadioButton = new JCheckBox("allow only isotropic expansion/contraction");
	private final JCheckBox chkUseNumerical = new JCheckBox("use numerical estimates of the diagonal elements as a starting point for the Hessian");
	private final JCheckBox chkUseUnit = new JCheckBox("use unit diagonal matrix as starting point for hessian");
	private final JCheckBox chklinmin = new JCheckBox("print details of line minimization");
	//private JCheckBox chkopti = new JCheckBox("perform an optimization with an exact Hessian (default)");
	private final JCheckBox chkoptimisefitShells = new JCheckBox("optimize only shells (optical calculation)");
	private final JCheckBox chkOptimizeCellRadii = new JCheckBox("optimize only shell radii");
	private final JCheckBox chkoutputDetailsOf = new JCheckBox("output details of the Hessian matrix");
	private final JCheckBox chkpositive = new JCheckBox(g.html("ensure that the Hessian always behaves as positive definite during Newton-Raphson <br>by ensuring the search vector has the same sign as the gradient vector"));
	private final JCheckBox chkuseImaginaryPhonon = new JCheckBox("use imaginary phonon modes to lower structural symmetry");
	private final JCheckBox chkExcludeShellRadii = new JCheckBox("exclude shell radii from the fitting/optimization");
	private final JCheckBox chkOptimizeUnitCell = new JCheckBox("optimize/fit unit cell coordinates but leave internal atomic coordinates fixed");
	private final JCheckBox chkXYZTrajectory = new JCheckBox("write xyz trajectory");

	private final KeywordListener keyuseImaginaryPhonon = new KeywordListener(chkuseImaginaryPhonon, "lower_symmetry");
	private final KeywordListener keyAllowOnlyIsotropicRadioButton = new KeywordListener(chkAllowOnlyIsotropicRadioButton, "isotropic");
	private final KeywordListener keyUseNumerical = new KeywordListener(chkUseNumerical, "numdiag");
	private final KeywordListener keyUseUnit = new KeywordListener(chkUseUnit, "unit");
	private final KeywordListener keyoutputDetailsOf = new KeywordListener(chkoutputDetailsOf, "hessian");
	private final KeywordListener keypositive = new KeywordListener(chkpositive,
	"positive");
	//private TaskKeywordListener keyopti = new TaskKeywordListener(chkopti, "optimise");
	private final KeywordListener keylinmin = new KeywordListener(chklinmin, "linmin");
	private final KeywordListener keyoptimisefitShells = new KeywordListener(chkoptimisefitShells, "shell");
	private final KeywordListener keyOptimizeCellRadii = new KeywordListener(chkOptimizeCellRadii, "breathe");
	private final KeywordListener keyExcludeShellRadii = new KeywordListener(chkExcludeShellRadii, "nobreathe");
	private final KeywordListener keyOptimizeUnitCell = new KeywordListener(chkOptimizeUnitCell, "cellonly");


	private final JLabel lblAfter = new JLabel("after");
	private final JLabel lblmaximumNumberOf = new JLabel("maximum number of points");
	private final JLabel lblMaximumFunctionalChange = new JLabel("<html>maximum functional change per minimisation step before Hessian is recalculated (eV)</html>");
	private final JLabel lblMaximumNumberOf = new JLabel("<html>maximum number of cycles of Hessian updating before exact calculation is performed</html>");
	private final JLabel lblScaleImaginaryMode = new JLabel("scale imaginary mode eigenvectors by");
	private final JLabel lblStopIfCell = new JLabel(g.html("stop optimization if cell parameter falls below (&Aring;)"));

	private final TitledPanel pnlParameterTolerance = new TitledPanel();
	private final TitledPanel pnlMaxStepSize = new TitledPanel();
	private final TitledPanel pnlMaxIndividualGradient = new TitledPanel();
	private final TitledPanel pnlBFGS = new TitledPanel();
	private final TitledPanel pnleigenvector = new TitledPanel();
	private final TitledPanel pnlfunctiontolerance = new TitledPanel();
	private final TitledPanel pnlgradienttolerance = new TitledPanel();
	private final TitledPanel pnlLineMinimisationOptions = new TitledPanel();
	private final TitledPanel pnlmaximumcycles = new TitledPanel();
	private final TitledPanel pnlotheroptions = new TitledPanel();
	private final TitledPanel pnlprimaryoptimizer = new TitledPanel();
	private final TitledPanel pnlswitchoptimizer = new TitledPanel();
	private final TitledPanel pnlunitcelloptions = new TitledPanel();
	private final TitledPanel pnloutputoptions = new TitledPanel();

	private final JPanel pnlHessianOptions = new JPanel();
	private final ExternalFieldConstraints pnlExternalField = new ExternalFieldConstraints();
	//private final OutputFormats pnlOutputFormats = new OutputFormats();
	private final RestartFile pnlRestartFile = new RestartFile();

	private final JTextField txtdelf = new JTextField();
	private final JTextField txtftol = new JTextField("0.00001");
	private final JTextField txtgmax = new JTextField("0.001");
	private final JTextField txtgtol = new JTextField("0.0001");
	private final JTextField txtlbfgs_order = new JTextField("10");
	private final String TXT_LINE = "10";
	private final JTextField txtline = new JTextField(TXT_LINE);
	private final String TXT_MAX_CYCOPT = "1000";
	private final JTextField txtmaxcycopt = new JTextField(TXT_MAX_CYCOPT);
	private final String TXT_MIN_CELL = "0.5";
	private final JTextField txtmincell = new JTextField(TXT_MIN_CELL);
	private final String TXT_SLOWER = "0.001";
	private final JTextField txtslower = new JTextField(TXT_SLOWER);
	private final String TXT_STEP_MXOPT = "1.0";
	private final JTextField txtstepmxopt = new JTextField(TXT_STEP_MXOPT);
	private final JTextField txtSwitch_minimiserStoppingCriterionNum = new JTextField();
	private final String TXT_UPDATE = "10";
	private final JTextField txtupdate = new JTextField(TXT_UPDATE);
	private final JTextField txtxtolopt = new JTextField("0.00001");
	private final JTextField txtxyz = new JTextField();

	public Optimization() {
		super();
		setLayout(null);

		// jTextArea4.setDisabledTextColor(new Color(204, 204, 204));
		// jTextArea4.setBackground(new Color(204, 204, 204));

		pnlParameterTolerance.setTitle("parameter tolerance");
		pnlParameterTolerance.setBounds(660, 334, 236, 48);
		add(pnlParameterTolerance);
		txtxtolopt.setBounds(9, 18, 80, 21);
		pnlParameterTolerance.add(txtxtolopt);
		
		pnloutputoptions.setBounds(660, 382, 468, 69);
		add(pnloutputoptions);
		pnloutputoptions.setTitle("output formats");
		chkXYZTrajectory.setBounds(7, 21, 163, 22);
		pnloutputoptions.add(chkXYZTrajectory);
		txtxyz.setBounds(182, 22, 267, 21);
		pnloutputoptions.add(txtxyz);
		pnlRestartFile.setBounds(660, 452, 468, 123);
		add(pnlRestartFile);

		pnlunitcelloptions.setTitle("shell options");
		pnlunitcelloptions.setBounds(208, 334, 446, 117);
		add(pnlunitcelloptions);
		chkoptimisefitShells.setBounds(12, 22, 384, 25);
		pnlunitcelloptions.add(chkoptimisefitShells);
		chkOptimizeCellRadii.setBounds(12, 51, 384, 25);
		pnlunitcelloptions.add(chkOptimizeCellRadii);
		chkExcludeShellRadii.setBounds(12, 80, 391, 25);
		pnlunitcelloptions.add(chkExcludeShellRadii);
		chkExcludeShellRadii.addActionListener(keyExcludeShellRadii);
		chkOptimizeCellRadii.addActionListener(keyOptimizeCellRadii);
		chkoptimisefitShells.addActionListener(keyoptimisefitShells);

		pnlMaxStepSize.setTitle("maximum step size");
		pnlMaxStepSize.setBounds(961, 1, 167, 98);
		add(pnlMaxStepSize);
		txtstepmxopt.setBounds(22, 19, 80, 21);
		pnlMaxStepSize.add(txtstepmxopt);

		pnleigenvector.setTitle("imaginary eigenvector following");
		pnleigenvector.setBounds(660, 162, 468, 109);
		add(pnleigenvector);
		chkuseImaginaryPhonon.addActionListener(keyuseImaginaryPhonon);
		chkuseImaginaryPhonon.setBounds(10, 23, 448, 25);
		pnleigenvector.add(chkuseImaginaryPhonon);
		lblScaleImaginaryMode.setBounds(10, 56, 311, 15);
		pnleigenvector.add(lblScaleImaginaryMode);
		txtslower.setBounds(327, 54, 87, 20);
		pnleigenvector.add(txtslower);

		pnlotheroptions.setTitle("unit cell options");
		pnlotheroptions.setBounds(0, 452, 654, 109);
		add(pnlotheroptions);
		lblStopIfCell.setBounds(10, 23, 378, 15);
		pnlotheroptions.add(lblStopIfCell);
		txtmincell.setBounds(370, 20, 64, 20);
		pnlotheroptions.add(txtmincell);
		chkAllowOnlyIsotropicRadioButton.addActionListener(keyAllowOnlyIsotropicRadioButton);
		chkAllowOnlyIsotropicRadioButton.setBounds(10, 43, 385, 25);
		pnlotheroptions.add(chkAllowOnlyIsotropicRadioButton);
		chkOptimizeUnitCell.setBounds(10, 71, 614, 25);
		pnlotheroptions.add(chkOptimizeUnitCell);
		chkOptimizeUnitCell.addActionListener(keyOptimizeUnitCell);

		pnlmaximumcycles.setTitle("maximum number of cycles");
		pnlmaximumcycles.setBounds(660, 277, 215, 51);
		add(pnlmaximumcycles);
		txtmaxcycopt.setBounds(10, 22, 90, 20);
		pnlmaximumcycles.add(txtmaxcycopt);

		pnlBFGS.setTitle("order of limited-memory BFGS");
		pnlBFGS.setBounds(881, 277, 247, 51);
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
		pnlfunctiontolerance.setBounds(902, 334, 226, 48);
		add(pnlfunctiontolerance);
		txtftol.setBounds(9, 19, 80, 21);
		pnlfunctiontolerance.add(txtftol);

		pnlprimaryoptimizer.setTitle("primary optimizer");
		pnlprimaryoptimizer.setBounds(0, 10, 268, 69);
		add(pnlprimaryoptimizer);
		cboOptimization.setBounds(10, 22, 197, 20);
		pnlprimaryoptimizer.add(cboOptimization);
		cboOptimization.addActionListener(keyOptimization);

		pnlswitchoptimizer.setTitle("switch optimizer to");
		pnlswitchoptimizer.setBounds(274, 10, 380, 70);
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
		pnlHessianOptions.setBounds(0, 85, 654, 243);
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
		txtupdate.setBounds(564, 155, 80, 20);
		pnlHessianOptions.add(txtupdate);
		lblMaximumFunctionalChange.setBounds(10, 188, 548, 42);
		pnlHessianOptions.add(lblMaximumFunctionalChange);
		txtdelf.setBounds(564, 195, 80, 21);
		pnlHessianOptions.add(txtdelf);

		//		chkopti.setName("optOrNot");
		//		chkopti.setBounds(0, 1, 654, 25);
		//		add(chkopti);
		//		chkopti.addActionListener(keyopti);
		//		chkopti.setActionCommand("CALTYP_OPT");
		//		chkopti.setToolTipText("Minimize the energy with respect to geometrical variables");

		pnlLineMinimisationOptions.setTitle("line minimisation options");
		pnlLineMinimisationOptions.setBounds(660, 1, 295, 98);
		add(pnlLineMinimisationOptions);
		txtline.setBounds(235, 24, 50, 20);
		pnlLineMinimisationOptions.add(txtline);
		lblmaximumNumberOf.setBounds(10, 26, 219, 15);
		pnlLineMinimisationOptions.add(lblmaximumNumberOf);
		chklinmin.setBounds(10, 61, 269, 25);
		pnlLineMinimisationOptions.add(chklinmin);

		chklinmin.addActionListener(keylinmin);
		pnlExternalField.radConstantVolume.setBounds(10, 53, 174, 23);
		pnlExternalField.radConstantPressure.setBounds(10, 82, 174, 23);
		pnlExternalField.radNone.setBounds(10, 24, 174, 23);


		pnlExternalField.setBounds(0, 334, 206, 117);
		add(pnlExternalField);

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
		final String s = txtlbfgs_order.getText();
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
		final String s = txtSwitch_minimiserStoppingCriterionNum.getText();
		if (!s.equals("")) {
			Double.parseDouble(s);
			lines = "switch_minimiser "
				+ optimizationChoiceValues[cboSwitchOptimization.getSelectedIndex()] + " "
				+ stoppingCriterionChoices[cboSwitch_minimiserStoppingCriterion.getSelectedIndex()] + " "
				+ s + Back.newLine;
		}
		return lines;
	}

	private String writeTolerance() {
		String lines = "";
		final String xtol = txtxtolopt.getText();
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
	
	private String writeOutputs() throws IncompleteOptionException {
		String line = "";
		if (chkXYZTrajectory.isSelected() && txtxyz.getText().equals(""))
			throw new IncompleteOptionException("Please enter an output filename");
		if (chkXYZTrajectory.isSelected()) {
			line += "output movie xyz "
			+ txtxyz.getText() + Back.newLine;
		}
		return line;
	}
	

	public String writeOptimization() throws IncompleteOptionException {
		return writeUpdate() + writeSlower() + writeSwitch_minimiser()
		+ writeStepMaxOpt() + writeMincell() + writeTolerance()
		+ writeLine() + writeLbfgs_order() + writeTol() + writeDelf()
		+ writeMaxcyc() + writeOutputs() + pnlRestartFile.writeOption();
	}
	
}