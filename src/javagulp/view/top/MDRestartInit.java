package javagulp.view.top;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.TreeSet;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;
import utility.misc.Nutpad;
import utility.misc.SerialListener;

public class MDRestartInit extends JPanel implements Serializable {

	private static final long serialVersionUID = -5513636720207314810L;

	private G g = new G();

	public DefaultComboBoxModel plist1 = new DefaultComboBoxModel();
	private DefaultListModel CoordlistFixed = new DefaultListModel();
	private TreeSet<Integer> fixedAtomslist = new TreeSet<Integer>();

	private JTextField txtcurrenttime = new JTextField();

	private JLabel lblstartingpoint = new JLabel("starting point in previous simulation (ps)");

	private TitledPanel pnlrestarttime = new TitledPanel();
	private RestartConstraint pnlrestartconstraint = new RestartConstraint();
	private RestartCellAverages pnlrestartcellaverages = new RestartCellAverages();
	private RestartAverages pnlRestartAverages = new RestartAverages();
	private MDInit pnlMDInitialization = new MDInit();

	private class RestartConstraint extends TitledPanel {

		private static final long serialVersionUID = -1429809272279764418L;

		private JLabel lblLambdaR = new JLabel(g.html("sum " + g.lambda
				+ "<sub>R</sub> (distance constraint)"));
		private JLabel lblLambdaV = new JLabel("<html>sum " + g.lambda
				+ "<sub>V</sub> (velocity constraint)</html>");

		private JTextField txtcfaverlambdaR = new JTextField();
		private JTextField txtcfaverlambdaV = new JTextField();

		private RestartConstraint() {
			super();

			setTitle("restart constraint forces (velocity verlet)");
			lblLambdaR.setBounds(8, 22, 180, 20);
			add(lblLambdaR);
			txtcfaverlambdaR.setBounds(194, 20, 67, 20);
			add(txtcfaverlambdaR);
			lblLambdaV.setBounds(8, 45, 175, 20);
			add(lblLambdaV);
			txtcfaverlambdaV.setBounds(194, 43, 67, 20);
			add(txtcfaverlambdaV);
		}

		private String writeCfaver() throws IncompleteOptionException {
			String lines = "";
			if (!txtcfaverlambdaR.getText().equals("")
					|| !txtcfaverlambdaV.getText().equals("")) {
				if (!txtcfaverlambdaR.getText().equals("")
						&& !txtcfaverlambdaV.getText().equals("")) {
					try {
						Double.parseDouble(txtcfaverlambdaR.getText());
						Double.parseDouble(txtcfaverlambdaR.getText());
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Missing one or more MD Restart constraint force averages.");
					}
					lines = "cfaver " + txtcfaverlambdaR.getText() + " "
							+ txtcfaverlambdaV.getText() + Back.newLine;
				} else {
					throw new IncompleteOptionException("Missing one or more MD Restart constraint force averages");
				}
			}
			return lines;
		}
	}

	private class MDInit extends TitledPanel {

		private static final long serialVersionUID = -1147518318653986397L;

		private JButton btnInitVelocities = new JButton("initialize velocities");
		private JButton btnInitAccelerations = new JButton("initialize accelerations");
		private JCheckBox chkCausesTheInitial = new JCheckBox("<html>initialise the velocites of atoms within a <br>molecule to the same average value, resulting in <br>no internal kinetic energy</html>");

		private KeywordListener keyCausesTheInitial = new KeywordListener(chkCausesTheInitial, "nomolecularinternalke");

		private MDInit() {
			super();

			setBorder(new TitledBorder(null, "md initialization",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			add(btnInitVelocities);
			btnInitVelocities.addActionListener(new SerialListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -7657999873128305297L;

				@Override
				public void actionPerformed(ActionEvent e) {
					// "option velocities"
				}
			});
			add(btnInitAccelerations);
			btnInitAccelerations.addActionListener(new SerialListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 7870398791242074863L;

				@Override
				public void actionPerformed(ActionEvent e) {
					// "option accelerations"
				}
			});
			add(chkCausesTheInitial);
			chkCausesTheInitial.addActionListener(keyCausesTheInitial);
		}

		// TODO This doesn't work at all. See if gulp outputs acceleration and
		// velocity information, and if so, import it.

		private void openJPad(JTextArea jTextArea) {
			Nutpad nut = new Nutpad(jTextArea.getText());
			nut.setLocationRelativeTo(null);
			nut.pack();
			nut.setVisible(true);
		}
	}

	private class RestartAverages extends TitledPanel {

		private static final long serialVersionUID = 7198146655359005133L;

