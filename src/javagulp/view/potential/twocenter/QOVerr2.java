package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class QOVerr2 extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -8213892552287868532L;

	private final JLabel lblEquation = new JLabel("<html>E = q<sub>i</sub>q<sub>j</sub> / r<sup>2</sup></html>");
	private final JLabel lblUnits = new JLabel("units");

	private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
			new String[] {"au", "nm", "pm"}));
	private final JComboBox cboEnerGrad = new JComboBox(new DefaultComboBoxModel(
			new String[] {"energy", "gradient"}));

	public QOVerr2() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };

		setTitle("qoverr2");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 225, 21);
		cboEnerGrad.setBounds(10, 55, 85, 21);
		add(cboEnerGrad);
		lblUnits.setBounds(10, 90, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(90, 90, 70, 21);
		add(cboUnits);
		radii = new Radii();
		radii.setBounds(10, 125, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public PotentialPanel clone() {
		final QOVerr2 q = new QOVerr2();
		q.cboEnerGrad.setSelectedIndex(this.cboEnerGrad.getSelectedIndex());
		q.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(q);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "qoverr2 " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (cboEnerGrad.getSelectedIndex() == 1)
			lines += "grad ";
		else
			lines += "ener ";
		lines += Back.newLine + pot.getAtomCombos();
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += radii.writeRadii();
		}
		return lines + Back.newLine;
	}
}
