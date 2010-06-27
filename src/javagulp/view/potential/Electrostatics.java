package javagulp.view.potential;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.model.GenericTableModel;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;
import javagulp.view.potential.electrostatics.CalculatePotential;
import javagulp.view.potential.electrostatics.Mortiers;
import javagulp.view.potential.electrostatics.Qeq;
import javagulp.view.potential.electrostatics.SnM;

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

public class Electrostatics extends JPanel implements Serializable {

	private JPanel panel_1;
	private JPanel panel;
	private static final long serialVersionUID = -1049388362113135590L;

	private final JButton btnSet = new JButton("set");

	private final G g = new G();

	private final JComboBox cboCoordinates = new JComboBox(new String[] {
			"fractional", g.html("cartesian (" + g.ang + ")") });

	private final JCheckBox chkElectrostaticSitePotentials = new JCheckBox("print electrostatic site potentials and their first derivatives");
	private final JCheckBox chkFirstDerivative = new JCheckBox("<html>calculate first derivatives of atomic charges with respect to atomic coordinates and strain as calculated by  EEM or QEq</html>");
	public final JCheckBox chkMortiers = new JCheckBox("Mortiers electronegativity equalization");
	public final JCheckBox chkqeq = new JCheckBox("QEQ electronegativity equalization");
	public final JCheckBox chkStreitzAndMintmire = new JCheckBox("Streitz and Mintmire electronegativity equalization");



	private final JLabel lblConvergedFigures = new JLabel("target number of converged significant figures");
	private final JLabel lblNumberOfPoints = new JLabel("<html>number of points</html>");
	private final JLabel lblOrderOfSeries = new JLabel("order of series in numerical integrals");
	private final JLabel lblUpperBound = new JLabel("upper bound in real space sum");

	public Mortiers pnlMortiers = new Mortiers();
	public Qeq pnlqeq = new Qeq();
	public SnM pnlSnm = new SnM();

	private final TitledPanel pnlAccuracy = new TitledPanel();
	private final CalculatePotential pnlCalculatePotential = new CalculatePotential();
	private final TitledPanel pnlSpecifyPoints = new TitledPanel();
	private final JPanel pnlOneDimensional = new JPanel();
	private final JCheckBox radPrintOutElectric = new JCheckBox(g.html("print electric field gradient tensor and asymmetry parameter at the atomic sites"));
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();
	private final JTabbedPane eeChoice = new JTabbedPane();
	private final GenericTableModel electrostaticsTableModel = new GenericTableModel(new String[] { "x", "y", "z" }, 0);

	// private JTextField smAtomTextField = new JTextField();
	// private JTextField qeqAtomTextField = new JTextField();
	// private JTextField eemAtomTextField = new JTextField();
	// this textfield is never read
	private final JTextField txtConvergedFigures = new JTextField("8");
	private final JTextField txtUpperBound = new JTextField("4");
	private final JTextField txtOrderOfSeries = new JTextField("20");
	private final JTextField txtNumberOfPoints = new JTextField();

	private final SerialListener keySet = new SerialListener() {
		private static final long serialVersionUID = -705014700308507226L;
		@Override
		public void actionPerformed(ActionEvent e) {
			electrostaticsTableModel.setRowCount(Integer.parseInt(txtNumberOfPoints.getText()));
		}
	};

	private final KeywordListener keyPrintOutElectric = new KeywordListener(radPrintOutElectric, "efg");
	private final KeywordListener keyFirstDerivative = new KeywordListener(chkFirstDerivative, "dcharge");
	private final KeywordListener keyElectrostaticSitePotentials = new KeywordListener(chkElectrostaticSitePotentials, "pot");
	private final KeywordListener keyMortiers = new KeywordListener(chkMortiers, "eem");
	private final KeywordListener keyqeq = new KeywordListener(chkqeq, "qeq");
	private final KeywordListener keyStreitzAndMintmire = new KeywordListener(chkStreitzAndMintmire, "sm");

