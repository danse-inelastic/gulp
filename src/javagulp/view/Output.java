package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.CgiCommunicate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.Nutpad;
import javagulp.model.SerialListener;
import javagulp.view.output.OutputFormats;
import javagulp.view.output.Terse;



//import cseo.RB.RBSubmit;
//import cseo.RB.RBSubmitReturn;
//import cseo.jodaf.client.FilePackage;
//import cseo.jodaf.client.JODAFException;

public class Output extends JPanel implements Serializable {


	private static final long serialVersionUID = -4891514818536259508L;


	private JButton btnViewInput = new JButton("view");
	private JButton btnViewOutput = new JButton("view");

	private JComboBox cboTimeUnits = new JComboBox(new String[] { "seconds", "minutes", "hours" });

	private String caller_TaskID;
//	private transient RBSubmitReturn submit;//inherit from this class to serialize?

	private OutputFormats pnlOutputFormats = new OutputFormats();
	private Terse pnlTerse = new Terse();

	private JCheckBox chkAfterEvery = new JCheckBox("after every");
	private JCheckBox chkOutputConstraints = new JCheckBox("output constraints");
	private JCheckBox chkProduceRestartFile = new JCheckBox("produce fitting/optimization dumpfile");

	private JLabel lblCycles = new JLabel("cycle(s)");
	private JLabel lblInputFile = new JLabel("gulp input file");
	private JLabel lblOutputFile = new JLabel("gulp stdout file");
	private JLabel lblTimeLimit = new JLabel("calculation time limit");

	private JPanel pnlCalculationTitle = new JPanel();

	private JTextField txtCalculationTitle = new JTextField();
	private JTextField txtDumpEvery = new JTextField("1");
	private JTextField txtFort12 = new JTextField("fort.12");
	private JTextField txtInfinity = new JTextField("infinity");
	public JTextField txtInputFile = new JTextField("input.gin");
	public JTextField txtOutputFile = new JTextField("output.gout");

	public long lastViewed = Long.MAX_VALUE;

