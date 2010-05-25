package javagulp.view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.ExForceTableModel;
import javagulp.model.G;
import javagulp.model.SerialListener;
import javagulp.model.TdExForceTableModel;
import javagulp.view.potential.IconHeaderRenderer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class ExternalForce extends JPanel implements Serializable {

	private static final long serialVersionUID = -6491244221778088155L;

	private final G g = new G();

	private final JPanel backdrop = new JPanel();

	private final ButtonGroup buttonGroup = new ButtonGroup();

	public ExForceTableModel exForceTableModel = new ExForceTableModel();
	private final IconHeaderRenderer iconHeaderRenderer = new IconHeaderRenderer();
	private final JLabel lblDelayExternalForce = new JLabel("delay external force until (ps)");

	private final JLabel lblfA = new JLabel(g.html("F = A cos[2 " + g.pi
			+ " (B t + C)]"));
	private final JLabel lblRemoveExternalForce = new JLabel("remove external force at (ps)");
	private final JPanel pnlexforce = new JPanel();

	private final JPanel pnltdexforce = new JPanel();
	private final JRadioButton radexforce = new JRadioButton("external force");

	private final JRadioButton radtdexforce = new JRadioButton("time-dependent external force");
	private final JScrollPane scrpaneTdexForce = new JScrollPane();

	private final JScrollPane scrpaneLexforce = new JScrollPane();
	private final JTable tableTdexforce = new JTable();

	private final JTable tableLexforce = new JTable();
	public TdExForceTableModel tdExForceTableModel = new TdExForceTableModel();

	private final JTextField txtDelayExternalForce = new JTextField();
	private final JTextField txtendforce = new JTextField();

	private final SerialListener keyexforce = new SerialListener() {
		private static final long serialVersionUID = 484346962122055547L;
		@Override
		public void actionPerformed(ActionEvent e) {
			((CardLayout) backdrop.getLayout()).show(backdrop, "exforce");
		}
	};
	private final SerialListener keytdexforce = new SerialListener() {
		private static final long serialVersionUID = -1157345058515543026L;
		@Override
		public void actionPerformed(ActionEvent e) {
			((CardLayout) backdrop.getLayout()).show(backdrop, "tdexforce");
		}
	};
	
//	private final SerialKeyListener keyexforce = new SerialKeyListener() {
//		private static final long serialVersionUID = 484346962122055547L;
//		@Override
//		public void keyTyped(final KeyEvent e) {
//			((CardLayout) backdrop.getLayout()).show(backdrop, "exforce");
//		}
//	};
//	private final SerialKeyListener keytdexforce = new SerialKeyListener() {
//		private static final long serialVersionUID = -1157345058515543026L;
//		@Override
//		public void keyTyped(final KeyEvent e) {
//			((CardLayout) backdrop.getLayout()).show(backdrop, "tdexforce");
//		}
//	};

	public ExternalForce() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(658, 287));

		backdrop.setLayout(new CardLayout());
		backdrop.setBounds(0, 59, 667, 228);
		add(backdrop);
		pnlexforce.setLayout(null);
		pnlexforce.setName("exforce");
		backdrop.add(pnlexforce, pnlexforce.getName());
		scrpaneLexforce.setBounds(10, 35, 638, 180);
		pnlexforce.add(scrpaneLexforce);
		tableLexforce.setModel(exForceTableModel);
		tableLexforce.getColumnModel().getColumn(3).setHeaderRenderer(iconHeaderRenderer);
		scrpaneLexforce.setViewportView(tableLexforce);
		pnltdexforce.setLayout(null);
		pnltdexforce.setName("tdexforce");
		backdrop.add(pnltdexforce, pnltdexforce.getName());
		scrpaneTdexForce.setBounds(10, 35, 638, 180);
		pnltdexforce.add(scrpaneTdexForce);
		tableTdexforce.setModel(tdExForceTableModel);
		scrpaneTdexForce.setViewportView(tableTdexforce);
		setUpForceColumn(tableTdexforce, tableTdexforce.getColumnModel().getColumn(2));
		lblfA.setBounds(10, 10, 150, 15);
		pnltdexforce.add(lblfA);
		radexforce.setSelected(true);
		buttonGroup.add(radexforce);
		radexforce.addActionListener(keyexforce);
		//radexforce.addKeyListener(keyexforce);
		radexforce.setBounds(6, 0, 320, 30);
		add(radexforce);
		buttonGroup.add(radtdexforce);
		radtdexforce.addActionListener(keytdexforce);
		//radtdexforce.addKeyListener(keytdexforce);
		radtdexforce.setBounds(6, 28, 320, 25);
		add(radtdexforce);
		lblRemoveExternalForce.setBounds(337, 8, 244, 15);
		add(lblRemoveExternalForce);
		txtendforce.setBounds(587, 6, 80, 20);
		add(txtendforce);
		lblDelayExternalForce.setBounds(332, 33, 249, 15);
		add(lblDelayExternalForce);
		txtDelayExternalForce.setBounds(588, 31, 79, 19);
		add(txtDelayExternalForce);
	}

	private void setUpForceColumn(JTable table2, TableColumn forceColumn) {

		// Set up the editor for the force cells.
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("x");
		comboBox.addItem("y");
		comboBox.addItem("z");
		forceColumn.setCellEditor(new DefaultCellEditor(comboBox));

		// Set up tool tips for the force cells.
		final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		forceColumn.setCellRenderer(renderer);

	}

	private String writeDelayForce() {
		String lines = "";
		if (!txtDelayExternalForce.getText().equals("")) {
			try {
				Double.parseDouble(txtDelayExternalForce.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for ExternalForce delay.");
			}
			lines = "delayforce " + txtDelayExternalForce.getText() + Back.newLine;
		}
		return lines;
	}

	public String writeExternalForce() throws IncompleteOptionException {
		return tdExForceTableModel.writeTdExternalForce()
		+ exForceTableModel.writeExternalForce() + writeProduction()
		+ writeDelayForce();
	}

	private String writeProduction() {
		String lines = "";
		if (!txtendforce.getText().equals("")) {
			try {
				Double.parseDouble(txtendforce.getText());
			} catch (final NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for ExternalForce end.");
			}
			lines = "endforce " + txtendforce.getText() + Back.newLine;
		}
		return lines;
	}
}
