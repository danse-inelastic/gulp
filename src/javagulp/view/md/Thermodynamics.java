package javagulp.view.md;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

	public class Thermodynamics extends TitledPanel {
		
		private G g = new G();

		private static final long serialVersionUID = 1579392356755048884L;
		private JTextField txtQnose = new JTextField();
		private JTextField txtQnose2 = new JTextField();
		private JTextField txtQpress = new JTextField();

		private JLabel lblPress = new JLabel(g.html("q<sub>press</sub>"));

		private JCheckBox chkConserved = new JCheckBox("output conserved ensemble quantity");

		private JRadioButton radNone = new JRadioButton();
		private JRadioButton radEnsembleNPT = new JRadioButton(g.html("NPT ensemble, q<sub>nose</sub>"));
		private JRadioButton radEnsembleNVE = new JRadioButton("NVE ensemble");
		private JRadioButton radEnsembleNVT = new JRadioButton(g.html("NVT ensemble, q<sub>nose</sub>"));
		private ButtonGroup ensemble = new ButtonGroup();

		public Thermodynamics() {
			setTitle("thermodynamic ensembles");
			ensemble.add(radNone);
			ensemble.add(radEnsembleNVE);
			ensemble.add(radEnsembleNVT);
			ensemble.add(radEnsembleNPT);
			radNone.setBounds(5, 15, 17, 17);
			add(radNone);
			radNone.setSelected(true);
			radEnsembleNVE.setBounds(5, 30, 165, 30);
			add(radEnsembleNVE);
			radEnsembleNVT.setBounds(5, 60, 187, 25);
			add(radEnsembleNVT);
			radEnsembleNPT.setBounds(5, 84, 187, 25);
			add(radEnsembleNPT);
			chkConserved.setBounds(5, 132, 294, 25);
			add(chkConserved);
			txtQnose.setBounds(198, 60, 63, 20);
			add(txtQnose);
			txtQnose2.setBounds(198, 84, 63, 20);
			add(txtQnose2);
			txtQpress.setBounds(198, 110, 63, 20);
			add(txtQpress);
			lblPress.setBounds(133, 112, 40, 20);
			add(lblPress);
		}

		public String writeEnsemble() throws IncompleteOptionException {
			String lines = "";
			if (radEnsembleNVE.isSelected())
				lines = "ensemble nve" + Back.newLine;
			else if (radEnsembleNVT.isSelected()) {
				if (!txtQnose.getText().equals(""))
					lines += "ensemble nvt " + txtQnose.getText() + Back.newLine;
				else
					throw new IncompleteOptionException("Missing nvt qnose in Molecular Dynamics");
			} else if (radEnsembleNPT.isSelected()) {
				if (!txtQnose2.getText().equals("")
						&& !txtQpress.getText().equals(""))
					lines += "ensemble npt " + txtQnose2.getText() + " "
							+ txtQpress.getText() + Back.newLine;
				else
					throw new IncompleteOptionException("Missing npt qnose or qpress in Molecular Dynamics");
			}
			if (chkConserved.isSelected())
				lines += "conserved" + Back.newLine;
			return lines;
		}
	}
