package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.TeleportEvent;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class SpawnCommand extends CMD {
	
	public static final String NAME = "Spawn";
	public static final String DESC = "Teleports the player to the spawnpoint for this server";
	public static final String PERM = "empiremc.spawntp";
	public static final String USAGE = "/spawn [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	TeleportEvent event;
	
	public SpawnCommand(CraftManager craft, final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0)
		{
			if (this.hasPerm())
			{
				if (this.isPlayer())
				{
					target = (Player) sender;
					event = new TeleportEvent(true, sender, target);
				}
				else throw new NotPlayerException();
			}
			else throw new NoPermException();
		}
		else if (args.length == 1)
		{
			if (this.hasPerm(SUB[0]))
			{
				target = Bukkit.getPlayer(args[0]);
				if (target != null) event = new TeleportEvent(true, sender, target);
				else throw new PNOException(args[0]);
			}
			else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "others" };
	}

}
