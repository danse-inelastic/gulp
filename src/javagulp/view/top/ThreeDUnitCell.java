package javagulp.view.top;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javax.swing.GroupLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import javagulp.model.G;
import javagulp.model.Material;

public class ThreeDUnitCell extends JPanel implements Serializable {

	private class CellParameters extends JPanel {

		private static final long serialVersionUID = -3031539266310166383L;

		private JTextField txtA = new JTextField();
		private JTextField txtB = new JTextField();
		private JTextField txtC = new JTextField();
		private JTextField txtAlpha = new JTextField();
		private JTextField txtBeta = new JTextField();
		private JTextField txtGamma = new JTextField();

		private G g = new G();

		private JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
		private JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
		private JLabel lblC = new JLabel(g.html("c (" + g.ang + ")"));
		private JLabel lblAlpha = new JLabel(g.html(g.alpha + " (deg)"));
		private JLabel lblBeta = new JLabel(g.html(g.beta + " (deg)"));
		private JLabel lblGamma = new JLabel(g.html(g.gamma + " (deg)"));

		private JCheckBox chkA = new JCheckBox("fit");
		private JCheckBox chkB = new JCheckBox("fit");
		private JCheckBox chkC = new JCheckBox("fit");
		private JCheckBox chkAlpha = new JCheckBox("fit");
		private JCheckBox chkBeta = new JCheckBox("fit");
		private JCheckBox chkGamma = new JCheckBox("fit");

		public CellParameters() {
			super();
			setLayout(null);

			lblA.setBounds(10, 13, 45, 15);
			add(lblA);
			lblB.setBounds(10, 34, 45, 15);
			add(lblB);
			lblC.setBounds(10, 55, 45, 15);
			add(lblC);
			lblAlpha.setBounds(10, 76, 70, 15);
			add(lblAlpha);
			lblBeta.setBounds(10, 97, 65, 15);
			add(lblBeta);
			lblGamma.setBounds(10, 118, 85, 15);
			add(lblGamma);
			txtA.setBounds(61, 11, 68, 20);
			add(txtA);
			txtB.setBounds(61, 32, 68, 20);
			add(txtB);
			txtC.setBounds(61, 53, 68, 20);
			add(txtC);
			txtAlpha.setBounds(61, 74, 68, 20);
			add(txtAlpha);
			txtBeta.setBounds(61, 95, 68, 20);
			add(txtBeta);
			txtGamma.setBounds(61, 116, 68, 20);
			add(txtGamma);
			chkA.setBounds(135, 8, 40, 25);
			add(chkA);
			chkB.setBounds(135, 29, 40, 25);
			add(chkB);
			chkC.setBounds(135, 50, 40, 25);
			add(chkC);
			chkAlpha.setBounds(135, 71, 40, 25);
			add(chkAlpha);
			chkBeta.setBounds(135, 92, 40, 25);
			add(chkBeta);
			chkGamma.setBounds(135, 113, 40, 25);
			add(chkGamma);
		}

