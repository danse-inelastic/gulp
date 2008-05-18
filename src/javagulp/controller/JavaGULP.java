package javagulp.controller;

import java.io.File;
import java.util.ArrayList;

import javagulp.view.Back;

public class JavaGULP {

	private static Back b = null;
	
	public static void main(final String[] args) {
		if (b == null)
			b = new Back();
		else
			b.addTab();
		// this only works for java
		if (args.length == 1) {
			String contents = Back.getFileContents(new File(args[0]));
			Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(contents);
		}
		// this only works for java web start
		if (args.length == 2) {
			String contents = Back.getFileContents(new File(args[0]));
			Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(contents);
		}
		if (args.length == 7) {
			String contents = Back.getFileContents(new File(args[0]));
			Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(contents);
			Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setParameters(args);
		}
		if (args.length == 10) {
			Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(args[0]);
			Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(args);
		}
//		if (args.length == 10) {
//			String contents = Back.getFileContents(new File(args[0]));
//			Back.getStructure().atomicCoordinates.getTableModel().importCoordinates(contents);
//			Back.getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(args);
//		}
	}
	
	public static void main(final ArrayList<ArrayList<String>> species, final ArrayList<ArrayList<double[]>> coordinates, final ArrayList<String[]> args, final ArrayList<String> names) {
		if (b == null)
			b = new Back();
		else
			b.addTab();
		System.out.println("args.size " + args.size());
		Back.getPanel().getStructures().importStructures(species, coordinates, args, names);
	}
}
