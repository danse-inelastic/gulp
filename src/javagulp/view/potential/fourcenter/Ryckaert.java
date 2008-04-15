package javagulp.view.potential.fourcenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.images.CreateIcon;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.Radii;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import utility.misc.G;
import utility.misc.SerialListener;

public class Ryckaert extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -5894432955972980741L;

	private G g = new G();
	private JComboBox cboPolynomialOrder = new JComboBox(new String[] { "1", "2", "3", "4", "5" });

	private JLabel lblPolynomialOrder = new JLabel("polynomial order");
	private JLabel lblImage = new JLabel(new CreateIcon().createIcon("torsionNum.png"));
	private JLabel lblEquation = new JLabel();
	
	private SerialListener keyOrder = new SerialListener() {
		private static final long serialVersionUID = 932263608165019670L;
		@Override
		public void actionPerformed(ActionEvent e) {
			for (PPP param: params)
				param.setVisible(false);

			int orderNum = cboPolynomialOrder.getSelectedIndex() + 2;
			String equation = "E = ";
			for (int i = 0; i < orderNum; i++) {
				equation += "c<sub>" + i + "</sub>" + "cos<sup>" + i + "</sup> " + g.phi + " + ";
				params[i].setVisible(true);
			}
			equation = equation.substring(0, equation.length() - 3);
			// remove last " + "
			lblEquation.setText(g.html(equation));
		}
	};

	public Ryckaert() {
		super(4);
		setBorder(new TitledBorder(null, "ryckaert",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		this.setPreferredSize(new java.awt.Dimension(658, 231));
		enabled = new boolean[] { false, false, false };
		radii = new Radii(new String[] {"12", "23", "34", "41"});
		radii.setBounds(470, 126, radii.getWidth(), radii.getHeight());
		add(radii);
		int x=10, y = 80, width = 225, height = 25;
		params = new PPP[6];
		for (int i=0; i < params.length; i++) {
			if (i == params.length / 2) {
				x += width + 5;
				y = 80;
			}
			params[i] = new PPP(g.html("c<sub>" + i + "</sub> (eV)"));
			params[i].setBounds(x, y, width, height);
			add(params[i]);
			y += height;
		}
		lblPolynomialOrder.setBounds(10, 20, 110, 15);
		add(lblPolynomialOrder);
		cboPolynomialOrder.addActionListener(keyOrder);
		cboPolynomialOrder.setBounds(125, 17, 52, 24);
		add(cboPolynomialOrder);
		cboPolynomialOrder.setSelectedIndex(0);
		lblImage.setBounds(470, 20, 190, 75);
		add(lblImage);
		lblEquation.setBounds(10, 50, 450, 25);
		add(lblEquation);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		int index = cboPolynomialOrder.getSelectedIndex();
		PPP[] c0 = new PPP[] {params[0]};
		PPP[] coefs = new PPP[index+1];
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = params[i+1];
		}
		Back.checkAndParseD(c0);
		Back.checkAndParseD(coefs);
		
		Potential pot = Back.getPanel().getPotential();
		String lines = "ryckaert " + (index + 1) + Back.newLine
				+ pot.getAtomCombos() + Back.concatFields(c0) + " "
				+ radii.writeRadii() + Back.writeFits(c0)
				+ Back.writeFits(coefs) + Back.concatFields(coefs) + Back.newLine;
		return lines;
	}
	
	@Override
	public PotentialPanel clone() {
		Ryckaert r = new Ryckaert();
		r.cboPolynomialOrder.setSelectedIndex(this.cboPolynomialOrder.getSelectedIndex());
		return super.clone(r);
	}
	
	@Override
	public int currentParameterCount() {
		int index = cboPolynomialOrder.getSelectedIndex();
		int count = 0;
		for (int i=0; i <= index; i++)
			if (params[i].chk.isSelected())
				count++;
		return count;
	}

	@Override
	public void setParameter(int j, String value) {
		int index = cboPolynomialOrder.getSelectedIndex();
		int count = 0;
		for (int i=0; i <= index; i++)
			if (params[i].chk.isSelected()) {
				count++;
				if (j == count)
					params[i].txt.setText(value);
			}
	}
}