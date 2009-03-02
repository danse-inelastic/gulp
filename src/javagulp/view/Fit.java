package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javagulp.view.fit.AbstractFit;
import javagulp.view.fit.FitPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;

public class Fit extends AbstractFit implements Serializable {

	private static final long serialVersionUID = -4430341489632365418L;

	private G g = new G();

	private JTextField txtFxDelta = new JTextField("0.00001");
	private final String TXTMAXCYCFIT = "500 * <no. of fitting variables>";
	private JTextField txtMaxCycfit = new JTextField(TXTMAXCYCFIT);
	private JTextField txtXtolFit = new JTextField("0.00001");
	private JTextField txtStepmxFit = new JTextField("1.0");
	private JTextField txtGtol = new JTextField("0.0001");
	private JTextField txtGmax = new JTextField("0.001");
	private JTextField txtFtol = new JTextField("0.00001");
	private JTextField txtOutputFittingParam = new JTextField();

	private JCheckBox chkSimultaneous = new JCheckBox(g.html("do simultaneous relaxation of shells <br>during fitting, including both position <br>and radius"));
	private JCheckBox chkRelax = new JCheckBox("fit to structural displacements on relaxation rather than to the derivatives");
	private JCheckBox chkFit = new JCheckBox("do fitting run using unit matrix with BFGS method");
	private JCheckBox chkDoFittingRun = new JCheckBox("do fitting run using full BFGS method");
	private JCheckBox chkOptimisefitShellsBut = new JCheckBox("fit only shells (optical calculation)");
	private JCheckBox chkUseGA = new JCheckBox("use genetic algorithm");
	
	public FitPanel fitPanel = new FitPanel();
	private JList fitList = new JList(fitPanel.fitListModel);
	private JScrollPane listScroll = new JScrollPane(fitList);

	private KeywordListener keySimultaneous = new KeywordListener(chkSimultaneous, "simultaneous");
	private KeywordListener keyRelax = new KeywordListener(chkRelax, "relax");
	private KeywordListener keyFit = new KeywordListener(chkFit, "fit");
	private KeywordListener keyDoFittingRun = new KeywordListener(chkDoFittingRun, "fbfgs");
	private KeywordListener keyOptimisefitShellsBut = new KeywordListener(chkOptimisefitShellsBut, "shell");

