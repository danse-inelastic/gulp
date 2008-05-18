package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class BrillouinIntegration extends TitledPanel implements Serializable {

	private static final long serialVersionUID = 3117144370107037990L;

	private class KpointsMesh extends TitledPanel {

		private static final long serialVersionUID = -109851516210611103L;

		private JTextField txtshrinkix = new JTextField();
		private JTextField txtshrinkiy = new JTextField();
		private JTextField txtshrinkiz = new JTextField();

		private JLabel lblX = new JLabel("x");
		private JLabel lblY = new JLabel("y");
		private JLabel lblZ = new JLabel("z");

		private G g = new G();
		private JCheckBox chkDoNotUse = new JCheckBox(g.html("do not use symmetry when reducing <br>the number of kpoints in the mesh"));

		private KeywordListener keyDoNotUse = new KeywordListener(chkDoNotUse, "noksymmetry");

		private KpointsMesh() {
			super();

			txtshrinkix.setBounds(25, 10, 38, 20);
			add(txtshrinkix);
			txtshrinkiy.setBounds(84, 10, 38, 20);
			add(txtshrinkiy);
			txtshrinkiz.setBounds(150, 10, 38, 20);
			add(txtshrinkiz);
			lblX.setBounds(7, 9, 16, 23);
			add(lblX);
			lblY.setBounds(71, 9, 16, 23);
			add(lblY);
			lblZ.setBounds(128, 9, 16, 23);
			add(lblZ);
			chkDoNotUse.addActionListener(keyDoNotUse);
			chkDoNotUse.setBounds(7, 36, 279, 56);
			add(chkDoNotUse);
		}

		private String writeShrink() throws IncompleteOptionException,
				InvalidOptionException {
			String lines = "";
			if (!txtshrinkix.getText().equals("")
					|| !txtshrinkiy.getText().equals("")
					|| !txtshrinkiz.getText().equals("")) {
				if (!txtshrinkix.getText().equals("")
						&& !txtshrinkiy.getText().equals("")
						&& !txtshrinkiz.getText().equals("")) {
					try {
						int shrinkix = Integer.parseInt(txtshrinkix.getText());
						int shrinkiy = Integer.parseInt(txtshrinkiy.getText());
						int shrinkiz = Integer.parseInt(txtshrinkiz.getText());
						if (shrinkix <= 0)
							throw new InvalidOptionException("Phonon shrink x must be >= 0");
						if (shrinkiy <= 0)
							throw new InvalidOptionException("Phonon shrink y must be >= 0");
						if (shrinkiz <= 0)
							throw new InvalidOptionException("Phonon shrink z must be >= 0");
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter a number for Phonon shrink.");
					}
					lines = "shrink " + txtshrinkix.getText() + " "
							+ txtshrinkiy.getText() + " "
							+ txtshrinkiz.getText() + Back.newLine;
				} else {
					throw new IncompleteOptionException("Missing one or more Phonon shrink options.");
				}
			}
			return lines;
		}
	}

	private class ExplicitKpoints extends JPanel {

		private static final long serialVersionUID = -6773882634142073544L;

		private JTextField txtexplicitkx = new JTextField();
		private JTextField txtexplicitky = new JTextField();
		private JTextField txtexplicitkz = new JTextField();
		public final String TXT_EXPLICT_WEIGHT = "1.0";
		private JTextField txtexplicitWeight = new JTextField(TXT_EXPLICT_WEIGHT);

		private JLabel lblexplicitkx = new JLabel("<html>k<sub>x</sub></html>");
		private JLabel lblexplicitky = new JLabel("<html>k<sub>y</sub></html>");
		private JLabel lblexplicitkz = new JLabel("<html>k<sub>z</sub></html>");
		private JLabel lblWeight = new JLabel("weight");

		private JCheckBox chkKpointsAreFor = new JCheckBox("<html>kpoints are for centered cell rather than primitive</html>");

		private KeywordListener keyKpointsAreFor = new KeywordListener(chkKpointsAreFor, "kfull");

		private ExplicitKpoints() {
			super();
			setLayout(null);

			txtexplicitkx.setBounds(33, 21, 63, 19);
			add(txtexplicitkx);
			txtexplicitky.setBounds(124, 21, 63, 19);
			add(txtexplicitky);
			txtexplicitkz.setBounds(215, 21, 63, 19);
			add(txtexplicitkz);
			lblexplicitkx.setBounds(11, 22, 16, 23);
			add(lblexplicitkx);
			lblexplicitky.setBounds(102, 22, 16, 23);
			add(lblexplicitky);
			lblexplicitkz.setBounds(193, 22, 16, 23);
			add(lblexplicitkz);
			txtexplicitWeight.setBackground(Back.grey);
			txtexplicitWeight.setBounds(329, 21, 48, 19);
			add(txtexplicitWeight);
			lblWeight.setBounds(284, 23, 45, 15);
			add(lblWeight);
			chkKpointsAreFor.addActionListener(keyKpointsAreFor);
			chkKpointsAreFor.setBounds(5, 53, 324, 39);
			add(chkKpointsAreFor);
		}

		private String writeKpoints() {
			String lines = "";
			// TODO check documentation for proper format
			if (!txtexplicitkx.getText().equals("")
					&& !txtexplicitky.getText().equals("")
					&& !txtexplicitkz.getText().equals("")) {
				lines = "kpoints " + txtexplicitkx.getText() + " "
						+ txtexplicitky.getText() + " "
						+ txtexplicitkz.getText();
				if (!txtexplicitWeight.getText().equals("")
						&& !txtexplicitkx.getText().equals(TXT_EXPLICT_WEIGHT)) {
					lines += " " + txtexplicitWeight.getText();
				}
				lines += Back.newLine;
			}
			return lines;
		}
	}

	private JTabbedPane paneKointMesh = new JTabbedPane();
	private KpointsMesh pnlKpointsMesh = new KpointsMesh();
	private ExplicitKpoints pnlExplicitKpoints = new ExplicitKpoints();

	public BrillouinIntegration() {
		super();

		paneKointMesh.setBounds(7, 28, 637, 266);
		add(paneKointMesh);
		paneKointMesh.add(pnlKpointsMesh, "kpoint mesh");
		paneKointMesh.add(pnlExplicitKpoints, "explicit kpoints");
		pnlKpointsMesh.setBounds(673, 137, 273, 81);
		pnlExplicitKpoints.setBounds(666, 245, 402, 75);
	}

	public String write() throws IncompleteOptionException,
			InvalidOptionException {
		return pnlExplicitKpoints.writeKpoints() + pnlKpointsMesh.writeShrink();
	}
}