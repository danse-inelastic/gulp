package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Harmonic extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7531784863663642510L;
	private G g = new G();

	private String equation = "E = 1/2 k<sub>2</sub>(r - r<sub>0</sub>)<sup>2</sup> + 1/6 k<sub>3</sub>(r - r<sub>0</sub>)<sup>3</sup> "
			+ "+ 1/24 k<sub>4</sub>(r - r<sub>0</sub>)<sup>4</sup> - C q<sub>i</sub>q<sub>j</sub>/r";
	private String[] labels = { "r<sub>0</sub> (&Aring;)",
			"k<sub>2</sub> (eV/&Aring;<sup>2</sup>)",
			"k<sub>3</sub> (eV/&Aring;<sup>3</sup>)",
			"k<sub>4</sub> (eV/&Aring;<sup>4</sup>)" };

	private JLabel lblC = new JLabel("C");
	private JLabel lblEquation = new JLabel(g.html(equation));
	private JLabel lblUnits = new JLabel("units");

	private PPP R0 = new PPP(g.html(labels[0]));
	private PPP K2 = new PPP(g.html(labels[1]));
	private PPP K3 = new PPP(g.html(labels[2]));
	private PPP K4 = new PPP(g.html(labels[3]));
	
	private JTextField txtC = new JTextField("1.0");
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});
	
	public Harmonic() {

		super(2);
		setLayout(null);
		setTitle("harmonic");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblC.setBounds(10, 155, 35, 21);
		txtC.setBounds(90, 155, 100, 20);
		lblEquation.setBounds(35, 14, 672, 28);
		R0.setBounds(10, 55, 225, 25);
		K2.setBounds(10, 80, 225, 25);
		K3.setBounds(10, 105, 225, 25);
		K4.setBounds(10, 130, 225, 25);
		lblUnits.setBounds(10, 180, 50, 21);
		cboUnits.setBounds(90, 180, 70, 21);
		cboEnerGra.setBounds(10, 205, 85, 21);
		
		add(lblC);
		add(txtC);
		add(lblEquation);
		add(R0);
		add(K2);
		add(K3);
		add(K4);
		add(lblUnits);
		add(cboEnerGra);
		add(cboUnits);

		txtC.setBackground(Back.grey);
		K3.txt.setBackground(Back.grey);
		K4.txt.setBackground(Back.grey);
		radii = new Radii(true);
		radii.setBounds(240, 55, radii.getWidth(), radii.getHeight());
		add(radii);
		
		R0.min = 0;
		R0.max = 3;
	}
	
	private boolean K3() {
		return !K3.txt.getText().equals("");
	}
	
	private boolean K4() {
		return !K4.txt.getText().equals("");
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		getParams();
		Back.checkAndParseD(params);

		// TODO check documentation for proper format
		String lines = "harmonic ";
		if (K3())
			lines += "k3 ";
		if (K4())
			lines += "k4 ";
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		lines += pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad";
		else
			lines += "ener";
		lines += pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
				+ Back.concatFields(params) + " ";
		if (!txtC.getText().equals("") && !txtC.getText().equals("1.0")) {
			Double.parseDouble(txtC.getText());
			lines += txtC.getText() + " ";
		}
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += radii.writeRadii();
		}
		lines += writeFits() + Back.newLine;
		return lines;
	}

	private String writeFits() {
		String lines = "";
		if (Back.getKeys().containsKeyword("fit")) {
			lines += checkFit(K2.chk);
			if (K3())
				lines += checkFit(K3.chk);
			if (K4())
				lines += checkFit(K4.chk);
			lines += checkFit(R0.chk);
		}
		return lines;
	}

	private String checkFit(JCheckBox chk) {
		if (chk.isSelected())
			return " 1";
		else
			return " 0";
	}

	@Override
	public PotentialPanel clone() {
		Harmonic h = new Harmonic();
		h.txtC.setText(this.txtC.getText());
		h.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		h.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(h);
	}
	
	private void getParams() {
		if (K3()) {
			if (K4()) {
				params = new PPP[]{K2, K3, K4, R0};
			} else {
				params = new PPP[]{K2, K3, R0};
			}
		} else {
			if (K4()) {
				params = new PPP[]{K2, K4, R0};
			} else {
				params = new PPP[]{K2, R0};
			}
		}
	}

	@Override
	public int currentParameterCount() {
		getParams();
		
		return super.currentParameterCount();
	}

	@Override
	public void setParameter(int i, String value) {
		getParams();
		
		super.setParameter(i, value);
	}
}