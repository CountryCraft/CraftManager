package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.enums.PrefixLevel;
import io.github.fifcostyle.CraftManager.events.GetSpeedEvent;
import io.github.fifcostyle.CraftManager.events.SetSpeedEvent;
import io.github.fifcostyle.CraftManager.exceptions.InvalidArgumentException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.StrNotIntException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class SpeedCommand extends CMD {
	
	public static final String NAME = "Speed";
	public static final String DESC = "Sets target's speed";
	public static final String PERM = "countrycraft.speed";
	public static final String USAGE = "/speed {target} {speed}";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	SetSpeedEvent event;
	GetSpeedEvent event2;
	
	public SpeedCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws TmAException, NoPermException, NotPlayerException, PNOException, StrNotIntException, InvalidArgumentException
	{
		if (args.length == 0) {
			if (this.isPlayer()) {
				if (this.hasPerm(SUB[0])) {
					target = (Player) sender;
					event2 = new GetSpeedEvent(sender, target);
				} else throw new NoPermException();
			} else throw new NotPlayerException();
		}
		else if (args.length == 1) {
			if (this.isPlayer()) {
				target = (Player) sender;
				if (this.hasPerm(SUB[0])) {
					try {
						Float f = Float.parseFloat(args[0]);
						if (!(f > 10)) {
							Float ff = 10f;
							Float fff = f/ff;
							event = new SetSpeedEvent(sender, target, fff);
						} else {
							sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!"));
							throw new InvalidArgumentException(args[0]);
						}
					} catch (NumberFormatException e) {
						throw new StrNotIntException(args[0]);
					}
				} else throw new NoPermException();
			} else throw new NotPlayerException();
		}
		else if (args.length == 2) {
			target = Bukkit.getPlayer(args[0]); if (target != null) {
				if (this.hasPerm(SUB[1])) {
					try {
						Float f = Float.parseFloat(args[1]);
						if (!(f > 10)) {
							Float ff = 10f;
							Float fff = f/ff;
							event = new SetSpeedEvent(sender, target, fff);
						} else sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!")); throw new InvalidArgumentException(args[1]);
					} catch (NumberFormatException e) {
						throw new StrNotIntException(args[1]);
					}
				} else throw new NoPermException();
			} 
			else if (args[0].equalsIgnoreCase("walk")) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[0])) {
						try {
							Float f = Float.parseFloat(args[1]);
							if (!(f > 10)) {
								Float ff = 10f;
								Float fff = f/ff;
								event = new SetSpeedEvent(sender, target, fff, true);
							} else sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!")); throw new InvalidArgumentException(args[1]);
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[1]);
						}
					} else throw new NoPermException();
				} else throw new NotPlayerException();
			}
			else if (args[0].equalsIgnoreCase("fly")) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[0])) {
						try {
							Float f = Float.parseFloat(args[1]);
							if (!(f > 10)) {
								Float ff = 10f;
								Float fff = f/ff;
								event = new SetSpeedEvent(sender, target, fff, true);
							} else sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!")); throw new InvalidArgumentException(args[1]);
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[1]);
						}
					} else throw new NoPermException();
				} else throw new NotPlayerException();
			} else throw new PNOException(args[0]);
		}
		else if (args.length == 3) {
			if (this.hasPerm(SUB[1])) {
				target = Bukkit.getPlayer(args[0]); if (target != null) {
					if (args[1].equalsIgnoreCase("walk")) {
						try {
							Float f = Float.parseFloat(args[2]);
							if (!(f > 10)) {
								Float ff = 10f;
								Float fff = f/ff;
								event = new SetSpeedEvent(sender, target, fff, false);
							} else sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!")); throw new InvalidArgumentException(args[2]);
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[2]);
						}
					} 
					else if (args[1].equalsIgnoreCase("fly")) {
						try {
							Float f = Float.parseFloat(args[2]);
							if (!(f > 10)) {
								Float ff = 10f;
								Float fff = f/ff;
								event = new SetSpeedEvent(sender, target, fff, true);
							} else sender.sendMessage(craft.getMessager().prefix(PrefixLevel.ERROR, "The speed value must be lower or equal to 10!")); throw new InvalidArgumentException(args[2]);
						} catch (NumberFormatException e) {
							throw new StrNotIntException(args[2]);
						}
					} else throw new InvalidArgumentException(args[1]);
				} else throw new PNOException(args[0]);
			} else throw new NoPermException();
		}
		else if (args.length > 3) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
		if (event2 != null) Bukkit.getPluginManager().callEvent(event2);
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}

}
