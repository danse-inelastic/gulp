package javagulp.model;

import java.io.Serializable;
import java.util.TreeSet;

import javagulp.view.Back;

public class TaskKeywords implements Serializable {
	private static final long serialVersionUID = -7544313514620261471L;
	
	private TreeSet<String> taskKeywords = new TreeSet<String>();
	
	public void putOrRemoveTaskKeyword(final boolean discern, final String taskKeyword) {
		if (discern)
			taskKeywords.add(taskKeyword);
		else
			taskKeywords.remove(taskKeyword);
	}
	
	public boolean containsKeyword(String taskKeyword) {
		if (taskKeywords.contains(taskKeyword))
			return true;
		else
			return false;
	}
	
	public String writeTaskKeywords() {
		String lines = "";
		for (String word: taskKeywords)
			lines += word + " ";
		return lines;
	}
}