package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JCheckBox;

public class KeywordListener implements ActionListener, Serializable {

	private static final long serialVersionUID = 8674881359650941422L;

	JCheckBox box;
	String keyword;

	public KeywordListener(JCheckBox box, String keyword) {
		this.box = box;
		this.keyword = keyword;
	}

	public void actionPerformed(ActionEvent e) {
		Back.getKeys().putOrRemoveKeyword(box.isSelected(), keyword);
	}
}