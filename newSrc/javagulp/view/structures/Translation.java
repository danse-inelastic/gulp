package javagulp.view.structures;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

	class Translation extends TitledPanel implements Serializable {
		private static final long serialVersionUID = 3789836754963176945L;
		
		private JLabel lblTranslateMarkedAtoms = new JLabel("translate marked atoms to");
		JLabel lblFractionalCoordinates = new JLabel("Use fractional coordinates.");
		private JLabel lblTranslationPoints = new JLabel("additional equidistant intermediate points");
		private JLabel lblSampleAt = new JLabel("and sample at");

		private JTextField txtX = new JTextField();
		private JTextField txtY = new JTextField();
		private JTextField txtZ = new JTextField();
		private JTextField txtPoints = new JTextField();
		
		private JCheckBox chkDoNotInclude = new JCheckBox("do not include the first point, or structure");

		private KeywordListener keyDoNotInclude = new KeywordListener(chkDoNotInclude, "nofirst_point");
		
		Translation() {
			super();
			
			setTitle("translation vector");
			lblTranslateMarkedAtoms.setBounds(10, 23, 209, 15);
			add(lblTranslateMarkedAtoms);
			txtY.setBounds(281, 21, 50, 20);
			add(txtY);
			txtZ.setBounds(337, 21, 50, 20);
			add(txtZ);
			txtX.setBounds(225, 21, 50, 20);
			add(txtX);
			lblSampleAt.setBounds(393, 21, 132, 19);
			add(lblSampleAt);
			txtPoints.setBounds(525, 21, 50, 20);
			add(txtPoints);
			lblTranslationPoints.setBounds(581, 23, 395, 15);
			add(lblTranslationPoints);
			chkDoNotInclude.setBounds(450, 44, 395, 25);
			chkDoNotInclude.addActionListener(keyDoNotInclude);
			add(chkDoNotInclude);
			lblFractionalCoordinates.setBounds(185, 44, 200, 25);
			//add(lblFractionalCoordinates);
		}
		
		String writeTranslate() throws IncompleteOptionException {
			String lines = "";
			JTextField[] fields = {txtX, txtY, txtZ, txtPoints};
			if (Back.checkAnyNonEmpty(fields)) {
				String[] descriptions = {"translate x", "translate y", "translate y", "translate number"};
				Back.checkAllNonEmpty(fields, descriptions);
				Back.parseFieldsD(fields, descriptions);
				lines = "translate " + Back.concatFields(fields) + Back.newLine;
			}
			return lines;
		}
	}