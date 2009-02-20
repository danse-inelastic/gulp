package javagulp.view;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.model.Keywords;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GulpRun extends JPanel implements Serializable {

	private static final long serialVersionUID = -4350272075095363083L;

	private String[] tabNames = { "MolecularDynamics", "MonteCarlo",
			"EnergeticsMatProp", "Optimization", "Constraints", "Fit",
			"Phonons", "FreeEnergy", "TransitionState", "StructurePrediction", 
			"Surface", "ExternalForce","Structures", "Potential",
			"PotentialOptions", "ChargesElementsBonding", "Electrostatics",
			"EwaldOptions", "Output", "Execution" };
	
	//private String[] bottomNames = {};

	private JPanel[] top = new JPanel[tabNames.length];
//	private JPanel[] bottom = new JPanel[bottomNames.length];

	public JTabbedPane topPane = new JTabbedPane();
	public JScrollPane topScroll = new JScrollPane(topPane);



//	public JTabbedPane bottomPane = new JTabbedPane();
//	public JScrollPane bottomScroll = new JScrollPane(bottomPane);

//	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//			topScroll, bottomScroll);

	private Keywords keywords = null;

	public GulpRun() {
		super();
		//setLayout(new CardLayout());
		setLayout(new BorderLayout());

//		splitPane.setDividerLocation((Back.frame.getHeight() - 135) / 2);
//		splitPane.setDividerSize(4);
//		splitPane.setResizeWeight(0.5);
//		add(splitPane, BorderLayout.CENTER);
		
		add(topPane, BorderLayout.CENTER);

		topPane.addChangeListener(keyTop);
		topPane.add(null, "molecular dynamics");
		topPane.add(null, "monte carlo");
		topPane.add(null, "energetics and material properties");
		topPane.add(null, "optimization");
		topPane.add(null, "constraints");
		topPane.add(null, "fit");
		topPane.add(null, "phonons");
		topPane.add(null, "free energy");
		topPane.add(null, "transition state");
		topPane.add(null, "structure prediction");
		topPane.add(null, "surface");
		topPane.add(null, "external force");

		topPane.add(null, "structures");
		topPane.add(null, "potentials");
		topPane.add(null, "potential options");
		topPane.add(null, "charges, elements and bonding");
		topPane.add(null, "electrostatics");
		topPane.add(null, "ewald options");

		topPane.add(null, "output");
		topPane.add(null, "execution");
	}
	
	private class TopListener implements ChangeListener, Serializable {
		private static final long serialVersionUID = -7619847591444570775L;
		
		public void stateChanged(ChangeEvent e) {
			int index = topPane.getSelectedIndex();
			topPane.setComponentAt(index, getTopPanel(index));
		}
	};
	private TopListener keyTop = new TopListener();
	
	private JPanel getTopPanel(int index) {
		if (top[index] == null) {
			String pkg = "javagulp.view.";
			try {
				Class c = Class.forName(pkg + tabNames[index]);
				top[index] = (JPanel) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return top[index];
	}
	
//	private class BottomListener implements ChangeListener, Serializable {
//		private static final long serialVersionUID = -7847271919463899366L;
//	
//		public void stateChanged(ChangeEvent e) {
//			int index = bottomPane.getSelectedIndex();
//			bottomPane.setComponentAt(index, getBottomPanel(index));
//		}
//	};
//	private BottomListener keyBottom = new BottomListener();
//	private SerialKeyAdapter keyDelete = new SerialKeyAdapter() {
//		private static final long serialVersionUID = -3244021879612727287L;
//		@Override
//		public void keyReleased(KeyEvent e) {
//			int index = bottomPane.getSelectedIndex();
//			if (index == 0 && e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//				if (JOptionPane.showConfirmDialog(null,
//						"Are you sure you want to delete all structures?") == JOptionPane.YES_OPTION) {
//					Structures s = Back.getPanel().getStructures();
//					s.tabs.removeAll();
//					s.tabs.addTab("" + (s.tabs.getTabCount() + 1), s.new Structure());
//				}
//			}
//		}
//	};
	
//	private JPanel getBottomPanel(int index) {
//		if (bottom[index] == null) {
//			String pkg = "javagulp.view.bottom.";
//			try {
//				Class c = Class.forName(pkg + bottomNames[index]);
//				bottom[index] = (JPanel) c.newInstance();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
//		return bottom[index];
//	}
	
	public String getWD() {
		return getExecution().txtWorkingDirectory.getText();
	}
	
	public String getBinary() {
		return getExecution().txtGulpBinary.getText();
	}
	
	public Keywords getKeywords() {
		if (keywords == null)
			keywords = new Keywords();
		return keywords;
	}
	
	//Top

	public MolecularDynamics getMd() {
		return (MolecularDynamics) getTopPanel(0);
	}

//	public MDRestartInit getMdRestartInit() {
//		return (MDRestartInit) getTopPanel(1);
//	}

	public MonteCarlo getMonteCarlo() {
		return (MonteCarlo) getTopPanel(1);
	}

	private EnergeticsMatProp getEnergeticsMatProp() {
		return (EnergeticsMatProp) getTopPanel(2);
	}

	public Optimization getOptimization() {
		return (Optimization) getTopPanel(3);
	}
	
	public Constraints getConstraints() {
		return (Constraints) getTopPanel(4);
	}
	public Fit getFit() {
		return (Fit) getTopPanel(5);
	}

//	public XYZFit getXyzfit() {
//		return (XYZFit) getTopPanel(7);
//	}

	public Phonons getPhonon() {
		return (Phonons) getTopPanel(6);
	}
	
	public FreeEnergy getFreeEnergy() {
		return (FreeEnergy) getTopPanel(7);
	}

	public TransitionState getTransitionState() {
		return (TransitionState) getTopPanel(8);
	}
	
	public StructurePrediction getStructurePrediction() {
		return (StructurePrediction) getTopPanel(9);
	}

//	public GeneticAlgorithm getGeneticAlgorithm() {
//		return (GeneticAlgorithm) getTopPanel(10);
//	}

//	public Defect getDefect() {
//		return (Defect) getTopPanel(13);
//	}

	public Surface getSurface() {
		return (Surface) getTopPanel(10);
	}

	public ExternalForce getExternalForce() {
		return (ExternalForce) getTopPanel(11);
	}
	
	public Structures getStructures() {
		return (Structures) getTopPanel(12);
	}

	public Potential getPotential() {
		return (Potential) getTopPanel(13);
	}

	public PotentialOptions getPotentialOptions() {
		return (PotentialOptions) getTopPanel(14);
	}

	public ChargesElementsBonding getChargesElementsBonding() {
		return (ChargesElementsBonding) getTopPanel(15);
	}

	public Electrostatics getElectrostatics() {
		return (Electrostatics) getTopPanel(16);
	}

	public EwaldOptions getEwaldOptions() {
		return (EwaldOptions) getTopPanel(17);
	}

	public Output getOutput() {
		return (Output) getTopPanel(18);
	}

	public Execution getExecution() {
		return (Execution) getTopPanel(19);
	}
}