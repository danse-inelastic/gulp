package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class MD extends JPanel implements Serializable {

	private static final long serialVersionUID = -9015449918647439906L;

	private G g = new G();

	private JCheckBox chkMD = new JCheckBox("perform a MD simulation");

	private KeywordListener keyMD = new KeywordListener(chkMD, "md");

	private Temperature pnlTemperature = new Temperature(0);
	public MDMass pnlMDmass = new MDMass();
	private Integrator pnlIntegrator = new Integrator();
	private MDMassless pnlMDmassless = new MDMassless();
	private Interpolation pnlPotentialInterpolation = new Interpolation();
	private Pressure pnlPressure = new Pressure();
	private Thermodynamics pnlThermodynamicEnsembles = new Thermodynamics();
	private VectorTable pnlVectorTable = new VectorTable();

	private TimeLengths pnlTimeLengths = new TimeLengths();
	private OutputFormats pnlOutputFormats = new OutputFormats();

	private String uriJPad = "http://cseobb.hec.utah.edu:18080/JPad/OPENARCH-INF/JPad.xml";

	public MD() {
		super();
		setLayout(null);

		pnlTemperature.setBounds(275, 7, 311, 106);
		add(pnlTemperature);
		pnlTemperature.txtFirstStep.setEnabled(true);
		pnlMDmass.setBounds(592, 106, 363, 131);
		add(pnlMDmass);
		pnlVectorTable.setBounds(275, 241, 406, 103);
		add(pnlVectorTable);
		pnlPressure.setBounds(275, 119, 311, 44);
		add(pnlPressure);
		pnlPotentialInterpolation.setBounds(275, 166, 311, 71);
		add(pnlPotentialInterpolation);
		chkMD.addActionListener(keyMD);
		chkMD.setBounds(4, 6, 185, 25);
		add(chkMD);
		pnlMDmassless.setBounds(592, 7, 363, 93);
		add(pnlMDmassless);
		pnlThermodynamicEnsembles.setBounds(2, 32, 267, 157);
		add(pnlThermodynamicEnsembles);
		pnlIntegrator.setBounds(2, 189, 267, 51);
		add(pnlIntegrator);
		pnlTimeLengths.setBounds(2, 241, 267, 105);
		add(pnlTimeLengths);
		pnlOutputFormats.setBounds(687, 241, 268, 103);
		add(pnlOutputFormats);
	}

	private class Thermodynamics extends TitledPanel {

		private static final long serialVersionUID = 1579392356755048884L;
		private JTextField txtQnose = new JTextField();
		private JTextField txtQnose2 = new JTextField();
		private JTextField txtQpress = new JTextField();

		private JLabel lblPress = new JLabel(g.html("q<sub>press</sub>"));

		private JCheckBox chkConserved = new JCheckBox("output conserved ensemble quantity");

		private JRadioButton radNone = new JRadioButton();
		private JRadioButton radEnsembleNPT = new JRadioButton(g.html("NPT ensemble, q<sub>nose</sub>"));
		private JRadioButton radEnsembleNVE = new JRadioButton("NVE ensemble");
		private JRadioButton radEnsembleNVT = new JRadioButton(g.html("NVT ensemble, q<sub>nose</sub>"));
		private ButtonGroup ensemble = new ButtonGroup();

		private Thermodynamics() {
			setTitle("thermodynamic ensembles");
			ensemble.add(radNone);
			ensemble.add(radEnsembleNVE);
			ensemble.add(radEnsembleNVT);
			ensemble.add(radEnsembleNPT);
			radNone.setBounds(5, 15, 17, 17);
			add(radNone);
			radNone.setSelected(true);
			radEnsembleNVE.setBounds(5, 30, 120, 30);
			add(radEnsembleNVE);
			radEnsembleNVT.setBounds(5, 60, 165, 25);
			add(radEnsembleNVT);
			radEnsembleNPT.setBounds(5, 84, 165, 25);
			add(radEnsembleNPT);
			chkConserved.setBounds(5, 132, 256, 25);
			add(chkConserved);
			txtQnose.setBounds(174, 60, 63, 20);
			add(txtQnose);
			txtQnose2.setBounds(174, 84, 63, 20);
			add(txtQnose2);
			txtQpress.setBounds(174, 110, 63, 20);
			add(txtQpress);
			lblPress.setBounds(122, 110, 40, 20);
			add(lblPress);
		}

		private String writeEnsemble() throws IncompleteOptionException {
			String lines = "";
			if (radEnsembleNVE.isSelected())
				lines = "ensemble nve" + Back.newLine;
			else if (radEnsembleNVT.isSelected()) {
				if (!txtQnose.getText().equals(""))
					lines += "ensemble nvt " + txtQnose.getText() + Back.newLine;
				else
					throw new IncompleteOptionException("Missing nvt qnose in Molecular Dynamics");
			} else if (radEnsembleNPT.isSelected()) {
				if (!txtQnose2.getText().equals("")
						&& !txtQpress.getText().equals(""))
					lines += "ensemble npt " + txtQnose2.getText() + " "
							+ txtQpress.getText() + Back.newLine;
				else
					throw new IncompleteOptionException("Missing npt qnose or qpress in Molecular Dynamics");
			}
			if (chkConserved.isSelected())
				lines += "conserved" + Back.newLine;
			return lines;
		}
	}

	private class Integrator extends TitledPanel {

		private static final long serialVersionUID = -5630354151968794086L;
		private String[] integrators = new String[] { "leapfrog verlet",
				"velocity verlet", "predictor corrector (NVE only)" };
		private JComboBox cboIntegrators = new JComboBox(integrators);

		private Integrator() {
			setTitle("integrator");
			cboIntegrators.setBounds(9, 20, 206, 20);
			add(cboIntegrators);
		}

		private String writeIntegrator() {
			String lines = "";
			// From the documentation:
			// Currently the Gear algorithm is only available in the NVE ensemble.
			if (cboIntegrators.getSelectedItem().equals(integrators[1]))
				lines = "integrator velocity verlet" + Back.newLine;
			else if (cboIntegrators.getSelectedItem().equals(integrators[2]))
				lines = "integrator gear" + Back.newLine;
			return lines;
		}
	}

	private class TimeLengths extends JPanel {

		private static final long serialVersionUID = 7793603346713580397L;
		private JTextField txtTimestep = new JTextField();
		private JTextField txtSample = new JTextField();
		private JTextField txtProduction = new JTextField();
		private JTextField txtEquilibration = new JTextField();

		private JLabel lblTimestep = new JLabel("timestep length (ps)");
		private JLabel lblEquilibrationTime = new JLabel("equilibration time (ps)");
		private JLabel lblProductionTime = new JLabel("production time (ps)");
		private JLabel lblSampleFrequency = new JLabel("sample frequency (ps)");

		private TimeLengths() {
			setBorder(new TitledBorder(null, "time lengths",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			setLayout(null);
			txtTimestep.setBounds(156, 19, 63, 19);
			add(txtTimestep);
			lblTimestep.setBounds(10, 21, 130, 15);
			add(lblTimestep);
			lblEquilibrationTime.setBounds(10, 42, 140, 15);
			add(lblEquilibrationTime);
			lblEquilibrationTime.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
			lblProductionTime.setBounds(10, 63, 130, 15);
			add(lblProductionTime);
			lblProductionTime.setToolTipText("Specifies the simulation time to be spent collecting production data for subsequent analysis.");
			lblSampleFrequency.setBounds(10, 84, 140, 15);
			add(lblSampleFrequency);
			lblSampleFrequency.setToolTipText("Controls how often the properties of the molecular dynamics run are to be sampled and output to the standard output channel. Averaged properties are also based on these samples.");
			txtSample.setBounds(156, 82, 63, 19);
			add(txtSample);
			txtProduction.setBounds(156, 61, 63, 19);
			add(txtProduction);
			txtEquilibration.setBounds(156, 40, 63, 19);
			add(txtEquilibration);
			txtEquilibration.setToolTipText("Specifies the simulation time to be spent equilibrating the kinetic and potential energy distributions prior to the production phase of the molecular dynamics run.");
		}

		private String writeTime() {
			// can be integer or float; see documentation
			// TODO add unit combo boxes like in OutputFormats
			String lines = "";
			try {
				if (!txtTimestep.getText().equals("")) {
					Double.parseDouble(txtTimestep.getText());
					lines += "timestep " + txtTimestep.getText() + Back.newLine;
				}
				if (!txtEquilibration.getText().equals("")) {
					Double.parseDouble(txtEquilibration.getText());
					lines += "equilibration " + txtEquilibration.getText()
							+ Back.newLine;
				}
				if (!txtProduction.getText().equals("")) {
					Double.parseDouble(txtProduction.getText());
					lines += "production " + txtProduction.getText() + Back.newLine;
				}
				if (!txtSample.getText().equals("")) {
					Double.parseDouble(txtSample.getText());
					lines += "sample " + txtSample.getText() + Back.newLine;
				}
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter numbers for MD time lengths.");
			}
			return lines;
		}
	}

	private class Pressure extends TitledPanel {

		private static final long serialVersionUID = 7736994189677354195L;

		private final String TXT_PRESSURE = "0.0";
		private JTextField txtPressure = new JTextField(TXT_PRESSURE);

		private Pressure() {
			setTitle("pressure (GPa)");
			txtPressure.setBounds(10, 19, 98, 19);
			add(txtPressure);
		}

		private String writePressure() {
			String lines = "";
			if (!txtPressure.getText().equals("")
					&& !txtPressure.getText().equals(TXT_PRESSURE)) {
				try {
					Double.parseDouble(txtPressure.getText());
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for MD pressure.");
				}
				lines = "pressure " + txtPressure.getText() + Back.newLine;
			}
			return lines;
		}
	}

	private class Interpolation extends TitledPanel {

		private static final long serialVersionUID = -1545757803614526779L;

		private JTextField txtPotentialInterpolation = new JTextField();

		private JLabel lblUse = new JLabel("use");
		private JLabel lblInterpolationPoints = new JLabel("interpolation points");
		private JLabel lblAccelerate = new JLabel("(i.e. 100000) to accelerate the calculation");

		private Interpolation() {
			setTitle("potential interpolation");
			txtPotentialInterpolation.setBounds(41, 21, 85, 20);
			add(txtPotentialInterpolation);
			lblUse.setBounds(10, 21, 25, 20);
			add(lblUse);
			lblInterpolationPoints.setBounds(132, 21, 125, 15);
			add(lblInterpolationPoints);
			lblAccelerate.setBounds(10, 47, 255, 15);
			add(lblAccelerate);
		}

		private String writePotentialInterpolation() {
			String lines = "";
			// integer, typically > 100000
			if (!txtPotentialInterpolation.getText().equals("")) {
				try {
					Integer.parseInt(txtPotentialInterpolation.getText());
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a large integer for MD potential interpolation.");
				}
				lines = "potential_interpolation "
						+ txtPotentialInterpolation.getText() + Back.newLine;
			}
			return lines;
		}
	}

	private class VectorTable extends TitledPanel {

		private static final long serialVersionUID = -5890258072124019041L;

		private JCheckBox chkStoreVectors = new JCheckBox("store a table of interatomic vectors to speed up calculation");

		private KeywordListener keyStoreVectors = new KeywordListener(chkStoreVectors, "storevectors");

		private JLabel lblUpdateFrequency = new JLabel("update the interatomic vector table every (steps)");
		private JLabel lblExtraAmount = new JLabel("<html>extra amount to add to global cutoff<br> when deciding which vectors to store</html>");

		private final String TXT_RESET_VECTORS = "1";
		private JTextField txtResetVectors = new JTextField(TXT_RESET_VECTORS);
		private final String TXT_EXTRA_CUTOFF = "0.0";
		private JTextField txtExtraCutoff = new JTextField(TXT_EXTRA_CUTOFF);

		private VectorTable() {
			setTitle("vector table");
			chkStoreVectors.setBounds(8, 18, 395, 25);
			add(chkStoreVectors);
			chkStoreVectors.addActionListener(keyStoreVectors);
			lblUpdateFrequency.setBounds(10, 46, 305, 15);
			add(lblUpdateFrequency);
			txtResetVectors.setBounds(321, 44, 79, 20);
			add(txtResetVectors);
			lblExtraAmount.setToolTipText("This allows for atoms to move across the boundary between updates.");
			lblExtraAmount.setBounds(10, 65, 254, 35);
			add(lblExtraAmount);
			txtExtraCutoff.setBounds(321, 72, 79, 20);
			add(txtExtraCutoff);
		}

		private String writeResetvectors() {
			String lines = "";
			if (!txtResetVectors.getText().equals("")
					&& !txtResetVectors.getText().equals(TXT_RESET_VECTORS)) {
				try {
					Integer.parseInt(txtResetVectors.getText());
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter an integer for MD reset vectors.");
				}
				lines = "resetvectors " + txtResetVectors.getText() + Back.newLine;
			}
			return lines;
		}

		private String writeExtracutoff() {
			String lines = "";
			// units: angstroms
			if (!txtExtraCutoff.getText().equals("")
					&& !txtExtraCutoff.getText().equals(TXT_EXTRA_CUTOFF)) {
				try {
					Double.parseDouble(txtExtraCutoff.getText());
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for MD extra cutoff.");
				}
				lines = "extracutoff " + txtExtraCutoff.getText() + Back.newLine;
			}
			return lines;
		}
	}

	private class MDMassless extends TitledPanel {

		private static final long serialVersionUID = -7716838461323185087L;

		private JLabel lblOptimizeShells = new JLabel("optimize shells at each time step for");
		private JLabel lblGradientNorm = new JLabel("iterations or until gradient norm");
		private JLabel lblShellExtrapolation = new JLabel("using rational function extrapolation of order");
		private JLabel lblIterationsReached = new JLabel("is reached");

		private final String TXTN = "10";
		private JTextField txtN = new JTextField(TXTN);
		private final String TXT_GRADIENT_NORM = "1.0D-10";
		private JTextField txtGradientNorm = new JTextField(TXT_GRADIENT_NORM);
		private final String TXT_EXTRAPOLATION = "8";
		private JTextField txtExtrapolation = new JTextField(TXT_EXTRAPOLATION);

		private MDMassless() {
			setTitle("MD with massless shells");
			lblOptimizeShells.setBounds(10, 22, 230, 15);
			add(lblOptimizeShells);
			txtN.setBounds(246, 20, 55, 20);
			add(txtN);
			lblGradientNorm.setBounds(10, 45, 206, 15);
			add(lblGradientNorm);
			txtGradientNorm.setBackground(Back.grey);
			txtGradientNorm.setBounds(222, 43, 63, 20);
			add(txtGradientNorm);
			lblShellExtrapolation.setBounds(10, 66, 285, 15);
			add(lblShellExtrapolation);
			txtExtrapolation.setBackground(Back.grey);
			txtExtrapolation.setBounds(301, 64, 55, 20);
			add(txtExtrapolation);
			lblIterationsReached.setBounds(291, 46, 65, 15);
			add(lblIterationsReached);
		}

		private String writeIterations() throws InvalidOptionException {
			String lines = "";
			if (!txtN.getText().equals("") && !txtN.getText().equals(TXTN)) {
				Integer.parseInt(txtN.getText());
				lines = "iterations " + txtN.getText();
				if (!txtGradientNorm.getText().equals("")
						&& !txtGradientNorm.getText().equals(TXT_GRADIENT_NORM)) {
					double d = Double.parseDouble(txtGradientNorm.getText());
					if (d < 1 || d > 10)
						throw new InvalidOptionException("MD iterations gradient norm must be between 1.0 and 10.0");
					lines += " " + txtGradientNorm.getText();
				}
				//TODO G.N. must be written if Extrapolation is written
				if (!txtExtrapolation.getText().equals("")
						&& !txtExtrapolation.getText().equals(TXT_EXTRAPOLATION)) {
					Integer.parseInt(txtExtrapolation.getText());
					lines += " " + txtExtrapolation.getText();
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}

	public class MDMass extends TitledPanel implements Serializable {

		private static final long serialVersionUID = 3863537565231540679L;

		private JLabel lblAssignShells = new JLabel("shell/core mass ratio");
		private JLabel lblSpecies = new JLabel("species");

		private JTextField txtShellmassRatio = new JTextField();

		public JComboBox cboShellmassSpecies = new JComboBox();

		private MDMass() {
			setTitle("MD with shells with mass");
			lblAssignShells.setBounds(10, 49, 135, 15);
			add(lblAssignShells);
			txtShellmassRatio.setBounds(151, 47, 88, 20);
			add(txtShellmassRatio);
			cboShellmassSpecies.setBounds(151, 18, 88, 25);
			add(cboShellmassSpecies);
			lblSpecies.setBounds(10, 23, 55, 15);
			add(lblSpecies);
		}

		private String writeShellMassRatio() throws InvalidOptionException,
				IncompleteOptionException {
			String lines = "";
			// TODO check for incomplete options
			// TODO put choices in cboShellmassSpecies
			if (!txtShellmassRatio.getText().equals("")) {
				if (cboShellmassSpecies.getSelectedItem() == null
						|| cboShellmassSpecies.getSelectedItem().equals(""))
					throw new IncompleteOptionException("Please select a species for shell/core mass ratio.");
				try {
					double ratio = Double.parseDouble(txtShellmassRatio.getText());
					if (ratio < 0 || ratio > 1)
						throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for MD shell mass ratio.");
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for MD shell mass ratio.");
				}
				lines = "shellmass" + Back.newLine + cboShellmassSpecies.getSelectedItem()
						+ " " + txtShellmassRatio.getText() + Back.newLine;
			}
			return lines;
		}
	}

	private class OutputFormats extends JPanel {

		private static final long serialVersionUID = -8425306576850279775L;

		private JLabel lblWriteFrequencyps = new JLabel("write to md dumpfile every");
		private JLabel lblMdarchive = new JLabel("create Insight II archive file");

		private JTextField txtWrite = new JTextField("1");
		private JTextField txtMdarchive = new JTextField();

		private JComboBox cboUnits = new JComboBox(new String[] { "ps", "ns",
				"fs", "s", "timesteps" });

		private OutputFormats() {
			setBorder(new TitledBorder(null, "output formats",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			setLayout(null);

			lblWriteFrequencyps.setBounds(7, 21, 175, 14);
			add(lblWriteFrequencyps);
			txtWrite.setBounds(182, 14, 70, 21);
			add(txtWrite);
			lblMdarchive.setBounds(10, 69, 180, 15);
			add(lblMdarchive);
			txtMdarchive.setBounds(182, 70, 70, 21);
			add(txtMdarchive);
			cboUnits.setBounds(154, 42, 98, 21);
			add(cboUnits);
		}

		private String writeMDWriteFrequency() {
			String lines = "";
			if (!txtWrite.getText().equals("")) {
				if (cboUnits.getSelectedItem().equals("timesteps")) {
					try {
						Integer.parseInt(txtWrite.getText());
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter an integer for MD write frequency.");
					}
					lines = "write " + txtWrite.getText() + Back.newLine;
				} else {
					try {
						Double.parseDouble(txtWrite.getText());
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter a number for MD write frequency.");
					}
					lines = "write " + txtWrite.getText() + " "
							+ cboUnits.getSelectedItem() + Back.newLine;
				}
			}
			return lines;
		}

		private String writeMDarchive() {
			String lines = "";
			if (!txtMdarchive.getText().equals("")) {
				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
			}
			return lines;
		}
	}

	public String writeMD() throws IncompleteOptionException,
			InvalidOptionException {
		//MDRestartInit m;
		//m = Back.getPanel().getMdRestartInit();
		return pnlTimeLengths.writeTime()
				+ pnlOutputFormats.writeMDWriteFrequency()
				+ pnlMDmass.writeShellMassRatio()
				+ pnlVectorTable.writeResetvectors()
				+ pnlPressure.writePressure()
				+ pnlOutputFormats.writeMDarchive()
				+ pnlPotentialInterpolation.writePotentialInterpolation()
				+ pnlMDmassless.writeIterations()
				+ pnlIntegrator.writeIntegrator()
				+ pnlVectorTable.writeExtracutoff()
				+ pnlThermodynamicEnsembles.writeEnsemble()
				//+ m.writeMDRestart() + pnlTemperature.writeTemperature();
				+ pnlTemperature.writeTemperature();
	}
}