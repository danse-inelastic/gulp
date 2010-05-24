package javagulp.view.execution;

import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MultipleStructures extends TitledPanel {
	
	private static final long serialVersionUID = -401085295937097455L;
	private final JLabel lblParallel = new JLabel("<html>when running multiple jobs sequentially, run n<br>jobs simultaneously</html>");
	public JTextField txtMultiple = new JTextField(Integer.toString(Runtime.getRuntime().availableProcessors()));
	public JCheckBox chkSeparate = new JCheckBox("put each structure in a separate input file");
	private JLabel lblN = new JLabel("n");
	
	
	public MultipleStructures(){	
		setTitle("run multiple structures (experimental)");
		lblParallel.setBounds(10, 60, 401, 37);
		add(lblParallel);
		txtMultiple.setBounds(40, 105, 49, 21);
		add(txtMultiple);
		chkSeparate.setBounds(10, 25, 401, 30);
		add(chkSeparate);
		//chkSeparate.setSelected(true);
		lblN.setBounds(10, 105, 25, 21);
		add(lblN);
	}

}