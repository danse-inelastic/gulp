package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.phonons.BrillouinIntegration;
import javagulp.view.phonons.Dispersion;
import javagulp.view.phonons.DispersionOptions;
import javagulp.view.phonons.GammaPointCorrection;
import javagulp.view.phonons.GammaPointOptions;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class Phonons extends TitledPanel implements Serializable {

	
	private TitledPanel pnlOptions;
	private static final long serialVersionUID = 820917949787670204L;


	private G g = new G();

	private JCheckBox chkCalculateFrequencies = new JCheckBox("calculate phonon frequencies");
	private JCheckBox chkDoNotOutput = new JCheckBox(g.html("do not output a list of kpoints at each configuration"));
	private JCheckBox chkDoNotPrint = new JCheckBox("do not output phonon DOS");
	private JCheckBox chkDoNotPrintFreqs = new JCheckBox("do not output phonon frequencies");
	private JCheckBox chkExcludeZeroPoint = new JCheckBox("exclude zero point energy");
	private JCheckBox chkNoRealSpace = new JCheckBox(g.html("no real space contributions to energy and its derivatives"));
	private JCheckBox chkNoReciprocalSpace = new JCheckBox(g.html("no reciprocal space contributions to energy and its derivatives"));
	private JCheckBox chkPrintEigenvectors = new JCheckBox("output eigenvectors in addition to eigenvalues");

	private KeywordListener keyExcludeZeroPoint = new KeywordListener(chkExcludeZeroPoint, "nozeropt");
	private KeywordListener keyNoReciprocalSpace = new KeywordListener(chkNoReciprocalSpace, "norecip");
	private KeywordListener keyNoRealSpace = new KeywordListener(chkNoRealSpace, "noreal");
	private KeywordListener keyDoNotPrint = new KeywordListener(chkDoNotPrint, "nodensity_out");
	private KeywordListener keyDoNotPrintFreqs = new KeywordListener(chkDoNotPrintFreqs, "nofrequency");
	private KeywordListener keyPrintEigenvectors = new KeywordListener(chkPrintEigenvectors, "eigenvectors");
	private KeywordListener keyCalculateFrequencies = new KeywordListener(chkCalculateFrequencies, "phonon");
	private KeywordListener keyDoNotOutput = new KeywordListener(chkDoNotOutput, "nokpoints");

	private JTabbedPane paneSpecifyKpoints = new JTabbedPane();

	private DispersionOptions pnlDispersionOptions = new DispersionOptions();
	private Dispersion pnlDispersion = new Dispersion();
	private BrillouinIntegration pnlBrillouinIntegration = new BrillouinIntegration();
	private GammaPointOptions pnlGammaPoints = new GammaPointOptions();
	private GammaPointCorrection pnlGammaCorrection = new GammaPointCorrection();

//	/**
//	 * @return
//	 */
//	protected JCheckBox getChkDoNotPrintFreq() {
//		if (chkDoNotPrintFreqs == null) {
//			chkDoNotPrintFreqs = 
//			chkDoNotPrintFreqs.setText("do not output phonon frequencies");
//			chkDoNotPrintFreqs.setBounds(10, 18, 425, 25);
//			chkDoNotPrintFreqs.addActionListener(keyDoNotPrintFreqs);
//		}
//		return chkDoNotPrintFreqs;
//	}
	
	public Phonons() {
		super();
		setLayout(null);

		pnlDispersionOptions.setBounds(5, 42, 482, 109);
		add(pnlDispersionOptions);

		chkCalculateFrequencies.addActionListener(keyCalculateFrequencies);
		chkCalculateFrequencies.setBounds(5, 5, 359, 30);
		add(chkCalculateFrequencies);
		

		
		paneSpecifyKpoints.setBounds(493, 9, 572, 395);
		add(paneSpecifyKpoints);
		paneSpecifyKpoints.addTab("Brillouin Zone Integration",
				pnlBrillouinIntegration);
		paneSpecifyKpoints.addTab("Band Structure", pnlDispersion);
		paneSpecifyKpoints.addTab("Gamma Point Options", pnlGammaPoints);
		paneSpecifyKpoints.addTab("Gamma Point Correction", pnlGammaCorrection);
		add(getPanel());
	}

	public String writePhonon() throws IncompleteOptionException,
			InvalidOptionException {
		return pnlDispersionOptions.writeOptions()
				+ pnlDispersion.writeDispersion()
				+ pnlGammaCorrection.writeGammaPointCorrection()
				+ pnlGammaPoints.writeGammaPointOptions()
				+ pnlBrillouinIntegration.write();
	}
	/**
	 * @return
	 */
	protected TitledPanel getPanel() {
		if (pnlOptions == null) {
			pnlOptions = new TitledPanel();
			pnlOptions.setBounds(5, 157, 482, 247);
			pnlOptions.setTitle("options");
			chkDoNotPrint.addActionListener(keyDoNotPrint);
			chkDoNotPrint.setBounds(10, 49, 425, 25);
			pnlOptions.add(chkDoNotPrint);
			chkDoNotPrintFreqs.setBounds(10, 18, 425, 25);
			chkDoNotPrintFreqs.addActionListener(keyDoNotPrintFreqs);
			pnlOptions.add(chkDoNotPrintFreqs);
			chkPrintEigenvectors.addActionListener(keyPrintEigenvectors);
			chkPrintEigenvectors.setBounds(10, 80, 359, 25);
			pnlOptions.add(chkPrintEigenvectors);
			chkDoNotOutput.addActionListener(keyDoNotOutput);
			chkDoNotOutput.setBounds(10, 111, 359, 25);
			pnlOptions.add(chkDoNotOutput);
			chkNoRealSpace.addActionListener(keyNoRealSpace);
			chkNoRealSpace.setBounds(10, 142, 469, 25);
			pnlOptions.add(chkNoRealSpace);
			chkNoReciprocalSpace.addActionListener(keyNoReciprocalSpace);
			chkNoReciprocalSpace.setBounds(10, 173, 452, 25);
			pnlOptions.add(chkNoReciprocalSpace);

			chkExcludeZeroPoint.addActionListener(keyExcludeZeroPoint);
			chkExcludeZeroPoint.setBounds(10, 204, 359, 25);
			pnlOptions.add(chkExcludeZeroPoint);
		}
		return pnlOptions;
	}

}