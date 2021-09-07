package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class OpenInvEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private Inventory inv;
	
	public OpenInvEvent(CommandSender sender, Player target, Inventory inv) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.inv = inv;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
	}
	public Inventory getInv() {
		return this.inv;
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
