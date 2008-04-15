package javagulp.view.top;

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

	private class Grid extends TitledPanel implements Serializable {
		private static final long serialVersionUID = -3709125169034847513L;

		private JTextField txtMin = new JTextField("6");
		private JTextField txtMax = new JTextField("6");
		private JTextField txtNumber = new JTextField("20");

		private JLabel lblNumber = new JLabel("increase the grid side length after");
		private JLabel lblMin = new JLabel("from a minimum of (2");
		private JLabel lblMax = new JLabel("<html>)<sup>3</sup> by 1 until (2</html>");
		private JLabel lblMaxPower = new JLabel("<html>)<sup>3</sup></html>");
		private JLabel lblIterations = new JLabel("iterations");

		private Grid() {
			super();

			txtMin.setBounds(151, 43, 33, 19);
			add(txtMin);
			txtMax.setBackground(Back.grey);
			txtMax.setBounds(285, 42, 33, 19);
			add(txtMax);
			lblNumber.setBounds(10, 21, 215, 15);
			add(lblNumber);
			txtNumber.setBackground(Back.grey);
			txtNumber.setBounds(231, 20, 33, 19);
			add(txtNumber);
			lblMin.setBounds(10, 52, 140, 15);
			add(lblMin);
			lblMaxPower.setBounds(321, 46, 15, 20);
			add(lblMaxPower);
			lblIterations.setBounds(270, 21, 60, 15);
			add(lblIterations);
			lblMax.setBounds(188, 47, 95, 20);
			add(lblMax);
		}

		private String writeGrid() throws IncompleteOptionException {
			String lines = "", min = txtMin.getText(), max = txtMax.getText(), n = txtNumber.getText();
			if (!min.equals("6") || !max.equals("6") || !n.equals("20")) {
				if (min.equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm grid min");
				Integer.parseInt(min);
				lines = "grid " + min;
				if (n.equals("")) {
					if (max.equals(""))
						throw new IncompleteOptionException("Please enter a value for genetic algorithm grid max");
					Integer.parseInt(max);
					Integer.parseInt(n);
					lines += " " + max + " " + n;
				} else {
					if (!max.equals("")) {
						Integer.parseInt(max);
						lines += " " + max;
					}
				}
				lines += Back.newLine;
			}
			return lines;
		}

	}

	private class CrossoverProbability extends TitledPanel implements Serializable {

		private static final long serialVersionUID = -3210983757540993050L;

		private JTextField txtMin = new JTextField("0.4");
		private JTextField txtMax = new JTextField("0.4");
		private JTextField txtIncrease = new JTextField("0.0");

		private JLabel lblMin = new JLabel("from");
		private JLabel lblMax = new JLabel("to");
		private JLabel lblIncrease = new JLabel("increase crossover probability by");

		private CrossoverProbability() {
			super();

			txtMin.setBounds(47, 45, 46, 20);
			add(txtMin);
			lblMin.setBounds(9, 48, 36, 15);
			add(lblMin);
			lblMax.setBounds(99, 48, 17, 15);
			add(lblMax);
			txtMax.setBackground(Back.grey);
			txtMax.setBounds(126, 42, 49, 21);
			add(txtMax);
			txtIncrease.setBackground(Back.grey);
			txtIncrease.setBounds(224, 21, 49, 21);
			add(txtIncrease);
			lblIncrease.setBounds(9, 22, 210, 15);
			add(lblIncrease);
		}

		private String writeCrossover() throws IncompleteOptionException {
			String lines = "", min = txtMin.getText(), max = txtMax.getText(), inc = txtIncrease.getText();
			if (!min.equals("0.4") && !max.equals("0.4") && inc.equals("0.0")) {
				if (min.equals("") || max.equals("") || inc.equals(""))
					throw new IncompleteOptionException("Please enter a values for crossover probability.");
				Double.parseDouble(min);
				Double.parseDouble(max);
				Double.parseDouble(inc);
				lines = "crossover " + txtMin.getText();
				if (!inc.equals("0.0")) {
					lines += " " + max + " " + inc;
				} else {
					if (!max.equals("0.4"))
						lines += " " + max;
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}

	private class MutationProbability extends TitledPanel implements Serializable {
		private static final long serialVersionUID = -2625966816110087416L;

		private JTextField txtInitial = new JTextField();
		private JTextField txtFinal = new JTextField();
		private JTextField txtIncrease = new JTextField("0.0");

		private JLabel lblInitial = new JLabel("initial");
		private JLabel lblFinal = new JLabel("final");
		private JLabel lblIncrease = new JLabel("<html>increase every 20 iterations by</html>");

		private MutationProbability() {
			super();
			
			txtInitial.setBounds(51, 25, 75, 19);
			add(txtInitial);
			lblFinal.setBounds(157, 27, 30, 15);
			add(lblFinal);
			txtFinal.setBackground(Back.grey);
			txtFinal.setBounds(193, 25, 75, 20);
			add(txtFinal);
			txtIncrease.setBounds(203, 48, 65, 20);
			add(txtIncrease);
			lblIncrease.setBounds(5, 50, 195, 15);
			add(lblIncrease);
			lblInitial.setBounds(5, 25, 40, 15);
			add(lblInitial);
		}

		private String writeMutation() throws IncompleteOptionException {
			String lines = "";
			//TODO add a listener to discretation and have it set initial and final to 1/discretation
			if (!txtInitial.getText().equals("") || !txtFinal.getText().equals("") || !txtIncrease.getText().equals("0.0")) {
				if (txtInitial.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm mutation initial");
				Double.parseDouble(txtInitial.getText());
				lines = "mutation " + txtInitial.getText();
				if (!txtIncrease.getText().equals("0.0")) {
					if (txtFinal.getText().equals(""))
						throw new IncompleteOptionException("Please enter a value for genetic algorithm mutation final");
					Double.parseDouble(txtFinal.getText());
					Double.parseDouble(txtIncrease.getText());
					lines += " " + txtFinal.getText() + " " + txtIncrease.getText();
				} else {
					if (!txtFinal.getText().equals("")) {
						Double.parseDouble(txtFinal.getText());
						lines += " " + txtFinal.getText();
					}
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}

	private class Configurations extends TitledPanel implements Serializable {
		private static final long serialVersionUID = 4730400389192705976L;

		private JComboBox cboStepSize = new JComboBox(new String[] { "0", "2" });

		private JLabel lblNumber = new JLabel("number of configurations (must be even)");
		private JLabel lblMax = new JLabel("maximum configurations");
		private JLabel lblCombo = new JLabel("step size");

		private JTextField txtNumber = new JTextField("10");
		private JTextField txtMax = new JTextField("10");

		private Configurations() {
			super();

			txtNumber.setBounds(275, 17, 54, 20);
			add(txtNumber);
			lblNumber.setBounds(9, 19, 260, 15);
			add(lblNumber);
			lblMax.setBounds(9, 37, 160, 15);
			add(lblMax);
			txtMax.setBackground(Back.grey);
			txtMax.setBounds(275, 38, 54, 20);
			add(txtMax);
			cboStepSize.setSelectedItem("2");
			cboStepSize.setBounds(275, 60, 54, 20);
			add(cboStepSize);
			lblCombo.setBounds(9, 58, 65, 15);
			add(lblCombo);
		}

		private String writeConfigurations() throws IncompleteOptionException, InvalidOptionException {
			String lines = "";
			if (!txtNumber.getText().equals("10") || !txtMax.getText().equals("10")) {
				if (txtNumber.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm configurations number");
				int number = Integer.parseInt(txtNumber.getText());
				if (number % 2 != 0)
					throw new InvalidOptionException("genetic algorithm configurations number must be even");
				lines = "configurations " + txtNumber.getText();
				//TODO check that number < max ?
				if (!txtMax.getText().equals("") && !txtMax.getText().equals("10")) {
					Integer.parseInt(txtMax.getText());
					lines += " " + txtMax.getText();
				}
				lines += " " + cboStepSize.getSelectedItem() + Back.newLine;
			}
			return lines;
		}
	}

	private class TournamentSelectionProbability extends TitledPanel implements Serializable {
		private static final long serialVersionUID = -6288129306797651735L;

		private int TOUR_MIN = 0, TOUR_MAX = 100, TOUR_INIT = 80;

		private JSlider sldInitial = new JSlider(SwingConstants.HORIZONTAL, TOUR_MIN,
				TOUR_MAX, TOUR_INIT);
		private JLabel lblInitial = new JLabel("initial");
		private JSlider sldFinal = new JSlider(SwingConstants.HORIZONTAL, TOUR_MIN,
				TOUR_MAX, TOUR_INIT);
		private JLabel lblFinal = new JLabel("final");
		private JLabel lblStepSize = new JLabel("step size");
		private JTextField txtStepSize = new JTextField("0.0");

		private TournamentSelectionProbability() {
			super();

			sldInitial.setValue(80);
			sldInitial.setMajorTickSpacing(20);
			sldInitial.setPaintTicks(true);
			sldInitial.setPaintLabels(true);
			sldInitial.setBounds(7, 42, 126, 42);
			add(sldInitial);
			lblInitial.setBounds(14, 21, 42, 14);
			add(lblInitial);
			sldFinal.setValue(80);
			sldFinal.setMajorTickSpacing(20);
			sldFinal.setPaintTicks(true);
			sldFinal.setPaintLabels(true);
			sldFinal.setBounds(159, 43, 134, 40);
			add(sldFinal);
			lblFinal.setBounds(159, 22, 44, 15);
			add(lblFinal);
			lblStepSize.setBounds(310, 22, 66, 15);
			add(lblStepSize);
			txtStepSize.setBounds(310, 46, 66, 22);
			add(txtStepSize);
		}

		private String writeTournament() throws IncompleteOptionException {
			String lines = "", step = txtStepSize.getText();
			double init = sldInitial.getValue(), finl = sldFinal.getValue();
			if (init != 80 || finl != 80 || !step.equals("0.0")) {
				lines = "tournament " + (init/100) + " " + (finl/100);
				if (!step.equals("0.0")) {
					Double.parseDouble(step);
					lines += " " + step;
				}
				lines += Back.newLine;
			}			
			return lines;
		}
	}

	private class Optimization extends TitledPanel implements Serializable {

		private static final long serialVersionUID = -1060521026503135675L;

		private JLabel lblIterationsInterval = new JLabel("candidates per");
		private JLabel lblSaveTheBest = new JLabel("save the best");
		private JLabel lblComboIterations = new JLabel("iterations");

		private JTextField txtSaveTheBest = new JTextField("2");
		private JTextField txtIterationsInterval = new JTextField();
		
		private JCheckBox chkOnly = new JCheckBox("<html>Save a <i>total</i> of n candidates for <i>all</i> iterations</html>");

		private Optimization() {
			super();

			lblSaveTheBest.setBounds(14, 24, 85, 15);
			add(lblSaveTheBest);
			txtSaveTheBest.setBounds(105, 22, 32, 20);
			add(txtSaveTheBest);
			lblIterationsInterval.setBounds(145, 24, 110, 15);
			add(lblIterationsInterval);
			txtIterationsInterval.setBounds(260, 20, 33, 23);
			add(txtIterationsInterval);
			lblComboIterations.setBounds(300, 24, 60, 15);
			add(lblComboIterations);
			chkOnly.setBounds(365, 24, 350, 15);
			add(chkOnly);
		}

		private String writeBest() throws IncompleteOptionException {
			String lines = "", best = txtSaveTheBest.getText(), iter = txtIterationsInterval.getText();
			if (!best.equals("2") || !iter.equals("") || chkOnly.isSelected()) {
				lines = "best";
				if (best.equals(""))
					throw new IncompleteOptionException("Please enter a value for genetic algorithm candidates");
				Integer.parseInt(best);
				lines += " " + best;
				if (!iter.equals("")) {
					Integer.parseInt(iter);
					lines += " " + iter;
				}
				if (chkOnly.isSelected())
					lines += "only";
				lines += Back.newLine;
			}			
			return lines;
		}
	}
	
	//TODO add minimum, maximum, dminimum, and dmaximum options

	private Grid pnlGrid = new Grid();
	private CrossoverProbability pnlCrossoverProbability = new CrossoverProbability();
	private MutationProbability pnlMutationProbability = new MutationProbability();
	private Configurations pnlConfigurations = new Configurations();
	private TournamentSelectionProbability pnlTSP = new TournamentSelectionProbability();
	private Optimization pnlOptimisation = new Optimization();

	private JLabel lblMinCostFunctionDifference = new JLabel("<html>any two candidates must have a cost function difference of at least</html>");
	private JLabel lblTwo = new JLabel("2");

	private JTextField txtDiscretizationIntervalPower = new JTextField("6");
	private JTextField txtMinCostFunctionDifference = new JTextField("0.0");
	private JTextField txtRandomNumberSeed = new JTextField("-1");

	public GeneticAlgorithm() {
		super();
		setBorder(new CompoundBorder(null, null));
		setLayout(null);

		pnlTSP.setTitle("tournament selection probability");
		pnlTSP.setBounds(2, 166, 404, 93);
		add(pnlTSP);
		pnlGrid.setTitle("grid");
		pnlGrid.setBounds(2, 4, 352, 76);
		add(pnlGrid);
		pnlCrossoverProbability.setTitle("crossover probability");
		pnlCrossoverProbability.setBounds(360, 86, 284, 74);
		add(pnlCrossoverProbability);
		pnlMutationProbability.setTitle("mutation probability");
		pnlMutationProbability.setBounds(2, 86, 352, 74);
		add(pnlMutationProbability);
		pnlConfigurations.setTitle("configurations");
		pnlConfigurations.setBounds(413, 168, 343, 91);
		add(pnlConfigurations);
		pnlOptimisation.setTitle("final candidate optimization");
		pnlOptimisation.setBounds(2, 265, 756, 49);
		add(pnlOptimisation);

		final TitledPanel pnlRandomNumberSeed = new TitledPanel();
		pnlRandomNumberSeed.setBounds(650, 86, 228, 74);
		pnlRandomNumberSeed.setTitle("random number seed");
		add(pnlRandomNumberSeed);
		txtRandomNumberSeed.setBounds(11, 20, 67, 20);
		pnlRandomNumberSeed.add(txtRandomNumberSeed);

		final TitledPanel pnlFittingResolution = new TitledPanel();
		pnlFittingResolution.setTitle("fitting resolution");
		pnlFittingResolution.setBounds(360, 4, 518, 77);
		add(pnlFittingResolution);
		lblTwo.setBounds(287, 22, 10, 20);
		pnlFittingResolution.add(lblTwo);
		txtDiscretizationIntervalPower.setBounds(298, 10, 26, 20);
		pnlFittingResolution.add(txtDiscretizationIntervalPower);
		JLabel lblDiscretizationInterval = new JLabel("<html>discretization interval for a fitted variable</html>");
		lblDiscretizationInterval.setBounds(10, 19, 271, 27);
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
		lblMinCostFunctionDifference.setBounds(10, 48, 423, 27);
		pnlFittingResolution.add(lblMinCostFunctionDifference);
		txtMinCostFunctionDifference.setBounds(439, 51, 69, 20);
		pnlFittingResolution.add(txtMinCostFunctionDifference);
	}

	private String writeDiscrete() {
		// TODO check format and add proper error checking
		String lines = "", text = txtDiscretizationIntervalPower.getText();
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
		String lines = "", text = txtRandomNumberSeed.getText();
		if (!text.equals("") && !text.equals("-1")) {
			Double.parseDouble(text);
			lines = "seed " + text + Back.newLine;
		}
		return lines;
	}

	private String writeUnique() {
		String lines = "", text = txtMinCostFunctionDifference.getText();
		if (!text.equals("") && !text.equals("0.0")) {
			Double.parseDouble(text);
			lines = "unique " + text + Back.newLine;
		}
		return lines;
	}
}