package javagulp.view.potential;

import java.awt.Dimension;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.misc.G;

public class Radii extends JPanel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public JTextField[] txtrmin;
	public JTextField[] txtrmax;
	
	public JLabel[] lblrmin;
	public JLabel[] lblrmax;
	
	public Radii() {
		this(false, new String[] {""});
	}
	
	public Radii(boolean rmin) {
		this(rmin, new String[] {""});
	}
	
	public Radii(String[] lbls) {
		this(false, lbls);
	}
	
	public Radii(boolean usermin, String[] lbls) {
		super();
		setLayout(null);
		
		if (usermin) {
			lblrmin = new JLabel[lbls.length];
			txtrmin = new JTextField[lbls.length];
		}
		lblrmax = new JLabel[lbls.length];
		txtrmax = new JTextField[lbls.length];
		
		G g = new G();
		int x = 0, y = 0, lblwidth = 45, txtwidth = 75, height = 20;
		for (int i=0; i < lbls.length; i++) {
			if (usermin) {
				lblrmin[i] = new JLabel(g.html("r" + g.sub(lbls[i]) + "min" ));
				lblrmin[i].setBounds(x, y, lblwidth, height);
				add(lblrmin[i]);
				x += lblwidth + 5;
				txtrmin[i] = new JTextField();
				txtrmin[i].setBackground(Back.grey);
				txtrmin[i].setBounds(x, y, txtwidth, height);
				add(txtrmin[i]);
				x += txtwidth + 5;
			}
			lblrmax[i] = new JLabel(g.html("r" + g.sub(lbls[i]) + "max" ));
			lblrmax[i].setBounds(x, y, lblwidth, height);
			add(lblrmax[i]);
			x += lblwidth + 5;
			txtrmax[i] = new JTextField();
			txtrmax[i].setBounds(x, y, txtwidth, height);
			add(txtrmax[i]);
			x = 0;
			y += height + 5;
		}
		int w = lblwidth + txtwidth + 5;
		if (usermin)
			w = w * 2 + 5;
		int h = height * lbls.length + 5 * (lbls.length - 1);
		this.setSize(new Dimension(w, h));
	}
	
	public String writeRadii() throws IncompleteOptionException {
		String lines = "";
		boolean usermin = false;
		if (txtrmin != null) {
			for (int i = 0; i < txtrmin.length; i++) {
				if (!txtrmin[i].getText().equals(""))
					usermin = true;
			}
		}
		for (int i = 0; i < txtrmax.length; i++) {
			if (usermin) {
				if (txtrmin[i].getText().equals(""))
					throw new IncompleteOptionException("Please enter values for all rmins");
				Double.parseDouble(txtrmin[i].getText());
				lines += txtrmin[i].getText() + " ";
			}
			if (txtrmax[i].getText().equals(""))
				throw new IncompleteOptionException("Please enter values for all rmaxs");
			Double.parseDouble(txtrmax[i].getText());
			lines += txtrmax[i].getText() + " ";
		}
		return lines;
	}
	
	public void setRadiiEnabled(boolean flag) {
		if (txtrmin != null) {
			for (JTextField field: txtrmin)
				field.setEnabled(flag);
		}
		for (JTextField field: txtrmax)
			field.setEnabled(flag);
	}
	
	public void clone(Radii b) {
		for (int i=0; i < this.txtrmin.length; i++)
			b.txtrmin[i].setText(this.txtrmin[i].getText());
		for (int i=0; i < this.txtrmax.length; i++)
			b.txtrmax[i].setText(this.txtrmax[i].getText());
	}
}
