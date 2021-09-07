package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SetDebugEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private boolean state;
	
	public SetDebugEvent(CommandSender sender, boolean state) {
		this.isCancelled = false;
		this.sender = sender;
		this.state = state;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public boolean getState() {
		return this.state;
	}
	
	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
