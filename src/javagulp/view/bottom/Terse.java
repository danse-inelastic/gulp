package javagulp.view.bottom;

import java.io.Serializable;

import javagulp.view.Back;

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
		this.setPreferredSize(new java.awt.Dimension(392, 161));

		add(lblTerse);
		lblTerse.setBounds(7, 7, 126, 21);
		radNone1.setSelected(true);
		add(radNone1);
		radNone1.setBounds(7, 28, 140, 21);
		add(radInput);
		radInput.setBounds(7, 49, 140, 21);
		add(radOutput);
		radOutput.setBounds(7, 70, 140, 21);
		add(radInputOutput);
		radInputOutput.setBounds(7, 91, 140, 21);

		group1.add(radNone1);
		group1.add(radInput);
		group1.add(radOutput);
		group1.add(radInputOutput);

		add(lblDoNotOutput);
		lblDoNotOutput.setBounds(147, 7, 238, 21);
		radNone2.setSelected(true);
		add(radNone2);
		radNone2.setBounds(147, 28, 238, 21);
		add(radCell);
		radCell.setBounds(147, 49, 238, 21);
		add(radCoordinates);
		radCoordinates.setBounds(147, 70, 238, 21);
		add(radStructure);
		radStructure.setBounds(147, 91, 238, 21);
		add(radPotentials);
		radPotentials.setBounds(147, 112, 238, 21);
		add(radDerivatives);
		radDerivatives.setBounds(147, 133, 238, 21);

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