package javagulp.view.bottom;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import utility.misc.SerialListener;

public class SpaceGroup extends JPanel implements Serializable {

	private static final long serialVersionUID = 1134755844499906502L;

	private class FracInt extends JPanel {

		private static final long serialVersionUID = -5357589098963042994L;

		final String[] fracInts = { "1/24", "1/12", "1/8", "1/6", "5/24",
				"1/4", "7/24", "1/3", "3/8", "5/12", "11/24", "1/2", "13/24",
				"7/12", "5/8", "2/3", "17/24", "3/4", "19/24", "5/6", "7/8",
				"11/12", "23/24" };

		private JComboBox cboFracX = new JComboBox(fracInts);
		private JComboBox cboFracY = new JComboBox(fracInts);
		private JComboBox cboFracZ = new JComboBox(fracInts);

		public FracInt() {
			super();
			setLayout(null);
			setName("fracInt");

			cboFracY.setBounds(69, 4, 65, 24);
			add(cboFracY);
			cboFracX.setBounds(3, 4, 64, 24);
			add(cboFracX);
			cboFracZ.setBounds(136, 4, 61, 24);
			add(cboFracZ);
		}

		public String getComboBoxes() {
			return cboFracX.getSelectedItem() + " "
					+ cboFracY.getSelectedItem() + " "
					+ cboFracZ.getSelectedItem();
		}
	}

	private class Number extends JPanel {

		private static final long serialVersionUID = 4481318866877519419L;

		private ButtonGroup originChoice = new ButtonGroup();

		private JRadioButton radOne = new JRadioButton("1");
		private JRadioButton radTwo = new JRadioButton("2");

		public Number() {
			super();
			setLayout(null);
			setName("number");

			originChoice.add(radOne);
			radOne.setBounds(4, 2, 55, 27);
			add(radOne);
			originChoice.add(radTwo);
			radTwo.setBounds(63, 2, 55, 27);
			add(radTwo);
		}
	}

	private class FracFloat extends JPanel {

		private static final long serialVersionUID = 1720357885366990603L;
		private JTextField txtXFracFloat = new JTextField();
		private JTextField txtYFracFloat = new JTextField();
		private JTextField txtZFracFloat = new JTextField();

		public FracFloat() {
			super();
			setLayout(null);
			setName("fracFloat");

			txtXFracFloat.setBounds(8, 6, 56, 19);
			add(txtXFracFloat);
			txtYFracFloat.setBounds(73, 6, 55, 19);
			add(txtYFracFloat);
			txtZFracFloat.setBounds(138, 6, 55, 19);
			add(txtZFracFloat);
		}
	}

	private Number number = new Number();
	private FracFloat fracFloat = new FracFloat();
	private FracInt fracInt = new FracInt();
	private JTextField txtSpaceGroup = new JTextField();

	final String[] originChoices = { "number", "fractional",
			"fractional integers" };

	private JComboBox cboOriginNumber = new JComboBox(originChoices);

	final JPanel panel = new JPanel();

	private SerialListener keyPanel = new SerialListener() {
		
		private static final long serialVersionUID = 678782875611473058L;

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (cboOriginNumber.getSelectedIndex()) {
			case 0:
				((CardLayout) panel.getLayout()).show(panel, "number");
				break;
			case 1:
				((CardLayout) panel.getLayout()).show(panel, "fracFloat");
				break;
			case 2:
				((CardLayout) panel.getLayout()).show(panel, "fracInt");
				break;
			}
		}
	};

	public SpaceGroup() {

		super();
		setLayout(null);
		final JComboBox cboNumber = new JComboBox();
		cboNumber.setActionCommand("comboBox_SpaceGroupChanged");
		cboNumber.setModel(new DefaultComboBoxModel(new String[] { "number", "symbol" }));
		cboNumber.setBounds(10, 25, 77, 24);
		add(cboNumber);
		txtSpaceGroup.setBounds(92, 27, 75, 20);
		add(txtSpaceGroup);
		JLabel lblOrigin = new JLabel("origin");
		lblOrigin.setBounds(12, 67, 39, 15);
		add(lblOrigin);

		panel.setLayout(new CardLayout());
		panel.setBounds(226, 61, 201, 30);
		add(panel);

		panel.add(number, number.getName());
		panel.add(fracFloat, fracFloat.getName());
		panel.add(fracInt, fracInt.getName());

		cboOriginNumber.addActionListener(keyPanel);
		cboOriginNumber.setSelectedIndex(0);
		cboOriginNumber.setBounds(57, 66, 164, 20);
		add(cboOriginNumber);
	}

	private String writeSpacegroup() {
		String lines = "";
		if (!txtSpaceGroup.getText().equals(""))
			lines = "spacegroup" + Back.newLine + txtSpaceGroup.getText() + Back.newLine;
		return lines;
	}

	public String writeSpaceGroup() throws IncompleteOptionException {
		return writeSpacegroup() + writeOrigin();
	}

	private String writeOrigin() throws IncompleteOptionException {
		String lines = "";
		if (cboOriginNumber.getSelectedIndex() == 0) {
			if (number.radTwo.isSelected()) {
				lines += "2";
			}
		} else if (cboOriginNumber.getSelectedIndex() == 1) {
			JTextField x = fracFloat.txtXFracFloat;
			JTextField y = fracFloat.txtYFracFloat;
			JTextField z = fracFloat.txtZFracFloat;
			if (!x.getText().equals("") || !y.getText().equals("")
					|| !z.getText().equals("")) {
				if (x.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for origin x");
				if (y.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for origin y");
				if (z.getText().equals(""))
					throw new IncompleteOptionException("Please enter a value for origin z");
				Double.parseDouble(x.getText());
				Double.parseDouble(y.getText());
				Double.parseDouble(z.getText());

				lines += x.getText() + " " + y.getText() + " " + z.getText();
			}
		} else if (cboOriginNumber.getSelectedIndex() == 2) {
			lines += fracInt.getComboBoxes();
		}
		if (!lines.equals(""))
			lines = "origin " + lines + Back.newLine;
		return lines;
	}
}