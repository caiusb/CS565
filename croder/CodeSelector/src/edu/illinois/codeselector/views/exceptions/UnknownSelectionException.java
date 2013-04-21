package edu.illinois.codeselector.views.exceptions;


public class UnknownSelectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -952467780768998699L;

	public UnknownSelectionException(String selectionType) {
		super(selectionType);
	}

}
