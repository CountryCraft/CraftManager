package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.StaffChatEvent;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.util.StringUtils;

public class StaffChatCommand extends CMD {
	
	public static final String NAME = "StaffChat";
	public static final String DESC = "Sends a staff-only message";
	public static final String PERM = "countrycraft.staffchat";
	public static final String USAGE = "/staffchat {message} | /sc {message}";
	public static final String[] SUB;
	CraftManager craft;
	StaffChatEvent event;
	
	public StaffChatCommand(CraftManager craft, final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, NoPermException
	{
		if (args.length == 0) throw new NeAException();
		else if (args.length > 0) 
		{
			if (this.hasPerm(SUB[0]))
			{
				final String[] arguments = new String[args.length];
				if (arguments.length > 0)
				{
					System.arraycopy(args, 0, arguments, 0, args.length);
					final String message = StringUtils.stringArrayToString(arguments, 0);
					if (message != null && message.length() > 0)
					{
						event = new StaffChatEvent(sender, message);
					}
				}
			}
			else throw new NoPermException();
		}
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "send" };
	}
}
