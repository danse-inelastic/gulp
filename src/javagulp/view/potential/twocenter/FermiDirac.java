package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;
import javagulp.view.top.Potential;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class FermiDirac extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -1726262428393032999L;
	
	private JLabel lblEquation = new JLabel("<html>E = a / (1 + exp(b(r - r<sub>0</sub>)))</html>");
	private JLabel lblUnits = new JLabel("units");
	
	private PPP a = new PPP("a");
	private PPP b = new PPP("b");
	private PPP r0 = new PPP("r0");
	
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public FermiDirac() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };

		setTitle("fermi-dirac");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 225, 21);
		add(a);
		a.setBounds(10, 70, 225, 25);
		add(b);
		b.setBounds(10, 95, 225, 25);
		add(r0);
		r0.setBounds(10, 120, 225, 25);
		add(lblUnits);
		lblUnits.setBounds(10, 145, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(90, 145, 70, 21);
		add(cboEnerGra);
		cboEnerGra.setBounds(10, 170, 100, 21);
		radii = new Radii(true);
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{a, b, r0};
	}

	@Override
	public PotentialPanel clone() {
		FermiDirac fd = new FermiDirac();
		fd.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		fd.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		return super.clone(fd);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();
		
		String lines = "fermi-dirac " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14(); 
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad";
		else
			lines += "ener";
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
}
