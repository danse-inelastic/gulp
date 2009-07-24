package javagulp.view.fit;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.model.SerialListener;
import javagulp.view.TitledPanel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class Pending extends JPanel implements Serializable {
	// TODO figure out why this is never used

	private static final long serialVersionUID = 9096178703227311547L;

	private final JButton btnAdd = new JButton("Add");
	private final JButton btnRemove = new JButton("Remove");

	private final JLabel label = new JLabel("i");
	private final JLabel label2 = new JLabel("j");
	private final JLabel lblDielectricConstant = new JLabel("Dielectric Constant:");
	private final JLabel lblElasticConstant = new JLabel("Elastic Constant:");
	private final JLabel lblI = new JLabel("i");
	private final JLabel lblJ = new JLabel("j");
	private final JLabel lblNumber = new JLabel("Number:");
	private final JLabel lblSelect = new JLabel("Select:");
	private final JLabel lblWeight = new JLabel("Weight:");
	private final JLabel numberLabel = new JLabel("Number:");
	private final JLabel weightLabel = new JLabel("Weight:");
	private final JLabel weightLabel_1 = new JLabel("Weight:");

	private final CardLayout observableTypes = new CardLayout();

	private final String[] observeItems = { "elastic", "hfdlc", "sdlc", "energy",
			"gradients", "static refractive index", "bulk modulus",
			"shear modulus", "weight", "piezoelectric", "monopole charges",
			"entropy", "high frequency refractive index", "phonon frequencies",
	"<html>heat capacity C<sub>V</sub></html>" };
	private final JComboBox cboObservables = new JComboBox(observeItems);

	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2_1 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel pnlOptions = new JPanel();

	private final TitledPanel pnlObservables = new TitledPanel();
	private final JScrollPane scrollPane = new JScrollPane();

	private final JTextField textField = new JTextField();
	private final JTextField txt4 = new JTextField();
	private final JTextField txt5 = new JTextField();
	private final JTextField txt6 = new JTextField();
	private final JTextField txt7 = new JTextField();
	private final JTextField txt8 = new JTextField();
	private final JTextField txt9 = new JTextField();
	private final JTextField txtI = new JTextField();
	private final JTextField txtJ = new JTextField();

	private final JList list = new JList();

	private final SerialListener keyObservables = new SerialListener() {
		private static final long serialVersionUID = 6868547295766778705L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final String selected = cboObservables.getSelectedItem().toString();
			if (selected.equals("elastic"))
				observableTypes.show(panel_1, "panel_2");
			if (selected.equals("hfdlc") || selected.equals("sdlc"))
				observableTypes.show(panel_1, "panel_2_1");
			if (selected.equals("energy")
					|| selected.equals("bulk modulus")
					|| selected.equals("shear modulus"))
				observableTypes.show(panel_1, "panel_4");
		}
	};

	public Pending() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(483, 259));

		pnlObservables.setBounds(8, 7, 471, 250);
		pnlObservables.setTitle("Observables");
		add(pnlObservables);

		lblSelect.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSelect.setBounds(8, 19, 70, 30);
		pnlObservables.add(lblSelect);
		btnAdd.setBounds(241, 21, 119, 30);
		pnlObservables.add(btnAdd);
		btnRemove.setBounds(362, 21, 103, 30);
		pnlObservables.add(btnRemove);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		panel_1.setLayout(observableTypes);
		panel_1.setBounds(8, 53, 230, 190);
		pnlObservables.add(panel_1);

		pnlOptions.setLayout(null);
		pnlOptions.setName("panel_2");
		panel_1.add(pnlOptions, pnlOptions.getName());
		textField.setBounds(57, 5, 43, 22);
		pnlOptions.add(textField);

		txtI.setBounds(110, 30, 42, 22);
		pnlOptions.add(txtI);
		txtJ.setBounds(169, 30, 43, 22);
		pnlOptions.add(txtJ);
		txt4.setBounds(110, 58, 110, 22);
		pnlOptions.add(txt4);
		numberLabel.setBounds(3, 3, 55, 22);
		pnlOptions.add(numberLabel);
		lblElasticConstant.setBounds(7, 28, 105, 21);
		pnlOptions.add(lblElasticConstant);

		label.setBounds(153, 30, 9, 22);
		pnlOptions.add(label);
		label2.setBounds(214, 30, 9, 22);
		pnlOptions.add(label2);
		weightLabel.setBounds(61, 57, 48, 22);
		pnlOptions.add(weightLabel);
		panel_2_1.setLayout(null);
		panel_2_1.setName("panel_2_1");
		panel_1.add(panel_2_1, panel_2_1.getName());

		txt5.setBounds(57, 5, 43, 22);
		panel_2_1.add(txt5);
		txt6.setBounds(127, 29, 33, 22);
		panel_2_1.add(txt6);
		txt7.setBounds(166, 29, 36, 22);
		panel_2_1.add(txt7);
		txt8.setBounds(110, 58, 110, 22);
		panel_2_1.add(txt8);

		lblNumber.setBounds(3, 3, 55, 22);
		panel_2_1.add(lblNumber);
		lblDielectricConstant.setBounds(1, 29, 126, 22);
		panel_2_1.add(lblDielectricConstant);
		lblI.setBounds(161, 31, 9, 22);
		panel_2_1.add(lblI);
		lblJ.setBounds(204, 31, 9, 22);
		panel_2_1.add(lblJ);
		weightLabel_1.setBounds(61, 57, 48, 22);
		panel_2_1.add(weightLabel_1);

		panel_4.setLayout(null);
		panel_4.setName("panel_4");
		panel_1.add(panel_4, panel_4.getName());
		lblWeight.setBounds(9, 8, 48, 22);
		panel_4.add(lblWeight);
		txt9.setBounds(59, 10, 110, 22);
		panel_4.add(txt9);

		cboObservables.setBounds(79, 23, 156, 24);
		pnlObservables.add(cboObservables);
		cboObservables.addActionListener(keyObservables);
		scrollPane.setBounds(240, 54, 224, 189);
		pnlObservables.add(scrollPane);
		scrollPane.setBorder(new CompoundBorder(null, null));
		list.setBorder(new BevelBorder(BevelBorder.LOWERED));
		scrollPane.setViewportView(list);
	}
}
