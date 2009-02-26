package javagulp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class JCopy implements Serializable {

	private static final long serialVersionUID = -6556780507616205807L;

	public void copy(final File in, final File out) {
		try {
			final FileInputStream fis = new FileInputStream(in);
			final FileOutputStream fos = new FileOutputStream(out);
			final byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}