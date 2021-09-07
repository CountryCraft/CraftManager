package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.Messager;
import io.github.antalafilip.CraftManager.exceptions.InvalidArgumentException;
import io.github.antalafilip.CraftManager.exceptions.InvalidItemException;
import io.github.antalafilip.CraftManager.exceptions.InvalidModifierException;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.NullWorldException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.PlayerImmuneException;
import io.github.antalafilip.CraftManager.exceptions.StrNotIntException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

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
	public CraftManager craft = CraftManager.craft;
	public Messager msgr = craft.getMessager();
	public PluginManager pmgr = Bukkit.getPluginManager();
	
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
		sender.sendMessage(msgr.prefix(this.getUsage()));
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
