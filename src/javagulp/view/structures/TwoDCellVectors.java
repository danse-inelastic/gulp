package javagulp.view.structures;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class TwoDCellVectors extends JPanel {
		
		private G g = new G();

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

			lblAV.setBounds(3, 10, 46, 16);
			add(lblAV);
			lblBV.setBounds(3, 37, 47, 15);
			add(lblBV);
			txtBX.setBounds(55, 35, 45, 20);
			add(txtBX);
			txtBY.setBounds(106, 35, 47, 20);
			add(txtBY);
			txtAX.setBounds(55, 10, 45, 18);
			add(txtAX);
			txtAY.setBounds(106, 10, 47, 18);
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
