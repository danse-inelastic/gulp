package javagulp.view.bottom;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.Back;
import javagulp.view.potential.AtomCombos;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.threecenter.ThreeAtomBondingOptions;
import javagulp.view.potential.twocenter.Lennard;
import javagulp.view.potential.twocenter.TwoAtomBondingOptions;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utility.misc.JCopy;
import utility.misc.SerialListener;

public class Potential extends JPanel implements Serializable {

	private static final long serialVersionUID = 7048421934237887044L;

	private String[] oneAtomPotentialList = { "spring",
			"EAM functional", "EAM density", "bond order self energy", "bsm", "cosh-spring" };
	private String[] twoAtomPotentialList = { "general potential",
			"buckingham", "lennard-jones", "morse", "harmonic", "rydberg",
			"tsuneyuki", "squared harmonic", "tersoff", "tersoff combined",
			"stillinger-weber two body", "many body", "EAM potential shift",
			"brenner", "spline", "qtaper", "qerfc", "Bond Order Charge",
			"Polynomial", "swjb2", "coulomb", "igauss", "covexp", "fermi-dirac",
			"ljbuffered", "qoverr2", "damped_dispersion" };
	private String[] threeAtomPotentialList = { "three body",
			"three body exponential harmonic", "vessal", "cosine harmonic",
			"urey-bradley", "murrell-mottram", "linear three body",
			"axilrod-teller", "exponential", "stillinger-weber three body",
			"stillinger-weber three body (JB)",
			"bond-bond-cosine(angle) cross term", "bond-bond cross term",
			"bond-bond-angle cross term", "hydrogen-bond", "equatorial" };
	private String[] fourAtomPotentialList = { "torsion", "out of plane",
			"ryckaert", "torsion-angle cross potential",
			"harmonic torsional potential",
			"exponentially decaying torsional potential",
			"torsional potential with cutoff tapering",
			"ESFF torsional potential with cutoff tapering", "Inversion" };

	private String[][] potentialAbbreviations = {
			{ "spring", "eam_functional", "eam_density", "BOSelfEnergy", "bsm", "cosh-spring" },
			{ "general", "buckingham", "lennard", "morse", "harmonic",
					"rydberg", "tsuneyuki", "squaredHarmonic", "tersoff",
					"tersoffCombine", "sw2", "manybody", "eamShift", "brenner",
					"spline", "qtaper", "qerfc", "BOCharge", "polynomial",
					"swjb2", "coulomb", "igauss", "covexp", "fermi-dirac",
					"ljbuffered", "qoverr2", "damped_dispersion" },
			{ "threebody", "threebodyExponentialHarmonic", "vessal", "cosine",
					"ureybradley", "murrellMottram", "lin3", "axilrodTeller",
					"exponential", "sw3", "sw3jb", "bcoscross", "bcross",
					"bacross", "hydrogen-bond", "equatorial" },
			{ "torsion", "outofPlane", "ryckaert", "torangle",
					"torharm", "torexp", "tortaper",
					"tortaperEsff", "inversion" } };

	private JLabel lbl1 = new JLabel("1");
	private JLabel lbl2 = new JLabel("2");
	private JLabel lbl3 = new JLabel("3");
	private JLabel lbl4 = new JLabel("4");

	public JComboBox cboCoreShellSpring = new JComboBox(oneAtomPotentialList);
	public JComboBox cboGeneralPotential = new JComboBox(twoAtomPotentialList);
	public JComboBox cboThreeBody = new JComboBox(threeAtomPotentialList);
	public JComboBox cboTorsion = new JComboBox(fourAtomPotentialList);

	private JButton btnCombinations = new JButton("<html>Generate<br>Combinations</html>");
	private JButton btnSavePotentials = new JButton("<html>Save<br>Potentials</html>");
	private JButton btnRestorePotentials = new JButton("<html>Restore<br>Potentials</html>");
	private JButton btnAddPotential = new JButton("Add Potential");
	private DefaultListModel potentialListModel = new DefaultListModel();
	public JList potentialList = new JList(potentialListModel);
	private JScrollPane listScroll = new JScrollPane(potentialList);
	public ArrayList<PotentialPanel> potentialPanels = new ArrayList<PotentialPanel>();

	private JPanel oneAtomBondingOptions = new JPanel();
	public TwoAtomBondingOptions twoAtomBondingOptions = new TwoAtomBondingOptions();
	public ThreeAtomBondingOptions threeAtomBondingOptions = new ThreeAtomBondingOptions();

