package javagulp.view.structPredict;

import java.io.Serializable;

import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;
import javagulp.view.md.Temperature;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class SimulatedAnnealing extends JPanel implements Serializable {
		private static final long serialVersionUID = 2993595893032807688L;
		
		private G g = new G();
		private JCheckBox chkWriteRestartFiles = new JCheckBox("write restart files before performing local minimizations");

		private KeywordListener keyWriteRestartFiles = new KeywordListener(chkWriteRestartFiles, "global");
		
		private JLabel lblTempTol = new JLabel("temperature tolerance");
		private JLabel lblFactor = new JLabel(g.html("temperature reduction factor <br>(larger->faster temperature decay)"));
		
		private JTextField txtFactor = new JTextField("0.9");
		private JTextField txtTtol = new JTextField("0.01");

		private Temperature pnlTemperature = new Temperature(100);
		
		SimulatedAnnealing() {
			super();
			setLayout(null);
			
			pnlTemperature.setBounds(10, 7, 503, 106);
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
			pnlOptions.setBounds(0, 188, 513, 50);
			add(pnlOptions);
			pnlOptions.setTitle("options");

			chkWriteRestartFiles.setBounds(0, 20, 495, 25);
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

		String writeSimulatedAnnealing() {
			return pnlTemperature.writeTemperature() + writeFactor() + writeTempTolerance();
		}
	}
