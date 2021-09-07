package io.github.antalafilip.CraftManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.events.gui.GUIOpenMainEvent;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class MenuCommand extends CMD {
	
	public static final String NAME = "Menu";
	public static final String DESC = "Opens the CraftManager GUI menu";
	public static final String PERM = "countrycraft.gui";
	public static final String USAGE = "/menu";
	public static final String[] SUB;
	GUIOpenMainEvent event;
	
	public MenuCommand(final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException
	{
		if (args.length == 0) {
			if (this.hasPerm(SUB[0])) {
				if (this.isPlayer()) event = new GUIOpenMainEvent(sender, (Player) sender);
				else throw new NotPlayerException();
			} else throw new NoPermException();
		} else throw new TmAException();
		
		if (event != null) pmgr.callEvent(event);
	}
	
	static {
		SUB = new String[] { "use" };
	}
}
