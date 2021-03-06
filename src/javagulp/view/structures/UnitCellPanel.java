package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.Material;
import javagulp.view.Back;
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
//		tabbedPane.add(twoDUnitCell, "2d");
//		tabbedPane.add(oneDUnitCell, "1d");
		//set the lattice property if it exists
		try{
			String latticeRaw = Back.atomSimProps.getProperty("lattice");
			threeDUnitCell.setParameters(latticeRaw.split(" +"));
		}catch(Exception e){
		}
	}

	public String writeUnitCell() throws IncompleteOptionException {
		return threeDUnitCell.write3DUnitCell()
		+ twoDUnitCell.write2DUnitCell()
		+ oneDUnitCell.writeOneDUnitCell();
	}
}