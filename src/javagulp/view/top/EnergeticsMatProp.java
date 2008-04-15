package javagulp.view.top;

import java.io.Serializable;

import javagulp.view.KeywordListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class EnergeticsMatProp extends JPanel implements Serializable {

	private static final long serialVersionUID = -2561314899818589985L;

	private JCheckBox chkUseCostFunction = new JCheckBox("<html>use cost function instead of energy during optimization; evaluate energy and cost function for single energy calculations</html>");
	private JCheckBox chkCalculateGradients = new JCheckBox("calculate gradients but do not optimize");
	private JCheckBox chkPerformSingle = new JCheckBox("perform a single energy calculation");
	private JCheckBox chkUseSpatialDecomposition = new JCheckBox("use spatial decomposition algorithm");
	private JCheckBox chkDoNotCalculate = new JCheckBox("do not calculate the energy");
	private JCheckBox chkUseTheMinimum = new JCheckBox("<html>use the minimum image convention on real space components of the energy during MD/conjugate gradient</html>");
	private JCheckBox chkCalculateMaterial = new JCheckBox("calculate material properties");

	private KeywordListener keyUseCostFunction = new KeywordListener(chkUseCostFunction, "cost");
	private KeywordListener keyCalculateGradients = new KeywordListener(chkCalculateGradients, "gradient");
	private KeywordListener keyUseSpatialDecomposition = new KeywordListener(chkUseSpatialDecomposition, "spatial");
	private KeywordListener keyDoNotCalculate = new KeywordListener(chkDoNotCalculate, "noenergy");
	private KeywordListener keyUseTheMinimum = new KeywordListener(chkUseTheMinimum, "minimum_image");
	private KeywordListener keyCalculateMaterial = new KeywordListener(chkCalculateMaterial, "property");

	public EnergeticsMatProp() {
		super();
		setLayout(null);

		chkUseCostFunction.addActionListener(keyUseCostFunction);
		chkUseCostFunction.setBounds(6, 74, 445, 38);
		add(chkUseCostFunction);
		chkCalculateGradients.addActionListener(keyCalculateGradients);
		chkCalculateGradients.setBounds(6, 43, 275, 25);
		add(chkCalculateGradients);
		chkPerformSingle.setToolTipText("Calculate the Bulk Lattice properties");
		chkPerformSingle.setBounds(7, 12, 245, 25);
		add(chkPerformSingle);
		chkUseSpatialDecomposition.addActionListener(keyUseSpatialDecomposition);
		chkUseSpatialDecomposition.setBounds(6, 149, 261, 30);
		add(chkUseSpatialDecomposition);
		chkDoNotCalculate.addActionListener(keyDoNotCalculate);
		chkDoNotCalculate.setBounds(6, 118, 195, 25);
		add(chkDoNotCalculate);
		chkUseTheMinimum.addActionListener(keyUseTheMinimum);
		chkUseTheMinimum.setBounds(6, 185, 422, 36);
		add(chkUseTheMinimum);
		chkCalculateMaterial.setBounds(6, 238, 225, 25);
		add(chkCalculateMaterial);
		chkCalculateMaterial.addActionListener(keyCalculateMaterial);
	}
}