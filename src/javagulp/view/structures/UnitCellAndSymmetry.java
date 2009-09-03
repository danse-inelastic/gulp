package javagulp.view.structures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.RotationOperatorTableModel;
import javagulp.model.TranslationOperatorTableModel;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class UnitCellAndSymmetry extends JPanel implements Serializable {

	private static final long serialVersionUID = 5665294447343395650L;

	//	private final RotationOperatorTableModel rModel = new RotationOperatorTableModel();
	//	private final TranslationOperatorTableModel tModel = new TranslationOperatorTableModel();

	private final List<RotationOperatorTableModel> rotations = new ArrayList<RotationOperatorTableModel>();
	private final List<TranslationOperatorTableModel> translations = new ArrayList<TranslationOperatorTableModel>();

	public UnitCellPanel unitCellPanel = new UnitCellPanel();


	public SpaceGroup spaceGroup = new SpaceGroup();

	private final JComboBox cboSetSymmetry = new JComboBox(new String[] {
			"", "triclinic", "monoclinic", "orthorhombic", "tetragonal",
			"hexagonal", "rhombohedral", "cubic" });

	private final JTextField txtSuperCellX = new JTextField("1");
	private final JTextField txtSuperCellY = new JTextField("1");
	private final JTextField txtSuperCellZ = new JTextField("1");

	private final JTable tableRotationOper;

	private final JTable tableTranslationOper;

	public UnitCellAndSymmetry() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(889, 364));

		unitCellPanel.setBounds(0, 0, 316, 277);
		//unitCellPanel.setLayout(new BorderLayout());
		add(unitCellPanel);

		final TitledPanel pnlSuperCell = new TitledPanel();
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
		tableRotationOper = new JTable(data, new String[] { "",
				"", "" });
		pnlSymmetryOper.add(tableRotationOper);
		tableRotationOper.setBounds(20, 20, 225, 80);
		tableTranslationOper = new JTable(new String[][] { { "" },
				{ "" }, { "" } }, new String[] { "" });
		pnlSymmetryOper.add(tableTranslationOper);
		resetOperators();

		tableTranslationOper.setBounds(285, 20, 75, 80);
		final JLabel lblRotationOper = new JLabel("rotation operator");
		pnlSymmetryOper.add(lblRotationOper);
		lblRotationOper.setBounds(66, 106, 107, 15);
		final JLabel lblTranslationOper = new JLabel("translation operator");
		pnlSymmetryOper.add(lblTranslationOper);
		lblTranslationOper.setBounds(260, 106, 125, 15);

		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				resetOperators();
			}
		});
		addButton.setText("add");
		addButton.setBounds(403, 15, 86, 25);
		pnlSymmetryOper.add(addButton);

		final TitledPanel pnlSetSymmetry = new TitledPanel();
		pnlSetSymmetry.setTitle("set symmetry cell type");
		pnlSetSymmetry.setBounds(0, 283, 316, 49);
		add(pnlSetSymmetry);
		cboSetSymmetry.setBounds(7, 19, 130, 25);
		pnlSetSymmetry.add(cboSetSymmetry);
		final UnitCellOptions pnlOptions = new UnitCellOptions();
		pnlOptions.setBounds(322, 191, 682, 195);
		add(pnlOptions);
	}

	private void resetOperators(){
		rotations.add(new RotationOperatorTableModel());
		translations.add(new TranslationOperatorTableModel());
		tableRotationOper.setModel(rotations.get(rotations.size()-1));
		tableTranslationOper.setModel(translations.get(translations.size()-1));
	}

	private String writeSupercell() throws IncompleteOptionException {
		String lines = "";
		final String x = txtSuperCellX.getText(), y = txtSuperCellY.getText(), z = txtSuperCellZ.getText();
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

	private String writeSymmetryOperators() {
		String lines = "";
		for(int i=0;i<rotations.size();i++){
			lines += rotations.get(i).writeSymOpOption(translations.get(i));
		}
		return lines;
	}

	public String writeUnitCellAndSymmetry() throws IncompleteOptionException {
		return writeSymmetryCell() + writeSymmetryOperators()
		+ unitCellPanel.writeUnitCell() + writeSupercell();
	}
}