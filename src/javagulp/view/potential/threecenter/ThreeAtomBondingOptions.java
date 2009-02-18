package javagulp.view.potential.threecenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.ButtonGroupTransitional;
import javagulp.view.Back;
import javagulp.view.Potential;
import javagulp.view.potential.PotentialPanel;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import javagulp.model.G;
import javagulp.model.SerialListener;

public class ThreeAtomBondingOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = 1187901338934861410L;

	private ButtonGroupTransitional btnGroupInterIntra = new ButtonGroupTransitional();

	private JRadioButton radInter;
	private JRadioButton radIntra;
	private JCheckBox chkBond;
	private G g = new G();

	SerialListener keyBond = new SerialListener() {
		private static final long serialVersionUID = 8564984988556729954L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (btnGroupInterIntra.getButtonCount() == 3
					&& Back.getPanel().getPotential().getVisiblePotential().selected[2])
				btnGroupInterIntra.clearSelection();
			Back.getPanel().getPotential().getVisiblePotential().setRadiiEnabled(!chkBond.isSelected());
			updateBooleans();
		}
	};
	SerialListener keyInter = new SerialListener() {
		private static final long serialVersionUID = 1242477682514390352L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getPanel().getPotential().getVisiblePotential().selected[0])
				btnGroupInterIntra.clearSelection();
			updateBooleans();
		}
	};
	SerialListener keyIntra = new SerialListener() {
		private static final long serialVersionUID = 2178325033592023123L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getPanel().getPotential().getVisiblePotential().selected[1])
				btnGroupInterIntra.clearSelection();
			updateBooleans();
		}
	};

	public ThreeAtomBondingOptions() {
		super();
		setBorder(new TitledBorder(null, "bonding options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		setName("bondingOptions");
		chkBond = new JCheckBox(g.html("potential only acts between bonded atoms"));
		chkBond.addActionListener(keyBond);
		chkBond.setBounds(5, 77, 295, 25);
		add(chkBond);
		radInter = new JRadioButton("potential only between intermolecular atoms");
		btnGroupInterIntra.add(radInter);
		radInter.addActionListener(keyInter);
		radInter.setBounds(5, 15, 310, 25);
		add(radInter);
		radIntra = new JRadioButton("potential only between intramolecular atoms");
		radIntra.addActionListener(keyIntra);
		btnGroupInterIntra.add(radIntra);
		radIntra.setBounds(5, 46, 310, 25);
		add(radIntra);
	}

	private void setEnabled(AbstractButton btn, boolean bool) {
		if (bool) {
			btn.setForeground(Color.black);
		} else {
			btn.setForeground(Color.gray);
		}
		btn.setEnabled(bool);
	}

	public void setEnabled(boolean[] bools) {
		if (bools.length != 3) {
			System.out.println("Boolean array must be of length 3.");
			return;
		}
		setEnabled(radInter, bools[0]);
		setEnabled(radIntra, bools[1]);
		setEnabled(chkBond, bools[2]);
	}

	public void setSelections(boolean[] bools) {
		if (bools.length != 3) {
			System.out.println("Boolean array must be of length 3.");
			return;
		}

		// These four potentials require just ONE of the three, so just add it
		// to the button group
		// The only other solution is to make a FourAtomBondingOptions panel,
		// but this is easier.
		Potential pot = Back.getPanel().getPotential();
		PotentialPanel pnl = pot.getVisiblePotential();
		
		boolean torexp = pnl.getClass().getName().equals("javagulp.view.potential.fourcenter.Torexp");
		boolean tortaper = pnl.getClass().getName().equals("javagulp.view.potential.fourcenter.Tortaper");
		boolean tortaperEsff = pnl.getClass().getName().equals("javagulp.view.potential.fourcenter.TortaperEsff");
		
		if (torexp || tortaper || tortaperEsff) {
			btnGroupInterIntra.add(chkBond);

			if (!bools[0] && !bools[1] && !bools[2])
				btnGroupInterIntra.clearSelection();
			else {
				radInter.setSelected(bools[0]);
				radIntra.setSelected(bools[1]);
				chkBond.setSelected(bools[2]);
			}
		} else {
			btnGroupInterIntra.remove(chkBond);

			if (!bools[0] && !bools[1])
				btnGroupInterIntra.clearSelection();
			else {
				radInter.setSelected(bools[0]);
				radIntra.setSelected(bools[1]);
			}
			chkBond.setSelected(bools[2]);
		}
	}

	private boolean[] getSelections() {
		return new boolean[] { radInter.isSelected(), radIntra.isSelected(),
				chkBond.isSelected() };
	}

	private void updateBooleans() {
		Back.getPanel().getPotential().getVisiblePotential().selected = getSelections();
	}

	private String getInterIntra() {
		String line = "";
		if (radInter.isSelected())
			line += "inter ";
		if (radIntra.isSelected())
			line += "intra ";
		return line;
	}

	private String getBond() throws IncompleteOptionException {
		String line = "";
		if (chkBond.isSelected()){
			if (radInter.isSelected() || radIntra.isSelected())
				line += "bond ";
			else
				throw new IncompleteOptionException("Please check either intermolecular or intramolecular");
		}
		return line;
	}

	public String getAll() throws IncompleteOptionException {
		return getInterIntra() + getBond();
	}

	public boolean Bond() {
		return chkBond.isSelected();
	}
}