package javagulp.view.fit;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JPanel;

public abstract class AbstractFit extends JPanel {

	public AbstractFit() {
		super();
		setLayout(null);
	}

	public abstract String writeFitPanel() throws IncompleteOptionException;
}
