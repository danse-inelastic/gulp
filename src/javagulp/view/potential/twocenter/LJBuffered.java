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

public class LJBuffered extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -2558871822990040094L;
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblEquation = new JLabel("<html>E = A / (r + r<sub>0</sub>)<sup>m</sup> - B / (r " +
											"+ r<sub>0</sub>)<sup>n</sup></html>");
	
	private PPP m = new PPP("m");
	private PPP n = new PPP("n");
	private PPP a = new PPP("A");
	private PPP b = new PPP("B");
	private PPP r0 = new PPP("r0");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	public LJBuffered() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };
		
		setTitle("buffered lennard-jones");
		add(lblEquation);
		lblEquation.setBounds(10, 20, 225, 21);
		add(m);
		m.setBounds(10, 55, 225, 21);
		m.txt.setText("12");
		m.chk.setVisible(false);
		add(n);
		n.setBounds(10, 90, 225, 21);
		n.txt.setText("6");
		n.chk.setVisible(false);
		add(a);
		a.setBounds(10, 125, 225, 21);
		add(b);
		b.setBounds(10, 160, 225, 21);
		add(lblUnits);
		lblUnits.setBounds(10, 195, 40, 21);
		add(cboUnits);
		cboUnits.setBounds(90, 195, 70, 21);
		add(r0);
		r0.setBounds(280, 90, 225, 21);
		r0.txt.setText("0.0");
		radii = new Radii(true);
		radii.setBounds(240, 125, radii.getWidth(), radii.getHeight());
		add(radii);
		
		params = new PPP[]{a, b, r0};
	}

	@Override
	public PotentialPanel clone() {
		LJBuffered lj = new LJBuffered();
		lj.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(lj);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		String lines = "";
		Potential pot = Back.getPanel().getPotential();
		PPP[] fields = {a, b, r0};
		Back.checkAndParseD(fields);
		
		lines += "ljbuffered ";
		if (!m.txt.getText().equals("") && !m.txt.getText().equals("12"))
			lines += m.txt.getText() + " ";
		if (!n.txt.getText().equals("") && !n.txt.getText().equals("6"))
			lines += n.txt.getText() + " ";
		lines += pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
				+ Back.concatFields(fields);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(fields) + Back.newLine;
	}
}
