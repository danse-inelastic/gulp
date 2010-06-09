package javagulp.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.SerialListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SinglePointEnergy extends JPanel implements Serializable {

	private static final long serialVersionUID = 1481985392857820882L;


	public SinglePointEnergy() {
		super();


	}
	
	public String write(){
		return "";
	}
//	public String writeMaximise() throws IncompleteOptionException {
//		String lines = "";
//		if (radFindTransitionState.isSelected()) {
//			if (txtModeNumber.getText().equals(""))
//				throw new IncompleteOptionException("Please enter a value for transition state RFO mode.");
//			Integer.parseInt(txtModeNumber.getText());
//			lines = "maximise mode " + txtModeNumber.getText() + Back.newLine;
//		} else if (radTransitionStateOfOrder.isSelected()) {
//			final String order = txtOrderOfTransitionState.getText();
//			if (order.equals(""))
//				throw new IncompleteOptionException("Please enter a value for transition state RFO order.");
//			Integer.parseInt(order);
//			lines = "maximise order " + order + Back.newLine;
//		}
//		return lines;
//	}
}