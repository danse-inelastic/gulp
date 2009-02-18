package javagulp.view.md;

import javagulp.view.Back;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

	class OutputFormats extends JPanel {

		private static final long serialVersionUID = -8425306576850279775L;

		private JLabel lblWriteFrequencyps = new JLabel("write to md dumpfile every");
		private JLabel lblMdarchive = new JLabel("create Insight II archive file");

		private JTextField txtWrite = new JTextField("1");
		private JTextField txtMdarchive = new JTextField();

		private JComboBox cboUnits = new JComboBox(new String[] { "ps", "ns",
				"fs", "s", "timesteps" });

		OutputFormats() {
			setBorder(new TitledBorder(null, "output formats",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			setLayout(null);

			lblWriteFrequencyps.setBounds(7, 21, 175, 14);
			add(lblWriteFrequencyps);
			txtWrite.setBounds(182, 14, 70, 21);
			add(txtWrite);
			lblMdarchive.setBounds(10, 69, 180, 15);
			add(lblMdarchive);
			txtMdarchive.setBounds(182, 70, 70, 21);
			add(txtMdarchive);
			cboUnits.setBounds(154, 42, 98, 21);
			add(cboUnits);
		}

		String writeMDWriteFrequency() {
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

		String writeMDarchive() {
			String lines = "";
			if (!txtMdarchive.getText().equals("")) {
				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
			}
			return lines;
		}
	}
