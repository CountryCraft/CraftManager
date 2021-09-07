package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.VanishEvent;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;
import io.github.antalafilip.CraftManager.util.MetadataUtils;

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
	MetadataUtils mtdutil;
	String key = "vanished";
	
	public VanishCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length == 0) {
			if (this.hasPerm(SUB[0])) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (MetadataUtils.has(target, key)) event = new VanishEvent(sender, target, false);
					else event = new VanishEvent(sender, target, true);
				}
				else throw new NotPlayerException();
			}
			else throw new NoPermException();
		}
		else if (args.length == 1) {
			if (this.hasPerm(SUB[1])) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (MetadataUtils.has(target, key)) event = new VanishEvent(sender, target, false);
					else event = new VanishEvent(sender, target, true);
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
