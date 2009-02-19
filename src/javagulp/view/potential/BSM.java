package javagulp.view.potential;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import javagulp.model.G;

public class BSM extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 8288060557186331610L;

	private G g = new G();

	private JLabel lblEquation = new JLabel("<html>E(bs) = 1/2 * K(r - r<sub>0</sub>)<sup>2</sup></html>");
	
	private JComboBox cboExponential = new JComboBox(new String[] { "harmonic", "exponential", "single_exponential" });
	private JComboBox cboCoreShell = new JComboBox(new String[] { "core", "shell" });
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private PPP k = new PPP("K");
	private PPP r0 = new PPP("r0");
	private PPP rho = new PPP("rho");
	
	public BSM() {
		super(1);
		setTitle("breathing shell model");
		add(cboExponential);
		cboExponential.setBounds(10, 49, 168, 21);
		cboExponential.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (cboExponential.getSelectedIndex() == 0){
					lblEquation.setText("<html>E(bs) = 1/2 * K * (r - r<sub>0</sub>)<sup>2</sup></html>");
					rho.txt.setEnabled(false);
				}
				else if (cboExponential.getSelectedIndex() == 1){
					lblEquation.setText(g.html(
							"E(bs) = K * [exp(" + g.rho + " * (r - r<sub>0</sub>)) + exp(-" + g.rho +
							" * (r - r<sub>0</sub>))]"));
					rho.txt.setEnabled(true);
				}
				else if (cboExponential.getSelectedIndex() == 2){
					lblEquation.setText(g.html("E(bs) = K * exp(" + g.rho + " * (r - r<sub>0</sub>))"));
					rho.txt.setEnabled(true);
				}
			}
		});
		add(lblEquation);
		lblEquation.setBounds(10, 20, 300, 21);
		add(cboCoreShell);
		cboCoreShell.setBounds(10, 84, 63, 21);
		add(k);
		k.setBounds(10, 119, 225, 21);
		add(r0);
		r0.setBounds(10, 154, 225, 21);
		add(rho);
		rho.setBounds(10, 189, 225, 21);
		rho.txt.setEnabled(false);
		add(cboUnits);
		cboUnits.setBounds(90, 84, 85, 21);
		getParams();
	}
	
	@Override
	public PotentialPanel clone() {
		BSM bs = new BSM();
		getParams();
		bs.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		bs.cboExponential.setSelectedIndex(this.cboExponential.getSelectedIndex());
		bs.cboCoreShell.setSelectedIndex(this.cboCoreShell.getSelectedIndex());
		return super.clone(bs);
	}

	@Override
	public String writePotential() throws IncompleteOptionException, InvalidOptionException {
		String lines = "";
		getParams();
		Back.checkAndParseD(params);
	
		lines += "bsm" + Back.newLine;
		if (cboExponential.getSelectedIndex() != 0)
			lines += cboExponential.getSelectedItem() + " ";
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		lines += Back.getPanel().getPotential().createLibrary.getAtomCombos() + " " 
				+ cboCoreShell.getSelectedItem() + " " + Back.fieldsAndFits(params);
		return lines + Back.newLine;
	}
	
	private void getParams() {
		if (cboExponential.getSelectedIndex() == 0)
			params = new PPP[] {k, r0};
		else
			params = new PPP[] {k, rho, r0};
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
