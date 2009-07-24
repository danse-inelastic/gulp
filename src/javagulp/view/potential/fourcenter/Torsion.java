package javagulp.view.potential.fourcenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Torsion extends PotentialPanel implements Serializable {

	private class Regular extends JPanel implements Serializable {
		private static final long serialVersionUID = 2667154652151595423L;

		private final PPP k = new PPP("k (eV)");

		G g = new G();

		private final JLabel lblISign = new JLabel("<html>isign</html>");
		private final JLabel lblN = new JLabel("n");
		private final JLabel lblPhi0 = new JLabel(g.html(g.phi + "<sub>0</sub> (deg)"));
		private final JLabel lblUnits = new JLabel("units");

		private final JComboBox cboISign = new JComboBox(new String[] { "+", "-" });
		private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

		private final JTextField txtN = new JTextField();
		private final JTextField txtPhi0 = new JTextField("0.0");

		private Regular() {
			super();
			setLayout(null);

			k.setBounds(0, 0, 225, 25);
			add(k);
			lblN.setBounds(0, 25, 80, 20);
			add(lblN);
			txtN.setBounds(80, 25, 100, 20);
			add(txtN);
			lblPhi0.setBounds(0, 50, 80, 25);
			add(lblPhi0);
			txtPhi0.setBackground(Back.grey);
			txtPhi0.setBounds(80, 50, 100, 20);
			add(txtPhi0);
			//			lblISign.setBounds(0, 75, 80, 20);
			//			add(lblISign);
			//			lblUnits.setBounds(0, 100, 40, 21);
			//			add(lblUnits);
			//			cboISign.setBounds(80, 75, 100, 20);
			//			add(cboISign);
			//			cboUnits.setBounds(80, 100, 70, 21);
			//			add(cboUnits);
		}

		private String writeRegular() throws IncompleteOptionException {
			if (txtN.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for N");
			Double.parseDouble(txtN.getText());
			final PPP[] params = {k};
			Back.checkAndParseD(params);

			String lines = Back.concatFields(params) + " ";
			if (cboISign.getSelectedItem().equals("-"))
				lines += "- ";
			lines += txtN.getText() + " ";
			if (!txtPhi0.getText().equals("") && !txtPhi0.getText().equals("0.0")) {
				Double.parseDouble(txtPhi0.getText());
				lines += txtPhi0.getText() + " ";
			}
			if (!Back.getCurrentRun().getPotential().createLibrary.threeAtomBondingOptions.Bond()) {
				lines += radii.writeRadii();
				if (cbormax41.getSelectedIndex() != 1)
					lines += cbormax41.getSelectedItem();
				else
					lines += "0";
			}
			lines += Back.writeFits(params);
			return lines;
		}
	}

	private class Esff extends JPanel implements Serializable {
		private static final long serialVersionUID = 5348506211167608829L;

		G g = new G();

		private final PPP k1 = new PPP(g.html("k<sub>1</sub> (eV)"));
		private final PPP k2 = new PPP(g.html("k<sub>2</sub> (eV)"));

		private final JLabel lblISign = new JLabel("<html>isign</html>");
		private final JLabel lblN = new JLabel("n");
		private final JLabel lblUnits = new JLabel("units");

		private final JTextField txtN = new JTextField();

		private final JComboBox cboISign = new JComboBox(new String[] { "+", "-" });
		private final JComboBox cboUnits = new JComboBox(new String[] {"kcal", "kjmol"});

		private Esff() {
			super();
			setLayout(null);

			k1.setBounds(0, 0, 225, 25);
			add(k1);
			k2.setBounds(0, 25, 225, 25);
			add(k2);
			lblN.setBounds(0, 50, 80, 20);
			add(lblN);
			txtN.setBounds(80, 50, 100, 20);
			add(txtN);
			lblISign.setBounds(0, 75, 80, 20);
			add(lblISign);
			lblUnits.setBounds(0, 100, 40, 21);
			add(lblUnits);
			cboISign.setBounds(80, 75, 100, 20);
			add(cboISign);
			cboUnits.setBounds(80, 100, 70, 21);
			add(cboUnits);
		}

		private String writeEsff() throws IncompleteOptionException {
			if (txtN.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for N");
			Double.parseDouble(txtN.getText());
			final PPP[] params = {k1, k2};
			Back.checkAndParseD(params);

			String lines = Back.concatFields(params) + " ";
			if (cboISign.getSelectedItem().equals("-"))
				lines += "- ";
			lines += txtN.getText() + " ";
			if (!Back.getCurrentRun().getPotential().createLibrary.threeAtomBondingOptions.Bond()) {
				lines += radii.writeRadii();
				if (cbormax41.getSelectedIndex() != 1)
					lines += cbormax41.getSelectedItem();
				else
					lines += "0";
			}
			lines += Back.writeFits(params);
			return lines;
		}
	}

	private static final long serialVersionUID = 3434453072946291408L;

	private final JComboBox cbormax41 = new JComboBox(new String[] { "", "infinity" });
	private final G g = new G();

	private final String strreg = g.html("E = k (1 + isign cos(n " + g.phi + " - " + g.phi + "<sub>0</sub>))");
	private final String stresff = g.html("E = k<sub>1</sub> sin<sup>2</sup>" + g.theta
			+ "<sub>123</sub> sin<sup>2</sup>" + g.theta
			+ "<sub>234</sub> + isign k<sub>2</sub> sin<sup>n</sup>"
			+ g.theta + "<sub>123</sub> sin<sup>n</sup>" + g.theta
			+ "<sub>234</sub> cos(n " + g.phi + ")");

	private final JLabel lblFourBodyEq = new JLabel(strreg);
	private final JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private final JLabel lblrmax41 = new JLabel("<html>r<sub>41</sub>max</html>");

	private final Regular r = new Regular();
	private final Esff esff = new Esff();

	private final JCheckBox chkEsff = new JCheckBox("esff");
	private final JCheckBox chkDreiding = new JCheckBox("dreiding");

	private class SerialListener implements ActionListener, Serializable {
		private static final long serialVersionUID = 932410562178213543L;

		public void actionPerformed(ActionEvent e) {
			if (chkEsff.isSelected()) {
				esff.setVisible(true);
				r.setVisible(false);
				lblFourBodyEq.setText(stresff);
			} else {
				r.setVisible(true);
				esff.setVisible(false);
				lblFourBodyEq.setText(strreg);
			}
		}
	}
	private final SerialListener keyEsff = new SerialListener();

	public Torsion() {
		super(4);
		setTitle("torsion");

		lblFourBodyEq.setOpaque(false);
		lblFourBodyEq.setBackground(Color.white);
		lblFourBodyEq.setBounds(15, 21, 426, 33);
		add(lblFourBodyEq);
		radii = new Radii(new String[] {"12", "23", "34"});
		radii.setBounds(240, 75, radii.getWidth(), radii.getHeight());
		add(radii);
		cbormax41.setEditable(true);
		cbormax41.setBounds(290, 150, 75, 20);
		add(cbormax41);
		lblrmax41.setBounds(240, 150, 50, 20);
		add(lblrmax41);
		lblImage.setBounds(400, 21, 189, 77);
		add(lblImage);
		r.setBounds(10, 75, 225, 130);
		add(r);
		esff.setBounds(10, 75, 225, 130);
		add(esff);
		chkEsff.setBounds(10, 50, 75, 25);
		add(chkEsff);
		chkEsff.addActionListener(keyEsff);
		chkDreiding.setBounds(90, 50, 100, 25);
		add(chkDreiding);
		keyEsff.actionPerformed(null);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "";
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		if (!pot.threeAtomBondingOptions.Bond()) {
			if (cbormax41.getSelectedItem() == null
					|| cbormax41.getSelectedItem().equals(""))
				throw new IncompleteOptionException("Please enter a value for r41");

			if (cbormax41.getSelectedIndex() != 1)
				Double.parseDouble((String) cbormax41.getSelectedItem());
		}

		String esffDreiding = "";
		if (chkEsff.isSelected())
			esffDreiding += "esff ";
		if (chkDreiding.isSelected())
			esffDreiding += "dreiding ";
		lines = "torsion " + pot.threeAtomBondingOptions.getAll() + esffDreiding;
		if (chkEsff.isSelected()){
			if (esff.cboUnits.getSelectedIndex() != 0)
				lines += esff.cboUnits.getSelectedItem();
		}
		else
			if (r.cboUnits.getSelectedIndex() != 0)
				lines += r.cboUnits.getSelectedItem();
		lines += Back.newLine + pot.getAtomCombos();
		if (chkEsff.isSelected())
			lines += esff.writeEsff();
		else
			lines += r.writeRegular();
		return lines + Back.newLine;
	}

	@Override
	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
		cbormax41.setEnabled(flag);
	}

	@Override
	public PotentialPanel clone() {
		final Torsion t = new Torsion();
		//		t.cbormax41.setSelectedIndex(this.cbormax41.getSelectedIndex());
		//		t.chkDreiding.setSelected(this.chkDreiding.isSelected());
		//		t.chkEsff.setSelected(this.chkEsff.isSelected());
		//
		//		t.esff.txtN.setText(this.esff.txtN.getText());
		//		t.esff.cboISign.setSelectedIndex(this.esff.cboISign.getSelectedIndex());
		//		t.esff.cboUnits.setSelectedIndex(this.esff.cboUnits.getSelectedIndex());
		//
		//		t.r.txtPhi0.setText(this.r.txtPhi0.getText());
		//		t.r.txtN.setText(this.r.txtN.getText());
		//		t.r.cboISign.setSelectedIndex(this.r.cboISign.getSelectedIndex());
		//		t.r.cboUnits.setSelectedIndex(this.r.cboUnits.getSelectedIndex());
		return super.clone(t);
	}

	@Override
	public int currentParameterCount() {
		PPP[] fields = null;
		if (chkEsff.isSelected())
			fields = new PPP[] {esff.k1, esff.k2};
		else
			fields = new PPP[] {r.k};

		int count = 0;
		for (final PPP p: fields)
			if (p.chk.isSelected())
				count++;
		return count;
	}

	@Override
	public void setParameter(int i, String value) {
		if (chkEsff.isSelected())
			params = new PPP[] {esff.k1, esff.k2};
		else
			params = new PPP[] {r.k};

		super.setParameter(i, value);
	}
}