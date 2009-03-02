package javagulp.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.GenericTableModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.potentialOptions.Mortiers;
import javagulp.view.potentialOptions.Qeq;
import javagulp.view.potentialOptions.SnM;

public class Electrostatics extends JPanel implements Serializable {

	private static final long serialVersionUID = -1049388362113135590L;

	private class CalculatePotential extends TitledPanel implements Serializable {

		private static final long serialVersionUID = -8320177136881534497L;

		private JLabel lblxMinMax = new JLabel("xmin, xmax");
		private JLabel lblyMinMax = new JLabel("ymin, ymax");
		private JLabel lblzMinMax = new JLabel("zmin, zmax");

		private JLabel lblxPoints = new JLabel("number of x points");
		private JLabel lblyPoints = new JLabel("number of y points");
		private JLabel lblzPoints = new JLabel("number of z points");

		private JTextField txtx = new JTextField();
		private JTextField txtxmin = new JTextField("0");
		private JTextField txtxmax = new JTextField("1");

		private JTextField txty = new JTextField();
		private JTextField txtymin = new JTextField("0");
		private JTextField txtymax = new JTextField("1");

		private JTextField txtz = new JTextField();
		private JTextField txtzmin = new JTextField("0");
		private JTextField txtzmax = new JTextField("1");

		private CalculatePotential() {
			super();

			setToolTipText("");
			setTitle("calculate electrostatic potential on a grid");
			lblxMinMax.setBounds(209, 19, 75, 15);
			add(lblxMinMax);
			txtxmin.setBackground(Back.grey);
			txtxmin.setBounds(290, 17, 64, 20);
			add(txtxmin);
			txtxmax.setBackground(Back.grey);
			txtxmax.setBounds(360, 17, 64, 20);
			add(txtxmax);
			txtymin.setBackground(Back.grey);
			txtymin.setBounds(290, 43, 64, 20);
			add(txtymin);
			txtymax.setBackground(Back.grey);
			txtymax.setBounds(360, 43, 64, 20);
			add(txtymax);
			txtzmin.setBackground(Back.grey);
			txtzmin.setBounds(290, 69, 64, 20);
			add(txtzmin);
			txtzmax.setBackground(Back.grey);
			txtzmax.setBounds(360, 69, 64, 20);
			add(txtzmax);
			lblyMinMax.setBounds(209, 45, 75, 15);
			add(lblyMinMax);
			lblzMinMax.setBounds(209, 71, 75, 15);
			add(lblzMinMax);
			lblxPoints.setBounds(10, 19, 120, 15);
			add(lblxPoints);
			txtx.setBounds(139, 17, 64, 20);
			add(txtx);
			txty.setBounds(139, 43, 64, 20);
			add(txty);
			txtz.setBounds(139, 69, 64, 20);
			add(txtz);
			lblyPoints.setBounds(10, 45, 120, 15);
			add(lblyPoints);
			lblzPoints.setBounds(10, 71, 120, 15);
			add(lblzPoints);
		}

		private String writePotgrid() throws IncompleteOptionException {
			String lines = "";
			JTextField[] fields = {txtx, txty, txtz};
			if (Back.checkAnyNonEmpty(fields)) {
				String[] descriptions = {"potential grid x", "potential grid y", "potential grid z"};
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsI(fields, descriptions);
				lines = "potgrid ";
				JTextField[] minmax = {txtxmin, txtxmax, txtymin, txtymax, txtzmin, txtzmax};
				// TODO this is not correct.  Cannot use check functions.
				if (Back.checkAnyNonEmpty(minmax)) {
					String[] minmaxDescs = {"potential grid xmin", "potential grid xmax", "potential grid ymin",
							"potential grid ymax", "potential grid zmin", "potential grid zmax"};
					Back.checkAllNonEmpty(minmax, minmaxDescs);
					double[] minmaxd = Back.parseFieldsD(minmax, minmaxDescs);
					// TODO check if numbers are valid?
					lines += Back.concatFields(minmax);
				}
				lines += Back.concatFields(fields) + Back.newLine;
			}
			return lines;
		}
	}