	SerialKeyAdapter listListener = new SerialKeyAdapter() {
		private static final long serialVersionUID = -8369027767995116365L;

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to remove this fit?") == JOptionPane.YES_OPTION) {
					if (fitPanel.fitListModel.getSize() > 0) {
						int index = fitList.getSelectedIndex();
						fitPanel.fitListModel.remove(index);
						fitPanel.fits.remove(index);
					}
				}
			}
		}
	};
	
	private SerialMouseAdapter keyList = new SerialMouseAdapter() {
		private static final long serialVersionUID = 5923969703181724344L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if (fitPanel.fitListModel.getSize() > 0) {
				fitPanel.scrollFit.setViewportView(fitPanel.fits.get(fitList.getSelectedIndex()));
			}
		}
	};

	public Fit() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(742, 350));

		add(listScroll);
		listScroll.setBounds(555, 4, 203, 329);
		fitList.addKeyListener(listListener);
		fitList.addMouseListener(keyList);

		chkSimultaneous.addActionListener(keySimultaneous);
		chkSimultaneous.setBounds(764, 31, 265, 52);
		add(chkSimultaneous);

		chkRelax.setToolTipText("<html>	Invokes fitting to structural displacements on relaxation rather <br>"
			+ "than to the derivatives. also means any observables are fitted at the <br>"
			+ "optimized rather than experimental structure. There is no need to give simultaneous <br>"
			+ "as an option if relax fitting. method should only be used once a reasonable set of <br>"
			+ "potentials have been obtained by conventional fitting, otherwise the optimizations may fail. <br>"
			+ "It is also an order of magnitude more expensive in cputime!</html>");
		chkRelax.addActionListener(keyRelax);
		chkRelax.setBounds(3, 85, 498, 25);
		add(chkRelax);

		final TitledPanel pnlParameterTolerance = new TitledPanel();
		pnlParameterTolerance.setTitle("parameter tolerance");
		pnlParameterTolerance.setBounds(1035, 214, 208, 48);
		add(pnlParameterTolerance);
		txtXtolFit.setBounds(22, 23, 80, 21);
		pnlParameterTolerance.add(txtXtolFit);

		final TitledPanel pnlMaxStepSize = new TitledPanel();
		pnlMaxStepSize.setTitle("maximum step size");
		pnlMaxStepSize.setBounds(1035, 162, 208, 46);
		add(pnlMaxStepSize);
		txtStepmxFit.setBounds(22, 19, 80, 21);
		pnlMaxStepSize.add(txtStepmxFit);

		fitPanel.setBounds(3, 116, 546, 244);
		fitPanel.scrollFit.setBounds(10, 64, 526, 172);
		add(fitPanel);

		final TitledPanel pnlMaxNumOfCycles = new TitledPanel();
		pnlMaxNumOfCycles.setTitle("maximum number of cycles");
		pnlMaxNumOfCycles.setBounds(764, 89, 265, 51);
		add(pnlMaxNumOfCycles);
		txtMaxCycfit.setBounds(10, 22, 190, 20);
		pnlMaxNumOfCycles.add(txtMaxCycfit);

		final TitledPanel pnlGradientTolerance = new TitledPanel();
		pnlGradientTolerance.setTitle("gradient tolerance");
		pnlGradientTolerance.setBounds(1035, 58, 208, 47);
		add(pnlGradientTolerance);
		txtGtol.setBounds(22, 19, 80, 21);
		pnlGradientTolerance.add(txtGtol);

		final TitledPanel pnlMaxIndividualGradient = new TitledPanel();
		pnlMaxIndividualGradient.setTitle("maximum individual gradient");
		pnlMaxIndividualGradient.setBounds(1035, 111, 208, 45);
		add(pnlMaxIndividualGradient);
		txtGmax.setBounds(22, 19, 80, 21);
		pnlMaxIndividualGradient.add(txtGmax);

		final TitledPanel pnlFuncTolerance = new TitledPanel();
		pnlFuncTolerance.setTitle("function tolerance");
		pnlFuncTolerance.setBounds(1035, 5, 208, 47);
		add(pnlFuncTolerance);
		txtFtol.setBounds(22, 19, 80, 21);
		pnlFuncTolerance.add(txtFtol);

		chkFit.setBounds(3, 5, 483, 25);
		chkFit.addActionListener(keyFit);
		add(chkFit);
		add(chkUseGA);
		chkUseGA.setBounds(3, 58, 182, 25);
		chkUseGA.addActionListener(keyGA);
		

		chkDoFittingRun.addActionListener(keyDoFittingRun);
		chkDoFittingRun.setToolTipText("involves the calculation of the full numerical hessian instead of just the diagonal elements.");
		chkDoFittingRun.setBounds(3, 30, 404, 25);
		add(chkDoFittingRun);

		final TitledPanel pnlNumericalDifferencing = new TitledPanel();
		pnlNumericalDifferencing.setTitle("numerical differencing");
		pnlNumericalDifferencing.setBounds(764, 146, 265, 52);
		add(pnlNumericalDifferencing);
		txtFxDelta.setBounds(9, 23, 92, 20);
		pnlNumericalDifferencing.add(txtFxDelta);
		JLabel lblFxDeltaFormula = new JLabel(g.html(("df(x)/dx = (f(x + "
				+ g.delta + ") - f(x))/" + g.delta)));
		lblFxDeltaFormula.setBounds(9, 19, 191, 27);
		pnlNumericalDifferencing.add(lblFxDeltaFormula);
		JLabel lblFxDelta = new JLabel(g.html(g.delta + " (&Aring;)"));
		lblFxDelta.setBounds(9, 54, 35, 15);
		pnlNumericalDifferencing.add(lblFxDelta);

		chkOptimisefitShellsBut.setBounds(764, 5, 265, 25);
		add(chkOptimisefitShellsBut);
		chkOptimisefitShellsBut.addActionListener(keyOptimisefitShellsBut);

		final TitledPanel pnlOutputFittingParam = new TitledPanel();
		pnlOutputFittingParam.setTitle("output fitting parameters");
		pnlOutputFittingParam.setBounds(764, 214, 265, 51);
		add(pnlOutputFittingParam);
		JLabel lblOutputFittingParameters = new JLabel("every (cycles)");
		lblOutputFittingParameters.setBounds(10, 26, 85, 15);
		pnlOutputFittingParam.add(lblOutputFittingParameters);
		txtOutputFittingParam.setBounds(101, 23, 57, 21);
		pnlOutputFittingParam.add(txtOutputFittingParam);
	}

	@Override
	public String writeFit() {
		String lines = "";
		String max = txtMaxCycfit.getText();
		if (max.equals("") && max.equals(TXTMAXCYCFIT)) {
			Double.parseDouble(max);
			lines += "maxcyc fit " + max + Back.newLine;
		}
		String delta = txtFxDelta.getText();
		if (delta.equals("") && delta.equals("0.00001")) {
			Double.parseDouble(delta);
			lines = "delta " + delta + Back.newLine;
		}
		String print = txtOutputFittingParam.getText();
		if (!print.equals("")) {
			Integer.parseInt(print);
			lines += "print " + print + Back.newLine;
		}
		String ftol = txtFtol.getText();
		if (ftol.equals("") && ftol.equals("0.00001")) {
			Double.parseDouble(ftol);
			lines = "ftol " + ftol + Back.newLine;
		}
		String gtol = txtGtol.getText();
		if (gtol.equals("") && gtol.equals("0.0001")) {
			Double.parseDouble(gtol);
			lines = "gtol " + gtol + Back.newLine;
		}
		String gmax = txtGmax.getText();
		if (gmax.equals("") && gmax.equals("0.001")) {
			Double.parseDouble(gmax);
			lines = "gmax " + gmax + Back.newLine;
		}
		String stepmx = txtStepmxFit.getText();
		if (stepmx.equals("") && stepmx.equals("1.0")) {
			Double.parseDouble(stepmx);
			lines = "stepmx fit " + stepmx + Back.newLine;
		}
		String xtol = txtXtolFit.getText();
		if (!xtol.equals("") && !xtol.equals("0.00001")) {
			Double.parseDouble(xtol);
			lines += "xtol fit " + xtol + Back.newLine;
		}
		return lines;
	}
	
	private SerialListener keyGA = new SerialListener() {
		private static final long serialVersionUID = -4174465371049719236L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(chkUseGA.isSelected(), "genetic");
		}
	};
}