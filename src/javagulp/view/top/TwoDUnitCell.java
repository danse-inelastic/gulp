package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class TwoDUnitCell extends JPanel implements Serializable {

	private static final long serialVersionUID = 440555811880387825L;

	private G g = new G();

	private class TwoDCellParameters extends JPanel {

		private static final long serialVersionUID = 6224150055887463745L;
		private JTextField txtA = new JTextField();
		private JTextField txtB = new JTextField();
		private JTextField txtAlpha = new JTextField();

		private JCheckBox chkA = new JCheckBox("fit");
		private JCheckBox chkB = new JCheckBox("fit");
		private JCheckBox chkAlpha = new JCheckBox("fit");

		private JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
		private JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
		private JLabel lblAlpha = new JLabel(g.html(g.alpha + " (deg)"));

		public TwoDCellParameters() {
			super();

			setLayout(null);
			lblA.setBounds(4, 9, 45, 15);
			add(lblA);
			txtA.setBounds(55, 6, 57, 20);
			add(txtA);
			lblB.setBounds(4, 32, 45, 15);
			add(lblB);
			txtB.setBounds(55, 29, 57, 20);
			add(txtB);
			txtAlpha.setBounds(55, 53, 57, 20);
			add(txtAlpha);
			lblAlpha.setBounds(4, 53, 45, 15);
			add(lblAlpha);
			chkA.setBounds(118, 4, 40, 25);
			add(chkA);
			chkB.setBounds(118, 27, 40, 25);
			add(chkB);
			chkAlpha.setBounds(118, 51, 40, 25);
			add(chkAlpha);
		}

		public String writeScell() throws IncompleteOptionException {
			JTextField[] fields = { txtA, txtB, txtAlpha };
			String[] descriptions = { "a cell parameter", "b cell parameter",
					"alpha cell parameter" };
			boolean text = false;
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getText().equals(""))
					text = true;
			}
			String lines = "";
			if (text) {
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = Back.concatFields(fields) + " ";
			}
			JCheckBox[] boxes = { chkA, chkB, chkAlpha };
			if (lines.equals(""))
				return "";
			else
				return "scell" + Back.newLine + lines + Back.writeFits(boxes) + Back.newLine;
		}
	}

	private class TwoDCellVectors extends JPanel {

		private static final long serialVersionUID = -4882584047310901686L;
		private JTextField txtAX = new JTextField();
		private JTextField txtAY = new JTextField();
		private JTextField txtBX = new JTextField();
		private JTextField txtBY = new JTextField();

		private JLabel lblAV = new JLabel(g.html("a (" + g.ang + ")"));
		private JLabel lblBV = new JLabel(g.html("b (" + g.ang + ")"));

		public TwoDCellVectors() {
			super();
			setLayout(null);

			lblAV.setBounds(3, 6, 29, 20);
			add(lblAV);
			lblBV.setBounds(2, 27, 30, 20);
			add(lblBV);
			txtBX.setBounds(38, 26, 45, 21);
			add(txtBX);
			txtBY.setBounds(84, 26, 47, 21);
			add(txtBY);
			txtAX.setBounds(38, 4, 45, 22);
			add(txtAX);
			txtAY.setBounds(84, 4, 47, 22);
			add(txtAY);
		}

		public String writeSvectors() throws IncompleteOptionException {
			JTextField[] fields = { txtAX, txtAY, txtBX, txtBY };
			String[] descriptions = { "ax cell vector", "ay cell vector",
					"bx cell vector", "by cell vector" };
			boolean text = false;
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getText().equals(""))
					text = true;
			}
			String lines = "";
			if (text) {
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = Back.concatFields(fields) + " ";
			}
			// TODO Documentation specifies 2 flags (corresponding to xx and xy
			// but not yy?). Need to add checkboxes to GUI.
			if (lines.equals(""))
				return "";
			else
				return "svectors" + Back.newLine + lines + Back.newLine;
		}
	}

	private JTabbedPane tabbedPane = new JTabbedPane();

	private TwoDCellParameters twoDCellParameters = new TwoDCellParameters();
	private TwoDCellVectors twoDCellVectors = new TwoDCellVectors();

	public TwoDUnitCell() {
		super();
		setLayout(null);

		tabbedPane.setBounds(0, 0, 182, 126);
		add(tabbedPane);
		tabbedPane.add(twoDCellParameters, "parameters");
		tabbedPane.add(twoDCellVectors, "vectors");
	}

	public String write2DUnitCell() throws IncompleteOptionException {
		if (tabbedPane.getSelectedIndex() == 0)
			return twoDCellParameters.writeScell();
		else
			return twoDCellVectors.writeSvectors();
	}
}