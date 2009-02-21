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

import javagulp.controller.IncompleteOptionException;

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

import com.sshtools.common.hosts.DialogKnownHostsKeyVerification;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.TransferCancelledException;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.io.IOStreamConnector;
import com.sshtools.j2ssh.session.SessionChannelClient;

//import cseo.RB.RBSubmit;
//import cseo.RB.RBSubmitReturn;
//import cseo.jodaf.client.FilePackage;
//import cseo.jodaf.client.JODAFException;

public class Output extends JPanel implements Serializable {

	private JButton runAtVnfButton;
	private static final long serialVersionUID = -4891514818536259508L;

	private JButton btnClear = new JButton("clear");
	private JButton btnRun = new JButton("run");
	private JButton btnViewInput = new JButton("view");
	private JButton btnViewOutput = new JButton("view");

	private JComboBox cboTimeUnits = new JComboBox(new String[] { "seconds", "minutes", "hours" });
	public JCheckBox chkSeparate = new JCheckBox("Put each structure in a separate input file");

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
	private JLabel lblStatus = new JLabel();

	private JPanel pnlCalculationTitle = new JPanel();

	private JTextField txtCalculationTitle = new JTextField();
	private JTextField txtDumpEvery = new JTextField("1");
	private JTextField txtFort12 = new JTextField("fort.12");
	private JTextField txtInfinity = new JTextField("infinity");
	public JTextField txtInputFile = new JTextField("input.gin");
	public JTextField txtOutputFile = new JTextField("output.gout");

	private long lastViewed = Long.MAX_VALUE;
	public String contents = "";
	Process gulpProcess;

