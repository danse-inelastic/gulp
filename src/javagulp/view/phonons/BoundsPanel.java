package javagulp.view.phonons;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.model.G;
import javagulp.model.SerialKeyAdapter;
import javagulp.view.Back;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class BoundsPanel extends JPanel {

		private static final long serialVersionUID = 179956006650728973L;

		private G g = new G();
		private JLabel lblPoints = new JLabel(g.html("number of high symmetry kpoints"));
		private int numHighSymPoints = 0;

		private JTextField txtPoints = new JTextField("2");

		private SerialKeyAdapter numPointsKeyListener = new SerialKeyAdapter() {
			private static final long serialVersionUID = 1268594961778847795L;
			@Override
			public void keyReleased(KeyEvent e) {
				updateBounds();
				JPanel phPan = Back.getPanel().getRunType().runTypes.get("phonons");
				phPan.pnlDispersion.dispersionModified = true;
			}
		};


		BoundsPanel() {
			super();
			setLayout(null);

			lblPoints.setBounds(0, 0, 120, 40);
			add(lblPoints);
			txtPoints.setBounds(130, 10, 39, 20);
			add(txtPoints);
			txtPoints.addKeyListener(numPointsKeyListener);
			updateBounds();
		}

		private void updateBounds() {
			int newNum;
			try {
				newNum = Integer.parseInt(txtPoints.getText());
			} catch (NumberFormatException e) {
				return;
			}
			if (newNum > numHighSymPoints) {
				for (int i = 0; i < newNum - numHighSymPoints; i++) {
					DispersionKpoint l = new DispersionKpoint();
					l.setBounds(5, (numHighSymPoints + i) * 25 + 40, 150, 20);
					add(l);
				}
				numHighSymPoints = newNum;
			} else if (numHighSymPoints > newNum) {
				for (int i = 0; i < numHighSymPoints - newNum; i++) {
					remove(getComponentCount() - 1);
				}
				numHighSymPoints = newNum;
			}
			repaint();
		}

		String writeDispersion() {
			String output = "";
			Dispersion disp = Back.getPanel().getPhonon().pnlDispersion;
			try {
				if (disp.dispersionModified) {
					output += "dispersion " + disp.txtLines.getText() + " "
							+ disp.txtFrequency.getText();
					for (int i = 0; i < disp.paneDispersion.getTabCount(); i++) {
						BoundsPanel b = (BoundsPanel) disp.paneDispersion.getComponent(i);

						// start at index 2 because of lblPoints and txtPoints
						for (int j = 2; j < b.getComponentCount(); j++) {
							DispersionKpoint l = (DispersionKpoint) b.getComponent(j);
							output += " " + l.txtBoundX.getText() + " "
									+ l.txtBoundY.getText() + " "
									+ l.txtBoundZ.getText() + " to";
						}
						output = output.substring(0, output.length() - 3);
					}
					output += Back.newLine;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return output;
		}
	}
