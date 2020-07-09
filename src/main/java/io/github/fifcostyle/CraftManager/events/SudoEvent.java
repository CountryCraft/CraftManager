package io.github.fifcostyle.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SudoEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private String command;
	
	public SudoEvent(CommandSender sender, Player target, String command) {
		this.isCancelled = false;
		this.sender = sender;
		this.command = command;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public String getCommand() {
		return this.command;
	}
	public Player getTarget() {
		return this.target;
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
