package javagulp.view.structures;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class ThreeDCellVectors extends JPanel {

		private static final long serialVersionUID = 5105344486272460180L;

		JTextField txtAx = new JTextField();
		JTextField txtAy = new JTextField();
		JTextField txtAz = new JTextField();
		JTextField txtBx = new JTextField();
		JTextField txtBy = new JTextField();
		JTextField txtBz = new JTextField();
		JTextField txtCx = new JTextField();
		JTextField txtCy = new JTextField();
		JTextField txtCz = new JTextField();

		G g = new G();

		JLabel lblA = new JLabel(g.html("a (" + g.ang + ")"));
		JLabel lblB = new JLabel(g.html("b (" + g.ang + ")"));
		JLabel lblC = new JLabel(g.html("c (" + g.ang + ")"));
		JLabel lblStrainOptimisationFlags = new JLabel("strain optimization flags");

		JCheckBox chkXX = new JCheckBox("xx");
		JCheckBox chkXY = new JCheckBox("xy");
		JCheckBox chkXZ = new JCheckBox("xz");
		JCheckBox chkYY = new JCheckBox("yy");
		JCheckBox chkYZ = new JCheckBox("yz");
		JCheckBox chkZZ = new JCheckBox("zz");

		public ThreeDCellVectors() {
			add(lblA);
			lblA.setBounds(9, 5, 48, 20);
			add(txtAx);
			txtAx.setBounds(63, 5, 50, 20);
			add(txtAy);
			txtAy.setBounds(119, 5, 50, 20);
			add(txtAz);
			txtAz.setBounds(175, 5, 50, 20);
			add(lblB);
			lblB.setBounds(9, 30, 48, 20);
			add(txtBx);
			txtBx.setBounds(63, 30, 50, 20);
			add(txtBy);
			txtBy.setBounds(119, 30, 50, 20);
			add(txtBz);
			txtBz.setBounds(175, 31, 50, 20);
			add(lblC);
			lblC.setBounds(10, 55, 47, 20);
			add(txtCx);
			txtCx.setBounds(63, 55, 50, 20);
			add(txtCy);
			txtCy.setBounds(119, 55, 50, 20);
			add(txtCz);
			txtCz.setBounds(175, 55, 50, 20);
			add(lblStrainOptimisationFlags);
			lblStrainOptimisationFlags.setBounds(5, 80, 198, 15);
			add(chkXX);
			chkXX.setBounds(45, 98, 39, 20);
			add(chkXY);
			chkXY.setBounds(101, 98, 50, 20);
			add(chkXZ);
			chkXZ.setBounds(157, 98, 50, 20);
			add(chkYY);
			chkYY.setBounds(101, 127, 50, 15);
			add(chkYZ);
			chkYZ.setBounds(157, 124, 50, 20);
			add(chkZZ);
			chkZZ.setBounds(157, 150, 50, 20);
			//setSize(304, 238);
			setLayout(null);

		}

		public String writeCellVectors() throws IncompleteOptionException {
			JTextField[] fields = { txtAx, txtAy, txtAz, txtBx, txtBy, txtBz,
					txtCx, txtCy, txtCz };
			String[] descriptions = { "ax cell vector", "ay cell vector",
					"az cell vector", "bx cell vector", "by cell vector",
					"bz cell vector", "cx cell vector", "cy cell vector",
					"cz cell vector", };
			String lines = "";
			if (Back.checkAnyNonEmpty(fields)) {
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = Back.concatFields(fields) + " ";
			}
			JCheckBox[] boxes = { chkXX, chkXY, chkXZ, chkYY, chkYZ, chkZZ };
			lines += Back.writeFits(boxes);
			if (lines.equals(""))
				return "";
			else
				return "vectors" + Back.newLine + lines + Back.newLine;
		}
	}
