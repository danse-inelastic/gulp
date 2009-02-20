package javagulp.view.structures;

import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

		class OneDCellParameters extends JPanel {

			private G g = new G();
			private static final long serialVersionUID = 2200725347770958750L;
			private JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
			private JTextField txtA = new JTextField();
			private JCheckBox chkA = new JCheckBox("fit");

			public OneDCellParameters() {
				super();
				setLayout(null);

				add(txtA);
				txtA.setBounds(72, 37, 98, 23);
				add(lblA);
				lblA.setBounds(0, 37, 66, 23);
				add(chkA);
				chkA.setBounds(176, 37, 53, 23);
			}

			public String writePcell() {
				String lines = "";
				if (!txtA.getText().equals(""))
					lines += txtA.getText() + " ";
				if (chkA.isSelected())
					lines += "1";
				if (!lines.equals(""))
					lines = "pcell" + Back.newLine + lines + Back.newLine;
				return lines;
			}
		}
