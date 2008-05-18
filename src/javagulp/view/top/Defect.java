package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;

public class Defect extends JPanel implements Serializable {

	private static final long serialVersionUID = -2353097731439178680L;

	private class Coordinate extends JPanel {

		private static final long serialVersionUID = -5826957211539162956L;

		private JComboBox cboType = new JComboBox(new String[] {
				"fractional coordinates", "cartesian coordinates" });

		private JTextField txtX = new JTextField();
		private JTextField txtY = new JTextField();
		private JTextField txtZ = new JTextField();

		private Coordinate() {
			super();
			setLayout(null);

			cboType.setBounds(2, 2, 165, 22);
			txtX.setBounds(170, 2, 40, 23);
			txtY.setBounds(213, 2, 40, 23);
			txtZ.setBounds(255, 2, 40, 23);

			add(cboType);
			add(txtX);
			add(txtY);
			add(txtZ);
		}
	}

	private class FixCoordinate extends TitledPanel {

		private static final long serialVersionUID = -2581514881672485855L;

		private Coordinate c = new Coordinate();

		private JComboBox cboCoreShell = new JComboBox(new String[] { "both",
				"core", "shell" });
		private JComboBox cboAxes = new JComboBox(new String[] { "none", "xyz",
				"xy", "xz", "yz", "x", "y", "z" });

		private JLabel lblFix = new JLabel("fix interstitial along:");

		private FixCoordinate() {
			super();
			setLayout(null);

			c.setBounds(5, 20, 300, 25);
			add(c);
			cboCoreShell.setBounds(315, 22, 65, 22);
			add(cboCoreShell);
			lblFix.setBounds(7, 53, 130, 15);
			add(lblFix);
			cboAxes.setBounds(140, 48, 64, 25);
			add(cboAxes);
		}
	}

	private G g = new G();

	private String[] forceOpts = {
			"none",
			"<html>use electrostatic force of region 1 screened by dielectric constant</html>",
			"<html>use electrostatic force of region 1 screened by dielectric constant, but neglecting contribution to derivatives of region 1</html>",
			"<html>use electrostatic force of defects screened by dielectric constant</html>",
			"<html>use electrostatic force of defects screened by dielectric constant, but neglecting contribution to derivatives of region 1</html>",
			"<html>consider interaction of region 2a only with defects</html>" };

	private JComboBox cboForces = new JComboBox(forceOpts);
	private JComboBox cboRestart = new JComboBox(new String[] { "<number>",
			"all" });

	private TitledPanel pnlDefect = new TitledPanel();
	private Coordinate pnlDefectC = new Coordinate();
	private TitledPanel pnlVacancy = new TitledPanel();
	private Coordinate pnlVacancyC = new Coordinate();
	private FixCoordinate pnlInterstitial = new FixCoordinate();
	private FixCoordinate pnlImpurity = new FixCoordinate();

	private JTextField txtRadiusOfRegion1 = new JTextField();
	private JTextField txtRadiusOfRegion2 = new JTextField();
	private JTextField txtRadiusOfOldRegion1 = new JTextField();

	private JCheckBox chkOutputRegion = new JCheckBox("output region 1 before calculation");
	private JCheckBox chkNoOptimisationsDuring = new JCheckBox("no optimizations during the bulk run");
	private JCheckBox chkSaveRegion2Information = new JCheckBox("save region2 information for restart");
	private JCheckBox chkRestartFromSaved = new JCheckBox("restart from saved region 2 information");
	private JCheckBox chkSymmetryCalculations = new JCheckBox("do not use symmetry in defect calculations");
	private JCheckBox chkSitePotentials = new JCheckBox("<html>when calculating site potentials in region 1, output values for all sites instead of just those in asymmetric unit</html>");
	private JCheckBox chkStoreAllIons = new JCheckBox("store all ions in region 2 that interact with region 1");
	private JCheckBox chkDefect = new JCheckBox("perform an embedded cluster defect calculation at the end of bulk calculation");