	protected JButton getRunAtVnfButton() {
		if (runAtVnfButton == null) {
			runAtVnfButton = new JButton();
			runAtVnfButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					//write file
					String gulpInputFile = txtInputFile.getText();
					
					//copy file over
					try {
						sendFiles("vnf.caltech.edu", gulpInputFile, null, gulpInputFile, null);
					} catch (ConnectException e1) {
						e1.printStackTrace();
					}
					
					//throw gulp into background
					//executeRemote();
					
					//shut down gulp
					
				}
			});
			runAtVnfButton.setText("export to vnf");
			runAtVnfButton.setBounds(9, 242, 148, 28);
		}
		return runAtVnfButton;
	}	
	
	public SerialListener keyRun = new SerialListener() {
		private static final long serialVersionUID = 5864600160182158595L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!Back.writer.incomplete) {
				//queue jobs
				jobs.clear();
				if (Back.getPanel().getOutput().chkSeparate.isSelected()) {
					int temp = Back.getPanel().getStructures().tabs.getSelectedIndex();
					for (int i=0; i < Back.getPanel().getStructures().tabs.getTabCount(); i++) {
						Back.getPanel().getStructures().tabs.setSelectedIndex(i);
						String[] contents = {Back.getStructure().atomicCoordinates.txtName.getText(), Back.writer.consoleOutput()};
						jobs.add(contents);
					}
					Back.getPanel().getStructures().tabs.setSelectedIndex(temp);
				} else {
					jobs.add(new String[]{"", Back.writer.consoleOutput()});
				}
				
				
				if (Back.getPanel().getExecution().radLocal.isSelected()) {
					if (Back.getPanel().getBinary().equals("")) {
						JOptionPane.showMessageDialog(Back.frame,
							"Please locate the gulp executable under the file menu.");
						return;
					}
					File f = new File(Back.getPanel().getWD() + "/"
							+ txtInputFile.getText());
					if (!f.exists() || lastViewed == Long.MAX_VALUE) {
						JOptionPane.showMessageDialog(null,
								"Please view your input file first.");
						return;
					}
					// if the user has edited their input file
					if (f.lastModified() > lastViewed) {
						contents = Back.getFileContents(f);
					} else {
						contents = Back.writer.consoleOutput();
					}
					//TODO Modify executeLocal to allow user modified input file
					if (e == null)
						executeLocal(true);
					else
						executeLocal(false);
				} else if (Back.getPanel().getExecution().radRemote.isSelected()) {
					executeRemote();
				} //else {
//					if (lastViewed == Long.MAX_VALUE) {
//						JOptionPane.showMessageDialog(null,
//								"Please view your input file first.");
//						return;
//					}
//					//TODO test this
//					runRequestBroker(contents);
//				}
			}
		}
	};
	private SerialListener keyClear = new SerialListener() {
		
		private static final long serialVersionUID = -7231887101581949066L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
					"Are you sure you want to clear the GUI?")) {
				Back.clearTab();
			}
		}
	};
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
				String contents = Back.writer.consoleOutput();
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

		btnRun.addActionListener(keyRun);
		btnRun.setBounds(163, 241, 136, 30);
		add(btnRun);
		lblStatus.setBounds(18, 275, 300, 20);
		add(lblStatus);
		btnClear.addActionListener(keyClear);
		btnClear.setBounds(305, 241, 128, 30);
		add(btnClear);
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
		txtInfinity.setBounds(196, 60, 136, 20);
		add(txtInfinity);
		lblTimeLimit.setBounds(9, 62, 165, 15);
		add(lblTimeLimit);
		lblInputFile.setBounds(9, 8, 136, 15);
		add(lblInputFile);
		txtOutputFile.setBounds(196, 33, 136, 20);
		add(txtOutputFile);
		btnViewInput.addActionListener(keyViewInput);
		btnViewInput.setBounds(340, 6, 93, 20);
		add(btnViewInput);

		chkProduceRestartFile.setBounds(9, 108, 300, 25);
		add(chkProduceRestartFile);
		chkAfterEvery.setBounds(50, 130, 109, 30);
		add(chkAfterEvery);
		lblCycles.setBounds(219, 133, 53, 25);
		add(lblCycles);
		txtDumpEvery.setBackground(Back.grey);
		txtDumpEvery.setBounds(165, 136, 48, 20);
		add(txtDumpEvery);
		chkOutputConstraints.addActionListener(keyOutputConstraints);
		chkOutputConstraints.setBounds(50, 156, 173, 25);
		add(chkOutputConstraints);
		txtFort12.setBackground(Back.grey);
		txtFort12.setBounds(314, 111, 84, 20);
		add(txtFort12);

		pnlOutputFormats.setBounds(439, 5, 330, 278);
		add(pnlOutputFormats);
		pnlTerse.setBounds(775, 5, 470, 277);
		add(pnlTerse);
		txtInputFile.setBounds(196, 6, 136, 20);
		add(txtInputFile);
		add(chkSeparate);
		chkSeparate.setBounds(9, 84, 378, 21);
		chkSeparate.setSelected(true);
		add(getRunAtVnfButton());
	}

	public void runLocally(final String output) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				if (btnRun.getText().equals("Abort")) {
					if (JOptionPane.showConfirmDialog(null,
							"Are you sure you want to abort GULP?") == JOptionPane.YES_OPTION) {
						btnRun.setText("Run");
						lblStatus.setText("GULP was aborted.");
						gulpProcess.destroy();
					}
					return;
				}
				try {
					btnRun.setText("Abort");
					lblStatus.setText("GULP is running.");
					long start = System.nanoTime();

					String[] commands = new String[] {
							Back.getPanel().getBinary(), txtInputFile.getText(),
							txtOutputFile.getText() };
					gulpProcess = Runtime.getRuntime().exec(commands, null,
							new File(Back.getPanel().getWD()));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(gulpProcess.getOutputStream()));
					bw.write(output);
					bw.close();

					BufferedReader br = new BufferedReader(new InputStreamReader(gulpProcess.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new FileWriter(Back.getPanel().getWD() + "/"
									+ txtOutputFile.getText()));
					String line = "";
					while ((line = br.readLine()) != null) {
						writer.write(line + System.getProperty("line.separator"));
					}
					br.close();
					writer.close();
					gulpProcess.waitFor();

					long stop = System.nanoTime();
					if (!lblStatus.getText().equals("GULP was aborted.")) {
						lblStatus.setText("GULP took " + (stop - start)
								/ 1000000000.0 + " seconds.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				btnRun.setText("Run");
			}
		});
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}
	
	public File qsubScript = null;
	
	private void executeRemote() {
		int parallel = 1;
		try {
			parallel = Integer.parseInt(Back.getPanel().getExecution().txtParallel.getText());
		} catch (NumberFormatException nfe) {
			
		}

		for (int j=0; j < parallel; j++) {
			final Execution ex = Back.getPanel().getExecution();
			for (int i=0; i < ex.hostsListModel.size(); i++) {
				final String hostname = (String) ex.hostsListModel.get(i);
				final PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
				if (ex.chkCredentials.isSelected())
					pwd.setUsername(ex.txtUsername.getText());
				else
					pwd.setUsername(ex.usernames.get(i));
				if (ex.chkCredentials.isSelected())
					pwd.setPassword(ex.pwdPassword.getText());
				else
					pwd.setPassword(ex.passwords.get(i));
				Thread t = new Thread(new Runnable() {
					public void run() {
						String[] job = null;
						String localDir = System.getProperty("user.home") + "/" + System.nanoTime();//random directory
						while ((job = getJob()) != null) {
							String path = Back.getPanel().getWD() + "/" + job[0];
							//create intermediate directories
							new File(localDir).mkdir();
							String jobDir = localDir + "/" + job[0];
							File directory = new File(jobDir);
							directory.mkdir();
							System.out.println(executeRemoteCommand(hostname, "mkdir " + path, pwd));

							//write out the files
							try {
								FileWriter fw = new FileWriter(jobDir + "/" + txtInputFile.getText());
								fw.write(job[1]);
								fw.close();
								sendFiles(hostname, path + "/", new String[]{txtInputFile.getText()}, jobDir + "/", pwd);
								
								String gulpCommand = Back.getPanel().getBinary() + " < " + txtInputFile.getText() + " > " + txtOutputFile.getText();
								
								if (ex.radPBS.isSelected()) {
									String str = null;
									if (ex.chkCustom.isSelected()) {
										str = Back.getFileContents(qsubScript).replace("DIRECTORY", job[0]);
									} else {
										str = "#!/bin/bash" + Back.newLine
										+ "cd " + path + Back.newLine
										+ gulpCommand;
									}
									FileWriter fw2 = new FileWriter(jobDir + "/" + job[0] + ".qsub");
									fw2.write(str);
									fw2.close();
									sendFiles(hostname, path + "/", new String[]{job[0] + ".qsub"}, jobDir + "/", pwd);
								} else {
									FileWriter fw2 = new FileWriter(jobDir + "/runGULP.sh");
									fw2.write(gulpCommand);
									fw2.close();
									sendFiles(hostname, path + "/", new String[]{"runGULP.sh"}, jobDir + "/", pwd);
								}
							} catch (ConnectException e) {
								JOptionPane.showMessageDialog(null, "Could not connect to the server.  Make sure you do not have ssh blocked.");
							} catch (IOException e) {
								e.printStackTrace();
							}

							//and execute
							if (ex.radPBS.isSelected()) {
								ex.addStatus(job[0], hostname);
								ex.updateStatus(job[0], "running");
								System.out.println(executeRemoteCommand(hostname, "cd " + path + ";qsub " + job[0] + ".qsub", pwd));
								ex.updateStatus(job[0], "done");
							} else {
								ex.addStatus(job[0], hostname);
								System.out.println(executeRemoteCommand(hostname, "chmod 777 " + path + "/runGULP.sh", pwd));
								ex.updateStatus(job[0], "running");
								System.out.println(executeRemoteCommand(hostname, "cd " + path + ";./runGULP.sh", pwd));
								ex.updateStatus(job[0], "done");
							}
						}
						Back.deleteDirectory(new File(localDir));
						System.out.println("done");
					}
				});
				t.setPriority(Thread.MIN_PRIORITY);
				t.start();
				threads.add(t);
			}
		}
	}
	
	public ArrayList<Thread> threads = new ArrayList<Thread>();
	private ArrayList<String[]> jobs = new ArrayList<String[]>();
	
	public synchronized String[] getJob() {
		if (jobs.size() == 0)
			return null;
		else
			return jobs.remove(0);
	}

	private void executeLocal(boolean synchronous) {
		int parallel = 1;
		try {
			parallel = Integer.parseInt(Back.getPanel().getExecution().txtParallel.getText());
		} catch (NumberFormatException nfe) {
			
		}
		
		for (int j=0; j < parallel; j++) {
			Runnable r = new Runnable() {
				public void run() {
					String[] job = null;
					while ((job = getJob()) != null) {
						//create directory
						String path = Back.getPanel().getWD() + "/" + job[0];
						File directory = new File(path);
						directory.mkdir();
						
						try {
							Back.getPanel().getExecution().addStatus(job[0], "localhost");
							Back.getPanel().getExecution().updateStatus(job[0], "running");
							String[] commands = new String[] {
									Back.getPanel().getBinary(), txtInputFile.getText(),
									txtOutputFile.getText() };
							Process p = Runtime.getRuntime().exec(commands, null, directory);
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
							bw.write(job[1]);
							bw.close();

							BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(path + "/" + txtOutputFile.getText()));
							String line = "";
							while ((line = br.readLine()) != null) {
								writer.write(line + System.getProperty("line.separator"));
							}
							br.close();
							writer.close();
							p.waitFor();
							Back.getPanel().getExecution().updateStatus(job[0], "done");
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			};
			if (synchronous) {
				r.run();
			} else {
				Thread t = new Thread(r);
				t.setPriority(Thread.MIN_PRIORITY);
				t.start();
				threads.add(t);
			}
		}
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
	
	private boolean sendFiles(final String remoteHost, final String remoteDirectory,
			final String[] localFileNames, final String localDirectory,
			PasswordAuthenticationClient pwd) throws ConnectException {
		try {
			final SshClient ssh = new SshClient();
			ssh.connect(remoteHost, new DialogKnownHostsKeyVerification(null));
			if (ssh.authenticate(pwd) == AuthenticationProtocolState.COMPLETE) {
				final SftpClient sftp = ssh.openSftpClient();
				try {
					for (int i=0; i < localFileNames.length; i++) {
						sftp.put(localDirectory
								+ localFileNames[i],remoteDirectory + localFileNames[i]);
					}
					sftp.quit();
					ssh.disconnect();
				} catch (TransferCancelledException e) {
					System.out.println("Transfer cancelled");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "User could not be authenticated.");
				return false;
			}
		} catch (ConnectException ce) {
			throw ce;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String executeRemoteCommand(String host, String cmd, PasswordAuthenticationClient pwd) {
		String result = "";
		try {
			SshClient ssh = new SshClient();
			ssh.connect(host, new DialogKnownHostsKeyVerification(null));
			if (ssh.authenticate(pwd) == AuthenticationProtocolState.COMPLETE) {
				SessionChannelClient session = ssh.openSessionChannel();
				if (session.executeCommand(cmd)) {
					IOStreamConnector output = new IOStreamConnector();
					java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
					output.connect(session.getInputStream(), bos);
					IOStreamConnector err = new IOStreamConnector();
					java.io.ByteArrayOutputStream err_bos = new java.io.ByteArrayOutputStream();
					err.connect(session.getStderrInputStream(), err_bos);
					session.getState().waitForState(ChannelState.CHANNEL_CLOSED);
					result = bos.toString() + err_bos.toString();
					
					output.close();
					err.close();
					bos.close();
					err_bos.close();
					session.close();
					ssh.disconnect();
				} else {
					return("Failed to execute: " + cmd);
				}
			} else {
				JOptionPane.showMessageDialog(null, "User could not be authenticated.");
			}
		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server.");
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return(result);
	}
}