		private JLabel lblsumv2 = new JLabel("<html>sum v<sup>2</sup></html>");
		private JLabel lblsumofenergies = new JLabel("sum of energies");
		private JLabel lblsumofvirials = new JLabel("sum of virials");
		private JLabel lblsumoftemperatures = new JLabel("sum of temperatures");
		private JLabel lblsumofconstraints = new JLabel("sum of constraints");
		private JLabel lblsumofconstants = new JLabel("sum of constants");
		private JLabel lblaveragingpoints = new JLabel("number of averaging points");

		private JTextField txtaverv2 = new JTextField();
		private JTextField txtaverenergies = new JTextField();
		private JTextField txtavervirials = new JTextField();
		private JTextField txtavertemp = new JTextField();
		private JTextField txtaverconstraints = new JTextField();
		private JTextField txtaverconstants = new JTextField();
		private JTextField txtavernumPoints = new JTextField();

		private RestartAverages() {
			super();

			setTitle("restart averages");
			lblsumv2.setBounds(8, 19, 45, 20);
			add(lblsumv2);
			txtaverv2.setBounds(111, 22, 67, 20);
			add(txtaverv2);
			txtaverenergies.setBounds(111, 43, 67, 20);
			add(txtaverenergies);
			lblsumofenergies.setBounds(8, 45, 105, 15);
			add(lblsumofenergies);
			lblsumofvirials.setBounds(8, 66, 90, 15);
			add(lblsumofvirials);
			lblsumoftemperatures.setBounds(184, 24, 135, 15);
			add(lblsumoftemperatures);
			lblsumofconstraints.setBounds(184, 45, 120, 15);
			add(lblsumofconstraints);
			lblsumofconstants.setBounds(184, 66, 110, 15);
			add(lblsumofconstants);
			lblaveragingpoints.setBounds(144, 89, 175, 15);
			add(lblaveragingpoints);
			txtavervirials.setBounds(111, 64, 67, 20);
			add(txtavervirials);
			txtavertemp.setBounds(325, 19, 67, 20);
			add(txtavertemp);
			txtaverconstraints.setBounds(325, 43, 67, 20);
			add(txtaverconstraints);
			txtaverconstants.setBounds(325, 64, 67, 20);
			add(txtaverconstants);
			txtavernumPoints.setBounds(325, 87, 67, 20);
			add(txtavernumPoints);
		}

		private String writeAver() throws IncompleteOptionException {
			String lines = "";
			if (!txtaverv2.getText().equals("")
					|| !txtaverenergies.getText().equals("")
					|| !txtavervirials.getText().equals("")
					|| !txtavertemp.getText().equals("")
					|| !txtaverconstraints.getText().equals("")
					|| !txtaverconstants.getText().equals("")
					|| !txtavernumPoints.getText().equals("")) {
				if (!txtaverv2.getText().equals("")
						&& !txtaverenergies.getText().equals("")
						&& !txtavervirials.getText().equals("")
						&& !txtavertemp.getText().equals("")
						&& !txtaverconstraints.getText().equals("")
						&& !txtaverconstants.getText().equals("")
						&& !txtavernumPoints.getText().equals("")) {
					try {
						Double.parseDouble(txtaverv2.getText());
						Double.parseDouble(txtaverenergies.getText());
						Double.parseDouble(txtavervirials.getText());
						Double.parseDouble(txtavertemp.getText());
						Double.parseDouble(txtaverconstraints.getText());
						Double.parseDouble(txtaverconstants.getText());
						Double.parseDouble(txtavernumPoints.getText());
						// TODO check if all are > 0
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter numbers for MD Restart averages.");
					}
					lines = "aver " + txtaverv2.getText() + " "
							+ txtaverenergies.getText() + " "
							+ txtavervirials.getText() + " "
							+ txtavertemp.getText() + " "
							+ txtaverconstraints.getText() + " "
							+ txtaverconstants.getText() + " "
							+ txtavernumPoints.getText() + Back.newLine;
				} else {
					throw new IncompleteOptionException("Missing some averages in MD Restart");
				}
			}
			return lines;
		}
	}

	private class RestartCellAverages extends TitledPanel {

		private static final long serialVersionUID = 5722892872444383639L;

		private JLabel lblsuma = new JLabel("sum a");
		private JLabel lblsumb = new JLabel("sum b");
		private JLabel lblsumc = new JLabel("sum c");
		private JLabel lblsumalpha = new JLabel(g.html("sum " + g.alpha));
		private JLabel lblsumbeta = new JLabel(g.html("sum " + g.beta));
		private JLabel lblsumgamma = new JLabel(g.html("sum " + g.gamma));
		private JLabel lblsumcellvolumes = new JLabel("sum cell volumes");

		private JTextField txtcavera = new JTextField();
		private JTextField txtcaverb = new JTextField();
		private JTextField txtcaverc = new JTextField();
		private JTextField txtcaveralpha = new JTextField();
		private JTextField txtcaverbeta = new JTextField();
		private JTextField txtcavergamma = new JTextField();
		private JTextField txtcavercellvolume = new JTextField();

