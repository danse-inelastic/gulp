package javagulp.view.potential.onecenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
import javagulp.view.potential.PotentialPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class BondOrderSelfEnergy extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = -7137600395543634782L;

	private final G g = new G();

	private final JTextField txtK = new JTextField();
	private final JTextField txtQ = new JTextField();
	private final JTextField txtRho = new JTextField("1.0");

	private final JLabel lblQ = new JLabel("<html>q<sub>0</sub> (au)</html>");
	private final JLabel lblK = new JLabel("<html>K<sub>q</sub> (eV)</html>");
	private final JLabel lblRho = new JLabel(g.html(g.rho + " (au)"));

	private final JLabel lblEquation = new JLabel(g.html("E = K<sub>q</sub>exp(-" + g.rho
			+ "/(q - q<sub>0</sub>)) &nbsp; &nbsp; if q &gt; q<sub>0 &nbsp;</sub> &nbsp; when q<sub>0</sub> &gt; 0<br>"
			+ "&nbsp; &nbsp;= K<sub>q</sub>exp(" + g.rho
			+ "/(q - q<sub>0</sub>)) &nbsp; &nbsp; &nbsp; if q &gt; q<sub>0 &nbsp;</sub> &nbsp; when q<sub>0</sub> &lt; 0"));

	public BondOrderSelfEnergy() {
		super(1);
		setTitle("bond order self energy");

		lblK.setBounds(14, 81, 45, 20);
		add(lblK);
		txtK.setBounds(64, 81, 100, 20);
		add(txtK);
		lblEquation.setBounds(11, 26, 330, 40);
		add(lblEquation);
		lblQ.setBounds(15, 110, 45, 20);
		add(lblQ);
		txtQ.setBounds(64, 109, 100, 20);
		add(txtQ);
		txtRho.setBackground(Back.grey);
		txtRho.setBounds(64, 139, 100, 20);
		add(txtRho);
		lblRho.setBounds(14, 141, 35, 15);
		add(lblRho);
	}

	@Override
	public String writePotential() throws IncompleteOptionException {
		final JTextField[] fields = { txtK, txtQ };
		final String[] descriptions = { "K", "q" };
		Back.checkAllNonEmpty(fields, descriptions);
		Back.parseFieldsD(fields, descriptions);

		String lines = "boselfenergy" + Back.newLine + getAtoms() + txtK.getText() + " ";
		if (!txtRho.getText().equals("") && !txtRho.getText().equals("1.0")) {
			Double.parseDouble(txtRho.getText());
			lines += txtRho.getText() + " ";
		}
		return lines + txtQ.getText() + Back.newLine;
	}
	@Override
	public PotentialPanel clone() {
		final BondOrderSelfEnergy bo = new BondOrderSelfEnergy();
		return super.clone(bo);
	}
}