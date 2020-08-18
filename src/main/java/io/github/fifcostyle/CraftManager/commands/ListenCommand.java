package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.enums.CmdListenLevel;
import io.github.fifcostyle.CraftManager.events.ToggleListenEvent;
import io.github.fifcostyle.CraftManager.exceptions.InvalidArgumentException;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class ListenCommand extends CMD {
	
	public static final String NAME = "Listen";
	public static final String DESC = "Toggles listening to different events";
	public static final String PERM = "countrycraft.listen";
	public static final String USAGE = "/listen {event/action} {level}";
	public static final String[] SUB;
	CraftManager craft = CraftManager.craft;
	ToggleListenEvent event;
	
	public ListenCommand(final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, InvalidArgumentException
	{
		if (this.isPlayer()) {
			Player target = (Player) sender;
			if (args.length < 2) throw new NeAException(); 
			else if (args.length == 2) {
				if (this.hasPerm(SUB[0])) {
					if (args[0].equalsIgnoreCase("command") || args[0].equalsIgnoreCase("cmd")) {
						if (args[1].equalsIgnoreCase("off")) event = new ToggleListenEvent(sender, target, CmdListenLevel.OFF);
						else if (args[1].equalsIgnoreCase("minimal") || args[1].equalsIgnoreCase("min")) event = new ToggleListenEvent(sender, target, CmdListenLevel.MINIMAL);
						else if (args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("max")) event = new ToggleListenEvent(sender, target, CmdListenLevel.ALL);
						else throw new InvalidArgumentException(args[1]);
					} else throw new InvalidArgumentException(args[1]);
				} else throw new NoPermException();
			} else throw new TmAException();
		} else throw new NotPlayerException();
		
		if (event != null) pmgr.callEvent(event);
	}
	
	static {
		SUB = new String[] { "command" };
	}

}
