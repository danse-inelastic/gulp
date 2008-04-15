package javagulp.view.top;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utility.misc.G;
import utility.misc.SerialListener;

public class Constraints extends JPanel implements Serializable {

	private static final long serialVersionUID = 6713823649209001153L;

	private class Unfreeze extends TitledPanel {

		private static final long serialVersionUID = -3107856008613176983L;

		private G g = new G();

		private JTextField txtRadius = new JTextField();
		private JTextField txtAtom = new JTextField();
		private JTextField txtX = new JTextField();
		private JTextField txtY = new JTextField();
		private JTextField txtZ = new JTextField();

		private JLabel lblAngRadiusOf = new JLabel(g.html("&Aring; radius of"));
		private JLabel lblAllowAtomsWithin = new JLabel("optimize atoms within");

		private JRadioButton radXYZ = new JRadioButton("coordinates");
		private JRadioButton radAtom = new JRadioButton("atom no.");
		private ButtonGroup group = new ButtonGroup();

		public Unfreeze() {
			super();
			setTitle("allow atoms around cluster to move");

			lblAllowAtomsWithin.setBounds(10, 20, 155, 30);
			add(lblAllowAtomsWithin);
			txtRadius.setBounds(160, 25, 50, 20);
			add(txtRadius);
			lblAngRadiusOf.setBounds(218, 20, 91, 30);
			add(lblAngRadiusOf);
			radXYZ.setBounds(10, 50, 110, 20);
			add(radXYZ);
			radAtom.setBounds(10, 75, 110, 20);
			add(radAtom);
			group.add(radXYZ);
			group.add(radAtom);
			txtAtom.setBounds(135, 75, 30, 20);
			add(txtAtom);
			txtX.setBounds(135, 50, 30, 20);
			add(txtX);
			txtY.setBounds(170, 50, 30, 20);
			add(txtY);
			txtZ.setBounds(205, 50, 30, 20);
			add(txtZ);
			radXYZ.setSelected(true);
		}

		private String writeUnfreeze() throws IncompleteOptionException {
			// From the documentation:
			// This option cannot be used with conp or conv for obvious reasons!
			String lines = "";
			if (radNone.isSelected()) {
				if (!txtRadius.getText().equals("")) {
					Double.parseDouble(txtRadius.getText());
					String x = txtX.getText(), y = txtY.getText(), z = txtZ.getText();
					if (radXYZ.isSelected()) {
						if (!x.equals("") && !y.equals("") && !z.equals("")) {
							Double.parseDouble(x);
							Double.parseDouble(y);
							Double.parseDouble(z);

							lines = "unfreeze " + x + " " + y + " " + z + " "
									+ txtRadius.getText() + Back.newLine;
						} else {
							throw new IncompleteOptionException("Please enter coordinates around which to optimize.");
						}
					} else {
						if (!txtAtom.getText().equals("")) {
							Double.parseDouble(txtAtom.getText());

							lines = "unfreeze " + txtAtom.getText() + " "
									+ txtRadius.getText() + Back.newLine;
						} else {
							throw new IncompleteOptionException("Please enter coordinates around which to optimize.");
						}
					}
				}
			}
			return lines;
		}
	}

	private JRadioButton radConstantPressure = new JRadioButton("constant pressure");
	private JRadioButton radConstantVolume = new JRadioButton("constant volume");
	private JRadioButton radNone = new JRadioButton("none");

	SerialListener a = new SerialListener() {
		
		private static final long serialVersionUID = 535906773678123414L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(radConstantPressure.isSelected(),
					"conp");
			Back.getKeys().putOrRemoveKeyword(radConstantVolume.isSelected(),
					"conv");
			pnlUnfreeze.setVisible(radNone.isSelected());
		}
	};

	private Unfreeze pnlUnfreeze = new Unfreeze();

	public Constraints() {
		super();
		setLayout(null);

		pnlUnfreeze.setBounds(189, 0, 322, 147);
		add(pnlUnfreeze);

		JPanel pnlExternalField = new JPanel();
		pnlExternalField.setLayout(null);
		pnlExternalField.setBorder(new TitledBorder(null,
				"external field constraints",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlExternalField.setBounds(0, 0, 180, 148);
		add(pnlExternalField);
		radConstantPressure.setBounds(21, 26, 137, 23);
		pnlExternalField.add(radConstantPressure);
		radConstantPressure.addActionListener(a);
		radConstantPressure.setToolTipText("Perform Constant Pressure Calculation - cell to vary");
		radConstantVolume.setBounds(21, 55, 137, 23);
		pnlExternalField.add(radConstantVolume);
		radConstantVolume.addActionListener(a);
		radConstantVolume.setToolTipText("Perform Constant Volume Calculation - cell to vary");
		pnlExternalField.add(radNone);
		radNone.setBounds(21, 84, 137, 23);
		radNone.setSelected(true);
		radNone.addActionListener(a);
		ButtonGroup group = new ButtonGroup();
		group.add(radConstantPressure);
		group.add(radConstantVolume);
		group.add(radNone);

		OptionsPanel pnlOptions = new OptionsPanel();
		pnlOptions.setBounds(0, 154, 809, 137);
		add(pnlOptions);
	}

	private class OptionsPanel extends TitledPanel {

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

		private OptionsPanel() {
			super();
			setTitle("options");

			chkDoNotSet.setBounds(7, 19, 215, 25);
			add(chkDoNotSet);
			chkDoNotSet.addActionListener(keyDoNotSet);
			chkOptimizeCellRadii.setBounds(7, 40, 180, 25);
			add(chkOptimizeCellRadii);
			chkOptimizeCellRadii.addActionListener(keyOptimizeCellRadii);
			chkHtmlDoNotFreeze.setBounds(7, 62, 795, 25);
			add(chkHtmlDoNotFreeze);
			chkHtmlDoNotFreeze.addActionListener(keyHtmlDoNotFreeze);
			chkExcludeShellRadii.setBounds(8, 84, 330, 25);
			add(chkExcludeShellRadii);
			chkExcludeShellRadii.addActionListener(keyExcludeShellRadii);
			chkOptimizeUnitCell.setBounds(8, 108, 505, 25);
			add(chkOptimizeUnitCell);
			chkOptimizeUnitCell.addActionListener(keyOptimizeUnitCell);
		}
	}

	public String writeUnfreeze() throws IncompleteOptionException {
		return pnlUnfreeze.writeUnfreeze();
	}
}