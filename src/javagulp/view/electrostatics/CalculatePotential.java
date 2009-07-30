package javagulp.view.electrostatics;

import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

public class CalculatePotential extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -8320177136881534497L;

	private final JLabel lblxMinMax = new JLabel("xmin, xmax");
	private final JLabel lblyMinMax = new JLabel("ymin, ymax");
	private final JLabel lblzMinMax = new JLabel("zmin, zmax");

	private final JLabel lblxPoints = new JLabel("number of x points");
	private final JLabel lblyPoints = new JLabel("number of y points");
	private final JLabel lblzPoints = new JLabel("number of z points");

	private final JTextField txtx = new JTextField();
	private final JTextField txtxmin = new JTextField("0");
	private final JTextField txtxmax = new JTextField("1");

	private final JTextField txty = new JTextField();
	private final JTextField txtymin = new JTextField("0");
	private final JTextField txtymax = new JTextField("1");

	private final JTextField txtz = new JTextField();
	private final JTextField txtzmin = new JTextField("0");
	private final JTextField txtzmax = new JTextField("1");

	public CalculatePotential() {
		super();

		setToolTipText("");
		setTitle("calculate electrostatic potential on a grid");
		lblxMinMax.setBounds(268, 19, 97, 15);
		add(lblxMinMax);
		txtxmin.setBackground(Back.grey);
		txtxmin.setBounds(371, 17, 64, 20);
		add(txtxmin);
		txtxmax.setBackground(Back.grey);
		txtxmax.setBounds(441, 17, 64, 20);
		add(txtxmax);
		txtymin.setBackground(Back.grey);
		txtymin.setBounds(371, 43, 64, 20);
		add(txtymin);
		txtymax.setBackground(Back.grey);
		txtymax.setBounds(441, 43, 64, 20);
		add(txtymax);
		txtzmin.setBackground(Back.grey);
		txtzmin.setBounds(371, 69, 64, 20);
		add(txtzmin);
		txtzmax.setBackground(Back.grey);
		txtzmax.setBounds(441, 69, 64, 20);
		add(txtzmax);
		lblyMinMax.setBounds(268, 45, 97, 15);
		add(lblyMinMax);
		lblzMinMax.setBounds(268, 71, 97, 15);
		add(lblzMinMax);
		lblxPoints.setBounds(10, 19, 151, 15);
		add(lblxPoints);
		txtx.setBounds(167, 17, 64, 20);
		add(txtx);
		txty.setBounds(167, 43, 64, 20);
		add(txty);
		txtz.setBounds(167, 69, 64, 20);
		add(txtz);
		lblyPoints.setBounds(10, 45, 151, 15);
		add(lblyPoints);
		lblzPoints.setBounds(10, 71, 151, 15);
		add(lblzPoints);
	}

	public String writePotgrid() throws IncompleteOptionException {
		String lines = "";
		final JTextField[] fields = {txtx, txty, txtz};
		if (Back.checkAnyNonEmpty(fields)) {
			final String[] descriptions = {"potential grid x", "potential grid y", "potential grid z"};
			Back.checkAllNonEmpty(fields, descriptions);
			Back.parseFieldsI(fields, descriptions);
			lines = "potgrid ";
			final JTextField[] minmax = {txtxmin, txtxmax, txtymin, txtymax, txtzmin, txtzmax};
			// TODO this is not correct.  Cannot use check functions.
			if (Back.checkAnyNonEmpty(minmax)) {
				final String[] minmaxDescs = {"potential grid xmin", "potential grid xmax", "potential grid ymin",
						"potential grid ymax", "potential grid zmin", "potential grid zmax"};
				Back.checkAllNonEmpty(minmax, minmaxDescs);
				final double[] minmaxd = Back.parseFieldsD(minmax, minmaxDescs);
				// TODO check if numbers are valid?
				lines += Back.concatFields(minmax);
			}
			lines += Back.concatFields(fields) + Back.newLine;
		}
		return lines;
	}
}
