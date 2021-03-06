package javagulp.view.potential;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 5670454762533030375L;
	//String potentialName="";
	File potentialFile;
	private final JTextField txtPotentialName;
	private final JTextArea txtDescription;
	private final JTextField txtCreator;

	public PotentialUploadDialog(Frame frame) {
		super(frame, "Input potential details", true);
		setModal(true);
		setPreferredSize(new Dimension(800,400));
		setMinimumSize(new Dimension(800, 400));
		//getContentPane().setLayout(new GridLayout(0, 1));

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);

		final JLabel lblPotentialName = new JLabel();
		lblPotentialName.setBounds(25, 7, 270, 15);
		lblPotentialName.setText("What is the name of the potential?");
		panel.add(lblPotentialName);

		txtPotentialName = new JTextField();
		txtPotentialName.setBounds(301, 5, 224, 19);
		txtPotentialName.setColumns(20);
		panel.add(txtPotentialName);

		final JLabel lblCreator = new JLabel();
		lblCreator.setBounds(25, 28, 137, 15);
		panel.add(lblCreator);
		lblCreator.setText("Who created it?");

		final Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;
		final String user = cgiMap.get("sentry.username");
		txtCreator = new JTextField(user);
		txtCreator.setBounds(168, 26, 224, 19);
		panel.add(txtCreator);
		txtCreator.setColumns(20);

		final JLabel lblDescription = new JLabel();
		lblDescription.setBounds(25, 49, 86, 15);
		panel.add(lblDescription);
		lblDescription.setText("Description:");

		txtDescription = new JTextArea();
		txtDescription.setBounds(25, 70, 332, 47);
		panel.add(txtDescription);
		txtDescription.setBorder(new LineBorder(Color.black, 1, false));
		txtDescription.setRows(3);
		txtDescription.setColumns(30);

		final JLabel lblLocation = new JLabel();
		lblLocation.setBounds(25, 131, 105, 15);
		panel.add(lblLocation);
		lblLocation.setText("Location:");

		final JTextField txtLocation = new JTextField();
		txtLocation.setBounds(136, 129, 343, 19);
		panel.add(txtLocation);
		txtLocation.setColumns(30);

		final JButton btnBrowse = new JButton();
		btnBrowse.setBounds(485, 126, 87, 25);
		panel.add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fileDialog = new JFileChooser();
				fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
					potentialFile = fileDialog.getSelectedFile();//.getPath();
				}
				//potentialName = getPotentialFile();
				txtLocation.setText(potentialFile.getPath());
			}
		});
		btnBrowse.setText("Browse");

		final JButton okButton = new JButton();
		okButton.setBounds(25, 167, 105, 25);
		panel.add(okButton);
		okButton.addActionListener(keyOk);
		okButton.setText("upload");
		//pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	private final ActionListener keyOk = new ActionListener() {

		public void actionPerformed(final ActionEvent e) {
			sendPotentialToServer(potentialFile);
			PotentialUploadDialog.this.dispose();
			dispose();
			hide();
		}
	};

	//	private File getPotentialFile(){
	//		// look for a potential on the user's machine
	//		JFileChooser fileDialog = new JFileChooser();
	//		fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
	//		File potentialFile;
	//		if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
	//			potentialFile = fileDialog.getSelectedFile();//.getPath();
	//			return potentialFile;
	//
	//		}
	//	}

	private void sendPotentialToServer(File potentialFile){
		//read the file
		final String content = getContents(potentialFile);

		final Map<String,String> cgiMap = Back.getCurrentRun().cgiMap;

		final String cgihome = cgiMap.get("cgihome");
		final CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		final Map<String, String> uploadPotentialPost = new HashMap<String, String>();
		uploadPotentialPost.put("routine", "storePotential");

		uploadPotentialPost.put("actor", "material_simulations/forcefieldwizard");
		uploadPotentialPost.put("actor.potentialContent", content);
		uploadPotentialPost.put("actor.potential_name", txtPotentialName.getText());
		uploadPotentialPost.put("actor.potential_filename", potentialFile.getName());
		uploadPotentialPost.put("actor.potentialDescription", txtDescription.getText());
		uploadPotentialPost.put("actor.potentialCreator", txtCreator.getText());
		//uploadPotentialPost.put("actor.runtype", Back.getRunTypeKeyword());
		uploadPotentialPost.put("routine", "storePotential");
		Back.getCurrentRun().putInAuthenticationInfo(uploadPotentialPost);
		//uploadPotentialPost.putAll(cgiMap);

		// resync the list of potentials (and when coding first time, change the way the potentials
		// are treated so that it grabs the list from the server rather than keeps them in jar
		// and change the way it puts the job on the server so it doesn't put the potential library as well

		// eventually put a status bar at the bottom of the ui and report progress on it
		// getTxtVnfStatus().setText("Computation "+cgiMap.get("simulationId")+" is being submitted to vnf....");
		cgiCom.setCgiParams(uploadPotentialPost);
		final String response = cgiCom.postAndGetString();
		if (response.trim().equals("success")){
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" has been successfully uploaded.");
		}else{
			JOptionPane.showMessageDialog(Back.frame, "Library "+potentialFile.getName()+" was not successfully uploaded.  " +
					"Please report this to jbrkeith@gmail.com with the file attached.  The server returned the following response: " +
					response.trim());
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
		final StringBuilder contents = new StringBuilder();

		try {
			//use buffering, reading one line at a time
			//FileReader always assumes default encoding is OK!
			final BufferedReader input =  new BufferedReader(new FileReader(aFile));
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
		catch (final IOException ex){
			ex.printStackTrace();
		}

		return contents.toString();
	}

	//	public static void main(String[] args){
	//		//1. Create the frame.
	//		JFrame frame = new JFrame("test");
	//
	//		//2. Optional: What happens when the frame closes?
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//
	//		//3. Create components and put them in the frame.
	//		//...create emptyLabel...
	//		JButton jButton = new JButton();
	//		frame.getContentPane().add(jButton, BorderLayout.CENTER);
	//		jButton.addActionListener(new ActionListener() {
	//			public void actionPerformed(final ActionEvent e) {
	//				new PotentialUploadDialog(frame);
	//			}
	//		});
	//		jButton.setText("test");
	//
	//		//4. Size the frame.
	//		frame.pack();
	//
	//		//5. Show it.
	//		frame.setVisible(true);
	//
	//	}


}
