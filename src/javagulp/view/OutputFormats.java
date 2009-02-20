package javagulp.view;

import java.awt.Font;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel implements Serializable {

	private static final long serialVersionUID = -1455606819678907706L;

	private JTextField txtOutputFilename = new JTextField("output");

	private String[] labels = { "THBREL/THBPHON", "BIOSYM (.xtl)",
			"BIOSYM (.car)", "movie format", "trajectory (.trj)",
			"SIESTA (.fdf)", "CRYSTAL98 (.str)", "phonon", "Crystal (.cif)", "MARVIN (.mvn)",
			"CRYSALIS (.xr)", "CERIUS2 (.cssr)", "(.xyz)", "movie format",
			"DLPOLY history", "derivatives (.drv)", "forces (.frc)",
			"frequency", "Pressure (.pre)" };
	private String[] formats = { "thbrel", "xtl", "arc",
			"movie arc", "trajectory", "fdf", "str", "phonon", "cif", "marvin", "xr",
			"cssr", "xyz", "movie xyz", "history", "drv", "frc", "freq", "pre" };
	private String[] extensions = { ".thbrel", ".xtl", "", "movie",
			"", ".fdf", "", ".phonon", "", "", "", "", ".xyz",
			"movie.xyz", ".history", ".drv", ".frc", ".freq", ".pre" };
	// TODO add trajectory ascii and equil options, frequency text option, and osc format
	// It seems some of these formats don't work. I don't know if it is because
	// of the type of calculation performed (md) or the
	// type of coordinates (cartesian), but GULP either complains of a bad
	// format or generates an empty file.
	public OutputFormats() {
		super();
		setBorder(new TitledBorder(null, "output file(s)",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		JCheckBox box;
		int x = 10, y = 50, width = 150, height = 20, hSpacing = 0, vSpacing = 5;
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
		txtOutputFilename.setBounds(10, 25, 120, 20);
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
						+ txtOutputFilename.getText() + extensions[i] + Back.newLine;
			}
		}
		return line;
	}
}