package javagulp.view;

import java.io.Serializable;

import javagulp.view.images.CreateIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class AboutBox extends JFrame implements Serializable {

	private static final long serialVersionUID = -2512309683667207622L;

	private ImageIcon icon = new CreateIcon().createIcon("banner.png");

	private JLabel lblComments = new JLabel("Designed for GULP binary 3.1");
	private JLabel lblCopyright = new JLabel("Copyright (c) 2007");
	private JLabel lblIcon = new JLabel(icon);
	private JLabel lblProduct = new JLabel("GULPUI");
	private JLabel lblVersion = new JLabel("1.0");

	public AboutBox() {
		super();
		setTitle("About");
		setLayout(null);

		add(lblProduct);
		add(lblVersion);
		add(lblCopyright);
		add(lblComments);
		add(lblIcon);

		lblProduct.setBounds(100, 0, 150, 20);
		lblVersion.setBounds(100, 20, 150, 20);
		lblCopyright.setBounds(100, 40, 150, 20);
		lblComments.setBounds(100, 60, 150, 20);
		lblIcon.setBounds(0, 0, 100, 100);
		setPreferredSize(new java.awt.Dimension(250, 100));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}