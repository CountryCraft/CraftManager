package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.SudoEvent;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.PlayerImmuneException;
import io.github.fifcostyle.CraftManager.util.StringUtils;

public class SudoCommand extends CMD {
	
	public static final String NAME = "Sudo";
	public static final String DESC = "Runs a command as the target user";
	public static final String PERM = "countrycraft.sudo";
	public static final String USAGE = "/sudo {player} {command}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	SudoEvent event;
	
	public SudoCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, NoPermException, PNOException, PlayerImmuneException
	{
		if (args.length <= 1) throw new NeAException();
		else if (args.length == 2 && args[0].startsWith("-")) throw new NeAException();
		else if (args.length > 1) 
		{
			if (this.hasPerm(SUB[0]))
			{
				/*if (args[0].startsWith("-"))
				{
					if (args[0].equalsIgnoreCase("-c") || args[0].equalsIgnoreCase("-chat"))
					{
						
					}
				}
				else
				{*/
					target = Bukkit.getPlayer(args[0]);
					if (target != null)
					{
						if (!target.hasPermission(PERM + "." + SUB[1]))
						{
							event = new SudoEvent(sender, target, StringUtils.stringArrayToString(args, 1));
						}
						else throw new PlayerImmuneException(target.getName());
					}
					else throw new PNOException(args[0]);
				//}
			}
		}
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute", "exempt" };
	}
}
