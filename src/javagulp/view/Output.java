package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.Nutpad;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;
import javagulp.view.output.OutputFormats;
import javagulp.view.output.Terse;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class Output extends JPanel implements Serializable {

	private static final long serialVersionUID = -4891514818536259508L;

	private final JButton btnViewInput = new JButton("view");
	private final JButton btnViewOutput = new JButton("view");

	private final JComboBox cboTimeUnits = new JComboBox(new String[] { "seconds", "minutes", "hours" });

	//private String caller_TaskID;
	public String selectedInputFile = "input.gin";
	//	private transient RBSubmitReturn submit;//inherit from this class to serialize?

	private OutputFormats pnlOutputFormats = new OutputFormats();
	private final Terse pnlTerse = new Terse();

	private final JLabel lblOutputFile = new JLabel("gulp stdout file");
	private final JLabel lblTimeLimit = new JLabel("calculation time limit");
	private JLabel savedInputFilesLabel;

	private final JPanel pnlCalculationTitle = new JPanel();

	private final JTextField txtCalculationTitle = new JTextField();
	private final JTextField txtInfinity = new JTextField("infinity");
	//public JTextField txtInputFile = new JTextField("input.gin");
	public JTextField txtOutputFile = new JTextField("output.gout");

	public long lastViewed = Long.MAX_VALUE;

	public Map<String,String> inputFileMap = new HashMap<String,String>();

	public DefaultListModel inputFileModel = new DefaultListModel();

	private final JList inputFileDisplayList = new JList(inputFileModel);

	private final SerialListener keyViewOutput = new SerialListener() {

		private static final long serialVersionUID = -8408579526286084765L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final File output = new File(Back.getCurrentRun().getWD() + Back.newLine
					+ txtOutputFile.getText());
			if (output.exists()) {
				final File f = new File(Back.getCurrentRun().getWD() + Back.newLine
						+ txtOutputFile.getText());
				final Nutpad nut = new Nutpad(f);
				nut.setLocationRelativeTo(getParent());
				nut.pack();
				nut.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, txtOutputFile.getText()
						+ " does not exist");
			}
		}
	};

	private final SerialListener keyViewInput = new SerialListener() {

		private static final long serialVersionUID = 7165880120019172111L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectedInputFile.equals(""))
				JOptionPane.showMessageDialog(null, "Please choose an input file.");
			else{
				if(selectedInputFile.equals("input.gin"))
					updateInputGin();
				final Nutpad nut = new Nutpad(inputFileMap.get(selectedInputFile));
				//Nutpad nut = new Nutpad(new File(Back.getPanel().getWD() + Back.newLine + txtInputFile.getText()));
				nut.setVisible(true);
			}
		}
	};

	public void updateInputGin(){
		final String contents = Back.writer.gulpInputFileToString();
		if (!Back.writer.incomplete) {
			inputFileMap.put("input.gin",contents); // this will replace any previous contents
			//Back.writer.writeAll(contents, "input.gin");
			final Date d = new Date();
			lastViewed = d.getTime();
		}
	}


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
		txtInfinity.setBounds(9, 227, 136, 20);
		add(txtInfinity);
		lblTimeLimit.setBounds(9, 206, 165, 15);
		add(lblTimeLimit);
		txtOutputFile.setBounds(9, 180, 136, 20);
		add(txtOutputFile);
		btnViewInput.addActionListener(keyViewInput);
		btnViewInput.setBounds(151, 5, 93, 20);
		add(btnViewInput);

		pnlOutputFormats.setBounds(250, 7, 589, 178);
		add(pnlOutputFormats);
		pnlTerse.setBounds(250, 191, 589, 227);
		add(pnlTerse);
		//txtInputFile.setBounds(180, 6, 152, 20);
		//add(txtInputFile);
		inputFileDisplayList.setBounds(9, 32, 235, 121);
		inputFileDisplayList.addMouseListener(keyList);
		inputFileModel.addElement("input.gin");
		inputFileDisplayList.setSelectedIndex(0);
		add(inputFileDisplayList);
		add(getSavedInputFilesLabel());
		pnlCalculationTitle.setBounds(9, 258, 235, 160);
		add(pnlCalculationTitle);
		pnlCalculationTitle.setLayout(null);
		pnlCalculationTitle.setBorder(new TitledBorder(null,
				"calculation description", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		txtCalculationTitle.setBounds(9, 20, 216, 130);
		pnlCalculationTitle.add(txtCalculationTitle);

		//add(pnlRestart);
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

	public String writeExecute() throws IncompleteOptionException {
		//		return pnlOutputFormats.writeOutputFormats() + writeDump()
		//				+ pnlTerse.writeTerse();// written twice
		return pnlTerse.writeTerse();// written twice
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

	private final SerialMouseAdapter keyList = new SerialMouseAdapter() {
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