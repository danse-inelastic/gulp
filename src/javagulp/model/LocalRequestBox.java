package javagulp.model;

import java.io.Serializable;

import javagulp.view.Back;
import cseo.jodaf.client.JODAFException;
import cseo.jodaf.client.JODAFRequestBox;

public class LocalRequestBox extends JODAFRequestBox implements Serializable {
	private static final long serialVersionUID = -3519873423170892607L;
	
	Back m;

	@Override
	protected Object receiveRequest(final String requestFromID,
			final String strRequest, final Object objData, final String strReply)
			throws JODAFException {
		if (strRequest.compareTo("GULP") == 0) {
			if (m != null)
				throw new JODAFException(this, "javagulp already exists.");
			m = new Back(this);
		} else {
			throw new JODAFException(this, "The request '" + strRequest + "' is unknown.");
		}
		return null;
	}
}