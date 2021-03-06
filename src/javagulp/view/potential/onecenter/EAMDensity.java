package javagulp.view.potential.onecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class EAMDensity extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -6987611034827062589L;

	private final JTabbedPane pane = new JTabbedPane();

	private class Gaussian extends JPanel {

		private static final long serialVersionUID = -9003314462000103529L;
		private final G g = new G();

		private final JLabel lblUnits = new JLabel("units");
		private final JLabel equation = new JLabel(g.html(g.rho + "<sub>i</sub> = A r<sub>ij</sub><sup>n</sup>exp(-B(r<sub>ij</sub> - r<sub>0</sub>)<sup>2</sup>)"));

		private final PPP N = new PPP("n");
		private final PPP A = new PPP(g.html("A (&Aring;<sup>-n</sup>)"));
		private final PPP B = new PPP(g.html("B (&Aring;<sup>-1</sup>)"));
		private final PPP R0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));

		private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
				new String[] {"kjmol", "kcal"}));

		public Gaussian() {
			super();
			setLayout(null);

			add(equation);
			add(N);
			add(A);
			add(B);
			add(R0);
			add(cboUnits);
			add(lblUnits);

			equation.setBounds(20, 10, 327, 40);
			N.setBounds(10, 45, 225, 25);
			A.setBounds(10, 70, 225, 25);
			B.setBounds(10, 95, 225, 25);
			R0.setBounds(10, 120, 225, 22);
			lblUnits.setBounds(10, 145, 70, 21);
			cboUnits.setBounds(100, 145, 85, 21);

			N.chk.setVisible(false);
		}

		public String writePotential() throws IncompleteOptionException {
			final PPP[] boxes = { A, B, R0 };
			Back.checkAndParseD(boxes);
			String lines = "eam_density gaussian ";
			if (cboUnits.getSelectedIndex() != 0)
				lines += cboUnits.getSelectedItem() + " ";
			if (!N.txt.getText().equals(""))
				lines += N.txt.getText();
			return lines + Back.newLine + getAtoms()
			+ Back.fieldsAndFits(boxes) + Back.newLine;
		}
	}

	private class Voter extends JPanel {
		private static final long serialVersionUID = -6821398433920094070L;
		private final G g = new G();

		private final JLabel lblUnits = new JLabel("units");
		private final JLabel equation = new JLabel(g.html(g.rho
				+ "<sub>i</sub> = A r<sup>6</sup>(exp(-" + g.beta
				+ " r) + 2<sup>9</sup>exp(-2 " + g.beta + " r))"));

		private final PPP A = new PPP(g.html("A (&Aring;<sup>-6</sup>)"));
		private final PPP Beta = new PPP(g.html(g.beta + " (&Aring<sup>-1</sup>)"));

		private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
				new String[] {"kjmol", "kcal"}));

		public Voter() {
			super();
			setLayout(null);

			add(equation);
			add(A);
			add(Beta);
			add(cboUnits);
			add(lblUnits);

			equation.setBounds(20, 10, 327, 40);
			A.setBounds(10, 45, 225, 25);
			Beta.setBounds(10, 70, 225, 25);
			lblUnits.setBounds(10, 95, 70, 21);
			cboUnits.setBounds(100, 95, 85, 21);
		}

		public String writePotential() throws IncompleteOptionException {
			final PPP[] boxes = { A, Beta };
			Back.checkAndParseD(boxes);
			final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
			String lines = "eam_density voter ";
			if (cboUnits.getSelectedIndex() != 0)
				lines += cboUnits.getSelectedItem();
			lines += Back.newLine + getAtoms() + Back.fieldsAndFits(boxes) + Back.newLine;
			return lines;
		}
	}

	private class Power extends JPanel {

		private static final long serialVersionUID = 1351149931296035651L;
		private final G g = new G();

		private final JLabel lblUnits = new JLabel("units");
		private final JLabel equation = new JLabel(g.html(g.rho
				+ "<sub>i</sub> = C r<sub>ij</sub><sup>-n</sup>"));

		private final PPP N = new PPP("n");
		private final PPP C = new PPP(g.html("C (&Aring<sup>n</sup>)"));

		private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
				new String[] {"kjmol", "kcal"}));

		public Power() {
			super();
			setLayout(null);

			add(equation);
			add(N);
			add(C);
			add(lblUnits);
			add(cboUnits);

			equation.setBounds(20, 10, 327, 40);
			N.chk.setVisible(false);
			N.setBounds(10, 45, 225, 25);
			C.setBounds(10, 70, 225, 25);
			lblUnits.setBounds(10, 95, 70, 21);
			cboUnits.setBounds(100, 95, 85, 21);
		}

		public String writePotential() throws IncompleteOptionException {
			final PPP[] boxes = { C };
			Back.checkAndParseD(boxes);
			String lines = "eam_density power ";
			if (cboUnits.getSelectedIndex() != 0)
				lines += cboUnits.getSelectedItem() + " ";
			final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
			if (!N.txt.getText().equals(""))
				lines += N.txt.getText();
			lines += Back.newLine + getAtoms() + Back.fieldsAndFits(boxes) + Back.newLine;
			return lines;
		}
	}

	private class Poly extends JPanel {

		private static final long serialVersionUID = 5389459664336996949L;
		private final G g = new G();

		private final JLabel lblUnits = new JLabel("units");
		private final JLabel equation;

		private final PPP A;
		private final PPP R0;

		private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
				new String[] {"kjmol", "kcal"}));

		private final int power;

		public Poly(int power) {
			super();
			setLayout(null);
			this.power = power;

			equation = new JLabel(g.html(g.rho + "<sub>i</sub> = A (r<sub>ij</sub> - r<sub>0</sub>)<sup>"
					+ power + "</sup> if r<sub>ij</sub> &lt&lt r<sub>0</sub> else = 0"));
			A = new PPP(g.html("A (&Aring<sup>-" + power + "</sup>)"));
			R0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));

			add(equation);
			add(A);
			add(R0);
			add(cboUnits);
			add(lblUnits);

			equation.setBounds(20, 10, 327, 40);
			A.setBounds(10, 45, 225, 25);
			R0.setBounds(10, 70, 225, 25);
			lblUnits.setBounds(10, 95, 70, 21);
			cboUnits.setBounds(100, 95, 85, 21);
		}

		public String writePotential() throws IncompleteOptionException {
			final PPP[] boxes = { A, R0 };
			Back.checkAndParseD(boxes);
			final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
			String pow = "";
			if (power == 2)
				pow = "quadratic";
			else if (power == 3)
				pow = "cubic";
			else
				pow = "quartic";
			String lines = "eam_density " + pow;
			if (cboUnits.getSelectedIndex() != 0)
				lines += " " + cboUnits.getSelectedItem();
			lines += Back.newLine + getAtoms() + Back.fieldsAndFits(boxes) + Back.newLine;
			return lines;
		}
	}

	private class Exponential extends JPanel {

		private static final long serialVersionUID = 4067553135550848025L;
		private final G g = new G();

		private final JLabel lblUnits = new JLabel("units");
		private final JLabel equation = new JLabel(g.html(g.rho + "<sub>i</sub> = A r<sub>ij</sub><sup>n</sup>exp(-B(r<sub>ij</sub> - r<sub>0</sub>))"));

		PPP n = new PPP("n");
		PPP A = new PPP(g.html("A (&Aring;<sup>-n</sup>)"));
		PPP B = new PPP(g.html("B (&Aring;<sup>-1</sup>)"));
		PPP R0 = new PPP(g.html("r<sub>0</sub> (&Aring;)"));

		private final JComboBox cboUnits = new JComboBox(new DefaultComboBoxModel(
				new String[] {"kjmol", "kcal"}));

		public Exponential() {
			super();
			setLayout(null);

			add(equation);
			add(n);
			add(A);
			add(B);
			add(R0);
			add(cboUnits);
			add(lblUnits);

			equation.setBounds(20, 10, 327, 40);
			n.chk.setVisible(false);
			n.setBounds(10, 45, 225, 25);
			A.setBounds(10, 70, 225, 25);
			B.setBounds(10, 95, 225, 25);
			R0.setBounds(10, 120, 225, 25);
			lblUnits.setBounds(10, 145, 70, 21);
			cboUnits.setBounds(100, 145, 85, 21);
		}

		public String writePotential() throws IncompleteOptionException {
			final PPP[] boxes = { A, B, R0 };
			Back.checkAndParseD(boxes);
			final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
			String lines = "eam_density exponential ";
			if (cboUnits.getSelectedIndex() != 0)
				lines += cboUnits.getSelectedItem() + " ";
			if (!n.txt.getText().equals(""))
				lines += n.txt.getText();
			lines += Back.newLine + getAtoms()
			+ Back.fieldsAndFits(boxes) + Back.newLine;
			return lines;
		}
	}

	private final Voter pnlVoter = new Voter();
	private final Poly pnlQuartic = new Poly(4);
	private final Poly pnlCubic = new Poly(3);
	private final Poly pnlQuadratic = new Poly(2);
	private final Gaussian pnlGaussian = new Gaussian();
	private final Exponential pnlExpo = new Exponential();
	private final Power pnlPower = new Power();

	public EAMDensity() {
		super(1);
		pane.setBounds(0, 0, 721, 310);
		add(pane);
		pane.setPreferredSize(new java.awt.Dimension(375, 121));

		pane.add("Power", pnlPower);
		pane.addTab("Exponential", pnlExpo);
		pane.addTab("Gaussian", pnlGaussian);
		pane.addTab("Quadratic", pnlQuadratic);
		pane.addTab("Cubic", pnlCubic);
		pane.addTab("Quartic", pnlQuartic);
		pane.addTab("Voter", pnlVoter);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		String lines = "";
		if (pane.getSelectedIndex() == 0)
			lines = pnlPower.writePotential();
		if (pane.getSelectedIndex() == 1)
			lines = pnlExpo.writePotential();
		if (pane.getSelectedIndex() == 2)
			lines = pnlGaussian.writePotential();
		if (pane.getSelectedIndex() == 3)
			lines = pnlQuadratic.writePotential();
		if (pane.getSelectedIndex() == 4)
			lines = pnlCubic.writePotential();
		if (pane.getSelectedIndex() == 5)
			lines = pnlQuartic.writePotential();
		if (pane.getSelectedIndex() == 6)
			lines = pnlVoter.writePotential();
		return lines;
	}

	@Override
	public PotentialPanel clone() {
		return new EAMDensity();
	}

	@Override
	public int currentParameterCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParameter(int i, String value) {
		// TODO Auto-generated method stub

	}
}