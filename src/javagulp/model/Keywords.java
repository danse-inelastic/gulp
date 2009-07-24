package javagulp.model;

import java.io.Serializable;
import java.util.TreeSet;

import javagulp.view.Back;

public class Keywords implements Serializable {
	private static final long serialVersionUID = -7544313514620261471L;

	private final TreeSet<String> keywords = new TreeSet<String>();

	public void putOrRemoveKeyword(final boolean discern, final String keyword) {
		if (discern)
			keywords.add(keyword);
		else
			keywords.remove(keyword);
	}

	public boolean containsKeyword(String keyword) {
		if (keywords.contains(keyword))
			return true;
		else
			return false;
	}

	public String writeKeywords() {
		String lines = "";
		for (final String word: keywords)
			lines += word + " ";
		return lines + Back.newLine;
	}
}