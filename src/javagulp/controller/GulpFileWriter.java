package javagulp.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javagulp.view.Back;
import javagulp.view.GulpRun;
import javagulp.view.SurfaceOptions;

import javax.swing.JOptionPane;

public class GulpFileWriter {

	public boolean incomplete = false;

	public String gulpInputFileToString() {
		StringBuffer completeFile = null;
		StringBuffer o = new StringBuffer();
		incomplete = false;
		try {
			final GulpRun gr = Back.getCurrentRun();
			o.append(gr.getOutput().writeTitleAndTimeLimit());
			//			String energy = gr.getXyzfit().writeEnergy();
			//			if (!energy.equals(""))
			//				o.append(energy);
			//			else {
			//				if (Back.getPanel().getOutput().chkSeparate.isSelected())
			//					o.append(Back.getStructure().writeStructure());
			//				else
			//					o.append(Back.getPanel().getStructures().writeStructures());
			//			}
			o.append(Back.getStructure().writeStructure());
			o.append(((SurfaceOptions)gr.getSelectedRunTypePanel("surface calc/optimize")).writeSurface());
			o.append(gr.getPotential().writeLibrary());
			o.append(gr.getPotential().createLibrary.writePotentials());
			//o.append(gr.getConstraints().writeUnfreeze());
			o.append(gr.getEwaldOptions().writeEwald());
			o.append(gr.getPotentialOptions().writePotentialOptions());
			o.append(gr.getElectrostatics().writeElectrostatics());
			o.append(gr.getChargesElementsBonding().writeChargesElementsBonding());
			o.append(gr.getRunTypePanel().writeRuntype());
			o.append(gr.getExternalForce().writeExternalForce());
			o.append(gr.getOutput().writeExecute());
			//write keywords last after checking all the other components and storing keywords
			completeFile = new StringBuffer(Back.getTaskKeywords().writeTaskKeywords()+Back.getKeys().writeKeywords()).append(o);
		} catch (final IncompleteOptionException e) {
			e.displayErrorAsPopup();
			incomplete = true;
		} catch (final NumberFormatException nfe) {
			//nfe.printStackTrace();
			JOptionPane.showMessageDialog(null, nfe.getMessage(),
					"NumberFormatException", JOptionPane.ERROR_MESSAGE);
			incomplete = true;
		} catch (final InvalidOptionException e) {
			e.displayErrorAsPopup();
			incomplete = true;
		}

		final Scanner sc = new Scanner(completeFile.toString());
		final StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			while (line.length() > 77) {
				// limit 80 characters on a line, but leave room for & and newLine
				// TODO make sure we can cut the line arbitrarily, and that it
				// doesn't have to be between values or something
				sb.append(line.substring(0, 77) + "&" + Back.newLine);
				line = line.substring(77);
			}
			sb.append(line + Back.newLine);
		}
		return sb.toString();
	}

	public void writeAll(String output, String outputFileName) {
		try {
			if (!incomplete) {
				final BufferedWriter out = new BufferedWriter(new FileWriter(Back.getCurrentRun().getWD() + Back.newLine + outputFileName));
				out.write(output);
				out.close();
			}
		} catch (final IOException e) {
		}
	}
}