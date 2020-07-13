package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.SetHealthEvent;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class KillCommand extends CMD {
	
	public static final String NAME = "Kill";
	public static final String DESC = "Kills target player or self";
	public static final String PERM = "countrycraft.kill";
	public static final String USAGE = "/kill [player] ";
	public static final String[] SUB;
	CraftManager craft;
	SetHealthEvent event;
	Player target;
	
	public KillCommand(CraftManager craft, final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0)
		{
			if (this.hasPermission(SUB[0]))
			{
				if (this.isPlayer())
				{
					target = (Player) sender;
					event = new SetHealthEvent(sender, target, 0.0);
				}
				else throw new NotPlayerException();
			}
			else throw new NoPermException();
		}
		else if (args.length == 1)
		{
			if (this.hasPermission(SUB[1]))
			{
				target = Bukkit.getPlayer(args[0]);
				if (target != null)
				{
					event = new SetHealthEvent(sender, target, 0.0);
				}
				else throw new PNOException(args[0]);
			}
			else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}

}
