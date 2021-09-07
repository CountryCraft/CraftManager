package io.github.antalafilip.CraftManager.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.github.antalafilip.CraftManager.enums.CmdListenLevel;

public class ToggleListenEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player target;
	private CmdListenLevel level;
	
	public ToggleListenEvent(CommandSender sender, Player target, CmdListenLevel level) {
		this.isCancelled = false;
		this.sender = sender;
		this.target = target;
		this.level = level;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getTarget() {
		return this.target;
	}
	public CmdListenLevel getLevel() {
		return this.level;
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
