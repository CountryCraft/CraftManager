package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.antalafilip.CraftManager.CraftManager;

public class SetMetadataEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private String key;
	private FixedMetadataValue value;
	
	public SetMetadataEvent(CommandSender sender, Player target, String key, Object value) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.key = key;
		this.value = new FixedMetadataValue(CraftManager.craft, value);
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
	}
	public String getKey() {
		return this.key;
	}
	public FixedMetadataValue getValue() {
		return this.value;
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