		public String writeCellParameters() throws IncompleteOptionException {
			JTextField[] fields = { txtA, txtB, txtC, txtAlpha, txtBeta,
					txtGamma };
			String[] descriptions = { "a cell parameter", "b cell parameter",
					"c cell parameter", "alpha cell parameter",
					"beta cell parameter", "gamma cell parameter" };
			boolean text = false;
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getText().equals(""))
					text = true;
			}
			String lines = "";
			if (text) {
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = Back.concatFields(fields) + " ";
			}
			JCheckBox[] boxes = { chkA, chkB, chkC, chkAlpha, chkBeta, chkGamma };
			if (lines.equals(""))
				return "";
			else
				return "cell" + Back.newLine + lines + Back.writeFits(boxes) + Back.newLine;
		}

		// TODO From the documentation: For optimizations or fitting, flags must
		// be set unless cellonly, conp or conv are specified.
	}

	private class CellVectors extends JPanel {

		private static final long serialVersionUID = 5105344486272460180L;

		private JTextField txtAx = new JTextField();
		private JTextField txtAy = new JTextField();
		private JTextField txtAz = new JTextField();
		private JTextField txtBx = new JTextField();
		private JTextField txtBy = new JTextField();
		private JTextField txtBz = new JTextField();
		private JTextField txtCx = new JTextField();
		private JTextField txtCy = new JTextField();
		private JTextField txtCz = new JTextField();

		private G g = new G();

		private JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
		private JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
		private JLabel lblC = new JLabel(g.html("c (" + g.ang + ")"));
		private JLabel lblStrainOptimisationFlags = new JLabel("strain optimization flags");

		private JCheckBox chkXX = new JCheckBox("xx");
		private JCheckBox chkXY = new JCheckBox("xy");
		private JCheckBox chkXZ = new JCheckBox("xz");
		private JCheckBox chkYY = new JCheckBox("yy");
		private JCheckBox chkYZ = new JCheckBox("yz");
		private JCheckBox chkZZ = new JCheckBox("zz");

		public CellVectors() {
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 26;
			gridBagConstraints.insets = new Insets(5, 9, 5, 1);
			add(lblA, gridBagConstraints);
			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			gridBagConstraints_4.gridx = 1;
			gridBagConstraints_4.gridy = 0;
			gridBagConstraints_4.ipadx = 46;
			gridBagConstraints_4.ipady = 1;
			gridBagConstraints_4.insets = new Insets(5, 5, 0, 0);
			add(txtAx, gridBagConstraints_4);
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.gridx = 2;
			gridBagConstraints_8.gridy = 0;
			gridBagConstraints_8.ipadx = 46;
			gridBagConstraints_8.ipady = 1;
			gridBagConstraints_8.insets = new Insets(5, 6, 0, 0);
			add(txtAy, gridBagConstraints_8);
			final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
			gridBagConstraints_13.gridx = 3;
			gridBagConstraints_13.gridy = 0;
			gridBagConstraints_13.ipadx = 46;
			gridBagConstraints_13.ipady = 1;
			gridBagConstraints_13.insets = new Insets(5, 6, 0, 293);
			add(txtAz, gridBagConstraints_13);
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridx = 0;
			gridBagConstraints_1.gridy = 1;
			gridBagConstraints_1.ipadx = 26;
			gridBagConstraints_1.insets = new Insets(5, 9, 5, 1);
			add(lblB, gridBagConstraints_1);
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			gridBagConstraints_5.gridx = 1;
			gridBagConstraints_5.gridy = 1;
			gridBagConstraints_5.ipadx = 46;
			gridBagConstraints_5.ipady = 1;
			gridBagConstraints_5.insets = new Insets(5, 5, 0, 0);
			add(txtBx, gridBagConstraints_5);
			final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
			gridBagConstraints_9.gridx = 2;
			gridBagConstraints_9.gridy = 1;
			gridBagConstraints_9.ipadx = 46;
			gridBagConstraints_9.ipady = 1;
			gridBagConstraints_9.insets = new Insets(5, 6, 0, 0);
			add(txtBy, gridBagConstraints_9);
			final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
			gridBagConstraints_14.gridx = 3;
			gridBagConstraints_14.gridy = 1;
			gridBagConstraints_14.ipadx = 46;
			gridBagConstraints_14.ipady = 1;
			gridBagConstraints_14.insets = new Insets(5, 6, 0, 293);
			add(txtBz, gridBagConstraints_14);
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridx = 0;
			gridBagConstraints_2.gridy = 2;
			gridBagConstraints_2.ipadx = 26;
			gridBagConstraints_2.insets = new Insets(5, 10, 5, 0);
			add(lblC, gridBagConstraints_2);
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.gridx = 1;
			gridBagConstraints_6.gridy = 2;
			gridBagConstraints_6.ipadx = 46;
			gridBagConstraints_6.ipady = 1;
			gridBagConstraints_6.insets = new Insets(5, 5, 0, 0);
			add(txtCx, gridBagConstraints_6);
			final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
			gridBagConstraints_10.gridx = 2;
			gridBagConstraints_10.gridy = 2;
			gridBagConstraints_10.ipadx = 46;
			gridBagConstraints_10.ipady = 1;
			gridBagConstraints_10.insets = new Insets(5, 6, 0, 0);
			add(txtCy, gridBagConstraints_10);
			final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
			gridBagConstraints_15.gridx = 3;
			gridBagConstraints_15.gridy = 2;
			gridBagConstraints_15.ipadx = 46;
			gridBagConstraints_15.ipady = 1;
			gridBagConstraints_15.insets = new Insets(5, 6, 0, 293);
			add(txtCz, gridBagConstraints_15);
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridx = 0;
			gridBagConstraints_3.gridy = 3;
			gridBagConstraints_3.gridwidth = 4;
			gridBagConstraints_3.ipadx = 24;
			gridBagConstraints_3.insets = new Insets(5, 5, 0, 297);
			add(lblStrainOptimisationFlags, gridBagConstraints_3);
			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			gridBagConstraints_7.gridx = 1;
			gridBagConstraints_7.gridy = 4;
			gridBagConstraints_7.ipady = -3;
			gridBagConstraints_7.insets = new Insets(3, 5, 0, 11);
			add(chkXX, gridBagConstraints_7);
			final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
			gridBagConstraints_11.gridx = 2;
			gridBagConstraints_11.gridy = 4;
			gridBagConstraints_11.ipadx = 10;
			gridBagConstraints_11.ipady = -3;
			gridBagConstraints_11.insets = new Insets(3, 6, 0, 0);
			add(chkXY, gridBagConstraints_11);
			final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
			gridBagConstraints_16.gridx = 3;
			gridBagConstraints_16.gridy = 4;
			gridBagConstraints_16.ipadx = 10;
			gridBagConstraints_16.ipady = -3;
			gridBagConstraints_16.insets = new Insets(3, 6, 0, 293);
			add(chkXZ, gridBagConstraints_16);
			final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
			gridBagConstraints_12.gridx = 2;
			gridBagConstraints_12.gridy = 5;
			gridBagConstraints_12.ipadx = 8;
			gridBagConstraints_12.ipady = -8;
			gridBagConstraints_12.insets = new Insets(9, 6, 2, 0);
			add(chkYY, gridBagConstraints_12);
			final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
			gridBagConstraints_17.gridx = 3;
			gridBagConstraints_17.gridy = 5;
			gridBagConstraints_17.ipadx = 8;
			gridBagConstraints_17.ipady = -3;
			gridBagConstraints_17.insets = new Insets(6, 6, 0, 293);
			add(chkYZ, gridBagConstraints_17);
			final GridBagConstraints gridBagConstraints_18 = new GridBagConstraints();
			gridBagConstraints_18.gridx = 3;
			gridBagConstraints_18.gridy = 6;
			gridBagConstraints_18.ipadx = 9;
			gridBagConstraints_18.ipady = -3;
			gridBagConstraints_18.insets = new Insets(6, 6, 205, 293);
			add(chkZZ, gridBagConstraints_18);
			setSize(500, 375);
			setLayout(new GridBagLayout());

		}

		public String writeCellVectors() throws IncompleteOptionException {
			JTextField[] fields = { txtAx, txtAy, txtAz, txtBx, txtBy, txtBz,
					txtCx, txtCy, txtCz };
			String[] descriptions = { "ax cell vector", "ay cell vector",
					"az cell vector", "bx cell vector", "by cell vector",
					"bz cell vector", "cx cell vector", "cy cell vector",
					"cz cell vector", };
			String lines = "";
			if (Back.checkAnyNonEmpty(fields)) {
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = Back.concatFields(fields) + " ";
			}
			JCheckBox[] boxes = { chkXX, chkXY, chkXZ, chkYY, chkYZ, chkZZ };
			lines += Back.writeFits(boxes);
			if (lines.equals(""))
				return "";
			else
				return "vectors" + Back.newLine + lines + Back.newLine;
		}
	}

	private static final long serialVersionUID = -6275533707695311267L;

	private JTabbedPane tabbedPane = new JTabbedPane();
	private CellParameters cellParameters = new CellParameters();
	private CellVectors cellVectors = new CellVectors();

	public ThreeDUnitCell() {
		super();
		setLayout(null);

		add(tabbedPane);
		tabbedPane.setBounds(0, 0, 217, 180);
		tabbedPane.add(cellParameters, "parameters");
		tabbedPane.add(cellVectors, "vectors");
	}

	public String write3DUnitCell() throws IncompleteOptionException {
		if (tabbedPane.getSelectedIndex() == 0)
			return cellParameters.writeCellParameters();
		else
			return cellVectors.writeCellVectors();
	}
	
	public void setParameters(String[] params) {
		cellParameters.txtA.setText(params[1]);
		cellParameters.txtB.setText(params[2]);
		cellParameters.txtC.setText(params[3]);
		cellParameters.txtAlpha.setText(params[4]);
		cellParameters.txtBeta.setText(params[5]);
		cellParameters.txtGamma.setText(params[6]);
	}
	
	public void setVectors(String[] vecs) {
		cellVectors.txtAx.setText(vecs[1]);
		cellVectors.txtAy.setText(vecs[2]);
		cellVectors.txtAz.setText(vecs[3]);
		cellVectors.txtBx.setText(vecs[4]);
		cellVectors.txtBy.setText(vecs[5]);
		cellVectors.txtBz.setText(vecs[6]);
		cellVectors.txtCx.setText(vecs[7]);
		cellVectors.txtCy.setText(vecs[8]);
		cellVectors.txtCz.setText(vecs[9]);
	}
	
	public void setVectors(Material mat) {
		cellVectors.txtAx.setText(""+mat.latticeVec[0]);
		cellVectors.txtAy.setText(""+mat.latticeVec[1]);
		cellVectors.txtAz.setText(""+mat.latticeVec[2]);
		cellVectors.txtBx.setText(""+mat.latticeVec[3]);
		cellVectors.txtBy.setText(""+mat.latticeVec[4]);
		cellVectors.txtBz.setText(""+mat.latticeVec[5]);
		cellVectors.txtCx.setText(""+mat.latticeVec[6]);
		cellVectors.txtCy.setText(""+mat.latticeVec[7]);
		cellVectors.txtCz.setText(""+mat.latticeVec[8]);
	}
	
	/**
	 * This method will convert lattice parameters (a b c alpha beta gamma) into
	 * lattice vectors. Assumes a is in the x direction and b is in the xy plane.
	 * 
	 * @param p
	 * @param degrees	Specified whether alpha beta and gamma are in degrees. If true
	 * 					is passed, they will be converted into radians.
	 * @return
	 */
	public double[][] parametersToVectors(double[] p, boolean degrees) {
		double conv = 1;
		if (degrees)
			conv = Math.PI / 180;
		
		double cosal = Math.cos(p[3] * conv);
		double cosbe = Math.cos(p[4] * conv);
		double cosga = Math.cos(p[5] * conv);
		double sinbe = Math.sin(p[4] * conv);
		double singa = Math.sin(p[5] * conv);
		//perform conversion
		double cosAlStar = ( cosbe * cosga - cosal ) / (sinbe * singa);
		double V = p[0]*p[1]*p[2]*Math.sqrt(1 - cosal*cosal - cosbe*cosbe - cosga*cosga + 2*cosal*cosbe*cosga);
		double cStar = p[0]*p[1]*singa/V;
		double[][] vector = {{p[0], 0, 0},
				{p[1]*cosga, p[1]*singa, 0},
				{p[2]*cosbe, -p[2]*sinbe*cosAlStar, 1/cStar}};
		return vector;
	}
	
	/**
	 * This method will convert a fractional coordinate to its cartesian equivalent.
	 * 
	 * @param fractionalCoordinate	A coordinate whose values are between 0 and 1 which represent the relative location along a b and c in the unit cell.
	 * @param latticeVector		The lattice vectors for the unit cell.
	 * @param coordinate	if true, coordinates are converted from fractional to cartesian, and vice versa
	 * @return	A cartesian projection of the given fractional coordinate and lattice vectors.
	 */
	//chad's method
	/*public double[] convertToCartesian(double[] fractionalCoordinate, double[][] latticeVector) {
		double[] d = new double[fractionalCoordinate.length];
		for (int i=0; i < fractionalCoordinate.length; i++) {
			for (int j=0; j < latticeVector.length; j++) {
				d[i] += latticeVector[i][j]*fractionalCoordinate[j];
			}
		}
		return d;
	}*/
	public double[] convertToCartesian(double[] fractionalCoordinate, double[][] latticeVector, boolean coordinate) {
		double[] d = new double[fractionalCoordinate.length];
		for (int i=0; i < fractionalCoordinate.length; i++) {
			double num = 0;
			for (int j=0; j < latticeVector.length; j++) {
				num += latticeVector[j][i];
			}
			if (coordinate)
				d[i] = fractionalCoordinate[i] * num;
			else
				d[i] = fractionalCoordinate[i] / num;
		}
		return d;
	}
	
	private double round(double val, int places) {
		long factor = (long)Math.pow(10,places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double)tmp / factor;
	}

	public double[][] getVector() {
		if (tabbedPane.getSelectedIndex() == 0) {
			double[] p = new double[6];
			p[0] = Float.parseFloat(cellParameters.txtA.getText());
			p[1] = Float.parseFloat(cellParameters.txtB.getText());
			p[2] = Float.parseFloat(cellParameters.txtC.getText());
			p[3] = Float.parseFloat(cellParameters.txtAlpha.getText());
			p[4] = Float.parseFloat(cellParameters.txtBeta.getText());
			p[5] = Float.parseFloat(cellParameters.txtGamma.getText());
			return parametersToVectors(p, true);
		} else {
			double[][] v = new double[3][3];
			v[0][0] = Double.parseDouble(cellVectors.txtAx.getText());
			v[0][1] = Double.parseDouble(cellVectors.txtAy.getText());
			v[0][2] = Double.parseDouble(cellVectors.txtAz.getText());
			v[1][0] = Double.parseDouble(cellVectors.txtBx.getText());
			v[1][1] = Double.parseDouble(cellVectors.txtBy.getText());
			v[1][2] = Double.parseDouble(cellVectors.txtBz.getText());
			v[2][0] = Double.parseDouble(cellVectors.txtCx.getText());
			v[2][1] = Double.parseDouble(cellVectors.txtCy.getText());
			v[2][2] = Double.parseDouble(cellVectors.txtCz.getText());
			return v;
		}
	}
}