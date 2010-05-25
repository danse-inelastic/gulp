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
	}
	
	public InvalidFileException() {
		this.message = "File contents could not be read.";
	}

	public void displayErrorAsPopup(final String filename) {
		this.message += " File was "+filename+".";
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void displayErrorAsPopup() {
		JOptionPane.showMessageDialog(null, message);
	}

}
