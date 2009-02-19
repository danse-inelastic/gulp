package javagulp.view.structures;

import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;

	 public class UnitCellOptions extends TitledPanel {
		
		private static final long serialVersionUID = 206634642308276667L;
		
		private JCheckBox chkDoNotBring = new JCheckBox("do not bring atomic coordinates back into the unit cell");
		private JCheckBox chkOutputRhombohedral = new JCheckBox("output rhombohedral structures in hexagonal form");
		private JCheckBox chkPrintTableComparing = new JCheckBox("print table comparing initial and structures");
		private JCheckBox chkOutputRototranslational = new JCheckBox("output rototranslational bulk symmetry operators");
		private JCheckBox chkTurnOffSymmetrySecond = new JCheckBox("turn off symmetry for second derivatives in bulk and defect calculations");
		private JCheckBox chkTurnOffSymmetryUnitCell = new JCheckBox("turn off symmetry after generating unit cell; non primitive cells still become primitive");
		private JCheckBox chkKeepTheFull = new JCheckBox("keep the full unit cell; do not generate the primitive unit cell");

		private KeywordListener keyDoNotBring = new KeywordListener(chkDoNotBring, "nomodcoord");
		private KeywordListener keyOutputRhombohedral = new KeywordListener(chkOutputRhombohedral, "hexagonal");
		private KeywordListener keyPrintTableComparing = new KeywordListener(chkPrintTableComparing, "compare");
		private KeywordListener keyOutputRototranslational = new KeywordListener(chkOutputRototranslational, "operators");
		private KeywordListener keyTurnOffSymmetryUnitCell = new KeywordListener(chkTurnOffSymmetryUnitCell, "nosymmetry");
		private KeywordListener keyKeepTheFull = new KeywordListener(chkKeepTheFull, "full");

		public UnitCellOptions() {
			super();
			setTitle("options");

			chkDoNotBring.setBounds(10, 90, 694, 25);
			add(chkDoNotBring);
			chkDoNotBring.addActionListener(keyDoNotBring);
			chkOutputRhombohedral.setBounds(10, 165, 694, 25);
			add(chkOutputRhombohedral);
			chkOutputRhombohedral.addActionListener(keyOutputRhombohedral);
			chkPrintTableComparing.setBounds(10, 115, 694, 25);
			add(chkPrintTableComparing);
			chkPrintTableComparing.addActionListener(keyPrintTableComparing);
			chkOutputRototranslational.setBounds(10, 140, 694, 25);
			add(chkOutputRototranslational);
			chkOutputRototranslational.addActionListener(keyOutputRototranslational);
			chkTurnOffSymmetrySecond.setBounds(10, 40, 694, 25);
			add(chkTurnOffSymmetrySecond);
			chkTurnOffSymmetryUnitCell.setBounds(10, 15, 694, 25);
			add(chkTurnOffSymmetryUnitCell);
			chkTurnOffSymmetryUnitCell.addActionListener(keyTurnOffSymmetryUnitCell);
			chkKeepTheFull.setBounds(10, 65, 694, 25);
			add(chkKeepTheFull);
			chkKeepTheFull.addActionListener(keyKeepTheFull);
		}
	}
