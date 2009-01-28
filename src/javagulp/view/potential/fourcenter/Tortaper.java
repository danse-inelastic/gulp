package javagulp.view.potential.fourcenter;

import java.awt.Color;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;
import javagulp.view.top.Potential;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Tortaper extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 4175306826241667614L;

	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private JCheckBox chkK = new JCheckBox("fit");
	private G g = new G();

	private JTextField txtK = new JTextField();
	private JTextField txtN = new JTextField();
	private JTextField txtPhi0 = new JTextField();
	private JTextField txtRTaper = new JTextField();

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblN = new JLabel(g.html("n"));
	private JLabel lblK = new JLabel(g.html("k (eV)"));
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblPhi0 = new JLabel("<html>" + "&#966" + "<sub>0</sub> (deg)</html>");
	private JLabel lblRTaper = new JLabel("<html>r<sub>taper</sub> (" + "&Aring" + ")</html>");
	private JLabel lblFourBodyEq = new JLabel(g.html("E = k (1 + cos(n " + g.phi + " - " + g.phi
		+ "<sub>0</sub>)) f(r<sub>12</sub>) f(r<sub>23</sub>) f(r<sub>34</sub>)<br>"
		+ "where f(r) = cosine tapering function"));

	public Tortaper() {
		super(4);
		setTitle("torsional potential with tapered cutoffs");

		lblFourBodyEq.setOpaque(false);
		lblFourBodyEq.setBackground(Color.white);
		lblFourBodyEq.setBounds(15, 24, 366, 58);
		add(lblFourBodyEq);
		lblN.setBounds(14, 126, 56, 14);
		add(lblN);
		txtN.setBounds(96, 123, 70, 20);
		add(txtN);
		lblK.setBounds(15, 99, 35, 15);
		add(lblK);
		txtK.setBounds(96, 97, 70, 20);
		add(txtK);
		chkK.setBounds(172, 94, 47, 25);
		add(chkK);
		lblImage.setBounds(460, 24, 190, 75);
		add(lblImage);
		txtPhi0.setBackground(Back.grey);
		txtPhi0.setBounds(96, 149, 70, 20);
		add(txtPhi0);
		lblPhi0.setBounds(15, 151, 65, 20);
		add(lblPhi0);
		txtRTaper.setBounds(96, 175, 70, 20);
		add(txtRTaper);
		lblRTaper.setBounds(15, 178, 65, 20);
		add(lblRTaper);
		lblUnits.setBounds(15, 200, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(96, 200, 70, 21);
		add(cboUnits);
		radii = new Radii(new String[] {"12", "23", "34", "41"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		JTextField[] fields = { txtK, txtN, txtRTaper};
		String[] descriptions = { "k", "n", "rtaper"};
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);
		Potential pot = Back.getPanel().getPotential();
		
		String lines = "tortaper " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + txtK.getText() + " "
				+ txtN.getText() + " ";
		if (!txtPhi0.getText().equals("")) {
			Double.parseDouble(txtPhi0.getText());
			lines += txtPhi0.getText() + " ";
		}
		lines += txtRTaper.getText() + " " + radii.writeRadii();
		JCheckBox[] boxes = {chkK};
		return lines + Back.writeFits(boxes) + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		return new Tortaper();
	}
	
	@Override
	public int currentParameterCount() {
		int count = 0;
		if (chkK.isSelected())
			count++;
		return count;
	}

	@Override
	public void setParameter(int i, String value) {
		int count = 0;
		if (chkK.isSelected()) {
			count++;
			if (i == count)
				txtK.setText(value);
		}
	}
}