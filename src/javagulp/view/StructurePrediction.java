package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.SerialListener;
import javagulp.view.structPredict.GeneticAlgorithm;
import javagulp.view.structPredict.SimulatedAnnealing;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StructurePrediction extends JPanel implements Serializable {

	private static final long serialVersionUID = 7887034029631999478L;

	private final JLabel chkPredictCrystal = new JLabel("predict crystal structure using");


	private final SerialListener keyTypeOfPrediction = new SerialListener() {
		private static final long serialVersionUID = 6109026665099623842L;
		@Override
		public void actionPerformed(ActionEvent e) {
			String alg = (String)cboTypeOfPrediction.getSelectedItem();
			Back.getKeys().putOrRemoveKeyword(alg=="genetic algorithms", "genetic");
			Back.getKeys().putOrRemoveKeyword(alg=="simulated annealing", "anneal");
//			Back.getKeys().putTaskKeyword(structurePredTypes.get(cboTypeOfPrediction.getSelectedItem()));
//			if (cboTypeOfPrediction.getSelectedItem().equals("genetic algorithms"))
//				scrollPane.setViewportView(pnlGeneticAlgorithm);
//			else
//				scrollPane.setViewportView(pnlSimulatedAnnealing);
		}
	};
	private final JComboBox cboTypeOfPrediction = new JComboBox(new String[] {
			"genetic algorithms", "simulated annealing" });
	private final GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
	private final SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
	private final JScrollPane scrollPane = new JScrollPane();

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

//	private final Map<String, String> structurePredTypes =
//		new HashMap<String, String>()
//		{
//		//Anonymous Inner class
//		{
//			put("genetic algorithms", "predict genetic");
//			put("simulated annealing", "predict anneal");
//		}
//		};

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

			scrollPane.setBounds(10, 47, 1032, 373);
			add(scrollPane);
			scrollPane.add(pnlGeneticAlgorithm);
			scrollPane.add(pnlSimulatedAnnealing);
			scrollPane.setViewportView(pnlGeneticAlgorithm);

			cboTypeOfPrediction.addActionListener(keyTypeOfPrediction);
			cboTypeOfPrediction.setBounds(271, 14, 169, 24);
			add(cboTypeOfPrediction);
		}

		public String write() throws IncompleteOptionException, InvalidOptionException {
			String lines = "";
			String algType = (String)cboTypeOfPrediction.getSelectedItem();
			//Back.getTaskKeywords().putOrRemoveTaskKeyword(algType.equals("genetic algorithms"), "genetic");
			Back.getKeys().putOrRemoveKeyword(algType.equals("genetic algorithms"), "genetic");
			Back.getKeys().putOrRemoveKeyword(algType.equals("simulated annealing"), "anneal");
			if (algType.equals("genetic algorithms")){
				lines+="predict"+Back.newLine;
				lines+= pnlGeneticAlgorithm.writeGeneticAlgorithm();
				lines+="end"+Back.newLine;
			}else
				lines = pnlSimulatedAnnealing.writeSimulatedAnnealing();
			return lines;
		}
}