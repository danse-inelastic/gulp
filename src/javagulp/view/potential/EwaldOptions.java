package javagulp.view.potential;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EwaldOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -3361062355703375625L;

	private final G g = new G();

	private final JComboBox cboMultipole = new JComboBox(new String[] { "monopole",
			"dipole", "quadrupole", "octopole" });
	private final JCheckBox chkCellMultipole = new JCheckBox("use cell multipole method for long-range interactions");
	private final JCheckBox chkDipoleCorrection = new JCheckBox("add surface dependent dipole correction");
	private final JCheckBox chkNoElectrostatics = new JCheckBox("<html>turn off Ewald summation/Coulomb interaction even when charges are present in the input</html>");
	private final JCheckBox chkUseTheWolf = new JCheckBox("use the Wolf approximation to the Ewald sum (J.Chem.Phys. 110, 8254, '99)");
	private final JCheckBox chkUseEwaldlikeMethod = new JCheckBox("use ewald-like method on dispersion terms");

	private final JLabel lblCellSize = new JLabel("cell size (determined by short potentials)");
	private final JLabel lblDipoleUnitCell = new JLabel("<html>2/3 " + g.pi
			+ " D<sup>2</sup> V  where D is dipole/unit cell</html>");
	private final JLabel lblEta = new JLabel(g.html(g.eta + " (" + g.ang
			+ "<sup>-1</sup>)"));
	private final JLabel lblHtml = new JLabel("<html>E<sub>i</sub><sub>j</sub> = q<sub>i</sub>q<sub>j</sub>/r"
			+ " erfc(&#951; r) - lim<sub>r->r<sub>max</sub></sub>q<sub>i</sub>q<sub>j</sub>/r<sub>max</sub> erfc(&#951; r<sub>max</sub>)<br>"
			+ "E<sub>i</sub> = - (erfc(&#951; r<sub>max</sub>)/(2 r<sub>max</sub>) + &#951;/(&#8730;&#960;)) q<sub>i</sub><sup>2</sup> </html>");
	private final JLabel lblMultipole = new JLabel("highest multipole");
	private final JLabel lblrmax = new JLabel(g.html("r<sub>max</sub> (" + g.ang
			+ ")"));

	private final TitledPanel pnlCellMultipole = new TitledPanel();
	private final TitledPanel pnlConvergenceSpeed = new TitledPanel();
	private final TitledPanel pnlDipoleCorrection = new TitledPanel();
	private final TitledPanel pnlqWolf = new TitledPanel();
	private final TitledPanel pnlSpaceCutoff = new TitledPanel();

	private final JTextField txtCellSize = new JTextField();
	private final JTextField txtqWolfEta = new JTextField();
	private final JTextField txtqWolfRmax = new JTextField();
	private final JTextField txtrspeed = new JTextField("1.0");
	private final JTextField txtewaldrealradius = new JTextField();

	private final KeywordListener keyUseEwaldlikeMethod = new KeywordListener(chkUseEwaldlikeMethod, "c6");
	private final KeywordListener keyDipoleCorrection = new KeywordListener(chkDipoleCorrection, "dipole");
	private final KeywordListener keyNoElectrostatics = new KeywordListener(chkNoElectrostatics, "noelectrostatics");

	public EwaldOptions() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(700, 315));


		chkUseEwaldlikeMethod.setBounds(0, 31, 820, 25);
		add(chkUseEwaldlikeMethod);
		chkUseEwaldlikeMethod.addActionListener(keyUseEwaldlikeMethod);

		pnlConvergenceSpeed.setBounds(357, 62, 463, 50);
		pnlConvergenceSpeed.setTitle("real/reciprocal space relative convergence speed");
		pnlConvergenceSpeed.setToolTipText("<html>Relative speed for reciprocal and real space terms<br>"
				+ " to be calculated. Formulae for determining optimum eta value <br>"
				+ "assume rspeed=1.0, however calculations can be speeded up by using<br>"
				+ " larger values for small systems (e.g. 2.0) or small values for large<br>"
				+ " systems (0.25) as reciprocal space calculation is generally faster.");
		add(pnlConvergenceSpeed);
		txtrspeed.setBounds(26, 20, 71, 23);
		pnlConvergenceSpeed.add(txtrspeed);

		pnlSpaceCutoff.setTitle("real space cutoff of Ewald sum");
		pnlSpaceCutoff.setBounds(0, 62, 351, 51);
		add(pnlSpaceCutoff);
		txtewaldrealradius.setBounds(12, 22, 127, 23);
		pnlSpaceCutoff.add(txtewaldrealradius);

		pnlDipoleCorrection.setTitle("dipole correction");
		pnlDipoleCorrection.setBounds(0, 123, 351, 103);
		add(pnlDipoleCorrection);
		chkDipoleCorrection.addActionListener(keyDipoleCorrection);
		chkDipoleCorrection.setBounds(12, 46, 329, 25);
		pnlDipoleCorrection.add(chkDipoleCorrection);
		lblDipoleUnitCell.setBounds(12, 20, 329, 20);
		pnlDipoleCorrection.add(lblDipoleUnitCell);

		pnlCellMultipole.setTitle("cell multipole method (only clusters)");
		pnlCellMultipole.setBounds(357, 122, 463, 103);
		add(pnlCellMultipole);
		cboMultipole.setSelectedIndex(2);
		cboMultipole.setBounds(170, 50, 152, 22);
		pnlCellMultipole.add(cboMultipole);
		lblMultipole.setBounds(5, 54, 159, 15);
		pnlCellMultipole.add(lblMultipole);
		lblCellSize.setBounds(5, 75, 339, 20);
		pnlCellMultipole.add(lblCellSize);
		txtCellSize.setBounds(350, 76, 50, 20);
		pnlCellMultipole.add(txtCellSize);
		chkCellMultipole.setBounds(10, 20, 443, 25);
		pnlCellMultipole.add(chkCellMultipole);

		chkNoElectrostatics.addActionListener(keyNoElectrostatics);
		chkNoElectrostatics.setBounds(0, 0, 820, 25);
		add(chkNoElectrostatics);

		pnlqWolf.setToolTipText("Calculates the electrostatic energy using the approximation to the Ewald sum due to Wolf et al (J. Chem. Phys., 110, 8254, 1999). At present the Wolf sum cannot be used with a defect calculation.");
		pnlqWolf.setTitle("Wolf approximation");
		pnlqWolf.setBounds(0, 232, 820, 110);
		add(pnlqWolf);
		lblHtml.setBounds(13, 50, 568, 53);
		pnlqWolf.add(lblHtml);
		lblEta.setBounds(355, 50, 60, 25);
		pnlqWolf.add(lblEta);
		lblrmax.setBounds(359, 77, 56, 26);
		pnlqWolf.add(lblrmax);
		txtqWolfEta.setBackground(Back.grey);
		txtqWolfEta.setBounds(587, 52, 71, 20);
		pnlqWolf.add(txtqWolfEta);
		txtqWolfRmax.setBackground(Back.grey);
		txtqWolfRmax.setBounds(587, 81, 71, 20);
		pnlqWolf.add(txtqWolfRmax);
		chkUseTheWolf.setBounds(13, 21, 699, 25);
		pnlqWolf.add(chkUseTheWolf);
	}

	private String writeCmm() throws IncompleteOptionException {
		// From the documentation:
		// NOTE: cmm cannot be used in conjunction with EEM or QEq at the
		// moment.
		String lines = "";
		if (chkCellMultipole.isSelected()) {
			if (txtCellSize.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for cell multipole method (cmm) cell size");
			lines += cboMultipole.getSelectedItem() + " "
			+ txtCellSize.getText() + Back.newLine;
		}
		return lines;
	}

	public String writeEwald() throws IncompleteOptionException {
		return writeRspeed() + writeCmm() + writeQwolf() + writeRealRadius();
	}

	private String writeQwolf() throws IncompleteOptionException {
		String lines = "";
		if (chkUseTheWolf.isSelected()) {
			if (txtqWolfEta.getText().equals("")
					&& txtqWolfRmax.getText().equals(""))
				throw new IncompleteOptionException("Please enter Eta and/or Rmax for qWolf");
			if (!txtqWolfEta.getText().equals(""))
				lines += txtqWolfEta.getText() + " ";
			if (!txtqWolfRmax.getText().equals(""))
				lines += txtqWolfRmax.getText();
			lines += Back.newLine;
		}
		return lines;
	}

	private String writeRspeed() {
		String lines = "";
		if (!txtrspeed.getText().equals("")
				&& !txtrspeed.getText().equals("1.0")) {
			Double.parseDouble(txtrspeed.getText());
			lines = "rspeed " + txtrspeed.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeRealRadius() {
		String lines = "";
		if (!txtewaldrealradius.getText().equals("")) {
			Double.parseDouble(txtewaldrealradius.getText());
			lines = "ewaldrealradius " + txtewaldrealradius.getText() + Back.newLine;
		}
		return lines;
	}
}