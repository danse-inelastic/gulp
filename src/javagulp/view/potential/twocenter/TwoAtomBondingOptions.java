package javagulp.view.potential.twocenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.ButtonGroupTransitional;
import javagulp.model.SerialListener;
import javagulp.view.Back;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class TwoAtomBondingOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = 5240761929430216593L;

	private final ButtonGroupTransitional btnGroupBonds = new ButtonGroupTransitional();
	private final ButtonGroupTransitional btnGroupInterIntra = new ButtonGroupTransitional();

	public JRadioButton radInter;
	public JRadioButton radIntra;
	public JRadioButton radBond;
	public JRadioButton radNotBond;
	public JRadioButton radNotBondOrTwoBonds;
	public JRadioButton radThreeBonds;

	public JTextField txtScale14;

	private final SerialListener keyInter = new SerialListener() {
		private static final long serialVersionUID = 2388442277166262098L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[0])
				btnGroupInterIntra.clearSelection();
			updateBooleans();
		}
	};
	private final SerialListener keyIntra = new SerialListener() {
		private static final long serialVersionUID = -1911843871257516353L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[1])
				btnGroupInterIntra.clearSelection();
			updateBooleans();
		}
	};
	private final SerialListener keyBond = new SerialListener() {
		private static final long serialVersionUID = 8520737063642238323L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[2])
				btnGroupBonds.clearSelection();
			Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().setRadiiEnabled(!radBond.isSelected());
			txtScale14.setEnabled(false);
			Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().enableScale14 = false;
			updateBooleans();
		}
	};
	private final SerialListener keyNotBond = new SerialListener() {
		private static final long serialVersionUID = 6737883603876339735L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[3])
				btnGroupBonds.clearSelection();
			txtScale14.setEnabled(false);
			Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().enableScale14 = false;
			updateBooleans();
		}
	};
	private final SerialListener keyNotBondOrTwoBonds = new SerialListener() {
		private static final long serialVersionUID = -7675603117130093609L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[4])
				btnGroupBonds.clearSelection();
			txtScale14.setEnabled(false);
			Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().enableScale14 = false;
			updateBooleans();
		}
	};
	private final SerialListener keyThreeBonds = new SerialListener() {
		private static final long serialVersionUID = 8049855880264970278L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected[5])
				btnGroupBonds.clearSelection();
			updateBooleans();
			txtScale14.setEnabled(radThreeBonds.isSelected());
			Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().enableScale14 = radThreeBonds.isSelected();
		}
	};

	public TwoAtomBondingOptions() {
		super();
		setBorder(new TitledBorder(null, "bonding options",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);

		radBond = new JRadioButton("potential only acts between bonded atoms");
		btnGroupBonds.add(radBond);
		radBond.addActionListener(keyBond);
		radBond.setBounds(454, 21, 438, 28);
		add(radBond);
		radInter = new JRadioButton("potential only between intermolecular atoms");
		btnGroupInterIntra.add(radInter);
		radInter.setBounds(10, 21, 438, 28);
		add(radInter);
		radIntra = new JRadioButton("potential only between intramolecular atoms");
		radIntra.addActionListener(keyIntra);
		btnGroupInterIntra.add(radIntra);
		radIntra.setBounds(10, 55, 438, 28);
		add(radIntra);
		radNotBond = new JRadioButton("potential does not act between bonded atoms");
		btnGroupBonds.add(radNotBond);
		radNotBond.addActionListener(keyNotBond);
		radNotBond.setBounds(454, 55, 438, 28);
		add(radNotBond);
		radNotBondOrTwoBonds = new JRadioButton("potential does not act between bonded atoms or atoms separated by two bonds");
		btnGroupBonds.add(radNotBondOrTwoBonds);
		radNotBondOrTwoBonds.addActionListener(keyNotBondOrTwoBonds);
		radNotBondOrTwoBonds.setBounds(10, 89, 701, 28);
		add(radNotBondOrTwoBonds);
		radThreeBonds = new JRadioButton("potential only acts between atoms separated by three bonds");
		btnGroupBonds.add(radThreeBonds);
		radThreeBonds.addActionListener(keyThreeBonds);
		radThreeBonds.setBounds(10, 123, 725, 28);
		add(radThreeBonds);
		txtScale14 = new JTextField();
		txtScale14.setText("0.5");
		txtScale14.setBounds(10, 157, 50, 21);
		add(txtScale14);

		final JLabel label = new JLabel();
		label.setText("1-4 scaling");
		label.setBounds(66, 157, 116, 21);
		add(label);
	}

	public void clearBondingOpts() {
		radBond.setSelected(false);
		radInter.setSelected(false);
		radIntra.setSelected(false);
		radNotBond.setSelected(false);
		radNotBondOrTwoBonds.setSelected(false);
		radThreeBonds.setSelected(false);
	}

	private void setEnabled(JComponent component, boolean bool) {
		if (bool) {
			component.setForeground(Color.black);
		} else {
			component.setForeground(Color.gray);
		}
		component.setEnabled(bool);
	}

	public void setEnabled(boolean[] bools) {
		if (bools.length != 6) {
			try {
				throw new Exception("Boolean array must be of length 6.");
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		setEnabled(radInter, bools[0]);
		setEnabled(radIntra, bools[1]);
		setEnabled(radBond, bools[2]);
		setEnabled(radNotBond, bools[3]);
		setEnabled(radNotBondOrTwoBonds, bools[4]);
		setEnabled(radThreeBonds, bools[5]);
		setEnabled(txtScale14, bools[5]);
	}

	public void setSelections(boolean[] bools) {
		if (bools.length != 6) {
			try {
				throw new Exception("Boolean array must be of length 6 for selection.");
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		if (!bools[0] && !bools[1])
			btnGroupInterIntra.clearSelection();
		else {
			radInter.setSelected(bools[0]);
			radIntra.setSelected(bools[1]);
		}
		if (!bools[2] && !bools[3] && !bools[4] && !bools[5])
			btnGroupBonds.clearSelection();
		else {
			radBond.setSelected(bools[2]);
			radNotBond.setSelected(bools[3]);
			radNotBondOrTwoBonds.setSelected(bools[4]);
			radThreeBonds.setSelected(bools[5]);
		}
	}

	private boolean[] getSelections() {
		return new boolean[] { radInter.isSelected(), radIntra.isSelected(),
				radBond.isSelected(), radNotBond.isSelected(),
				radNotBondOrTwoBonds.isSelected(), radThreeBonds.isSelected() };
	}

	private void updateBooleans() {

		Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().scale14 = txtScale14.getText();
		Back.getCurrentRun().getPotential().createLibrary.getVisiblePotential().selected = getSelections();
	}

	public String getInterIntra() {
		String line = "";
		if (radInter.isSelected())
			line += "inter ";
		if (radIntra.isSelected())
			line += "intra ";
		return line;
	}

	private String getBond() throws IncompleteOptionException {
		String line = "";
		if (radBond.isSelected()) {
			if (radInter.isSelected() || radIntra.isSelected())
				line += "bond ";
			else
				throw  new IncompleteOptionException("Please check either intermolecular or intramolecular");
		}
		if (radNotBond.isSelected())
			line += "x12 ";
		if (radNotBondOrTwoBonds.isSelected())
			line += "x13 ";
		if (radThreeBonds.isSelected())
			line += "o14 ";
		return line;
	}

	public String getScale14() {
		String line = "";
		if (!(txtScale14.getText().equals("")) && radThreeBonds.isSelected())
			line += txtScale14.getText() + " ";
		return line;
	}

	public String getAll() throws IncompleteOptionException {
		return getInterIntra() + getBond() + getScale14();
	}

	public String getInterIntraBond() throws IncompleteOptionException {
		return getInterIntra() + getBond();
	}

	public boolean Bond() {
		return radBond.isSelected();
	}
}