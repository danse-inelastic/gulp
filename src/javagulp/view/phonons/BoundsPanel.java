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
		private int number = 0;

		private JTextField txtPoints = new JTextField("2");

		private SerialKeyAdapter pointsKeyListener = new SerialKeyAdapter() {
			private static final long serialVersionUID = 1268594961778847795L;
			@Override
			public void keyReleased(KeyEvent e) {
				updateBounds();
				Back.getPanel().getPhonon().pnlDispersion.dispersionModified = true;
			}
		};

		BoundsPanel() {
			super();
			setLayout(null);

			lblPoints.setBounds(0, 0, 120, 40);
			add(lblPoints);
			txtPoints.setBounds(130, 10, 39, 20);
			add(txtPoints);
			txtPoints.addKeyListener(pointsKeyListener);
			updateBounds();
		}

		private void updateBounds() {
			int newSize;
			try {
				newSize = Integer.parseInt(txtPoints.getText());
			} catch (NumberFormatException e) {
				return;
			}
			if (newSize > number) {
				for (int i = 0; i < newSize - number; i++) {
					DispersionKpoint l = new DispersionKpoint();
					l.setBounds(5, (number + i) * 25 + 40, 150, 20);
					add(l);
				}
				number = newSize;
			} else if (number > newSize) {
				for (int i = 0; i < number - newSize; i++) {
					remove(getComponentCount() - 1);
				}
				number = newSize;
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
