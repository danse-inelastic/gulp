package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Qtaper extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -3158630926642421049L;
	private final G g = new G();

	private final PPP C = new PPP("C (eV)");

	private final JLabel lblEq = new JLabel(g.html("E = q<sub>i</sub>q<sub>j</sub>/r f(r) + C (1 - f(r)) <br>"
			+ "where<br>" + "f(r) = polynomial taper"));
	private final JLabel lblUnits = new JLabel("units");

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public Qtaper() {
		super(2);
		setTitle("qtaper");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBounds(19, 24, 223, 55);
		add(lblEq);
		C.setBounds(19, 101, 225, 25);
		add(C);
		lblUnits.setBounds(19, 126, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(66, 126, 70, 21);
		add(cboUnits);
		radii = new Radii();
		radii.setBounds(240, 100, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{C};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);

		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		String line = "qtaper " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			line += cboUnits.getSelectedItem() + " ";
		line += pot.twoAtomBondingOptions.getScale14() + Back.newLine
		+ pot.getAtomCombos() + Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			line += " " + radii.writeRadii();
		}
		return line + Back.writeFits(params) + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		final Qtaper q = new Qtaper();
		q.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(q);
	}
}