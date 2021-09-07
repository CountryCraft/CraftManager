package io.github.antalafilip.CraftManager.exceptions;

public class PlayerImmuneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5352309633135505399L;
	
	public PlayerImmuneException(String playerName) {
		super(playerName);
	}

}
