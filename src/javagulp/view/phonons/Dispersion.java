package javagulp.view.phonons;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.BareBonesBrowserLaunch;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class Dispersion extends TitledPanel implements Serializable {

	private TitledPanel pnlOptions;
	private static final long serialVersionUID = -4898054157855983814L;


	JTabbedPane paneDispersion = new JTabbedPane();
	public boolean dispersionModified = false;

	private final G g = new G();
	private final JButton btnVisualizeBrillouinZone = new JButton("<html>visualize Brillouin zone</html>");

	private final SerialListener keyVisualizeBrillouinZone = new SerialListener() {
		private static final long serialVersionUID = -7205702855296565824L;
		@Override
		public void actionPerformed(ActionEvent e) {
			BareBonesBrowserLaunch.openURL("http://www.cryst.ehu.es/cryst/get_kvec.html");
		}
	};

	private final JCheckBox chkKpointsAreFor = new JCheckBox("<html>kpoints are for centered cell rather than primitive</html>");

	private final KeywordListener keyKpointsAreFor = new KeywordListener(chkKpointsAreFor, "kfull");

	private final JLabel lblLines = new JLabel("number of unconnected dispersion lines");
	private final JLabel lblFrequency = new JLabel("number of sampled points between kpoints");

	private final String TXT_LINES = "1";
	JTextField txtLines = new JTextField(TXT_LINES);
	private final String TXT_FREQUENCY = "20";
	JTextField txtFrequency = new JTextField(TXT_FREQUENCY);
	//private JTextField txtboxvalue = new JTextField();
	//private JComboBox cboNumBoxes = new JComboBox(new String[] {
	//		"number of boxes", "box size (1/cm)" });

	//private JLabel lblChangeThe = new JLabel("change the");



	private class LinesKeyListener extends KeyAdapter implements Serializable {
		private static final long serialVersionUID = 83298433634021501L;
		@Override
		public void keyReleased(KeyEvent e) {
			final int oldSize = paneDispersion.getComponentCount();
			int newSize;
			try {
				newSize = Integer.parseInt(txtLines.getText());
			} catch (final NumberFormatException e1) {
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

	private final LinesKeyListener linesKeyListener = new LinesKeyListener();

	public Dispersion() {
		super();

		btnVisualizeBrillouinZone.setBounds(382, 8, 237, 25);
		btnVisualizeBrillouinZone.addActionListener(keyVisualizeBrillouinZone);
		add(btnVisualizeBrillouinZone);
		lblLines.setBounds(10, 10, 291, 20);
		add(lblLines);
		txtLines.setBounds(296, 11, 53, 20);
		add(txtLines);
		add(paneDispersion);
		paneDispersion.setBounds(10, 36, 274, 257);
		for (int i = 0; i < Integer.parseInt(txtLines.getText()); i++)
			paneDispersion.add("" + (paneDispersion.getComponentCount() + 1), new BoundsPanel());
		txtLines.addKeyListener(linesKeyListener);
		add(getPnlOptions());

		//		do not delete these lines
		//		txtboxvalue.setBounds(575, 258, 77, 19);
		//		add(txtboxvalue);
		//		lblChangeThe.setBounds(354, 260, 70, 15);
		//		add(lblChangeThe);
		//		cboNumBoxes.setBounds(430, 257, 139, 21);
		//		add(cboNumBoxes);
	}

	//	private String writeDispersionBox() throws IncompleteOptionException {
	//		String lines = "";
	//		if (!txtboxvalue.getText().equals("")) {
	//			try {
	//				Integer.parseInt(txtboxvalue.getText());
	//			} catch (NumberFormatException e) {
	//				throw new NumberFormatException("Please enter an integer for the number of boxes in Phonons");
	//			}
	//			lines = "box dispersion " + cboNumBoxes.getSelectedItem() + " "
	//			+ txtboxvalue.getText() + Back.newLine;
	//		}
	//		return lines;
	//	}

	public String writeDispersion() throws IncompleteOptionException {
		final BoundsPanel b = (BoundsPanel) paneDispersion.getComponent(0);
		//TODO remove 0 and use all
		return b.writeDispersion();//+writeDispersionBox();
	}
	/**
	 * @return
	 */
	protected TitledPanel getPnlOptions() {
		if (pnlOptions == null) {
			pnlOptions = new TitledPanel();
			pnlOptions.setBounds(294, 39, 325, 138);
			pnlOptions.setTitle("options");
			lblFrequency.setBounds(10, 21, 311, 20);
			pnlOptions.add(lblFrequency);
			txtFrequency.setBounds(10, 47, 53, 20);
			pnlOptions.add(txtFrequency);
			chkKpointsAreFor.addActionListener(keyKpointsAreFor);
			chkKpointsAreFor.setBounds(10, 73, 305, 55);
			pnlOptions.add(chkKpointsAreFor);
		}
		return pnlOptions;
	}
	/**
	 * @return
	 */
}