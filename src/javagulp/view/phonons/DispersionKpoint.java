package javagulp.view.phonons;

import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DispersionKpoint extends JPanel implements Serializable {

	private static final long serialVersionUID = -4857283959710322431L;

	public JTextField txtBoundX = new JTextField("0.0");
	public JTextField txtBoundY = new JTextField("0.0");
	public JTextField txtBoundZ = new JTextField("0.0");

	private JLabel lblTo = new JLabel("to");

	public DispersionKpoint() {
		setLayout(null);

		txtBoundX.setBounds(0, 0, 25, 20);
		add(txtBoundX);
		txtBoundY.setBounds(30, 0, 25, 20);
		add(txtBoundY);
		txtBoundZ.setBounds(60, 0, 25, 20);
		add(txtBoundZ);

		lblTo.setBounds(90, 0, 30, 20);
		add(lblTo);
	}
}