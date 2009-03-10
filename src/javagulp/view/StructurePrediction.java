package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.structPredict.GeneticAlgorithm;
import javagulp.view.structPredict.SimulatedAnnealing;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javagulp.model.SerialListener;

public class StructurePrediction extends JPanel implements Serializable {

	private static final long serialVersionUID = 7887034029631999478L;

	private JCheckBox chkPredictCrystal = new JCheckBox("predict crystal structure using");

	
	private SerialListener keyPredictCrystal = new SerialListener() {
		private static final long serialVersionUID = 6109026665099623842L;
		@Override
		public void actionPerformed(ActionEvent e) {
			updateKeywords();
		}
	};
	private JComboBox cboCrystalStructure = new JComboBox(new String[] {
			"genetic algorithms", "simulated annealing" });
	private GeneticAlgorithm pnlGeneticAlgorithm = new GeneticAlgorithm();
	private SimulatedAnnealing pnlSimulatedAnnealing = new SimulatedAnnealing();
	private JScrollPane scrollPane = new JScrollPane();
	
	private SerialListener keyCrystalStructure = new SerialListener() {
		private static final long serialVersionUID = -2241183516916061456L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (cboCrystalStructure.getSelectedItem().equals("genetic algorithms"))
				scrollPane.setViewportView(pnlGeneticAlgorithm);
			else
				scrollPane.setViewportView(pnlSimulatedAnnealing);
			updateKeywords();
		}
	};
	
	private void updateKeywords() {
		Back.getTaskKeys().putOrRemoveTaskKeyword(chkPredictCrystal.isSelected(), "predict");
		Back.getKeys().putOrRemoveKeyword(false, "genetic");
		Back.getKeys().putOrRemoveKeyword(false, "anneal");
		if (chkPredictCrystal.isSelected()) {
			if (cboCrystalStructure.getSelectedItem().equals("genetic algorithms"))
				Back.getKeys().putOrRemoveKeyword(true, "genetic");
			else
				Back.getKeys().putOrRemoveKeyword(true, "anneal");
		}
	}
	
	

	public StructurePrediction() {
		super();
		setLayout(null);

		chkPredictCrystal.addActionListener(keyPredictCrystal);
		chkPredictCrystal.setToolTipText("Performs structure prediction calculations");
		chkPredictCrystal.setBounds(10, 11, 255, 30);
		add(chkPredictCrystal);

		scrollPane.setBounds(10, 47, 987, 350);
		add(scrollPane);
		scrollPane.add(pnlGeneticAlgorithm);
		scrollPane.add(pnlSimulatedAnnealing);
		scrollPane.setViewportView(pnlGeneticAlgorithm);

		cboCrystalStructure.addActionListener(keyCrystalStructure);
		cboCrystalStructure.setBounds(271, 14, 169, 24);
		add(cboCrystalStructure);
	}
	
	public String writeStructurePrediction() throws IncompleteOptionException, InvalidOptionException {
		String lines = "";
		if (cboCrystalStructure.getSelectedItem().equals("genetic algorithms"))
			lines = pnlGeneticAlgorithm.writeGeneticAlgorithm();
		else
			lines = pnlSimulatedAnnealing.writeSimulatedAnnealing();
		return lines;
	}
}