package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.ClearInvEvent;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class ClearInvCommand extends CMD {
	
	public static final String NAME = "Clear inventory";
	public static final String DESC = "Clears target's inventory";
	public static final String PERM = "countrycraft.clearinv";
	public static final String USAGE = "/clearinv [player] | /ci [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	ClearInvEvent event;
	
	public ClearInvCommand(CraftManager craft, final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0) {
			if (this.hasPerm(SUB[0])) {
				if (this.isPlayer()) {
					target = (Player) sender;
					event = new ClearInvEvent(sender, target);
				} else throw new NotPlayerException();
			} else throw new NoPermException();
		}
		else if (args.length == 1) {
			if (this.hasPerm(SUB[1])) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) event = new ClearInvEvent(sender, target);
				else throw new PNOException(args[0]);
			} else throw new NoPermException();
		}
		else if (args.length > 1) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}
}
