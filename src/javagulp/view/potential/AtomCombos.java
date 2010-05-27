package javagulp.view.potential;

import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class AtomCombos extends JPanel implements Serializable {

	private static final long serialVersionUID = 3417721968831207268L;

	private final JLabel lblOne = new JLabel("1");
	private final JLabel lblTwo = new JLabel("2");
	private final JLabel lblThree = new JLabel("3");
	private final JLabel lblFour = new JLabel("4");

	public JComboBox[] cboAtom = new JComboBox[4];

	//	private SerialListener keyAtom1 = new SerialListener() {
	//		private static final long serialVersionUID = 5665438920667453802L;
	//		@Override
	//		public void actionPerformed(ActionEvent e) {
	//			doit(0);
	//		}
	//	};
	//	private SerialListener keyAtom2 = new SerialListener() {
	//		private static final long serialVersionUID = -8042509382328764158L;
	//		@Override
	//		public void actionPerformed(ActionEvent e) {
	//			doit(1);
	//		}
	//	};
	//	private SerialListener keyAtom3 = new SerialListener() {
	//		private static final long serialVersionUID = -7323496484439715667L;
	//		@Override
	//		public void actionPerformed(ActionEvent e) {
	//			doit(2);
	//		}
	//	};
	//	private SerialListener keyAtom4 = new SerialListener() {
	//		private static final long serialVersionUID = -8273746518170264448L;
	//		@Override
	//		public void actionPerformed(ActionEvent e) {
	//			doit(3);
	//		}
	//	};
	//	private void doit(int number) {
	//		PotentialPanel p = Back.getPanel().getPotential().createLibrary.getVisiblePotential();
	//		//PotentialPanel p = Back.getPanel().getPotential().getCurrentPotential();
	//		if (p != null)
	//			p.atom[number] = (String) cboAtom[number].getSelectedItem();
	//	}

	public AtomCombos() {
		super();
		setLayout(null);
		setBorder(new TitledBorder(null, "select interacting atoms",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));

		for (int i=0; i < cboAtom.length; i++) {
			cboAtom[i] = new JComboBox();
			cboAtom[i].setEditable(true);
		}

		//cboAtom[0].addActionListener(keyAtom1);
		cboAtom[0].setBounds(26, 15, 94, 20);
		add(cboAtom[0]);
		//cboAtom[1].addActionListener(keyAtom2);
		cboAtom[1].setBounds(142, 15, 94, 20);
		add(cboAtom[1]);
		//cboAtom[2].addActionListener(keyAtom3);
		cboAtom[2].setBounds(262, 15, 94, 20);
		add(cboAtom[2]);
		//cboAtom[3].addActionListener(keyAtom4);
		cboAtom[3].setBounds(378, 15, 94, 20);
		add(cboAtom[3]);

		lblOne.setBounds(10, 15, 10, 20);
		add(lblOne);
		lblTwo.setBounds(126, 15, 10, 20);
		add(lblTwo);
		lblThree.setBounds(242, 15, 14, 20);
		add(lblThree);
		lblFour.setBounds(362, 15, 10, 20);
		add(lblFour);
	}
}