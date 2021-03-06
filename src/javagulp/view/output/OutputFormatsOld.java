package javagulp.view.output;

import java.awt.Font;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormatsOld extends JPanel implements Serializable {

	private static final long serialVersionUID = -1455606819678907706L;

	private final JTextField txtOutputFilename = new JTextField("output");

	private final String[] labels = { "SIESTA (.fdf)", "CRYSTAL98 (.str)", "Crystal (.cif)", "MARVIN (.mvn)",
			"derivatives (.drv)", "forces (.frc)", "frequency"};
	private final String[] formats = { "fdf", "str", "cif", "marvin", "drv", "frc", "freq"};
	//private final String[] extensions = {".fdf", "", ".phonon", "", "", "", "", ".xyz",
	//		"movie.xyz", ".history", ".drv", ".frc", ".freq", ".pre" };
	// TODO add trajectory ascii and equil options, frequency text option, and osc format
	// It seems some of these formats don't work. I don't know if it is because
	// of the type of calculation performed (md) or the
	// type of coordinates (cartesian), but GULP either complains of a bad
	// format or generates an empty file.
	public OutputFormatsOld() {
		super();
		setBorder(new TitledBorder(null, "output file(s)",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		JCheckBox box;
		int x = 10, y = 50;
		final int width = 150, height = 20, hSpacing = 0, vSpacing = 5;
		for (int i = 0; i < labels.length; i++) {
			if (i == 9) {
				x += width + hSpacing;
				y = 25;
			}
			box = new JCheckBox(labels[i]);
			if (i == 3 || i == 13) {
				box.setBounds(x + 40, y, width - 40, height);
				box.setFont(new Font("Dialog", Font.PLAIN, 10));
				//				if (i == 13)
				//					box.setSelected(true);
			} else
				box.setBounds(x, y, width, height);
			add(box);
			y += height + vSpacing;
		}
		txtOutputFilename.setBounds(237, 22, 120, 20);
		add(txtOutputFilename);
	}

	public String writeOutputFormats() throws IncompleteOptionException {
		String line = "";
		boolean selected = false;
		for (int i = 0; i < labels.length; i++) {
			if (((JCheckBox) getComponent(i)).isSelected()) {
				selected = true;
			}
		}
		if (selected && txtOutputFilename.getText().equals(""))
			throw new IncompleteOptionException("Please enter an output filename");

		for (int i = 0; i < labels.length; i++) {
			if (((JCheckBox) getComponent(i)).isSelected()) {
				line += "output " + formats[i] + " "
				+ txtOutputFilename.getText() + Back.newLine;
			}
		}
		return line;
	}
}