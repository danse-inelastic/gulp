package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class BOCharge extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7623048176589167154L;

	private G g = new G();

	private JComboBox cboFunction = new JComboBox(new String[] {
			"cosine taper function", "sine taper function" });

	private JTextField txtDelta = new JTextField();

	private JLabel lblDelta = new JLabel(g.html(g.delta + "<sub>Q</sub> (au)"));
	private JLabel lblFunction = new JLabel("H(r)");
	private JLabel lblEquations = new JLabel(g.html("atom 1: Q = sum [ - "
			+ g.delta + "<sub>Q</sub> H(r) ]<br>atom 2: Q = sum [ + " + g.delta
			+ "<sub>Q</sub> H(r) ]"));

	public BOCharge() {
		super(2);
		setBorder(new TitledBorder(null, "Bond Order Charge",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		this.setPreferredSize(new java.awt.Dimension(427, 175));

		lblDelta.setBounds(35, 97, 65, 25);
		add(lblDelta);
		txtDelta.setBounds(104, 97, 72, 20);
		add(txtDelta);
		lblEquations.setBounds(33, 33, 263, 58);
		add(lblEquations);
		lblFunction.setBounds(35, 140, 25, 20);
		add(lblFunction);
		cboFunction.setBounds(65, 140, 175, 21);
		add(cboFunction);
		radii = new Radii(true);
		radii.setBounds(240, 98, radii.getWidth(), radii.getHeight());
		add(radii);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		JTextField[] fields = { txtDelta };
		String[] descriptions = { "delta" };
		CreateLibrary pot = Back.getPanel().getPotential().createLibrary;
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		String lines = "bocharge";
		if (cboFunction.getSelectedIndex() == 1)
			lines += " staper";
		return lines + Back.newLine + pot.getAtomCombos()
				+ Back.concatFields(fields) + " " + radii.writeRadii() + Back.newLine;
	}
	
	@Override
	public PotentialPanel clone() {
		BOCharge bo = new BOCharge();
		bo.txtDelta.setText(this.txtDelta.getText());
		return super.clone(bo);
	}
}
