package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GetSpeedEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private boolean isFlying;
	
	public GetSpeedEvent(CommandSender sender, Player target) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.isFlying = target.isFlying();
	}
	public GetSpeedEvent(CommandSender sender, Player target, boolean isFlying) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.isFlying = isFlying;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
	}
	public boolean getFlying() {
		return this.isFlying;
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
