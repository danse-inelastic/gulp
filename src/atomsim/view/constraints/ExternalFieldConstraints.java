package javagulp.view.constraints;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;



public class ExternalFieldConstraints extends TitledPanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865926603882174677L;
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

		radConstantPressure.addActionListener(a);
		radConstantVolume.addActionListener(a);
		radNone.addActionListener(a);

		final ButtonGroup group = new ButtonGroup();
		group.add(radConstantPressure);
		group.add(radConstantVolume);
		group.add(radNone);
	}

	SerialListener a = new SerialListener() {

		private static final long serialVersionUID = 535906773678123414L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(radConstantPressure.isSelected(),
			"conp");
			Back.getKeys().putOrRemoveKeyword(radConstantVolume.isSelected(),
			"conv");
		}
	};

}