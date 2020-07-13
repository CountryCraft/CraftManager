package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.ClearInvEvent;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class ClearInvCommand extends CMD {
	
	public static final String NAME = "Clear inventory";
	public static final String DESC = "Clears target's inventory";
	public static final String PERM = "countrycraft.debug";
	public static final String USAGE = "/clear [player] | /ci [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	ClearInvEvent event;
	
	public ClearInvCommand(CraftManager craft, final CommandSender sender) {
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
					event = new ClearInvEvent(sender, target);
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
				if (target != null) event = new ClearInvEvent(sender, target);
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
