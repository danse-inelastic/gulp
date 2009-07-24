package javagulp.view.fit;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JPanel;

public abstract class AbstractFit extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7553135746194739969L;
	public String gulpFileLines = "";

	public AbstractFit() {
		super();
		setLayout(null);
	}

	public abstract String writeFitPanel() throws IncompleteOptionException;
}
