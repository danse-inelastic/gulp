package javagulp.view.constraints;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Unfreeze extends TitledPanel {

	private static final long serialVersionUID = -3107856008613176983L;

	private final G g = new G();

	private final JTextField txtRadius = new JTextField();
	private final JTextField txtAtom = new JTextField();
	private final JTextField txtX = new JTextField();
	private final JTextField txtY = new JTextField();
	private final JTextField txtZ = new JTextField();

	private final JLabel lblAngRadiusOf = new JLabel(g.html("&Aring; radius of"));
	private final JLabel lblAllowAtomsWithin = new JLabel("optimize atoms within");

	private final JRadioButton radXYZ = new JRadioButton("coordinates");
	private final JRadioButton radAtom = new JRadioButton("atom no.");
	private final ButtonGroup group = new ButtonGroup();

	private final JRadioButton radNone;

	public Unfreeze(JRadioButton radNone) {
		super();
		setTitle("allow atoms around cluster to move");
		this.radNone = radNone;

		lblAllowAtomsWithin.setBounds(10, 20, 188, 30);
		add(lblAllowAtomsWithin);
		txtRadius.setBounds(204, 26, 50, 20);
		add(txtRadius);
		lblAngRadiusOf.setBounds(260, 20, 91, 30);
		add(lblAngRadiusOf);
		radXYZ.setBounds(10, 50, 110, 20);
		add(radXYZ);
		radAtom.setBounds(10, 75, 110, 20);
		add(radAtom);
		group.add(radXYZ);
		group.add(radAtom);
		txtAtom.setBounds(135, 75, 30, 20);
		add(txtAtom);
		txtX.setBounds(135, 50, 30, 20);
		add(txtX);
		txtY.setBounds(170, 50, 30, 20);
		add(txtY);
		txtZ.setBounds(205, 50, 30, 20);
		add(txtZ);
		radXYZ.setSelected(true);
	}

	public String writeUnfreeze() throws IncompleteOptionException {
		// From the documentation:
		// This option cannot be used with conp or conv for obvious reasons!
		String lines = "";
		if (radNone.isSelected()) {
			if (!txtRadius.getText().equals("")) {
				Double.parseDouble(txtRadius.getText());
				final String x = txtX.getText(), y = txtY.getText(), z = txtZ.getText();
				if (radXYZ.isSelected()) {
					if (!x.equals("") && !y.equals("") && !z.equals("")) {
						Double.parseDouble(x);
						Double.parseDouble(y);
						Double.parseDouble(z);

						lines = "unfreeze " + x + " " + y + " " + z + " "
						+ txtRadius.getText() + Back.newLine;
					} else {
						throw new IncompleteOptionException("Please enter coordinates around which to optimize.");
					}
				} else {
					if (!txtAtom.getText().equals("")) {
						Double.parseDouble(txtAtom.getText());

						lines = "unfreeze " + txtAtom.getText() + " "
						+ txtRadius.getText() + Back.newLine;
					} else {
						throw new IncompleteOptionException("Please enter coordinates around which to optimize.");
					}
				}
			}
		}
		return lines;
	}
}
