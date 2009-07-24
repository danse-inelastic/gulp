package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.Material;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ThreeDUnitCell extends JPanel implements Serializable {




	private static final long serialVersionUID = -6275533707695311267L;

	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final ThreeDCellParameters cellParameters = new ThreeDCellParameters();
	private final ThreeDCellVectors cellVectors = new ThreeDCellVectors();

	public ThreeDUnitCell() {
		super();
		setLayout(new BorderLayout());

		add(tabbedPane);
		tabbedPane.add(cellVectors, "vectors");
		tabbedPane.add(cellParameters, "parameters");

	}

	public String write3DUnitCell() throws IncompleteOptionException {
		if (tabbedPane.getSelectedIndex() == 0)
			return cellVectors.writeCellVectors();
		else
			return cellParameters.writeCellParameters();
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

		final double cosal = Math.cos(p[3] * conv);
		final double cosbe = Math.cos(p[4] * conv);
		final double cosga = Math.cos(p[5] * conv);
		final double sinbe = Math.sin(p[4] * conv);
		final double singa = Math.sin(p[5] * conv);
		//perform conversion
		final double cosAlStar = ( cosbe * cosga - cosal ) / (sinbe * singa);
		final double V = p[0]*p[1]*p[2]*Math.sqrt(1 - cosal*cosal - cosbe*cosbe - cosga*cosga + 2*cosal*cosbe*cosga);
		final double cStar = p[0]*p[1]*singa/V;
		final double[][] vector = {{p[0], 0, 0},
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
		final double[] d = new double[fractionalCoordinate.length];
		for (int i=0; i < fractionalCoordinate.length; i++) {
			double num = 0;
			for (final double[] element : latticeVector) {
				num += element[i];
			}
			if (coordinate)
				d[i] = fractionalCoordinate[i] * num;
			else
				d[i] = fractionalCoordinate[i] / num;
		}
		return d;
	}

	private double round(double val, int places) {
		final long factor = (long)Math.pow(10,places);
		val = val * factor;
		final long tmp = Math.round(val);
		return (double)tmp / factor;
	}

	public double[][] getVector() {
		if (tabbedPane.getSelectedIndex() == 0) {
			final double[] p = new double[6];
			p[0] = Float.parseFloat(cellParameters.txtA.getText());
			p[1] = Float.parseFloat(cellParameters.txtB.getText());
			p[2] = Float.parseFloat(cellParameters.txtC.getText());
			p[3] = Float.parseFloat(cellParameters.txtAlpha.getText());
			p[4] = Float.parseFloat(cellParameters.txtBeta.getText());
			p[5] = Float.parseFloat(cellParameters.txtGamma.getText());
			return parametersToVectors(p, true);
		} else {
			final double[][] v = new double[3][3];
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