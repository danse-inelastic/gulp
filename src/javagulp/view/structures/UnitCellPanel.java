package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.TitledPanel;

import javax.swing.JTabbedPane;

public class UnitCellPanel extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -6630264256468105811L;



	private final JTabbedPane tabbedPane = new JTabbedPane();
	public ThreeDUnitCell threeDUnitCell = new ThreeDUnitCell();
	private final TwoDUnitCell twoDUnitCell = new TwoDUnitCell();
	private final OneDUnitCell oneDUnitCell = new OneDUnitCell();

	public UnitCellPanel() {
		super();
		setLayout(new BorderLayout());

		add(tabbedPane);
		tabbedPane.add(threeDUnitCell, "3d");
		tabbedPane.add(twoDUnitCell, "2d");
		//twoDUnitCell.setLayout(new BorderLayout());
		tabbedPane.add(oneDUnitCell, "1d");
		//oneDUnitCell.setLayout(new BorderLayout());
	}



	public String writeUnitCell() throws IncompleteOptionException {
		return threeDUnitCell.write3DUnitCell()
		+ twoDUnitCell.write2DUnitCell()
		+ oneDUnitCell.writeOneDUnitCell();
	}
}