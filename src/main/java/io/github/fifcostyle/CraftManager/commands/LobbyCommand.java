package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.TeleportEvent;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class LobbyCommand extends CMD {
	
	public static final String NAME = "Clear inventory";
	public static final String DESC = "Clears target's inventory";
	public static final String PERM = "countrycraft.debug";
	public static final String USAGE = "/clear [player] | /ci [player]";
	public static final String[] SUB;
	CraftManager craft;
	TeleportEvent event;
	
	public LobbyCommand(final CommandSender sender) {
		super (sender, NAME, DESC, PERM, SUB, USAGE);
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException
	{
		
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}

}
