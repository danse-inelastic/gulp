package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.phonons.BrillouinIntegration;
import javagulp.view.phonons.Dispersion;
import javagulp.view.phonons.GammaPointCorrection;
import javagulp.view.phonons.GammaPointOptions;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class Phonons extends TitledPanel implements Serializable {


	private TitledPanel pnlOptions;
	private static final long serialVersionUID = 820917949787670204L;
	private final String TXT_BROADEN_PEAKS = "2.0";
	private JTextField txtBroadenPeaks = new JTextField(TXT_BROADEN_PEAKS);
	private JLabel lblBroadenPeaks = new JLabel("DOS peak-broadening factor");
	private JTextField txtboxvalue = new JTextField();
	private String[] cboNumBoxesOptions = new String[]{"number", "size"};
	private JComboBox cboNumBoxes = new JComboBox(new String[] {"DOS number of boxes", "DOS bin size (units: 1/cm)"});


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
	private TaskKeywordListener keyCalculateFrequencies = new TaskKeywordListener(chkCalculateFrequencies, "phonon");
	private KeywordListener keyDoNotOutput = new KeywordListener(chkDoNotOutput, "nokpoints");

	public JTabbedPane paneSpecifyKpoints = new JTabbedPane();

	//private DispersionOptions pnlDispersionOptions = new DispersionOptions();
	public Dispersion pnlDispersion = new Dispersion();
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

		//pnlDispersionOptions.setBounds(5, 42, 482, 109);
		//add(pnlDispersionOptions);

		chkCalculateFrequencies.addActionListener(keyCalculateFrequencies);
		chkCalculateFrequencies.setBounds(5, 5, 359, 30);
		add(chkCalculateFrequencies);



		paneSpecifyKpoints.setBounds(493, 9, 611, 395);
		add(paneSpecifyKpoints);
		paneSpecifyKpoints.addTab("Brillouin Zone Integration",
				pnlBrillouinIntegration);
		paneSpecifyKpoints.addTab("Band Structure", pnlDispersion);
		paneSpecifyKpoints.addTab("Gamma Point Options", pnlGammaPoints);
		paneSpecifyKpoints.addTab("Gamma Point Correction", pnlGammaCorrection);
		add(getPanel());
	}

	private String writeDosBox() throws IncompleteOptionException {
		String lines = "";
		if (!txtboxvalue.getText().equals("")) {
			try {
				Integer.parseInt(txtboxvalue.getText());
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Please enter an integer for the number of boxes in Phonons");
			}
			lines = "box density " + cboNumBoxesOptions[cboNumBoxes.getSelectedIndex()] + " "
			+ txtboxvalue.getText() + Back.newLine;
		}
		return lines;
	}

	private String writeBroaden() throws IncompleteOptionException,
	InvalidOptionException {
		String line = "";
		if (!txtBroadenPeaks.getText().equals(TXT_BROADEN_PEAKS)) {
			try {
				double broaden = Double.parseDouble(txtBroadenPeaks.getText());
				// and > 1?
				if (broaden < 0)
					throw new InvalidOptionException("Phonon broadening must be greater than 0");
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for Phonon broadening.");
			}
			line = "broaden_dos " + txtBroadenPeaks.getText() + Back.newLine;
		} //else {
//			throw new IncompleteOptionException("Please enter something for Phonon broadening.");
//		}
		return line;
	}

	public String writeDosOptions() throws IncompleteOptionException,
	InvalidOptionException {
		return writeDosBox() + writeBroaden();
	}

	public String writePhonon() throws IncompleteOptionException,
	InvalidOptionException {
		return writeDosOptions()
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
			pnlOptions.setBounds(5, 41, 482, 363);
			pnlOptions.setTitle("options");
			chkDoNotPrint.addActionListener(keyDoNotPrint);
			chkDoNotPrint.setBounds(10, 49, 462, 25);
			pnlOptions.add(chkDoNotPrint);
			chkDoNotPrintFreqs.setBounds(10, 18, 462, 25);
			chkDoNotPrintFreqs.addActionListener(keyDoNotPrintFreqs);
			pnlOptions.add(chkDoNotPrintFreqs);
			chkPrintEigenvectors.addActionListener(keyPrintEigenvectors);
			chkPrintEigenvectors.setBounds(10, 80, 462, 25);
			pnlOptions.add(chkPrintEigenvectors);
			chkDoNotOutput.addActionListener(keyDoNotOutput);
			chkDoNotOutput.setBounds(10, 111, 462, 25);
			pnlOptions.add(chkDoNotOutput);
			chkNoRealSpace.addActionListener(keyNoRealSpace);
			chkNoRealSpace.setBounds(10, 142, 462, 25);
			pnlOptions.add(chkNoRealSpace);
			chkNoReciprocalSpace.addActionListener(keyNoReciprocalSpace);
			chkNoReciprocalSpace.setBounds(10, 173, 462, 25);
			pnlOptions.add(chkNoReciprocalSpace);
			
			chkExcludeZeroPoint.addActionListener(keyExcludeZeroPoint);
			chkExcludeZeroPoint.setBounds(10, 204, 359, 25);
			pnlOptions.add(chkExcludeZeroPoint);
			lblBroadenPeaks.setBounds(79, 232, 291, 25);
			pnlOptions.add(lblBroadenPeaks);

			cboNumBoxes.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (txtboxvalue.getText().equals("number of boxes"))
						txtboxvalue.setText("64");
					else
						txtboxvalue.setText("");
				}
			});
			cboNumBoxes.setBounds(117, 268, 252, 21);
			pnlOptions.add(cboNumBoxes);

			txtBroadenPeaks.setBackground(Back.grey);
			txtboxvalue.setBounds(10, 269, 92, 19);
			pnlOptions.add(txtboxvalue);
			txtBroadenPeaks.setBounds(10, 235, 63, 20);
			pnlOptions.add(txtBroadenPeaks);
			
		}
		return pnlOptions;
	}

}