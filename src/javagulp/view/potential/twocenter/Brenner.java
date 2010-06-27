package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PotentialPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class Brenner extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -4587031590876298044L;

	private final JLabel lbl = new JLabel("Applicable to C, H, and O.  No options necessary.");

	private final JCheckBox chkOne = new JCheckBox("Use brenner potential for C, H, and Si instead.");

	// TODO change writePotential so it checks for ANY non-supported atoms,
	// not just the first structure
	public Brenner() {
		super(2);

		setBorder(new TitledBorder(null, "brenner",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));

		lbl.setBounds(20, 37, 357, 16);
		add(lbl);
		chkOne.setBounds(20, 60, 357, 16);
		add(chkOne);
	}

	@Override
	public String writePotential() throws InvalidOptionException {
		//final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
//		String one = (String) pot.pnlAtom.cboAtom[0].getSelectedItem();
//		String two = (String) pot.pnlAtom.cboAtom[1].getSelectedItem();
//		if (one.endsWith(" core"))
//			one = one.substring(0, one.length() - 6);
//		if (two.endsWith(" core"))
//			two = two.substring(0, two.length() - 6);
//		one = one.toUpperCase();
//		two = two.toUpperCase();
//		String[] s = null;
		String brenner = "brenner";
		if (chkOne.isSelected()) {
			brenner += " 1";
//			s = new String[] {"C", "H", "Si"};
		} //else
//			s = new String[] {"C", "H", "O"};
//		if ((!one.equals(s[0]) && !one.equals(s[1]) && !one.equals(s[2]))
//				|| (!two.equals(s[0]) && !two.equals(s[1]) && !two.equals(s[2])))
//			throw new InvalidOptionException("Atoms must be of the species "
//					+ s[0] + ", " + s[1] + ", or " + s[2]);
		return brenner + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		final Brenner b = new Brenner();
		b.chkOne.setSelected(this.chkOne.isSelected());
		return super.clone(b);
	}
}