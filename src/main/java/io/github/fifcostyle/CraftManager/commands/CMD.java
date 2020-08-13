package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.exceptions.InvalidArgumentException;
import io.github.fifcostyle.CraftManager.exceptions.InvalidItemException;
import io.github.fifcostyle.CraftManager.exceptions.InvalidModifierException;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.NullWorldException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.PlayerImmuneException;
import io.github.fifcostyle.CraftManager.exceptions.StrNotIntException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public abstract class CMD {
	
	/*
	public static final String NAME = "";
	public static final String DESC = "";
	public static final String PERM = "";
	public static final String USAGE = "";
	public static final String[] SUB;
	CraftManager craft;
	*/
	private final CommandSender sender;
	private final String name;
	private final String description;
	private final String permission;
	private final String usage;
	private final String[] subPermissions;
	CraftManager craft = CraftManager.craft;
	
	public CMD(final CommandSender sender, final String name, final String description, final String permission, final String[] subPermissions, final String usage) {
		this.sender = sender;
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.usage = usage;
		this.subPermissions = subPermissions;
	}
	
	public CommandSender getSender() {
		return this.sender;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public String[] getSubPermissions() {
		return this.subPermissions;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public void sendUsage() {
		sender.sendMessage(craft.getMessager().prefix(this.getUsage()));
	}
	
	public boolean hasPerm() { 
		return this.sender.hasPermission(permission) || this.isConsole() || this.isRemoteConsole();
	}
	
	public boolean hasPerm(final String sub) {
        final String permission = this.permission + "." + sub;
        return this.sender.hasPermission(permission) || this.isConsole() || this.isRemoteConsole();
    }
	
	public boolean isPlayer() {
        return this.sender instanceof Player;
    }
    
    public boolean isConsole() {
        return this.sender instanceof ConsoleCommandSender;
    }
    
    public boolean isRemoteConsole() {
        return this.sender instanceof RemoteConsoleCommandSender;
    }
    
	
	public abstract void run (final CommandSender p0, final Command p1, final String p2, final String[] p3) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException, PlayerImmuneException, InvalidItemException, StrNotIntException, InvalidModifierException, NullWorldException, InvalidArgumentException;

}
