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
import javagulp.model.SerialListener;

public class Torexp extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 7849238242046889539L;

	private G g = new G();

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblN = new JLabel(g.html("n"));
	private JLabel lblPhi0 = new JLabel(g.html(g.phi + g.sub("0") + " (deg)<"));
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblFourBodyEq = new JLabel();

	
	private JTextField txtN = new JTextField();
	private JTextField txtPhi0 = new JTextField("0.0");
	
	private PPP KeV =   new PPP(g.html("k (eV)"));
	private PPP K1 =   new PPP(g.html("k<sub>1</sub (eV)"));
	private PPP K2 =   new PPP(g.html("k<sub>2</sub (eV)"));
	
	private PPP Rho12 = new PPP(g.html(g.rho + g.sub("12") + " (" + g.ang + ")"));
	private PPP Rho23 = new PPP(g.html(g.rho + g.sub("23") + " (" + g.ang + ")"));
	private PPP Rho34 = new PPP(g.html(g.rho + g.sub("34") + " (" + g.ang + ")"));

	private JCheckBox chkEsff = new JCheckBox("esff");
	private JCheckBox chkDreiding = new JCheckBox("dreiding");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private String strreg = g.html("E = k (1 + cos(n "
			+ g.phi + " - " + g.phi + "<sub>0</sub>)) exp(-r<sub>12</sub>/"
			+ g.rho + "<sub>12</sub>) " + "exp(-r<sub>23</sub>/" + g.rho
			+ "<sub>23</sub>) exp(-r<sub>34</sub>/" + g.rho + "<sub>34</sub>)");
	private String strEsff = g.html("E = [k<sub>1</sub> sin<sup>2</sup>" + g.theta
			+ "<sub>123</sub> sin<sup>2</sup>" + g.theta
			+ "<sub>234</sub> + k<sub>2</sub> sin<sup>n</sup>"
			+ g.theta + "<sub>123</sub> sin<sup>n</sup>" + g.theta
			+ "<sub>234</sub> cos(n " + g.phi + ")]exp(-r<sub>12</sub>/" + g.rho
			+ "<sub>12</sub>) exp(-r<sub>23</sub>/" + g.rho
			+ "<sub>23</sub>) exp(-r<sub>34</sub>/" + g.rho + "<sub>34</sub>)");
	
	private SerialListener keyEsff = new SerialListener() {
		private static final long serialVersionUID = 932410562178213543L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (chkEsff.isSelected()) {
				lblFourBodyEq.setText(strEsff);
				KeV.setVisible(false);
				lblPhi0.setVisible(false);
				txtPhi0.setVisible(false);
				K1.setVisible(true);
				K2.setVisible(true);
				params = new PPP[] {K1, K2, Rho12, Rho23, Rho34};
			} else {
				lblFourBodyEq.setText(strreg);
				KeV.setVisible(true);
				lblPhi0.setVisible(true);
				txtPhi0.setVisible(true);
				K1.setVisible(false);
				K2.setVisible(false);
				params = new PPP[] {KeV, Rho12, Rho23, Rho34};
			}
		}
	};
	
	public Torexp() {
		super(4);
		setTitle("exponentially-decaying torsional potential");
		this.setPreferredSize(new java.awt.Dimension(644, 231));

		lblFourBodyEq.setBounds(15, 24, 665, 33);
		add(lblFourBodyEq);
		lblN.setBounds(10, 120, 70, 14);
		add(lblN);
		txtN.setBounds(90, 120, 100, 20);
		add(txtN);
		KeV.setBounds(10, 95, 225, 25);
		add(KeV);
		K1.setBounds(10, 95, 225, 25);
		add(K1);
		Rho12.setBounds(10, 170, 225, 25);
		add(Rho12);
		Rho23.setBounds(10, 195, 225, 25);
		add(Rho23);
		Rho34.setBounds(10, 220, 225, 25);
		add(Rho34);
		lblUnits.setBounds(10, 245, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 245, 70, 21);
		add(cboUnits);
		radii = new Radii(new String[] {"12", "23", "34", "41"});
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		lblImage.setBounds(460, 66, 190, 75);
		add(lblImage);
		txtPhi0.setBackground(Back.grey);
		txtPhi0.setBounds(90, 145, 100, 20);
		add(txtPhi0);
		lblPhi0.setBounds(10, 145, 75, 25);
		add(lblPhi0);
		K2.setBounds(10, 145, 225, 25);
		add(K2);
		chkEsff.setBounds(10, 70, 75, 25);
		add(chkEsff);
		chkEsff.addActionListener(keyEsff);
		chkDreiding.setBounds(90, 70, 100, 25);
		add(chkDreiding);
		keyEsff.actionPerformed(null);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Potential pot = Back.getPanel().getPotential();
		if (txtN.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for n");
		Double.parseDouble(txtN.getText());
		
		String esffDreiding = "";
		if (chkEsff.isSelected())
			esffDreiding += "esff";
		if (chkDreiding.isSelected())
			esffDreiding += "dreiding";
		String lines = "torexp " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += esffDreiding + Back.newLine + pot.getAtomCombos();
		if (chkEsff.isSelected()) {
			lines += writeEsff();
		} else {
			lines += writeRegular();
		}
		return lines + Back.newLine;
	}
	
	private String writeRegular() throws IncompleteOptionException {
		PPP[] k = {KeV};
		Back.checkAndParseD(k);
		
		String lines = KeV.txt.getText() + " " + txtN.getText() + " ";
		if (!txtPhi0.getText().equals("") && !txtPhi0.getText().equals("0.0")) {
			Double.parseDouble(txtPhi0.getText());
			lines += txtPhi0.getText() + " ";
		}
		PPP[] rho = { Rho12, Rho23, Rho34  };
		lines +=  Back.concatFields(rho) + " " + radii.writeRadii() + Back.writeFits(k) + Back.writeFits(rho);
		return lines;
	}
	
	private String writeEsff() throws IncompleteOptionException {
		PPP[] params = {K1, K2};
		Back.checkAndParseD(params);
		
		String lines = Back.concatFields(params) + " " + txtN.getText() + " ";
		PPP[] rho = { Rho12, Rho23, Rho34  };
		lines +=  Back.concatFields(rho) + " " + radii.writeRadii() + Back.writeFits(params) + Back.writeFits(rho);
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		Torexp t = new Torexp();
		t.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		t.chkDreiding.setSelected(this.chkDreiding.isSelected());
		t.chkEsff.setSelected(this.chkEsff.isSelected());
		t.txtN.setText(this.txtN.getText());
		t.txtPhi0.setText(this.txtPhi0.getText());
		return super.clone(t);
	}
}