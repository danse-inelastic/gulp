package javagulp.view.structures;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.RotationOperatorTableModel;
import javagulp.model.TranslationOperatorTableModel;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class UnitCellAndSymmetry extends JPanel implements Serializable {

	private static final long serialVersionUID = 5665294447343395650L;

	private RotationOperatorTableModel rModel = new RotationOperatorTableModel();
	private TranslationOperatorTableModel tModel = new TranslationOperatorTableModel();

	public UnitCellPanel unitCellPanel = new UnitCellPanel();


	public SpaceGroup spaceGroup = new SpaceGroup();

	private JComboBox cboSetSymmetry = new JComboBox(new String[] {
			"triclinic", "monoclinic", "orthorhombic", "tetragonal",
			"hexagonal", "rhombohedral", "cubic" });

	private JTextField txtSuperCellX = new JTextField("1");
	private JTextField txtSuperCellY = new JTextField("1");
	private JTextField txtSuperCellZ = new JTextField("1");

	public UnitCellAndSymmetry() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(889, 364));

		unitCellPanel.setBounds(0, 0, 316, 277);
		//unitCellPanel.setLayout(new BorderLayout());
		add(unitCellPanel);

		TitledPanel pnlSuperCell = new TitledPanel();
		pnlSuperCell.setBounds(0, 338, 316, 48);
		add(pnlSuperCell);
		pnlSuperCell.setTitle("extend unit cell to supercell");
		txtSuperCellX.setBounds(7, 19, 26, 20);
		pnlSuperCell.add(txtSuperCellX);
		txtSuperCellY.setBounds(36, 19, 26, 20);
		pnlSuperCell.add(txtSuperCellY);
		txtSuperCellZ.setBounds(65, 19, 26, 20);
		pnlSuperCell.add(txtSuperCellZ);

		final TitledPanel pnlInputSymmetry = new TitledPanel();
		pnlInputSymmetry.setTitle("input symmetry by space group or symmetry operators");
		pnlInputSymmetry.setBounds(322, 0, 682, 185);
		add(pnlInputSymmetry);
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(3, 20, 669, 155);
		pnlInputSymmetry.add(tabbedPane);
		tabbedPane.addTab("space group", spaceGroup);

		final JPanel pnlSymmetryOper = new JPanel();
		pnlSymmetryOper.setLayout(null);
		tabbedPane.addTab("symmetry operators", pnlSymmetryOper);
		final String[][] data = { { "", "", "" }, { "", "", "" },
				{ "", "", "" } };
		final JTable tableRotationOper = new JTable(data, new String[] { "",
				"", "" });
		pnlSymmetryOper.add(tableRotationOper);
		tableRotationOper.setModel(rModel);
		tableRotationOper.setBounds(50, 20, 225, 80);
		final JTable tableTranslationOper = new JTable(new String[][] { { "" },
				{ "" }, { "" } }, new String[] { "" });
		pnlSymmetryOper.add(tableTranslationOper);
		tableTranslationOper.setModel(tModel);
		tableTranslationOper.setBounds(285, 20, 225, 80);
		JLabel lblRotationOper = new JLabel("rotation operator");
		pnlSymmetryOper.add(lblRotationOper);
		lblRotationOper.setBounds(102, 103, 107, 15);
		JLabel lblTranslationOper = new JLabel("translation operator");
		pnlSymmetryOper.add(lblTranslationOper);
		lblTranslationOper.setBounds(327, 103, 125, 15);

		final TitledPanel pnlSetSymmetry = new TitledPanel();
		pnlSetSymmetry.setTitle("set symmetry cell type");
		pnlSetSymmetry.setBounds(0, 283, 316, 49);
		add(pnlSetSymmetry);
		cboSetSymmetry.setBounds(7, 19, 130, 25);
		pnlSetSymmetry.add(cboSetSymmetry);
		UnitCellOptions pnlOptions = new UnitCellOptions();
		pnlOptions.setBounds(322, 191, 682, 195);
		add(pnlOptions);
	}

	private String writeSupercell() throws IncompleteOptionException {
		String lines = "", x = txtSuperCellX.getText(), y = txtSuperCellY.getText(), z = txtSuperCellZ.getText();
		if ((!x.equals("") && !x.equals("1"))
				|| (!y.equals("") && !y.equals("1"))
				|| (!z.equals("") && !z.equals("1"))) {
			if (x.equals(""))
				throw new IncompleteOptionException("Please enter a value for supercell x");
			if (y.equals(""))
				throw new IncompleteOptionException("Please enter a value for supercell y");
			if (z.equals(""))
				throw new IncompleteOptionException("Please enter a value for supercell z");
			lines = "supercell " + x + " " + y + " " + z + Back.newLine;
		}
		return lines;
	}

	private String writeSymmetryCell() {
		String lines = "";
		if (cboSetSymmetry.getSelectedIndex() != 0)
			lines = "symmetry_cell " + cboSetSymmetry.getSelectedItem() + Back.newLine;
		return lines;
	}

	public String writeUnitCellAndSymmetry() throws IncompleteOptionException {
		return writeSymmetryCell() + rModel.writeSymOpOption(tModel)
				+ unitCellPanel.writeUnitCell() + writeSupercell();
	}
}