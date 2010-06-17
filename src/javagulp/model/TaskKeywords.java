package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskKeywords implements Serializable {
	private static final long serialVersionUID = -7544313514620261471L;

	private final ArrayList<String> taskKeywords = new ArrayList<String>();

	public void putOrRemoveTaskKeyword(final boolean discern, final String taskKeyword) {
		if (discern)
			taskKeywords.add(taskKeyword);
		else
			taskKeywords.remove(taskKeyword);
	}

	public void putTaskKeyword(final String task) {
		taskKeywords.add(task);
	}

	//	public void putTaskKeywords(final String tasks) {
	//		// this removes all task keywords and puts in new ones
	//		taskKeywords.clear();
	//		taskKeywords.add(tasks);
	//	}

	public boolean containsKeyword(String taskKeyword) {
		if (taskKeywords.contains(taskKeyword))
			return true;
		else
			return false;
	}

	public String writeTaskKeywords() {
		String lines = "";
		for (final String word: taskKeywords)
			lines += word + " ";
		return lines;
	}
}