package javagulp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JCheckBox;

public class TaskKeywordListener implements ActionListener, Serializable {

	private static final long serialVersionUID = 8674881359650941422L;

	JCheckBox box;
	String taskKeyword;

//	public TaskKeywordListener(JCheckBox box, String taskKeyword) {
//		this.box = box;
//		this.taskKeyword = taskKeyword;
//	}

	public TaskKeywordListener(String taskKeyword) {
		Back.getTaskKeywords().putTaskKeywords(taskKeyword);
	}
	
	public void actionPerformed(ActionEvent e) {
		Back.getTaskKeywords().putTaskKeywords(taskKeyword);
	}
}