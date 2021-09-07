package io.github.antalafilip.CraftManager.enums;

/**
 * Used for defining different levels of plugin chat prefixes.
 * @author Filip Antala
 *
 */
public enum PrefixLevel {
	
	/**
	 * Used for standard info messages.
	 */
	INFO(),
	
	/**
	 * Used for warning messages, like unsafe commands.
	 */
	WARNING(),
	
	/**
	 * Used for error messages, like failed commands.
	 */
	ERROR(),
	
	/**
	 * Used for staff messages, like staff chat or command listening.
	 */
	STAFF(),
	
	/**
	 * Used for debugging messages, like listening for events.
	 */
	DEBUG();
}
