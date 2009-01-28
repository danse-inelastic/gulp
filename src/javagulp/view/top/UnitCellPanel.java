package javagulp.view.top;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javagulp.model.G;

public class UnitCellPanel extends TitledPanel implements Serializable {

	private static final long serialVersionUID = -6630264256468105811L;

	private G g = new G();

	private JTabbedPane tabbedPane = new JTabbedPane();
	public ThreeDUnitCell threeDUnitCell = new ThreeDUnitCell();
	private TwoDUnitCell twoDUnitCell = new TwoDUnitCell();
	private OneDUnitCell oneDUnitCell = new OneDUnitCell();

	public UnitCellPanel() {
		super();
		setLayout(null);

		tabbedPane.setBounds(0, 0, 240, 210);
		add(tabbedPane);
		tabbedPane.add(threeDUnitCell, "3d");
		tabbedPane.add(twoDUnitCell, "2d");
		tabbedPane.add(oneDUnitCell, "1d");
	}

	private class OneDUnitCell extends JPanel {

		private static final long serialVersionUID = -2905642930994016008L;

		private class OneDCellParameters extends JPanel {

			private static final long serialVersionUID = 2200725347770958750L;
			private JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
			private JTextField txtA = new JTextField();
			private JCheckBox chkA = new JCheckBox("fit");

			public OneDCellParameters() {
				super();
				setLayout(null);

				txtA.setBounds(46, 8, 47, 18);
				add(txtA);
				lblA.setBounds(10, 10, 30, 15);
				add(lblA);
				chkA.setBounds(99, 5, 40, 25);
				add(chkA);
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

		public class OneDCellVectors extends JPanel implements Serializable {

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

		private JTabbedPane tabbedPane = new JTabbedPane();

		private OneDCellParameters oneDCellParameters = new OneDCellParameters();
		private OneDCellVectors oneDCellVectors = new OneDCellVectors();

		public OneDUnitCell() {
			super();
			setLayout(null);

			tabbedPane.setBounds(0, 0, 182, 126);
			add(tabbedPane);
			tabbedPane.add(oneDCellParameters, "parameters");
			tabbedPane.add(oneDCellVectors, "vectors");
		}

		public String writeOneDUnitCell() throws IncompleteOptionException {
			if (tabbedPane.getSelectedIndex() == 0)
				return oneDCellParameters.writePcell();
			else
				return oneDCellVectors.writePvectors();
		}
	}

	public String writeUnitCell() throws IncompleteOptionException {
		return threeDUnitCell.write3DUnitCell()
				+ twoDUnitCell.write2DUnitCell()
				+ oneDUnitCell.writeOneDUnitCell();
	}
}