	private JCheckBox chkLibrary = new JCheckBox("use library");
	private JButton btnCreateLibrary = new JButton("create library");
	private JPanel pnlPotential = new JPanel();

	public AtomCombos pnlAtom = new AtomCombos();

	public JScrollPane potentialBackdrop = new JScrollPane();
	private JScrollPane scrollBonding = new JScrollPane();

	private PotentialPanel[][] potentials = {
			{ null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null },
			{ null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null },
			{ null, null, null, null, null, null, null, null, null } };

	public int potentialNumber = 1;
	private String library;

	private SerialListener keyLibrary = new SerialListener() {
		private static final long serialVersionUID = 8698926269816312994L;

		public void actionPerformed(ActionEvent e) {
			if (chkLibrary.isSelected()) {
				JFileChooser findLibrary = new JFileChooser();
				findLibrary.setCurrentDirectory(new File(Back.getPanel().getWD()));
				if (JFileChooser.APPROVE_OPTION == findLibrary.showOpenDialog(getParent())) {
					final File newLocation = new File(Back.getPanel().getWD() + "/"
									+ findLibrary.getSelectedFile().getName());
					try {
						new JCopy().copy(findLibrary.getSelectedFile(), newLocation);
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
					library = removeDotSomething(newLocation.getName());
				} else {
					chkLibrary.setSelected(false);
				}
			}
		}
	};
	private SerialListener keyCreateLibrary = new SerialListener() {
		private static final long serialVersionUID = 6309224298033486625L;

		public void actionPerformed(ActionEvent e){
			if (potentialListModel.getSize() == 0) {
				JOptionPane.showMessageDialog(Back.frame,
						"You have not added any potentials to save.");
				return;
			}
			JFileChooser findLibrary = new JFileChooser();
			findLibrary.setCurrentDirectory(new File(Back.getPanel().getWD()));
			if (JFileChooser.APPROVE_OPTION == findLibrary.showSaveDialog(getParent())) {
				try {
					FileWriter f = new FileWriter(findLibrary.getSelectedFile());
					f.write(writePotentials());
					f.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	private SerialListener keyCombinations = new SerialListener() {
		private static final long serialVersionUID = 222500108052711638L;
		
		public void actionPerformed(ActionEvent e) {
			PotentialPanel p = getCurrentPotential();
			TreeSet<String> temp = Back.getStructure().atomicCoordinates.getTableModel().getAtoms();
			Object[] atoms = temp.toArray();
			ArrayList<ArrayList<Integer>> test = combinations(atoms.length, p.potentialNumber);
			for (int j=0; j < test.size(); j++) {
				PotentialPanel q = p.clone();
				for (int i=0; i < test.get(j).size(); i++) {
					q.atom[i] = (String) atoms[test.get(j).get(i)];
				}
				Random r = new Random();
				for (PPP ppp: q.params) {
					if (Back.getPanel().getXyzfit().chkInitialize.isSelected())
						ppp.txt.setText("" + (r.nextFloat() * (ppp.max - ppp.min) + ppp.min));
					//TODO if using lennard-jones, initialize values using geometric combination rules
				}
				String temp2 = potentialAbbreviations[q.potentialNumber - 1][getIndex()];
				potentialListModel.addElement(temp2);
				potentialPanels.add(q);
			}
		}
		
		/**
		 * This method will find all combinations of symbols and digits. For example,
		 * if 3 and 2 are used a parameters, the method will return the values
		 * [[0,0], [0,1], [0,2]]
		 * [[1,0], [1,1], [1,2]]
		 * [[2,0], [2,1], [2,2]]
		 * 
		 * @param symbols
		 * @param digits
		 * @return a list of the symbols^digits different combinations
		 */
		private ArrayList<ArrayList<Integer>> combinations(int symbols, int digits) {
			return combinations(symbols, new ArrayList<Integer>(), digits);
		}
		
		private ArrayList<ArrayList<Integer>> combinations(int symbols, ArrayList<Integer> number, int digits) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			if (number.size() == digits) {
					temp.add(number);
			} else {
				for (int j=0; j < symbols; j++) {
					ArrayList<Integer> temp2 = new ArrayList<Integer>(number);
					temp2.add(j);
					temp.addAll(combinations(symbols, temp2, digits));
				}
			}
			return temp;
		}
	};
	private SerialListener keySavePotentials = new SerialListener() {
		private static final long serialVersionUID = 222500108052711638L;
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showSaveDialog(Back.frame)) {
				try {
					ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(fileDialog.getSelectedFile()));
					int index = potentialPanels.size();
					oo.writeInt(index);
					for (int i = 0; i < index; i++) {
						oo.writeObject(potentialPanels.get(i));
						oo.writeObject((String) potentialListModel.get(i));
					}
					oo.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};
	private SerialListener keyRestorePotentials = new SerialListener() {
		private static final long serialVersionUID = 222500108052711638L;
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				try {
					ObjectInput oi = new ObjectInputStream(new FileInputStream(fileDialog.getSelectedFile()));
					int index = oi.readInt();
					for (int i = 0; i < index; i++) {
						potentialPanels.add((PotentialPanel) oi.readObject());
						potentialListModel.addElement((String) oi.readObject());
					}
					oi.close();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	};
	private SerialListener keyAddPotential = new SerialListener() {
		private static final long serialVersionUID = 222500108052711638L;
		
		public void actionPerformed(ActionEvent e) {
			PotentialPanel p = getCurrentPotential();
			boolean lennard = p.getClass().getName().equals("javagulp.view.potential.twocenter.Lennard");
			
			if (lennard && !((Lennard) p).chkAll.isSelected())
				if (checkAtoms(p))
					return;
			if (!lennard)
				if (checkAtoms(p))
					return;
			try {
				p.writePotential();
				String temp2 = potentialAbbreviations[p.potentialNumber - 1][getIndex()];
				potentialListModel.addElement(temp2);
				potentialPanels.add(p);
				PotentialPanel q = p.clone();
				potentials[p.potentialNumber - 1][getIndex()] = q;
				performSwitch(q);
			} catch (IncompleteOptionException ioe) {
				ioe.displayErrorAsPopup();
			} catch (InvalidOptionException ioe) {
				ioe.displayErrorAsPopup();
			} catch (NumberFormatException nfe) {
				if (nfe.getMessage().startsWith("Please enter a numeric value for "))
					JOptionPane.showMessageDialog(null, nfe.getMessage());
				else
					JOptionPane.showMessageDialog(null, "Please enter numeric values");
			}
		}

		private boolean checkAtoms(PotentialPanel p) {
			switch (p.potentialNumber) {
			case 4:
				if (pnlAtom.cboAtom[3].getSelectedItem() == null
						|| pnlAtom.cboAtom[3].getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Please specify atom 4.");
					return true;
				}
			case 3:
				if (pnlAtom.cboAtom[2].getSelectedItem() == null
						|| pnlAtom.cboAtom[2].getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Please specify atom 3.");
					return true;
				}
			case 2:
				if (pnlAtom.cboAtom[1].getSelectedItem() == null
						|| pnlAtom.cboAtom[1].getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Please specify atom 2.");
					return true;
				}
			case 1:
				if (pnlAtom.cboAtom[0].getSelectedItem() == null
						|| pnlAtom.cboAtom[0].getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Please specify atom 1.");
					return true;
				}
			}
			return false;
		}
	};
	private void performSwitch(PotentialPanel p) {
		potentialBackdrop.setViewportView(p);
		for (int i=0; i < 4; i++)
			pnlAtom.cboAtom[i].setEnabled(false);
		for (int i=0; i < p.potentialNumber; i++) {
			pnlAtom.cboAtom[i].setEnabled(true);
			pnlAtom.cboAtom[i].setSelectedItem(p.atom[i]);
		}
		if (p.potentialNumber == 1) {
			scrollBonding.setViewportView(oneAtomBondingOptions);
		} else if (p.potentialNumber == 2) {
			scrollBonding.setViewportView(twoAtomBondingOptions);
			twoAtomBondingOptions.setSelections(p.selected);
			twoAtomBondingOptions.setEnabled(p.enabled);
			twoAtomBondingOptions.txtScale14.setText(p.scale14);
			twoAtomBondingOptions.txtScale14.setEnabled(p.enableScale14);
		} else {
			scrollBonding.setViewportView(threeAtomBondingOptions);
			threeAtomBondingOptions.setSelections(p.selected);
			threeAtomBondingOptions.setEnabled(p.enabled);
		}
	}
	private SerialListener keyOne = new SerialListener() {
		private static final long serialVersionUID = -4007844896838907666L;
		public void actionPerformed(ActionEvent e) {
			potentialNumber = 1;
			performSwitch(getCurrentPotential());
		}
	};
	private SerialListener keyTwo = new SerialListener() {
		private static final long serialVersionUID = 7968791753203371347L;
		public void actionPerformed(ActionEvent e) {
			potentialNumber = 2;
			performSwitch(getCurrentPotential());
		}
	};
	private SerialListener keyThree = new SerialListener() {
		private static final long serialVersionUID = 6090705018849026165L;
		public void actionPerformed(ActionEvent e) {
			potentialNumber = 3;
			performSwitch(getCurrentPotential());
		}
	};
	private SerialListener keyFour = new SerialListener() {
		private static final long serialVersionUID = 2114110958141112816L;
		public void actionPerformed(ActionEvent e) {
			potentialNumber = 4;
			performSwitch(getCurrentPotential());
		}
	};
	
	private class ListKeyListener extends KeyAdapter implements Serializable {
		private static final long serialVersionUID = 2578376394974273341L;

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to remove the selected potentials?") == JOptionPane.YES_OPTION) {
					if (potentialListModel.getSize() > 0) {
						int[] indices = potentialList.getSelectedIndices();
						for (int i=indices.length-1; i >= 0 ; i--) {
							potentialListModel.remove(indices[i]);
							potentialPanels.remove(indices[i]);
						}
					}
				}
			}
		}
	};
	private ListKeyListener listKeyListener = new ListKeyListener();

	private class PotentialListener implements
		ListSelectionListener, Serializable {
		private static final long serialVersionUID = -2720144256318780471L;

		public void valueChanged(ListSelectionEvent e) {
			int index = potentialList.getSelectedIndex();
			if (potentialListModel.getSize() > 0 && index != -1) {
				performSwitch(potentialPanels.get(index));
			}
		}
	};
	private PotentialListener listMouseListener = new PotentialListener();

	public Potential() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(1255, 420));

		pnlAtom.setBounds(778, 49, 476, 42);
		add(pnlAtom);
		potentialBackdrop.setBounds(193, 98, 721, 315);
		scrollBonding.setBounds(920, 98, 329, 315);
		add(scrollBonding);
		chkLibrary.setBounds(45, 7, 91, 21);
		add(chkLibrary);
		chkLibrary.addActionListener(keyLibrary);
		btnCreateLibrary.setBounds(31, 28, 126, 21);
		add(btnCreateLibrary);
		btnCreateLibrary.addActionListener(keyCreateLibrary);
		pnlPotential.setBorder(new TitledBorder(null,
				"potentials (partitioned by number of interacting atoms)",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlPotential.setLayout(null);
		pnlPotential.setBounds(190, 0, 588, 91);
		add(pnlPotential);
		add(potentialBackdrop);
		add(listScroll);
		listScroll.setBounds(7, 91, 182, 322);
		add(btnAddPotential);
		btnAddPotential.setBounds(17, 56, 154, 28);
		add(btnCombinations);
		btnCombinations.setBounds(784, 7, 126, 42);
		btnCombinations.addActionListener(keyCombinations);
		add(btnSavePotentials);
		btnSavePotentials.setBounds(915, 7, 90, 42);
		btnSavePotentials.addActionListener(keySavePotentials);
		add(btnRestorePotentials);
		btnRestorePotentials.setBounds(1010, 7, 90, 42);
		btnRestorePotentials.addActionListener(keyRestorePotentials);
		btnAddPotential.addActionListener(keyAddPotential);
		potentialList.addKeyListener(listKeyListener);
		potentialList.addListSelectionListener(listMouseListener);
		cboCoreShellSpring.addActionListener(keyOne);
		cboGeneralPotential.addActionListener(keyTwo);
		cboThreeBody.addActionListener(keyThree);
		cboTorsion.addActionListener(keyFour);
		cboCoreShellSpring.setBounds(21, 21, 168, 28);
		cboCoreShellSpring.setMaximumRowCount(30);
		pnlPotential.add(cboCoreShellSpring);
		cboGeneralPotential.setBounds(21, 56, 196, 28);
		cboGeneralPotential.setMaximumRowCount(30);
		pnlPotential.add(cboGeneralPotential);
		cboThreeBody.setBounds(238, 21, 259, 28);
		cboThreeBody.setMaximumRowCount(30);
		pnlPotential.add(cboThreeBody);
		cboTorsion.setBounds(238, 56, 322, 28);
		cboTorsion.setMaximumRowCount(30);
		pnlPotential.add(cboTorsion);
		lbl1.setBounds(7, 21, 14, 21);
		pnlPotential.add(lbl1);
		lbl2.setBounds(7, 56, 14, 21);
		pnlPotential.add(lbl2);
		lbl3.setBounds(224, 21, 14, 21);
		pnlPotential.add(lbl3);
		lbl4.setBounds(224, 56, 14, 21);
		pnlPotential.add(lbl4);
	}

	private String removeDotSomething(final String name) {
		final String[] newName = name.split("\\.");
		return newName[0];
	}

	public String writeLibrary() throws IncompleteOptionException {
		String lines = "";
		if (chkLibrary.isSelected()) {
			lines = "library " + library;
			if (Back.getPanel().getPotentialOptions().chkDoNotInclude.isSelected())
				lines += " nodump";
			lines += Back.newLine;
		}
		return lines;
	}

	public String writePotentials() {
		StringBuffer lines = new StringBuffer();
		try {
			PotentialPanel q = (PotentialPanel) potentialBackdrop.getViewport().getView();
			for (int i = 0; i < potentialPanels.size(); i++) {
				PotentialPanel p = potentialPanels.get(i);
				potentialNumber = p.potentialNumber;
				potentialBackdrop.setViewportView(p);
				performSwitch(p);
				lines.append(p.writePotential());
			}
			potentialBackdrop.setViewportView(q);
			performSwitch(q);
		} catch (IncompleteOptionException e) {
			e.displayErrorAsPopup();
		} catch (InvalidOptionException e) {
			e.displayErrorAsPopup();
		}
		return lines.toString();
	}

	/**
	 * This method returns the atoms in the combo boxes. It will return the
	 * appropriate number for your potential. The atoms are separated by spaces,
	 * with an additional trailing space. This method assumes you have already
	 * called checkAtoms.
	 * 
	 * @return
	 */
	public String getAtomCombos() {
		String line = "";
		for (int i=0; i < potentialNumber; i++)
			line += pnlAtom.cboAtom[i].getSelectedItem() + " ";
		return line;
	}
	
	private int getIndex() {
		int index = 0;
		if (potentialNumber == 1) {
			index = cboCoreShellSpring.getSelectedIndex();
		} else if (potentialNumber == 2) {
			index = cboGeneralPotential.getSelectedIndex();
		} else if (potentialNumber == 3) {
			index = cboThreeBody.getSelectedIndex();
		} else if (potentialNumber == 4) {
			index = cboTorsion.getSelectedIndex();
		} else
			;// error
		return index;
	}
	
	//TODO There shouldn't be any difference between this and getCurrentPotential.
	public PotentialPanel getVisiblePotential() {
		return (PotentialPanel) Back.getPanel().getPotential().potentialBackdrop.getViewport().getView();
	}

	public PotentialPanel getCurrentPotential() {
		String pkg = "javagulp.view.potential.";
		if (potentialNumber == 2)
			pkg += "twocenter.";
		else if (potentialNumber == 3)
			pkg += "threecenter.";
		else if (potentialNumber == 4)
			pkg += "fourcenter.";
		
		if (potentials[potentialNumber - 1][getIndex()] == null) {
			try {
				Class c = Class.forName(pkg + classNames[potentialNumber - 1][getIndex()]);
				potentials[potentialNumber - 1][getIndex()] = (PotentialPanel) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return potentials[potentialNumber - 1][getIndex()];
	}
	
	private String[][] classNames = {
			{ "Spring", "EAMFunctional", "EAMDensity", "BondOrderSelfEnergy",
					"BSM", "CoshSpring" },
			{ "GeneralPotential", "Buckingham", "Lennard", "Morse", "Harmonic",
					"Rydberg", "Tsuneyuki", "SquaredHarmonic", "Tersoff",
					"TersoffCombine", "SW2", "Manybody", "EAMPotentialShift",
					"Brenner", "Spline", "Qtaper", "Qerfc", "BOCharge",
					"Polynomial", "SWJB2", "Coulomb", "Igauss", "CovExp",
					"FermiDirac", "LJBuffered", "QOVerr2", "DampedDispersion", },
			{ "ThreeBody", "ThreeBodyExponentialHarmonic", "Vessal",
					"CosineHarmonic", "UreyBradley", "MurrellMottram",
					"Linear3", "AxilrodTeller", "Exponential", "Sw3", "Sw3jb",
					"Bcoscross", "Bcross", "Bacross", "HydrogenBond",
					"Equatorial", },
			{ "Torsion", "OutofPlane", "Ryckaert", "Torangle", "Torharm",
					"Torexp", "Tortaper", "TortaperEsff", "Inversion" } };
}