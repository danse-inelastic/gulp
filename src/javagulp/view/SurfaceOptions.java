package javagulp.view;

import java.io.Serializable;

import javagulp.model.ListModelAddMore;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.G;

public class SurfaceOptions extends JPanel implements Serializable {

	private static final long serialVersionUID = -5321125458721087743L;

	private G g = new G();

	public ListModelAddMore coordList = new ListModelAddMore();
	private JList list = new JList(coordList);

	private JTextField txtWidth = new JTextField();
	private JTextField txtSbulkEnergy = new JTextField();
	private JTextField txtHklPlaneSpacing = new JTextField();
	private JTextField txtTotalEnergy = new JTextField();

	public SurfaceOptions() {
		super();
		setLayout(null);

		final JPanel pnlAttachmentEnergy = new JPanel();
		pnlAttachmentEnergy.setLayout(null);
		pnlAttachmentEnergy.setBorder(new TitledBorder(null,
				"attachment energy", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlAttachmentEnergy.setBounds(14, 104, 381, 61);
		add(pnlAttachmentEnergy);
		txtWidth.setBounds(177, 22, 76, 19);
		pnlAttachmentEnergy.add(txtWidth);
		JLabel lblWidthOfGrowth = new JLabel(g.html("relaxation layer width (&Aring;)"));
		lblWidthOfGrowth.setBounds(10, 24, 175, 15);
		pnlAttachmentEnergy.add(lblWidthOfGrowth);

		final JPanel pnlBulkEnergy = new JPanel();
		pnlBulkEnergy.setLayout(null);
		pnlBulkEnergy.setBorder(new TitledBorder(null,
				"bulk energy of surface atoms",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlBulkEnergy.setBounds(14, 42, 381, 56);
		add(pnlBulkEnergy);
		txtSbulkEnergy.setBounds(8, 26, 85, 20);
		pnlBulkEnergy.add(txtSbulkEnergy);
		JLabel lblev = new JLabel("eV");
		lblev.setBounds(98, 22, 29, 27);
		pnlBulkEnergy.add(lblev);

		final JPanel pnlSelectFirstAtom = new JPanel();
		pnlSelectFirstAtom.setBorder(new TitledBorder(null,
				"select first atom of bulk region (region 2)",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlSelectFirstAtom.setLayout(null);
		pnlSelectFirstAtom.setBounds(401, 42, 336, 301);
		add(pnlSelectFirstAtom);
		final JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(14, 21, 315, 273);
		pnlSelectFirstAtom.add(scrollPane);

		final JPanel pnlAttachmentEnergy2 = new JPanel();
		pnlAttachmentEnergy2.setBorder(new TitledBorder(null,
				"attachment energy", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlAttachmentEnergy2.setLayout(null);
		pnlAttachmentEnergy2.setBounds(14, 175, 381, 49);
		add(pnlAttachmentEnergy2);
		txtHklPlaneSpacing.setBounds(306, 16, 65, 20);
		pnlAttachmentEnergy2.add(txtHklPlaneSpacing);
		JLabel lblPlaneSpacing = new JLabel("hkl plane spacing (growth slice width)");
		lblPlaneSpacing.setBounds(9, 18, 291, 15);
		pnlAttachmentEnergy2.add(lblPlaneSpacing);

		final JPanel pnlTotalEnergy = new JPanel();
		pnlTotalEnergy.setLayout(null);
		pnlTotalEnergy.setBorder(new TitledBorder(null,
				"total energy of bulk unit cell",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlTotalEnergy.setBounds(14, 230, 381, 56);
		add(pnlTotalEnergy);
		txtTotalEnergy.setBounds(8, 26, 85, 20);
		pnlTotalEnergy.add(txtTotalEnergy);
		JLabel lblev2 = new JLabel("eV");
		lblev2.setBounds(98, 22, 29, 27);
		pnlTotalEnergy.add(lblev2);
	}

	public String writeSurface() {
		// TODO parse numbers?
		String lines = "";
		if (list.getSelectedIndex() != -1)
			lines += "sregion2 " + (list.getSelectedIndex() + 1) + Back.newLine;
		if (!txtHklPlaneSpacing.getText().equals(""))
			lines += "dhkl " + txtHklPlaneSpacing.getText() + Back.newLine;
		if (!txtSbulkEnergy.getText().equals(""))
			lines += "sbulkenergy " + txtSbulkEnergy.getText() + Back.newLine;
		if (!txtTotalEnergy.getText().equals(""))
			lines += "totalenergy " + txtTotalEnergy.getText() + Back.newLine;
		return lines;
	}
}