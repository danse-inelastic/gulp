package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.view.md.Temperature;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FreeEnergy extends JPanel implements Serializable {

	private static final long serialVersionUID = 780810858009015977L;

	private final G g = new G();

	private final JCheckBox chkUseTheZero = new JCheckBox("use the zero static internal stress approximation");
	//private JCheckBox chkFreeEnergy = new JCheckBox("<html>use Gibbs free-energy as the quantity to be calculated/optimized instead of the internal energy</html>");
	private final JCheckBox chkRunStaticOptimisation = new JCheckBox("run static optimization before free energy optimization");

	private final KeywordListener keyUseTheZero = new KeywordListener(chkUseTheZero, "zsisa");
	//private TaskKeywordListener keyFreeEnergy = new TaskKeywordListener(chkFreeEnergy, "free_energy");
	private final KeywordListener keyRunStaticOptimisation = new KeywordListener(chkRunStaticOptimisation, "static_first");

	private final JLabel lblDensityCutoff = new JLabel("(multiple of density cutoff)");
	private final JLabel lblHighestBand = new JLabel("highest band (mode number)");
	private final JLabel lblLowestBand = new JLabel("lowest band (mode number)");
	private final JLabel lblMaximumSearchRange = new JLabel(g.html("maximum search range for pairs of atoms interacting via the same many body term that gives a contribution to the third derivatives"));

	private final TitledPanel pnlFreeEnergy = new TitledPanel();
	private final TitledPanel pnlMaxRange = new TitledPanel();
	private final Temperature pnlTemperature = new Temperature(0);

	private final JTextField txtlowest_modehigh = new JTextField();
	private final JTextField txtlowest_modelow = new JTextField();
	private final JTextField txtscmaxsearch = new JTextField("2.0");

	public FreeEnergy() {
		super();
		setLayout(null);

		//final TitledPanel panel = new TitledPanel();
		//panel.setBounds(0, 0, 1070, 425);
		//add(panel);

		//		chkFreeEnergy.setBounds(6, 10, 452, 47);
		//		//panel.add(chkFreeEnergy);
		//		add(chkFreeEnergy);
		//		chkFreeEnergy.addActionListener(keyFreeEnergy);
		chkUseTheZero.setBounds(7, 10, 419, 30);
		//panel.add(chkUseTheZero);
		add(chkUseTheZero);
		chkUseTheZero.addActionListener(keyUseTheZero);
		chkRunStaticOptimisation.setBounds(7, 46, 451, 25);
		add(chkRunStaticOptimisation);
		//panel.add(chkRunStaticOptimisation);
		chkRunStaticOptimisation.addActionListener(keyRunStaticOptimisation);

		pnlTemperature.setBounds(461, 88, 398, 193);
		//panel.add(pnlTemperature);
		add(pnlTemperature);

		//panel.add(pnlMaxRange);
		add(pnlMaxRange);
		pnlMaxRange.setTitle("maximum search range");
		pnlMaxRange.setBounds(7, 77, 448, 134);
		pnlMaxRange.setToolTipText("<html>For free energy minimisation, parameter sets the maximum<br>"
				+ "search range for pairs of atoms interacting via the same many<br>"
				+ "body term that gives a contribution to the third derivatives.<br>"
				+ "The value is a multiple of the density cut-off value for the<br>"
				+ "EAM model. In principle, the range can be up to 3 times the<br>"
				+ "density pairwise cut-off. However, makes free energy<br>"
				+ "minimisation very expensive. In practice, a value of around<br>"
				+ "2 will give almost identical results, depending on the system,<br>"
				+ "with a dramatic increase in speed. However, if precise gradients<br>"
				+ "are needed then a value of 3.0 should be used to check<br>"
				+ "the influence. Negative values and values greater than 3.0 are<br>"
				+ "disallowed as being stupid!</html>");
		txtscmaxsearch.setBounds(222, 92, 87, 20);
		pnlMaxRange.add(txtscmaxsearch);
		lblMaximumSearchRange.setBounds(10, 24, 426, 62);
		pnlMaxRange.add(lblMaximumSearchRange);
		lblDensityCutoff.setBounds(10, 92, 209, 15);
		pnlMaxRange.add(lblDensityCutoff);

		//panel.add(pnlFreeEnergy);
		add(pnlFreeEnergy);
		pnlFreeEnergy.setTitle("free energy from band of frequencies");
		pnlFreeEnergy.setBounds(464, 10, 398, 72);
		lblLowestBand.setBounds(10, 22, 244, 15);
		pnlFreeEnergy.add(lblLowestBand);
		lblHighestBand.setBounds(10, 43, 244, 15);
		pnlFreeEnergy.add(lblHighestBand);
		txtlowest_modelow.setBounds(260, 20, 63, 19);
		pnlFreeEnergy.add(txtlowest_modelow);
		txtlowest_modehigh.setBackground(Back.grey);
		txtlowest_modehigh.setBounds(260, 41, 63, 19);
		pnlFreeEnergy.add(txtlowest_modehigh);
	}

	private String writeLowestMode() throws IncompleteOptionException {
		String lines = "";
		final String low = txtlowest_modelow.getText(), high = txtlowest_modehigh.getText();
		if (!low.equals("")) {
			Integer.parseInt(low);
			lines = "lowest_mode " + low;
			if (!high.equals("")) {
				Integer.parseInt(high);
				lines += " " + high;
			}
			lines += Back.newLine;
		}
		return lines;
	}

	private String writeScMaxSearch() throws InvalidOptionException {
		String lines = "";
		final String max = txtscmaxsearch.getText();
		if (!max.equals("") && !max.equals("2.0")) {
			final double number = Double.parseDouble(max);
			if (number < 0 || number > 3)
				throw new InvalidOptionException("Please enter a value between 0.0 and 3.0 for free energy maximum search range.");
			lines = "scmaxsearch " + max + Back.newLine;
		}
		return lines;
	}

	public String writeFreeEnergy() throws IncompleteOptionException, InvalidOptionException {
		return pnlTemperature.writeTemperature() + writeLowestMode() + writeScMaxSearch();
	}
}