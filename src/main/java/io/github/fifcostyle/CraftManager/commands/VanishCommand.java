package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.VanishEvent;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class VanishCommand extends CMD {
	
	public static final String NAME = "Vanish";
	public static final String DESC = "Hides target player from others";
	public static final String PERM = "countrycraft.vanish";
	public static final String USAGE = "/vanish | /v [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	boolean state;
	VanishEvent event;
	
	public VanishCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0) {
			if (this.hasPermission(SUB[0])) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (target.hasMetadata("vanished")) event = new VanishEvent(sender, target, false);
					else event = new VanishEvent(sender, target, true);
				}
				else throw new NotPlayerException();
			}
			else throw new NoPermException();
		}
		else if (args.length == 1) {
			if (this.hasPermission(SUB[1])) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (target.hasMetadata("vanished")) event = new VanishEvent(sender, target, false);
					else event = new VanishEvent(sender, target, true);
				}
				else throw new PNOException(args[0]);
			}
			else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}
}
