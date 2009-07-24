package javagulp.view.constraints;

import javagulp.view.KeywordListener;
import javagulp.view.TitledPanel;

import javax.swing.JCheckBox;

public class ConstraintsOptions extends TitledPanel {

	private static final long serialVersionUID = 740089487749329041L;



	private final JCheckBox chkHtmlDoNotFreeze = new JCheckBox("<html>do not freeze out atoms with no degrees of freedom from first and and second derivative calculations during optimization</html>");


	private final KeywordListener keyHtmlDoNotFreeze = new KeywordListener(chkHtmlDoNotFreeze, "noexclude");


	public ConstraintsOptions() {
		super();
		setTitle("options");


		chkHtmlDoNotFreeze.setBounds(7, 86, 945, 25);
		add(chkHtmlDoNotFreeze);
		chkHtmlDoNotFreeze.addActionListener(keyHtmlDoNotFreeze);

	}
}

