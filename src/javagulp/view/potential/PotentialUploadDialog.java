package javagulp.view.potential;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javagulp.controller.CgiCommunicate;
import javagulp.view.Back;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class PotentialUploadDialog extends JDialog {

	public PotentialUploadDialog(Frame frame) {
		super(frame, "Input potential details", false);
		getContentPane().setLayout(new GridLayout(0, 1));

		final JPanel panel = new JPanel();
		getContentPane().add(panel);

		final JLabel label = new JLabel();
		label.setText("What is the name of your potential?");
		panel.add(label);

		final JTextField textField = new JTextField();
		textField.setColumns(20);
		panel.add(textField);

		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);

		Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		String user = cgiMap.get("sentry.username");
		final JLabel label_1 = new JLabel(user);
		label_1.setText("Who created it?");
		panel_1.add(label_1);

		final JTextField textField_1 = new JTextField();
		textField_1.setColumns(20);
		panel_1.add(textField_1);

		final JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2);

		final JLabel label_2 = new JLabel();
		label_2.setText("Description:");
		panel_2.add(label_2);

		final JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(Color.black, 1, false));
		textArea.setRows(4);
		textArea.setColumns(30);
		panel_2.add(textArea);

		final JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);

		final JLabel label_3 = new JLabel();
		label_3.setText("Locate:");
		panel_3.add(label_3);

		final JTextField textField_2 = new JTextField();
		textField_2.setColumns(30);
		panel_3.add(textField_2);

		final JButton browseButton = new JButton();
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				uploadPotential();
			}
		});
		browseButton.setText("Browse");
		panel_3.add(browseButton);
		// TODO Auto-generated constructor stub
	}

	private void uploadPotential(){
		// look for a potential on the user's machine
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//fileDialog.setCurrentDirectory(new File(txtWorkingDirectory.getText()));
		String contents;
		File potentialFile;
		if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
			potentialFile = fileDialog.getSelectedFile();//.getPath();
			//read the file
			contents = getContents(potentialFile);
		} else return;

		Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;

		String cgihome = cgiMap.get("cgihome");
		CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		Map<String, String> uploadPotentialPost = new HashMap<String, String>();
		uploadPotentialPost.put("actor", "gulpsimulationwizard");
		uploadPotentialPost.put("actor.librarycontent", contents);
		uploadPotentialPost.put("actor.libraryname", potentialFile.getName());
		uploadPotentialPost.put("actor.runtype", Back.getRunTypeKeyword());
		uploadPotentialPost.put("routine", "storePotential");
		uploadPotentialPost.putAll(cgiMap);

		// resync the list of potentials (and when coding first time, change the way the potentials
		// are treated so that it grabs the list from the server rather than keeps them in jar
		// and change the way it puts the job on the server so it doesn't put the potential library as well

		// eventually put a status bar at the bottom of the ui and report progress on it
		// getTxtVnfStatus().setText("Computation "+cgiMap.get("simulationId")+" is being submitted to vnf....");
		cgiCom.setCgiParams(uploadPotentialPost);
		String response = cgiCom.postAndGetString();
		if (response.trim().equals("success")){
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" has been successfully uploaded.");
		}else{
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" was not successfully uploaded.  Please report this to jbrkeith@gmail.com with the file attached.");
		}
		Back.getCurrentRun().getPotential().populatePotentialList();
	}
	
	/**
	 * Fetch the entire contents of a text file, and return it in a String.
	 * This style of implementation does not throw Exceptions to the caller.
	 *
	 * @param aFile is a file which already exists and can be read.
	 */
	public String getContents(File aFile) {
		//...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				String line = null; //not declared within while loop
				/*
				 * readLine is a bit quirky :
				 * it returns the content of a line MINUS the newline.
				 * it returns null only for the END of the stream.
				 * it returns an empty String if two newlines appear in a row.
				 */
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return contents.toString();
	}

}
