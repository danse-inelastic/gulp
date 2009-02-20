package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.md.Integrator;
import javagulp.view.md.Interpolation;
import javagulp.view.md.MDMass;
import javagulp.view.md.MDMassless;
import javagulp.view.md.OutputFormats;
import javagulp.view.md.Pressure;
import javagulp.view.md.Temperature;
import javagulp.view.md.Thermodynamics;
import javagulp.view.md.TimeLengths;
import javagulp.view.md.VectorTable;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class MolecularDynamics extends JPanel implements Serializable {

	private static final long serialVersionUID = -9015449918647439906L;

	private G g = new G();

	private JCheckBox chkMD = new JCheckBox("perform a MD simulation");

	private KeywordListener keyMD = new KeywordListener(chkMD, "md");

	private Temperature pnlTemperature = new Temperature(0);
	public MDMass pnlMDmass = new MDMass();
	private Integrator pnlIntegrator = new Integrator();
	private MDMassless pnlMDmassless = new MDMassless();
	private Interpolation pnlPotentialInterpolation = new Interpolation();
	private Pressure pnlPressure = new Pressure();
	private Thermodynamics pnlThermodynamicEnsembles = new Thermodynamics();
	private VectorTable pnlVectorTable = new VectorTable();

	private TimeLengths pnlTimeLengths = new TimeLengths();
	private OutputFormats pnlOutputFormats = new OutputFormats();

	private String uriJPad = "http://cseobb.hec.utah.edu:18080/JPad/OPENARCH-INF/JPad.xml";

	public MolecularDynamics() {
		super();
		setLayout(null);

		pnlTemperature.setBounds(317, 6, 444, 106);
		add(pnlTemperature);
		pnlTemperature.txtFirstStep.setEnabled(true);
		pnlMDmass.setBounds(790, 119, 399, 118);
		add(pnlMDmass);
		pnlVectorTable.setBounds(317, 243, 503, 103);
		add(pnlVectorTable);
		pnlPressure.setBounds(317, 119, 467, 44);
		add(pnlPressure);
		pnlPotentialInterpolation.setBounds(318, 169, 466, 71);
		add(pnlPotentialInterpolation);
		chkMD.addActionListener(keyMD);
		chkMD.setBounds(4, 6, 307, 25);
		add(chkMD);
		pnlMDmassless.setBounds(767, 6, 422, 106);
		add(pnlMDmassless);
		pnlThermodynamicEnsembles.setBounds(2, 32, 309, 157);
		add(pnlThermodynamicEnsembles);
		pnlIntegrator.setBounds(2, 189, 310, 51);
		add(pnlIntegrator);
		pnlTimeLengths.setBounds(4, 243, 309, 105);
		add(pnlTimeLengths);
		pnlOutputFormats.setBounds(826, 243, 363, 103);
		add(pnlOutputFormats);
	}





	public String writeMD() throws IncompleteOptionException,
			InvalidOptionException {
		//MDRestartInit m;
		//m = Back.getPanel().getMdRestartInit();
		return pnlTimeLengths.writeTime()
				+ pnlOutputFormats.writeMDWriteFrequency()
				+ pnlMDmass.writeShellMassRatio()
				+ pnlVectorTable.writeResetvectors()
				+ pnlPressure.writePressure()
				+ pnlOutputFormats.writeMDarchive()
				+ pnlPotentialInterpolation.writePotentialInterpolation()
				+ pnlMDmassless.writeIterations()
				+ pnlIntegrator.writeIntegrator()
				+ pnlVectorTable.writeExtracutoff()
				+ pnlThermodynamicEnsembles.writeEnsemble()
				//+ m.writeMDRestart() + pnlTemperature.writeTemperature();
				+ pnlTemperature.writeTemperature();
	}
}