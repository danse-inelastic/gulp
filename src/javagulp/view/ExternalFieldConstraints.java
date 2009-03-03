package javagulp.view;

import java.io.Serializable;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



public class ExternalFieldConstraints extends TitledPanel implements Serializable { 
	
	JRadioButton radConstantPressure = new JRadioButton("constant pressure");
	JRadioButton radConstantVolume = new JRadioButton("constant volume");
	JRadioButton radNone = new JRadioButton("none");
	
		public ExternalFieldConstraints() {
			setTitle("external field constraints");
			radConstantPressure.setBounds(21, 26, 221, 23);
			add(radConstantPressure);
			
			radConstantPressure.setToolTipText("Perform Constant Pressure Calculation - cell to vary");
			radConstantVolume.setBounds(21, 55, 221, 23);
			add(radConstantVolume);
			
			radConstantVolume.setToolTipText("Perform Constant Volume Calculation - cell to vary");
			add(radNone);
			radNone.setBounds(21, 84, 221, 23);
			radNone.setSelected(true);
			
			ButtonGroup group = new ButtonGroup();
			group.add(radConstantPressure);
			group.add(radConstantVolume);
			group.add(radNone);
		}
}