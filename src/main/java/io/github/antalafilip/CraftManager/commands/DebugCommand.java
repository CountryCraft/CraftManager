package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.GetDebugEvent;
import io.github.antalafilip.CraftManager.events.SetDebugEvent;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class DebugCommand extends CMD {
	
	public static final String NAME = "Debug";
	public static final String DESC = "Toggles server-wide debug mode on or off";
	public static final String PERM = "countrycraft.debug";
	public static final String USAGE = "/debug [on/off]";
	public static final String[] SUB;
	CraftManager craft;
	SetDebugEvent event;
	GetDebugEvent event2;
	
	public DebugCommand(CraftManager craft, final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException
	{
		if (args.length == 0) {
			if (this.hasPerm(SUB[0])) {
				event2 = new GetDebugEvent(sender);
			} else throw new NoPermException();
		}
		else if (args.length == 1) {
			if (this.hasPerm(SUB[0])) {
				if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("on")) event = new SetDebugEvent(sender, true);
				else event = new SetDebugEvent(sender, false);
			} else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
		if (event2 != null) Bukkit.getPluginManager().callEvent(event2);
	}
	
	static {
		SUB = new String[] { "execute" };
	}
}
