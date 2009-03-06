package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Morse extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -1955767596650215691L;

	private G g = new G();

	private JTextField txtC = new JTextField("1.0");
	
	private JLabel lblMorseEq = new JLabel(g.html("E = D{[1 - exp(-a(r - r<sub>0</sub>))]<sup>2</sup> - 1} - C q<sub>i</sub>q<sub>j</sub>/r"));
	private JLabel lblC = new JLabel("Coul");
	private JLabel lblUnits = new JLabel("units");

	private PPP D = new PPP("D (eV)");
	private PPP A = new PPP(g.html("a (&Aring;<sup>-1</sup>)"));
	private PPP r0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"ev", "kjmol", "kcal"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});
	
	public Morse() {
		super(2);
		setTitle("morse");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblMorseEq.setOpaque(false);
		lblMorseEq.setBounds(6, 16, 265, 34);
		add(lblMorseEq);
		D.setBounds(10, 60, 225, 25);
		add(D);
		A.setBounds(10, 85, 225, 25);
		add(A);
		r0.setBounds(10, 110, 225, 25);
		add(r0);
		lblC.setBounds(10, 135, 50, 20);
		add(lblC);
		txtC.setBounds(90, 135, 100, 20);
		add(txtC);
		txtC.setBackground(Back.grey);
		lblUnits.setBounds(10, 160, 50, 21);
		//add(lblUnits);
		cboUnits.setBounds(90, 160, 85, 21);
		//add(cboUnits); //remove this for now since all other unit labels would be off...
		cboEnerGra.setBounds(10, 185, 85, 21);
		add(cboEnerGra);
		radii = new Radii(true);
		radii.setBounds(240, 60, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{D, A, r0};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		Back.checkAndParseD(params);

		String lines = "morse " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14();
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad";
		else
			lines += "ener";
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
		if (!txtC.getText().equals("") && !txtC.getText().equals("1.0")) {
			Double.parseDouble(txtC.getText());
			lines += " " + txtC.getText();
		}
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		Morse m = new Morse();
		//m.txtC.setText(this.txtC.getText());
		//m.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		//m.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(m);
	}
}