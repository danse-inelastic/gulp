package javagulp.model;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javagulp.view.Back;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Nutpad extends JFrame {

	private static final long serialVersionUID = 7039308358105899711L;

	private JTextArea mEditArea;
	private final JFileChooser mFileChooser = new JFileChooser(".");

	private Action mOpenAction;
	private Action mSaveAction;
	private Action mSaveVnfAction;
	private Action mExitAction;

	private File file = null;

	public Nutpad(File loadedFile) {
		super();
		file = loadedFile;
		createActions();
		this.setContentPane(new contentPanel(loadedFile));
		this.setJMenuBar(createMenuBar());
		//JFrame.EXIT_ON_CLOSE throws IllegalArgumentException in java web start
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
	}

	public Nutpad(String contents) {
		super();
		createActions();
		this.setContentPane(new contentPanel(contents));
		this.setJMenuBar(createMenuBar());
		//JFrame.EXIT_ON_CLOSE throws IllegalArgumentException in java web start
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
	}

	private class contentPanel extends JPanel {

		private static final long serialVersionUID = -6324322656704584756L;

		contentPanel(File file) {
			initialize();
			// read given file
			try {
				final FileReader reader = new FileReader(file);
				mEditArea.read(reader, "");  // Use TextComponent read
			} catch (final IOException ioex) {
				System.out.println(ioex);
			}
		}

		contentPanel(String contents) {
			initialize();
			mEditArea.setText(contents);
		}

		private void initialize() {
			mEditArea = new JTextArea(50, 80);
			mEditArea.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
			mEditArea.setFont(new Font("monospaced", Font.PLAIN, 14));
			final JScrollPane scrollingText = new JScrollPane(mEditArea);

			this.setLayout(new BorderLayout());
			this.add(scrollingText, BorderLayout.CENTER);
		}
	}

	/** Utility function to create a menubar. */
	private JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu fileMenu = menuBar.add(new JMenu("File"));
		fileMenu.add(mOpenAction);  // Note use of actions, not text.
		fileMenu.add(mSaveAction);
		fileMenu.add(mSaveVnfAction);
		fileMenu.addSeparator();
		fileMenu.add(mExitAction);
		return menuBar;
	}

	/** Utility function to define actions. */
	private void createActions() {
		mOpenAction = new AbstractAction("Open...") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1493840220632136728L;

			public void actionPerformed(ActionEvent e) {
				final int retval = mFileChooser.showOpenDialog(Nutpad.this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					final File f = mFileChooser.getSelectedFile();
					try {
						final FileReader reader = new FileReader(f);
						mEditArea.read(reader, "");  // Use TextComponent read
					} catch (final IOException ioex) {
						System.out.println(e);
						//System.exit(1);
					}
				}
			}
		};

		mSaveAction = new AbstractAction("Save") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3294324834199401502L;

			public void actionPerformed(ActionEvent e) {
				if (file == null) {
					final int retval = mFileChooser.showSaveDialog(Nutpad.this);
					if (retval == JFileChooser.APPROVE_OPTION) {
						final File f = mFileChooser.getSelectedFile();
						try {
							final BufferedWriter writer = new BufferedWriter(new FileWriter(f));
							mEditArea.write(writer);  // Use TextComponent write
						} catch (final IOException ioex) {
							System.out.println(e);
							//System.exit(1);
						}
					}
				} else {
					try {
						final BufferedWriter fw = new BufferedWriter(new FileWriter(file));
						fw.write(mEditArea.getText());
						fw.close();
					} catch (final IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};

		mSaveVnfAction = new AbstractAction("Save to vnf") {
			private static final long serialVersionUID = -3294324834199401502L;
			public void actionPerformed(ActionEvent e) {
				final String name = (String)JOptionPane.showInputDialog(
						Back.frame, "Input the name of the input file", "",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				Back.getCurrentRun().getOutput().inputFileMap.put(name, mEditArea.getText());
				Back.getCurrentRun().getOutput().inputFileModel.addElement(name);
			}
		};

		mExitAction = new AbstractAction("Exit") {
			private static final long serialVersionUID = -6823728690923785461L;
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		};

	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			cancel();
		}
		super.processWindowEvent(e);
	}

	void cancel() {
		dispose();
		//Java may or may not terminate the VM, so we garbage collect
		System.gc();
	}
}