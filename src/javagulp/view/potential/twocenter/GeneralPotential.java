package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utility.misc.G;

public class GeneralPotential extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 501308416265295195L;

	private JComboBox cboSetTo0 = new JComboBox(new String[] { " ", "energy",
			"energy gradient" });
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private G g = new G();
	
	private PPP A = new PPP("<html>A (eV/&Aring;<sup>m</sup>)</html>");
	private PPP Rho = new PPP("<html>&#961; (&Aring;)</html>");
	private PPP C = new PPP("<html>C (eV/&Aring;<sup>n</sup>)</html>");

	private JTextField txtM = new JTextField("0");
	private JTextField txtN = new JTextField("6");

	private JLabel lblM = new JLabel("m");
	private JLabel lblN = new JLabel("n");
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblSetTo0 = new JLabel("Set to 0 at cutoff:");
	private JLabel lblGeneralPotentialEq = new JLabel("<html>E = A exp(-r/&#961;) r<sup>-m</sup> - C r<sup>-n</sup></html>");

	public GeneralPotential() {
		super(2);
		setTitle("general potential");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblGeneralPotentialEq.setOpaque(false);
		lblGeneralPotentialEq.setBounds(10, 24, 170, 20);
		add(lblGeneralPotentialEq);
		A.setBounds(10, 55, 225, 25);
		add(A);
		Rho.setBounds(10, 80, 225, 25);
		add(Rho);
		C.setBounds(10, 105, 225, 25);
		add(C);
		lblM.setBounds(10, 130, 25, 20);
		add(lblM);
		txtM.setBackground(Back.grey);
		txtM.setBounds(90, 130, 100, 20);
		add(txtM);
		lblN.setBounds(10, 155, 25, 20);
		add(lblN);
		txtN.setBackground(Back.grey);
		txtN.setBounds(90, 155, 100, 20);
		add(txtN);
		lblSetTo0.setBounds(278, 105, 110, 15);
		add(lblSetTo0);
		cboSetTo0.setBounds(394, 102, 131, 20);
		add(cboSetTo0);
		cboUnits.setBounds(394, 127, 85, 21);
		lblUnits.setBounds(278, 127, 70, 21);
		add(lblUnits);
		add(cboUnits);
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{A, Rho, C};
		Rho.min = 0;
		Rho.max = 1;
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		
		String lines = "general ";
		if (!txtM.getText().equals("") && !txtM.getText().equals("0")) {
			Double.parseDouble(txtM.getText());
			lines += txtM.getText() + " ";
		}
		if (!txtN.getText().equals("") && !txtN.getText().equals("6")) {
			Double.parseDouble(txtN.getText());
			lines += txtN.getText() + " ";
		}
		if (cboSetTo0.getSelectedIndex() == 1)
			lines += "ener ";
		if (cboSetTo0.getSelectedIndex() == 2)
			lines += "grad ";
		//TODO The gulp documentation has abbreviated bonding options for this
		// potential, but it is surely a typo. test to make sure.
		Potential pot = Back.getPanel().getPotential();
		lines += pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " "; 
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
				+ Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		GeneralPotential g = new GeneralPotential();
		g.txtM.setText(this.txtM.getText());
		g.txtN.setText(this.txtN.getText());
		g.cboSetTo0.setSelectedIndex(this.cboSetTo0.getSelectedIndex());
		g.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(g);
	}
}