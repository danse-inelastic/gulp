package javagulp.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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


import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.sshtools.common.hosts.DialogKnownHostsKeyVerification;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.TransferCancelledException;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.io.IOStreamConnector;
import com.sshtools.j2ssh.session.SessionChannelClient;

import javagulp.controller.CgiCommunicate;
import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;

//TODO: fix copying of the library files for local/remote execution, do two working directories for both remote and local execution

public class Execution extends JPanel implements Serializable {
	private JScrollPane scrollPane;
	private JLabel statusLabel;
	private JTextArea txtVnfStatus;
	private JLabel nLabel;
	private TitledPanel pnlVnfExecution;
	private TitledPanel pnlHighThroughput;
	private TitledPanel howExecute;
	private JRadioButton radVnf;
	private TitledPanel pnlRemoteExecution;
	private TitledPanel pnlLocalExecution;
	private TitledPanel placeOfExecution;
	private JPanel pnlExecutionBackdrop;
	private static final long serialVersionUID = -907728808045994280L;

	private JLabel lblHosts = new JLabel("<html>remote hosts<br>(double click to add)</html>");
	private JLabel lblUsername = new JLabel("Username");
	private JLabel lblPassword = new JLabel("Password");
	private JLabel lblParallel = new JLabel("<html>when running multiple jobs sequentially, run n<br>jobs simultaneously</html>");
	public JCheckBox chkSeparate = new JCheckBox("put each structure in a separate input file");

	public JTextField txtUsername = new JTextField();
	public JTextField txtMultiple = new JTextField(Integer.toString(Runtime.getRuntime().availableProcessors()));
	public JTextField txtGulpBinary = new JTextField();
	public JTextField txtWorkingDirectory = new JTextField(System.getProperty("user.home"));

	private JButton btnPause = new JButton("pause");
	private JButton btnGulpBinary = new JButton("gulp binary");
	private JButton btnWorkingDirectory = new JButton("working directory");

	public JCheckBox chkCredentials = new JCheckBox("<html>Use the same credentials for all hosts</html>");
	public JCheckBox chkCustom = new JCheckBox("use custom submission script");
	public JPasswordField pwdPassword = new JPasswordField();

	public JRadioButton radLocal = new JRadioButton("local machine");
	public JRadioButton radRemote = new JRadioButton("remote machine(s)");
	private ButtonGroup grpExecute = new ButtonGroup();

	public JRadioButton radPBS = new JRadioButton("use PBS");
	private JRadioButton radDirect = new JRadioButton("execute directly");
	private ButtonGroup grpMethod = new ButtonGroup();

	public DefaultListModel hostsListModel = new DefaultListModel();
	private JList hostsList = new JList(hostsListModel);
	private JScrollPane scrollHosts = new JScrollPane(hostsList);

	private DefaultTableModel modelStatus = new DefaultTableModel(new String[]{"Job Name", "Host", "Start Time", "Elapsed Time", "Status"}, 0);
	private JTable tableStatus = new JTable(modelStatus);
	private JScrollPane scrollStatus = new JScrollPane(tableStatus);

	private JButton btnSubmit = new JButton("submit job");
	//private JLabel lblStatus = new JLabel();
	public String contents = "";
	Process gulpProcess;

	public ArrayList<String> usernames = new ArrayList<String>();
	public ArrayList<String> passwords = new ArrayList<String>();

	public ArrayList<Thread> threads = new ArrayList<Thread>();
	private ArrayList<String[]> jobs = new ArrayList<String[]>();


