package javagulp.view.phonons;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.BareBonesBrowserLaunch;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;
import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialListener;

public class Dispersion extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -4898054157855983814L;


	private JTabbedPane paneDispersion = new JTabbedPane();
	public boolean dispersionModified = false;
	
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
	private JTextField txtboxvalue = new JTextField();
	private JComboBox cboNumBoxes = new JComboBox(new String[] {
			"number of boxes", "box size (1/cm)" });

	private JLabel lblChangeThe = new JLabel("change the");
	private JLabel lblFor = new JLabel("for");



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
		chkKpointsAreFor.setBounds(150, 10, 446, 60);
		add(chkKpointsAreFor);
		lblLines.setBounds(10, 70, 144, 20);
		add(lblLines);
		txtLines.setBounds(160, 71, 30, 20);
		add(txtLines);
		lblFrequency.setBounds(219, 70, 173, 20);
		add(lblFrequency);
		txtFrequency.setBounds(398, 71, 30, 20);
		add(txtFrequency);
		add(paneDispersion);
		paneDispersion.setBounds(7, 98, 301, 252);
		for (int i = 0; i < Integer.parseInt(txtLines.getText()); i++)
			paneDispersion.add("" + (paneDispersion.getComponentCount() + 1), new BoundsPanel());
		txtLines.addKeyListener(linesKeyListener);
		
		txtboxvalue.setBounds(232, 78, 77, 19);
		add(txtboxvalue);
		lblChangeThe.setBounds(11, 80, 70, 15);
		add(lblChangeThe);
		cboNumBoxes.setBounds(87, 77, 139, 21);
		add(cboNumBoxes);
		lblFor.setBounds(9, 50, 20, 20);
		add(lblFor);
	}

	private String writeDispersionBox() throws IncompleteOptionException {
		String lines = "";
		if (!txtboxvalue.getText().equals("")) {
			try {
				Integer.parseInt(txtboxvalue.getText());
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Please enter an integer for the number of boxes in Phonons");
			}
			lines = "box " + cboBoxType.getSelectedItem() + " "
			+ cboNumBoxes.getSelectedItem() + " "
			+ txtboxvalue.getText() + Back.newLine;
		}
		return lines;
	}
	
	public String writeDispersion() {
		BoundsPanel b = (BoundsPanel) paneDispersion.getComponent(0);
		//TODO remove 0 and use all
		return b.writeDispersion();
	}
}