package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.SerialListener;
import javagulp.view.energetics.FreeEnergy;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RunType extends JPanel implements Serializable {

	private JScrollPane scrollPane_1;
	private JList list;
	private JButton addButton;
	private static final long serialVersionUID = 7887034029631999478L;

	private final JLabel lblRunType = new JLabel("run type:");

	private final String[] runTypeLabels = {"(free) energy", 
			"optimization", "fit",
			"phonons", //"free energy calc/optimize",
			"molecular dynamics", "monte carlo",
			//"energetics and material properties",
			//"surface calc/optimize",
			"transition state",
			"structure prediction"};

	//	private String[] runTypeClassNames = { "MolecularDynamics", "MonteCarlo",
	//			"EnergyMatProps", "Optimization", "Fit", "Phonons", "FreeEnergy",
	//			"TransitionState", "StructurePrediction", "Surface"};

	//TODO probably should combine free energy with optimize and energy tabs with option panel and/or checkbox in each which, if checked,
	//would replace the task keyword with the free energy keyword and still allow the user to input pertinent options (i.e. optimization options)

	//TODO should also combine surface with optimize and energy tabs with option panel

	//TODO
	//instead of three maps one should actually have made a bunch of small classes...
	//but when you do, comment out runTypeLabels and provide another map with integers for how the run
	//types should be ordered in the combo box
	private final Map<String, String> labelsAndClasses = new HashMap<String, String>() {
		//Anonymous Inner class
		{
			put("molecular dynamics", "MolecularDynamics");
			put("monte carlo", "MonteCarlo");
			put("(free) energy", "SinglePointEnergy");
			put("optimization", "Optimization");
			put("fit", "Fit");
			put("phonons", "Phonons");
			//put("surface calc/optimize", "SurfaceOptions");
			put("transition state", "TransitionState");
			put("structure prediction", "StructurePrediction");
		}
	};

	private final Map<String, JPanel> runTypes =
		new HashMap<String, JPanel>() {
		//Anonymous Inner class
		{
			put("molecular dynamics", null);
			put("monte carlo", null);
			put("(free) energy", null);
			put("optimization", null);
			put("fit", null);
			put("phonons", null);
			//put("surface calc/optimize", null);
			put("transition state", null);
			put("structure prediction", null);
		}
		};

		private final Map<String, String> runTypeKeywords =
			new HashMap<String, String>() {
			//Anonymous Inner class
			{
				put("(free) energy", "");
				put("molecular dynamics", "md");
				put("monte carlo", "montecarlo conv");
				//put("energetics and material properties", "energy");
				put("optimization", "optimise");
				put("fit", "fit");
				put("phonons", "phonon");
				//put("free energy calc/optimize", "free_energy");
				//put("surface calc/optimize", "");
				put("transition state", "transition_state");
				put("structure prediction", "predict");
			}
			};

			//public JPanel[] runTypes = new JPanel[labelsAndClasses.size()];

			protected JComboBox cboRunType = new JComboBox(runTypeLabels);
			//	private GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
			//	private SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
			private final JScrollPane scrollPane = new JScrollPane();

			//private TaskKeywordListener keyMD = new TaskKeywordListener("md");

			private final SerialListener keyRunType = new SerialListener() {
				private static final long serialVersionUID = -2241183516916061456L;
				@Override
				public void actionPerformed(ActionEvent e) {
					final String type = (String)cboRunType.getSelectedItem();
					scrollPane.add(getSelectedRunTypePanel(type));
					scrollPane.setViewportView(getSelectedRunTypePanel(type));
				}
			};
			private final DefaultListModel listModel = new DefaultListModel();

			protected JPanel getSelectedRunTypePanel(String type){
				if (runTypes.get(type) == null) {
					final String pkg = "javagulp.view.";
					try {
						final Class c = Class.forName(pkg + labelsAndClasses.get(type));
						runTypes.put(type, (JPanel) c.newInstance());
					} catch (final ClassNotFoundException ex) {
						ex.printStackTrace();
					} catch (final InstantiationException ex) {
						ex.printStackTrace();
					} catch (final IllegalAccessException ex) {
						ex.printStackTrace();
					}
				}
				return runTypes.get(type);
			}

			public RunType() {
				super();
				setLayout(null);

				//		chkPredictCrystal.addActionListener(keyPredictCrystal);
				//		chkPredictCrystal.setToolTipText("Performs structure prediction calculations");
				lblRunType.setBounds(10, 20, 95, 20);
				add(lblRunType);

				scrollPane.setBounds(0, 56, 1154, 629);
				add(scrollPane);
				runTypes.put("(free) energy", new SinglePointEnergy());
				scrollPane.add(runTypes.get("(free) energy"));
				scrollPane.setViewportView(runTypes.get("(free) energy"));

				cboRunType.setMaximumRowCount(30);
				cboRunType.addActionListener(keyRunType);
				cboRunType.setBounds(111, 18, 340, 24);
				add(cboRunType);
				add(getAddButton());
				add(getScrollPane_1());
			}

			public String writeRuntype() throws IncompleteOptionException, InvalidOptionException {
				String runtypeLines="";
				//final String optionChosen = (String)cboRunType.getSelectedItem();
				if(listModel.size()>0){
					for (final Object objectChosen: listModel.toArray()){
						final String optionChosen = (String)objectChosen;
						if (optionChosen.equals("optimization"))
							runtypeLines += ((Optimization)getSelectedRunTypePanel("optimization")).writeOptimization();
						else if (optionChosen.equals("structure prediction"))
							runtypeLines += ((StructurePrediction)getSelectedRunTypePanel("structure prediction")).write();
						else if (optionChosen.equals("phonons"))
							runtypeLines += ((Phonons)getSelectedRunTypePanel("phonons")).writePhonon();
						else if (optionChosen.equals("free energy calc/optimize"))
							runtypeLines += ((SinglePointEnergy)getSelectedRunTypePanel("(free) energy")).write();
						else if (optionChosen.equals("fit")) {
							runtypeLines += ((Fit)getSelectedRunTypePanel("fit")).writeFitOptions() +
							((Fit)getSelectedRunTypePanel("fit")).fitPanelHolder.writeFitPanels();
						} else if (optionChosen.equals("monte carlo"))
							runtypeLines += ((MonteCarlo)getSelectedRunTypePanel("monte carlo")).writeMonteCarlo();
						else if (optionChosen.equals("molecular dynamics"))
							runtypeLines += ((MolecularDynamics)getSelectedRunTypePanel("molecular dynamics")).writeMD();
						//		getDefect().writeDefect();
					}
				}
				return runtypeLines;
			}
			/**
			 * @return
			 */
			protected JButton getAddButton() {
				if (addButton == null) {
					addButton = new JButton();
					addButton.addActionListener(new ActionListener() {
						public void actionPerformed(final ActionEvent e) {
							final String optionChosen = (String)cboRunType.getSelectedItem();
							listModel.addElement(optionChosen);
							Back.getTaskKeywords().putOrRemoveTaskKeyword(true, runTypeKeywords.get(optionChosen));
							//getList().repaint();
						}
					});
					addButton.setText("add");
					addButton.setBounds(484, 19, 95, 22);
				}
				return addButton;
			}
			/**
			 * @return
			 */
			protected JList getList() {
				if (list == null) {
					list = new JList();
					list.setVisibleRowCount(1);
					list.setLayoutOrientation(JList.VERTICAL_WRAP);
					list.setModel(listModel);
				}
				return list;
			}
			/**
			 * @return
			 */
			protected JScrollPane getScrollPane_1() {
				if (scrollPane_1 == null) {
					scrollPane_1 = new JScrollPane();
					scrollPane_1.setBounds(586, 9, 568, 41);
					scrollPane_1.setViewportView(getList());
				}
				return scrollPane_1;
			}
			/**
			 * @return
			 */

			//	public MolecularDynamics getMd() {
			//		return (MolecularDynamics) getRunType(0);
			//	}
			//
			////	public MDRestartInit getMdRestartInit() {
			////		return (MDRestartInit) getTopPanel(1);
			////	}
			//
			//	public MonteCarlo getMonteCarlo() {
			//		return (MonteCarlo) getRunType(1);
			//	}
			//
			//	private EnergyMatProps getEnergeticsMatProp() {
			//		return (EnergyMatProps) getRunType(2);
			//	}
			//
			//	public Optimization getOptimization() {
			//		return (Optimization) getRunType(3);
			//	}
			//
			//	public Fit getFit() {
			//		return (Fit) getRunType(4);
			//	}
			//
			////	public XYZFit getXyzfit() {
			////		return (XYZFit) getTopPanel(7);
			////	}
			//
			//	public Phonons getPhonon() {
			//		return (Phonons) getRunType(5);
			//	}
			//
			//	public FreeEnergy getFreeEnergy() {
			//		return (FreeEnergy) getRunType(6);
			//	}
			//
			//	public TransitionState getTransitionState() {
			//		return (TransitionState) getRunType(7);
			//	}
			//
			//	public StructurePrediction getStructurePrediction() {
			//		return (StructurePrediction ) getRunType(8);
			//	}
			//
			////	public GeneticAlgorithm getGeneticAlgorithm() {
			////		return (GeneticAlgorithm) getTopPanel(10);
			////	}
			//
			////	public Defect getDefect() {
			////		return (Defect) getTopPanel(13);
			////	}
			//
			//	public Surface getSurface() {
			//		return (Surface) getRunType(9);
			//	}

			//	public String writeStructurePrediction() throws IncompleteOptionException, InvalidOptionException {
			//		String lines = "";
			//		if (cboRunType.getSelectedItem().equals("genetic algorithms"))
			//			lines = pnlGeneticAlgorithm.writeGeneticAlgorithm();
			//		else
			//			lines = pnlSimulatedAnnealing.writeSimulatedAnnealing();
			//		return lines;
			//	}
}