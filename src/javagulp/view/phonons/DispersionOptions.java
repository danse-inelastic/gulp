package javagulp.view.phonons;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class DispersionOptions extends JPanel {

	private static final long serialVersionUID = 1389393007405803508L;


	


	private JComboBox cboBoxType = new JComboBox(new String[] {
			"dispersion curves", "density of states" });


	public DispersionOptions() {
		super();
		setLayout(null);

		setBorder(new TitledBorder(null,
				"density of states and dispersion curve options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		
		cboBoxType.setBounds(37, 50, 139, 21);
		add(cboBoxType);
	}




}
