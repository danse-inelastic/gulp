package javagulp.view.potential;

import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PPP extends JPanel  implements Serializable {
	//Abbreviation for PotentialParameterPanel
	private static final long serialVersionUID = 325076769255213973L;
	
	public JLabel lbl = new JLabel();
	public JTextField txt = new JTextField();
	public JCheckBox chk = new JCheckBox("fit");
	
	/**
	 * These values represent the minimum and maximum values that are realistic.
	 */
	public double min = -10, max = 10;
	
	public PPP (String label, int w1, int w2, int w3) {
		super();
		setLayout(null);
		this.setPreferredSize(new Dimension(225, 25));
		
		add(lbl);
		add(txt);
		add(chk);
		
		lbl.setBounds(0, 0, w1, 25);
		txt.setBounds(w1+5, 0, w2, 20);
		chk.setBounds(w1+w2+10, 0, w3, 20);
		
		lbl.setText(label);
	}
	
	public PPP(String label) {
		this(label, 75, 100, 40);
	}
}