	// TODO add accuracy keyword
	public Electrostatics() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(973, 336));

		// set up tabbed pane and three sub panes
		eeChoice.setBounds(0, 2, 521, 209);
		//add(eeChoice); // just don't let users alter this for now TODO: in future make this a set of panels where users can add charge calculation settings for a given atom
		eeChoice.add(pnlMortiers, "Mortiers");
		eeChoice.add(pnlqeq, "QEq");
		eeChoice.add(pnlSnm, "Streitz and Mintmire");
		pnlSnm.cbosmatom.setBounds(56, 49, 79, 26);

		pnlSpecifyPoints.setToolTipText("Allows the user to specify points in space at which the electrostatic potential should be calculated. Note that it is necessary to also specify the \"pot\" keyword to trigger the calculation of the potential.");
		pnlSpecifyPoints.setTitle("specify points at which the electrostatic potential is calculated");
		pnlSpecifyPoints.setBounds(527, 279, 604, 126);
		add(pnlSpecifyPoints);
		lblNumberOfPoints.setVerticalAlignment(SwingConstants.TOP);
		lblNumberOfPoints.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNumberOfPoints.setBounds(10, 19, 175, 15);
		pnlSpecifyPoints.add(lblNumberOfPoints);
		txtNumberOfPoints.setBounds(10, 40, 38, 20);
		pnlSpecifyPoints.add(txtNumberOfPoints);
		scrollPane.setBounds(240, 18, 301, 98);
		pnlSpecifyPoints.add(scrollPane);
		table.setModel(electrostaticsTableModel);
		scrollPane.setViewportView(table);
		btnSet.addActionListener(keySet);
		btnSet.setBounds(65, 40, 90, 20);
		pnlSpecifyPoints.add(btnSet);
		cboCoordinates.setBounds(10, 66, 145, 24);
		pnlSpecifyPoints.add(cboCoordinates);

		pnlCalculatePotential.setBounds(527, 160, 604, 113);
		add(pnlCalculatePotential);

		pnlAccuracy.setTitle("electrostatic summation accuracy");
		pnlAccuracy.setBounds(0, 137, 521, 142);
		add(pnlAccuracy);
		lblConvergedFigures.setBounds(10, 24, 331, 15);
		pnlAccuracy.add(lblConvergedFigures);
		txtConvergedFigures.setBackground(Back.grey);
		txtConvergedFigures.setBounds(347, 22, 59, 20);
		pnlAccuracy.add(txtConvergedFigures);


		pnlOneDimensional.setLayout(null);
		pnlOneDimensional.setBorder(new TitledBorder(null, "1d systems",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlOneDimensional.setBounds(10, 61, 396, 71);
		pnlAccuracy.add(pnlOneDimensional);
		lblUpperBound.setBounds(9, 17, 310, 15);
		pnlOneDimensional.add(lblUpperBound);
		txtUpperBound.setBackground(Back.grey);
		txtUpperBound.setBounds(325, 15, 61, 20);
		pnlOneDimensional.add(txtUpperBound);
		lblOrderOfSeries.setBounds(9, 41, 310, 15);
		pnlOneDimensional.add(lblOrderOfSeries);
		txtOrderOfSeries.setBackground(Back.grey);
		txtOrderOfSeries.setBounds(325, 39, 61, 20);
		pnlOneDimensional.add(txtOrderOfSeries);
		final TitledPanel pnlOptions = new TitledPanel();
		pnlOptions.setBounds(527, 5, 604, 149);
		pnlOptions.setTitle("options");
		add(pnlOptions);

		final TitledPanel pnlCalculate = new TitledPanel();
		pnlCalculate.setBounds(0, 5, 521, 126);
		pnlCalculate.setTitle("calculate charges");
		add(pnlCalculate);
		chkMortiers.addActionListener(keyMortiers);
		chkMortiers.setBounds(10, 22, 501, 25);
		pnlCalculate.add(chkMortiers);
		chkqeq.addActionListener(keyqeq);
		chkqeq.setBounds(10, 53, 462, 25);
		pnlCalculate.add(chkqeq);
		chkStreitzAndMintmire.addActionListener(keyStreitzAndMintmire);
		chkStreitzAndMintmire.setBounds(10, 84, 501, 25);
		pnlCalculate.add(chkStreitzAndMintmire);

		chkElectrostaticSitePotentials.addActionListener(keyElectrostaticSitePotentials);
		chkElectrostaticSitePotentials.setBounds(10, 21, 584, 30);
		pnlOptions.add(chkElectrostaticSitePotentials);
		chkFirstDerivative.addActionListener(keyFirstDerivative);
		chkFirstDerivative.setBounds(10, 49, 584, 40);
		pnlOptions.add(chkFirstDerivative);

		radPrintOutElectric.addActionListener(keyPrintOutElectric);
		radPrintOutElectric.setBounds(10, 95, 584, 35);
		pnlOptions.add(radPrintOutElectric);

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
		return writeElectrostaticCalculationPoints()
		+ pnlCalculatePotential.writePotgrid();
//		+ pnlqeq.writeQeq()
//		+ pnlMortiers.writeElectronegativity()
//		+ pnlSnm.writeSmelectronegativity();
	}

}