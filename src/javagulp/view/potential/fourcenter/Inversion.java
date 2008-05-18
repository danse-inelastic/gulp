package javagulp.view.potential.fourcenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;
import utility.misc.SerialListener;

public class Inversion extends PotentialPanel implements Serializable {
	private static final long serialVersionUID = -2291778583428349603L;

	G g = new G();
	
	private String text = g.html("E = k * (1 - cos " + g.phi + ")");
	private String squared = g.html("E = 1/2 (k/sin(k0)" + g.sup("2") + ") * (cos " + g.phi + " - cos k0)" + g.sup("2"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEq = new JLabel();
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblK0 = new JLabel("k0");
	
	private JTextField txtK0 = new JTextField();
	
	private JCheckBox chkSquared = new JCheckBox("squared");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	private PPP K = new PPP("k");
	
	SerialListener keySquared = new SerialListener() {
		private static final long serialVersionUID = -2516546900856198341L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (chkSquared.isSelected()) {
				lblEq.setText(squared);
				lblK0.setVisible(true);
				txtK0.setVisible(true);
			} else {
				lblEq.setText(text);
				lblK0.setVisible(false);
				txtK0.setVisible(false);
			}
		}
	};
	
	public Inversion() {
		super(4);
		setTitle("inversion");
		
		lblEq.setBounds(10, 25, 640, 25);
		add(lblEq);
		lblImage.setBounds(518, 24, 190, 75);
		add(lblImage);
		chkSquared.setBounds(10, 50, 75, 25);
		add(chkSquared);
		chkSquared.addActionListener(keySquared);
		K.setBounds(10, 75, 225, 25);
		add(K);
		lblUnits.setBounds(10, 125, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 125, 70, 21);
		add(cboUnits);
		radii = new Radii(new String[] {"12", "13", "14"});
		radii.setBounds(240, 75, radii.getWidth(), radii.getHeight());
		add(radii);
		lblK0.setBounds(10, 100, 75, 25);
		add(lblK0);
		txtK0.setBounds(90, 100, 100, 20);
		add(txtK0);
		
		keySquared.actionPerformed(null);
		
		params = new PPP[]{K};
	}

	@Override
	public PotentialPanel clone() {
		Inversion i = new Inversion();
		i.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		i.chkSquared.setSelected(this.chkSquared.isSelected());
		i.txtK0.setText(this.txtK0.getText());
		return super.clone(i);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		
		String lines = "inversion ";
		if (chkSquared.isSelected())
			lines += "squared ";
		Potential pot = Back.getPanel().getPotential();
		lines += pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " ";
		if (chkSquared.isSelected()) {
			if (txtK0.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for k0");
			Double.parseDouble(txtK0.getText());
			lines += txtK0.getText() + " ";
		}
		return lines + radii.writeRadii() + Back.writeFits(params) + Back.newLine;
	}
}