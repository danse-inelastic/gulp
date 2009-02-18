package javagulp.view.md;

import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

	class MDMassless extends TitledPanel {

		private static final long serialVersionUID = -7716838461323185087L;

		private JLabel lblOptimizeShells = new JLabel("optimize shells at each time step for");
		private JLabel lblGradientNorm = new JLabel("iterations or until gradient norm");
		private JLabel lblShellExtrapolation = new JLabel("using rational function extrapolation of order");
		private JLabel lblIterationsReached = new JLabel("is reached");

		private final String TXTN = "10";
		private JTextField txtN = new JTextField(TXTN);
		private final String TXT_GRADIENT_NORM = "1.0D-10";
		private JTextField txtGradientNorm = new JTextField(TXT_GRADIENT_NORM);
		private final String TXT_EXTRAPOLATION = "8";
		private JTextField txtExtrapolation = new JTextField(TXT_EXTRAPOLATION);

		private MDMassless() {
			setTitle("MD with massless shells");
			lblOptimizeShells.setBounds(10, 22, 230, 15);
			add(lblOptimizeShells);
			txtN.setBounds(246, 20, 55, 20);
			add(txtN);
			lblGradientNorm.setBounds(10, 45, 206, 15);
			add(lblGradientNorm);
			txtGradientNorm.setBackground(Back.grey);
			txtGradientNorm.setBounds(222, 43, 63, 20);
			add(txtGradientNorm);
			lblShellExtrapolation.setBounds(10, 66, 285, 15);
			add(lblShellExtrapolation);
			txtExtrapolation.setBackground(Back.grey);
			txtExtrapolation.setBounds(301, 64, 55, 20);
			add(txtExtrapolation);
			lblIterationsReached.setBounds(291, 46, 65, 15);
			add(lblIterationsReached);
		}

		private String writeIterations() throws InvalidOptionException {
			String lines = "";
			if (!txtN.getText().equals("") && !txtN.getText().equals(TXTN)) {
				Integer.parseInt(txtN.getText());
				lines = "iterations " + txtN.getText();
				if (!txtGradientNorm.getText().equals("")
						&& !txtGradientNorm.getText().equals(TXT_GRADIENT_NORM)) {
					double d = Double.parseDouble(txtGradientNorm.getText());
					if (d < 1 || d > 10)
						throw new InvalidOptionException("MD iterations gradient norm must be between 1.0 and 10.0");
					lines += " " + txtGradientNorm.getText();
				}
				//TODO G.N. must be written if Extrapolation is written
				if (!txtExtrapolation.getText().equals("")
						&& !txtExtrapolation.getText().equals(TXT_EXTRAPOLATION)) {
					Integer.parseInt(txtExtrapolation.getText());
					lines += " " + txtExtrapolation.getText();
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}
