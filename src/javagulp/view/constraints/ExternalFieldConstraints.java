package javagulp.view.constraints;

import java.io.Serializable;

import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;



public class ExternalFieldConstraints extends TitledPanel implements Serializable { 
	
	public JRadioButton radConstantPressure = new JRadioButton("constant pressure");
	public JRadioButton radConstantVolume = new JRadioButton("constant volume");
	public JRadioButton radNone = new JRadioButton("none");
	
		public ExternalFieldConstraints() {
			setTitle("external field constraints");
			radConstantPressure.setBounds(10, 82, 221, 23);
			add(radConstantPressure);
			
			radConstantPressure.setToolTipText("Perform Constant Pressure Calculation - cell to vary");
			radConstantVolume.setBounds(10, 53, 221, 23);
			add(radConstantVolume);
			
			radConstantVolume.setToolTipText("Perform Constant Volume Calculation - cell to vary");
			add(radNone);
			radNone.setBounds(10, 24, 221, 23);
			radNone.setSelected(true);
			
			ButtonGroup group = new ButtonGroup();
			group.add(radConstantPressure);
			group.add(radConstantVolume);
			group.add(radNone);
		}
}