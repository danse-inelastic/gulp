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

public class StructurePrediction extends JPanel implements Serializable {

	private static final long serialVersionUID = 7887034029631999478L;

	private JLabel chkPredictCrystal = new JLabel("predict crystal structure using");

	
	private SerialListener keyTypeOfPrediction = new SerialListener() {
		private static final long serialVersionUID = 6109026665099623842L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getTaskKeywords().putTaskKeywords(structurePredTypes.get(cboTypeOfPrediction.getSelectedItem()));
			if (cboTypeOfPrediction.getSelectedItem().equals("genetic algorithms"))
				scrollPane.setViewportView(pnlGeneticAlgorithm);
			else
				scrollPane.setViewportView(pnlSimulatedAnnealing);
		}
	};
	private JComboBox cboTypeOfPrediction = new JComboBox(new String[] {
			"genetic algorithms", "simulated annealing" });
	private GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
	private SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
	private JScrollPane scrollPane = new JScrollPane();
	
//	private SerialListener keyCrystalStructure = new SerialListener() {
//		private static final long serialVersionUID = -2241183516916061456L;
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if (cboCrystalStructure.getSelectedItem().equals("genetic algorithms"))
//				scrollPane.setViewportView(pnlGeneticAlgorithm);
//			else
//				scrollPane.setViewportView(pnlSimulatedAnnealing);
//			updateKeywords();
//		}
//	};
	
	private Map<String, String> structurePredTypes =   
		new HashMap<String, String>()   
		{  
		//Anonymous Inner class  
		{  
			put("genetic algorithms", "predict genetic");  
			put("simulated annealing", "predict anneal");  
		}  
		};
	
//	private void updateKeywords() {
//		Back.getTaskKeywords().putTaskKeywords(structurePredTypes.get(cboCrystalStructure.getSelectedItem()));
//		
//		Back.getKeys().putOrRemoveKeyword(false, "genetic");
//		Back.getKeys().putOrRemoveKeyword(false, "anneal");
//		if (chkPredictCrystal.isSelected()) {
//			if (cboCrystalStructure.getSelectedItem().equals("genetic algorithms"))
//				Back.getKeys().putOrRemoveKeyword(true, "genetic");
//			else
//				Back.getKeys().putOrRemoveKeyword(true, "anneal");
//		}
//	}
	
	

	public StructurePrediction() {
		super();
		setLayout(null);

//		chkPredictCrystal.addActionListener(keyPredictCrystal);
//		chkPredictCrystal.setToolTipText("Perform structure prediction calculations");
		chkPredictCrystal.setBounds(10, 11, 255, 30);
		add(chkPredictCrystal);

		scrollPane.setBounds(10, 47, 987, 350);
		add(scrollPane);
		scrollPane.add(pnlGeneticAlgorithm);
		scrollPane.add(pnlSimulatedAnnealing);
		scrollPane.setViewportView(pnlGeneticAlgorithm);

		cboTypeOfPrediction.addActionListener(keyTypeOfPrediction);
		cboTypeOfPrediction.setBounds(271, 14, 169, 24);
		add(cboTypeOfPrediction);
	}
	
	public String writeStructurePrediction() throws IncompleteOptionException, InvalidOptionException {
		String lines = "";
		if (cboTypeOfPrediction.getSelectedItem().equals("genetic algorithms"))
			lines = pnlGeneticAlgorithm.writeGeneticAlgorithm();
		else
			lines = pnlSimulatedAnnealing.writeSimulatedAnnealing();
		return lines;
	}
}