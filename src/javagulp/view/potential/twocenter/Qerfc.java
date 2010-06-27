package javagulp.view.potential.twocenter;

import java.awt.Color;
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

public class Qerfc extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 6970835068233843810L;

	private final G g = new G();

	private final PPP rho = new PPP(g.html(g.rho + " (&Aring;)"));

	private final JComboBox cboUnits = new JComboBox(new String[] { "au", "nm", "pm" });
	private final JComboBox cboEnerGra = new JComboBox(new String[] { "energy", "gradient" });

	private final JLabel lblUnits = new JLabel("units");
	private final JLabel lblEq = new JLabel("<html>E = q<sub>i</sub>q<sub>j</sub>/r erfc(r/" + g.rho + ")</html>");

	public Qerfc() {
		super(2);
		setTitle("qerfc");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblEq.setOpaque(false);
		lblEq.setBackground(Color.white);
		lblEq.setBounds(17, 24, 159, 25);
		add(lblEq);
		rho.setBounds(17, 61, 225, 25);
		add(rho);
		lblUnits.setBounds(17, 86, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(70, 86, 70, 21);
		add(cboUnits);
		cboEnerGra.setBounds(17, 111, 85, 21);
		add(cboEnerGra);
		radii = new Radii(false);
		radii.setBounds(240, 60, radii.getWidth(), radii.getHeight());
		add(radii);

		params = new PPP[]{rho};
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		// From the documentation: NOTE : The keyword "noelectrostatics"
		// should be included when using this potential to turn off the normal
		// Ewald sum, otherwise the Coulomb interaction will be double counted.
		Back.getKeys().putOrRemoveKeyword(true, "noelectrostatics");

		Back.checkAndParseD(params);

		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		String line = "qerfc " + pot.twoAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			line += cboUnits.getSelectedItem() + " ";
		if (cboEnerGra.getSelectedIndex() == 1)
			line += "grad";
		else
			line += "ener";
		line += Back.newLine + getAtoms() + Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			line += radii.writeRadii();
		}
		return line + Back.writeFits(params) + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		final Qerfc q = new Qerfc();
		q.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		q.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(q);
	}
}