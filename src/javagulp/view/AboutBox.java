package javagulp.view;

import java.io.Serializable;

import javagulp.view.images.CreateIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AboutBox extends JFrame implements Serializable {

	private JPanel panel;
	private static final long serialVersionUID = -2512309683667207622L;

	private ImageIcon icon = new CreateIcon().createIcon("banner.png");

	private JLabel lblComments = new JLabel("Designed for GULP binary 3.1");
	private JLabel lblCopyright = new JLabel("Copyright (c) 2009");
	private JLabel lblIcon = new JLabel(icon);
	private JLabel lblProduct = new JLabel(Back.version);

	public AboutBox() {
		super();
		setTitle("About");
		setLayout(null);
		setPreferredSize(new java.awt.Dimension(300, 130));
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

			lblProduct.setBounds(72, 13, 120, 20);
			panel.add(lblComments);
			lblComments.setBounds(72, 39, 208, 20);
			panel.add(lblIcon);
			lblIcon.setBounds(10, 13, 56, 88);
			panel.add(lblCopyright);
			lblCopyright.setBounds(73, 71, 150, 20);
		}
		return panel;
	}
}