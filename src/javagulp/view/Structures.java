package javagulp.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.CoordinatesTableModelInterface;
import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialMouseAdapter;
import javagulp.model.Value;
import javagulp.view.structures.AtomicCoordinates;
import javagulp.view.structures.UnitCellAndSymmetry;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Structures extends JPanel implements Serializable {
	private static final long serialVersionUID = 7137698348577497607L;

	public JTabbedPane tabs = new JTabbedPane();

	public class Structure extends JPanel implements Serializable {
		private static final long serialVersionUID = -2465509128074812336L;

		public JTabbedPane tabs = new JTabbedPane();

		public AtomicCoordinates atomicCoordinates = new AtomicCoordinates();
		public UnitCellAndSymmetry unitCellAndSymmetry = new UnitCellAndSymmetry();

		public Structure() {
			super();
			setLayout(new BorderLayout());

			tabs.add(atomicCoordinates, "atomic coordinates");
			tabs.add(unitCellAndSymmetry, "unit cell and symmetry");
			add(tabs);
		}

		public String writeStructure() throws IncompleteOptionException {
			return unitCellAndSymmetry.writeUnitCellAndSymmetry()
			+ atomicCoordinates.getTableModel().writeTether()
			+ atomicCoordinates.writeTranslate()
			+ atomicCoordinates.writeAtomicCoordinates()
			+ unitCellAndSymmetry.spaceGroup.writeSpaceGroup();
		}
	}

	private final SerialKeyAdapter keyDelete = new SerialKeyAdapter() {
		private static final long serialVersionUID = -3244021879612727287L;
		@Override
		public void keyReleased(KeyEvent e) {
			final int index = tabs.getSelectedIndex();
			if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to delete this structure?") == JOptionPane.YES_OPTION) {
					tabs.remove(index);
				}
			}
		}
	};

	private final SerialMouseAdapter keyAdd = new SerialMouseAdapter() {
		private static final long serialVersionUID = -5647308722399834435L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				addTab(new Structure());
			}
		}
	};

	public Structures() {
		super();
		setLayout(new BorderLayout());

		tabs.addKeyListener(keyDelete);
		tabs.addMouseListener(keyAdd);
		add(tabs);
		addTab(new Structure());
	}

	private void addTab(Structure s) {
		tabs.addTab("" + (tabs.getTabCount() + 1), s);
	}

	public String writeStructures() throws IncompleteOptionException {
		final StringBuffer sb = new StringBuffer();
		for (int i=0; i < tabs.getTabCount(); i++)
			sb.append(((Structure) tabs.getComponent(i)).writeStructure());
		return sb.toString();
	}

	public void importStructures(final ArrayList<ArrayList<String>> species, final ArrayList<ArrayList<double[]>> coordinates, ArrayList<String[]> args, ArrayList<String> names) {
		//tabs.removeAll();
		for (int i=0; i < args.size(); i++) {
			final Structure s = new Structure();
			s.atomicCoordinates.getTableModel().importCoordinates(species.get(i), coordinates.get(i));
			s.unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setParameters(args.get(i));
			s.atomicCoordinates.txtName.setText(names.get(i));
			addTab(s);
		}
	}

	//	public void importStructures(final FilePackage[] files) {
	//		//tabs.removeAll();
	//		for (int i=0; i < files.length; i++) {
	//			Structure s = new Structure();
	//			s.atomicCoordinates.setTable("cartesian");
	//			s.atomicCoordinates.getTableModel().importCoordinates(files[i].getFileAsString());
	//			s.atomicCoordinates.txtName.setText(files[i].getFileName());
	//			addTab(s);
	//		}
	//	}

		public void importStructures(final File[] files) {
			tabs.removeAll();
			int i=0;
			for (; i < files.length; i++) {
				try {
					Structure s = new Structure();
					s.atomicCoordinates.setTable("cartesian");
					CoordinatesTableModelInterface model = s.atomicCoordinates.getTableModel();
					model.importCoordinates(Back.getFileContents(files[i]));
					s.atomicCoordinates.txtName.setText(files[i].getName());//i
// ignore charges for now
//					File allCharges = new File(files[i].getParent() + "/allCharges.txt");
//					WorkspaceParser wp = new WorkspaceParser(files[i].getParentFile());
//					//TODO Prompt user to import charges or create a specific button.
//					Value v = null;
//					if (allCharges.exists()) {
//						v = wp.parseDataFile(allCharges).get(0);
//						for (int j=0; j < v.y.length; j++)
//							model.setValueAt(v.y[j] + "", j, 8);
//					} else {
//						if (wp.logFile.exists() && wp.parseNetCharges().size() > 0) {
//							v = wp.parseNetCharges().get(0);
//							for (int j=0; j < v.y.length; j++)
//								model.setValueAt(v.y[j] + "", j, 8);
//						}
//					}
					addTab(s);
				} catch (RuntimeException e) {
					System.out.println(files[i].getPath());
					e.printStackTrace();
				}
			}
		}
}