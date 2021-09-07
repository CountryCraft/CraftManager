package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.MessageEvent;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.util.StringUtils;

public class MessageCommand extends CMD {
	
	public static final String NAME = "Message";
	public static final String DESC = "Sends a message to the specified player";
	public static final String PERM = "empiremc.message";
	public static final String USAGE = "/message {player} {message} | /msg {player} {message}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	MessageEvent event;
	
	public MessageCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, NoPermException, PNOException
	{
		if (args.length <= 1) throw new NeAException();
		else if (args.length == 2 && args[0].startsWith("-")) throw new NeAException();
		else if (args.length > 1) 
		{
			if (this.hasPerm(SUB[0]))
			{
				target = Bukkit.getPlayer(args[0]);
				if (target != null)
				{
					event = new MessageEvent(sender, target, StringUtils.stringArrayToString(args, 1));
				}
				else throw new PNOException(args[0]);
			}
		}
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "send" };
	}
}
