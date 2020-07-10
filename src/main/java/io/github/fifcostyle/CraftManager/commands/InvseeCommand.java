package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.OpenInvEvent;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class InvseeCommand extends CMD {
	
	public static final String NAME = "Invsee";
	public static final String DESC = "See and interact with target player's inventory";
	public static final String PERM = "countrycraft.openinv";
	public static final String USAGE = "/invsee {player}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	OpenInvEvent event;
	
	public InvseeCommand(final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException 
	{
		if (args.length == 0) throw new NeAException();
		else if (args.length == 1) 
		{
			if (this.isPlayer()) 
			{
				if (this.hasPermission(SUB[0]))
				{
					target = Bukkit.getPlayer(args[0]);
					if (target != null) event = new OpenInvEvent(sender, (Player) sender, target.getInventory());
				}
				else throw new NoPermException();
			}
			else throw new NotPlayerException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute" };
	}
}
