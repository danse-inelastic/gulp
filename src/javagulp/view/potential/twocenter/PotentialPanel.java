package javagulp.view.potential.twocenter;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.potential.PPP;
import javagulp.view.potential.Radii;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public abstract class PotentialPanel extends JPanel implements Cloneable {

	public int potentialNumber;
	public String[] atom;
	public boolean[] selected;
	public boolean[] enabled;
	public boolean enableScale14 = false;
	public PPP[] params;
	public Radii radii;
	public String scale14 = "1";

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

	public PotentialPanel(int number) {
		super();
		setBorder(border);
		setLayout(null);
		potentialNumber = number;
		atom = new String[number];
		if (number == 2) {
			selected = new boolean[] { false, false, false, false, false, false };
			enabled = new boolean[] { false, false, false, false, false, false };
		} else if (number == 3 || number == 4) {
			selected = new boolean[] { false, false, false };
			enabled = new boolean[] { true, true, true };
		}
	}

	public void setTitle(final String title) {
		border.setTitle(title);
	}

	abstract public String writePotential() throws IncompleteOptionException, InvalidOptionException;
	
	@Override
	public abstract PotentialPanel clone();
	
	public PotentialPanel clone(PotentialPanel b) {
		if (this.params != null && b.params != null
				&& this.params.length == b.params.length) {
			for (int i=0; i < this.params.length; i++) {
				b.params[i].txt.setText(this.params[i].txt.getText());
				b.params[i].chk.setSelected(this.params[i].chk.isSelected());
			}
		}
		if (this.radii != null) {
			this.radii.clone(b.radii);
		}
		for (int i=0; i < this.atom.length; i++)
			b.atom[i] = this.atom[i];
		for (int i=0; i < this.selected.length; i++)
			b.selected[i] = this.selected[i];
		for (int i=0; i < this.enabled.length; i++)
			b.enabled[i] = this.enabled[i];
		return b;
	}

	/**
	 * This method should return the number of parameters that are currently
	 * being fit, not the total number of parameters of the potential.
	 * 
	 * @return
	 */
	public int currentParameterCount() {
		int count = 0;
		if (params != null) {
			for (PPP p: params)
				if (p.chk.isSelected())
					count++;
		}
		return count;
	}

	/**
	 * This method will update the ith current parameter with a new value.
	 * @param i
	 */
	public void setParameter(int i, String value) {
		int count = 0;
		if (params != null) {
			for (PPP p: params)
				if (p.chk.isSelected()) {
					count++;
					if (i == count)
						p.txt.setText(value);
				}
		}

	}

	public void setRadiiEnabled(boolean flag) {
		if (radii != null)
			radii.setRadiiEnabled(flag);
	}
}
