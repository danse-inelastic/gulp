package javagulp.model;

import java.io.Serializable;

public class G implements Serializable {

	private static final long serialVersionUID = 4517689400868048519L;

	public String alpha = "&#945;", beta = "&#946;", gamma = "&#947;",
	chi = "&#967;", epsilon = "&#949;", eta = "&#951;",
	theta = "&#952;", lambda = "&#955;", mu = "&#956;", phi = "&#966;",
	pi = "&#960;", rho = "&#961;", sigma = "&#963;", zeta = "&#962;",
	delta = "&#948;", Gamma = "&#915;", infinity = "&#8734;",
	part = "&#8706;", in = "&#8712;", ang = "&Aring;";

	public String html(String x) {
		return "<html>" + x + "</html>";
	}

	public String sub(String x) {
		return "<sub>" + x + "</sub>";
	}

	public String sup(String x) {
		return "<sup>" + x + "</sup>";
	}
}