package javagulp.view.potential.threecenter;

import java.awt.Color;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class MurrellMottram extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 5839651410432317511L;

	private final G g = new G();

	private final JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});

	private final JLabel eq = new JLabel();

	private final JLabel lblUnits = new JLabel("units");

	private final PPP[] c = new PPP[11];

	private final PPP r12 = new PPP(g.html("r<sub>12</sub><sup>0</sup> (" + g.ang + ")"), 50, 90, 40);
	private final PPP r13 = new PPP(g.html("r<sub>12</sub><sup>0</sup> (" + g.ang + ")"), 50, 90, 40);
	private final PPP r23 = new PPP(g.html("r<sub>12</sub><sup>0</sup> (" + g.ang + ")"), 50, 90, 40);
	private final PPP k = new PPP("k (eV)", 35, 90, 40);
	private final PPP rho = new PPP(g.html(g.rho), 35, 90, 40);

	public MurrellMottram() {
		super(3);

		setBorder(new TitledBorder(null, "Murrell-Mottram",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		lblUnits.setBounds(620, 235, 40, 21);
		add(lblUnits);
		cboUnits.setBounds(620, 260, 70, 21);
		add(cboUnits);

		//add Cs
		int x=10, y = 160;
		for (int i=0; i <= 10; i++) {
			if (i == 6) {
				x = 180;
				y = 160;
			}
			c[i] = new PPP("<html>c<sub>" + i + "</sub></html>");
			c[i].lbl.setBounds(0, 0, 20, 25);
			c[i].txt.setBounds(20, 0, 100, 20);
			c[i].chk.setBounds(125, 0, 40, 20);
			c[i].setBounds(x, y, 165, 25);
			add(c[i]);
			y += 25;
		}

		r12.setBounds(350, 160, 190, 25);
		add(r12);
		r13.setBounds(350, 185, 190, 25);
		add(r13);
		r23.setBounds(350, 210, 190, 25);
		add(r23);
		k.setBounds(540, 160, 175, 25);
		add(k);
		rho.setBounds(540, 185, 175, 25);
		add(rho);

		radii = new Radii(true, new String[] {"12", "13", "23"});
		radii.setBounds(350, 235, radii.getWidth(), radii.getHeight());
		add(radii);

		eq.setBounds(7, 18, 581, 117);
		add(eq);
		final String sub1 = g.sub("1"), sub2 = g.sub("2"), sub3 = g.sub("3"), sup2 = g.sup("2");
		eq.setText(g.html("E = k exp(-" + g.rho + "Q" + sub1 + ")f<sub>MM</sub>(Q" + sub1 + "Q" + sub2 + "Q" + sub3
				+ ")<br>f<sub>MM</sub> = c<sub>0</sub> + c" + sub1 + "Q" + sub1 + " + c" + sub2 + "Q" + sub1 + sup2 + " + c" + sub3
				+ "(Q" + sub2 + sup2 + " + Q" + sub3 + sup2 + ") + c<sub>4</sub>Q" + sub1 + "<sup>3</sup> + c<sub>5</sub>Q"
				+ sub1 + "(Q" + sub2 + sup2 + " + Q" + sub3 + sup2 + ")<br>&nbsp;&nbsp;&nbsp; + c<sub>6</sub>(Q" + sub3
				+ "<sup>3</sup> - 3Q" + sub3 + "Q" + sub2 + sup2 + ") + c<sub>7</sub>Q" + sub1 + "<sup>4</sup> + c<sub>8</sub>Q"
				+ sub1 + sup2 + "(Q" + sub2 + sup2 + "+Q" + sub3 + sup2 + ") + c<sub>9</sub>" + "(Q" + sub2 + sup2
				+ "+Q" + sub3 + sup2 + ")" + sup2 + " + " + "c<sub>10</sub>Q" + sub1 + "(Q" + sub3 + "<sup>3</sup> - 3Q"
				+ sub3 + "Q" + sub2 + sup2 + ")<br>Q" + sub1 + "=(R" + sub1 + " + R" + sub2 + " + R" + sub3
				+ ")/sqrt(3) &nbsp;&nbsp;&nbsp; Q" + sub2 + "=(R" + sub2 + " - R" + sub3
				+ ")/sqrt(2) &nbsp;&nbsp;&nbsp; " + "Q" + sub3 + "=(2R" + sub1 + " - R" + sub2 + " - R" + sub3 + ")/sqrt(6)<br> "
				+ "R" + sub1 + "=(r<sub>12</sub> - r<sub>12</sub><sup>0</sup>)/r<sub>12</sub><sup>0</sup> &nbsp;&nbsp;&nbsp; "
				+ "R" + sub2 + "=(r<sub>13</sub> - r<sub>13</sub><sup>0</sup>)/r<sub>13</sub><sup>0</sup> &nbsp;&nbsp;&nbsp; "
				+ "R" + sub3 + "=(r<sub>23</sub> - r<sub>23</sub><sup>0</sup>)/r<sub>23</sub><sup>0</sup><br>"));
		eq.setBackground(Color.white);

		final JLabel lblImage = new JLabel(new CreateIcon().createIcon("angleNum.png"));
		lblImage.setBounds(588, 35, 119, 77);
		add(lblImage);

		params = new PPP[16];
		params[0] = k;
		params[1] = rho;
		params[2] = r12;
		params[3] = r13;
		params[4] = r23;
		for (int i=5; i < 16; i++)
			params[i] = c[i-5];
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		final PPP[] p = {k, rho, r12, r13, r23};
		Back.checkAndParseD(p);
		Back.checkAndParseD(c);

		final CreateLibrary pot = Back.getCurrentRun().getPotential().createLibrary;

		String lines = "murrell-mottram " + pot.threeAtomBondingOptions.getAll();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem();
		lines += Back.newLine + getAtoms() + Back.concatFields(p);
		if (!pot.threeAtomBondingOptions.Bond())
			lines += " " + radii.writeRadii();
		return lines + Back.writeFits(p) + " " + Back.concatFields(c)
		+ Back.writeFits(c) + Back.newLine;
	}

	@Override
	public void setRadiiEnabled(boolean flag) {
		radii.setRadiiEnabled(flag);
	}

	@Override
	public PotentialPanel clone() {
		final MurrellMottram m = new MurrellMottram();
		m.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(m);
	}
}