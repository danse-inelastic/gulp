package javagulp.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.SerialListener;

public class TransitionState extends JPanel implements Serializable {

	private static final long serialVersionUID = 1481985392857820882L;

	private final ButtonGroup btnGroupTransitionState = new ButtonGroup();
	private final ButtonGroup btnGroupRFOOptions = new ButtonGroup();

	private JTextField txtModeNumber = new JTextField();
	private JTextField txtOrderOfTransitionState = new JTextField("1");

	private JRadioButton radInvokeRational = new JRadioButton();
	private JRadioButton radInvokeRFO = new JRadioButton();
	private JRadioButton radNone1 = new JRadioButton();
	private JRadioButton radNone2 = new JRadioButton();
	private JRadioButton radFindTransitionState = new JRadioButton("find transition state along mode number");
	private JRadioButton radTransitionStateOfOrder = new JRadioButton("or find transition state of order");

	private SerialListener keyInvokeRational = new SerialListener() {
		private static final long serialVersionUID = 5665428119022612136L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(false, "transition_state");
			Back.getKeys().putOrRemoveKeyword(radInvokeRational.isSelected(), "rfo");
		}
	};
	private SerialListener keyInvokeRFO = new SerialListener() {
		private static final long serialVersionUID = -8125830214860897746L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getKeys().putOrRemoveKeyword(false, "rfo");
			Back.getKeys().putOrRemoveKeyword(radInvokeRFO.isSelected(), "transition_state");
		}
	};

	public TransitionState() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(483, 287));

		final TitledPanel pnlRFOOptions = new TitledPanel();
		pnlRFOOptions.setTitle("RFO options");
		pnlRFOOptions.setBounds(0, 196, 476, 84);
		add(pnlRFOOptions);
		btnGroupRFOOptions.add(radFindTransitionState);
		radFindTransitionState.setBounds(7, 35, 280, 21);
		pnlRFOOptions.add(radFindTransitionState);
		txtModeNumber.setBounds(294, 35, 63, 21);
		pnlRFOOptions.add(txtModeNumber);
		btnGroupRFOOptions.add(radTransitionStateOfOrder);
		radTransitionStateOfOrder.setBounds(7, 56, 224, 21);
		pnlRFOOptions.add(radTransitionStateOfOrder);
		txtOrderOfTransitionState.setBounds(294, 56, 63, 21);
		pnlRFOOptions.add(txtOrderOfTransitionState);
		pnlRFOOptions.add(radNone2);
		radNone2.setBounds(7, 14, 21, 21);
		btnGroupRFOOptions.add(radNone2);
		radNone2.setSelected(true);

		final JPanel pnlTransitionState = new JPanel();
		pnlTransitionState.setBorder(new TitledBorder(null,
				"transition state search type",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlTransitionState.setLayout(null);
		pnlTransitionState.setBounds(0, 0, 478, 192);
		add(pnlTransitionState);

		radInvokeRational.setBounds(14, 63, 21, 28);
		pnlTransitionState.add(radInvokeRational);
		btnGroupTransitionState.add(radInvokeRational);
		radInvokeRational.addActionListener(keyInvokeRational);

		final JTextArea txtareaInvokeTheRational = new JTextArea("Invoke Rational Function Optimisation (RFO) to search for stationary points of any order. "
						+ "By default the optimizer searches for the minimum and may prove advantageous "
						+ "over the standard optimizer if the hessian is ill-conditioned.");
		txtareaInvokeTheRational.setBounds(35, 42, 427, 63);
		pnlTransitionState.add(txtareaInvokeTheRational);
		txtareaInvokeTheRational.setFont(new Font("Sans", Font.BOLD, 12));
		txtareaInvokeTheRational.setOpaque(false);
		txtareaInvokeTheRational.setWrapStyleWord(true);
		txtareaInvokeTheRational.setLineWrap(true);

		final JTextArea txtareaInvokeRFO = new JTextArea("Invoke RFO to find the nearest stationary point with one negative "
				+ "hessian eigenvalue (first order transition state). A transition state optimization will only lead to one negative phonon frequency "
				+ "if the calculation is run without any crystal symmetry.");
		txtareaInvokeRFO.setBounds(35, 105, 427, 77);
		pnlTransitionState.add(txtareaInvokeRFO);
		txtareaInvokeRFO.setFont(new Font("Sans", Font.BOLD, 12));
		txtareaInvokeRFO.setOpaque(false);
		txtareaInvokeRFO.setWrapStyleWord(true);
		txtareaInvokeRFO.setLineWrap(true);

		radInvokeRFO.setBounds(14, 119, 21, 28);
		pnlTransitionState.add(radInvokeRFO);
		btnGroupTransitionState.add(radInvokeRFO);
		pnlTransitionState.add(radNone1);
		radNone1.setBounds(14, 21, 21, 21);
		radInvokeRFO.addActionListener(keyInvokeRFO);
		btnGroupTransitionState.add(radNone1);
		radNone1.setSelected(true);

	}

	public String writeMaximise() throws IncompleteOptionException {
		String lines = "";
		if (radFindTransitionState.isSelected()) {
			if (txtModeNumber.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for transition state RFO mode.");
			Integer.parseInt(txtModeNumber.getText());
			lines = "maximise mode " + txtModeNumber.getText() + Back.newLine;
		} else if (radTransitionStateOfOrder.isSelected()) {
			String order = txtOrderOfTransitionState.getText();
			if (order.equals(""))
				throw new IncompleteOptionException("Please enter a value for transition state RFO order.");
			Integer.parseInt(order);
			lines = "maximise order " + order + Back.newLine;
		}
		return lines;
	}
}