package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

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

	
//	private SerialListener keyPredictCrystal = new SerialListener() {
//		private static final long serialVersionUID = 6109026665099623842L;
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			updateKeywords();
//		}
//	};
	private String[] runTypeLabels = {
			"molecular dynamics", "monte carlo", "energetics and material properties",
			"optimization", "fit", "phonons", "free energy", "transition state", 
			"structure prediction", "surface"};
	
	private String[] runTypeClassNames = { "MolecularDynamics", "MonteCarlo",
			"EnergeticsMatProp", "Optimization", "Fit", "Phonons", "FreeEnergy", 
			"TransitionState", "StructurePrediction", "Surface"};
	
	public JPanel[] runTypes = new JPanel[runTypeClassNames.length];
	
	private JComboBox cboRunType = new JComboBox(runTypeLabels);
//	private GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
//	private SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
	private JScrollPane scrollPane = new JScrollPane();
	
	private SerialListener keyRunType = new SerialListener() {
		private static final long serialVersionUID = -2241183516916061456L;
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = cboRunType.getSelectedIndex();
			if (runTypes[index] == null) {
				String pkg = "javagulp.view.";
				try {
					Class c = Class.forName(pkg + runTypeClassNames[index]);
					runTypes[index] = (JPanel) c.newInstance();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				}
			}
			scrollPane.add(runTypes[index]);
			scrollPane.setViewportView(runTypes[index]);
		}
	};

	public RunType() {
		super();
		setLayout(null);

//		chkPredictCrystal.addActionListener(keyPredictCrystal);
//		chkPredictCrystal.setToolTipText("Performs structure prediction calculations");
		lblRunType.setBounds(10, 11, 255, 30);
		add(lblRunType);

		scrollPane.setBounds(10, 47, 987, 350);
		add(scrollPane);
		runTypes[0] = new MolecularDynamics();
		scrollPane.add(runTypes[0]);
		scrollPane.setViewportView(runTypes[0]);

		cboRunType.addActionListener(keyRunType);
		cboRunType.setBounds(271, 14, 169, 24);
		add(cboRunType);
	}
	
//	public String writeStructurePrediction() throws IncompleteOptionException, InvalidOptionException {
//		String lines = "";
//		if (cboRunType.getSelectedItem().equals("genetic algorithms"))
//			lines = pnlGeneticAlgorithm.writeGeneticAlgorithm();
//		else
//			lines = pnlSimulatedAnnealing.writeSimulatedAnnealing();
//		return lines;
//	}
}