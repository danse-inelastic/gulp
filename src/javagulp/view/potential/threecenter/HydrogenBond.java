package javagulp.view.potential.threecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;
import javagulp.view.top.Potential;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class HydrogenBond extends PotentialPanel implements Serializable {
	private static final long serialVersionUID = 7011842152178311038L;

	private G g = new G();
	
	private PPP A = new PPP(g.html("A (eV * " + g.ang + "<sup>m</sup>)"));
	private PPP B = new PPP(g.html("B (eV * " + g.ang + "<sup>n</sup>)"));
	
	private JLabel lblUnits = new JLabel("units");
	private JLabel lblM = new JLabel("m");
	private JLabel lblN = new JLabel("n");
	private JLabel lblP = new JLabel("p");
	private JLabel lblThetaMin = new JLabel(g.html(g.theta + "min"));
	private JLabel lblThetaMax = new JLabel(g.html(g.theta + "max"));
	private JLabel lblEquation = new JLabel(g.html("E = (A/(r<sup>m</sup>) - B/(r<sup>n</sup>))*(cos " + g.theta + ")<sup>p</sup><br>If " + g.theta + " > 90 degrees, else 0"));
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
	
	private JTextField txtM = new JTextField("12");
	private JTextField txtN = new JTextField("10");
	private JTextField txtP = new JTextField("4");
	private JTextField txtThetaMin = new JTextField();
	private JTextField txtThetaMax = new JTextField();
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	
	public HydrogenBond() {
		super(3);
		setTitle("hydrogen-bond");
		enabled = new boolean[] {true, true, false};
		
		lblEquation.setBounds(10, 25, 400, 40);
		add(lblEquation);
		A.setBounds(10, 70, 225, 25);
		add(A);
		B.setBounds(10,95, 225, 25);
		add(B);
		lblM.setBounds(10, 120, 75, 25);
		add(lblM);
		txtM.setBounds(90, 120, 100, 20);
		add(txtM);
		txtM.setBackground(Back.grey);
		lblN.setBounds(10, 145, 75, 25);
		add(lblN);
		txtN.setBounds(90, 145, 100, 20);
		add(txtN);
		txtN.setBackground(Back.grey);
		lblP.setBounds(10, 170, 75, 25);
		add(lblP);
		txtP.setBounds(90, 170, 100, 20);
		add(txtP);
		txtP.setBackground(Back.grey);
		lblThetaMin.setBounds(10, 195, 75, 25);
		add(lblThetaMin);
		txtThetaMin.setBounds(90, 195, 100, 20);
		add(txtThetaMin);
		txtThetaMin.setBackground(Back.grey);
		lblThetaMax.setBounds(10, 220, 75, 25);
		add(lblThetaMax);
		lblUnits.setBounds(10, 245, 40, 21);
		add(lblUnits);
		txtThetaMax.setBounds(90, 220, 100, 20);
		add(txtThetaMax);
		cboUnits.setBounds(90, 245, 70, 21);
		add(cboUnits);
		txtThetaMax.setBackground(Back.grey);
		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(240, 120, radii.getWidth(), radii.getHeight());
		add(radii);
		lblImage.setBounds(419, 11, 122, 80);
		add(lblImage);
		
		params = new PPP[] {A, B};
	}
	
	@Override
	public PotentialPanel clone() {
		HydrogenBond h = new HydrogenBond();
		h.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		h.txtM.setText(this.txtM.getText());
		h.txtN.setText(this.txtN.getText());
		h.txtP.setText(this.txtP.getText());
		h.txtThetaMin.setText(this.txtThetaMin.getText());
		h.txtThetaMax.setText(this.txtThetaMax.getText());
		return super.clone(h);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();
		String m = txtM.getText(), n = txtN.getText(), p = txtP.getText(),
			min = txtThetaMin.getText(), max = txtThetaMax.getText();
		
		String lines = "hydrogen-bond " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (!m.equals("") && !m.equals("12")) {
			Double.parseDouble(m);
			lines += m + " ";
		}
		if (!n.equals("") && !n.equals("10")) {
			Double.parseDouble(n);
			lines += n + " ";
		}
		if (!p.equals("") && !p.equals("4")) {
			Double.parseDouble(p);
			lines += p + " ";
		}
		if (!min.equals("") && !max.equals("")) {
			if (min.equals(""))
				throw new IncompleteOptionException("Please enter a value for theta min");
			if (max.equals(""))
				throw new IncompleteOptionException("Please enter a value for theta max");
			Double.parseDouble(min);
			Double.parseDouble(max);
			lines += "taper";
		}
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(params) + " ";
		if (!min.equals("") && !max.equals("")) {
			lines += min + " " + max + " ";
		}
		lines += radii.writeRadii() + Back.writeFits(params) + Back.newLine;
		return lines;
	}
}