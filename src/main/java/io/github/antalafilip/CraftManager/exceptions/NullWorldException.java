package io.github.antalafilip.CraftManager.exceptions;

public class NullWorldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8199257219939553067L;
	
	public NullWorldException(String worldName) {
		super(worldName);
	}

}
