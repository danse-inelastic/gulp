package javagulp.view.potential.twocenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utility.misc.G;
import utility.misc.SerialListener;

public class Lennard extends PotentialPanel implements Serializable {

	private G g = new G();

	private class LennardAB extends PotentialPanel {

		private static final long serialVersionUID = 8823654542511080623L;

		private PPP A = new PPP("<html>A (eV ang<sup>m</sup>)</html>");
		private PPP B = new PPP("<html>B (eV ang<sup>n</sup>)</html>");
		
		private JComboBox cboABParameters = new JComboBox(new String[] {
				"manual input", "standard combination rules",
				"ESFF combination rules" });
		
		private JPanel esffPanel = new JPanel();
		private JScrollPane inputScroll = new JScrollPane();
		
		private JLabel jwAB = new JLabel(g.html("E = A r<sup>-m</sup> - B r<sup>-n</sup>"));
		private JLabel jwAB_2 = new JLabel(g.html("A = A<sub>ij</sub> = sqrt(A<sub>i</sub>A<sub>j</sub>) <br>B = B<sub>ij</sub> = sqrt(B<sub>i</sub>B<sub>j</sub>)"));
		private JLabel jwAB_3 = new JLabel(g.html("A = A<sub>ij</sub> = A<sub>i</sub>B<sub>j</sub> + A<sub>j</sub>B<sub>i</sub> <br>B = B<sub>ij</sub> = 3 B<sub>i</sub> B<sub>j</sub>"));
		private JPanel manualInput = new JPanel();
		private JLabel setParametersByLabel = new JLabel("set parameters by");
		private JPanel standardCombinationRules = new JPanel();

		private SerialListener keyABParameters = new SerialListener() {
			private static final long serialVersionUID = 37488141038300915L;
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (cboABParameters.getSelectedIndex()) {
				case 0:
					inputScroll.setViewportView(manualInput);
					txtM.setText("12");
					break;
				case 1:
					inputScroll.setViewportView(standardCombinationRules);
					txtM.setText("12");
					break;
				case 2:
					inputScroll.setViewportView(esffPanel);
					txtM.setText("9");
					break;
				}
			}
		};
		
		public LennardAB() {
			super(2);
			setLayout(null);
			enabled = new boolean[] { true, true, true, false, false, false };

			jwAB.setBounds(12, 3, 118, 20);
			add(jwAB);
			setParametersByLabel.setBounds(181, 8, 115, 15);
			add(setParametersByLabel);
			inputScroll.setBounds(2, 29, 274, 92);
			add(inputScroll);
			manualInput.setLayout(null);
			A.setBounds(10, 9, 225, 25);
			manualInput.add(A);
			B.setBounds(10, 34, 225, 25);
			manualInput.add(B);
			standardCombinationRules.setLayout(null);
			jwAB_2.setBounds(10, 10, 144, 51);
			standardCombinationRules.add(jwAB_2);
			esffPanel.setLayout(null);
			jwAB_3.setBounds(10, 20, 325, 40);
			esffPanel.add(jwAB_3);
			cboABParameters.addActionListener(keyABParameters);
			inputScroll.setViewportView(manualInput);
			cboABParameters.setBounds(301, 7, 196, 21);
			add(cboABParameters);
		}

		@Override
		public String writePotential() throws IncompleteOptionException, InvalidOptionException {
			return "";
		}

