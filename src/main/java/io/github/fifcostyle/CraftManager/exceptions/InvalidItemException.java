package io.github.fifcostyle.CraftManager.exceptions;

public class InvalidItemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2919567536139424113L;
	
	public InvalidItemException(String invItem) {
		super(invItem);
	}
}
