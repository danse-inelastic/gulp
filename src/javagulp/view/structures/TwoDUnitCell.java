package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class TwoDUnitCell extends JPanel implements Serializable {

	private static final long serialVersionUID = 440555811880387825L;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private TwoDCellParameters twoDCellParameters = new TwoDCellParameters();
	private TwoDCellVectors twoDCellVectors = new TwoDCellVectors();

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