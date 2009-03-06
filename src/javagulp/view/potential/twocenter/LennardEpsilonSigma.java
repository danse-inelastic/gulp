package javagulp.view.potential.twocenter;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class LennardEpsilonSigma extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 17817067200197020L;

	private G g = new G();

	private String txtMinimum = g.html("c<sub>1</sub> = (n/(m-n))<br>c<sub>2</sub> = (m/(m-n))");
	private String txtZero = g.html("c<sub>1</sub> = (n/(m-n))(m/n)<sup>m/(m-n)</sup><br>c<sub>2</sub> = (m/(m-n))(m/n)<sup>n/(m-n)</sup></html>");

	private ButtonGroup buttonGroup = new ButtonGroup();
	public JRadioButton minimum = new JRadioButton("distance of potential energy minimum");
	public JRadioButton zero = new JRadioButton("distance at which the potential goes to zero");

	public JComboBox cboEpsSigParameters = new JComboBox(new String[] { "manual input", "combination rules",
					"geometric combination rules" });

	public PPP Epsilon = new PPP(g.html(g.epsilon + " (eV)"));
	public PPP Sigma = new PPP(g.html(g.sigma + " (" + g.ang + ")"));
	
	private JLabel defineSigmaAsLabel = new JLabel(g.html("define " + g.sigma + " as"));
	private JLabel jwAB_2_1 = new JLabel(g.html(g.epsilon + " = 2 sqrt("
			+ g.epsilon + "<sub>1</sub> " + g.epsilon + "<sub>2</sub>)" + "("
			+ g.sigma + "<sub>1</sub> " + g.sigma
			+ "<sub>2</sub>)<sup>3</sup>/(" + g.sigma
			+ "<sub>1</sub><sup>6</sup>" + " + " + g.sigma
			+ "<sub>1</sub><sup>6</sup>) <br>" + g.sigma + " = ((" + g.sigma
			+ "<sub>1</sub><sup>6</sup> + " + g.sigma
			+ "<sub>1</sub><sup>6</sup>)/2)<sup>1/6</sup>"));
	private JLabel jwAB_3_1 = new JLabel(g.html(g.epsilon + " = sqrt("
			+ g.epsilon + "<sub>1</sub> " + g.epsilon + "<sub>3</sub>) <br>"
			+ g.sigma + " = (" + g.sigma + "<sub>1</sub> + " + g.sigma
			+ "<sub>2</sub>)/2"));
	private JLabel lblEpsSig = new JLabel(g.html("E = " + g.epsilon
			+ "(c<sub>1</sub>(" + g.sigma + "/r)<sup>m</sup> - c<sub>2</sub>("
			+ g.sigma + "/r)<sup>n</sup>)"));
	private JLabel lblEquation = new JLabel(txtMinimum);
	private JLabel lblSetParameters = new JLabel("set parameters by");

	private SerialListener listener = new SerialListener() {
		private static final long serialVersionUID = 7545795612343262301L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (minimum.isSelected())
				lblEquation.setText(txtMinimum);
			if (zero.isSelected())
				lblEquation.setText(txtZero);
		}
	};

	private JPanel combinationRules = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private JPanel geometricCombinationRules = new JPanel();
	private JPanel manualInputES = new JPanel();
	
	private SerialListener keyEpsSigParameters = new SerialListener() {
		private static final long serialVersionUID = 9127812535462595754L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Lennard l = (Lennard) getParent().getParent();
				switch (cboEpsSigParameters.getSelectedIndex()) {
				case 0:
					scrollPane.setViewportView(manualInputES);
					l.txtM.setText("12");
					break;
				case 1:
					scrollPane.setViewportView(combinationRules);
					l.txtM.setText("12");
					break;
				case 2:
					scrollPane.setViewportView(geometricCombinationRules);
					l.txtM.setText("12");
					break;
				}
			} catch (NullPointerException npe) {
				//This happens when cloning because EpsSig is not yet a child of a Lennard object.
			}
		}
	};

	public LennardEpsilonSigma() {
		super(2);

		defineSigmaAsLabel.setBounds(4, 125, 70, 15);
		lblEpsSig.setBounds(4, 3, 155, 25);
		add(lblEpsSig);
		add(defineSigmaAsLabel);
		minimum.setBounds(80, 120, 325, 24);
		add(minimum);
		minimum.addActionListener(listener);
		zero.setBounds(80, 140, 325, 24);
		add(zero);
		zero.addActionListener(listener);
		buttonGroup.add(minimum);
		buttonGroup.add(zero);
		minimum.setSelected(true);
		lblEquation.setBounds(91, 161, 250, 56);
		add(lblEquation);
		lblSetParameters.setBounds(179, 10, 115, 15);
		add(lblSetParameters);
		scrollPane.setBounds(4, 34, 274, 73);
		add(scrollPane);
		manualInputES.setLayout(null);
		Epsilon.setBounds(10, 10, 225, 25);
		manualInputES.add(Epsilon);
		Sigma.setBounds(10, 35, 225, 25);
		manualInputES.add(Sigma);
		combinationRules.setLayout(null);
		jwAB_2_1.setBounds(0, 10, 254, 51);
		combinationRules.add(jwAB_2_1);
		geometricCombinationRules.setLayout(null);
		jwAB_3_1.setBounds(10, 10, 144, 51);
		geometricCombinationRules.add(jwAB_3_1);
		cboEpsSigParameters.addActionListener(keyEpsSigParameters);
		scrollPane.setViewportView(manualInputES);
		cboEpsSigParameters.setBounds(299, 4, 217, 24);
		add(cboEpsSigParameters);
	}

	@Override
	public String writePotential() throws IncompleteOptionException,
			InvalidOptionException {
		return "";// potential written out in Lennard
	}
	
	@Override
	public LennardEpsilonSigma clone() {
		LennardEpsilonSigma l = new LennardEpsilonSigma();
//		l.cboEpsSigParameters.setSelectedIndex(this.cboEpsSigParameters.getSelectedIndex());
//		l.zero.setSelected(this.zero.isSelected());
//		l.minimum.setSelected(this.minimum.isSelected());
		return l;
	}
}