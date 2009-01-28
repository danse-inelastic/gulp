package javagulp.view.top;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javagulp.view.Back;

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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javagulp.model.SerialKeyAdapter;
import javagulp.model.SerialListener;
import javagulp.model.SerialMouseAdapter;

public class Execution extends JPanel implements Serializable {
	private static final long serialVersionUID = -907728808045994280L;
	
	private JLabel lblExecute = new JLabel("Execute on");
	private JLabel lblHosts = new JLabel("Remote Hosts");
	private JLabel lblUsername = new JLabel("Username");
	private JLabel lblPassword = new JLabel("Password");
	private JLabel lblParallel = new JLabel("<html>Run n jobs per host in parallel</html>");
	
	public JTextField txtUsername = new JTextField();
	public JTextField txtParallel = new JTextField("1");
	public JTextField txtGulpBinary = new JTextField();
	public JTextField txtWorkingDirectory = new JTextField(System.getProperty("user.home"));
	
	private JButton btnPause = new JButton("Pause");
	private JButton btnGulpBinary = new JButton("Gulp Binary");
	private JButton btnWorkingDirectory = new JButton("Working Directory");
	
	public JCheckBox chkCredentials = new JCheckBox("<html>Use the same credentials for all hosts</html>");
	public JCheckBox chkCustom = new JCheckBox("<html>Use custom<br>qsub script</html>");
	public JPasswordField pwdPassword = new JPasswordField();
	
	public JRadioButton radLocal = new JRadioButton("Local Machine");
	public JRadioButton radRemote = new JRadioButton("Remote Machine(s)");
	private ButtonGroup grpExecute = new ButtonGroup();
	
	public JRadioButton radPBS = new JRadioButton("Use PBS");
	private JRadioButton radDirect = new JRadioButton("Execute Directly");
	private ButtonGroup grpMethod = new ButtonGroup();
	
	public DefaultListModel hostsListModel = new DefaultListModel();
	private JList hostsList = new JList(hostsListModel);
	private JScrollPane scrollHosts = new JScrollPane(hostsList);
	
	private DefaultTableModel modelStatus = new DefaultTableModel(new String[]{"Job Name", "Host", "Start Time", "Elapsed Time", "Status"}, 0);
	private JTable tableStatus = new JTable(modelStatus);
	private JScrollPane scrollStatus = new JScrollPane(tableStatus);
	
	public ArrayList<String> usernames = new ArrayList<String>();
	public ArrayList<String> passwords = new ArrayList<String>();
	
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
		setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(686, 385));

		radLocal.addActionListener(keyRemote);
		radRemote.addActionListener(keyRemote);
		add(radLocal);
		add(radRemote);
		add(radDirect);
		add(radPBS);
		add(lblExecute);
		add(lblHosts);
		add(scrollHosts);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(pwdPassword);
		add(chkCredentials);
		add(scrollStatus);
		add(lblParallel);
		add(txtParallel);
		add(btnPause);
		
		grpExecute.add(radLocal);
		grpExecute.add(radRemote);
		grpMethod.add(radDirect);
		grpMethod.add(radPBS);
		
		lblExecute.setBounds(28, 0, 105, 28);
		radLocal.setBounds(7, 21, 161, 21);
		radRemote.setBounds(7, 42, 161, 21);
		scrollHosts.setBounds(7, 112, 154, 203);
		lblUsername.setBounds(168, 112, 105, 21);
		txtUsername.setBounds(168, 133, 154, 21);
		lblPassword.setBounds(168, 154, 105, 21);
		pwdPassword.setBounds(168, 175, 154, 21);
		chkCredentials.setBounds(168, 63, 154, 49);
		lblHosts.setBounds(35, 84, 105, 28);
		radDirect.setBounds(168, 21, 161, 21);
		radPBS.setBounds(168, 42, 161, 21);
		scrollStatus.setBounds(329, 7, 357, 308);
		lblParallel.setBounds(168, 203, 98, 42);
		txtParallel.setBounds(287, 210, 35, 21);
		btnPause.setBounds(168, 240, 80, 21);
		add(chkCustom);
		chkCustom.setBounds(168, 280, 161, 42);
		add(txtWorkingDirectory);
		txtWorkingDirectory.setBounds(168, 322, 518, 28);
		add(txtGulpBinary);
		txtGulpBinary.setBounds(168, 357, 518, 28);
		add(btnWorkingDirectory);
		btnWorkingDirectory.setBounds(7, 322, 154, 28);
		btnWorkingDirectory.addActionListener(keyWorkingDirectory);
		add(btnGulpBinary);
		btnGulpBinary.setBounds(7, 357, 154, 28);
		btnGulpBinary.addActionListener(keyGulpExecutable);

		txtWorkingDirectory.setEnabled(false);
		txtGulpBinary.setEnabled(false);
		radDirect.setSelected(true);
		radLocal.setSelected(true);
		
		hostsList.addMouseListener(keyMouse);
		hostsList.addKeyListener(keyHosts);
		txtUsername.addKeyListener(keyUsername);
		pwdPassword.addKeyListener(keyPassword);
		btnPause.addActionListener(keyPause);
		chkCustom.addActionListener(keyCustom);
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
	
	private SerialListener keyRemote = new SerialListener() {
		private static final long serialVersionUID = -6558056553136490457L;
		@Override
		public void actionPerformed(ActionEvent e) {
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
			for (Thread t : Back.getPanel().getOutput().threads) {
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
				fileDialog.setCurrentDirectory(new File(Back.getPanel().getWD()));
				if (JFileChooser.APPROVE_OPTION == fileDialog.showOpenDialog(Back.frame)) {
					Back.getPanel().getOutput().qsubScript = fileDialog.getSelectedFile();
				} else {
					Back.getPanel().getOutput().qsubScript = null;
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
}
