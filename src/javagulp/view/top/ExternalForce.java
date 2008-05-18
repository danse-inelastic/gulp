package javagulp.view.top;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.ExForceTableModel;
import javagulp.model.TdExForceTableModel;
import javagulp.view.Back;
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

import javagulp.model.G;
import javagulp.model.SerialListener;

public class ExternalForce extends JPanel implements Serializable {

	private static final long serialVersionUID = -6491244221778088155L;

	private G g = new G();

	private JPanel backdrop = new JPanel();

	private ButtonGroup buttonGroup = new ButtonGroup();

	public ExForceTableModel exForceTableModel = new ExForceTableModel();
	private IconHeaderRenderer iconHeaderRenderer = new IconHeaderRenderer();
	private JLabel lblDelayExternalForce = new JLabel("delay external force until (ps)");

	private JLabel lblfA = new JLabel(g.html("F = A cos[2 " + g.pi
			+ " (B t + C)]"));
	private JLabel lblRemoveExternalForce = new JLabel("remove external force at (ps)");
	private JPanel pnlexforce = new JPanel();

	private JPanel pnltdexforce = new JPanel();
	private JRadioButton radexforce = new JRadioButton("external force");

	private JRadioButton radtdexforce = new JRadioButton("time-dependent external force");
	private JScrollPane scrpaneTdexForce = new JScrollPane();

	private JScrollPane scrpaneLexforce = new JScrollPane();
	private JTable tableTdexforce = new JTable();

	private JTable tableLexforce = new JTable();
	public TdExForceTableModel tdExForceTableModel = new TdExForceTableModel();

	private JTextField txtDelayExternalForce = new JTextField();
	private JTextField txtendforce = new JTextField();
	
	private SerialListener keyexforce = new SerialListener() {
		private static final long serialVersionUID = 484346962122055547L;
		@Override
		public void actionPerformed(ActionEvent e) {
			((CardLayout) backdrop.getLayout()).show(backdrop, "exforce");
		}
	};
	private SerialListener keytdexforce = new SerialListener() {
		private static final long serialVersionUID = -1157345058515543026L;
		@Override
		public void actionPerformed(ActionEvent e) {
			((CardLayout) backdrop.getLayout()).show(backdrop, "tdexforce");
		}
	};

	public ExternalForce() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(658, 287));

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
		radexforce.setBounds(6, 0, 120, 30);
		add(radexforce);
		buttonGroup.add(radtdexforce);
		radtdexforce.addActionListener(keytdexforce);
		radtdexforce.setBounds(6, 28, 220, 25);
		add(radtdexforce);
		lblRemoveExternalForce.setBounds(256, 8, 180, 15);
		add(lblRemoveExternalForce);
		txtendforce.setBounds(446, 6, 80, 20);
		add(txtendforce);
		lblDelayExternalForce.setBounds(251, 33, 185, 15);
		add(lblDelayExternalForce);
		txtDelayExternalForce.setBounds(447, 31, 79, 19);
		add(txtDelayExternalForce);
	}

	private void setUpForceColumn(JTable table2, TableColumn forceColumn) {

		// Set up the editor for the force cells.
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("x");
		comboBox.addItem("y");
		comboBox.addItem("z");
		forceColumn.setCellEditor(new DefaultCellEditor(comboBox));

		// Set up tool tips for the force cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		forceColumn.setCellRenderer(renderer);

	}

	private String writeDelayForce() {
		String lines = "";
		if (!txtDelayExternalForce.getText().equals("")) {
			try {
				Double.parseDouble(txtDelayExternalForce.getText());
			} catch (NumberFormatException nfe) {
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
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Please enter a number for ExternalForce end.");
			}
			lines = "endforce " + txtendforce.getText() + Back.newLine;
		}
		return lines;
	}
}
