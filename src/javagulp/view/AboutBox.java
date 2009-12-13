package javagulp.view;

import java.io.Serializable;

import javagulp.view.images.CreateIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AboutBox extends JFrame implements Serializable {

	private JLabel lblProduct_1;
	private JPanel panel;
	private static final long serialVersionUID = -2512309683667207622L;

	private final ImageIcon icon = new CreateIcon().createIcon("banner.png");

	private final JLabel lblComments = new JLabel("GULP 3.1");
	private final JLabel lblCopyright = new JLabel("Copyright (c) 2009");
	private final JLabel lblIcon = new JLabel(icon);
	private final JLabel lblProduct = new JLabel(Back.version);

	public AboutBox() {
		super();
		setTitle("About");
		setLayout(null);
		setPreferredSize(new java.awt.Dimension(400, 200));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().add(getPanel());
	}
	/**
	 * @return
	 */
	protected JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(lblProduct);
			lblProduct.setBounds(10, 10, 135, 20);
			panel.add(lblComments);
			lblComments.setBounds(66, 89, 208, 20);
			panel.add(lblIcon);
			lblIcon.setBounds(10, 74, 50, 51);
			panel.add(lblCopyright);
			lblCopyright.setBounds(10, 143, 150, 20);
			panel.add(getLblProduct_1());
		}
		return panel;
	}
	/**
	 * @return
	 */
	protected JLabel getLblProduct_1() {
		if (lblProduct_1 == null) {
			lblProduct_1 = new JLabel();
			lblProduct_1.setText("contains:");
			lblProduct_1.setBounds(10, 48, 135, 20);
		}
		return lblProduct_1;
	}
}