package javagulp.view.structures;

import java.io.Serializable;

import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

		public class OneDCellVectors extends JPanel implements Serializable {

			private G g = new G();
			private static final long serialVersionUID = -8276461934235049515L;
			private JLabel lblAX = new JLabel(g.html("a (" + g.ang + ")"));
			private JTextField txtAX = new JTextField();
			private JCheckBox chkAX = new JCheckBox("fit");

			public OneDCellVectors() {
				super();
				setLayout(null);

				txtAX.setBounds(46, 8, 47, 18);
				add(txtAX);
				lblAX.setBounds(10, 10, 30, 15);
				add(lblAX);
				chkAX.setBounds(99, 5, 40, 25);
				add(chkAX);
			}

			public String writePvectors() {
				String lines = "";
				if (!txtAX.getText().equals(""))
					lines += txtAX.getText() + " ";
				if (chkAX.isSelected())
					lines += "1";
				if (!lines.equals(""))
					lines = "pvector" + Back.newLine + lines + Back.newLine;
				return lines;
			}
		}
