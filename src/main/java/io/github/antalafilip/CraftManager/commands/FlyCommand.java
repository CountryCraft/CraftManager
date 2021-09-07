package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.SetFlyEvent;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class FlyCommand extends CMD {
	
	public static final String NAME = "Fly";
	public static final String DESC = "Toggles user's fly";
	public static final String PERM = "countrycraft.fly";
	public static final String USAGE = "/fly [player] [on/off]";
	public static final String[] SUB;
	CraftManager craft;
	SetFlyEvent event;
	
	public FlyCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	@Override
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0) {
			if (sender instanceof Player) {
				if (this.hasPerm(SUB[0])) {
        				Player target = (Player) sender;
        				if (target.getAllowFlight()) event = new SetFlyEvent(sender, target, false);
        				else event = new SetFlyEvent(sender, target, true);
    				} else throw new NoPermException();
			} else throw new NotPlayerException();
		}
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("true")) {
				if (this.isPlayer()) {
					if (this.hasPerm(SUB[0])) {
						Player target = (Player) sender;
						event = new SetFlyEvent(sender, target, true);
					} else throw new NoPermException();
				} else throw new NotPlayerException();
			}
			else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("false")) {
				if (this.isPlayer()) {
					if (this.hasPerm(SUB[0])) {
						Player target = (Player) sender;
						event = new SetFlyEvent(sender, target, false);
					} else throw new NoPermException();
				} else throw new NotPlayerException();
			}
			else if (this.hasPerm(SUB[1])) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (target.getAllowFlight()) event = new SetFlyEvent(sender, target, false);
	    				else event = new SetFlyEvent(sender, target, true);
				} else throw new PNOException(args[0]);
			} else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}
}