	public SerialListener keySubmit = new SerialListener() {
		private static final long serialVersionUID = 5864600160182158595L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!Back.writer.incomplete) {
				
				// run on vnf or locally or remotely 
				if (radVnf.isSelected()) {
					//get files as strings
					String currentInputFile = Back.getPanel().getOutput().selectedInputFile;
					if(currentInputFile.equals("input.gin"))
						Back.getPanel().getOutput().updateInputGin();
					String gulpInputFile = Back.getPanel().getOutput().inputFileMap.get(currentInputFile);
					String gulpLibrary = Back.getPanel().getPotential().libraryContents;
					String librarySelected = Back.getPanel().getPotential().librarySelected;//post the files
					Map<String,String> cgiMap = Back.getPanel().cgiMap;

					String cgihome = cgiMap.get("cgihome");
					CgiCommunicate cgiCom = new CgiCommunicate(cgihome);

					getTxtVnfStatus().setText("Computation "+cgiMap.get("simulationId")+" is being submitted to vnf....");
					cgiMap.put("actor.configurations", gulpInputFile);
					cgiMap.put("actor.librarycontent", gulpLibrary);
					cgiMap.put("actor.libraryname", librarySelected);
					
					cgiCom.setCgiParams(cgiMap);
					String response = cgiCom.postAndGetString();
					if (response.trim().equals("success")){
						getTxtVnfStatus().setText("Computation "+cgiMap.get("simulationId")+" has been successfully submitted.\n"+
							"You can alter the settings and submit another computation or clear the gui by clicking File->Clear Gui");
					}else{
						getTxtVnfStatus().setText("There was a problem with the submission.  The server returned the following message:\n"+
								response);
					}
					//close gulp
					//System.exit(0);
				} else if (radLocal.isSelected()) {
					logJob();
					if (Back.getPanel().getBinary().equals("")) {
						JOptionPane.showMessageDialog(Back.frame,
						"Please locate the gulp executable under the file menu.");
						return;
					}
					File f = new File(Back.getPanel().getWD() + "/"
							+ Back.getPanel().getOutput().selectedInputFile);
					if (!f.exists() || Back.getPanel().getOutput().lastViewed == Long.MAX_VALUE) {
						JOptionPane.showMessageDialog(null,
						"Please view your input file first.");
						return;
					}
//					// if the user has viewed/edited their input file sooner than the last time it was written, use the viewed file
//					if (Back.getPanel().getOutput().lastViewed < f.lastModified()) {
//						contents = Back.getFileContents(f);
//					} else {
//						contents = Back.writer.gulpInputFileToString();
//					}
					contents = Back.getPanel().getOutput().inputFileMap.get(Back.getPanel().getOutput().selectedInputFile);
					if (e == null)
						executeLocal(true);
					else
						executeLocal(false);
				} else if (radRemote.isSelected()) {
					logJob();
					executeRemote();
				} 
			}
			
		}

		void logJob(){
			//queue jobs
			jobs.clear();
			if (chkSeparate.isSelected()) {
				int temp = Back.getPanel().getStructures().tabs.getSelectedIndex();
				for (int i=0; i < Back.getPanel().getStructures().tabs.getTabCount(); i++) {
					Back.getPanel().getStructures().tabs.setSelectedIndex(i);
					String[] contents = {Back.getStructure().atomicCoordinates.txtName.getText(), Back.writer.gulpInputFileToString()};
					jobs.add(contents);
				}
				Back.getPanel().getStructures().tabs.setSelectedIndex(temp);
			} else {
				jobs.add(new String[]{"", Back.writer.gulpInputFileToString()});
			}
		}

	};

	//	public void runLocally(final String output) {
	//		Thread t = new Thread(new Runnable() {
	//			public void run() {
	//				if (btnRun.getText().equals("Abort")) {
	//					if (JOptionPane.showConfirmDialog(null,
	//							"Are you sure you want to abort GULP?") == JOptionPane.YES_OPTION) {
	//						btnRun.setText("Run");
	//						lblStatus.setText("GULP was aborted.");
	//						gulpProcess.destroy();
	//					}
	//					return;
	//				}
	//				try {
	//					btnRun.setText("Abort");
	//					lblStatus.setText("GULP is running.");
	//					long start = System.nanoTime();
	//
	//					String[] commands = new String[] {
	//							Back.getPanel().getBinary(), Back.getPanel().getOutput().txtInputFile.getText(),
	//							Back.getPanel().getOutput().txtOutputFile.getText() };
	//					gulpProcess = Runtime.getRuntime().exec(commands, null,
	//							new File(Back.getPanel().getWD()));
	//					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(gulpProcess.getOutputStream()));
	//					bw.write(output);
	//					bw.close();
	//
	//					BufferedReader br = new BufferedReader(new InputStreamReader(gulpProcess.getInputStream()));
	//					BufferedWriter writer = new BufferedWriter(new FileWriter(Back.getPanel().getWD() + "/"
	//									+ Back.getPanel().getOutput().txtOutputFile.getText()));
	//					String line = "";
	//					while ((line = br.readLine()) != null) {
	//						writer.write(line + System.getProperty("line.separator"));
	//					}
	//					br.close();
	//					writer.close();
	//					gulpProcess.waitFor();
	//
	//					long stop = System.nanoTime();
	//					if (!lblStatus.getText().equals("GULP was aborted.")) {
	//						lblStatus.setText("GULP took " + (stop - start)
	//								/ 1000000000.0 + " seconds.");
	//					}
	//				} catch (IOException e) {
	//					e.printStackTrace();
	//				} catch (InterruptedException e) {
	//					e.printStackTrace();
	//				}
	//				btnRun.setText("Run");
	//			}
	//		});
	//		t.setPriority(Thread.MIN_PRIORITY);
	//		t.start();
	//	}

	public File qsubScript = null;

	private void executeRemote() {
		int numSimultaneous = 1;
		try {
			numSimultaneous = Integer.parseInt(Back.getPanel().getExecution().txtMultiple.getText());
		} catch (NumberFormatException nfe) {
		}
		for (int j=0; j < numSimultaneous; j++) {
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
						String localDir = System.getProperty("user.home") + Back.newLine + System.nanoTime();//random directory
						while ((job = getJob()) != null) {
							String path = Back.getPanel().getWD() + Back.newLine + job[0];
							//create intermediate directories
							new File(localDir).mkdir();
							String jobDir = localDir + Back.newLine + job[0];
							File directory = new File(jobDir);
							directory.mkdir();
							System.out.println(executeRemoteCommand(hostname, "mkdir " + path, pwd));

							//write out the files
							try {
								FileWriter fw = new FileWriter(jobDir + Back.newLine + Back.getPanel().getOutput().selectedInputFile);
								fw.write(job[1]);
								fw.close();
								sendFiles(hostname, path + Back.newLine, new String[]{Back.getPanel().getOutput().selectedInputFile}, jobDir + "/", pwd);

								String gulpCommand = Back.getPanel().getBinary() + " < " + Back.getPanel().getOutput().selectedInputFile + " > " + Back.getPanel().getOutput().txtOutputFile.getText();

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


	public synchronized String[] getJob() {
		if (jobs.size() == 0)
			return null;
		else
			return jobs.remove(0);
	}

	private void executeLocal(boolean synchronous) {
		int parallel = 1;
		try {
			parallel = Integer.parseInt(Back.getPanel().getExecution().txtMultiple.getText());
		} catch (NumberFormatException nfe) {

		}

		for (int j=0; j < parallel; j++) {
			Runnable r = new Runnable() {
				public void run() {
					String[] job = null;
					while ((job = getJob()) != null) {
						//create directory
						String path = Back.getPanel().getWD() + Back.newLine + job[0];
						File directory = new File(path);
						directory.mkdir();

						try {
							Back.getPanel().getExecution().addStatus(job[0], "localhost");
							Back.getPanel().getExecution().updateStatus(job[0], "running");
							String[] commands = new String[] {
									Back.getPanel().getBinary(), Back.getPanel().getOutput().selectedInputFile,
									Back.getPanel().getOutput().txtOutputFile.getText() };
							Process p = Runtime.getRuntime().exec(commands, null, directory);
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
							bw.write(job[1]);
							bw.close();

							BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(path + Back.newLine + Back.getPanel().getOutput().txtOutputFile.getText()));
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


	public String formatTimeHMS(int secondsLeft) {
		int timeRemainingSecs = secondsLeft;
		int timeRemainingMins = (timeRemainingSecs/60);
		int timeRemainingHours = (timeRemainingMins/60);
		timeRemainingMins = (timeRemainingMins-(timeRemainingHours*60));
		timeRemainingSecs = (timeRemainingSecs-(timeRemainingHours*60*60)-(timeRemainingMins*60));
		String timeRemainingHoursStr = Integer.toString(timeRemainingHours),
		timeRemainingMinsStr = Integer.toString(timeRemainingMins),
		timeRemainingSecsStr = Integer.toString(timeRemainingSecs);
		if (timeRemainingHours < 9)
			timeRemainingHoursStr = "0"+timeRemainingHours;
		if (timeRemainingMins < 9)
			timeRemainingMinsStr = "0"+timeRemainingMins;
		if (timeRemainingSecs < 9)
			timeRemainingSecsStr = "0"+timeRemainingSecs;
		return ""+timeRemainingHoursStr+":"+timeRemainingMinsStr+":"+timeRemainingSecsStr;
	}

	public void addStatus(String jobName, String hostname) {
		final long start = System.currentTimeMillis();
		Date d = new Date(start);
		String[] data = {jobName, hostname, d.getHours()+":"+d.getMinutes()+":"+d.getSeconds(), "0", "Queued"};
		modelStatus.addRow(data);
		final int index = modelStatus.getRowCount()-1;
		//update elapsed time in a new thread
		Thread t = new Thread() {
			@Override
			public synchronized void run() {
				while (!modelStatus.getValueAt(index, 4).equals("done")) {
					String elapsed = formatTimeHMS((int)((System.currentTimeMillis()-start)/1000));
					modelStatus.setValueAt(elapsed, index, 3);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}

	public void updateStatus(String jobName, String status) {
		for (int i=0; i < modelStatus.getRowCount(); i++) {
			if (modelStatus.getValueAt(i, 0).equals(jobName))
				modelStatus.setValueAt(status, i, 4);
		}
	}

	public Execution() {
		super();
		setBorder(new LineBorder(Color.black, 1, false));
		setLayout(null);
		add(scrollStatus);
		add(btnPause);

		btnSubmit.addActionListener(keySubmit);
		btnSubmit.setBounds(7, 365, 136, 25);
		add(btnSubmit);
		scrollStatus.setBounds(489, 4, 579, 134);
		btnPause.setBounds(149, 365, 80, 25);
		btnPause.addActionListener(keyPause);
		add(getPnlExecutionBackdrop());
		add(getPlaceOfExecution());
		add(getHowExecute());
		add(getPnlHighThroughput());
	}
	private SerialMouseAdapter keyMouse = new SerialMouseAdapter() {
		private static final long serialVersionUID = -3862775803812225199L;
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				int index = hostsList.getSelectedIndex();
				if (index != -1 && !chkCredentials.isSelected()) {
					txtUsername.setText(usernames.get(index));
					pwdPassword.setText(passwords.get(index));
				}
			} else if (e.getClickCount() == 2) {
				String host = JOptionPane.showInputDialog("Please enter a new host.");
				if (host != null) {
					hostsListModel.addElement(host);
					usernames.add("");
					passwords.add("");
				}
			}
		}
	};

	private SerialListener keyPlaceOfExecution= new SerialListener() {
		private static final long serialVersionUID = -6558056553136490457L;
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl=(CardLayout) getPnlExecutionBackdrop().getLayout();
			if (radVnf.isSelected()) {
				cl.show(getPnlExecutionBackdrop(), getPnlVnfExecution().getName());
			} else if(radLocal.isSelected()) {
				cl.show(getPnlExecutionBackdrop(), getPnlLocalExecution().getName());
			} else if(radRemote.isSelected()) {
				cl.show(getPnlExecutionBackdrop(), getPnlRemoteExecution().getName());
			}

			if (radLocal.isSelected()) {
				txtWorkingDirectory.setEnabled(false);
				txtGulpBinary.setEnabled(false);
			} else {
				txtWorkingDirectory.setEnabled(true);
				txtGulpBinary.setEnabled(true);
			}
		}
	};

	private SerialListener keyWorkingDirectory = new SerialListener() {
		private static final long serialVersionUID = -6558056553136490457L;
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileDialog.setCurrentDirectory(new File(txtWorkingDirectory.getText()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				txtWorkingDirectory.setText(fileDialog.getSelectedFile().getPath());
			}
		}
	};
	private SerialListener keyGulpExecutable = new SerialListener() {
		private static final long serialVersionUID = 3561307658761872751L;
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileDialog.setCurrentDirectory(new File(txtWorkingDirectory.getText()));
			if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				if (!fileDialog.getSelectedFile().getName().startsWith("gulp")) {
					JOptionPane.showMessageDialog(Back.frame, "The gulp executable must start with the letters \"gulp\"");
					this.actionPerformed(e);//call again
				} else
					txtGulpBinary.setText(fileDialog.getSelectedFile().getPath());
			}
		}
	};
	private SerialListener keyPause = new SerialListener() {
		private static final long serialVersionUID = -3356551563353996933L;
		@Override
		public void actionPerformed(ActionEvent e) {
			/*int[] indices = tableStatus.getSelectedRows();
			for (int i=0; i < indices.length; i++) {
				Thread t = Back.getPanel().execute.threads.get(indices[i]);
				t.suspend();
			}*/
			for (Thread t : threads) {
				if (toggle) {
					t.suspend();
					toggle = false;
				} else {
					t.resume();
					toggle = true;
				}
			}
			//TODO add cancel option
		}
		boolean toggle = true;
	};
	private SerialListener keyCustom = new SerialListener() {
		private static final long serialVersionUID = -2720927249956998833L;
		@Override
		public void actionPerformed(ActionEvent e) {
			if (chkCustom.isSelected()) {
				JFileChooser fileDialog = new JFileChooser();
				if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
				} else {
					chkCustom.setSelected(false);
				}
			}
		}
	};
	private SerialKeyAdapter keyPassword = new SerialKeyAdapter() {
		private static final long serialVersionUID = -8327779868170787702L;
		@Override
		public void keyReleased(KeyEvent e) {
			if (!chkCredentials.isSelected() && hostsList.getSelectedIndex() != -1)
				passwords.set(hostsList.getSelectedIndex(), new String(pwdPassword.getPassword()));
		}
	};
	private SerialKeyAdapter keyUsername = new SerialKeyAdapter() {
		private static final long serialVersionUID = 3402669870819160012L;
		@Override
		public void keyReleased(KeyEvent e) {
			if (!chkCredentials.isSelected() && hostsList.getSelectedIndex() != -1)
				usernames.set(hostsList.getSelectedIndex(), txtUsername.getText());
		}
	};
	private SerialKeyAdapter keyHosts = new  SerialKeyAdapter() {
		private static final long serialVersionUID = -5433639756017979950L;
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE && hostsList.getSelectedIndex() != -1) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this host?") == JOptionPane.YES_OPTION) {
					int index = hostsList.getSelectedIndex();
					hostsListModel.remove(index);
					usernames.remove(index);
					passwords.remove(index);
				}
			}
		}
	};
	/**
	 * @return
	 */
	protected JPanel getPnlExecutionBackdrop() {
		if (pnlExecutionBackdrop == null) {
			pnlExecutionBackdrop = new JPanel();
			//pnlExecutionBackdrop.setBounds(7, 144, 724, 192);
			pnlExecutionBackdrop.setLayout(new CardLayout());
			pnlExecutionBackdrop.setBounds(7, 144, 723, 215);
			add(pnlExecutionBackdrop);
			pnlExecutionBackdrop.add(getPnlVnfExecution(), getPnlVnfExecution().getName());
			pnlExecutionBackdrop.add(getPnlLocalExecution(), getPnlLocalExecution().getName());
			pnlExecutionBackdrop.add(getPnlRemoteExecution(), getPnlRemoteExecution().getName());
		}
		return pnlExecutionBackdrop;
	}
	/**
	 * @return
	 */
	protected TitledPanel getPlaceOfExecution() {
		if (placeOfExecution == null) {
			placeOfExecution = new TitledPanel();
			placeOfExecution.setBounds(7, 4, 196, 134);
			placeOfExecution.setTitle("where to execute");

			radLocal.addActionListener(keyPlaceOfExecution);
			radLocal.setBounds(10, 53, 161, 21);
			placeOfExecution.add(getRadVnf());
			grpExecute.add(getRadVnf());
			getRadVnf().addActionListener(keyPlaceOfExecution);
			placeOfExecution.add(radLocal);

			grpExecute.add(radLocal);

			radRemote.addActionListener(keyPlaceOfExecution);
			radRemote.setBounds(10, 80, 161, 21);
			placeOfExecution.add(radRemote);
			grpExecute.add(radRemote);

		}
		return placeOfExecution;
	}
	
	protected TitledPanel getPnlVnfExecution() {
		if (pnlVnfExecution == null) {
			pnlVnfExecution = new TitledPanel();
			pnlVnfExecution.setName("vnfExecution");
			pnlVnfExecution.setTitle("vnf submission");
			pnlVnfExecution.add(getStatusLabel());
			pnlVnfExecution.add(getScrollPane());
		}
		return pnlVnfExecution;
	}
	
	/**
	 * @return
	 */
	protected TitledPanel getPnlLocalExecution() {
		if (pnlLocalExecution == null) {
			pnlLocalExecution = new TitledPanel();
			pnlLocalExecution.setLayout(null);
			pnlLocalExecution.setName("localExecution");
			pnlLocalExecution.setTitle("local submission");
			btnWorkingDirectory.setBounds(10, 27, 175, 19);
			pnlLocalExecution.add(btnWorkingDirectory);
			btnWorkingDirectory.addActionListener(keyWorkingDirectory);
			btnGulpBinary.setBounds(10, 69, 175, 19);
			pnlLocalExecution.add(btnGulpBinary);
			btnGulpBinary.addActionListener(keyGulpExecutable);
			txtWorkingDirectory.setBounds(191, 27, 495, 19);
			pnlLocalExecution.add(txtWorkingDirectory);

			txtWorkingDirectory.setEnabled(false);
			txtGulpBinary.setBounds(191, 69, 495, 19);
			pnlLocalExecution.add(txtGulpBinary);
			txtGulpBinary.setEnabled(false);
		}
		return pnlLocalExecution;
	}
	/**
	 * @return
	 */
	protected TitledPanel getPnlRemoteExecution() {
		if (pnlRemoteExecution == null) {
			pnlRemoteExecution = new TitledPanel();
			pnlRemoteExecution.setLayout(null);
			pnlRemoteExecution.setName("remoteExecution");
			pnlRemoteExecution.setTitle("remote submission");
			lblHosts.setBounds(10, 25, 154, 28);
			pnlRemoteExecution.add(lblHosts);
			scrollHosts.setBounds(10, 66, 154, 138);
			pnlRemoteExecution.add(scrollHosts);

			hostsList.addMouseListener(keyMouse);
			hostsList.addKeyListener(keyHosts);
			lblUsername.setBounds(180, 65, 105, 21);
			pnlRemoteExecution.add(lblUsername);
			txtUsername.setBounds(302, 65, 154, 21);
			pnlRemoteExecution.add(txtUsername);
			txtUsername.addKeyListener(keyUsername);
			lblPassword.setBounds(180, 91, 105, 21);
			pnlRemoteExecution.add(lblPassword);
			pwdPassword.setBounds(302, 91, 154, 21);
			pnlRemoteExecution.add(pwdPassword);
			pwdPassword.addKeyListener(keyPassword);
			chkCredentials.setBounds(190, 118, 315, 28);
			pnlRemoteExecution.add(chkCredentials);
		}
		return pnlRemoteExecution;
	}
	/**
	 * @return
	 */
	protected JRadioButton getRadVnf() {
		if (radVnf == null) {
			radVnf = new JRadioButton();
			radVnf.setSelected(true);
			radVnf.setText("vnf");
			radVnf.setBounds(10, 24, 138, 23);
		}
		return radVnf;
	}
	/**
	 * @return
	 */
	protected TitledPanel getHowExecute() {
		if (howExecute == null) {
			howExecute = new TitledPanel();
			howExecute.setBounds(209, 4, 274, 134);
			howExecute.setTitle("how to execute");
			radDirect.setBounds(10, 26, 161, 21);
			howExecute.add(radDirect);
			grpMethod.add(radDirect);
			radDirect.setSelected(true);
			radPBS.setBounds(10, 87, 161, 21);
			howExecute.add(radPBS);
			grpMethod.add(radPBS);
			chkCustom.setBounds(35, 53, 229, 28);
			howExecute.add(chkCustom);
			chkCustom.addActionListener(keyCustom);
		}
		return howExecute;
	}
	/**
	 * @return
	 */
	protected TitledPanel getPnlHighThroughput() {
		if (pnlHighThroughput == null) {
			pnlHighThroughput = new TitledPanel();
			pnlHighThroughput.setBounds(736, 144, 332, 215);
			pnlHighThroughput.setTitle("high throughput execution");
			lblParallel.setBounds(10, 62, 312, 37);
			pnlHighThroughput.add(lblParallel);
			txtMultiple.setBounds(54, 105, 49, 21);
			pnlHighThroughput.add(txtMultiple);
			chkSeparate.setBounds(10, 26, 314, 30);
			pnlHighThroughput.add(chkSeparate);
			chkSeparate.setSelected(true);
			pnlHighThroughput.add(getNLabel());
		}
		return pnlHighThroughput;
	}


	/**
	 * @return
	 */
	protected JLabel getNLabel() {
		if (nLabel == null) {
			nLabel = new JLabel();
			nLabel.setText("n");
			nLabel.setBounds(20, 105, 25, 21);
		}
		return nLabel;
	}
	/**
	 * @return
	 */
	protected JTextArea getTxtVnfStatus() {
		if (txtVnfStatus == null) {
			txtVnfStatus = new JTextArea();
		}
		return txtVnfStatus;
	}
	/**
	 * @return
	 */
	protected JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setText("status");
			statusLabel.setBounds(10, 26, 228, 15);
		}
		return statusLabel;
	}
	/**
	 * @return
	 */
	protected JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 47, 703, 158);
			scrollPane.setViewportView(getTxtVnfStatus());
		}
		return scrollPane;
	}
	/**
	 * @return
	 */
}
