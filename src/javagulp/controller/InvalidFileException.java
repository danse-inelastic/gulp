package javagulp.controller;

import java.io.Serializable;

import javagulp.view.Back;

import javax.swing.JOptionPane;

public class InvalidFileException extends Exception implements
Serializable {

	private static final long serialVersionUID = 3422956625510524280L;
	String message;

	public InvalidFileException(final String message) {
		super(message);
		this.message = message;
		this.message += Back.newLine + Back.getCurrentRun().getOutput().selectedInputFile
		+ " was not written.";
	}

	public void displayErrorAsPopup() {
		JOptionPane.showMessageDialog(null, message);
	}

}
