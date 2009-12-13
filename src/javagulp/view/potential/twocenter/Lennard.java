package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Lennard extends PotentialPanel implements Serializable {

	private final G g = new G();

	private static final long serialVersionUID = 8231505124234995372L;

	private final LennardAB pnlAB = new LennardAB();

	private final JComboBox cboUnits = new JComboBox(new String[] {"ev", "kjmol", "kcal"});
	private final JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});

	private final JLabel lblM = new JLabel("m");
	private final JLabel lblN = new JLabel("n");
	private final JLabel lblUnits = new JLabel("units");

	private final LennardEpsilonSigma pnlEpsilonSigma = new LennardEpsilonSigma();
	public JTextField txtM = new JTextField("12");
	private final JTextField txtN = new JTextField("6");

	public JCheckBox chkAll = new JCheckBox("all");

	private final JTabbedPane tabbedPane = new JTabbedPane();

	private class SerialChangeListener implements Serializable, ChangeListener {
		private static final long serialVersionUID = -3908551382276176335L;

		public void stateChanged(ChangeEvent e) {
			getParams();
		}
	};
	private final SerialChangeListener keyChange = new SerialChangeListener();

	public Lennard() {
		super(2);
		enabled = new boolean[] { true, true, true, true, true, true };
		setTitle("lennard-jones");
		//pnlAB = new LennardAB();

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
		//lblUnits.setBounds(10, 195, 50, 21);
		//add(lblUnits);
		//cboUnits.setBounds(80, 195, 85, 21);
		//add(cboUnits);
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
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
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
				final PPP[] boxes = {pnlEpsilonSigma.Epsilon, pnlEpsilonSigma.Sigma};
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
				final PPP[] boxes = {pnlAB.A, pnlAB.B};
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
		final Lennard l = new Lennard();
		//l.txtM.setText(this.txtM.getText());
		//l.txtN.setText(this.txtN.getText());
		//l.chkAll.setSelected(this.chkAll.isSelected());
		//l.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		//l.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		//l.tabbedPane.setSelectedIndex(this.tabbedPane.getSelectedIndex());

		//TODO This should work but it dosn't.  The PPPs aren't getting cloned.
		//		l.pnlAB = this.pnlAB.clone();
		//		l.pnlEpsilonSigma = this.pnlEpsilonSigma.clone();
		//		this.getParams();
		/*l.pnlAB.cboABParameters.setSelectedIndex(this.pnlAB.cboABParameters.getSelectedIndex());

		l.pnlEpsilonSigma.cboEpsSigParameters.setSelectedIndex(this.pnlEpsilonSigma.cboEpsSigParameters.getSelectedIndex());
		l.pnlEpsilonSigma.zero.setSelected(this.pnlEpsilonSigma.zero.isSelected());
		l.pnlEpsilonSigma.minimum.setSelected(this.pnlEpsilonSigma.minimum.isSelected());
		this.getParams();*/
		//		l.getParams();
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