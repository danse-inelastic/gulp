package javagulp.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

	private final ImageIcon icon = new CreateIcon().createIcon("banner.png");

	private final JLabel lblComments = new JLabel("Designed for GULP binary 3.1");
	private final JLabel lblCopyright = new JLabel("Copyright (c) 2009");
	private final JLabel lblIcon = new JLabel(icon);
	private final JLabel lblProduct = new JLabel(Back.version);

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
			panel.setLayout(new GridBagLayout());

			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridx = 1;
			gridBagConstraints_1.gridy = 0;
			gridBagConstraints_1.ipadx = 55;
			gridBagConstraints_1.ipady = 5;
			gridBagConstraints_1.insets = new Insets(13, 12, 0, 98);
			panel.add(lblProduct, gridBagConstraints_1);

			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridx = 1;
			gridBagConstraints_2.gridy = 1;
			gridBagConstraints_2.ipadx = 25;
			gridBagConstraints_2.ipady = 5;
			gridBagConstraints_2.insets = new Insets(6, 12, 0, 10);
			panel.add(lblComments, gridBagConstraints_2);
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridheight = 3;
			gridBagConstraints.ipadx = -6;
			gridBagConstraints.ipady = 16;
			gridBagConstraints.insets = new Insets(28, 10, 22, 0);
			panel.add(lblIcon, gridBagConstraints);
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridx = 1;
			gridBagConstraints_3.gridy = 2;
			gridBagConstraints_3.ipadx = 34;
			gridBagConstraints_3.ipady = 5;
			gridBagConstraints_3.insets = new Insets(12, 13, 10, 67);
			panel.add(lblCopyright, gridBagConstraints_3);
		}
		return panel;
	}
}