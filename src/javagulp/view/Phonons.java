package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.phonons.BrillouinIntegration;
import javagulp.view.phonons.Dispersion;
import javagulp.view.phonons.GammaPointCalculation;
import javagulp.view.phonons.OutputFormats;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class Phonons extends TitledPanel implements Serializable {

	private TitledPanel pnlOptions;
	private static final long serialVersionUID = 820917949787670204L;
	private final String TXT_BROADEN_PEAKS = "2.0";
	private final String txt_num_bins = "64";
	private final JTextField txtBroadenPeaks = new JTextField(TXT_BROADEN_PEAKS);
	private final JLabel lblBroadenPeaks = new JLabel("DOS peak-broadening factor");
	private final JTextField txtBinValue = new JTextField(txt_num_bins);
	private final String[] cboNumBoxesOptions = new String[]{"number", "size"};
	private final JComboBox cboNumBins = new JComboBox(new String[] {"DOS number of bins", "DOS bin size (units: 1/cm)"});

	private final G g = new G();

	private final JCheckBox chkDoNotOutput = new JCheckBox(g.html("do not output a list of kpoints at each configuration"));
	private final JCheckBox chkDoNotPrint = new JCheckBox("do not output phonon DOS");
	private final JCheckBox chkDoNotPrintFreqs = new JCheckBox("do not output phonon frequencies");
	private final JCheckBox chkExcludeZeroPoint = new JCheckBox("exclude zero point energy");
	private final JCheckBox chkNoRealSpace = new JCheckBox(g.html("no real space contributions to energy and its derivatives"));
	private final JCheckBox chkNoReciprocalSpace = new JCheckBox(g.html("no reciprocal space contributions to energy and its derivatives"));
	private final JCheckBox chkPrintEigenvectors = new JCheckBox("output eigenvectors in addition to eigenvalues");

	private final KeywordListener keyExcludeZeroPoint = new KeywordListener(chkExcludeZeroPoint, "nozeropt");
	private final KeywordListener keyNoReciprocalSpace = new KeywordListener(chkNoReciprocalSpace, "norecip");
	private final KeywordListener keyNoRealSpace = new KeywordListener(chkNoRealSpace, "noreal");
	private final KeywordListener keyDoNotPrint = new KeywordListener(chkDoNotPrint, "nodensity_out");
	private final KeywordListener keyDoNotPrintFreqs = new KeywordListener(chkDoNotPrintFreqs, "nofrequency");
	private final KeywordListener keyPrintEigenvectors = new KeywordListener(chkPrintEigenvectors, "eigenvectors");
	//private TaskKeywordListener keyCalculateFrequencies = new TaskKeywordListener(chkCalculateFrequencies, "phonon");
	private final KeywordListener keyDoNotOutput = new KeywordListener(chkDoNotOutput, "nokpoints");

	public JTabbedPane paneSpecifyKpoints = new JTabbedPane();

	//private DispersionOptions pnlDispersionOptions = new DispersionOptions();
	public Dispersion pnlDispersion = new Dispersion();
	private final BrillouinIntegration pnlBrillouinIntegration = new BrillouinIntegration();
	private final GammaPointCalculation pnlGammaPoints = new GammaPointCalculation();
	private final RestartFile pnlRestartFile = new RestartFile();
	private final OutputFormats pnlOutputFormats = new OutputFormats();
	//private GammaPointCorrection pnlGammaCorrection = new GammaPointCorrection();

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

		paneSpecifyKpoints.setBounds(0, 7, 654, 444);
		add(paneSpecifyKpoints);
		paneSpecifyKpoints.addTab("Brillouin Zone Integration",
				pnlBrillouinIntegration);
		paneSpecifyKpoints.addTab("Band Structure", pnlDispersion);
		paneSpecifyKpoints.addTab("Gamma Point Calculation", pnlGammaPoints);
		//paneSpecifyKpoints.addTab("Gamma Point Correction", pnlGammaCorrection);
		add(getPanel());

		pnlRestartFile.setBounds(660, 457, 499, 145);
		add(pnlRestartFile);
		pnlOutputFormats.setBounds(0, 457, 654, 145);
		add(pnlOutputFormats);
	}

	private String writeDosBox() throws IncompleteOptionException {
		String lines = "";
		if (cboNumBins.getSelectedIndex()==0){
			if (!txtBinValue.getText().equals(txt_num_bins)) {
				try {
					Integer.parseInt(txtBinValue.getText());
				} catch (final NumberFormatException e) {
					throw new NumberFormatException("Please enter an integer for the number of bins in Phonons");
				}
				lines = "box density " + cboNumBoxesOptions[0] + " "
				+ txtBinValue.getText() + Back.newLine;
			}
		} else {
			if (!txtBinValue.getText().equals("")) {
				try {
					Integer.parseInt(txtBinValue.getText());
				} catch (final NumberFormatException e) {
					throw new NumberFormatException("Please enter an integer for the bin size in Phonons");
				}
				lines = "box density " + cboNumBoxesOptions[1] + " "
				+ txtBinValue.getText() + Back.newLine;
			}
		}
		return lines;
	}

	private String writeBroaden() throws IncompleteOptionException,
	InvalidOptionException {
		String line = "";
		if (!txtBroadenPeaks.getText().equals(TXT_BROADEN_PEAKS)) {
			try {
				final double broaden = Double.parseDouble(txtBroadenPeaks.getText());
				// and > 1?
				if (broaden < 0)
					throw new InvalidOptionException("Phonon broadening must be greater than 0");
			} catch (final NumberFormatException nfe) {
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
		+ pnlOutputFormats.writeOutputFormats()
		+ pnlDispersion.writeDispersion()
		+ pnlGammaPoints.writeGammaPointCorrection()
		+ pnlGammaPoints.writeGammaPointOptions()
		+ pnlBrillouinIntegration.write()
		+ pnlRestartFile.writeOption();
	}

	protected TitledPanel getPanel() {
		if (pnlOptions == null) {
			pnlOptions = new TitledPanel();
			pnlOptions.setBounds(660, 5, 499, 446);
			pnlOptions.setTitle("options");
			chkDoNotPrint.addActionListener(keyDoNotPrint);
			chkDoNotPrint.setBounds(10, 235, 462, 25);
			pnlOptions.add(chkDoNotPrint);
			chkDoNotPrintFreqs.setBounds(10, 266, 462, 25);
			chkDoNotPrintFreqs.addActionListener(keyDoNotPrintFreqs);
			pnlOptions.add(chkDoNotPrintFreqs);
			chkPrintEigenvectors.addActionListener(keyPrintEigenvectors);
			chkPrintEigenvectors.setSelected(true);
			chkPrintEigenvectors.setBounds(10, 80, 462, 25);
			pnlOptions.add(chkPrintEigenvectors);
			chkDoNotOutput.addActionListener(keyDoNotOutput);
			chkDoNotOutput.setBounds(10, 111, 462, 25);
			pnlOptions.add(chkDoNotOutput);
			chkNoRealSpace.addActionListener(keyNoRealSpace);
			chkNoRealSpace.setBounds(10, 142, 462, 25);
			pnlOptions.add(chkNoRealSpace);
			chkNoReciprocalSpace.addActionListener(keyNoReciprocalSpace);
			chkNoReciprocalSpace.setBounds(10, 173, 486, 25);
			pnlOptions.add(chkNoReciprocalSpace);

			chkExcludeZeroPoint.addActionListener(keyExcludeZeroPoint);
			chkExcludeZeroPoint.setBounds(10, 204, 359, 25);
			pnlOptions.add(chkExcludeZeroPoint);
			lblBroadenPeaks.setBounds(108, 47, 291, 25);
			pnlOptions.add(lblBroadenPeaks);

			cboNumBins.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (cboNumBins.getSelectedIndex()==0)
						txtBinValue.setText(txt_num_bins);
					else
						txtBinValue.setText("");
				}
			});
			cboNumBins.setBounds(108, 20, 252, 21);
			pnlOptions.add(cboNumBins);

			txtBroadenPeaks.setBackground(Back.grey);
			txtBinValue.setBackground(Back.grey);
			txtBinValue.setBounds(10, 21, 92, 20);
			pnlOptions.add(txtBinValue);
			txtBroadenPeaks.setBounds(10, 47, 63, 20);
			pnlOptions.add(txtBroadenPeaks);

		}
		return pnlOptions;
	}

}