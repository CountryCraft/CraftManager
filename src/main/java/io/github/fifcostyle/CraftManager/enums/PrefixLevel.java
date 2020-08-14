package io.github.fifcostyle.CraftManager.enums;

public enum PrefixLevel {
	
	/*
	 * Used for standard messages
	 */
	INFO(),
	
	/*
	 * Used for warning messages, like unsafe commands
	 */
	WARNING(),
	
	/*
	 * Used for error messages, like failed commands
	 */
	ERROR(),
	
	/*
	 * Used for staff messages, like staffchat or command listening
	 */
	STAFF(),
	
	/*
	 * Used for debugging messages, like listening for events and such
	 */
	DEBUG();
}
