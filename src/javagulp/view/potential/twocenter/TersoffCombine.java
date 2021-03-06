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

public class TersoffCombine extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 7066205879433087197L;

	private final TersoffBondOrder pnlBOa = new TersoffBondOrder("BOa");
	private final TersoffBondOrder pnlBOr = new TersoffBondOrder("BOr");

	private final JCheckBox chkChia = new JCheckBox("fit");
	private final JCheckBox chkChir = new JCheckBox("fit");

	private final JTextField txtRmax = new JTextField("generated");
	private final JTextField txtRTaper = new JTextField("generated");
	private final JTextField txtZA = new JTextField("generated");
	private final JTextField txtZB = new JTextField("generated");
	private final JTextField txtA = new JTextField("generated");
	private final JTextField txtB = new JTextField("generated");
	private final JTextField txtChia = new JTextField();
	private final JTextField txtChir = new JTextField();

	private final G g = new G();

	private final JLabel lblChir = new JLabel(g.html(g.chi + "<sub>r</sub>"));
	private final JLabel lblChia = new JLabel("<html>&#967;<sub>a</sub></html>");
	private final JLabel lblRmax = new JLabel(g.html("r<sub>max</sub> (&Aring;)"));
	private final JLabel lblRTaper = new JLabel(g.html("r<sub>taper</sub> (&Aring;)"));
	private final JLabel lblZB = new JLabel(g.html("z<sub>b</sub> (&Aring;<sup>-1</sup>)"));
	private final JLabel lblZA = new JLabel(g.html("z<sub>a</sub> (&Aring;<sup>-1</sup>)"));
	private final JLabel lblBev = new JLabel("B (eV)");
	private final JLabel lblAev = new JLabel("A (eV)");

	private final JLabel lblTersoffEq = new JLabel(g.html("E = f(r)[" + g.chi
			+ "<sub>r</sub> A exp(-z<sub>a</sub> r)BO<sub>r</sub> - "
			+ g.chi + "<sub>a</sub> B exp(-z<sub>b</sub> r)BO<sub>a</sub>]"
			+ "<br>where<br>BO<sub>r</sub>=(1 + (" + g.alpha + " " + g.zeta
			+ ")<sup>n</sup>)<sup>(-1/2n)</sup><br>BO<sub>a</sub>=(1 + ("
			+ g.alpha + " " + g.zeta + ")<sup>n</sup>)<sup>(-1/2n)</sup>"
			+ "<br>where<br>" + g.zeta + " = sum (r<sub>ik</sub>) [f(r<sub>ik</sub>) exp("
			+ g.lambda + "<sup>m</sup>(r<sub>ij</sub> - r<sub>ik</sub>)<sup>m</sup>)]"
			+ "<br>or<br>" + g.zeta + " = sum (r<sub>ik</sub>) [f(r<sub>ik</sub>)g("
			+ g.theta + ")exp(" + g.lambda
			+ "<sup>m</sup>(r<sub>ij</sub> - r<sub>ik</sub>)<sup>m</sup>)]"
			+ "<br>where f(r<sub>ik</sub>) = cosine taper function"
			+ "<br>and<br>g(" + g.theta
			+ ") = 1 + (c/d)<sup>2</sup> - c<sup>2</sup>/[d<sup>2</sup> + (h - cos("
			+ g.theta + "))<sup>2</sup>]"));

	public TersoffCombine() {
		super(2);
		setTitle("Tersoff combined (Bond Order)");
		this.setPreferredSize(new java.awt.Dimension(644, 301));

		lblAev.setBounds(469, 10, 42, 21);
		add(lblAev);
		txtA.setEditable(false);
		txtA.setBounds(378, 10, 77, 21);
		add(txtA);
		lblBev.setBounds(621, 10, 42, 21);
		add(lblBev);
		txtB.setEditable(false);
		txtB.setBounds(537, 10, 77, 21);
		add(txtB);
		txtZA.setEditable(false);
		txtZA.setBounds(378, 36, 77, 21);
		add(txtZA);
		lblZA.setBounds(462, 36, 49, 21);
		add(lblZA);
		lblZB.setBounds(621, 36, 49, 21);
		add(lblZB);
		txtZB.setEditable(false);
		txtZB.setBounds(537, 36, 77, 21);
		add(txtZB);
		txtRTaper.setEditable(false);
		txtRTaper.setBounds(378, 62, 77, 21);
		add(txtRTaper);
		txtRmax.setEditable(false);
		txtRmax.setBounds(537, 62, 77, 21);
		add(txtRmax);
		lblRTaper.setBounds(462, 62, 56, 21);
		add(lblRTaper);
		lblRmax.setBounds(621, 62, 49, 21);
		add(lblRmax);
		add(lblTersoffEq);
		lblTersoffEq.setBounds(7, 21, 357, 280);
		pnlBOa.setBounds(371, 109, 336, 105);
		add(pnlBOa);
		pnlBOr.setBounds(371, 213, 336, 105);
		add(pnlBOr);
		lblChir.setBounds(462, 89, 22, 21);
		add(lblChir);
		chkChir.setBounds(484, 89, 42, 21);
		add(chkChir);
		txtChir.setBounds(378, 89, 77, 21);
		add(txtChir);
		lblChia.setBounds(621, 89, 22, 21);
		add(lblChia);
		chkChia.setBounds(643, 89, 42, 21);
		add(chkChia);
		txtChia.setBounds(537, 89, 77, 21);
		add(txtChia);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;
		final JTextField[] fields = { txtChir, txtChia };
		final String[] descriptions = { "Chir", "Chia" };
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		final JCheckBox[] boxes = { chkChir, chkChia };
		final String lines = "botwobody combine" + Back.newLine + getAtoms()
		+ Back.concatFields(fields) + Back.writeFits(boxes) + Back.newLine;
		return lines + "boattractive" + pnlBOa.writeBondOrder()
		+ "borepulsive" + pnlBOr.writeBondOrder();
	}

	@Override
	public PotentialPanel clone() {
		return super.clone(new TersoffCombine());
	}
}