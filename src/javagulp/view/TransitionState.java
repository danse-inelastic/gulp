package javagulp.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.SerialListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class TransitionState extends JPanel implements Serializable {

	private static final long serialVersionUID = 1481985392857820882L;

	private final ButtonGroup btnGroupTransitionState = new ButtonGroup();
	private final ButtonGroup btnGroupRFOOptions = new ButtonGroup();

	private final JTextField txtModeNumber = new JTextField();
	private final JTextField txtOrderOfTransitionState = new JTextField("1");

	private final JRadioButton radAnyOrder = new JRadioButton();
	private final JRadioButton radFirstOrder = new JRadioButton();
	private final JRadioButton radNone = new JRadioButton();
	private final JRadioButton radFindTransitionState = new JRadioButton("find transition state along mode number");
	private final JRadioButton radTransitionStateOfOrder = new JRadioButton("or find transition state of order");

	private final SerialListener keyTransitionAlgType = new SerialListener() {
		private static final long serialVersionUID = 5665428119022612136L;
		@Override
		public void actionPerformed(ActionEvent e) {
			Back.getTaskKeywords().putOrRemoveTaskKeyword(radAnyOrder.isSelected(), "transition_state");
			Back.getTaskKeywords().putOrRemoveTaskKeyword(radFirstOrder.isSelected(), "rfo");
			//Back.getTaskKeywords().putOrRemoveTaskKeyword(radAnyOrder.isSelected(), "rfo");
		}
	};
//	private final SerialListener keyInvokeRFO = new SerialListener() {
//		private static final long serialVersionUID = -8125830214860897746L;
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			Back.getTaskKeywords().putTaskKeyword("rfo");
//			//Back.getTaskKeywords().putOrRemoveTaskKeyword(radFirstOrder.isSelected(), "transition_state");
//		}
//	};

	public TransitionState() {
		super();
		setLayout(null);
		radNone.setText("none");
		radNone.setBounds(7, 23, 120, 21);
		btnGroupRFOOptions.add(radNone);
		radNone.setSelected(true);

		final JPanel pnlTransitionState = new JPanel();
		pnlTransitionState.setBorder(new TitledBorder(null,
				"transition state search type",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlTransitionState.setLayout(null);
		pnlTransitionState.setBounds(0, 0, 835, 97);
		add(pnlTransitionState);

		radAnyOrder.setBounds(14, 60, 810, 28);
		radAnyOrder.setText("search for stationary points of any order");
		pnlTransitionState.add(radAnyOrder);
		btnGroupTransitionState.add(radAnyOrder);
		radAnyOrder.addActionListener(keyTransitionAlgType);
		
//		final JTextArea txtareaInvokeRFO = new JTextArea("invoke RFO to find the nearest stationary point with one negative hessian eigenvalue (first order transition state)");
//		txtareaInvokeRFO.setBounds(35, 105, 427, 77);
//		pnlTransitionState.add(txtareaInvokeRFO);
//		txtareaInvokeRFO.setFont(new Font("Sans", Font.BOLD, 12));
//		txtareaInvokeRFO.setOpaque(false);
//		txtareaInvokeRFO.setWrapStyleWord(true);
//		txtareaInvokeRFO.setLineWrap(true);
//		pnlTransitionState.add(txtareaInvokeTheRational);
//		txtareaInvokeTheRational.setFont(new Font("Sans", Font.BOLD, 12));
//		txtareaInvokeTheRational.setOpaque(false);
//		txtareaInvokeTheRational.setWrapStyleWord(true);
//		txtareaInvokeTheRational.setLineWrap(true);



		radFirstOrder.setBounds(14, 26, 811, 28);
		radFirstOrder.setText("find the nearest stationary point with one negative hessian eigenvalue (first order transition state)");
		pnlTransitionState.add(radFirstOrder);
		btnGroupTransitionState.add(radFirstOrder);
		radFirstOrder.addActionListener(keyTransitionAlgType);
		//this.setPreferredSize(new java.awt.Dimension(483, 287));

		final TitledPanel pnlRFOOptions = new TitledPanel();
		pnlRFOOptions.setBounds(0, 103, 835, 120);
		add(pnlRFOOptions);
		pnlRFOOptions.setTitle("rational function optimization options");
		btnGroupRFOOptions.add(radFindTransitionState);
		radFindTransitionState.setBounds(7, 56, 390, 21);
		pnlRFOOptions.add(radFindTransitionState);
		txtModeNumber.setBounds(403, 56, 63, 21);
		pnlRFOOptions.add(txtModeNumber);
		btnGroupRFOOptions.add(radTransitionStateOfOrder);
		radTransitionStateOfOrder.setBounds(7, 89, 390, 21);
		radTransitionStateOfOrder.setSelected(true);
		pnlRFOOptions.add(radTransitionStateOfOrder);
		txtOrderOfTransitionState.setBounds(403, 89, 63, 21);
		pnlRFOOptions.add(txtOrderOfTransitionState);
		pnlRFOOptions.add(radNone);

	}

	public String writeMaximise() throws IncompleteOptionException {
		String lines = "";
		if (radFindTransitionState.isSelected()) {
			if (txtModeNumber.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for transition state RFO mode.");
			Integer.parseInt(txtModeNumber.getText());
			lines = "maximise mode " + txtModeNumber.getText() + Back.newLine;
		} else if (radTransitionStateOfOrder.isSelected()) {
			final String order = txtOrderOfTransitionState.getText();
			if (order.equals(""))
				throw new IncompleteOptionException("Please enter a value for transition state RFO order.");
			Integer.parseInt(order);
			lines = "maximise order " + order + Back.newLine;
		}
		return lines;
	}
}