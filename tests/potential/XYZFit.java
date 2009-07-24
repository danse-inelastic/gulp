package potential;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.view.Back;
import javagulp.view.Execution;
import javagulp.view.fit.FitParams;
import javagulp.view.fit.StatReport;
import javagulp.view.fit.Stats;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.structures.ThreeDUnitCell;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class XYZFit extends JPanel implements Serializable {
	private static final long serialVersionUID = 936292957400705240L;

	G g = new G();

	private final JCheckBox chkPotentials = new JCheckBox("Only fit selected potentials");
	private final JCheckBox chkParameters = new JCheckBox("Only fit selected parameters");
	public JCheckBox chkInitialize = new JCheckBox("Initialize with random values");
	private final JCheckBox chkRandom = new JCheckBox("Fit parameters in random order");

	private final JLabel lblMessage = new JLabel(g.html("GULP can fit to the results of a higher quality simulation.  Simply enter an XYZ file of the simulation and a text file that contains the energy of each timestep (on separate lines)."));
	private final JLabel lblIterations = new JLabel("Perform n iterations");
	private final JLabel lblMaxSteps = new JLabel("Maximum number of timesteps");
	private final JLabel lblFireball = new JLabel("Parse Fireball log file for energies");
	private final JLabel lblGULP = new JLabel("Parse GULP output file for fit parameters");
	private final JLabel lblStart = new JLabel("Start at timestep");
	private final JLabel lblEvery = new JLabel("and sample every");
	private final JLabel lblTimesteps = new JLabel("timesteps");
	private final JLabel lblIterNber = new JLabel("");
	private final JLabel lblPercentage = new JLabel("");
	private final JLabel lblTimeElapsed = new JLabel("");

	private final JTextField txtEnergy = new JTextField();
	private final JTextField txtCharge = new JTextField();
	private final JTextField txtMaxSteps = new JTextField("1");
	private final JTextField txtXYZ = new JTextField();
	private final JTextField txtStart = new JTextField("1");
	private final JTextField txtEvery = new JTextField("1");
	private final JTextField txtIterations = new JTextField("1");

	private final JButton btnEnergy = new JButton("Energy file");
	private final JButton btnCharge = new JButton("Charge file");
	private final JButton btnXYZ = new JButton("XYZ file");
	private final JButton btnFireball = new JButton("f.log");
	private final JButton btnGULP = new JButton("GULP");
	private final JButton btnAutoFit = new JButton("Auto Fit");

	private final JProgressBar progress = new JProgressBar();
	private final Execution execution = new Execution();

	private final JCheckBox chkFractional = new JCheckBox("Fractional Coordinates");

	private final SerialListener keyEnergy = new SerialListener() {
		private static final long serialVersionUID = -436428610873861478L;
		@Override
		public void actionPerformed(ActionEvent e) {
			final File f = addFile(txtEnergy, cohesiveEnergy);
			if (f != null) {
				final WorkspaceParser wp = new WorkspaceParser(f.getParentFile());
				cohesiveEnergy = wp.parseDataFile(f);
			} else
				cohesiveEnergy = null;
		}
	};
	private final SerialListener keyCharge = new SerialListener() {
		private static final long serialVersionUID = -436428610873861478L;
		@Override
		public void actionPerformed(ActionEvent e) {
			final File f = addFile(txtCharge, netCharges);
			if (f != null) {
				final WorkspaceParser wp = new WorkspaceParser(f.getParentFile());
				netCharges = wp.parseDataFile(f);
			} else
				netCharges = null;
		}
	};
	private final SerialListener keyXYZ = new SerialListener() {
		private static final long serialVersionUID = 3323712165792485606L;
		@Override
		public void actionPerformed(ActionEvent e) {
			final File f = addFile(txtXYZ, atoms);
			if (f != null) {
				final WorkspaceParser wp = new WorkspaceParser(f.getParentFile());
				atoms = wp.readPositions();
			} else
				atoms = null;
		}
	};
	private File addFile(JTextField box, Object o) {
		final JFileChooser fileDialog = new JFileChooser();
		fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
		File f = null;
		if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
			f = fileDialog.getSelectedFile();
			box.setText(f.getPath());
		} else {
			box.setText("");
			o = null;
		}
		return f;
	}

	private ArrayList<Value> cohesiveEnergy = null;
	private ArrayList<Value> netCharges = null;
	private ArrayList<ArrayList<Atom>> atoms = null;

	private final SerialListener keyFireball = new SerialListener() {
		private static final long serialVersionUID = 2414651371471318659L;
		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(Back.getCurrentRun().getWD()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				final File file = fileDialog.getSelectedFile();
				final WorkspaceParser wp = new WorkspaceParser(file.getParentFile());
				cohesiveEnergy = wp.parseCohesiveEnergy();
				netCharges = wp.parseNetCharges();
				if (cohesiveEnergy.size() != netCharges.size())
					JOptionPane.showMessageDialog(Back.frame, "Parsing error.");

				final File f1 = new File(wp.workspace + "/energy.txt");
				wp.writeDataFile(cohesiveEnergy, f1);
				final File f2 = new File(wp.workspace + "/allCharges.txt");
				wp.writeDataFile(netCharges, f2);

				txtEnergy.setText(f1.getPath());
				txtCharge.setText(f2.getPath());
			}
		}
	};
	private final SerialListener keyGULP = new SerialListener() {
		private static final long serialVersionUID = -5140319728280092725L;

		@Override
		public void actionPerformed(ActionEvent ae) {
			final ArrayList<PPP> current = new ArrayList<PPP>();
			final CreateLibrary pot = Back.getCurrentRun().getPotential();
			for (final PotentialPanel p: pot.potentialPanels) {
				for (final PPP ppp: p.params) {
					if (ppp.chk.isSelected())
						current.add(ppp);
				}
			}
			parse(current).setVisible(true);
		}
	};
	private final SerialListener keyAuto = new SerialListener() {
		private static final long serialVersionUID = 2136166371869485840L;
		@Override
		public void actionPerformed(ActionEvent e) {
			/*try {
				Scanner sc = new Scanner(new File("/home/users/jfennick/TiC.params"));
				int lines = sc.nextInt();
				int params = sc.nextInt();
				ArrayList<double[]> values = new ArrayList<double[]>();

				for (int i=0; i < params; i++)
					values.add(new double[lines]);
				for (int i=0; i < lines; i++) {
					for (int j=0; j < params; j++) {
						values.get(j)[i] = sc.nextDouble();
					}
				}
				for (int i=0; i < values.size(); i++) {
					Stats s = new Stats(values.get(i));
					s.removeOutliers();
				}
				sc.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}*/

			final Thread t = new Thread(new Runnable() {

				public void run() {
					final ArrayList<PPP> ppps = new ArrayList<PPP>();

					//get desired PotentialPanels/PPPs
					int[] indices1 = null;
					final CreateLibrary pot = Back.getCurrentRun().getPotential();
					if (chkPotentials.isSelected() && pot.potentialList.getSelectedIndex() != -1)
						indices1 = pot.potentialList.getSelectedIndices();
					else {
						indices1 = new int[pot.potentialPanels.size()];
						for (int i=0; i < indices1.length; i++)
							indices1[i] = i;
					}

					for (final int element : indices1) {
						final PotentialPanel p = pot.potentialPanels.get(element);
						for (final PPP ppp : p.params) {
							// DO NOT REMOVE BRACKETS. For whatever reason,
							// it completely skips the add statements if the
							// scope is not explicitly stated with brackets.
							// It may be a bug in Java.  Try running with client VM.
							if (chkParameters.isSelected()) {
								if (ppp.chk.isSelected()) {
									ppps.add(ppp);
								}
							} else {
								ppps.add(ppp);
							}
							ppp.chk.setSelected(false);
						}
					}

					//progress bar initialization
					final int iter = Integer.parseInt(txtIterations.getText());
					progress.setMaximum(ppps.size() * iter);
					progress.setValue(0);

					final ArrayList<double[]> values = new ArrayList<double[]>();

					//perform calculations
					for (int i=0; i < ppps.size(); i++)
						values.add(new double[iter]);

					lblIterNber.setText("");
					addTime();
					addPercentage();
					final Random r = new Random();
					for (int i=0; i < iter; i++) {
						lblIterNber.setText("Iteration " + String.valueOf(i+1));
						// TODO perform stats after each iteration and
						// automatically determine if more iterations are
						// necessary.
						final ArrayList<Integer> indices2 = new ArrayList<Integer>();
						for (int j=0; j < ppps.size(); j++) {
							indices2.add(Integer.valueOf(j));
						}
						for (int j=0; j < ppps.size(); j++) {
							try {
								int index = j;
								if (chkRandom.isSelected()) {
									index = indices2.remove(r.nextInt(indices2.size()));
								}
								final PPP ppp = ppps.get(index);
								ppp.chk.setSelected(true);
								if (chkInitialize.isSelected()) {
									ppp.txt.setText("" + (r.nextFloat() * (ppp.max - ppp.min) + ppp.min));
								}
								Back.getCurrentRun().getOutput().keyRun.actionPerformed(null);
								final ArrayList<PPP> current = new ArrayList<PPP>();
								current.add(ppp);
								FitParams fp;
								try {
									fp = parse(current);
									if (fp != null) {
										fp.btnUpdate.doClick();
										System.out.println("fp.params.length " + fp.params.length);
										values.get(index)[i] = fp
										.params[0];
										fp.dispose();
									} else {
										values.get(index)[i] = 0;//zeros are ignored
									}
								} catch (final NumberFormatException e) {
									values.get(index)[i] = 0;//zeros are ignored
									e.printStackTrace();
								}
								ppp.chk.setSelected(false);
								progress.setValue(progress.getValue()+1);
							} catch (final Exception e) {
								e.printStackTrace();
							}
						}
					}

					lblIterNber.setText("Done");
					lblPercentage.setText("100%");

					if (chkParameters.isSelected()) {
						for (final PPP ppp: ppps)
							ppp.chk.setSelected(true);
					}

					//perform statistics on values and update mins/maxes/values
					final Stats[] stats = new Stats[values.size()];
					for (int i=0; i < values.size(); i++) {
						final Stats s = new Stats(values.get(i));
						stats[i] = s;
						final PPP ppp = ppps.get(i);
						if (s.pops.size() == 1) {
							if (s.pops.get(0).size() > 2) {
								//solution most likely found
								s.values = s.pops.get(0);
								s.recalculate();
								ppp.min = s.values.get(0).value;
								ppp.max = s.values.get(s.values.size()-1).value;
							}
						} else if (s.pops.size() == 0) {
							//oh noes!  Increase data/warn user/???
							//for now, just remove outliers.
						} else if (s.pops.size() > 1) {
							// possibly getting stuck in local minima. At
							// this point, additional checks need to be
							// performed.  for now, just use the largest population.

							int index = 0, size = 0;
							for (int j=0; j < s.pops.size(); j++) {
								if (s.pops.get(j).size() > size) {
									size = s.pops.get(j).size();
									index = j;
								}
							}
							s.values = s.pops.get(index);
							s.recalculate();
						}
						s.removeOutliers();
						ppp.txt.setText("" + s.average);
					}

					final StatReport sr = new StatReport(values, ppps, stats);
				}
			});
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		}
	};

	public void addPercentage() {
		//update percentage done in a new thread
		final Thread t = new Thread() {
			@Override
			public synchronized void run() {
				while (!(lblIterNber.getText()).equals("Done")) {
					lblPercentage.setText(String.valueOf(progress.getValue()*100/progress.getMaximum()) + "%");
					try {
						Thread.sleep(1000);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}

	public void addTime() {
		final long start = System.currentTimeMillis();
		//update elapsed time in a new thread
		final Thread t = new Thread() {
			@Override
			public synchronized void run() {
				while (!(lblIterNber.getText()).equals("Done")) {
					final String elapsed = execution.formatTimeHMS((int)((System.currentTimeMillis()-start)/1000));
					lblTimeElapsed.setText("Time elapsed: " + elapsed);
					try {
						Thread.sleep(1000);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}

	private ArrayList<String> find(Scanner sc, String s) {
		final ArrayList<String> params = new ArrayList<String>();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.equals(s)) {
				sc.nextLine();
				line = sc.nextLine().trim();
				while (!line.startsWith("-") && !line.contains("Derivative")) {
					params.add(line);
					line = sc.nextLine().trim();
				}
				return params;
			}
		}
		return params;
	}

	private FitParams parse(ArrayList<PPP> ppps) {
		FitParams fp = null;
		try {
			final String outputFile = Back.getCurrentRun().getWD()
			+ "/" + Back.getCurrentRun().getOutput().txtOutputFile.getText();
			final Scanner sc = new Scanner(new File(outputFile));
			final String p = "     Parameter No.       Parameter Value          Parameter Type  Species";
			final String e = "   Observable no.  Type            Observable   Calculated    Residual  Error(%)";

			final ArrayList<String> oldParams = find(sc, p);
			final ArrayList<String> newParams = find(sc, p);
			final ArrayList<String> error = find(sc, e);
			sc.close();

			/*String s = "";
			s += p + Back.newline;
			for (String str: oldParams)
				s += str + Back.newline;
			s += p + Back.newline;
			for (String str: newParams)
				s += str + Back.newline;
			s += e + Back.newline;
			for (String str: error)
				s += str + Back.newline;*/
			System.out.println("newParams.size() " + newParams.size());
			if (newParams.size() != 0)
				fp = new FitParams(ppps, oldParams, newParams, error);

			//Nutpad n = new Nutpad(s);
			//n.setVisible(true);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return fp;
	}

	public XYZFit() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(804, 360));

		add(lblMessage);
		lblMessage.setBounds(7, 7, 839, 70);

		add(btnXYZ);
		btnXYZ.setBounds(7, 77, 91, 28);
		btnXYZ.addActionListener(keyXYZ);
		add(btnEnergy);
		btnEnergy.setMargin(new Insets(0, 0, 0, 0));
		btnEnergy.setBounds(7, 112, 91, 28);
		btnEnergy.addActionListener(keyEnergy);
		add(btnCharge);
		btnCharge.setMargin(new Insets(0, 0, 0, 0));
		btnCharge.setBounds(7, 147, 91, 28);
		btnCharge.addActionListener(keyCharge);
		add(btnFireball);
		btnFireball.setBounds(7, 180, 91, 28);
		btnFireball.addActionListener(keyFireball);
		add(txtXYZ);
		txtXYZ.setBounds(105, 77, 339, 28);
		add(txtEnergy);
		txtEnergy.setBounds(105, 112, 339, 28);
		add(txtCharge);
		txtCharge.setBounds(105, 147, 339, 28);
		add(lblFireball);
		lblFireball.setBounds(105, 180, 301, 28);
		lblStart.setBounds(7, 215, 122, 28);
		add(lblStart);
		txtStart.setBounds(147, 216, 42, 28);
		add(txtStart);
		lblEvery.setBounds(195, 215, 140, 28);
		add(lblEvery);
		txtEvery.setBounds(341, 216, 42, 28);
		add(txtEvery);
		lblTimesteps.setBounds(388, 215, 83, 28);
		add(lblTimesteps);
		add(txtMaxSteps);
		txtMaxSteps.setBounds(7, 250, 91, 28);
		add(lblMaxSteps);
		lblMaxSteps.setBounds(105, 250, 301, 28);
		add(btnGULP);
		btnGULP.setBounds(7, 285, 91, 28);
		btnGULP.addActionListener(keyGULP);
		add(lblGULP);
		lblGULP.setBounds(105, 285, 301, 28);
		add(chkFractional);
		chkFractional.setBounds(495, 77, 351, 28);
		add(chkRandom);
		chkRandom.setSelected(true);
		chkRandom.setMargin(new Insets(0, 0, 0, 0));
		chkRandom.setBounds(495, 180, 351, 28);
		add(lblIterations);
		lblIterations.setBounds(413, 250, 168, 28);
		add(txtIterations);
		txtIterations.setBounds(581, 250, 49, 28);
		add(chkInitialize);
		chkInitialize.setSelected(true);
		chkInitialize.setBounds(495, 215, 351, 28);
		add(progress);
		progress.setBounds(7, 318, 650, 25);
		progress.setMinimum(0);
		add(lblIterNber);
		lblIterNber.setBounds(680, 285, 100, 25);
		add(btnAutoFit);
		btnAutoFit.setBounds(413, 285, 98, 28);
		btnAutoFit.addActionListener(keyAuto);
		add(lblPercentage);
		lblPercentage.setBounds(531, 285, 50, 28);
		add(lblTimeElapsed);
		lblTimeElapsed.setBounds(601, 285, 200, 28);
		add(chkPotentials);
		chkPotentials.setBounds(495, 112, 351, 28);
		add(chkParameters);
		chkParameters.setBounds(495, 147, 351, 28);

	}

	public String writeEnergy() throws IncompleteOptionException {
		String line = "";
		final String xyz = txtXYZ.getText(), energy = txtEnergy.getText();
		if (Back.getKeys().containsKeyword("fit") && !xyz.equals("") && !energy.equals("")) {
			int max = 1, start = 1, every = 1, count = 0, index = 0;
			try {
				max = Integer.parseInt(txtMaxSteps.getText());
				start = Integer.parseInt(txtStart.getText());
				every = Integer.parseInt(txtEvery.getText());
			} catch (final NumberFormatException e) {}
			final StringBuffer sb = new StringBuffer();
			index += start - 1;
			do {
				final int lines = atoms.get(index).size();
				if (chkFractional.isSelected()) {
					sb.append(Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.write3DUnitCell());
					sb.append("fractional " + lines + Back.newLine);
					final ThreeDUnitCell three = Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell;
					final double[][] vector = three.getVector();
					for (int i = 0; i < lines; i++) {
						final Atom a = atoms.get(index).get(i);
						final double[] point = three.convertToCartesian(a.p.toArray(), vector, false);
						String charge = "";
						if (netCharges != null)
							charge = " " + netCharges.get(index).y[i];
						sb.append(a.symbol + " " + point[0] + " " + point[1] + " " + point[2] + charge + Back.newLine);
					}
				} else {
					sb.append("cartesian " + lines + Back.newLine);
					for (int i = 0; i < lines; i++) {
						final Atom a = atoms.get(index).get(i);
						String charge = "";
						if (netCharges != null)
							charge = " " + netCharges.get(index).y[i];
						sb.append(a.symbol + " " + a.p + charge + Back.newLine);
					}
				}
				sb.append("observable" + Back.newLine + "energy" + Back.newLine + cohesiveEnergy.get(index).y[0] + Back.newLine + "end" + Back.newLine);
				count++;
				index += every;
			} while (count < max);
			line = sb.toString();
		}
		return line;
	}

	private class Timestep implements Serializable {
		private static final long serialVersionUID = 6414726766441077468L;

		public float[][] xyz;

		public Timestep(int numberOfAtoms) {
			xyz = new float[numberOfAtoms][3];
		}
	}

	private class Xyzfile implements Serializable {
		private static final long serialVersionUID = -6817107817356833260L;

		String[] species;
		ArrayList<Timestep> xyz;

		public Xyzfile(int numberOfAtoms) {
			species = new String[numberOfAtoms];
			xyz = new ArrayList<Timestep>();
		}
	}
}