	private JButton btnSet = new JButton("set");

	private G g = new G();

	private JComboBox cboCoordinates = new JComboBox(new String[] {
			"fractional", g.html("cartesian (" + g.ang + ")") });

	private JCheckBox chkElectrostaticSitePotentials = new JCheckBox("print electrostatic site potentials and their first derivatives");
	private JCheckBox chkFirstDerivative = new JCheckBox("<html>calculate first derivatives of atomic charges with respect to atomic coordinates and strain as calculated by  EEM or QEq</html>");
	
	private JLabel lblConvergedFigures = new JLabel("target number of converged significant figures");
	private JLabel lblNumberOfPoints = new JLabel("<html>number of points</html>");
	private JLabel lblOrderOfSeries = new JLabel("order of series in numerical integrals");
	private JLabel lblUpperBound = new JLabel("upper bound in real space sum");

	public Mortiers pnlMortiers = new Mortiers();
	public Qeq pnlqeq = new Qeq();
	public SnM snm = new SnM();

	private TitledPanel pnlAccuracy = new TitledPanel();
	private CalculatePotential pnlCalculatePotential = new CalculatePotential();
	private TitledPanel pnlSpecifyPoints = new TitledPanel();
	private JPanel pnlOneDimensional = new JPanel();
	private JCheckBox radPrintOutElectric = new JCheckBox(g.html("print electric field gradient tensor and asymmetry parameter at the atomic sites"));
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table = new JTable();
	private JTabbedPane eeChoice = new JTabbedPane();
	private GenericTableModel electrostaticsTableModel = new GenericTableModel(new String[] { "x", "y", "z" }, 0);

	// private JTextField smAtomTextField = new JTextField();
	// private JTextField qeqAtomTextField = new JTextField();
	// private JTextField eemAtomTextField = new JTextField();
	// this textfield is never read
	private JTextField txtConvergedFigures = new JTextField("8");
	private JTextField txtUpperBound = new JTextField("4");
	private JTextField txtOrderOfSeries = new JTextField("20");
	private JTextField txtNumberOfPoints = new JTextField();

	private SerialListener keySet = new SerialListener() {
		private static final long serialVersionUID = -705014700308507226L;
		@Override
		public void actionPerformed(ActionEvent e) {
			electrostaticsTableModel.setRowCount(Integer.parseInt(txtNumberOfPoints.getText()));
		}
	};
	
