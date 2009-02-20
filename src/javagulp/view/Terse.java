package javagulp.view;

import java.awt.Dimension;
import java.io.Serializable;


import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class Terse extends JPanel implements Serializable {

	private static final long serialVersionUID = -8004045257003429159L;

	private JLabel lblTerse = new JLabel("Make terse");
	private JRadioButton radNone1 = new JRadioButton("None");
	private JRadioButton radInput = new JRadioButton("Input");
	private JRadioButton radOutput = new JRadioButton("Output");
	private JRadioButton radInputOutput = new JRadioButton("Input and Output");
	private ButtonGroup group1 = new ButtonGroup();

	private JLabel lblDoNotOutput = new JLabel("Do not output");
	private JRadioButton radNone2 = new JRadioButton("show full output");
	private JRadioButton radCell = new JRadioButton("unit cell parameters for vectors");
	private JRadioButton radCoordinates = new JRadioButton("the coordinates of the atoms");
	private JRadioButton radStructure = new JRadioButton("unit cell or coordinates");
	private JRadioButton radPotentials = new JRadioButton("interatomic potentials");
	private JRadioButton radDerivatives = new JRadioButton("derivatives");
	private ButtonGroup group2 = new ButtonGroup();

	public Terse() {
		super();
		setLayout(null);
		setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		this.setPreferredSize(new java.awt.Dimension(470, 250));

		add(lblTerse);
		lblTerse.setBounds(7, 7, 126, 21);
		radNone1.setSelected(true);
		add(radNone1);
		radNone1.setBounds(7, 34, 140, 21);
		add(radInput);
		radInput.setBounds(7, 61, 140, 21);
		add(radOutput);
		radOutput.setBounds(7, 88, 140, 21);
		add(radInputOutput);
		radInputOutput.setBounds(7, 115, 170, 21);

		group1.add(radNone1);
		group1.add(radInput);
		group1.add(radOutput);
		group1.add(radInputOutput);

		add(lblDoNotOutput);
		lblDoNotOutput.setBounds(183, 7, 238, 21);
		radNone2.setSelected(true);
		add(radNone2);
		radNone2.setBounds(183, 34, 238, 21);
		add(radCell);
		radCell.setBounds(183, 61, 277, 21);
		add(radCoordinates);
		radCoordinates.setBounds(183, 88, 238, 21);
		add(radStructure);
		radStructure.setBounds(183, 115, 238, 21);
		add(radPotentials);
		radPotentials.setBounds(183, 142, 238, 21);
		add(radDerivatives);
		radDerivatives.setBounds(183, 169, 238, 21);

		group2.add(radNone2);
		group2.add(radCell);
		group2.add(radCoordinates);
		group2.add(radStructure);
		group2.add(radPotentials);
		group2.add(radDerivatives);
	}

	public String writeTerse() {
		String lines = "terse ";
		if (!radNone1.isSelected()) {
			if (radInput.isSelected())
				lines += "in ";
			else if (radOutput.isSelected())
				lines += "out ";
			else
				lines += "inout ";
		}
		if (!radNone2.isSelected()) {
			if (radCell.isSelected())
				lines += "cell ";
			else if (radCoordinates.isSelected())
				lines += "coordinates ";
			else if (radStructure.isSelected())
				lines += "structure ";
			else if (radPotentials.isSelected())
				lines += "potentials ";
			else
				lines += "derivatives ";
		}
		if (lines.equals("terse "))
			return "";
		else
			return lines + Back.newLine;
	}
}