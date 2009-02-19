package javagulp.view.potential.twocenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class Polynomial extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -1569248852541678518L;

	private PPP[] params = new PPP[6];
	
	private G g = new G();
	private JComboBox cboPolynomialOrder = new JComboBox(new String[] { "1",
			"2", "3", "4", "5" });
	private JComboBox cboUnits = new JComboBox(new String[] { "kjmol", "kcal" });
	// private JCheckBox chkHarmonicFactor = new JCheckBox("harmonic factor");

	private JLabel lblPolynomialOrder = new JLabel("polynomial order");
	private JLabel lblEquation = new JLabel();
	private JLabel lblUnits = new JLabel("units");

	private SerialListener keyOrder = new SerialListener() {
		private static final long serialVersionUID = -1288769483834281993L;
		@Override
		public void actionPerformed(ActionEvent e) {
			for (PPP param: params)
				param.setVisible(false);

			int orderNum = cboPolynomialOrder.getSelectedIndex() + 2;
			String equation = "E = ";
			for (int i = 0; i < orderNum; i++) {
				equation += "c<sub>" + i + "</sub>" + "*r<sup>" + i + "</sup> " + " + ";
				params[i].setVisible(true);
			}
			equation = equation.substring(0, equation.length() - 3);
			// remove last " + "
			lblEquation.setText(g.html(equation));
		}
	};

	public Polynomial() {
		super(2);
		setBorder(new TitledBorder(null, "Polynomial",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		enabled = new boolean[] { true, true, true, true, true, true };
		
		int x = 10, y = 80;
		for (int i=0; i < params.length; i++) {
			if (i == params.length / 2) {
				x = 240;
				y = 80;
			}
			params[i] = new PPP("<html>c<sub>"+i+"</sub> (eV)</html>");
			add(params[i]);
			params[i].setBounds(x, y, 225, 25);
			y += 25;
		}
		lblEquation.setBounds(10, 55, 450, 25);
		add(lblEquation);
		lblUnits.setBounds(480, 130, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(586, 130, 70, 21);
		add(cboUnits);
		lblPolynomialOrder.setBounds(7, 20, 110, 15);
		add(lblPolynomialOrder);
		cboPolynomialOrder.addActionListener(keyOrder);
		cboPolynomialOrder.setBounds(123, 17, 52, 24);
		add(cboPolynomialOrder);
		cboPolynomialOrder.setSelectedIndex(0);
		radii = new Radii(true);
		radii.setBounds(10, 130, radii.getWidth(), radii.getHeight());
		add(radii);
		// chkHarmonicFactor.setBounds(460, 45, 120, 20);
		// add(chkHarmonicFactor);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "";
		int index = cboPolynomialOrder.getSelectedIndex() + 2;
		PPP[] coefs = new PPP[index];
		for (int i = 0; i < index; i++) {
			coefs[i] = params[i];
		}
		Back.checkAndParseD(coefs);
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		
		lines = "polynomial " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += pot.twoAtomBondingOptions.getScale14() + (index + 1) + Back.newLine 
			+ pot.getAtomCombos() + Back.concatFields(coefs);
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		return lines + Back.writeFits(coefs) + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		Polynomial p = new Polynomial();
		p.cboPolynomialOrder.setSelectedIndex(this.cboPolynomialOrder.getSelectedIndex());
		p.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(p);
	}
}
