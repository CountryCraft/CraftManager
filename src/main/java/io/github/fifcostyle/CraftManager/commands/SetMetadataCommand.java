package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.enums.PrefixLevel;
import io.github.fifcostyle.CraftManager.events.SetMetadataEvent;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;
import io.github.fifcostyle.CraftManager.util.MetadataUtils;

public class SetMetadataCommand extends CMD {
	
	public static final String NAME = "SetMetadata";
	public static final String DESC = "Sets target's metadata to the given value";
	public static final String PERM = "countrycraft.util.setmetadata";
	public static final String USAGE = "/setmetadata {player} {metadataKey} {(String) metadataValue}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	Player psender;
	SetMetadataEvent event;
	String mKey = "SetMetadataAwaiting";
	
	public SetMetadataCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (args.length < 3) throw new NeAException();
		else if (args.length == 3) {
			if (this.hasPerm()) {
				if (this.isPlayer()) {
					psender = (Player) sender;
					if (craft.getDebugMode()) {
						target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							if (MetadataUtils.has(psender, mKey)) {
								event = new SetMetadataEvent(sender, target, args[1], args[2]);
								MetadataUtils.rem(psender, mKey);
							} else {
								MetadataUtils.set(psender, mKey, true);
								psender.sendMessage(craft.getMessager().format(PrefixLevel.WARNING, "metadata.dangerous"));
								new BukkitRunnable() {
									public void run() {
										if (MetadataUtils.has(psender, mKey)) {
											MetadataUtils.rem(psender, mKey);
											psender.sendMessage(craft.getMessager().format("metadata.expired"));
										}
									}
								}.runTaskLater(craft, 200);
							}
						} else throw new PNOException(args[0]);
					} else sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "debugmode.wrongstate", true));
				} else throw new NotPlayerException();
			} else throw new NoPermException();
		}
		else if (args.length > 3) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] {};
	}

}
