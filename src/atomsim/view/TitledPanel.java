package javagulp.view;

import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TitledPanel extends JPanel implements Serializable {

	private static final long serialVersionUID = 4517304056767862915L;

	TitledBorder border = new TitledBorder(null, null, TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, null, null);

	public TitledPanel() {
		super();
		setBorder(border);
		setLayout(null);
	}

	public TitledPanel(final String title) {
		super();
		setBorder(border);
		setLayout(null);
		border.setTitle(title);
	}

	public void setTitle(final String title) {
		border.setTitle(title);
	}

}
