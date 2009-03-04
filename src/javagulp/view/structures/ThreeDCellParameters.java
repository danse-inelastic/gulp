package javagulp.view.structures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

	class ThreeDCellParameters extends JPanel {

		private JComboBox cboGamma;
		private JComboBox cboBeta;
		private JComboBox cboAlpha;
		private JComboBox cboC;
		private JComboBox cboB;
		private JComboBox cboA;
		private String[] options = new String[]{"","no reference","fit reference","optimise","fix"};
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


		public ThreeDCellParameters() {
			super();
			setLayout(null);

			lblA.setBounds(20, 10, 45, 20);
			add(lblA);
			lblB.setBounds(20, 36, 45, 20);
			add(lblB);
			lblC.setBounds(20, 62, 45, 20);
			add(lblC);
			lblAlpha.setBounds(20, 88, 45, 20);
			add(lblAlpha);
			lblBeta.setBounds(20, 114, 45, 20);
			add(lblBeta);
			lblGamma.setBounds(20, 140, 45, 20);
			add(lblGamma);
			txtA.setBounds(75, 11, 68, 20);
			add(txtA);
			txtB.setBounds(75, 36, 68, 20);
			add(txtB);
			txtC.setBounds(75, 63, 68, 20);
			add(txtC);
			txtAlpha.setBounds(75, 89, 68, 20);
			add(txtAlpha);
			txtBeta.setBounds(75, 115, 68, 20);
			add(txtBeta);
			txtGamma.setBounds(75, 141, 68, 20);
			add(txtGamma);
			add(getCboA());
			add(getCboB());
			add(getCboC());
			add(getCboAlpha());
			add(getCboBeta());
			add(getCboGamma());
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
			//JCheckBox[] boxes = { chkA, chkB, chkC, chkAlpha, chkBeta, chkGamma };
			JComboBox[] boxes = { getCboA(), getCboB(), getCboC(), getCboAlpha(), getCboBeta(), getCboGamma() };
			if (lines.equals(""))
				return "";
			else
				return "cell" + Back.newLine + lines + Back.writeFlags(boxes) + Back.newLine;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboA() {
			if (cboA == null) {
				cboA = new JComboBox(options);
				cboA.setBounds(150, 8, 117, 24);
			}
			return cboA;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboB() {
			if (cboB == null) {
				cboB = new JComboBox(options);
				cboB.setBounds(150, 34, 117, 24);
			}
			return cboB;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboC() {
			if (cboC == null) {
				cboC = new JComboBox(options);
				cboC.setBounds(150, 60, 117, 24);
			}
			return cboC;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboAlpha() {
			if (cboAlpha == null) {
				cboAlpha = new JComboBox(options);
				cboAlpha.setBounds(150, 86, 117, 24);
			}
			return cboAlpha;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboBeta() {
			if (cboBeta == null) {
				cboBeta = new JComboBox(options);
				cboBeta.setBounds(150, 112, 117, 24);
			}
			return cboBeta;
		}
		/**
		 * @return
		 */
		protected JComboBox getCboGamma() {
			if (cboGamma == null) {
				cboGamma = new JComboBox(options);
				cboGamma.setBounds(150, 138, 117, 24);
			}
			return cboGamma;
		}

		// TODO From the documentation: For optimizations or fitting, flags must
		// be set unless cellonly, conp or conv are specified.
	}