	private KeywordListener keyPrintOutElectric = new KeywordListener(radPrintOutElectric, "efg");
	private KeywordListener keyFirstDerivative = new KeywordListener(chkFirstDerivative, "dcharge");
	private KeywordListener keyElectrostaticSitePotentials = new KeywordListener(chkElectrostaticSitePotentials, "pot");
	// TODO add accuracy keyword
	public Electrostatics() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(973, 336));

		// set up tabbed pane and three sub panes
		eeChoice.setBounds(0, 2, 487, 209);
		add(eeChoice);
		eeChoice.add(pnlMortiers, "Mortiers");
		eeChoice.add(pnlqeq, "QEq");
		eeChoice.add(snm, "Streitz and Mintmire");

		pnlSpecifyPoints.setToolTipText("Allows the user to specify points in space at which the electrostatic potential should be calculated. Note that it is necessary to also specify the \"pot\" keyword to trigger the calculation of the potential.");
		pnlSpecifyPoints.setTitle("specify points at which the electrostatic potential is calculated");
		pnlSpecifyPoints.setBounds(504, 203, 462, 126);
		add(pnlSpecifyPoints);
		lblNumberOfPoints.setVerticalAlignment(SwingConstants.TOP);
		lblNumberOfPoints.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNumberOfPoints.setBounds(10, 19, 110, 15);
		pnlSpecifyPoints.add(lblNumberOfPoints);
		txtNumberOfPoints.setBounds(10, 40, 38, 20);
		pnlSpecifyPoints.add(txtNumberOfPoints);
		scrollPane.setBounds(154, 21, 301, 98);
		pnlSpecifyPoints.add(scrollPane);
		table.setModel(electrostaticsTableModel);
		scrollPane.setViewportView(table);
		btnSet.addActionListener(keySet);
		btnSet.setBounds(65, 40, 55, 20);
		pnlSpecifyPoints.add(btnSet);
		cboCoordinates.setBounds(10, 66, 132, 18);
		pnlSpecifyPoints.add(cboCoordinates);

		pnlCalculatePotential.setBounds(504, 101, 466, 94);
		add(pnlCalculatePotential);

		radPrintOutElectric.addActionListener(keyPrintOutElectric);
		radPrintOutElectric.setBounds(504, 63, 455, 35);
		add(radPrintOutElectric);
		chkFirstDerivative.addActionListener(keyFirstDerivative);
		chkFirstDerivative.setBounds(504, 23, 460, 40);
		add(chkFirstDerivative);

		pnlAccuracy.setTitle("electrostatic summation accuracy");
		pnlAccuracy.setBounds(0, 217, 487, 117);
		add(pnlAccuracy);
		lblConvergedFigures.setBounds(10, 21, 295, 15);
		pnlAccuracy.add(lblConvergedFigures);
		txtConvergedFigures.setBackground(Back.grey);
		txtConvergedFigures.setBounds(311, 19, 59, 20);
		pnlAccuracy.add(txtConvergedFigures);


		pnlOneDimensional.setLayout(null);
		pnlOneDimensional.setBorder(new TitledBorder(null, "1d systems",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlOneDimensional.setBounds(5, 41, 365, 71);
		pnlAccuracy.add(pnlOneDimensional);
		lblUpperBound.setBounds(9, 17, 195, 15);
		pnlOneDimensional.add(lblUpperBound);
		txtUpperBound.setBackground(Back.grey);
		txtUpperBound.setBounds(250, 15, 61, 20);
		pnlOneDimensional.add(txtUpperBound);
		lblOrderOfSeries.setBounds(9, 41, 235, 15);
		pnlOneDimensional.add(lblOrderOfSeries);
		txtOrderOfSeries.setBackground(Back.grey);
		txtOrderOfSeries.setBounds(250, 39, 61, 20);
		pnlOneDimensional.add(txtOrderOfSeries);

		chkElectrostaticSitePotentials.addActionListener(keyElectrostaticSitePotentials);
		chkElectrostaticSitePotentials.setBounds(504, 0, 398, 30);
		add(chkElectrostaticSitePotentials);

		// qeqAtomTextField.addKeyListener(new SerialKeyAdapter() {
		// public void keyReleased(KeyEvent e) {
		// Back.getData().put("qeq.atom",qeqAtomTextField.getText());
		// }
		// });
		// qeqAtomTextField.setBounds(50, 30, 49, 18);
		// qeqPanel.add(qeqAtomTextField);
		// smAtomTextField.addKeyListener(new SerialKeyAdapter() {
		// public void keyReleased(KeyEvent e) {
		// Back.getData().put("sm.atom",smAtomTextField.getText());
		// }
		// });
		// smAtomTextField.setBounds(51, 27, 49, 18);
		// smPanel.add(smAtomTextField);

	}

	private String writeElectrostaticCalculationPoints() {
		String lines = "";
		if (electrostaticsTableModel.getRowCount() > 0) {
			String coordinates = "";
			if (!cboCoordinates.getSelectedItem().equals("fractional"))
				coordinates = " cart";
			lines = "potsites" + coordinates + Back.newLine + electrostaticsTableModel.writeTable("");
		}
		return lines;
	}

	public String writeElectrostatics() throws IncompleteOptionException,
	InvalidOptionException {
		return pnlqeq.writeQeq() + writeElectrostaticCalculationPoints()
		+ pnlCalculatePotential.writePotgrid()
		+ pnlMortiers.writeElectronegativity()
		+ snm.writeSmelectronegativity();
	}
}