package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import javagulp.model.SerialListener;

public class Constraints extends JPanel implements Serializable {

	private static final long serialVersionUID = 6713823649209001153L;


	private JRadioButton radConstantPressure = new JRadioButton("constant pressure");
	private JRadioButton radConstantVolume = new JRadioButton("constant volume");
	private JRadioButton radNone = new JRadioButton("none");

	SerialListener a = new SerialListener() {
		
		private static final long serialVersionUID = 535906773678123414L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(radConstantPressure.isSelected(),
					"conp");
			Back.getKeys().putOrRemoveKeyword(radConstantVolume.isSelected(),
					"conv");
			pnlUnfreeze.setVisible(radNone.isSelected());
		}
	};

	private Unfreeze pnlUnfreeze = new Unfreeze(radNone);

	public Constraints() {
		super();
		setLayout(null);

		pnlUnfreeze.setBounds(266, 0, 412, 147);
		add(pnlUnfreeze);

		JPanel pnlExternalField = new JPanel();
		pnlExternalField.setLayout(null);
		pnlExternalField.setBorder(new TitledBorder(null,
				"external field constraints",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlExternalField.setBounds(0, 0, 260, 148);
		add(pnlExternalField);
		radConstantPressure.setBounds(21, 26, 221, 23);
		pnlExternalField.add(radConstantPressure);
		radConstantPressure.addActionListener(a);
		radConstantPressure.setToolTipText("Perform Constant Pressure Calculation - cell to vary");
		radConstantVolume.setBounds(21, 55, 221, 23);
		pnlExternalField.add(radConstantVolume);
		radConstantVolume.addActionListener(a);
		radConstantVolume.setToolTipText("Perform Constant Volume Calculation - cell to vary");
		pnlExternalField.add(radNone);
		radNone.setBounds(21, 84, 221, 23);
		radNone.setSelected(true);
		radNone.addActionListener(a);
		ButtonGroup group = new ButtonGroup();
		group.add(radConstantPressure);
		group.add(radConstantVolume);
		group.add(radNone);

		ConstraintsOptions pnlOptions = new ConstraintsOptions();
		//JPanel pnlOptions = new JPanel();
		pnlOptions.setBounds(0, 154, 957, 137);
		add(pnlOptions);
	}

	public String writeUnfreeze() throws IncompleteOptionException {
		return pnlUnfreeze.writeUnfreeze();
	}
}