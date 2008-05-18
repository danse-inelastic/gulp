package javagulp.view.top;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.model.BareBonesBrowserLaunch;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;
import utility.misc.SerialKeyAdapter;
import utility.misc.SerialListener;

public class Dispersion extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -4898054157855983814L;

	private class BoundsPanel extends JPanel {

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

	private JTabbedPane paneDispersion = new JTabbedPane();

	private G g = new G();
	private JButton btnVisualizeBrillouinZone = new JButton(g.html("visualize<br> Brillouin zone"));

	private SerialListener keyVisualizeBrillouinZone = new SerialListener() {
		private static final long serialVersionUID = -7205702855296565824L;
		@Override
		public void actionPerformed(ActionEvent e) {
			BareBonesBrowserLaunch.openURL("http://www.cryst.ehu.es/cryst/get_kvec.html");
		}
	};

	private JCheckBox chkKpointsAreFor = new JCheckBox("<html>kpoints are for centered cell rather than primitive</html>");

	private KeywordListener keyKpointsAreFor = new KeywordListener(chkKpointsAreFor, "kfull");

	private JLabel lblLines = new JLabel("number of lines");
	private JLabel lblFrequency = new JLabel("sample frequencies");

	private final String TXT_LINES = "1";
	private JTextField txtLines = new JTextField(TXT_LINES);
	private final String TXT_FREQUENCY = "20";
	private JTextField txtFrequency = new JTextField(TXT_FREQUENCY);

	private boolean dispersionModified = false;

	private class LinesKeyListener extends KeyAdapter implements Serializable {
		private static final long serialVersionUID = 83298433634021501L;
		@Override
		public void keyReleased(KeyEvent e) {
			int oldSize = paneDispersion.getComponentCount();
			int newSize;
			try {
				newSize = Integer.parseInt(txtLines.getText());
			} catch (NumberFormatException e1) {
				return;
			}
			if (newSize > oldSize) {
				for (int i = 0; i < newSize - oldSize; i++) {
					paneDispersion.add("" + (paneDispersion.getComponentCount() + 1), new BoundsPanel());
				}
			} else if (oldSize > newSize) {
				for (int i = 0; i < oldSize - newSize; i++) {
					paneDispersion.remove(paneDispersion.getComponentCount() - 1);
				}
			}
			dispersionModified = true;
		}
	}

	private LinesKeyListener linesKeyListener = new LinesKeyListener();

	public Dispersion() {
		super();

		btnVisualizeBrillouinZone.setBounds(10, 23, 126, 42);
		btnVisualizeBrillouinZone.addActionListener(keyVisualizeBrillouinZone);
		add(btnVisualizeBrillouinZone);
		chkKpointsAreFor.addActionListener(keyKpointsAreFor);
		chkKpointsAreFor.setBounds(150, 10, 155, 60);
		add(chkKpointsAreFor);
		lblLines.setBounds(10, 70, 115, 20);
		add(lblLines);
		txtLines.setBounds(120, 70, 30, 20);
		add(txtLines);
		lblFrequency.setBounds(155, 70, 125, 20);
		add(lblFrequency);
		txtFrequency.setBounds(280, 70, 30, 20);
		add(txtFrequency);
		add(paneDispersion);
		paneDispersion.setBounds(7, 98, 301, 252);
		for (int i = 0; i < Integer.parseInt(txtLines.getText()); i++)
			paneDispersion.add("" + (paneDispersion.getComponentCount() + 1), new BoundsPanel());
		txtLines.addKeyListener(linesKeyListener);
	}

	public String writeDispersion() {
		BoundsPanel b = (BoundsPanel) paneDispersion.getComponent(0);
		//TODO remove 0 and use all
		return b.writeDispersion();
	}
}