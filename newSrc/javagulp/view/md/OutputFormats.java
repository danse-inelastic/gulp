package javagulp.view.md;

import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

	public class OutputFormats extends JPanel {

		private static final long serialVersionUID = -8425306576850279775L;

		private JLabel lblWriteFrequencyps = new JLabel("write to md dumpfile every");
		private JLabel lblMdarchive = new JLabel("create Insight II archive file");

		private JTextField txtWrite = new JTextField();
		private JTextField txtMdarchive = new JTextField();

		private JComboBox cboUnits = new JComboBox(new String[] { "ps", "ns",
				"fs", "s", "timesteps" });

		public OutputFormats() {
			setBorder(new TitledBorder(null, "output formats",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			setLayout(null);

			lblWriteFrequencyps.setBounds(7, 21, 230, 14);
			add(lblWriteFrequencyps);
			txtWrite.setBounds(228, 18, 85, 21);
			add(txtWrite);
			lblMdarchive.setBounds(7, 49, 230, 15);
			add(lblMdarchive);
			txtMdarchive.setBounds(228, 45, 183, 21);
			add(txtMdarchive);
			cboUnits.setBounds(319, 18, 92, 21);
			add(cboUnits);
		}

		public String writeMDWriteFrequency() {
			String lines = "";
			if (!txtWrite.getText().equals("")) {
				if (cboUnits.getSelectedItem().equals("timesteps")) {
					try {
						Integer.parseInt(txtWrite.getText());
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter an integer for MD write frequency.");
					}
					lines = "write " + txtWrite.getText() + Back.newLine;
				} else {
					try {
						Double.parseDouble(txtWrite.getText());
					} catch (NumberFormatException nfe) {
						throw new NumberFormatException("Please enter a number for MD write frequency.");
					}
					lines = "write " + txtWrite.getText() + " "
							+ cboUnits.getSelectedItem() + Back.newLine;
				}
			}
			return lines;
		}

		public String writeMDarchive() {
			String lines = "";
			if (!txtMdarchive.getText().equals("")) {
				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
			}
			return lines;
		}
	}
