package javagulp.view;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JCheckBox;

	class ConstraintsOptions extends TitledPanel {

		private static final long serialVersionUID = 740089487749329041L;

		private JCheckBox chkDoNotSet = new JCheckBox("do not set any flags for fitting");
		private JCheckBox chkOptimizeCellRadii = new JCheckBox("only optimize shell radii");
		private JCheckBox chkHtmlDoNotFreeze = new JCheckBox("<html>do not freeze out atoms with no degrees of freedom from first and and second derivative calculations during optimization</html>");
		private JCheckBox chkExcludeShellRadii = new JCheckBox("exclude shell radii from the fitting/optimization");
		private JCheckBox chkOptimizeUnitCell = new JCheckBox("optimize/fit unit cell coordinates but leave internal atomic coordinates fixed");

		private KeywordListener keyDoNotSet = new KeywordListener(chkDoNotSet,
				"noflags");
		private KeywordListener keyOptimizeCellRadii = new KeywordListener(chkOptimizeCellRadii, "breathe");
		private KeywordListener keyHtmlDoNotFreeze = new KeywordListener(chkHtmlDoNotFreeze, "noexclude");
		private KeywordListener keyExcludeShellRadii = new KeywordListener(chkExcludeShellRadii, "nobreathe");
		private KeywordListener keyOptimizeUnitCell = new KeywordListener(chkOptimizeUnitCell, "cellonly");

		ConstraintsOptions() {
			super();
			setTitle("options");

			chkDoNotSet.setBounds(7, 19, 537, 25);
			add(chkDoNotSet);
			chkDoNotSet.addActionListener(keyDoNotSet);
			chkOptimizeCellRadii.setBounds(7, 40, 537, 25);
			add(chkOptimizeCellRadii);
			chkOptimizeCellRadii.addActionListener(keyOptimizeCellRadii);
			chkHtmlDoNotFreeze.setBounds(7, 62, 945, 25);
			add(chkHtmlDoNotFreeze);
			chkHtmlDoNotFreeze.addActionListener(keyHtmlDoNotFreeze);
			chkExcludeShellRadii.setBounds(8, 84, 536, 25);
			add(chkExcludeShellRadii);
			chkExcludeShellRadii.addActionListener(keyExcludeShellRadii);
			chkOptimizeUnitCell.setBounds(8, 108, 846, 25);
			add(chkOptimizeUnitCell);
			chkOptimizeUnitCell.addActionListener(keyOptimizeUnitCell);
		}
	}

