package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Tsuneyuki extends PotentialPanel implements Serializable {
	private static final long serialVersionUID = 5942765183989223713L;

	private JComboBox cboGForm = new JComboBox(new String[] { "1", "2" });
	private JCheckBox chkZeta = new JCheckBox("fit");
	private G g = new G();

	private JTextField txtQ1 = new JTextField();
	private JTextField txtQ2 = new JTextField();
	private JTextField txtZeta = new JTextField();

	private JLabel lblGForm = new JLabel("g(r) form");
	private JLabel lblZeta = new JLabel(g.html(g.zeta + " (1/&Aring;)"));
	private JLabel lblQ2 = new JLabel(g.html("Q<sub>2</sub> (au)"));
	private JLabel lblQ1 = new JLabel(g.html("Q<sub>1</sub> (au)"));
	private JLabel lblEq = new JLabel(g.html("E = [Q<sub>1</sub>Q<sub>2</sub> - q<sub>1</sub>q<sub>2</sub>]g(r)/r<br>"
		+ "where <br>1) g(r) = (1 + " + g.zeta + " r)exp(-2 " + g.zeta
		+ " r) or<br>2) g(r) = (1 + 11(" + g.zeta + " r)/8 + 3(" + g.zeta
		+ " r)<sup>2</sup>/4 + (" + g.zeta + " r)<sup>3</sup>/6)exp(-2 "
		+ g.zeta + " r)<br>"));

	public Tsuneyuki() {
		super(2);
		setTitle("tsuneyuki");

		lblEq.setOpaque(false);
		lblEq.setBounds(7, 17, 428, 94);
		add(lblEq);
		lblQ1.setBounds(12, 150, 52, 26);
		add(lblQ1);
		lblQ2.setBounds(14, 178, 47, 26);
		add(lblQ2);
		lblZeta.setBounds(263, 181, 60, 15);
		add(lblZeta);
		txtQ1.setBounds(101, 151, 72, 20);
		add(txtQ1);
		txtQ2.setBounds(101, 179, 72, 20);
		add(txtQ2);
		txtZeta.setBounds(330, 179, 64, 20);
		add(txtZeta);
		chkZeta.setBounds(408, 176, 43, 24);
		add(chkZeta);
		lblGForm.setBounds(13, 122, 65, 22);
		add(lblGForm);
		cboGForm.setBounds(101, 122, 72, 22);
		add(cboGForm);
		radii = new Radii(true);
		radii.setBounds(215, 125, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		JTextField[] fields = { txtQ1, txtQ2, txtZeta };
		String[] descriptions = { "Q1", "Q2", "zeta" };
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		String lines = "tsuneyuki";
		if (cboGForm.getSelectedIndex() == 1)
			lines += " form2";
		lines += Back.newLine + pot.getAtomCombos() + Back.concatFields(fields)
				+ " " + radii.writeRadii();
		if (chkZeta.isSelected())
			lines += " 1";
		return lines + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		Tsuneyuki ts = new Tsuneyuki();
		ts.cboGForm.setSelectedIndex(this.cboGForm.getSelectedIndex());
		ts.chkZeta.setSelected(this.chkZeta.isSelected());
		ts.txtQ1.setText(this.txtQ1.getText());
		ts.txtQ2.setText(this.txtQ2.getText());
		ts.txtZeta.setText(this.txtZeta.getText());
		return super.clone(ts);
	}
}