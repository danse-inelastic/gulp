package javagulp.view.structures;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class ThreeDCellParameters extends JPanel {

		private static final long serialVersionUID = -3031539266310166383L;

		JTextField txtA = new JTextField();
		JTextField txtB = new JTextField();
		JTextField txtC = new JTextField();
		JTextField txtAlpha = new JTextField();
		JTextField txtBeta = new JTextField();
		JTextField txtGamma = new JTextField();

		G g = new G();

		JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
		JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
		JLabel lblC = new JLabel(g.html("c (" + g.ang + ")"));
		JLabel lblAlpha = new JLabel(g.html(g.alpha + " (deg)"));
		JLabel lblBeta = new JLabel(g.html(g.beta + " (deg)"));
		JLabel lblGamma = new JLabel(g.html(g.gamma + " (deg)"));

		JCheckBox chkA = new JCheckBox("fit");
		JCheckBox chkB = new JCheckBox("fit");
		JCheckBox chkC = new JCheckBox("fit");
		JCheckBox chkAlpha = new JCheckBox("fit");
		JCheckBox chkBeta = new JCheckBox("fit");
		JCheckBox chkGamma = new JCheckBox("fit");

		public ThreeDCellParameters() {
			super();
			setLayout(null);

			lblA.setBounds(10, 13, 45, 15);
			add(lblA);
			lblB.setBounds(10, 34, 45, 15);
			add(lblB);
			lblC.setBounds(10, 55, 45, 15);
			add(lblC);
			lblAlpha.setBounds(10, 76, 70, 15);
			add(lblAlpha);
			lblBeta.setBounds(10, 97, 65, 15);
			add(lblBeta);
			lblGamma.setBounds(10, 118, 85, 15);
			add(lblGamma);
			txtA.setBounds(61, 11, 68, 20);
			add(txtA);
			txtB.setBounds(61, 32, 68, 20);
			add(txtB);
			txtC.setBounds(61, 53, 68, 20);
			add(txtC);
			txtAlpha.setBounds(61, 74, 68, 20);
			add(txtAlpha);
			txtBeta.setBounds(61, 95, 68, 20);
			add(txtBeta);
			txtGamma.setBounds(61, 116, 68, 20);
			add(txtGamma);
			chkA.setBounds(135, 8, 40, 25);
			add(chkA);
			chkB.setBounds(135, 29, 40, 25);
			add(chkB);
			chkC.setBounds(135, 50, 40, 25);
			add(chkC);
			chkAlpha.setBounds(135, 71, 40, 25);
			add(chkAlpha);
			chkBeta.setBounds(135, 92, 40, 25);
			add(chkBeta);
			chkGamma.setBounds(135, 113, 40, 25);
			add(chkGamma);
		}

		public String writeCellParameters() throws IncompleteOptionException {
			JTextField[] fields = { txtA, txtB, txtC, txtAlpha, txtBeta,
					txtGamma };
			String[] descriptions = { "a cell parameter", "b cell parameter",
					"c cell parameter", "alpha cell parameter",
					"beta cell parameter", "gamma cell parameter" };
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
			JCheckBox[] boxes = { chkA, chkB, chkC, chkAlpha, chkBeta, chkGamma };
			if (lines.equals(""))
				return "";
			else
				return "cell" + Back.newLine + lines + Back.writeFits(boxes) + Back.newLine;
		}

		// TODO From the documentation: For optimizations or fitting, flags must
		// be set unless cellonly, conp or conv are specified.
	}