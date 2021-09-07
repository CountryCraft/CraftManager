package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SetSpeedEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private Float speed;
	private boolean flying;
	
	public SetSpeedEvent(CommandSender sender, Player target, Float speed) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.speed = speed;
		this.flying = target.isFlying();
	}
	public SetSpeedEvent(CommandSender sender, Player target, Float speed, boolean flying) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.speed = speed;
		this.flying = flying;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
	}
	public Float getSpeed() {
		return this.speed;
	}
	public boolean getFlying() {
		return this.flying;
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
