package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PotentialPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Tersoff extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -2090546744906031541L;

	private final TersoffBondOrder BOa = new TersoffBondOrder("BOa");
	private final TersoffBondOrder BOr = new TersoffBondOrder("BOr");

	private final JTextField txtrmax = new JTextField();
	private final JTextField txtRTaper = new JTextField();
	private final JTextField txtZa = new JTextField();
	private final JTextField txtZb = new JTextField();
	private final JTextField txtA = new JTextField();
	private final JTextField txtB = new JTextField();

	private final JCheckBox chkZa = new JCheckBox("fit");
	private final JCheckBox chkZb = new JCheckBox("fit");
	private final JCheckBox chkA = new JCheckBox("fit");
	private final JCheckBox chkB = new JCheckBox("fit");

	private final G g = new G();

	private final JLabel lblAev = new JLabel("A (eV)");
	private final JLabel lblBev = new JLabel("B (eV)");
	private final JLabel lblZa = new JLabel("<html>z<sub>a</sub> (&Aring;<sup>-1</sup>)</html>");
	private final JLabel lblZb = new JLabel("<html>z<sub>b</sub> (&Aring;<sup>-1</sup>)</html>");

	private final JLabel lblRTaper = new JLabel(g.html("r<sub>taper</sub> (&Aring;)"));
	private final JLabel lblrmax = new JLabel(g.html("r<sub>max</sub> (&Aring;)"));
	private final JLabel lblTersoffEq = new JLabel(g.html("E = f(r)[A exp(-z<sub>a</sub> r)BO<sub>r</sub> - B exp(-z<sub>b</sub> r)BO<sub>a</sub>]"
			+ "<br>" + "where" + "<br>" + "BO<sub>r</sub>=(1 + (" + g.alpha + " " + g.zeta
			+ ")<sup>n</sup>)<sup>(-1/2n)</sup><br>BO<sub>a</sub>=(1 + (" + g.alpha + " "
			+ g.zeta + ")<sup>n</sup>)<sup>(-1/2n)</sup><br>where<br>" + g.zeta
			+ " = sum (r<sub>ik</sub>) [f(r<sub>ik</sub>) exp("
			+ g.lambda + "<sup>m</sup>(r<sub>ij</sub> - r<sub>ik</sub>)<sup>m</sup>)]"
			+ "<br>or<br>" + g.zeta + " = sum (r<sub>ik</sub>) [f(r<sub>ik</sub>)g("
			+ g.theta + ")exp(" + g.lambda
			+ "<sup>m</sup>(r<sub>ij</sub> - r<sub>ik</sub>)<sup>m</sup>)]<br>"
			+ "where f(r<sub>ik</sub>) = cosine taper function" + "<br>and<br>g(" + g.theta
			+ ") = 1 + (c/d)<sup>2</sup> - c<sup>2</sup>/[d<sup>2</sup> + (h - cos("
			+ g.theta + "))<sup>2</sup>]"));

	public Tersoff() {
		super(2);
		setTitle("Tersoff (Bond Order)");
		this.setPreferredSize(new java.awt.Dimension(714, 294));

		add(BOa);
		BOa.setBounds(371, 84, 336, 105);
		add(BOr);
		BOr.setBounds(371, 189, 336, 105);
		lblAev.setBounds(460, 13, 40, 20);
		add(lblAev);
		chkA.setBounds(510, 13, 40, 20);
		add(chkA);
		txtA.setBounds(380, 14, 75, 20);
		add(txtA);
		lblBev.setBounds(630, 12, 40, 22);
		add(lblBev);
		chkB.setBounds(680, 12, 40, 22);
		add(chkB);
		txtB.setBounds(549, 14, 75, 20);
		add(txtB);
		txtZa.setBounds(380, 39, 75, 20);
		add(txtZa);
		txtZb.setBounds(549, 39, 75, 20);
		add(txtZb);
		txtRTaper.setBounds(380, 65, 75, 20);
		add(txtRTaper);
		txtrmax.setBounds(549, 65, 75, 20);
		add(txtrmax);
		lblRTaper.setBounds(460, 65, 55, 20);
		add(lblRTaper);
		lblrmax.setBounds(628, 63, 49, 21);
		add(lblrmax);
		add(lblTersoffEq);
		lblTersoffEq.setBounds(7, 14, 357, 273);
		lblZa.setBounds(460, 36, 50, 25);
		add(lblZa);
		lblZb.setBounds(630, 34, 50, 25);
		add(lblZb);
		chkZa.setBounds(510, 36, 40, 25);
		add(chkZa);
		chkZb.setBounds(680, 34, 40, 25);
		add(chkZb);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		final JTextField[] fields = { txtA, txtB, txtZa, txtZb, txtRTaper, txtrmax };
		final String[] descriptions = { "A", "B", "za", "zb", "rtaper", "rmax" };
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		final JCheckBox[] boxes = { chkA, chkB, chkZa, chkZb };
		final String lines = "botwobody" + Back.newLine + getAtoms()
		+ Back.concatFields(fields) + Back.writeFits(boxes) + Back.newLine;
		return lines + "boattractive" + BOa.writeBondOrder()
		+ "borepulsive" + BOr.writeBondOrder();
	}

	@Override
	public PotentialPanel clone() {
		return super.clone(new Tersoff());
	}
}