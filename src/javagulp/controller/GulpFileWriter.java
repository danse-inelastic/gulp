package javagulp.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javagulp.view.Back;
import javagulp.view.Fit;
import javagulp.view.FreeEnergy;
import javagulp.view.GulpRun;
import javagulp.view.MolecularDynamics;
import javagulp.view.MonteCarlo;
import javagulp.view.Optimization;
import javagulp.view.Phonons;
import javagulp.view.StructurePrediction;
import javagulp.view.Structures;
import javagulp.view.SurfaceOptions;
import javagulp.view.Structures.Structure;

import javax.swing.JOptionPane;

public class GulpFileWriter {

	public boolean incomplete = false;

	public String gulpInputFileToString() {
		StringBuffer o = new StringBuffer();
		incomplete = false;
		try {
			GulpRun gr = Back.getCurrentRun();
			o.append(Back.getTaskKeywords().writeTaskKeywords());
			o.append(Back.getKeys().writeKeywords());
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
			o.append(((SurfaceOptions)gr.getSelectedRunTypePanel("surface")).writeSurface());
			o.append(gr.getPotential().writeLibrary());
			o.append(gr.getPotential().createLibrary.writePotentials());
			o.append(gr.getConstraints().writeUnfreeze());
			o.append(gr.getEwaldOptions().writeEwald());
			o.append(gr.getPotentialOptions().writePotentialOptions());
			o.append(gr.getElectrostatics().writeElectrostatics());
			o.append(((Optimization)gr.getSelectedRunTypePanel("surface")).writeOptimization());
			o.append(gr.getChargesElementsBonding().writeChargesElementsBonding());
			o.append(((StructurePrediction)gr.getSelectedRunTypePanel("structure prediction")).writeStructurePrediction());
			o.append(((Phonons)gr.getSelectedRunTypePanel("phonons")).writePhonon());
			o.append(((FreeEnergy)gr.getSelectedRunTypePanel("free energy")).writeFreeEnergy());
//			o.append(gr.getDefect().writeDefect());
			o.append(((Fit)gr.getSelectedRunTypePanel("fit")).writeFitOptions());
			o.append(((Fit)gr.getSelectedRunTypePanel("fit")).fitPanelHolder.writeFitPanels());
			o.append(((MonteCarlo)gr.getSelectedRunTypePanel("monte carlo")).writeMonteCarlo());
			o.append(((MolecularDynamics)gr.getSelectedRunTypePanel("molecular dynamics")).writeMD());
			o.append(gr.getExternalForce().writeExternalForce());
			o.append(gr.getOutput().writeExecute());
		} catch (IncompleteOptionException e) {
			e.displayErrorAsPopup();
			incomplete = true;
		} catch (NumberFormatException nfe) {
			//nfe.printStackTrace();
			JOptionPane.showMessageDialog(null, nfe.getMessage(),
					"NumberFormatException", JOptionPane.ERROR_MESSAGE);
			incomplete = true;
		} catch (InvalidOptionException e) {
			e.displayErrorAsPopup();
			incomplete = true;
		}
		
		Scanner sc = new Scanner(o.toString());
		StringBuffer sb = new StringBuffer();
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
				BufferedWriter out = new BufferedWriter(new FileWriter(Back.getCurrentRun().getWD() + Back.newLine + outputFileName));
				out.write(output);
				out.close();
			}
		} catch (final IOException e) {
		}
	}
}