package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javagulp.model.G;

public class EwaldOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -3361062355703375625L;

	private G g = new G();

	private JComboBox cboMultipole = new JComboBox(new String[] { "monopole",
			"dipole", "quadrupole", "octopole" });
	private JCheckBox chkCellMultipole = new JCheckBox("use cell multipole method for long-range interactions");
	private JCheckBox chkDipoleCorrection = new JCheckBox("add surface dependent dipole correction");
	private JCheckBox chkNoElectrostatics = new JCheckBox("<html>turn off Ewald summation/Coulomb interaction even when charges are present in the input</html>");
	private JCheckBox chkUseTheWolf = new JCheckBox("use the Wolf approximation to the Ewald sum (J.Chem.Phys. 110, 8254, '99)");
	private JCheckBox chkUseEwaldlikeMethod = new JCheckBox("use ewald-like method on dispersion terms");
	
	private JLabel lblCellSize = new JLabel("cell size (determined by short potentials)");
	private JLabel lblDipoleUnitCell = new JLabel("<html>2/3 " + g.pi
			+ " D<sup>2</sup> V  where D is dipole/unit cell</html>");
	private JLabel lblEta = new JLabel(g.html(g.eta + " (" + g.ang
			+ "<sup>-1</sup>)"));
	private JLabel lblHtml = new JLabel("<html>E<sub>i</sub><sub>j</sub> = q<sub>i</sub>q<sub>j</sub>/r"
					+ " erfc(&#951; r) - lim<sub>r->r<sub>max</sub></sub>q<sub>i</sub>q<sub>j</sub>/r<sub>max</sub> erfc(&#951; r<sub>max</sub>)<br>"
					+ "E<sub>i</sub> = - (erfc(&#951; r<sub>max</sub>)/(2 r<sub>max</sub>) + &#951;/(&#8730;&#960;)) q<sub>i</sub><sup>2</sup> </html>");
	private JLabel lblMultipole = new JLabel("highest multipole");
	private JLabel lblrmax = new JLabel(g.html("r<sub>max</sub> (" + g.ang
			+ ")"));

	private TitledPanel pnlCellMultipole = new TitledPanel();
	private TitledPanel pnlConvergenceSpeed = new TitledPanel();
	private TitledPanel pnlDipoleCorrection = new TitledPanel();
	private TitledPanel pnlqWolf = new TitledPanel();
	private TitledPanel pnlSpaceCutoff = new TitledPanel();

	private JTextField txtCellSize = new JTextField();
	private JTextField txtqWolfEta = new JTextField();
	private JTextField txtqWolfRmax = new JTextField();
	private JTextField txtrspeed = new JTextField("1.0");
	private JTextField txtewaldrealradius = new JTextField();

	private KeywordListener keyUseEwaldlikeMethod = new KeywordListener(chkUseEwaldlikeMethod, "c6");	
	private KeywordListener keyDipoleCorrection = new KeywordListener(chkDipoleCorrection, "dipole");
	private KeywordListener keyNoElectrostatics = new KeywordListener(chkNoElectrostatics, "noelectrostatics");

	public EwaldOptions() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(700, 315));


		chkUseEwaldlikeMethod.setBounds(0, 31, 435, 25);
		add(chkUseEwaldlikeMethod);
		chkUseEwaldlikeMethod.addActionListener(keyUseEwaldlikeMethod);
		
		pnlConvergenceSpeed.setBounds(314, 66, 407, 50);
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
		pnlSpaceCutoff.setBounds(0, 66, 308, 51);
		add(pnlSpaceCutoff);
		txtewaldrealradius.setBounds(12, 22, 127, 23);
		pnlSpaceCutoff.add(txtewaldrealradius);

		pnlDipoleCorrection.setTitle("dipole correction");
		pnlDipoleCorrection.setBounds(0, 123, 309, 103);
		add(pnlDipoleCorrection);
		chkDipoleCorrection.addActionListener(keyDipoleCorrection);
		chkDipoleCorrection.setBounds(12, 46, 280, 25);
		pnlDipoleCorrection.add(chkDipoleCorrection);
		lblDipoleUnitCell.setBounds(12, 20, 245, 20);
		pnlDipoleCorrection.add(lblDipoleUnitCell);

		pnlCellMultipole.setTitle("cell multipole method (only clusters)");
		pnlCellMultipole.setBounds(314, 122, 407, 103);
		add(pnlCellMultipole);
		cboMultipole.setSelectedIndex(2);
		cboMultipole.setBounds(143, 50, 105, 22);
		pnlCellMultipole.add(cboMultipole);
		lblMultipole.setBounds(5, 54, 132, 15);
		pnlCellMultipole.add(lblMultipole);
		lblCellSize.setBounds(5, 75, 280, 20);
		pnlCellMultipole.add(lblCellSize);
		txtCellSize.setBounds(290, 75, 50, 20);
		pnlCellMultipole.add(txtCellSize);
		chkCellMultipole.setBounds(10, 20, 387, 25);
		pnlCellMultipole.add(chkCellMultipole);

		chkNoElectrostatics.addActionListener(keyNoElectrostatics);
		chkNoElectrostatics.setBounds(0, 0, 600, 25);
		add(chkNoElectrostatics);

		pnlqWolf.setToolTipText("Calculates the electrostatic energy using the approximation to the Ewald sum due to Wolf et al (J. Chem. Phys., 110, 8254, 1999). At present the Wolf sum cannot be used with a defect calculation.");
		pnlqWolf.setTitle("Wolf approximation");
		pnlqWolf.setBounds(0, 232, 721, 110);
		add(pnlqWolf);
		lblHtml.setBounds(13, 50, 335, 53);
		pnlqWolf.add(lblHtml);
		lblEta.setBounds(355, 50, 60, 25);
		pnlqWolf.add(lblEta);
		lblrmax.setBounds(359, 77, 56, 26);
		pnlqWolf.add(lblrmax);
		txtqWolfEta.setBackground(Back.grey);
		txtqWolfEta.setBounds(416, 52, 71, 20);
		pnlqWolf.add(txtqWolfEta);
		txtqWolfRmax.setBackground(Back.grey);
		txtqWolfRmax.setBounds(416, 78, 71, 20);
		pnlqWolf.add(txtqWolfRmax);
		chkUseTheWolf.setBounds(13, 21, 495, 25);
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