		private RestartCellAverages() {
			super();
			setTitle("restart cell averages");

			lblsuma.setBounds(8, 21, 40, 15);
			add(lblsuma);
			lblsumb.setBounds(8, 43, 40, 15);
			add(lblsumb);
			lblsumc.setBounds(8, 64, 40, 15);
			add(lblsumc);
			lblsumalpha.setBounds(127, 21, 65, 15);
			add(lblsumalpha);
			lblsumbeta.setBounds(127, 43, 60, 15);
			add(lblsumbeta);
			lblsumgamma.setBounds(127, 64, 80, 15);
			add(lblsumgamma);
			lblsumcellvolumes.setBounds(127, 85, 110, 15);
			add(lblsumcellvolumes);

			txtcavera.setBounds(54, 19, 67, 20);
			add(txtcavera);
			txtcaverb.setBounds(54, 41, 67, 20);
			add(txtcaverb);
			txtcaverc.setBounds(54, 62, 67, 20);
			add(txtcaverc);
			txtcaveralpha.setBounds(253, 19, 67, 20);
			add(txtcaveralpha);
			txtcaverbeta.setBounds(253, 41, 67, 20);
			add(txtcaverbeta);
			txtcavergamma.setBounds(253, 62, 67, 20);
			add(txtcavergamma);
			txtcavercellvolume.setBounds(253, 83, 67, 20);
			add(txtcavercellvolume);
		}

		private String writeCaver() throws IncompleteOptionException {
			String lines = "";
			if (!txtcavera.getText().equals("")
					|| !txtcaverb.getText().equals("")
					|| !txtcaverc.getText().equals("")
					|| !txtcaveralpha.getText().equals("")
					|| !txtcaverbeta.getText().equals("")
					|| !txtcavergamma.getText().equals("")
					|| !txtcavercellvolume.getText().equals("")) {
				if (!txtcavera.getText().equals("")
						&& !txtcaverb.getText().equals("")
						&& !txtcaverc.getText().equals("")
						&& !txtcaveralpha.getText().equals("")
						&& !txtcaverbeta.getText().equals("")
						&& !txtcavergamma.getText().equals("")
						&& !txtcavercellvolume.getText().equals("")) {
					try {
						Double.parseDouble(txtcavera.getText());
						Double.parseDouble(txtcaverb.getText());
						Double.parseDouble(txtcaverc.getText());
						Double.parseDouble(txtcaveralpha.getText());
						Double.parseDouble(txtcaverbeta.getText());
						Double.parseDouble(txtcavergamma.getText());
						Double.parseDouble(txtcavercellvolume.getText());
						// TODO check that a,b,c are > 0 and angles are
						// reasonable
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter numbers for MD Restart cumulative averages.");
					}
					lines = "caver " + txtcavera.getText() + " "
							+ txtcaverb.getText() + " " + txtcaverc.getText()
							+ " " + txtcaveralpha.getText() + " "
							+ txtcaverbeta.getText() + " "
							+ txtcavergamma.getText() + " "
							+ txtcavercellvolume.getText() + Back.newLine;
				} else {
					throw new IncompleteOptionException("Missing one or more MD Restart cumulative averages.");
				}
			}
			return lines;
		}
	}

	public MDRestartInit() {
		super();
		setLayout(null);

		pnlrestarttime.setTitle("restart time");
		pnlrestarttime.setBounds(10, 10, 398, 67);
		add(pnlrestarttime);

		txtcurrenttime.setBounds(270, 18, 98, 19);
		pnlrestarttime.add(txtcurrenttime);

		lblstartingpoint.setBounds(4, 20, 260, 15);
		pnlrestarttime.add(lblstartingpoint);

		pnlrestartconstraint.setBounds(414, 10, 398, 71);
		add(pnlrestartconstraint);

		pnlrestartcellaverages.setBounds(10, 83, 398, 109);
		add(pnlrestartcellaverages);

		pnlRestartAverages.setBounds(10, 198, 398, 119);
		add(pnlRestartAverages);

		pnlMDInitialization.setBounds(414, 87, 360, 115);
		// removed for paper
		// add(pnlMDInitialization);
	}

	private String writeCurrentTime() {
		String lines = "";
		if (!txtcurrenttime.getText().equals("")) {
			lines = "current_time " + txtcurrenttime.getText() + Back.newLine;
		}
		return lines;
	}

	public String writeMDRestart() throws IncompleteOptionException {
		return pnlrestartconstraint.writeCfaver()
				+ pnlrestartcellaverages.writeCaver()
				+ pnlRestartAverages.writeAver() + writeCurrentTime();
	}
}