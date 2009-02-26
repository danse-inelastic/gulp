package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class DampedDispersion extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 5997431758239364163L;
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEquation = new JLabel(
			"<html>E = -(C<sub>6</sub> / r<sup>6</sup>)f<sub>6</sub>(r) - " +
			"(C<sub>8</sub> / r<sup>8</sup>)f<sub>8</sub>(r) - " +
			"(C<sub>10</sub> / r<sup>10</sup>)f<sub>10</sub>(r)" + Back.newLine + "where " +
			"f<sub>2n</sub>(r) is given by:" + Back.newLine + "f<sub>2n</sub>(r) = 1 - " +
			"{sum(k=0 -> 2n)[(b * r)<sup>k</sup>] / k!} * exp(-b * r)</html>");
	
	private PPP c6 = new PPP("C6");
	private PPP c8 = new PPP("C8");
	private PPP c10 = new PPP("C10");
	private PPP b6 = new PPP("B6");
	private PPP b8 = new PPP("B8");
	private PPP b10 = new PPP("B10");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public DampedDispersion() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };
		
		setTitle("damped_dispersion");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 450, 50);
		add(c6);
		c6.setBounds(10, 95, 225, 21);
		add(c8);
		c8.setBounds(10, 120, 225, 21);
		c8.txt.setText("0");
		add(c10);
		c10.setBounds(10, 145, 225, 21);
		c10.txt.setText("0");
		add(b6);
		b6.setBounds(10, 170, 225, 21);
		b6.txt.setText("0");
		add(b8);
		b8.setBounds(10, 195, 225, 21);
		b8.txt.setText("0");
		add(b10);
		b10.setBounds(10, 220, 225, 21);
		b10.txt.setText("0");
		add(lblUnits);
		lblUnits.setBounds(280, 145, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(360, 145, 70, 21);
		radii = new Radii(true);
		radii.setBounds(240, 170, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{c6, c8, c10, b6, b8, b10};
	}

	@Override
	public PotentialPanel clone() {
		DampedDispersion dd = new DampedDispersion();
		dd.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(dd);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		// TODO documentation is ambiguous. It claims defaults for everything
		// except c6, but also claims c6, c8, and c10 must be specified.
		PPP[] fields = {c6, c8, c10};
		Back.checkAndParseD(fields);
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		
		String lines = "damped " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine 
				+ pot.getAtomCombos() + Back.concatFields(fields);
		if (!b6.txt.getText().equals("") && !b6.txt.getText().equals("0")){
			Double.parseDouble(b6.txt.getText());
			lines += " " + b6.txt.getText();
		}
		if (!b8.txt.getText().equals("") && !b8.txt.getText().equals("0")){
			Double.parseDouble(b8.txt.getText());
			lines += " " + b8.txt.getText();
		}
		if (!b10.txt.getText().equals("") && !b10.txt.getText().equals("0")){
			Double.parseDouble(b10.txt.getText());
			lines += " " + b10.txt.getText();
		}
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}
}