	private SerialListener keyViewOutput = new SerialListener() {
		
		private static final long serialVersionUID = -8408579526286084765L;

		@Override
		public void actionPerformed(ActionEvent e) {
			File output = new File(Back.getPanel().getWD() + "/"
					+ txtOutputFile.getText());
			if (output.exists()) {
				File f = new File(Back.getPanel().getWD() + "/"
						+ txtOutputFile.getText());
				Nutpad nut = new Nutpad(f);
				nut.setLocationRelativeTo(getParent());
				nut.pack();
				nut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, txtOutputFile.getText()
						+ " does not exist");
			}
		}
	};
	private SerialListener keyViewInput = new SerialListener() {
		
		private static final long serialVersionUID = 7165880120019172111L;

		@Override
		public void actionPerformed(ActionEvent e) {
				String contents = Back.writer.gulpInputToString();
				if (!Back.writer.incomplete) {
					Back.writer.writeAll(contents, txtInputFile.getText());
					Date d = new Date();
					lastViewed = d.getTime();
					Nutpad nut = new Nutpad(new File(Back.getPanel().getWD() + "/" + txtInputFile.getText()));
					nut.setVisible(true);
				}
		}
	};

	private KeywordListener keyOutputConstraints = new KeywordListener(chkOutputConstraints, "outcon");

	public Output() {
		super();
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(1255, 287));



		btnViewOutput.addActionListener(keyViewOutput);
		btnViewOutput.setBounds(340, 34, 93, 20);
		add(btnViewOutput);
		cboTimeUnits.setBounds(340, 60, 93, 20);
		add(cboTimeUnits);
		lblOutputFile.setBounds(9, 35, 136, 15);
		add(lblOutputFile);
		pnlCalculationTitle.setLayout(null);
		pnlCalculationTitle.setBorder(new TitledBorder(null,
				"calculation title", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlCalculationTitle.setBounds(9, 187, 424, 48);
		add(pnlCalculationTitle);
		txtCalculationTitle.setBounds(9, 20, 363, 19);
		pnlCalculationTitle.add(txtCalculationTitle);
		txtInfinity.setBounds(180, 60, 152, 20);
		add(txtInfinity);
		lblTimeLimit.setBounds(9, 62, 165, 15);
		add(lblTimeLimit);
		lblInputFile.setBounds(9, 8, 136, 15);
		add(lblInputFile);
		txtOutputFile.setBounds(180, 33, 152, 20);
		add(txtOutputFile);
		btnViewInput.addActionListener(keyViewInput);
		btnViewInput.setBounds(340, 6, 93, 20);
		add(btnViewInput);

		chkProduceRestartFile.setBounds(9, 86, 300, 25);
		add(chkProduceRestartFile);
		chkAfterEvery.setBounds(50, 117, 109, 30);
		add(chkAfterEvery);
		lblCycles.setBounds(223, 120, 53, 25);
		add(lblCycles);
		txtDumpEvery.setBackground(Back.grey);
		txtDumpEvery.setBounds(165, 123, 48, 20);
		add(txtDumpEvery);
		chkOutputConstraints.addActionListener(keyOutputConstraints);
		chkOutputConstraints.setBounds(50, 153, 173, 25);
		add(chkOutputConstraints);
		txtFort12.setBackground(Back.grey);
		txtFort12.setBounds(315, 89, 118, 20);
		add(txtFort12);

		pnlOutputFormats.setBounds(439, 5, 330, 278);
		add(pnlOutputFormats);
		pnlTerse.setBounds(775, 5, 470, 277);
		add(pnlTerse);
		txtInputFile.setBounds(180, 6, 152, 20);
		add(txtInputFile);

	}


//	public void runRequestBroker(String contents) {
//		try {
//			RBSubmit gis = new RBSubmit();
//			FilePackage fp = new FilePackage(null, txtInputFile.getText(),
//					contents.getBytes());
//			gis.AddInput(fp);
//			// add other files..
//
//			// This needs to match the app name set in RB
//			gis.Configuration = "GULP";
//			caller_TaskID = gis.returnCaller_TaskID();
//			String RB_id = Back.reqboxLocal.loadApplication("Resource Broker");
//			submit = (RBSubmitReturn) Back.reqboxLocal.sendRequest(RB_id,
//					"DirectSubmit", gis, null);
//			// System.out.println(submit.err_msg);
//			// System.out.println(submit.job_id);
//		} catch (JODAFException e) {
//			e.displayErrorAsPopup();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//	}

	private String writeDump() {
		String lines = "";
		if (chkProduceRestartFile.isSelected()) {
			lines = "dump";
			if (chkAfterEvery.isSelected()
					&& !txtDumpEvery.getText().equals("1")) {
				Integer.parseInt(txtDumpEvery.getText());
				lines += " every " + txtDumpEvery.getText();
			}
			if (!txtFort12.getText().equals("fort.12")) {
				lines += " " + txtFort12.getText();
			}
			lines += Back.newLine;
		}
		return lines;
	}

	public String writeExecute() throws IncompleteOptionException {
		return pnlOutputFormats.writeOutputFormats() + writeDump()
				+ pnlTerse.writeTerse();// written twice
	}

	public String writeTitleAndTimeLimit() {
		String lines = "";
		if (!txtCalculationTitle.getText().equals("")) {
			lines = "title" + Back.newLine + txtCalculationTitle.getText() + Back.newLine + "end" + Back.newLine;
		}
		if (!txtInfinity.getText().equals("infinity")) {
			Double.parseDouble(txtInfinity.getText());
			lines += "time " + txtInfinity.getText() + " "
					+ cboTimeUnits.getSelectedItem() + Back.newLine;
		}
		return lines;
	}
	
}