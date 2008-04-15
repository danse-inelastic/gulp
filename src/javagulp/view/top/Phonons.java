package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utility.misc.G;

public class Phonons extends TitledPanel implements Serializable {

	private static final long serialVersionUID = 820917949787670204L;

	private class DispersionOptions extends JPanel {

		private static final long serialVersionUID = 1389393007405803508L;

		private final String TXT_BROADEN_PEAKS = "2.0";
		private JTextField txtBroadenPeaks = new JTextField(TXT_BROADEN_PEAKS);
		private JTextField txtboxvalue = new JTextField();

		private JCheckBox chkBroadenPeaks = new JCheckBox("broaden the peaks by factor");

		private JComboBox cboBoxType = new JComboBox(new String[] {
				"dispersion curves", "density of states" });
		private JComboBox cboNumBoxes = new JComboBox(new String[] {
				"number of boxes", "box size (1/cm)" });

		private JLabel lblChangeThe = new JLabel("change the");
		private JLabel lblFor = new JLabel("for");

		private DispersionOptions() {
			super();
			setLayout(null);

			setBorder(new TitledBorder(null,
					"density of states and dispersion curve options",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			add(txtBroadenPeaks);
			txtBroadenPeaks.setBackground(Back.grey);
			txtBroadenPeaks.setBounds(211, 23, 63, 20);
			chkBroadenPeaks.setBounds(3, 20, 202, 25);
			add(chkBroadenPeaks);
			lblChangeThe.setBounds(11, 80, 70, 15);
			add(lblChangeThe);
			cboNumBoxes.setBounds(87, 77, 139, 21);
			add(cboNumBoxes);
			lblFor.setBounds(9, 50, 20, 20);
			add(lblFor);
			cboBoxType.setBounds(37, 50, 139, 21);
			add(cboBoxType);
			txtboxvalue.setBounds(232, 78, 77, 19);
			add(txtboxvalue);
		}

		private String writeBox() throws IncompleteOptionException {
			String lines = "";
			if (!txtboxvalue.getText().equals("")) {
				try {
					Integer.parseInt(txtboxvalue.getText());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Please enter an integer for the number of boxes in Phonons");
				}
				lines = "box " + cboBoxType.getSelectedItem() + " "
						+ cboNumBoxes.getSelectedItem() + " "
						+ txtboxvalue.getText() + Back.newLine;
			}
			return lines;
		}

		private String writeBroaden() throws IncompleteOptionException,
				InvalidOptionException {
			String line = "";
			if (chkBroadenPeaks.isSelected()) {
				if (!txtBroadenPeaks.getText().equals("")
						&& !txtBroadenPeaks.getText().equals(TXT_BROADEN_PEAKS)) {
					try {
						double broaden = Double.parseDouble(txtBroadenPeaks.getText());
						// and > 1?
						if (broaden < 0)
							throw new InvalidOptionException("Phonon broadening must be greater than 0");
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter a number for Phonon broadening.");
					}
					line = "broaden_dos " + txtBroadenPeaks.getText() + Back.newLine;
				} else {
					throw new IncompleteOptionException("Please enter something for Phonon broadening.");
				}
			}
			return line;
		}

		private String writeOptions() throws IncompleteOptionException,
				InvalidOptionException {
			return writeBox() + writeBroaden();
		}
	}

	private G g = new G();

	private JCheckBox chkCalculateFrequencies = new JCheckBox("calculate phonon frequencies");
	private JCheckBox chkDoNotOutput = new JCheckBox(g.html("do not output a list of kpoints at each configuration"));
	private JCheckBox chkDoNotPrint = new JCheckBox("do not print phonon DOS to stdout");
	private JCheckBox chkExcludeZeroPoint = new JCheckBox("exclude zero point energy");
	private JCheckBox chkNoRealSpace = new JCheckBox(g.html("no real space contributions to energy and its derivatives"));
	private JCheckBox chkNoReciprocalSpace = new JCheckBox(g.html("no reciprocal space contributions to energy and its derivatives"));
	private JCheckBox chkPrintEigenvectors = new JCheckBox("print eigenvectors in addition to eigenvalues");

	private KeywordListener keyExcludeZeroPoint = new KeywordListener(chkExcludeZeroPoint, "nozeropt");
	private KeywordListener keyNoReciprocalSpace = new KeywordListener(chkNoReciprocalSpace, "norecip");
	private KeywordListener keyNoRealSpace = new KeywordListener(chkNoRealSpace, "noreal");
	private KeywordListener keyDoNotPrint = new KeywordListener(chkDoNotPrint, "nodensity_out");
	private KeywordListener keyPrintEigenvectors = new KeywordListener(chkPrintEigenvectors, "eigenvectors");
	private KeywordListener keyCalculateFrequencies = new KeywordListener(chkCalculateFrequencies, "phonon");
	private KeywordListener keyDoNotOutput = new KeywordListener(chkDoNotOutput, "nokpoints");

	private JTabbedPane paneSpecifyKpoints = new JTabbedPane();

	private DispersionOptions pnlDispersionOptions = new DispersionOptions();
	private Dispersion pnlDispersion = new Dispersion();
	private BrillouinIntegration pnlBrillouinIntegration = new BrillouinIntegration();
	private GammaPointOptions pnlGammaPoints = new GammaPointOptions();
	private GammaPointCorrection pnlGammaCorrection = new GammaPointCorrection();

	public Phonons() {
		super();
		setLayout(null);

		chkExcludeZeroPoint.addActionListener(keyExcludeZeroPoint);
		chkExcludeZeroPoint.setBounds(0, 75, 239, 25);
		add(chkExcludeZeroPoint);
		chkNoReciprocalSpace.addActionListener(keyNoReciprocalSpace);
		chkNoReciprocalSpace.setBounds(0, 135, 319, 42);
		add(chkNoReciprocalSpace);
		chkNoRealSpace.addActionListener(keyNoRealSpace);
		chkNoRealSpace.setBounds(0, 94, 323, 50);
		add(chkNoRealSpace);
		chkDoNotPrint.addActionListener(keyDoNotPrint);
		chkDoNotPrint.setBounds(0, 28, 257, 25);
		add(chkDoNotPrint);
		chkPrintEigenvectors.addActionListener(keyPrintEigenvectors);
		chkPrintEigenvectors.setBounds(0, 52, 323, 25);
		add(chkPrintEigenvectors);

		pnlDispersionOptions.setBounds(0, 226, 330, 109);
		add(pnlDispersionOptions);

		chkCalculateFrequencies.addActionListener(keyCalculateFrequencies);
		chkCalculateFrequencies.setBounds(0, 1, 223, 30);
		add(chkCalculateFrequencies);
		chkDoNotOutput.addActionListener(keyDoNotOutput);
		chkDoNotOutput.setBounds(0, 175, 324, 51);
		add(chkDoNotOutput);

		paneSpecifyKpoints.setBounds(332, 5, 728, 391);
		add(paneSpecifyKpoints);
		paneSpecifyKpoints.addTab("Brillouin Zone Integration",
				pnlBrillouinIntegration);
		paneSpecifyKpoints.addTab("Band Structure", pnlDispersion);
		paneSpecifyKpoints.addTab("Gamma Point Options", pnlGammaPoints);
		paneSpecifyKpoints.addTab("Gamma Point Correction", pnlGammaCorrection);
	}

	public String writePhonon() throws IncompleteOptionException,
			InvalidOptionException {
		return pnlDispersionOptions.writeOptions()
				+ pnlDispersion.writeDispersion()
				+ pnlGammaCorrection.writeGammaPointCorrection()
				+ pnlGammaPoints.writeGammaPointOptions()
				+ pnlBrillouinIntegration.write();
	}
}