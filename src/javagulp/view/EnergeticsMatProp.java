package javagulp.view;

import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class EnergeticsMatProp extends JPanel implements Serializable {

	private static final long serialVersionUID = -2561314899818589985L;

	private final JCheckBox chkUseCostFunction = new JCheckBox("<html>use cost function instead of energy during optimization; evaluate energy and cost function for single energy calculations</html>");
	private final JCheckBox chkCalculateGradients = new JCheckBox("calculate gradients but do not optimize");
	//	private JCheckBox chkPerformSingle = new JCheckBox("perform a single energy calculation");
	private final JCheckBox chkUseSpatialDecomposition = new JCheckBox("use spatial decomposition algorithm");
	private final JCheckBox chkDoNotCalculate = new JCheckBox("do not calculate the energy");
	private final JCheckBox chkUseTheMinimum = new JCheckBox("<html>use the minimum image convention on real space components of the energy during MD/conjugate gradient</html>");
	private final JCheckBox chkCalculateMaterial = new JCheckBox("calculate material properties");

	private final KeywordListener keyUseCostFunction = new KeywordListener(chkUseCostFunction, "cost");
	private final KeywordListener keyCalculateGradients = new KeywordListener(chkCalculateGradients, "gradient");
	private final KeywordListener keyUseSpatialDecomposition = new KeywordListener(chkUseSpatialDecomposition, "spatial");
	private final KeywordListener keyDoNotCalculate = new KeywordListener(chkDoNotCalculate, "noenergy");
	private final KeywordListener keyUseTheMinimum = new KeywordListener(chkUseTheMinimum, "minimum_image");
	private final KeywordListener keyCalculateMaterial = new KeywordListener(chkCalculateMaterial, "property");

	public EnergeticsMatProp() {
		super();
		setLayout(null);

		chkUseCostFunction.addActionListener(keyUseCostFunction);
		chkUseCostFunction.setBounds(6, 41, 886, 25);
		add(chkUseCostFunction);
		chkCalculateGradients.addActionListener(keyCalculateGradients);
		chkCalculateGradients.setBounds(6, 10, 766, 25);
		add(chkCalculateGradients);
		//		chkPerformSingle.setToolTipText("Calculate the Bulk Lattice properties");
		//		chkPerformSingle.setBounds(7, 12, 765, 25);
		//		add(chkPerformSingle);
		chkUseSpatialDecomposition.addActionListener(keyUseSpatialDecomposition);
		chkUseSpatialDecomposition.setBounds(6, 103, 766, 25);
		add(chkUseSpatialDecomposition);
		chkDoNotCalculate.addActionListener(keyDoNotCalculate);
		chkDoNotCalculate.setBounds(6, 72, 766, 25);
		add(chkDoNotCalculate);
		chkUseTheMinimum.addActionListener(keyUseTheMinimum);
		chkUseTheMinimum.setBounds(6, 134, 886, 25);
		add(chkUseTheMinimum);
		chkCalculateMaterial.setBounds(6, 165, 766, 25);
		add(chkCalculateMaterial);
		chkCalculateMaterial.addActionListener(keyCalculateMaterial);
	}
}