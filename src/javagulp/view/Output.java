package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import javagulp.model.Nutpad;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;
import javagulp.view.output.OutputFormats;
import javagulp.view.output.Terse;



//import cseo.RB.RBSubmit;
//import cseo.RB.RBSubmitReturn;
//import cseo.jodaf.client.FilePackage;
//import cseo.jodaf.client.JODAFException;

public class Output extends JPanel implements Serializable {


	private JLabel savedInputFilesLabel;
	private JList inputFileDisplayList;
	private TitledPanel pnlDump = new TitledPanel();
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
	private JLabel lblOutputFile = new JLabel("gulp stdout file");
	private JLabel lblTimeLimit = new JLabel("calculation time limit");

	private JPanel pnlCalculationTitle = new JPanel();

	private JTextField txtCalculationTitle = new JTextField();
	private JTextField txtDumpEvery = new JTextField("1");
	private JTextField txtFort12 = new JTextField("fort.12");
	private JTextField txtInfinity = new JTextField("infinity");
	//public JTextField txtInputFile = new JTextField("input.gin");
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
	public Map<String,String> inputFileMap = new HashMap<String,String>();
	private SerialListener keyViewInput = new SerialListener() {
		
		private static final long serialVersionUID = 7165880120019172111L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectedInputFile.equals("input.gin"))
				updateInputGin();
			Nutpad nut = new Nutpad(inputFileMap.get(selectedInputFile));
			//Nutpad nut = new Nutpad(new File(Back.getPanel().getWD() + "/" + txtInputFile.getText()));
			nut.setVisible(true);
		}
	};

	public void updateInputGin(){
		String contents = Back.writer.gulpInputFileToString();
		if (!Back.writer.incomplete) {
			inputFileMap.put("input.gin",contents);
			//Back.writer.writeAll(contents, "input.gin");
			Date d = new Date();
			lastViewed = d.getTime();
		} 
	}
	
	private KeywordListener keyOutputConstraints = new KeywordListener(chkOutputConstraints, "outcon");

	public Output() {
		super();
		setLayout(null);
		//this.setPreferredSize(new java.awt.Dimension(1255, 287));

		btnViewOutput.addActionListener(keyViewOutput);
		btnViewOutput.setBounds(151, 157, 93, 20);
		add(btnViewOutput);
		cboTimeUnits.setBounds(151, 226, 93, 20);
		add(cboTimeUnits);
		lblOutputFile.setBounds(9, 160, 136, 15);
		add(lblOutputFile);
		pnlCalculationTitle.setLayout(null);
		pnlCalculationTitle.setBorder(new TitledBorder(null,
				"calculation title", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		pnlCalculationTitle.setBounds(250, 316, 330, 48);
		add(pnlCalculationTitle);
		txtCalculationTitle.setBounds(9, 20, 311, 19);
		pnlCalculationTitle.add(txtCalculationTitle);
		txtInfinity.setBounds(9, 227, 136, 20);
		add(txtInfinity);
		lblTimeLimit.setBounds(9, 206, 165, 15);
		add(lblTimeLimit);
		txtOutputFile.setBounds(9, 180, 136, 20);
		add(txtOutputFile);
		btnViewInput.addActionListener(keyViewInput);
		btnViewInput.setBounds(151, 5, 93, 20);
		add(btnViewInput);

		pnlOutputFormats.setBounds(250, 7, 330, 300);
		add(pnlOutputFormats);
		pnlTerse.setBounds(586, 134, 443, 230);
		add(pnlTerse);
		//txtInputFile.setBounds(180, 6, 152, 20);
		//add(txtInputFile);
		add(getInputFileList());
		inputFileModel.addElement("input.gin");
		add(getSavedInputFilesLabel());
		
		pnlDump.setBounds(586, 7, 443, 121);
		pnlDump.setTitle("dump file");
		txtFort12.setBackground(Back.grey);
		txtFort12.setBounds(304, 24, 118, 20);
		pnlDump.add(txtFort12);
		txtDumpEvery.setBackground(Back.grey);
		lblCycles.setBounds(236, 55, 53, 25);
		pnlDump.add(lblCycles);
		chkOutputConstraints.addActionListener(keyOutputConstraints);
		chkOutputConstraints.setBounds(67, 88, 173, 25);
		pnlDump.add(chkOutputConstraints);
		chkProduceRestartFile.setBounds(10, 21, 288, 25);
		pnlDump.add(chkProduceRestartFile);
		txtDumpEvery.setBounds(182, 58, 48, 20);
		pnlDump.add(txtDumpEvery);
		chkAfterEvery.setBounds(67, 52, 109, 30);
		pnlDump.add(chkAfterEvery);
		add(pnlDump);


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

	public DefaultListModel inputFileModel = new DefaultListModel();
	
	protected JList getInputFileList() {
		if (inputFileDisplayList == null) {
			inputFileDisplayList = new JList(inputFileModel);
			inputFileDisplayList.setBounds(9, 32, 235, 121);
			inputFileDisplayList.addMouseListener(keyList);
		}
		return inputFileDisplayList;
	}
	
	public String selectedInputFile;
	private SerialMouseAdapter keyList = new SerialMouseAdapter() {
		private static final long serialVersionUID = 5923969703181724344L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if (inputFileModel.getSize() > 0) {
				selectedInputFile = (String)inputFileDisplayList.getSelectedValue();
			}
		}
	};
	/**
	 * @return
	 */
	protected JLabel getSavedInputFilesLabel() {
		if (savedInputFilesLabel == null) {
			savedInputFilesLabel = new JLabel();
			savedInputFilesLabel.setText("gulp input files");
			savedInputFilesLabel.setBounds(9, 8, 121, 15);
		}
		return savedInputFilesLabel;
	}
	
}