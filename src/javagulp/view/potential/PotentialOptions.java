package javagulp.view.potential;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.model.GenericTableModel;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PotentialOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -2476083163680364699L;

	private final G g = new G();

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private final JCheckBox chkNoListBased = new JCheckBox("no list-based methods for three-/four-body potentials");

	public GenericTableModel polarisabilityTableModel = new GenericTableModel(new String[] {"atom", g.html("dipolar polarisibility (" + g.ang + g.sup("3") + ")") }, 0);
	private final JTable table = new JTable(polarisabilityTableModel);
	private final JScrollPane scrollPane = new JScrollPane(table);

	public JComboBox cboSpecies = new JComboBox();

	private final ResetPotentials pnlResetInteratomic = new ResetPotentials();

	private final JTextField txtCutoffBondLength = new JTextField();
	private final JTextField txtScale = new JTextField();

	private final JRadioButton radRetainColoumbic = new JRadioButton("retain the coloumbic interaction between its atoms");
	private final JRadioButton radRemoveColoumbic = new JRadioButton("remove the coloumbic interaction between its atoms");

	private final JCheckBox chkSetAveragePotential = new JCheckBox("set average potential across lattice sites to 0 (for surface/bulk comparisons)");
	private final JCheckBox chkDoNotUseCutoff = new JCheckBox("<html>do not use a cutoff for exponential repulsive terms when they become less than the accuracy factor (default=10<sup>-8</sup>) to save computer time but use cutoff given in the input file</html>");
	private final JCheckBox chkRemoveColoumbic = new JCheckBox("<html>remove the coloumbic interaction between bonded atoms and atoms with a bonded atom in common</html>");
	private final JCheckBox chkDoNotStop = new JCheckBox("do not stop execution if molecule becomes unbonded");
	private final JCheckBox chkPrintThreeBody = new JCheckBox("print three-body angles found for three-body potentials");
	private final JCheckBox chkOutputList = new JCheckBox("output list of valid torsional terms before and after a calculation");
	public JCheckBox chkDoNotInclude = new JCheckBox("do not include library potentials in gulp output");
	final JCheckBox chkFit = new JCheckBox("fit");

	private final KeywordListener keySetAveragePotential = new KeywordListener(chkSetAveragePotential, "zero_potential");
	private final KeywordListener keyDoNotUseCutoff = new KeywordListener(chkDoNotUseCutoff, "norepulsive_cutoff");
	private final KeywordListener keyChkRemoveColoumbic = new KeywordListener(chkRemoveColoumbic, "molmec");
	private final KeywordListener keyDoNotStop = new KeywordListener(chkDoNotStop,
	"fix_molecule");
	private final KeywordListener keyPrintThreeBody = new KeywordListener(chkPrintThreeBody, "angle");
	private final KeywordListener keyOutputList = new KeywordListener(chkOutputList,
	"torsion");
	private final KeywordListener keyNoListBased = new KeywordListener(chkNoListBased, "nolist_md");

	private final SerialListener keyCoulombGroup = new SerialListener() {
		private static final long serialVersionUID = -1256050152081117096L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(radRemoveColoumbic.isSelected(), "molecule");
			Back.getKeys().putOrRemoveKeyword(radRetainColoumbic.isSelected(), "molq");
		}
	};


	public PotentialOptions() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(1050, 399));

		final TitledPanel pnlCutoffBondLength = new TitledPanel();
		pnlCutoffBondLength.setTitle("cutoff bondlength search at");
		pnlCutoffBondLength.setBounds(0, 0, 383, 49);
		add(pnlCutoffBondLength);
		txtCutoffBondLength.setBounds(15, 22, 84, 20);
		pnlCutoffBondLength.add(txtCutoffBondLength);
		final JLabel angLabel = new JLabel(g.html("(" + g.ang + ")"));
		angLabel.setBounds(105, 24, 20, 15);
		pnlCutoffBondLength.add(angLabel);

		add(pnlResetInteratomic);
		pnlResetInteratomic.setBounds(0, 55, 383, 127);

		final TitledPanel pnlSetDipole = new TitledPanel();
		pnlSetDipole.setTitle("set dipole polarisability");
		pnlSetDipole.setBounds(0, 289, 383, 159);
		add(pnlSetDipole);
		scrollPane.setBounds(10, 22, 363, 127);
		pnlSetDipole.add(scrollPane);

		polarisabilityTableModel.uneditableColumns = new int[] { 0 };
		polarisabilityTableModel.requiredColumns = new int[] { 1 };
		final IconHeaderRenderer iconHeaderRenderer = new IconHeaderRenderer();
		table.getColumnModel().getColumn(1).setHeaderRenderer(iconHeaderRenderer);

		final TitledPanel pnlIdentifyMolecules = new TitledPanel();
		pnlIdentifyMolecules.setTitle("identify molecules based on covalent radii but");
		pnlIdentifyMolecules.setBounds(389, 250, 771, 179);
		add(pnlIdentifyMolecules);
		buttonGroup.add(radRetainColoumbic);
		radRetainColoumbic.addActionListener(keyCoulombGroup);
		radRetainColoumbic.setBounds(10, 51, 500, 25);
		pnlIdentifyMolecules.add(radRetainColoumbic);
		radRemoveColoumbic.addActionListener(keyCoulombGroup);
		buttonGroup.add(radRemoveColoumbic);
		radRemoveColoumbic.setBounds(10, 20, 500, 25);
		pnlIdentifyMolecules.add(radRemoveColoumbic);
		chkRemoveColoumbic.addActionListener(keyChkRemoveColoumbic);
		chkRemoveColoumbic.setBounds(10, 82, 681, 47);
		pnlIdentifyMolecules.add(chkRemoveColoumbic);
		chkDoNotStop.addActionListener(keyDoNotStop);
		chkDoNotStop.setBounds(10, 135, 500, 28);
		pnlIdentifyMolecules.add(chkDoNotStop);

		final TitledPanel pnlScalingTransformation = new TitledPanel();
		pnlScalingTransformation.setTitle("apply a scaling transformation to EAM");
		pnlScalingTransformation.setBounds(0, 188, 383, 95);
		add(pnlScalingTransformation);
		txtScale.setBounds(233, 68, 69, 20);
		pnlScalingTransformation.add(txtScale);
		final JLabel lblEquations = new JLabel("<html>f'(sum(" + g.rho
				+ "<sub>i</sub>)) = f(sum(" + g.rho
				+ "<sub>i</sub>)/scale<sub>i</sub>)<br> " + g.rho
				+ "'<sub>i</sub> = scale<sub>i</sub> " + g.rho
				+ "<sub>i</sub></html>");
		lblEquations.setBounds(8, 17, 358, 45);
		pnlScalingTransformation.add(lblEquations);
		final JLabel lblSpecies = new JLabel("species");
		lblSpecies.setBounds(8, 68, 63, 15);
		pnlScalingTransformation.add(lblSpecies);
		final JLabel lblScale = new JLabel("scale");
		lblScale.setBounds(178, 67, 50, 20);
		pnlScalingTransformation.add(lblScale);
		chkFit.setBounds(316, 63, 50, 25);
		pnlScalingTransformation.add(chkFit);
		cboSpecies.setBounds(74, 67, 85, 20);
		pnlScalingTransformation.add(cboSpecies);

		final TitledPanel pnlOptions = new TitledPanel();
		pnlOptions.setTitle("options");
		pnlOptions.setBounds(387, 0, 773, 244);
		add(pnlOptions);

		chkPrintThreeBody.addActionListener(keyPrintThreeBody);
		chkPrintThreeBody.setBounds(10, 22, 590, 25);
		pnlOptions.add(chkPrintThreeBody);

		chkDoNotInclude.setBounds(10, 53, 590, 25);
		pnlOptions.add(chkDoNotInclude);
		chkNoListBased.addActionListener(keyNoListBased);
		chkNoListBased.setBounds(10, 84, 590, 25);
		pnlOptions.add(chkNoListBased);
		chkOutputList.addActionListener(keyOutputList);
		chkOutputList.setBounds(10, 115, 638, 25);
		pnlOptions.add(chkOutputList);
		chkSetAveragePotential.addActionListener(keySetAveragePotential);
		chkSetAveragePotential.setBounds(10, 146, 709, 24);
		pnlOptions.add(chkSetAveragePotential);

		chkDoNotUseCutoff.addActionListener(keyDoNotUseCutoff);
		chkDoNotUseCutoff.setBounds(10, 176, 709, 58);
		pnlOptions.add(chkDoNotUseCutoff);
		chkDoNotUseCutoff.setAlignmentY(Component.TOP_ALIGNMENT);
	}

	private String writeEamAlloy() throws IncompleteOptionException {
		String lines = "";
		if (cboSpecies.getSelectedIndex() > 0) {
			if (txtScale.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for EAM scaling transformation.");
			Double.parseDouble(txtScale.getText());
			lines = "eam_alloy " + cboSpecies.getSelectedItem() + " " + txtScale.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeCutd() {
		String lines = "";
		final String length = txtCutoffBondLength.getText();
		if (!length.equals("")) {
			Double.parseDouble(length);
			lines = "cutd " + length + Back.newLine;
		}
		return lines;
	}

	public String writePotentialOptions() throws IncompleteOptionException {
		return polarisabilityTableModel.writeTable("polarisability ")
		+ writeEamAlloy() + writeCutd()
		+ pnlResetInteratomic.writeCutp();
	}
}