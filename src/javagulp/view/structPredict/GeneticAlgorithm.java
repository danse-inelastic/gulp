package javagulp.view.structPredict;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

public class GeneticAlgorithm extends JPanel implements Serializable {

	private static final long serialVersionUID = 5526997928406360022L;

	//TODO add minimum, maximum, dminimum, and dmaximum options

	private final Grid pnlGrid = new Grid();
	private final CrossoverProbability pnlCrossoverProbability = new CrossoverProbability();
	private final MutationProbability pnlMutationProbability = new MutationProbability();
	private final Configurations pnlConfigurations = new Configurations();
	private final TournamentSelectionProbability pnlTSP = new TournamentSelectionProbability();
	private final Optimization pnlOptimisation = new Optimization();

	private final JLabel lblMinCostFunctionDifference = new JLabel("any two candidates must have a cost function difference of at least");
	private final JLabel lblTwo = new JLabel("2");

	private final JTextField txtDiscretizationIntervalPower = new JTextField("6");
	private final JTextField txtMinCostFunctionDifference = new JTextField("0.0");
	private final JTextField txtRandomNumberSeed = new JTextField("-1");

	public GeneticAlgorithm() {
		super();
		setBorder(new CompoundBorder(null, null));
		setLayout(null);

		pnlTSP.setTitle("tournament selection probability");
		pnlTSP.setBounds(2, 166, 526, 93);
		add(pnlTSP);
		pnlGrid.setTitle("grid");
		pnlGrid.setBounds(2, 4, 407, 76);
		add(pnlGrid);
		pnlCrossoverProbability.setTitle("crossover probability");
		pnlCrossoverProbability.setBounds(379, 86, 379, 74);
		add(pnlCrossoverProbability);
		pnlMutationProbability.setTitle("mutation probability");
		pnlMutationProbability.setBounds(2, 86, 371, 74);
		add(pnlMutationProbability);
		pnlConfigurations.setTitle("configurations");
		pnlConfigurations.setBounds(534, 166, 458, 91);
		add(pnlConfigurations);
		pnlOptimisation.setTitle("final candidate optimization");
		pnlOptimisation.setBounds(2, 265, 990, 56);
		add(pnlOptimisation);

		final TitledPanel pnlRandomNumberSeed = new TitledPanel();
		pnlRandomNumberSeed.setBounds(764, 86, 228, 74);
		pnlRandomNumberSeed.setTitle("random number seed");
		add(pnlRandomNumberSeed);
		txtRandomNumberSeed.setBounds(11, 20, 67, 20);
		pnlRandomNumberSeed.add(txtRandomNumberSeed);

		final TitledPanel pnlFittingResolution = new TitledPanel();
		pnlFittingResolution.setTitle("fitting resolution");
		pnlFittingResolution.setBounds(415, 4, 577, 77);
		add(pnlFittingResolution);
		lblTwo.setBounds(325, 20, 10, 20);
		pnlFittingResolution.add(lblTwo);
		txtDiscretizationIntervalPower.setBounds(340, 10, 26, 20);
		pnlFittingResolution.add(txtDiscretizationIntervalPower);
		final JLabel lblDiscretizationInterval = new JLabel("discretization interval for a fitted variable");
		lblDiscretizationInterval.setBounds(10, 19, 309, 21);
		pnlFittingResolution.add(lblDiscretizationInterval);
		// JCheckBox25 = new JCheckBox();
		// JCheckBox25.setText("two");
		// JCheckBox25.setBounds(609, 237, 81, 25);
		// add(JCheckBox25);
		//
		// JCheckBox26 = new JCheckBox();
		// JCheckBox26.setText("exponential");
		// JCheckBox26.setBounds(604, 206, 104, 25);
		// add(JCheckBox26);
		lblMinCostFunctionDifference.setBounds(10, 46, 462, 20);
		pnlFittingResolution.add(lblMinCostFunctionDifference);
		txtMinCostFunctionDifference.setBounds(498, 47, 69, 20);
		pnlFittingResolution.add(txtMinCostFunctionDifference);
	}

	private String writeDiscrete() {
		// TODO check format and add proper error checking
		String lines = "";
		final String text = txtDiscretizationIntervalPower.getText();
		if (!text.equals("") && !text.equals("6")) {
			lines = "discrete " + text + Back.newLine;
		}
		return lines;
	}

	public String writeGeneticAlgorithm() throws IncompleteOptionException, InvalidOptionException {
		//TODO This *may* only need to be written if doing a fit operation.  Investigate.
		return writeUnique() + pnlTSP.writeTournament() + pnlGrid.writeGrid()
		+ writeDiscrete() + writeSeed()
		+ pnlCrossoverProbability.writeCrossover()
		+ pnlOptimisation.writeBest()
		+ pnlConfigurations.writeConfigurations()
		+ pnlMutationProbability.writeMutation();
	}

	private String writeSeed() {
		String lines = "";
		final String text = txtRandomNumberSeed.getText();
		if (!text.equals("") && !text.equals("-1")) {
			Double.parseDouble(text);
			lines = "seed " + text + Back.newLine;
		}
		return lines;
	}

	private String writeUnique() {
		String lines = "";
		final String text = txtMinCostFunctionDifference.getText();
		if (!text.equals("") && !text.equals("0.0")) {
			Double.parseDouble(text);
			lines = "unique " + text + Back.newLine;
		}
		return lines;
	}
}