package javagulp.view.md;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Thermodynamics extends TitledPanel {

	private final G g = new G();

	private static final long serialVersionUID = 1579392356755048884L;
	private final JTextField txtQnose = new JTextField();//"0.05");
	private final JTextField txtQnose2 = new JTextField();//"0.05");
	private final JTextField txtQpress = new JTextField();//"0.05");

	private final JLabel lblPress = new JLabel(g.html("q<sub>press</sub>"));

	private final JCheckBox chkConserved = new JCheckBox("output conserved ensemble quantity");

	private final JRadioButton radNone = new JRadioButton();
	private final JRadioButton radEnsembleNPT = new JRadioButton(g.html("NPT ensemble, q<sub>nose</sub>"));
	private final JRadioButton radEnsembleNVE = new JRadioButton("NVE ensemble");
	private final JRadioButton radEnsembleNVT = new JRadioButton(g.html("NVT ensemble, q<sub>nose</sub>"));
	private final ButtonGroup ensemble = new ButtonGroup();

	private final KeywordListener keyChkConserved = new KeywordListener(chkConserved, "conserved");
	
	public Thermodynamics() {
		setTitle("thermodynamic ensembles");
		ensemble.add(radNone);
		radNone.setText("none");
		ensemble.add(radEnsembleNVE);
		ensemble.add(radEnsembleNVT);
		ensemble.add(radEnsembleNPT);
		radNone.setBounds(5, 21, 121, 25);
		add(radNone);
		radNone.setSelected(true);
		radEnsembleNVE.setBounds(5, 52, 165, 25);
		add(radEnsembleNVE);
		radEnsembleNVT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back.getKeys().putOrRemoveKeyword(radEnsembleNVT.isSelected(), "conv");
			}
		});
		radEnsembleNVT.setBounds(5, 83, 187, 25);
		add(radEnsembleNVT);
		radEnsembleNPT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back.getKeys().putOrRemoveKeyword(radEnsembleNPT.isSelected(), "conp");
			}
		});
		radEnsembleNPT.setBounds(5, 114, 187, 25);
		add(radEnsembleNPT);
		chkConserved.setBounds(5, 182, 294, 25);
		chkConserved.addActionListener(keyChkConserved);
		add(chkConserved);
		txtQnose.setBounds(198, 83, 63, 20);
		add(txtQnose);
		txtQnose2.setBounds(198, 114, 63, 20);
		add(txtQnose2);
		txtQpress.setBounds(198, 143, 63, 20);
		add(txtQpress);
		lblPress.setBounds(120, 145, 71, 20);
		add(lblPress);
	}

	public String writeEnsemble() throws IncompleteOptionException {
		String lines = "";
		if (radEnsembleNVE.isSelected())
			lines = "ensemble nve" + Back.newLine;
		else if (radEnsembleNVT.isSelected()) {
			if (!txtQnose.getText().equals("")){
				lines += "ensemble nvt " + txtQnose.getText() + Back.newLine;
			} else
				throw new IncompleteOptionException("Missing nvt qnose in Molecular Dynamics");
		} else if (radEnsembleNPT.isSelected()) {
			if (!txtQnose2.getText().equals("") && !txtQpress.getText().equals("")){
				lines += "ensemble npt " + txtQnose2.getText() + " " + txtQpress.getText() + Back.newLine;
			} else
				throw new IncompleteOptionException("Missing npt qnose or qpress in Molecular Dynamics");
		}
		return lines;
	}
}
