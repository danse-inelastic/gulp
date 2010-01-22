package javagulp.view.phonons;

import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class OutputFormats extends JPanel {

	private JTextField txtOsc;
	private JTextField txtFreq;
	private static final long serialVersionUID = -8425306576850279775L;

	private JTextField txtDos;
	private JTextField txtForceConst;
	private final JCheckBox chkDos = new JCheckBox("write phonon DOS / dispersions (if any)");
	private final JCheckBox chkForceConst = new JCheckBox("write energy and force constants for QM/MM");
	private final JCheckBox chkFreq = new JCheckBox();
	private final JCheckBox chkOsc = new JCheckBox();
	final boolean vnfMode = Back.getVnfmode();

	public OutputFormats() {
		setBorder(new TitledBorder(null, "output formats",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		setLayout(null);
				
		{
			chkDos.setBounds(10, 23, 352, 22);
			if(vnfMode){
				chkDos.setSelected(true);
				chkDos.setEnabled(false);
			}
			add(chkDos);
		}
		{
			txtDos = new JTextField();
			if(vnfMode){
				txtDos.setText("dos.dens");
				txtDos.setEnabled(false);
			}
			txtDos.setBounds(368, 24, 260, 21);
			add(txtDos);
			txtDos.setColumns(10);
		}
		{
			chkForceConst.setBounds(10, 107, 352, 22);
			add(chkForceConst);
		}
		{
			txtForceConst = new JTextField();
			txtForceConst.setColumns(10);
			txtForceConst.setBounds(368, 108, 260, 21);
			add(txtForceConst);
		}

		{
			chkFreq.setText("write frequencies for Gruneisen parameter");
			chkFreq.setBounds(10, 51, 352, 22);
			add(chkFreq);
		}

		{
			txtFreq = new JTextField();
			txtFreq.setColumns(10);
			txtFreq.setBounds(368, 52, 260, 21);
			add(txtFreq);
		}

		{
			chkOsc.setText("write oscillator strengths for phonon modes");
			chkOsc.setBounds(10, 79, 352, 22);
			add(chkOsc);
		}

		{
			txtOsc = new JTextField();
			txtOsc.setColumns(10);
			txtOsc.setBounds(368, 81, 260, 21);
			add(txtOsc);
		}
	}


	public String writeOutputFormats() throws IncompleteOptionException {
		String lines = "";

		if (chkDos.isSelected() && txtDos.getText().equals(""))
			throw new IncompleteOptionException("Please enter a Dos/Dispersion output filename");
		if (chkDos.isSelected()) {
			lines += "output phonon "
				+ txtDos.getText() + Back.newLine;
		}
		if (chkFreq.isSelected() && txtFreq.getText().equals(""))
			throw new IncompleteOptionException("Please enter a phonon frequency file name");
		if (chkFreq.isSelected()) {
			lines += "output frequency "
				+ txtFreq.getText() + Back.newLine;
		}
		if (chkOsc.isSelected() && txtOsc.getText().equals(""))
			throw new IncompleteOptionException("Please enter an oscillator strength file name");
		if (chkOsc.isSelected()) {
			lines += "output osc "
				+ txtOsc.getText() + Back.newLine;
		}
		if (chkForceConst.isSelected() && txtForceConst.getText().equals(""))
			throw new IncompleteOptionException("Please enter a force constant output file name");
		if (chkForceConst.isSelected()) {
			lines += "output frc "
				+ txtForceConst.getText() + Back.newLine;
		}
		//		if (!txtWrite.getText().equals("")) {
		//			if (cboUnits.getSelectedItem().equals("timesteps")) {
		//				try {
		//					Integer.parseInt(txtWrite.getText());
		//				} catch (final NumberFormatException nfe) {
		//					throw new NumberFormatException("Please enter an integer for MD status write frequency.");
		//				}
		//				lines = "write " + txtWrite.getText() + Back.newLine;
		//			} else {
		//				try {
		//					Double.parseDouble(txtWrite.getText());
		//				} catch (final NumberFormatException nfe) {
		//					throw new NumberFormatException("Please enter a number for MD status write frequency.");
		//				}
		//				lines = "write " + txtWrite.getText() + " "
		//				+ cboUnits.getSelectedItem() + Back.newLine;
		//			}
		//		}
		return lines;
	}

	//		public String writeMDarchive() {
	//			String lines = "";
	//			if (!txtMdarchive.getText().equals("")) {
	//				lines = "mdarchive " + txtMdarchive.getText() + Back.newLine;
	//			}
	//			return lines;
	//		}
}