		@Override
		public LennardAB clone() {
			LennardAB ab = new LennardAB();
			ab.cboABParameters.setSelectedIndex(this.cboABParameters.getSelectedIndex());
			return ab;
		}
	}

	private static final long serialVersionUID = 8231505124234995372L;

	private LennardAB pnlAB = new LennardAB();
	
	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});

	private JLabel lblM = new JLabel("m");
	private JLabel lblN = new JLabel("n");
	private JLabel lblUnits = new JLabel("units");

	private LennardEpsilonSigma pnlEpsilonSigma = new LennardEpsilonSigma();
	public JTextField txtM = new JTextField("12");
	private JTextField txtN = new JTextField("6");

	public JCheckBox chkAll = new JCheckBox("all");

	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private class SerialChangeListener implements Serializable, ChangeListener {
		private static final long serialVersionUID = -3908551382276176335L;

		public void stateChanged(ChangeEvent e) {
			getParams();
		}
	};
	private SerialChangeListener keyChange = new SerialChangeListener();

	public Lennard() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };
		setTitle("lennard-jones");

		tabbedPane.setBounds(168, 14, 518, 266);
		add(tabbedPane);
		tabbedPane.add(pnlAB, "<html>A, B form</html>");
		tabbedPane.add(pnlEpsilonSigma, "<html>&#949;, &#963; form</html>");
		tabbedPane.addChangeListener(keyChange);
		txtM.setBackground(Back.grey);
		txtM.setBounds(32, 51, 36, 20);
		add(txtM);
		txtN.setBackground(Back.grey);
		txtN.setBounds(102, 51, 36, 20);
		add(txtN);
		lblM.setBounds(9, 50, 22, 20);
		add(lblM);
		lblN.setBounds(79, 50, 17, 20);
		add(lblN);
		chkAll.setBounds(42, 133, 63, 28);
		add(chkAll);
		cboEnerGra.setBounds(10, 170, 85, 21);
		add(cboEnerGra);
		lblUnits.setBounds(10, 195, 50, 21);
		add(lblUnits);
		cboUnits.setBounds(80, 195, 85, 21);
		add(cboUnits);
		radii = new Radii(true);
		//put rmax below rmin
		radii.lblrmax[0].setBounds(0, 25, 45, 25);
		radii.txtrmax[0].setBounds(50, 25, 75, 25);
		radii.setBounds(7, 77, 135, 55);
		add(radii);
		getParams();
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		if (txtM.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for m");
		if (txtN.getText().equals(""))
			throw new IncompleteOptionException("Please enter a value for n");
		Potential pot = Back.getPanel().getPotential();
		Boolean all = false;
		
		String lines = "lennard ", values = "", fits = "";
		if (chkAll.isSelected()) {
			all = true;
		}
		if (tabbedPane.getSelectedIndex() == 1) {
			lines += "epsilon ";
			if (pnlEpsilonSigma.zero.isSelected())
				lines += "zero ";
			if (pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex() == 1)
				lines += "combine ";
			if (pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex() == 2) {
				lines += "geometric ";
				if (!txtM.getText().equals("9"))
					lines += txtM.getText() + " ";
			} else {
				if (!txtM.getText().equals("12"))
					lines += txtM.getText() + " ";
			}
			if (!txtN.getText().equals("6"))
				lines += txtN.getText() + " ";
			if (pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex() == 0 && !all) {
				PPP[] boxes = {pnlEpsilonSigma.Epsilon, pnlEpsilonSigma.Sigma};
				Back.checkAndParseD(boxes);
				values += Back.concatFields(boxes) + " ";
				fits = Back.writeFits(boxes);
			}
		} else {
			if (pnlAB.cboABParameters.getSelectedIndex() == 2)
				lines += "esff ";
			if (pnlAB.cboABParameters.getSelectedIndex() == 1)
				lines += "combine ";
			if (!txtM.getText().equals("12"))
				lines += txtM.getText() + " ";
			if (!txtN.getText().equals("6"))
				lines += txtN.getText() + " ";
			if (pnlAB.cboABParameters.getSelectedIndex() == 0 && !all) {
				PPP[] boxes = {pnlAB.A, pnlAB.B};
				Back.checkAndParseD(boxes);
				values += Back.concatFields(boxes) + " ";
				fits = Back.writeFits(boxes);
			}
		}
		lines += pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " ";
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad";
		else
			lines += "ener";
		if (all)
			lines += " all";
		// TODO The GUI has no option for <all>, and <zero> applies to
		// both AB and Epsilon Sigma.
		lines += " " + pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos();
		if (!all)
			lines += values;
		if (!pot.twoAtomBondingOptions.Bond()) {
			lines += " " + radii.writeRadii();
		}
		if (!all)
			lines += fits;
		return lines + Back.newLine;
	}

	@Override
	public PotentialPanel clone() {
		Lennard l = new Lennard();
		l.txtM.setText(this.txtM.getText());
		l.txtN.setText(this.txtN.getText());
		l.chkAll.setSelected(this.chkAll.isSelected());
		l.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		l.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		l.tabbedPane.setSelectedIndex(this.tabbedPane.getSelectedIndex());
		
		//TODO This should work but it dosn't.  The PPPs aren't getting cloned.
		l.pnlAB = this.pnlAB.clone();
		l.pnlEpsilonSigma = this.pnlEpsilonSigma.clone();
		this.getParams();
		/*l.pnlAB.cboABParameters.setSelectedIndex(this.pnlAB.cboABParameters.getSelectedIndex());
		
		l.pnlEpsilonSigma.cboEpsSigParameters.setSelectedIndex(this.pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex());
		l.pnlEpsilonSigma.zero.setSelected(this.pnlEpsilonSigma.zero.isSelected());
		l.pnlEpsilonSigma.minimum.setSelected(this.pnlEpsilonSigma.minimum.isSelected());
		this.getParams();*/
		l.getParams();
		return super.clone(l);
	}
	
	private void getParams() {
		if (tabbedPane.getSelectedIndex() == 1) {
			if (pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex() == 0)
				params = new PPP[]{pnlEpsilonSigma.Epsilon, pnlEpsilonSigma.Sigma};
			else
				params = new PPP[]{};
		} else {
			if (pnlAB.cboABParameters.getSelectedIndex() == 0)
				params = new PPP[]{pnlAB.A, pnlAB.B};
			else
				params = new PPP[]{};
		}
		System.out.println("params.length " + params.length);
	}

	@Override
	public int currentParameterCount() {
		getParams();
		
		return super.currentParameterCount();
	}

	@Override
	public void setParameter(int i, String value) {
		getParams();
		
		super.setParameter(i, value);
	}
}