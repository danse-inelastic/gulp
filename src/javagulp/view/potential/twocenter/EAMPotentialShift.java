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

import utility.misc.G;

public class EAMPotentialShift extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 7072736025158773762L;

	private G g = new G();

	private JLabel lblEq = new JLabel(g.html("E = 2.0 g r<sup>6</sup>(exp(-"
			+ g.beta + " r) + 2<sup>9</sup>exp(-2 " + g.beta + " r))"));
	private JLabel lblUnits = new JLabel("units");

	private PPP G = new PPP("g");
	private PPP Beta = new PPP(g.html(g.beta
			+ " (&Aring;<sup>-1</sup>)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal", "au"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});

	public EAMPotentialShift() {
		super(2);
		setTitle("EAM Potential Shift");
		enabled = new boolean[] { true, true, true, true, true, true };

		add(lblEq);
		lblEq.setBounds(23, 20, 326, 48);
		lblEq.setOpaque(false);
		G.setBounds(10, 70, 225, 25);
		add(G);
		Beta.setBounds(10, 95, 225, 25);
		add(Beta);
		lblUnits.setBounds(11, 139, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(62, 139, 70, 21);
		add(cboUnits);
		cboEnerGra.setBounds(11, 164, 85, 21);
		add(cboEnerGra);
		radii = new Radii(true);
		radii.setBounds(240, 95, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{G, Beta};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		
		Potential pot = Back.getPanel().getPotential();

		String lines = "eam_potential_shift " + pot.twoAtomBondingOptions.getInterIntraBond(); 
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad ";
		else
			lines += "ener ";
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() 
					+ Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " ";
		return lines + radii.writeRadii() + Back.writeFits(params) + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		EAMPotentialShift eam = new EAMPotentialShift();
		eam.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		eam.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		return super.clone(eam);
	}
}