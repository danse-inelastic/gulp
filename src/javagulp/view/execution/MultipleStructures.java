package javagulp.view.execution;

import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MultipleStructures extends TitledPanel {
	
	private final JLabel lblParallel = new JLabel("<html>when running multiple jobs sequentially, run n<br>jobs simultaneously</html>");
	public JTextField txtMultiple = new JTextField(Integer.toString(Runtime.getRuntime().availableProcessors()));
	public JCheckBox chkSeparate = new JCheckBox("put each structure in a separate input file");
	private JLabel lblN = new JLabel("n");
	
	
	public MultipleStructures(){
		
		setTitle("high throughput execution (experimental)");
		lblParallel.setBounds(10, 98, 401, 37);
		add(lblParallel);
		txtMultiple.setBounds(41, 141, 49, 21);
		add(txtMultiple);
		chkSeparate.setBounds(10, 62, 401, 30);
		add(chkSeparate);
		//chkSeparate.setSelected(true);
		lblN.setBounds(10, 141, 25, 21);
		add(lblN);

		final JCheckBox checkBox = new JCheckBox();
		checkBox.setText("New JCheckBox");
		checkBox.setBounds(10, 33, 122, 23);
		add(checkBox);
	}
	

	protected JLabel getNLabel() {
		if (lblN == null) {

		}
		return lblN;
	}

}