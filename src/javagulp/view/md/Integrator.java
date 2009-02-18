package javagulp.view.md;

import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.JComboBox;

	class Integrator extends TitledPanel {

		private static final long serialVersionUID = -5630354151968794086L;
		private String[] integrators = new String[] { "leapfrog verlet",
				"velocity verlet", "predictor corrector (NVE only)" };
		private JComboBox cboIntegrators = new JComboBox(integrators);

		Integrator() {
			setTitle("integrator");
			cboIntegrators.setBounds(9, 20, 206, 20);
			add(cboIntegrators);
		}

		String writeIntegrator() {
			String lines = "";
			// From the documentation:
			// Currently the Gear algorithm is only available in the NVE ensemble.
			if (cboIntegrators.getSelectedItem().equals(integrators[1]))
				lines = "integrator velocity verlet" + Back.newLine;
			else if (cboIntegrators.getSelectedItem().equals(integrators[2]))
				lines = "integrator gear" + Back.newLine;
			return lines;
		}
	}