package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
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

import javax.swing.JPanel;

public class MolecularDynamics extends JPanel implements Serializable {

	private static final long serialVersionUID = -9015449918647439906L;

	private final G g = new G();

	//private JCheckBox chkMD = new JCheckBox("perform a MD simulation");

	private final Temperature pnlTemperature = new Temperature(0);
	public MDMass pnlMDmass = new MDMass();
	private final Integrator pnlIntegrator = new Integrator();
	private final MDMassless pnlMDmassless = new MDMassless();
	private final Interpolation pnlPotentialInterpolation = new Interpolation();
	private final Pressure pnlPressure = new Pressure();
	private final Thermodynamics pnlThermodynamicEnsembles = new Thermodynamics();
	private final VectorTable pnlVectorTable = new VectorTable();

	private final TimeLengths pnlTimeLengths = new TimeLengths();
	private final OutputFormats pnlOutputFormats = new OutputFormats();
	private final RestartFile pnlRestartFile = new RestartFile();

	//private String uriJPad = "http://cseobb.hec.utah.edu:18080/JPad/OPENARCH-INF/JPad.xml";

	public MolecularDynamics() {
		super();
		setLayout(null);

		pnlTemperature.setBounds(317, 6, 289, 197);
		add(pnlTemperature);
		pnlTemperature.txtFirstStep.setEnabled(true);
		pnlMDmass.setBounds(618, 111, 503, 118);
		pnlMDmass.cboShellmassSpecies.setBounds(180, 18, 116, 25);
		add(pnlMDmass);
		pnlVectorTable.setBounds(618, 226, 503, 122);
		add(pnlVectorTable);
		pnlPressure.setBounds(317, 204, 289, 87);
		add(pnlPressure);
		pnlPotentialInterpolation.setBounds(618, 346, 503, 71);
		add(pnlPotentialInterpolation);
		//chkMD.addActionListener(keyMD);
		//chkMD.setBounds(4, 6, 307, 25);
		//add(chkMD);
		pnlMDmassless.setBounds(618, 6, 503, 106);
		add(pnlMDmassless);
		pnlThermodynamicEnsembles.setBounds(4, 6, 309, 214);
		add(pnlThermodynamicEnsembles);
		pnlIntegrator.setBounds(317, 297, 289, 85);
		add(pnlIntegrator);
		pnlTimeLengths.setBounds(2, 226, 309, 156);
		add(pnlTimeLengths);
		pnlOutputFormats.setBounds(4, 388, 602, 153);
		add(pnlOutputFormats);
		pnlRestartFile.setBounds(618, 423, 503, 118);
		add(pnlRestartFile);
	}


	public String writeMD() throws IncompleteOptionException,
	InvalidOptionException {
		//MDRestartInit m;
		//m = Back.getPanel().getMdRestartInit();
		return pnlTimeLengths.writeTime()
		+ pnlOutputFormats.writeOutputFormats()
		+ pnlMDmass.writeShellMassRatio()
		+ pnlVectorTable.writeResetvectors()
		+ pnlPressure.writePressure()
		//+ pnlOutputFormats.writeMDarchive()
		+ pnlPotentialInterpolation.writePotentialInterpolation()
		+ pnlMDmassless.writeIterations()
		+ pnlIntegrator.writeIntegrator()
		+ pnlVectorTable.writeExtracutoff()
		+ pnlThermodynamicEnsembles.writeEnsemble()
		+ pnlTemperature.writeTemperature()
		+ pnlRestartFile.writeOption();
	}
}