package io.github.antalafilip.CraftManager.exceptions;

public class PNOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5537150558593549957L;
	
	public PNOException(String playerName) {
		super(playerName);
	}
}
