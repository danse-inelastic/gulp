package javagulp.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.model.Keywords;
import javagulp.view.bottom.ChargesElementsBonding;
import javagulp.view.bottom.Electrostatics;
import javagulp.view.bottom.EwaldOptions;
import javagulp.view.bottom.Execution;
import javagulp.view.bottom.Output;
import javagulp.view.bottom.Potential;
import javagulp.view.bottom.PotentialOptions;
import javagulp.view.bottom.Structures;
import javagulp.view.top.Constraints;
import javagulp.view.top.Defect;
import javagulp.view.top.EnergeticsMatProp;
import javagulp.view.top.ExternalForce;
import javagulp.view.top.Fit;
import javagulp.view.top.FreeEnergy;
import javagulp.view.top.GeneticAlgorithm;
import javagulp.view.top.MD;
import javagulp.view.top.MDRestartInit;
import javagulp.view.top.MonteCarlo;
import javagulp.view.top.Optimization;
import javagulp.view.top.Phonons;
import javagulp.view.top.StructurePrediction;
import javagulp.view.top.Surface;
import javagulp.view.top.TransitionState;
import javagulp.view.top.XYZFit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utility.misc.SerialKeyAdapter;

public class GulpRun extends JPanel implements Serializable {

	private static final long serialVersionUID = -4350272075095363083L;

//	private String[] topNames = { "MD", "MDRestartInit", "MonteCarlo",
//			"EnergeticsMatProp", "Optimization", "Constraints", "Fit",
//			"XYZFit", "Phonons", "FreeEnergy", "TransitionState",
//			"StructurePrediction", "GeneticAlgorithm", "Defect", "Surface",
//			"ExternalForce" };

	private String[] topNames = { "MD", "MonteCarlo",
			"EnergeticsMatProp", "Optimization", "Constraints", "Fit",
			"XYZFit", "Phonons", "FreeEnergy", "TransitionState",
			"StructurePrediction", "GeneticAlgorithm", "Surface",
			"ExternalForce" };
	
	private String[] bottomNames = { "Structures", "Potential",
			"PotentialOptions", "ChargesElementsBonding", "Electrostatics",
			"EwaldOptions", "Output", "Execution" };

	private JPanel[] top = new JPanel[topNames.length];
	private JPanel[] bottom = new JPanel[bottomNames.length];

	public JTabbedPane topPane = new JTabbedPane();
	public JScrollPane topScroll = new JScrollPane(topPane);

