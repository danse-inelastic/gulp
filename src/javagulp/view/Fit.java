package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javagulp.model.G;
import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;
import javagulp.view.fit.FitPanelHolder;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Fit extends JPanel implements Serializable {

	private final ButtonGroup fitButtonGroup = new ButtonGroup();
	private static final long serialVersionUID = -4430341489632365418L;

	private final G g = new G();

	private final JTextField txtFxDelta = new JTextField("0.00001");
	private final String TXTMAXCYCFIT = "500 * <no. of fitting variables>";
	private final JTextField txtMaxCycfit = new JTextField(TXTMAXCYCFIT);
	private final JTextField txtXtolFit = new JTextField("0.00001");
	private final JTextField txtStepmxFit = new JTextField("1.0");
	private final JTextField txtGtol = new JTextField("0.0001");
	private final JTextField txtGmax = new JTextField("0.001");
	private final JTextField txtFtol = new JTextField("0.00001");
	private final JTextField txtOutputFittingParam = new JTextField();

	private final JCheckBox chkSimultaneous = new JCheckBox(g.html("do simultaneous relaxation of shells during fitting, including both position and radius"));
	private final JCheckBox chkRelax = new JCheckBox("fit to structural displacements on relaxation rather than to the derivatives");
	private final JRadioButton rboFit = new JRadioButton("use BFGS method but only calculate diagonal of Hessian");
	private final JRadioButton rboFullHessian = new JRadioButton("use BFGS method and calculate full Hessian");
	private final JCheckBox chkOptimisefitShellsBut = new JCheckBox("fit only shells (optical calculation)");
	private final JCheckBox chkUseGA = new JCheckBox("use genetic algorithm");
	private final JCheckBox chkDoNotSet = new JCheckBox("do not set any flags for fitting");

	public FitPanelHolder fitPanelHolder = new FitPanelHolder();
	private final JList fitList = new JList(fitPanelHolder.fitListModel);
	private final JScrollPane listScroll = new JScrollPane(fitList);

	private final KeywordListener keySimultaneous = new KeywordListener(chkSimultaneous, "simultaneous");
	private final KeywordListener keyRelax = new KeywordListener(chkRelax, "relax");
	//	private TaskKeywordListener keyFit = new TaskKeywordListener(rboFit, "fit");
	//	private TaskKeywordListener keyDoFittingRun = new TaskKeywordListener(rboFullHessian, "fbfgs");
	private final KeywordListener keyOptimisefitShellsBut = new KeywordListener(chkOptimisefitShellsBut, "shell");
	private final KeywordListener keyDoNotSet = new KeywordListener(chkDoNotSet, "noflags");

	SerialKeyAdapter listListener = new SerialKeyAdapter() {
		private static final long serialVersionUID = -8369027767995116365L;

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to remove this fit?") == JOptionPane.YES_OPTION) {
					if (fitPanelHolder.fitListModel.getSize() > 0) {
						final int index = fitList.getSelectedIndex();
						fitPanelHolder.fitListModel.remove(index);
						fitPanelHolder.fitPanelsForGulpInputFile.remove(index);
					}
				}
			}
		}
	};

	private final SerialMouseAdapter keyList = new SerialMouseAdapter() {
		private static final long serialVersionUID = 5923969703181724344L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if (fitPanelHolder.fitListModel.getSize() > 0) {
				fitPanelHolder.scrollFit.setViewportView(fitPanelHolder.fitPanelsForGulpInputFile.get(fitList.getSelectedIndex()));
			}
		}
	};

	public Fit() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(742, 350));

		add(listScroll);
		listScroll.setBounds(567, 8, 143, 245);
		fitList.addKeyListener(listListener);
		fitList.addMouseListener(keyList);

		final TitledPanel pnlParameterTolerance = new TitledPanel();
		pnlParameterTolerance.setTitle("parameter tolerance");
		pnlParameterTolerance.setBounds(716, 390, 246, 51);
		add(pnlParameterTolerance);
		txtXtolFit.setBounds(22, 23, 80, 21);
		pnlParameterTolerance.add(txtXtolFit);

		final TitledPanel pnlMaxStepSize = new TitledPanel();
		pnlMaxStepSize.setTitle("maximum step size");
		pnlMaxStepSize.setBounds(716, 338, 246, 46);
		add(pnlMaxStepSize);
		txtStepmxFit.setBounds(22, 19, 80, 21);
		pnlMaxStepSize.add(txtStepmxFit);

		fitPanelHolder.setBounds(0, 9, 558, 244);
		fitPanelHolder.scrollFit.setBounds(10, 64, 526, 172);
		add(fitPanelHolder);

		final TitledPanel pnlMaxNumOfCycles = new TitledPanel();
		pnlMaxNumOfCycles.setTitle("maximum number of cycles");
		pnlMaxNumOfCycles.setBounds(716, 9, 246, 51);
		add(pnlMaxNumOfCycles);
		txtMaxCycfit.setBounds(10, 22, 228, 20);
		pnlMaxNumOfCycles.add(txtMaxCycfit);

		final TitledPanel pnlGradientTolerance = new TitledPanel();
		pnlGradientTolerance.setTitle("gradient tolerance");
		pnlGradientTolerance.setBounds(716, 234, 246, 47);
		add(pnlGradientTolerance);
		txtGtol.setBounds(22, 19, 80, 21);
		pnlGradientTolerance.add(txtGtol);

		final TitledPanel pnlMaxIndividualGradient = new TitledPanel();
		pnlMaxIndividualGradient.setTitle("maximum individual gradient");
		pnlMaxIndividualGradient.setBounds(716, 287, 246, 45);
		add(pnlMaxIndividualGradient);
		txtGmax.setBounds(22, 19, 80, 21);
		pnlMaxIndividualGradient.add(txtGmax);

		final TitledPanel pnlFuncTolerance = new TitledPanel();
		pnlFuncTolerance.setTitle("function tolerance");
		pnlFuncTolerance.setBounds(716, 181, 246, 47);
		add(pnlFuncTolerance);
		txtFtol.setBounds(22, 19, 80, 21);
		pnlFuncTolerance.add(txtFtol);

		final TitledPanel pnlNumericalDifferencing = new TitledPanel();
		pnlNumericalDifferencing.setTitle("numerical differencing");
		pnlNumericalDifferencing.setBounds(716, 66, 246, 52);
		add(pnlNumericalDifferencing);
		txtFxDelta.setBounds(9, 23, 92, 20);
		pnlNumericalDifferencing.add(txtFxDelta);
		final JLabel lblFxDeltaFormula = new JLabel(g.html(("df(x)/dx = (f(x + "
				+ g.delta + ") - f(x))/" + g.delta)));
		lblFxDeltaFormula.setBounds(9, 19, 191, 27);
		pnlNumericalDifferencing.add(lblFxDeltaFormula);
		final JLabel lblFxDelta = new JLabel(g.html(g.delta + " (&Aring;)"));
		lblFxDelta.setBounds(9, 54, 35, 15);
		pnlNumericalDifferencing.add(lblFxDelta);

		final TitledPanel pnlOutputFittingParam = new TitledPanel();
		pnlOutputFittingParam.setTitle("output fitting parameters");
		pnlOutputFittingParam.setBounds(716, 124, 246, 51);
		add(pnlOutputFittingParam);
		final JLabel lblOutputFittingParameters = new JLabel("every (cycles)");
		lblOutputFittingParameters.setBounds(15, 25, 140, 18);
		pnlOutputFittingParam.add(lblOutputFittingParameters);
		txtOutputFittingParam.setBounds(158, 24, 78, 21);
		pnlOutputFittingParam.add(txtOutputFittingParam);

		final TitledPanel panel = new TitledPanel();
		panel.setTitle("fitting options");
		panel.setBounds(0, 259, 710, 269);
		add(panel);

		chkDoNotSet.setBounds(10, 209, 537, 25);
		panel.add(chkDoNotSet);
		chkDoNotSet.addActionListener(keyDoNotSet);

		rboFit.setSelected(true);
		rboFit.setBounds(10, 23, 483, 25);
		panel.add(rboFit);
		fitButtonGroup.add(rboFit);
		rboFit.addActionListener(keyTypeOfHessian);


		rboFullHessian.addActionListener(keyTypeOfHessian);
		rboFullHessian.setBounds(10, 54, 404, 25);
		panel.add(rboFullHessian);
		fitButtonGroup.add(rboFullHessian);
		rboFullHessian.setToolTipText("involves the calculation of the full numerical hessian instead of just the diagonal elements.");
		chkUseGA.setBounds(10, 85, 182, 25);
		panel.add(chkUseGA);
		chkUseGA.addActionListener(keyGA);

		chkRelax.setToolTipText("<html>	Invokes fitting to structural displacements on relaxation rather <br>"
				+ "than to the derivatives. also means any observables are fitted at the <br>"
				+ "optimized rather than experimental structure. There is no need to give simultaneous <br>"
				+ "as an option if relax fitting. method should only be used once a reasonable set of <br>"
				+ "potentials have been obtained by conventional fitting, otherwise the optimizations may fail. <br>"
				+ "It is also an order of magnitude more expensive in cputime!</html>");
		chkRelax.setBounds(10, 116, 558, 25);
		panel.add(chkRelax);
		chkRelax.addActionListener(keyRelax);

		chkOptimisefitShellsBut.setBounds(10, 147, 265, 25);
		panel.add(chkOptimisefitShellsBut);
		chkOptimisefitShellsBut.addActionListener(keyOptimisefitShellsBut);

		chkSimultaneous.addActionListener(keySimultaneous);
		chkSimultaneous.setBounds(10, 178, 647, 30);
		panel.add(chkSimultaneous);
	}

	public String writeFitOptions() {
		String lines = "";
		final String max = txtMaxCycfit.getText();
		if (max.equals("") && max.equals(TXTMAXCYCFIT)) {
			Double.parseDouble(max);
			lines += "maxcyc fit " + max + Back.newLine;
		}
		final String delta = txtFxDelta.getText();
		if (delta.equals("") && delta.equals("0.00001")) {
			Double.parseDouble(delta);
			lines = "delta " + delta + Back.newLine;
		}
		final String print = txtOutputFittingParam.getText();
		if (!print.equals("")) {
			Integer.parseInt(print);
			lines += "print " + print + Back.newLine;
		}
		final String ftol = txtFtol.getText();
		if (ftol.equals("") && ftol.equals("0.00001")) {
			Double.parseDouble(ftol);
			lines = "ftol " + ftol + Back.newLine;
		}
		final String gtol = txtGtol.getText();
		if (gtol.equals("") && gtol.equals("0.0001")) {
			Double.parseDouble(gtol);
			lines = "gtol " + gtol + Back.newLine;
		}
		final String gmax = txtGmax.getText();
		if (gmax.equals("") && gmax.equals("0.001")) {
			Double.parseDouble(gmax);
			lines = "gmax " + gmax + Back.newLine;
		}
		final String stepmx = txtStepmxFit.getText();
		if (stepmx.equals("") && stepmx.equals("1.0")) {
			Double.parseDouble(stepmx);
			lines = "stepmx fit " + stepmx + Back.newLine;
		}
		final String xtol = txtXtolFit.getText();
		if (!xtol.equals("") && !xtol.equals("0.00001")) {
			Double.parseDouble(xtol);
			lines += "xtol fit " + xtol + Back.newLine;
		}
		return lines;
	}

	private final SerialListener keyGA = new SerialListener() {
		private static final long serialVersionUID = -4174465371049719236L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(chkUseGA.isSelected(), "genetic");
		}
	};

	private final SerialListener keyTypeOfHessian = new SerialListener() {
		private static final long serialVersionUID = -4174465371049719236L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getTaskKeywords().putOrRemoveTaskKeyword(rboFullHessian.isSelected(), "fbfgs");
		}
	};
}