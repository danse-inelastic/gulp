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

import javagulp.model.G;

public class SquaredHarmonic extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 8449636138551488882L;

	private G g = new G();

	private JTextField txtC = new JTextField("1.0");
	
	private JLabel lblC = new JLabel("C");
	private JLabel lblEq = new JLabel(g.html("E = 1/4 k<sub>2</sub>(r<sup>2</sup>  - r<sub>0</sub><sup>2</sup>)"
			+ "<sup>2</sup> - C q<sub>i</sub>q<sub>j</sub>/r"));
	private JLabel lblUnits = new JLabel("units");

	private PPP K2 = new PPP(g.html("k<sub>2</sub> (eV/&Aring;<sup>4</sup>)"));
	private PPP r0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});
	
	public SquaredHarmonic() {
		super(2);
		setTitle("squared harmonic");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBounds(16, 20, 513, 37);
		add(lblEq);
		K2.setBounds(10, 55, 225, 25);
		add(K2);
		r0.setBounds(10, 80, 225, 25);
		add(r0);
		lblC.setBounds(10, 105, 75, 28);
		add(lblC);
		txtC.setBackground(Back.grey);
		txtC.setBounds(90, 105, 100, 20);
		add(txtC);
		lblUnits.setBounds(10, 130, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 130, 70, 21);
		add(cboUnits);
		cboEnerGra.setBounds(10, 155, 85, 21);
		add(cboEnerGra);
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{K2, r0};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);

		Potential pot = Back.getPanel().getPotential();
		String lines = "squaredharmonic " + pot.twoAtomBondingOptions.getInterIntraBond(); 
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
				+ Back.concatFields(params);
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
		SquaredHarmonic sq = new SquaredHarmonic();
		sq.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		sq.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		return super.clone(sq);
	}
}