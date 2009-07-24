package javagulp.view.structures;

import java.awt.BorderLayout;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class OneDUnitCell extends JPanel {

	private static final long serialVersionUID = -2905642930994016008L;

	private final JTabbedPane tabbedPane = new JTabbedPane();

	private final OneDCellParameters oneDCellParameters = new OneDCellParameters();
	private final OneDCellVectors oneDCellVectors = new OneDCellVectors();

	public OneDUnitCell() {
		super();
		setLayout(new BorderLayout());

		add(tabbedPane);
		tabbedPane.add(oneDCellVectors, "vectors");
		tabbedPane.add(oneDCellParameters, "parameters");
		//oneDCellParameters.setLayout(null);

	}

	public String writeOneDUnitCell() throws IncompleteOptionException {
		if (tabbedPane.getSelectedIndex() == 0)
			return oneDCellParameters.writePcell();
		else
			return oneDCellVectors.writePvectors();
	}
}