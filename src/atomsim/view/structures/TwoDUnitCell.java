package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TwoDUnitCell extends JPanel implements Serializable {

	private static final long serialVersionUID = 440555811880387825L;

	private final JTabbedPane tabbedPane = new JTabbedPane();

	private final TwoDCellParameters twoDCellParameters = new TwoDCellParameters();
	private final TwoDCellVectors twoDCellVectors = new TwoDCellVectors();

	public TwoDUnitCell() {
		super();
		setLayout(new BorderLayout());

		add(tabbedPane);
		tabbedPane.add(twoDCellVectors, "vectors");
		tabbedPane.add(twoDCellParameters, "parameters");

	}

	public String write2DUnitCell() throws IncompleteOptionException {
		if (tabbedPane.getSelectedIndex() == 0)
			return twoDCellParameters.writeScell();
		else
			return twoDCellVectors.writeSvectors();
	}
}