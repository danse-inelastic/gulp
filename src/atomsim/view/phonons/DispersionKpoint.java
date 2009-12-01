package javagulp.view.phonons;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import javagulp.model.SerialKeyAdapter;
import javagulp.view.Back;
import javagulp.view.Phonons;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DispersionKpoint extends JPanel implements Serializable {

	private static final long serialVersionUID = -4857283959710322431L;

	public JTextField txtBoundX = new JTextField("0.0");
	public JTextField txtBoundY = new JTextField("0.0");
	public JTextField txtBoundZ = new JTextField("0.0");

	private final JLabel lblTo = new JLabel("to");

	private final SerialKeyAdapter pointKeyListener = new SerialKeyAdapter() {
		private static final long serialVersionUID = 1268594961778847795L;
		@Override
		public void keyReleased(KeyEvent e) {
			((Phonons)Back.getCurrentRun().getSelectedRunTypePanel("phonons")).pnlDispersion.dispersionModified = true;
		}
	};

	public DispersionKpoint() {
		setLayout(null);

		txtBoundX.setBounds(0, 0, 49, 20);
		txtBoundX.addKeyListener(pointKeyListener);
		add(txtBoundX);
		txtBoundY.setBounds(55, 0, 49, 20);
		txtBoundY.addKeyListener(pointKeyListener);
		add(txtBoundY);
		txtBoundZ.setBounds(110, 0, 49, 20);
		txtBoundZ.addKeyListener(pointKeyListener);
		add(txtBoundZ);

		lblTo.setBounds(170, 0, 30, 20);
		add(lblTo);
	}
}