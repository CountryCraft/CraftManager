package io.github.fifcostyle.CraftManager.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeleportEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private CommandSender sender;
	private Player toTeleport;
	private Player teleportTo;
	private Location tpLocation;
	private String lobbyName;
	private String mode;
	private boolean isUnsafe;
	private boolean force;
	private boolean override;
	
	//Player to player
	public TeleportEvent(CommandSender sender, Player toTeleport, Player teleportTo, boolean force, boolean override) {
		this.isCancelled = false;
		this.sender = sender;
		this.toTeleport = toTeleport;
		this.teleportTo = teleportTo;
		this.force = force;
		this.override = override;
		this.mode = "PlayerToPlayer";
		this.isUnsafe = checkUnsafe(teleportTo.getLocation());
	}
	//Player to location
	public TeleportEvent(CommandSender sender, Player toTeleport, Location tpLocation, boolean force, boolean override) {
		this.isCancelled = false;
		this.sender = sender;
		this.toTeleport = toTeleport;
		this.tpLocation = tpLocation;
		this.force = force;
		this.override = override;
		this.mode = "PlayerToLoc";
		this.isUnsafe = checkUnsafe(tpLocation);
	}
	//Player to lobby
	public TeleportEvent(boolean toLobby, CommandSender sender, Player toTeleport, String lobbyName) {
		this.isCancelled = false;
		this.sender = sender;
		this.toTeleport = toTeleport;
		this.lobbyName = lobbyName;
		this.mode = "PlayerToLobby";
		this.isUnsafe = false;
	}
	
	private boolean checkUnsafe(Location loc) {
		if (loc.getBlock().getType() == Material.AIR) return false;
		else return true;
	}
	public CommandSender getSender() {
		return this.sender;
	}
	public Player getToTp() {
		return this.toTeleport;
	}
	public Player getTpTo() { 
		return this.teleportTo;
	}
	public Location getTpLoc() {
		return this.tpLocation;
	}
	public String getMode() {
		return this.mode;
	}
	public boolean isUnsafe() {
		return this.isUnsafe;
	}
	public boolean getForce() {
		return this.force;
	}
	public boolean getOverride() {
		return this.override;
	}
	public String getLobbyName() {
		return this.lobbyName;
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
