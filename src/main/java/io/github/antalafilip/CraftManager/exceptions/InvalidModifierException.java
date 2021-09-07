package io.github.antalafilip.CraftManager.exceptions;

public class InvalidModifierException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7364549609971853428L;
	
	public InvalidModifierException(String invalidModifier) {
		super(invalidModifier);
	}

}