	private KeywordListener keyOutputRegion = new KeywordListener(chkOutputRegion, "regi_before");
	private KeywordListener keyNoOptimisationsDuring = new KeywordListener(chkNoOptimisationsDuring, "bulk_noopt");
	private KeywordListener keySaveRegion2Information = new KeywordListener(chkSaveRegion2Information, "save");
	private KeywordListener keyRestartFromSaved = new KeywordListener(chkRestartFromSaved, "restore");
	private KeywordListener keySymmetryCalculations = new KeywordListener(chkSymmetryCalculations, "nodsymmetry");
	private KeywordListener keySitePotentials = new KeywordListener(chkSitePotentials, "nodpsym");
	private KeywordListener keyStoreAllIons = new KeywordListener(chkStoreAllIons, "newda");
	private KeywordListener keyDefect = new KeywordListener(chkDefect, "defect");

	public Defect() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(959, 350));

		pnlDefect.setTitle("position defect at:");
		pnlDefect.setBounds(364, 126, 357, 49);
		add(pnlDefect);
		pnlDefectC.setBounds(5, 20, 345, 25);
		pnlDefect.add(pnlDefectC);

		pnlVacancy.setTitle("place vacancy at:");
		pnlVacancy.setBounds(0, 126, 357, 49);
		add(pnlVacancy);
		pnlVacancyC.setBounds(5, 20, 345, 25);
		pnlVacancy.add(pnlVacancyC);

		pnlInterstitial.setTitle("place interstitial at:");
		pnlInterstitial.setBounds(399, 182, 392, 84);
		add(pnlInterstitial);

		pnlImpurity.setTitle("place impurity at:");
		pnlImpurity.setBounds(0, 182, 392, 84);
		add(pnlImpurity);

		chkOutputRegion.addActionListener(keyOutputRegion);
		// Check logic here!!!
		// (Output region 1 list before the start of a defect calculation)
		chkOutputRegion.setBounds(0, 28, 238, 21);
		add(chkOutputRegion);
		chkNoOptimisationsDuring.addActionListener(keyNoOptimisationsDuring);
		chkNoOptimisationsDuring.setBounds(280, 49, 259, 21);
		add(chkNoOptimisationsDuring);
		chkSaveRegion2Information.addActionListener(keySaveRegion2Information);
		chkSaveRegion2Information.setBounds(0, 70, 252, 21);
		chkSaveRegion2Information.setToolTipText("<html>Causes the region 2 matrices, derived from the bulk second derivatives, to be saved to disk as fort.44 for use in restarts. is important for large bulk materials where the second derivatives are expensive to recalculate.</html>");
		add(chkSaveRegion2Information);
		chkRestartFromSaved.addActionListener(keyRestartFromSaved);
		chkRestartFromSaved.setBounds(0, 91, 280, 21);
		add(chkRestartFromSaved);
		chkSymmetryCalculations.addActionListener(keySymmetryCalculations);
		chkSymmetryCalculations.setBounds(0, 49, 294, 21);
		add(chkSymmetryCalculations);
		chkSitePotentials.addActionListener(keySitePotentials);
		chkSitePotentials.setBounds(280, 70, 364, 49);
		add(chkSitePotentials);
		chkStoreAllIons.addActionListener(keyStoreAllIons);
		chkStoreAllIons.setBounds(280, 28, 350, 21);
		add(chkStoreAllIons);
		chkDefect.addActionListener(keyDefect);
		chkDefect.setBounds(0, 4, 550, 25);
		add(chkDefect);

		final TitledPanel pnlRestart = new TitledPanel();
		pnlRestart.setTitle("restart with larger region 1");
		pnlRestart.setBounds(679, 0, 273, 91);
		add(pnlRestart);
		JLabel lblMoveAllRegion = new JLabel("<html>move all region 2 ions within radius (&Aring;) to region 1 after restart</html>");
		lblMoveAllRegion.setBounds(10, 22, 151, 63);
		pnlRestart.add(lblMoveAllRegion);
		cboRestart.setEditable(true);
		cboRestart.setBounds(167, 41, 94, 25);
		pnlRestart.add(cboRestart);

		final TitledPanel pnlForces = new TitledPanel();
		pnlForces.setTitle("forces acting on region 2 ions");
		pnlForces.setBounds(0, 266, 791, 56);
		add(pnlForces);
		cboForces.setBounds(7, 21, 780, 28);
		pnlForces.add(cboForces);

		final TitledPanel pnlRadii = new TitledPanel();
		pnlRadii.setTitle("radii of defect regions");
		pnlRadii.setBounds(728, 91, 224, 84);
		add(pnlRadii);
		JLabel lblRadiusOfRegion1 = new JLabel("Radius of region 1");
		lblRadiusOfRegion1.setBounds(7, 21, 154, 14);
		pnlRadii.add(lblRadiusOfRegion1);
		JLabel lblRadiusOfRegion2 = new JLabel("Radius of region 2");
		lblRadiusOfRegion2.setBounds(7, 42, 154, 14);
		pnlRadii.add(lblRadiusOfRegion2);
		JLabel lblRadiusOfOldRegion1 = new JLabel("Radius of old region 1");
		lblRadiusOfOldRegion1.setBounds(7, 63, 154, 14);
		pnlRadii.add(lblRadiusOfOldRegion1);
		txtRadiusOfRegion1.setBounds(168, 14, 49, 21);
		pnlRadii.add(txtRadiusOfRegion1);
		txtRadiusOfRegion2.setBounds(168, 35, 49, 21);
		txtRadiusOfRegion2.setBackground(Back.grey);
		pnlRadii.add(txtRadiusOfRegion2);
		txtRadiusOfOldRegion1.setBounds(168, 56, 49, 21);
		txtRadiusOfOldRegion1.setBackground(Back.grey);
		pnlRadii.add(txtRadiusOfOldRegion1);
		txtRadiusOfOldRegion1.setToolTipText(g.html("When restarting from a dumpfile containing an explicit region 1 specification, but with a larger region 1 radius, then the old region 1 radius from the previous run must also be given to ensure a correct restart."));
	}

	public String writeDefect() throws IncompleteOptionException {
		return writeInterstitial() + writeImpurity() + writeCentre()
				+ writeVacancy() + move2aTo1() + writeMode2a() + writeSize();
	}

	// TODO write these methods; GUI may need some additional items
	private String writeInterstitial() throws IncompleteOptionException {
		String lines = "";
		return lines;
	}

	private String writeImpurity() throws IncompleteOptionException {
		String lines = "";
		return lines;
	}

	private String writeCentre() {
		String lines = "";
		return lines;
	}

	private String writeVacancy() {
		String lines = "";
		return lines;
	}

	private String move2aTo1() {
		String lines = "";
		if (!cboRestart.getSelectedItem().equals("<number>")) {
			if (cboRestart.getSelectedItem().equals("all")) {
				lines = "move_2a_to_1" + Back.newLine;
			} else {
				Double.parseDouble((String) cboRestart.getSelectedItem());
				lines = "move_2a_to_1 " + cboRestart.getSelectedItem() + Back.newLine;
			}
		}
		return lines;
	}

	private String writeMode2a() {
		String lines = "";
		if (cboForces.getSelectedIndex() != 0) {
			lines = "mode2a " + cboForces.getSelectedIndex() + Back.newLine;
		}
		return lines;
	}

	private String writeSize() {
		String lines = "";
		if (!txtRadiusOfRegion1.getText().equals("")) {
			Double.parseDouble(txtRadiusOfRegion1.getText());
			lines = "size " + txtRadiusOfRegion1.getText();
			if (txtRadiusOfRegion2.getText().equals("")) {
				Double.parseDouble(txtRadiusOfRegion2.getText());
				lines += " " + txtRadiusOfRegion2.getText();
			}
			if (txtRadiusOfOldRegion1.getText().equals("")) {
				Double.parseDouble(txtRadiusOfOldRegion1.getText());
				lines += " " + txtRadiusOfOldRegion1.getText();
			}
			lines += Back.newLine;
		}
		return lines;
	}
}