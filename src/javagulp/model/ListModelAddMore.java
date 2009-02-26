package javagulp.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ListModelAddMore extends DefaultListModel implements Serializable {

	private static final long serialVersionUID = 1752817377503292101L;

	public ListModelAddMore() {
		super();
	}

	public void update(final ArrayList<String> atomlist) {
		clear();
		for (int i = 0; i < atomlist.size(); i++) {
			addElement(atomlist.get(i));
		}
	}
}
