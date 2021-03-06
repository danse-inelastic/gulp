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

public class SimulatedAnnealing extends JPanel implements Serializable {
	private static final long serialVersionUID = 2993595893032807688L;

	private final G g = new G();
	private final JCheckBox chkWriteRestartFiles = new JCheckBox("write restart files before performing local minimizations");

	private final KeywordListener keyWriteRestartFiles = new KeywordListener(chkWriteRestartFiles, "global");

	private final JLabel lblTempTol = new JLabel("temperature tolerance");
	private final JLabel lblFactor = new JLabel(g.html("temperature reduction factor <br>(larger->faster temperature decay)"));

	private final JTextField txtFactor = new JTextField("0.9");
	private final JTextField txtTtol = new JTextField("0.01");

	private final Temperature pnlTemperature = new Temperature(100);

	public SimulatedAnnealing() {
		super();
		setLayout(null);

		pnlTemperature.setBounds(10, 7, 469, 106);
		add(pnlTemperature);

		final TitledPanel pnlOptions = new TitledPanel();
		pnlOptions.setLayout(null);
		pnlOptions.setBounds(10, 119, 469, 143);
		add(pnlOptions);
		pnlOptions.setTitle("options");

		chkWriteRestartFiles.setBounds(10, 25, 449, 25);
		pnlOptions.add(chkWriteRestartFiles);
		chkWriteRestartFiles.addActionListener(keyWriteRestartFiles);

		lblFactor.setBounds(10, 53, 299, 47);
		pnlOptions.add(lblFactor);
		pnlOptions.add(txtFactor);
		lblTempTol.setBounds(10, 106, 213, 21);
		pnlOptions.add(lblTempTol);
		pnlOptions.add(txtTtol);
	}

	private String writeFactor() {
		String lines = "";
		if (txtFactor.getText().equals("") && txtFactor.getText().equals("0.9")) {
			Double.parseDouble(txtFactor.getText());
			lines = "factor " + txtFactor.getText() + Back.newLine;
		}
		txtFactor.setBounds(315, 58, 80, 22);
		return lines;
	}

	private String writeTempTolerance() {
		String lines = "";
		if (txtTtol.getText().equals("") && txtTtol.getText().equals("0.01")) {
			Double.parseDouble(txtTtol.getText());
			lines = "ttol " + txtTtol.getText() + Back.newLine;
		}
		txtTtol.setBounds(315, 107, 80, 19);
		return lines;
	}

	public String writeSimulatedAnnealing() {
		return pnlTemperature.writeTemperature() + writeFactor() + writeTempTolerance();
	}
	/**
	 * @return
	 */
}
