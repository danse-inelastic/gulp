package potential;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.potential.CreateLibrary;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.twocenter.GeneralPotential;

import javax.swing.JTextField;

import junit.framework.TestCase;

import org.junit.Test;

public class GeneralTest extends TestCase {

	private final GeneralPotential g = new GeneralPotential();
	private PPP a;
	private PPP rho;
	private PPP c;
	private JTextField txtrmin;
	private JTextField txtrmax;

	private final String[] testValuesA = {"", "A", "0", "1"};
	private final String[] testValuesRho = {"", "B", "2", "3"};
	private final String[] testValuesC = {"", "C", "4", "5"};

	private final boolean[] result = {false, false, true, true};

	@Override
	protected void setUp() throws Exception {
		final CreateLibrary pot = Back.getCurrentRun().getPotential();
		pot.potentialNumber = 2;
		pot.pnlAtom.cboAtom[0].addItem("H");
		pot.pnlAtom.cboAtom[1].addItem("N");
		final PotentialPanel[][] pnl = (PotentialPanel[][]) PotTest.getField("potentials", pot);
		pnl[1][0] = g;
		a = g.params[0];
		rho = g.params[1];
		c = g.params[2];
		txtrmin = (JTextField) PotTest.getField("txtrmin", g);
		txtrmax = (JTextField) PotTest.getField("txtrmax", g);
	}

	@Test
	public void testWritePotential() {
		final String t = "^" + "general " + PotTest.genBondingOptions() + Back.newLine;
		final String b = PotTest.genAtoms(2) + PotTest.genFloats(4, 5)
		+ PotTest.genFlags(3, 3) + Back.newLine + "$";
		final Pattern p = Pattern.compile(t+b);

		boolean fail = false;
		System.out.println("General testWritePotential");
		System.out.println(t+b);
		//for each test case
		for (int i=0; i < result.length; i++) {
			System.out.println("index " + i);
			//set options in the GUI
			a.txt.setText(testValuesA[i]);
			rho.txt.setText(testValuesRho[i]);
			c.txt.setText(testValuesC[i]);

			txtrmin.setText(PotTest.getRandomFloat(0, 1));
			txtrmax.setText(PotTest.getRandomFloat(0, 10));


			boolean good = false;
			String s = null;
			try {
				s = g.writePotential();
				//parse output string for correct format
				final Matcher m = p.matcher(s);
				if (m.matches()) {
					System.out.println("match");
					//perform more error checking
					for (int j=0; j<m.groupCount(); j++) {
						System.out.println("group " + j + " " + m.group(j));
					}
					final boolean elements = PotTest.areElements(new String[]{
							m.group(4), m.group(5)});
					boolean numbers = true;
					for (int j=0; j < 5; j++) {
						try {
							Double.parseDouble(m.group(2*i+6));
						} catch (final NumberFormatException nfe) {
							nfe.printStackTrace();
							numbers = false;
							System.out.println(m.group(2*i+7) + " is not a double.");
							// We have already verified these numbers are floating
							// point in the regex, so this should never happen.
						}
					}
					if (elements && numbers);
					good = true;
				}
			} catch (final IncompleteOptionException e) {
				if (result[i])
					e.printStackTrace();
			} catch (final Exception e) {
				if (result[i])
					e.printStackTrace();
			}
			//check if test failed
			System.out.println(s);
			if (good == result[i]) {
				System.out.println("pass");
			} else {
				System.out.println("fail");
				fail = true;
			}
		}
		if (fail)
			fail("One or more errors have occurred");
	}

	@Test
	public void testClone() {
		//fail("Not yet implemented");
	}
}
