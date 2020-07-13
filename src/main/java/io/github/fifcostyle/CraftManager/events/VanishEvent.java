package io.github.fifcostyle.CraftManager.events;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VanishEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private boolean state;
	
	public VanishEvent(CommandSender sender, Player target, boolean state) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.state = state;
	}
	public VanishEvent(Player target, boolean state) {
		this.isCancelled = false;
		this.sender = Bukkit.getConsoleSender();
		this.target = target;
		this.state = state;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
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
