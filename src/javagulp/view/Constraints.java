package javagulp.view;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JPanel;
import javagulp.model.SerialListener;
import javagulp.view.constraints.ConstraintsOptions;
import javagulp.view.constraints.ExternalFieldConstraints;
import javagulp.view.constraints.Unfreeze;

public class Constraints extends JPanel implements Serializable {

	private static final long serialVersionUID = 6713823649209001153L;
	private ExternalFieldConstraints pnlExternalField = new ExternalFieldConstraints();



	SerialListener a = new SerialListener() {
		
		private static final long serialVersionUID = 535906773678123414L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(pnlExternalField.radConstantPressure.isSelected(),
					"conp");
			Back.getKeys().putOrRemoveKeyword(pnlExternalField.radConstantVolume.isSelected(),
					"conv");
			pnlUnfreeze.setVisible(pnlExternalField.radNone.isSelected());
		}
	};

	private Unfreeze pnlUnfreeze = new Unfreeze(pnlExternalField.radNone);




	

	public Constraints() {
		super();
		setLayout(null);

		pnlUnfreeze.setBounds(266, 0, 412, 147);
		add(pnlUnfreeze);
		pnlExternalField.radConstantPressure.addActionListener(a);
		pnlExternalField.radConstantVolume.addActionListener(a);
		pnlExternalField.radNone.addActionListener(a);
		pnlExternalField.setBounds(0, 0, 260, 148);
		add(pnlExternalField);
		

		ConstraintsOptions pnlOptions = new ConstraintsOptions();
		//JPanel pnlOptions = new JPanel();
		pnlOptions.setBounds(0, 154, 957, 188);
		add(pnlOptions);
	}

	public String writeUnfreeze() throws IncompleteOptionException {
		return pnlUnfreeze.writeUnfreeze();
	}
}