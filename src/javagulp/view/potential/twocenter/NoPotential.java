package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;
import javagulp.view.potential.PotentialPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NoPotential extends PotentialPanel implements Serializable {
	public NoPotential() {
		super(1);
	}
	
	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "";
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		return new NoPotential();
	}
}