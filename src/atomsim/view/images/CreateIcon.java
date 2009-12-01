package javagulp.view.images;

import javax.swing.ImageIcon;

public class CreateIcon {

	public ImageIcon createIcon(final String path) {
		final java.net.URL imgURL = this.getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
