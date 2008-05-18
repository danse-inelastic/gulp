package javagulp.view.top;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class StructurePrediction extends JPanel implements Serializable {

	private static final long serialVersionUID = 7887034029631999478L;

	private class SimulatedAnnealing extends JPanel implements Serializable {
		private static final long serialVersionUID = 2993595893032807688L;
		
		private JLabel lblTempTol = new JLabel("temperature tolerance");
		private JLabel lblFactor = new JLabel(g.html("temperature reduction factor <br>(larger->faster temperature decay)"));
		
		private JTextField txtFactor = new JTextField("0.9");
		private JTextField txtTtol = new JTextField("0.01");

		private Temperature pnlTemperature = new Temperature(100);
		
		private SimulatedAnnealing() {
			super();
			setLayout(null);
			
			pnlTemperature.setBounds(10, 7, 311, 106);
			add(pnlTemperature);
			
			lblFactor.setBounds(10, 105, 299, 47);
			add(lblFactor);
			txtFactor.setBounds(314, 120, 80, 22);
			add(txtFactor);
			lblTempTol.setBounds(10, 149, 213, 21);
			add(lblTempTol);
			txtTtol.setBounds(314, 150, 80, 19);
			add(txtTtol);
			
			final TitledPanel pnlOptions = new TitledPanel();
			pnlOptions.setLayout(null);
			pnlOptions.setBounds(0, 188, 404, 50);
			add(pnlOptions);
			pnlOptions.setTitle("options");

			chkWriteRestartFiles.setBounds(0, 20, 394, 25);
			pnlOptions.add(chkWriteRestartFiles);
			chkWriteRestartFiles.addActionListener(keyWriteRestartFiles);
		}

		private String writeFactor() {
			String lines = "";
			if (txtFactor.getText().equals("") && txtFactor.getText().equals("0.9")) {
				Double.parseDouble(txtFactor.getText());
				lines = "factor " + txtFactor.getText() + Back.newLine;
			}
			return lines;
		}
		
		private String writeTempTolerance() {
			String lines = "";
			if (txtTtol.getText().equals("") && txtTtol.getText().equals("0.01")) {
				Double.parseDouble(txtTtol.getText());
				lines = "ttol " + txtTtol.getText() + Back.newLine;
			}
			return lines;
		}

		private String writeSimulatedAnnealing() {
			return pnlTemperature.writeTemperature() + writeFactor() + writeTempTolerance();
		}
	}
	
	private G g = new G();

	private JCheckBox chkPredictCrystal = new JCheckBox("predict crystal structure using");
	private JCheckBox chkWriteRestartFiles = new JCheckBox("write restart files before performing local minimizations");

	private KeywordListener keyWriteRestartFiles = new KeywordListener(chkWriteRestartFiles, "global");
	
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
		Back.getKeys().putOrRemoveKeyword(chkPredictCrystal.isSelected(), "predict");
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
		chkPredictCrystal.setBounds(3, 3, 220, 30);
		add(chkPredictCrystal);

		scrollPane.setBounds(3, 30, 1024, 385);
		add(scrollPane);
		scrollPane.add(pnlGeneticAlgorithm);
		scrollPane.add(pnlSimulatedAnnealing);
		scrollPane.setViewportView(pnlGeneticAlgorithm);

		cboCrystalStructure.addActionListener(keyCrystalStructure);
		cboCrystalStructure.setBounds(229, 6, 169, 24);
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