	public JTabbedPane bottomPane = new JTabbedPane();
	public JScrollPane bottomScroll = new JScrollPane(bottomPane);

	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
			topScroll, bottomScroll);

	private Keywords keywords = null;

	public GulpRun() {
		super();
		setLayout(new BorderLayout());

		splitPane.setDividerLocation((Back.frame.getHeight() - 135) / 2);
		splitPane.setDividerSize(4);
		splitPane.setResizeWeight(0.5);
		add(splitPane, BorderLayout.CENTER);

		topPane.addChangeListener(keyTop);
		topPane.add(null, "molecular dynamics");
		//topPane.add(null, "molecular dynamics restart");
		topPane.add(null, "monte carlo");
		//topPane.add(null, "");
		//topPane.setEnabledAt(2, false);
		topPane.add(null, "energetics and material properties");
		topPane.add(null, "optimization");
		topPane.add(null, "constraints");
		topPane.add(null, "fit");
		topPane.add(null, "XYZ fit");
		topPane.add(null, "phonons");
		//topPane.add(null, "");
		//topPane.setEnabledAt(8, false);
		topPane.add(null, "free energy");
		//topPane.add(null, "");
		//topPane.setEnabledAt(9, false);
		topPane.add(null, "transition state");
		//topPane.add(null, "");
		//topPane.setEnabledAt(10, false);
		topPane.add(null, "structure prediction");
		//topPane.add(null, "");
		//topPane.setEnabledAt(11, false);
		topPane.add(null,"genetic algorithm");
		//topPane.add(null, "");
		//topPane.setEnabledAt(12, false);
		// topPane.add(null, "defects");
		//topPane.add(null, "");
		//topPane.setEnabledAt(13, false);
		topPane.add(null, "surface");
		//topPane.add(null, "");
		//topPane.setEnabledAt(14, false);
		topPane.add(null, "external force");
		//topPane.add(null, "");
		//topPane.setEnabledAt(15, false);
		
		bottomPane.addChangeListener(keyBottom);
		bottomPane.addKeyListener(keyDelete);
		bottomPane.add(null, "structures");
		bottomPane.add(null, "potentials");
		bottomPane.add(null, "potential options");
		bottomPane.add(null, "charges, elements and bonding");
		bottomPane.add(null, "electrostatics");
		//bottomPane.add(null, "");
		//bottomPane.setEnabledAt(4, false);
		bottomPane.add(null, "ewald options");
		//bottomPane.add(null, "");
		//bottomPane.setEnabledAt(5, false);
		bottomPane.add(null, "output");
		bottomPane.add(null, "execution");
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
			String pkg = "javagulp.view.top.";
			try {
				Class c = Class.forName(pkg + topNames[index]);
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
	
	private class BottomListener implements ChangeListener, Serializable {
		private static final long serialVersionUID = -7847271919463899366L;
	
		public void stateChanged(ChangeEvent e) {
			int index = bottomPane.getSelectedIndex();
			bottomPane.setComponentAt(index, getBottomPanel(index));
		}
	};
	private BottomListener keyBottom = new BottomListener();
	private SerialKeyAdapter keyDelete = new SerialKeyAdapter() {
		private static final long serialVersionUID = -3244021879612727287L;
		@Override
		public void keyReleased(KeyEvent e) {
			int index = bottomPane.getSelectedIndex();
			if (index == 0 && e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete all structures?") == JOptionPane.YES_OPTION) {
					Structures s = Back.getPanel().getStructures();
					s.tabs.removeAll();
					s.tabs.addTab("" + (s.tabs.getTabCount() + 1), s.new Structure());
				}
			}
		}
	};
	
	private JPanel getBottomPanel(int index) {
		if (bottom[index] == null) {
			String pkg = "javagulp.view.bottom.";
			try {
				Class c = Class.forName(pkg + bottomNames[index]);
				bottom[index] = (JPanel) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return bottom[index];
	}
	
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

	public MD getMd() {
		return (MD) getTopPanel(0);
	}

	public MDRestartInit getMdRestartInit() {
		return (MDRestartInit) getTopPanel(1);
	}

	public MonteCarlo getMonteCarlo() {
		return (MonteCarlo) getTopPanel(2);
	}

	private EnergeticsMatProp getEnergeticsMatProp() {
		return (EnergeticsMatProp) getTopPanel(3);
	}

	public Optimization getOptimization() {
		return (Optimization) getTopPanel(4);
	}
	
	public Constraints getConstraints() {
		return (Constraints) getTopPanel(5);
	}
	public Fit getFit() {
		return (Fit) getTopPanel(6);
	}

	public XYZFit getXyzfit() {
		return (XYZFit) getTopPanel(7);
	}

	public Phonons getPhonon() {
		return (Phonons) getTopPanel(8);
	}
	
	public FreeEnergy getFreeEnergy() {
		return (FreeEnergy) getTopPanel(9);
	}

	public TransitionState getTransitionState() {
		return (TransitionState) getTopPanel(10);
	}
	public StructurePrediction getStructurePrediction() {
		return (StructurePrediction) getTopPanel(11);
	}

	public GeneticAlgorithm getGeneticAlgorithm() {
		return (GeneticAlgorithm) getTopPanel(12);
	}

	public Defect getDefect() {
		return (Defect) getTopPanel(13);
	}

	public Surface getSurface() {
		return (Surface) getTopPanel(14);
	}

	public ExternalForce getExternalForce() {
		return (ExternalForce) getTopPanel(15);
	}
	
	//Bottom
	
	public Structures getStructures() {
		return (Structures) getBottomPanel(0);
	}

	public Potential getPotential() {
		return (Potential) getBottomPanel(1);
	}

	public PotentialOptions getPotentialOptions() {
		return (PotentialOptions) getBottomPanel(2);
	}

	public ChargesElementsBonding getChargesElementsBonding() {
		return (ChargesElementsBonding) getBottomPanel(3);
	}

	public Electrostatics getElectrostatics() {
		return (Electrostatics) getBottomPanel(4);
	}

	public EwaldOptions getEwaldOptions() {
		return (EwaldOptions) getBottomPanel(5);
	}

	public Output getOutput() {
		return (Output) getBottomPanel(6);
	}

	public Execution getExecution() {
		return (Execution) getBottomPanel(7);
	}
}