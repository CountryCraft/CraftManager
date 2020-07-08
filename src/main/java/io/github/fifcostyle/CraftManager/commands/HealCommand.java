package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.SetFoodEvent;
import io.github.fifcostyle.CraftManager.events.SetHealthEvent;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class HealCommand extends CMD {
	
	public static final String NAME = "Heal";
	public static final String DESC = "Heals target user";
	public static final String PERM = "countrycraft.heal";
	public static final String USAGE = "/heal [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	SetHealthEvent event;
	SetFoodEvent event2;
	
	public HealCommand(final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0) 
		{
			if (this.isPlayer())
			{
				if (this.hasPermission(SUB[0]))
				{
					target = (Player) sender;
					event = new SetHealthEvent(sender, target, 20.0); event2 = new SetFoodEvent(sender, target, 20);
				}
				else throw new NoPermException();
			}
			else throw new NotPlayerException();
		}
		else if (args.length == 1)
		{
			if (this.hasPermission(SUB[1]))
			{
				target = Bukkit.getPlayer(args[0]);
				if (target != null)
				{
					event = new SetHealthEvent(sender, target, 20.0); event2 = new SetFoodEvent(sender, target, 20);
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