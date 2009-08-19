package javagulp.view;

import java.awt.Font;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MonteCarlo extends JPanel implements Serializable {

	private static final long serialVersionUID = 927197258293319661L;

	private final G g = new G();

	//private JButton button = new JButton("New JButton");
	private final JButton btnCreate = new JButton("create");

	private final JTextArea txtareaMoleculeFile = new JTextArea("molecule atom file");
	private final JTextArea txtareaSymbol = new JTextArea("symbol");

	private final JCheckBox chkMonteCarlo = new JCheckBox("perform a monte carlo calculation");
//	private final JCheckBox chkXYZTrajectory = new JCheckBox("write series of xyz configurations for moves");

	//private TaskKeywordListener keyMonteCarlo = new TaskKeywordListener(chkMonteCarlo,
	//		"montecarlo");

	private final TitledPanel pnlChemicalPotential = new TitledPanel();
	private final TitledPanel pnlMaximumDisplacement = new TitledPanel();
	private final TitledPanel pnlMaximumRotation = new TitledPanel();
	private final TitledPanel pnlVolume = new TitledPanel();
	private final TitledPanel pnlRestart = new TitledPanel();
	private final TitledPanel pnlAtomCreation = new TitledPanel();
	private final TitledPanel pnlAtomDestruction = new TitledPanel();
	private final TitledPanel pnlAtomDisplacement = new TitledPanel();
	private final TitledPanel pnlMolecularRotation = new TitledPanel();
	private final TitledPanel pnlSamplingFrequency = new TitledPanel();
	private final TitledPanel pnlTrialMoves = new TitledPanel();
	private final TitledPanel pnlInsertedMolecules = new TitledPanel();
	private final TitledPanel pnlOutputFrequency = new TitledPanel();
//	private final TitledPanel pnloutputoptions = new TitledPanel();

	private final TitledPanel pnlAtomInsertion = new TitledPanel();

	private final JLabel lblEv = new JLabel("eV");
	private final JLabel lblAevery = new JLabel(g.html(g.ang + " every"));
	private final JLabel lblDegEvery = new JLabel("deg every");
	private final JLabel lblA3 = new JLabel(g.html(g.ang + "<sup>3</sup>"));
	private final JLabel lblFirstStepNumber = new JLabel("first step number");
	private final JLabel lblFilename = new JLabel("filename (*.gmc)");
	private final JLabel lblMaxDisplacementMoves = new JLabel("moves");
	private final JLabel lblMaxRotationMoves = new JLabel("moves");
	private final JLabel lblNumberOfAccepted = new JLabel("number of accepted operations between file writes");
	private final JLabel lblNumberOfAtoms = new JLabel("number of atoms");
	private final JLabel lblNumberOfAcceptedSteps = new JLabel("number of steps accepted so far");
	private final JLabel lblRunningMeanOf = new JLabel("running mean of the energy");
	private final JLabel lblMaxDisplacementRatio = new JLabel("to achieve an acceptance ratio");
	private final JLabel lblMaxRotationRatio = new JLabel("to achieve an acceptance ratio");
	private final JLabel lblTrialOperationsBetween = new JLabel("trial operations between output");

	// TODO This textbox is never used
	private final JTextField txtMoleculeFile = new JTextField();
	private final JTextField txtAtomSymbol = new JTextField();
	private final String TXT_MCCHEMICAL_POTENTIAL = "0.0";
	private final JTextField txtmcchemicalpotential = new JTextField(TXT_MCCHEMICAL_POTENTIAL);
	private final JTextField txtmccreate = new JTextField();
	private final JTextField txtmcdestroy = new JTextField();
	private final JTextField txtMcMaxDisplaceFrequency = new JTextField();
	private final String TXT_MCMAX_DISPLACELENGTH = "0.05";
	private final JTextField txtMcMaxDisplaceLength = new JTextField(TXT_MCMAX_DISPLACELENGTH);
	private final JTextField txtMcMaxDisplaceTargetRatio = new JTextField();
	private final String TXT_MCMAX_ROTATION_MAX = "180";
	private final JTextField txtMcmaxrotationMax = new JTextField(TXT_MCMAX_ROTATION_MAX);
	private final JTextField txtMcmaxrotationPermoves = new JTextField();
	private final JTextField txtMcmaxrotationRatio = new JTextField();
	private final JTextField txtMcmeansEnergy = new JTextField();
	private final JTextField txtMcmeansNumAtoms = new JTextField();
	private final String TXT_MC_MOVE = "1.0";
	private final JTextField txtmcmove = new JTextField(TXT_MC_MOVE);
	private final String TXT_MC_OUT_FREQ = "100";
	private final JTextField txtmcoutfreq = new JTextField(TXT_MC_OUT_FREQ);
	private final String TXT_MC_ROTATE = "0.0";
	private final JTextField txtmcrotate = new JTextField(TXT_MC_ROTATE);
	private final JTextField txtMcsampleFilename = new JTextField();
	private final String TXT_MC_SAMPLE_FREQUENCY = "10";
	private final JTextField txtMcsampleFrequency = new JTextField(TXT_MC_SAMPLE_FREQUENCY);
	private final JTextField txtMcFirststep = new JTextField();
	private final JTextField txtMcStepsofar = new JTextField();
	private final String TXT_MC_TRIAL = "0";
	private final JTextField txtmctrial = new JTextField(TXT_MC_TRIAL);
	private final JTextField txtmcvolume = new JTextField();
//	private final JTextField txtxyz = new JTextField();

	// TODO fix atoms to be inserted to allow more than one atom since we
	// eliminated AddPanel

	public MonteCarlo() {
		super();
		setLayout(null);

		pnlChemicalPotential.setTitle("chemical potential");
		pnlChemicalPotential.setBounds(624, 0, 203, 51);
		add(pnlChemicalPotential);

		txtmcchemicalpotential.setBounds(10, 20, 126, 21);
		pnlChemicalPotential.add(txtmcchemicalpotential);

		lblEv.setBounds(145, 19, 26, 22);
		pnlChemicalPotential.add(lblEv);

		pnlMaximumDisplacement.setTitle("maximum displacement");
		pnlMaximumDisplacement.setBounds(0, 100, 304, 84);
		add(pnlMaximumDisplacement);

		txtMcMaxDisplaceLength.setBounds(10, 19, 79, 21);
		pnlMaximumDisplacement.add(txtMcMaxDisplaceLength);

		lblAevery.setBounds(94, 22, 81, 15);
		pnlMaximumDisplacement.add(lblAevery);

		txtMcMaxDisplaceFrequency.setBackground(Back.grey);
		txtMcMaxDisplaceFrequency.setBounds(240, 51, 56, 21);
		pnlMaximumDisplacement.add(txtMcMaxDisplaceFrequency);

		txtMcMaxDisplaceTargetRatio.setBackground(Back.grey);
		txtMcMaxDisplaceTargetRatio.setBounds(181, 20, 64, 19);
		pnlMaximumDisplacement.add(txtMcMaxDisplaceTargetRatio);

		lblMaxDisplacementMoves.setBounds(251, 20, 50, 19);
		pnlMaximumDisplacement.add(lblMaxDisplacementMoves);

		lblMaxDisplacementRatio.setBounds(10, 46, 224, 30);
		pnlMaximumDisplacement.add(lblMaxDisplacementRatio);

		pnlVolume.setTitle("volume");
		pnlVolume.setBounds(833, 0, 205, 50);
		add(pnlVolume);

		// txtmcvolume = new JTextField();//"<unit cell volume>");
		txtmcvolume.setBounds(9, 19, 126, 21);
		pnlVolume.add(txtmcvolume);

		lblA3.setBounds(141, 18, 30, 20);
		pnlVolume.add(lblA3);

		pnlAtomCreation.setTitle("atom creation probability");
		pnlAtomCreation.setBounds(181, 0, 204, 44);
		add(pnlAtomCreation);

		txtmccreate.setBounds(8, 16, 79, 21);
		pnlAtomCreation.add(txtmccreate);

		pnlAtomDestruction.setTitle("atom destruction probability");
		pnlAtomDestruction.setBounds(391, 0, 227, 44);
		add(pnlAtomDestruction);

		txtmcdestroy.setBounds(8, 16, 79, 21);
		pnlAtomDestruction.add(txtmcdestroy);

		pnlAtomDisplacement.setTitle("atom displacment probability");
		pnlAtomDisplacement.setBounds(0, 50, 304, 44);
		add(pnlAtomDisplacement);

		txtmcmove.setBounds(8, 16, 79, 21);
		pnlAtomDisplacement.add(txtmcmove);

		pnlTrialMoves.setTitle("number of trial moves");
		pnlTrialMoves.setBounds(0, 0, 175, 44);
		add(pnlTrialMoves);

		txtmctrial.setBounds(8, 16, 79, 21);
		pnlTrialMoves.add(txtmctrial);

		pnlInsertedMolecules.setTitle("molecules to be inserted");
		pnlInsertedMolecules.setBounds(0, 190, 304, 79);
		add(pnlInsertedMolecules);

		txtMoleculeFile.setBounds(136, 20, 116, 19);
		pnlInsertedMolecules.add(txtMoleculeFile);

		btnCreate.setBounds(10, 43, 120, 18);
		btnCreate.setEnabled(false);
		pnlInsertedMolecules.add(btnCreate);

		//button.setBounds(258, 20, 36, 19);
		//pnlInsertedMolecules.add(button);

		txtareaMoleculeFile.setFont(new Font("Sans", Font.BOLD, 12));
		txtareaMoleculeFile.setOpaque(false);
		txtareaMoleculeFile.setLineWrap(true);
		txtareaMoleculeFile.setWrapStyleWord(true);
		txtareaMoleculeFile.setBounds(7, 21, 126, 14);
		pnlInsertedMolecules.add(txtareaMoleculeFile);

		//chkMonteCarlo.addActionListener(keyMonteCarlo);
		//chkMonteCarlo.setBounds(0, 0, 618, 25);
		//add(chkMonteCarlo);

		pnlRestart.setTitle("restart");
		pnlRestart.setBounds(624, 179, 414, 104);
		add(pnlRestart);

		txtMcFirststep.setBounds(293, 18, 79, 20);
		pnlRestart.add(txtMcFirststep);

		lblFirstStepNumber.setBounds(10, 20, 260, 15);
		pnlRestart.add(lblFirstStepNumber);

		lblNumberOfAcceptedSteps.setBounds(10, 41, 260, 15);
		pnlRestart.add(lblNumberOfAcceptedSteps);

		txtMcStepsofar.setBounds(293, 39, 79, 20);
		pnlRestart.add(txtMcStepsofar);

		txtMcmeansEnergy.setBounds(293, 60, 79, 19);
		pnlRestart.add(txtMcmeansEnergy);

		lblRunningMeanOf.setBounds(10, 62, 260, 15);
		pnlRestart.add(lblRunningMeanOf);

		lblNumberOfAtoms.setBounds(10, 83, 260, 15);
		pnlRestart.add(lblNumberOfAtoms);

		txtMcmeansNumAtoms.setBounds(293, 81, 79, 19);
		pnlRestart.add(txtMcmeansNumAtoms);

		pnlOutputFrequency.setTitle("frequency of binary configuration output");
		pnlOutputFrequency.setBounds(624, 105, 414, 68);
		add(pnlOutputFrequency);

		txtMcsampleFrequency.setBounds(351, 20, 53, 19);
		pnlOutputFrequency.add(txtMcsampleFrequency);

		lblNumberOfAccepted.setBounds(10, 22, 335, 15);
		pnlOutputFrequency.add(lblNumberOfAccepted);

		txtMcsampleFilename.setBounds(167, 43, 90, 19);
		pnlOutputFrequency.add(txtMcsampleFilename);

		lblFilename.setBounds(10, 43, 151, 15);
		pnlOutputFrequency.add(lblFilename);

		pnlMolecularRotation.setTitle("molecular rotation probability");
		pnlMolecularRotation.setBounds(310, 50, 308, 44);
		add(pnlMolecularRotation);

		txtmcrotate.setBounds(8, 16, 79, 21);
		pnlMolecularRotation.add(txtmcrotate);

		pnlSamplingFrequency.setTitle("sampling frequency of running averages");
		pnlSamplingFrequency.setBounds(624, 57, 414, 42);
		add(pnlSamplingFrequency);

		txtmcoutfreq.setBounds(331, 17, 73, 21);
		pnlSamplingFrequency.add(txtmcoutfreq);

		lblTrialOperationsBetween.setBounds(9, 20, 316, 15);
		pnlSamplingFrequency.add(lblTrialOperationsBetween);

		pnlMaximumRotation.setTitle("maximum rotation");
		pnlMaximumRotation.setBounds(310, 100, 308, 82);
		add(pnlMaximumRotation);

		txtMcmaxrotationMax.setBounds(9, 19, 79, 21);
		pnlMaximumRotation.add(txtMcmaxrotationMax);

		lblDegEvery.setBounds(94, 19, 86, 21);
		pnlMaximumRotation.add(lblDegEvery);

		txtMcmaxrotationRatio.setBackground(Back.grey);
		txtMcmaxrotationRatio.setBounds(250, 52, 48, 20);
		pnlMaximumRotation.add(txtMcmaxrotationRatio);

		txtMcmaxrotationPermoves.setBackground(Back.grey);
		txtMcmaxrotationPermoves.setBounds(186, 20, 64, 19);
		pnlMaximumRotation.add(txtMcmaxrotationPermoves);

		lblMaxRotationMoves.setBounds(258, 20, 50, 19);
		pnlMaximumRotation.add(lblMaxRotationMoves);

		lblMaxRotationRatio.setBounds(8, 46, 236, 30);
		pnlMaximumRotation.add(lblMaxRotationRatio);

		pnlAtomInsertion.setTitle("atoms to be inserted");
		pnlAtomInsertion.setBounds(310, 191, 308, 78);
		add(pnlAtomInsertion);

		txtAtomSymbol.setBounds(72, 19, 53, 19);
		pnlAtomInsertion.add(txtAtomSymbol);

		txtareaSymbol.setWrapStyleWord(true);
		txtareaSymbol.setOpaque(false);
		txtareaSymbol.setLineWrap(true);
		txtareaSymbol.setFont(new Font("Dialog", Font.BOLD, 12));
		txtareaSymbol.setBounds(10, 21, 55, 20);
		pnlAtomInsertion.add(txtareaSymbol);
		// TODO txtareaSymbol does not appear in the GUI
	
		//don't think this works in all versions of gulp yet
//		pnloutputoptions.setBounds(660, 382, 468, 69);
//		add(pnloutputoptions);
//		pnloutputoptions.setTitle("output formats");
//		chkXYZTrajectory.setBounds(7, 21, 163, 22);
//		pnloutputoptions.add(chkXYZTrajectory);
//		txtxyz.setBounds(182, 22, 267, 21);
//		pnloutputoptions.add(txtxyz);
	}

	private String writeMCChemicalPotential() {
		String lines = "";
		if (!txtmcchemicalpotential.getText().equals("")
				&& !txtmcchemicalpotential.getText().equals(TXT_MCCHEMICAL_POTENTIAL)) {
			try {
				Double.parseDouble(txtmcchemicalpotential.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo chemical potential.");
			}
			lines = "mcchemicalpotential " + txtmcchemicalpotential.getText()
			+ Back.newLine;
		}
		return lines;
	}

	private String writeMCCreate() throws InvalidOptionException {
		String lines = "";
		if (!txtmccreate.getText().equals("")) {
			try {
				final double mccreate = Double.parseDouble(txtmccreate.getText());
				if (mccreate < 0 || mccreate > 1)
					throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo create.");
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo create.");
			}
			lines = "mccreate " + txtmccreate.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCDestroy() throws InvalidOptionException {
		String lines = "";
		if (!txtmcdestroy.getText().equals("")) {
			try {
				final double mcdestroy = Double.parseDouble(txtmcdestroy.getText());
				if (mcdestroy < 0 || mcdestroy > 1)
					throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo destroy.");
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo destroy.");
			}
			lines = "mcdestroy " + txtmcdestroy.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCDisplace() {
		String lines = "";
		final String displace = txtMcMaxDisplaceLength.getText(), ratio = txtMcMaxDisplaceTargetRatio.getText(), frequency = txtMcMaxDisplaceFrequency.getText();
		if (!displace.equals("")
				&& !displace.equals(TXT_MCMAX_DISPLACELENGTH)) {
			lines = "mcmaxdisplacement ";
			try {
				Double.parseDouble(displace);
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo displace.");
			}
			lines += displace + " ";
		}
		if (!ratio.equals("")) {
			try {
				Double.parseDouble(ratio);
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo ratio.");
			}
			lines += "target " + ratio;
			if (!frequency.equals("")) {
				try {
					Double.parseDouble(frequency);
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Monte Carlo frequency.");
				}
				lines += " " + frequency;
			}
		}
		if (!lines.equals(""))
			lines += Back.newLine;
		if (displace.equals("") && ratio.equals("")) {
			lines = "";
		}
		return lines;
	}

	private String writeMCMaxRotation() {
		String lines = "";
		final String rotation = txtMcmaxrotationMax.getText(), ratio = txtMcmaxrotationRatio.getText(), frequency = txtMcmaxrotationPermoves.getText();
		if (!rotation.equals("")
				&& !rotation.equals(TXT_MCMAX_ROTATION_MAX)) {
			lines = "mcmaxrotation ";
			try {
				Double.parseDouble(rotation);
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo rotation.");
			}
			lines += rotation + " ";
		}
		if (!ratio.equals("")) {
			if (lines.equals(""))
				lines = "mcmaxrotation ";
			try {
				Double.parseDouble(ratio);
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo ratio.");
			}
			lines += "target " + ratio;
			if (!frequency.equals("")) {
				try {
					Double.parseDouble(frequency);
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Monte Carlo frequency.");
				}
				lines += " " + frequency;
			}
		}
		if (!lines.equals(""))
			lines += Back.newLine;
		return lines;
	}

	private String writeMCMeans() throws IncompleteOptionException {
		String lines = "";
		final String energy = txtMcmeansEnergy.getText(), atoms = txtMcmeansNumAtoms.getText();
		if (!energy.equals("") || !atoms.equals("")) {
			if (!energy.equals("") && !atoms.equals("")) {
				try {
					Double.parseDouble(energy);
					Double.parseDouble(atoms);
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter a number for Monte Carlo means.");
				}
				lines = "mcmeans " + energy + " " + atoms + Back.newLine;
			} else {
				throw new IncompleteOptionException("Missing one or more Monte Carlo means.");
			}
		}
		return lines;
	}

	private String writeMCMove() throws InvalidOptionException {
		String lines = "";
		if (!txtmcmove.getText().equals("")
				&& !txtmcmove.getText().equals(TXT_MC_MOVE)) {
			try {
				final double mcrotate = Double.parseDouble(txtmcmove.getText());
				if (mcrotate < 0 || mcrotate > 1)
					throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo move.");
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo move.");
			}
			lines = "mcmove " + txtmcmove.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCOutfreq() {
		String lines = "";
		if (!txtmcoutfreq.getText().equals("")
				&& !txtmcoutfreq.getText().equals(TXT_MC_OUT_FREQ)) {
			try {
				Integer.parseInt(txtmcoutfreq.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter an integer for Monte Carlo out frequency.");
			}
			lines = "mcoutfreq " + txtmcoutfreq.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCRotate() throws InvalidOptionException {
		String lines = "";
		if (!txtmcrotate.getText().equals("")
				&& !txtmcrotate.getText().equals(TXT_MC_ROTATE)) {
			try {
				final double mcrotate = Double.parseDouble(txtmcrotate.getText());
				if (mcrotate < 0 || mcrotate > 1)
					throw new InvalidOptionException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo rotate.");
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number between 0 and 1 (inclusive) for Monte Carlo rotate.");
			}
			lines = "mcrotate " + txtmcrotate.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCSample() {
		String lines = "";
		final String frequency = txtMcsampleFrequency.getText(), filename = txtMcsampleFilename.getText();
		if (!frequency.equals("")
				&& !frequency.equals(TXT_MC_SAMPLE_FREQUENCY)) {
			lines = "mcsample " + frequency;
			if (!filename.equals("")) {
				lines += " " + filename;
			}
			lines += Back.newLine;
		} else {
			if (!filename.equals("")) {
				lines = "mcsample " + filename + Back.newLine;
			}
		}
		return lines;
	}

	private String writeMCStep() throws IncompleteOptionException,
	InvalidOptionException {
		String lines = "";
		final String first = txtMcFirststep.getText(), sofar = txtMcStepsofar.getText();
		if (!first.equals("") || !sofar.equals("")) {
			if (!first.equals("") && !sofar.equals("")) {
				try {
					final int ifirst = Integer.parseInt(first);
					final int isofar = Integer.parseInt(sofar);
					if (isofar > ifirst)
						throw new InvalidOptionException("First step should be greater than current steps in Monte Carlo means.");
				} catch (final NumberFormatException nfe) {
					throw new NumberFormatException("Please enter numbers for Monte Carlo step.");
				}
				lines = "mcstep " + first + " " + sofar + Back.newLine;
			} else {
				throw new IncompleteOptionException("Missing one or more Monte Carlo steps.");
			}
		}
		return lines;
	}

	private String writeMCTrial() {
		String lines = "";
		if (!txtmctrial.getText().equals("")
				&& !txtmctrial.getText().equals(TXT_MC_TRIAL)) {
			try {
				Integer.parseInt(txtmctrial.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter an integer for Monte Carlo trial.");
			}
			lines = "mctrial " + txtmctrial.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeMCVolume() {
		String lines = "";
		// units: angstroms^3
		if (!txtmcvolume.getText().equals("")) {
			try {
				Double.parseDouble(txtmcvolume.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Monte Carlo volume.");
			}
			lines = "mcvolume " + txtmcvolume.getText() + Back.newLine;
		}
		return lines;
	}

	public String writeMonteCarlo() throws IncompleteOptionException,
	InvalidOptionException {
		return writeMCChemicalPotential() + writeMCDisplace() + writeMCVolume()
		+ writeMCCreate() + writeMCDestroy() + writeMCMove()
		+ writeMCTrial() + writeMCStep() + writeMCSample()
		+ writeMCRotate() + writeMCOutfreq() + writeMCMeans()
		+ writeMCMaxRotation() + writeSpecies();
	}

	private String writeSpecies() {
		// TODO check documentation on proper format
		String lines = "";
		if (!txtAtomSymbol.getText().equals("")) {
			lines = "gcmcspecies " + txtAtomSymbol.getText() + Back.newLine;
		}
		return lines;
	}
}
