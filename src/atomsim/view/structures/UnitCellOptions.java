package javagulp.view.structures;

import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;

public class UnitCellOptions extends TitledPanel {

	private static final long serialVersionUID = 206634642308276667L;

	private final JCheckBox chkDoNotBring = new JCheckBox("do not bring atomic coordinates back into the unit cell");
	private final JCheckBox chkOutputRhombohedral = new JCheckBox("output rhombohedral structures in hexagonal form");
	private final JCheckBox chkPrintTableComparing = new JCheckBox("print table comparing initial and structures");
	private final JCheckBox chkOutputRototranslational = new JCheckBox("output rototranslational bulk symmetry operators");
	private final JCheckBox chkTurnOffSymmetrySecond = new JCheckBox("turn off symmetry for second derivatives in bulk and defect calculations");
	private final JCheckBox chkTurnOffSymmetryUnitCell = new JCheckBox("turn off symmetry after generating unit cell; non primitive cells still become primitive");
	private final JCheckBox chkKeepTheFull = new JCheckBox("keep the full unit cell; do not generate the primitive unit cell");

	private final KeywordListener keyDoNotBring = new KeywordListener(chkDoNotBring, "nomodcoord");
	private final KeywordListener keyOutputRhombohedral = new KeywordListener(chkOutputRhombohedral, "hexagonal");
	private final KeywordListener keyPrintTableComparing = new KeywordListener(chkPrintTableComparing, "compare");
	private final KeywordListener keyOutputRototranslational = new KeywordListener(chkOutputRototranslational, "operators");
	private final KeywordListener keyTurnOffSymmetryUnitCell = new KeywordListener(chkTurnOffSymmetryUnitCell, "nosymmetry");
	private final KeywordListener keyKeepTheFull = new KeywordListener(chkKeepTheFull, "full");

	public UnitCellOptions() {
		super();
		setTitle("options");

		chkDoNotBring.setBounds(10, 90, 655, 25);
		add(chkDoNotBring);
		chkDoNotBring.addActionListener(keyDoNotBring);
		chkOutputRhombohedral.setBounds(10, 165, 655, 25);
		add(chkOutputRhombohedral);
		chkOutputRhombohedral.addActionListener(keyOutputRhombohedral);
		chkPrintTableComparing.setBounds(10, 115, 655, 25);
		add(chkPrintTableComparing);
		chkPrintTableComparing.addActionListener(keyPrintTableComparing);
		chkOutputRototranslational.setBounds(10, 140, 655, 25);
		add(chkOutputRototranslational);
		chkOutputRototranslational.addActionListener(keyOutputRototranslational);
		chkTurnOffSymmetrySecond.setBounds(10, 40, 655, 25);
		add(chkTurnOffSymmetrySecond);
		chkTurnOffSymmetryUnitCell.setBounds(10, 15, 655, 25);
		add(chkTurnOffSymmetryUnitCell);
		chkTurnOffSymmetryUnitCell.addActionListener(keyTurnOffSymmetryUnitCell);
		chkKeepTheFull.setBounds(10, 65, 655, 25);
		add(chkKeepTheFull);
		chkKeepTheFull.addActionListener(keyKeepTheFull);
	}
}
