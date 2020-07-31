package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.GetMetadataEvent;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class GetMetadataCommand extends CMD {
	
	public static final String NAME = "GetMetadata";
	public static final String DESC = "Gets target Metadata values";
	public static final String PERM = "countrycraft.util.getmetadata";
	public static final String USAGE = "/getmetadata {player} {metadatakey}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	GetMetadataEvent event;
	
	public GetMetadataCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, PNOException
	{
		if (args.length < 2) throw new NeAException();
		else if (args.length == 2) {
			if (this.hasPermission()) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) event = new GetMetadataEvent(sender, target, args[1]);
				else throw new PNOException(args[0]);
			} else throw new NoPermException();
		}
		else if (args.length > 2) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "" };
	}
}
