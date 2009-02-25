package potential;

import java.lang.reflect.Field;
import java.util.Random;

import javagulp.view.Back;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PotTest {
	
	public static Test suite() {
		new Back(null);
		Back.frame.setVisible(false);
		TestSuite suite = new TestSuite("Test for test.potential");
		//$JUnit-BEGIN$
		suite.addTestSuite(AxilrodTellerTest.class);
		suite.addTestSuite(GeneralTest.class);
		//$JUnit-END$
		return suite;
	}
	
	public static Object getField(String fieldName, Object instance) {
		try {
			Field f = instance.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(instance);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("NULL");
		//JOptionPane.showMessageDialog(null, "");
		return null;
	}
	
	//TODO if users rename atoms in ChargesElementsBonding, this will need to be changed
	public static final String[] elements = { "H", "He", "Li", "Be", "B", "C",
		"N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar",
		"K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu",
		"Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr",
		"Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb",
		"Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm",
		"Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta",
		"W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po",
		"At", "Rn", "Fr", "Ra", "Ac", "Th", "U", "Np", "Pu", "Am", "Cm",
		"Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh",
		"Hs", "Mt", "Ds", "Rg" };
	
	private static boolean isElement(String atom) {
		atom = atom.trim();
		if (!(atom.length() == 1 || atom.length() == 2))
			return false;
		for (String s: elements)
			if (s.equals(atom))
				return true;
		//case insensitive version
		/*for (String s: elements)
			if (s.toLowerCase().equals(atom.toLowerCase()))
				return true;*/
		return false;
	}
	
	public static boolean areElements(String[] atoms) {
		boolean flag = true;
		for(String atom: atoms) {
			if (!isElement(atom)) {
				flag = false;
				System.out.println(atom + " is not an atom");
			}
		}
		return flag;
	}
	
	private static String fp = "([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? *)";
	private static String bonding = "( +inter| +intra)?( +bond)?( +kcal| +kjmol)?";
	private static String atom = "([A-Za-z]{1,2} +)";
	private static String flag = "([01] +)";
	
	public static String genAtoms(int numAtoms) {
		String s = "";
		for (int i=0; i < numAtoms; i++)
			s += atom;
		return s;
	}
	
	public static String genBondingOptions() {
		return bonding;
	}
	
	public static String genFloats(int min, int max) {
		String s = "";
		for (int i=0; i < min; i++)
			s += fp;
		for (int i=0; i < max-min; i++)
			s += fp + "?";
		return s;
	}
	
	public static String genFlags(int min, int max) {
		String s = "";
		//TODO determine when fitting is being performed and generate more specific regex
		for (int i=0; i < min; i++)
			s += flag + "?";
		for (int i=0; i < max-min; i++)
			s += flag + "?";
		return s;
	}
	
	private static Random r = new Random();
	
	public static String getRandomFloat() {
		return "" + (r.nextFloat()*1000000000-500000000);
	}
	
	public static String getRandomFloat(double min, double max) {
		return "" + (r.nextFloat()*(max-min)+min);
	}
	
	public static String getRandomElement() {
		return elements[r.nextInt(elements.length)];
	}
}
