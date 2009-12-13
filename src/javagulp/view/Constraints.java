package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.constraints.ConstraintsOptions;
import javagulp.view.constraints.Unfreeze;

import javax.swing.JPanel;

public class Constraints extends JPanel implements Serializable {

	private static final long serialVersionUID = 6713823649209001153L;






	private final Unfreeze pnlUnfreeze = new Unfreeze(null);


	public Constraints() {
		super();
		setLayout(null);

		pnlUnfreeze.setBounds(0, 0, 412, 147);
		add(pnlUnfreeze);



		final ConstraintsOptions pnlOptions = new ConstraintsOptions();
		//JPanel pnlOptions = new JPanel();
		pnlOptions.setBounds(0, 154, 957, 188);
		add(pnlOptions);
	}

	public String writeUnfreeze() throws IncompleteOptionException {
		return pnlUnfreeze.writeUnfreeze();
	}
}