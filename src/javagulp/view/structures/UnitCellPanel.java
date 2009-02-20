package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class UnitCellPanel extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -6630264256468105811L;



	private JTabbedPane tabbedPane = new JTabbedPane();
	public ThreeDUnitCell threeDUnitCell = new ThreeDUnitCell();
	private TwoDUnitCell twoDUnitCell = new TwoDUnitCell();
	private OneDUnitCell oneDUnitCell = new OneDUnitCell();

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