package javagulp.view.potential.twocenter;

import java.awt.event.ActionEvent;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

	class LennardAB extends PotentialPanel {

		private G g = new G();
		
		private static final long serialVersionUID = 8823654542511080623L;

		PPP A = new PPP("<html>A (eV ang<sup>m</sup>)</html>");
		PPP B = new PPP("<html>B (eV ang<sup>n</sup>)</html>");
		
		JComboBox cboABParameters = new JComboBox(new String[] {
				"manual input", "standard combination rules",
				"ESFF combination rules" });
		
		private JPanel esffPanel = new JPanel();
		private JScrollPane inputScroll = new JScrollPane();
		
		private JLabel jwAB = new JLabel(g.html("E = A r<sup>-m</sup> - B r<sup>-n</sup>"));
		private JLabel jwAB_2 = new JLabel(g.html("A = A<sub>ij</sub> = sqrt(A<sub>i</sub>A<sub>j</sub>) <br>B = B<sub>ij</sub> = sqrt(B<sub>i</sub>B<sub>j</sub>)"));
		private JLabel jwAB_3 = new JLabel(g.html("A = A<sub>ij</sub> = A<sub>i</sub>B<sub>j</sub> + A<sub>j</sub>B<sub>i</sub> <br>B = B<sub>ij</sub> = 3 B<sub>i</sub> B<sub>j</sub>"));
		private JPanel manualInput = new JPanel();
		private JLabel setParametersByLabel = new JLabel("set parameters by");
		private JPanel standardCombinationRules = new JPanel();
		public JTextField txtM = new JTextField("12");
		
		private SerialListener keyABParameters = new SerialListener() {
			private static final long serialVersionUID = 37488141038300915L;
			//JTextField txtM = Back.getPanel().getPotential().createLibrary.potentialPanels[]
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (cboABParameters.getSelectedIndex()) {
				case 0:
					inputScroll.setViewportView(manualInput);
					txtM.setText("12");
					break;
				case 1:
					inputScroll.setViewportView(standardCombinationRules);
					txtM.setText("12");
					break;
				case 2:
					inputScroll.setViewportView(esffPanel);
					txtM.setText("9");
					break;
				}
			}
		};
		
		public LennardAB(){//JTextField txtM) {
			super(2);
			setLayout(null);
			enabled = new boolean[] { true, true, true, false, false, false };
			
			this.txtM = ((Lennard)Back.getCurrentRun().getPotential().createLibrary.potentialPanels.get(2)).txtM;
			//txtM;
			
			jwAB.setBounds(12, 3, 118, 20);
			add(jwAB);
			setParametersByLabel.setBounds(181, 8, 115, 15);
			add(setParametersByLabel);
			inputScroll.setBounds(2, 29, 274, 92);
			add(inputScroll);
			manualInput.setLayout(null);
			A.setBounds(10, 9, 225, 25);
			manualInput.add(A);
			B.setBounds(10, 34, 225, 25);
			manualInput.add(B);
			standardCombinationRules.setLayout(null);
			jwAB_2.setBounds(10, 10, 144, 51);
			standardCombinationRules.add(jwAB_2);
			esffPanel.setLayout(null);
			jwAB_3.setBounds(10, 20, 325, 40);
			esffPanel.add(jwAB_3);
			cboABParameters.addActionListener(keyABParameters);
			inputScroll.setViewportView(manualInput);
			cboABParameters.setBounds(301, 7, 196, 21);
			add(cboABParameters);
		}

		@Override
		public String writePotential() throws IncompleteOptionException, InvalidOptionException {
			return "";
		}

		@Override
		public LennardAB clone() {
			LennardAB ab = new LennardAB();
			//ab.cboABParameters.setSelectedIndex(this.cboABParameters.getSelectedIndex());
			return ab;
		}
	}
