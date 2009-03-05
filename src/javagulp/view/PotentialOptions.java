package javagulp.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.GenericTableModel;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class PotentialOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -2476083163680364699L;

	private G g = new G();

	private ButtonGroup buttonGroup = new ButtonGroup();

	private JCheckBox chkNoListBased = new JCheckBox("no list-based methods for three-/four-body potentials");

	public GenericTableModel polarisabilityTableModel = new GenericTableModel(new String[] {"atom", g.html("dipolar polarisibility (" + g.ang + g.sup("3") + ")") }, 0);
	private JTable table = new JTable(polarisabilityTableModel);
	private JScrollPane scrollPane = new JScrollPane(table);

	public JComboBox cboSpecies = new JComboBox();

	private ResetPotentials pnlResetInteratomic = new ResetPotentials();

	private class ResetPotentials extends TitledPanel {

		private static final long serialVersionUID = 3973999468513730548L;

		private JLabel lblNewMaxCutoff = new JLabel(g.html("new max cutoff (must be less than normal cutoff) (" + g.ang + ")"));
		private JLabel lblTaperForm = new JLabel("taper form");
		private JLabel lblTaperRange = new JLabel(g.html("taper range (" + g.ang + ")"));
		private JTextField txtNewMaxCutoff = new JTextField();
		private JTextField txtTaperRange = new JTextField();
		private String[] taperForms = { "polynomial", "cosine", "voter" };
		private JComboBox cboTaperForm = new JComboBox(taperForms);

		public ResetPotentials() {
			super();
			setTitle("reset interatomic potential cutoffs");
			setBounds(0, 55, 375, 115);

			txtNewMaxCutoff.setBounds(194, 28, 84, 20);
			add(txtNewMaxCutoff);
			lblNewMaxCutoff.setBounds(7, 22, 182, 33);
			add(lblNewMaxCutoff);
			lblTaperForm.setBounds(8, 57, 77, 23);
			add(lblTaperForm);
			txtTaperRange.setBackground(Back.grey);
			txtTaperRange.setBounds(117, 87, 84, 20);
			add(txtTaperRange);
			lblTaperRange.setBounds(7, 89, 95, 15);
			add(lblTaperRange);
			cboTaperForm.setBounds(81, 58, 120, 21);
			add(cboTaperForm);
		}

		public String writeCutp() {
			String lines = "";
			if (!txtNewMaxCutoff.getText().equals("")) {
				Double.parseDouble(txtNewMaxCutoff.getText());
				lines += "cutp " + txtNewMaxCutoff.getText() + " "
						+ cboTaperForm.getSelectedItem();
				if (!txtTaperRange.getText().equals("")) {
					lines += " " + txtTaperRange.getText();
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}

	private JTextField txtCutoffBondLength = new JTextField();
	private JTextField txtScale = new JTextField();

	private JRadioButton radRetainColoumbic = new JRadioButton("retain the coloumbic interaction between its atoms");
	private JRadioButton radRemoveColoumbic = new JRadioButton("remove the coloumbic interaction between its atoms");

	
	private JCheckBox chkSetAveragePotential = new JCheckBox("<html>set average potential across lattice sites to 0 (for surface/bulk comparisons)</html>");
	private JCheckBox chkDoNotUseCutoff = new JCheckBox("<html>do not use a cutoff for exponential repulsive terms when they become less than the accuracy factor (default=10<sup>-8</sup>) to save computer time but use cutoff given in the input file</html>");
	private JCheckBox chkRemoveColoumbic = new JCheckBox("remove the coloumbic interaction between bonded atoms and atoms with a bonded atom in common");
	private JCheckBox chkDoNotStop = new JCheckBox("do not stop execution if molecule becomes unbonded");
	private JCheckBox chkPrintThreeBody = new JCheckBox("print three-body angles found for three-body potentials");
	private JCheckBox chkOutputList = new JCheckBox("output list of valid torsional terms before and after a calculation");
	public JCheckBox chkDoNotInclude = new JCheckBox("do not include library potentials in gulp output");
	final JCheckBox chkFit = new JCheckBox("fit");

	private KeywordListener keySetAveragePotential = new KeywordListener(chkSetAveragePotential, "zero_potential");
	private KeywordListener keyDoNotUseCutoff = new KeywordListener(chkDoNotUseCutoff, "norepulsive_cutoff");
	private KeywordListener keyChkRemoveColoumbic = new KeywordListener(chkRemoveColoumbic, "molmec");
	private KeywordListener keyDoNotStop = new KeywordListener(chkDoNotStop,
			"fix_molecule");
	private KeywordListener keyPrintThreeBody = new KeywordListener(chkPrintThreeBody, "angle");
	private KeywordListener keyOutputList = new KeywordListener(chkOutputList,
			"torsion");
	private KeywordListener keyNoListBased = new KeywordListener(chkNoListBased, "nolist_md");

	private SerialListener keyCoulombGroup = new SerialListener() {
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
		pnlCutoffBondLength.setBounds(0, 0, 378, 49);
		add(pnlCutoffBondLength);
		txtCutoffBondLength.setBounds(15, 22, 84, 20);
		pnlCutoffBondLength.add(txtCutoffBondLength);
		JLabel angLabel = new JLabel(g.html("(" + g.ang + ")"));
		angLabel.setBounds(105, 24, 20, 15);
		pnlCutoffBondLength.add(angLabel);

		add(pnlResetInteratomic);
		chkSetAveragePotential.addActionListener(keySetAveragePotential);
		chkSetAveragePotential.setBounds(381, 31, 520, 27);
		add(chkSetAveragePotential);

		final TitledPanel pnlSetDipole = new TitledPanel();
		pnlSetDipole.setTitle("set dipole polarisability");
		pnlSetDipole.setBounds(381, 127, 444, 103);
		add(pnlSetDipole);
		scrollPane.setBounds(10, 22, 421, 71);
		pnlSetDipole.add(scrollPane);

		polarisabilityTableModel.uneditableColumns = new int[] { 0 };
		polarisabilityTableModel.requiredColumns = new int[] { 1 };
		IconHeaderRenderer iconHeaderRenderer = new IconHeaderRenderer();
		table.getColumnModel().getColumn(1).setHeaderRenderer(iconHeaderRenderer);

		chkDoNotUseCutoff.addActionListener(keyDoNotUseCutoff);
		chkDoNotUseCutoff.setAlignmentY(Component.TOP_ALIGNMENT);
		chkDoNotUseCutoff.setBounds(381, 55, 601, 66);
		add(chkDoNotUseCutoff);

		final TitledPanel pnlIdentifyMolecules = new TitledPanel();
		pnlIdentifyMolecules.setTitle("identify molecules based on covalent radii but");
		pnlIdentifyMolecules.setBounds(378, 238, 709, 151);
		add(pnlIdentifyMolecules);
		buttonGroup.add(radRetainColoumbic);
		radRetainColoumbic.addActionListener(keyCoulombGroup);
		radRetainColoumbic.setBounds(10, 51, 345, 25);
		pnlIdentifyMolecules.add(radRetainColoumbic);
		radRemoveColoumbic.addActionListener(keyCoulombGroup);
		buttonGroup.add(radRemoveColoumbic);
		radRemoveColoumbic.setBounds(10, 20, 355, 25);
		pnlIdentifyMolecules.add(radRemoveColoumbic);
		chkRemoveColoumbic.addActionListener(keyChkRemoveColoumbic);
		chkRemoveColoumbic.setBounds(10, 82, 660, 25);
		pnlIdentifyMolecules.add(chkRemoveColoumbic);
		chkDoNotStop.addActionListener(keyDoNotStop);
		chkDoNotStop.setBounds(10, 113, 364, 28);
		pnlIdentifyMolecules.add(chkDoNotStop);

		final TitledPanel pnlScalingTransformation = new TitledPanel();
		pnlScalingTransformation.setTitle("apply a scaling transformation to EAM");
		pnlScalingTransformation.setBounds(0, 176, 375, 95);
		add(pnlScalingTransformation);
		txtScale.setBounds(185, 66, 69, 20);
		pnlScalingTransformation.add(txtScale);
		JLabel lblEquations = new JLabel("<html>f'(sum(" + g.rho
				+ "<sub>i</sub>)) = f(sum(" + g.rho
				+ "<sub>i</sub>)/scale<sub>i</sub>)<br> " + g.rho
				+ "'<sub>i</sub> = scale<sub>i</sub> " + g.rho
				+ "<sub>i</sub></html>");
		lblEquations.setBounds(8, 17, 270, 45);
		pnlScalingTransformation.add(lblEquations);
		JLabel lblSpecies = new JLabel("species");
		lblSpecies.setBounds(8, 68, 50, 15);
		pnlScalingTransformation.add(lblSpecies);
		JLabel lblScale = new JLabel("scale");
		lblScale.setBounds(144, 65, 35, 20);
		pnlScalingTransformation.add(lblScale);
		chkFit.setBounds(256, 63, 40, 25);
		pnlScalingTransformation.add(chkFit);
		cboSpecies.setBounds(64, 65, 74, 20);
		pnlScalingTransformation.add(cboSpecies);

		chkPrintThreeBody.addActionListener(keyPrintThreeBody);
		chkPrintThreeBody.setBounds(0, 277, 380, 25);
		add(chkPrintThreeBody);

		chkDoNotInclude.setBounds(0, 308, 320, 25);
		add(chkDoNotInclude);
		chkOutputList.addActionListener(keyOutputList);
		chkOutputList.setBounds(381, 0, 430, 25);
		add(chkOutputList);
		chkNoListBased.addActionListener(keyNoListBased);
		chkNoListBased.setBounds(0, 339, 375, 25);
		add(chkNoListBased);
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
		String lines = "", length = txtCutoffBondLength.getText();
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