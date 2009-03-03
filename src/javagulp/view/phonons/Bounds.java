import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.model.G;
import javagulp.model.SerialKeyAdapter;
import javagulp.view.Back;
import javagulp.view.phonons.Dispersion.BoundsPanel;
import javagulp.view.phonons.Dispersion.BoundsPanel.listPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class BoundsPanel extends JPanel {

		private static final long serialVersionUID = 179956006650728973L;

		public class listPanel extends JPanel implements Serializable {

			private static final long serialVersionUID = -4857283959710322431L;

			public JTextField txtBoundX = new JTextField("0.0");
			public JTextField txtBoundY = new JTextField("0.0");
			public JTextField txtBoundZ = new JTextField("0.0");

			private JLabel lblTo = new JLabel("to:");
			
			public listPanel() {
				setLayout(null);

				txtBoundX.setBounds(0, 0, 25, 20);
				add(txtBoundX);
				txtBoundY.setBounds(30, 0, 25, 20);
				add(txtBoundY);
				txtBoundZ.setBounds(60, 0, 25, 20);
				add(txtBoundZ);

				lblTo.setBounds(90, 0, 30, 20);
				add(lblTo);
			}
		}

		private G g = new G();
		private JLabel lblPoints = new JLabel(g.html("number of high symmetry kpoints"));
		private int number = 0;

		private JTextField txtPoints = new JTextField("2");

		private SerialKeyAdapter pointsKeyListener = new SerialKeyAdapter() {
			private static final long serialVersionUID = 1268594961778847795L;
			@Override
			public void keyReleased(KeyEvent e) {
				updateBounds();
				dispersionModified = true;
			}
		};

		private BoundsPanel() {
			super();
			setLayout(null);

			lblPoints.setBounds(0, 0, 120, 40);
			add(lblPoints);
			txtPoints.setBounds(125, 0, 25, 20);
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
					listPanel l = new listPanel();
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

		private String writeDispersion() {
			String output = "";
			try {
				if (dispersionModified) {
					output += "dispersion " + txtLines.getText() + " "
							+ txtFrequency.getText();
					for (int i = 0; i < paneDispersion.getTabCount(); i++) {
						BoundsPanel b = (BoundsPanel) paneDispersion.getComponent(i);

						// start at index 2 because of lblPoints and txtPoints
						for (int j = 2; j < b.getComponentCount(); j++) {
							listPanel l = (listPanel) b.getComponent(j);
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
