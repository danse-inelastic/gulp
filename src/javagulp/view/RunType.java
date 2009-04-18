package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.structPredict.GeneticAlgorithm;
import javagulp.view.structPredict.SimulatedAnnealing;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javagulp.model.SerialListener;

public class RunType extends JPanel implements Serializable {

	private static final long serialVersionUID = 7887034029631999478L;

	private JLabel lblRunType = new JLabel("set run type:");

	private String[] runTypeLabels = {"molecular dynamics", "monte carlo", 
			"energetics and material properties", "optimization", "fit", "phonons", 
			"free energy", "transition state", 
			"structure prediction", "surface"};
	
//	private String[] runTypeClassNames = { "MolecularDynamics", "MonteCarlo",
//			"EnergeticsMatProp", "Optimization", "Fit", "Phonons", "FreeEnergy", 
//			"TransitionState", "StructurePrediction", "Surface"};

	private Map<String, String> labelsAndClasses =
		new HashMap<String, String>()   
		{  
		//Anonymous Inner class  
		{  
			put("molecular dynamics", "MolecularDynamics");  
			put("monte carlo", "MonteCarlo");  
			put("energetics and material properties", "EnergeticsMatProp");  
			put("optimization", "Optimization");  
			put("fit", "Fit"); 
			put("phonons", "Phonons"); 
			put("free energy", "FreeEnergy"); 
			put("transition state", "TransitionState"); 
			put("structure prediction", "StructurePrediction"); 
			put("surface", "Surface"); 
		}  
		};
	
	public Map<String, JPanel> runTypes =   
		new HashMap<String, JPanel>()   
		{  
		//Anonymous Inner class  
		{  
			put("molecular dynamics", null);  
			put("monte carlo", null);  
			put("energetics and material properties", null);  
			put("optimization", null);  
			put("fit", null); 
			put("phonons", null); 
			put("free energy", null); 
			put("transition state", null); 
			put("structure prediction", null); 
			put("surface", null); 
		}  
		};

	//public JPanel[] runTypes = new JPanel[labelsAndClasses.size()];
	
	private JComboBox cboRunType = new JComboBox(runTypeLabels);
//	private GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
//	private SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
	private JScrollPane scrollPane = new JScrollPane();
	
	private SerialListener keyRunType = new SerialListener() {
		private static final long serialVersionUID = -2241183516916061456L;
		@Override
		public void actionPerformed(ActionEvent e) {
			String type = (String)cboRunType.getSelectedItem();
			scrollPane.add(getRunType(type));
			scrollPane.setViewportView(getRunType(type));
		}
	};
	
	private JPanel getRunType(String type){
		if (runTypes.get(type) == null) {
			String pkg = "javagulp.view.";
			try {
				Class c = Class.forName(pkg + labelsAndClasses.get(type));
				runTypes.put(type, (JPanel) c.newInstance());
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
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
		lblRunType.setBounds(10, 11, 130, 20);
		add(lblRunType);

		scrollPane.setBounds(0, 39, 1282, 592);
		add(scrollPane);
		runTypes.put("molecular dynamics", new MolecularDynamics());
		scrollPane.add(runTypes.get("molecular dynamics"));
		scrollPane.setViewportView(runTypes.get("molecular dynamics"));

		cboRunType.addActionListener(keyRunType);
		cboRunType.setBounds(146, 9, 169, 24);
		add(cboRunType);
	}
	
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
//	private EnergeticsMatProp getEnergeticsMatProp() {
//		return (EnergeticsMatProp) getRunType(2);
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