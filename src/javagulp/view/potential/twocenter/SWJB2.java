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
import javax.swing.border.TitledBorder;

import utility.misc.G;

public class SWJB2 extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -5713798782373640010L;

	private G g = new G();

	private JLabel lblEq = new JLabel(g.html("E = A exp(" + g.rho
		+ "/(r - r<sub>cutoff</sub>))(B/r<sup>4</sup> - 1)*F(q<sub>i</sub>,q<sub>j</sub>)<br>"
		+ "F(q<sub>i</sub>,q<sub>j</sub>) = e<sup>1/Q</sup>e<sup>1/(q<sub>i<sub>+q<sub>j<sub>-Q)</sup> if q<sub>i</sub>+q<sub>j</sub> < Q F(q<sub>i</sub>,q<sub>j</sub>) = 0 if q<sub>i</sub>+q<sub>j</sub> > Q<br>"
		+ "See Chem. Eng. Sci., 49, 2991 (2000) for more details."));

	private PPP A = new PPP("A (eV)");
	private PPP rho = new PPP(g.html("rho (" + g.ang + ")"));
	private PPP B = new PPP(g.html("B (" + g.ang + "^4)"));
	private PPP Q = new PPP("Q (au)");
	
	private JLabel lblUnits = new JLabel("units");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	public SWJB2() {
		super(2);
		setBorder(new TitledBorder(null,
				"stillinger-weber-jiang-brown two body",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBounds(19, 18, 375, 60);
		add(lblEq);
		A.setBounds(10, 80, 225, 25);
		add(A);
		rho.setBounds(10, 105, 225, 25);
		add(rho);
		B.setBounds(10, 130, 225, 25);
		add(B);
		Q.setBounds(10, 155, 225, 25);
		add(Q);
		lblUnits.setBounds(10, 180, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 180, 70, 21);
		add(cboUnits);
		radii = new Radii(true);
		radii.setBounds(240,80, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{ A, rho, B, Q };
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Potential pot = Back.getPanel().getPotential();
		Back.checkAndParseD(params);
		
		String lines = "sw2jb " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos()
				+ Back.concatFields(params) + " " + radii.writeRadii()
				+ Back.writeFits(params) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		SWJB2 sw = new SWJB2();
		sw.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(sw);
	}
}