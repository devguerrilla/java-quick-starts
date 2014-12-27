package com.devguerrilla.quickstarts.java.turkeyservice;

/**
 * Simple application exception thrown when a service request is made
 * for a type of meat we don't know how to cook.
 */
public class AnimalNotFoundException extends Exception {

	/** For serialisation */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which accepts an explanatory message
	 * @param message error message
	 */
	public AnimalNotFoundException(String message) {
		super(message);
	}
}
