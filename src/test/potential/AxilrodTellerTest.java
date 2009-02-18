package test.potential;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.Potential;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;
import javagulp.view.potential.threecenter.AxilrodTeller;
import junit.framework.TestCase;

import org.junit.Test;

public class AxilrodTellerTest extends 
 {
	
	private AxilrodTeller a = new AxilrodTeller();
	private PPP k;
	
	private String[] testValuesK = {"", "A", "0", "1"};
	
	private boolean[] result = {false, false, true, true};


	protected void setUp() throws Exception {
		Potential pot = Back.getPanel().getPotential();
		pot.potentialNumber = 3;
		pot.pnlAtom.cboAtom[0].addItem("C");
		pot.pnlAtom.cboAtom[1].addItem("H");
		pot.pnlAtom.cboAtom[2].addItem("B");
		PotentialPanel[][] pnl = (PotentialPanel[][]) PotTest.getField("potentials", pot);
		pnl[2][7] = a;
		k = a.params[0];
	}

	@Test
	public void testWritePotential() {
		String t = "^" + "axilrod-teller " + PotTest.genBondingOptions() + Back.newLine;
		String b = PotTest.genAtoms(3) + PotTest.genFloats(4, 7)
				+ PotTest.genFlags(1, 1) + Back.newLine + "$";
		Pattern p = Pattern.compile(t+b);
		
		boolean fail = false;
		System.out.println("Axilrod-teller testWritePotential");
		System.out.println(t+b);
		//for each test case
		for (int i=0; i < result.length; i++) {
			System.out.println("index " + i);
			//set options in the GUI
			k.txt.setText(testValuesK[i]);
			
			a.radii.txtrmin[0].setText(PotTest.getRandomFloat(0, 1));
			a.radii.txtrmin[1].setText(PotTest.getRandomFloat(0, 1));
			a.radii.txtrmin[2].setText(PotTest.getRandomFloat(0, 1));
			a.radii.txtrmax[0].setText(PotTest.getRandomFloat(0, 10));
			a.radii.txtrmax[1].setText(PotTest.getRandomFloat(0, 10));
			a.radii.txtrmax[2].setText(PotTest.getRandomFloat(0, 10));
			
			
			boolean good = false;
			String s = null;
			try {
				s = a.writePotential();
				//parse output string for correct format
				Matcher m = p.matcher(s);
				if (m.matches()) {
					System.out.println("match");
					//perform more error checking
					for (int j=0; j<m.groupCount(); j++) {
						System.out.println("group " + j + " " + m.group(j));
					}
					boolean elements = PotTest.areElements(new String[]{
							m.group(4), m.group(5), m.group(6)});
					boolean numbers = true;
					for (int j=0; j < 7; j++) {
						try {
							Double.parseDouble(m.group(2*i+7));
						} catch (NumberFormatException nfe) {
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
			} catch (IncompleteOptionException e) {
				if (result[i])
					e.printStackTrace();
			} catch (Exception e) {
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
