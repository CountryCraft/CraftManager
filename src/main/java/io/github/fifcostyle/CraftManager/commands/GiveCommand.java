package io.github.fifcostyle.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.fifcostyle.CraftManager.CraftManager;
import io.github.fifcostyle.CraftManager.events.GiveItemEvent;
import io.github.fifcostyle.CraftManager.exceptions.InvalidItemException;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.StrNotIntException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class GiveCommand extends CMD {
	
	public static final String NAME = "Give";
	public static final String DESC = "Gives player target item";
	public static final String PERM = "countrycraft.give";
	public static final String USAGE = "/give | /item | /i [player] {item} [amount]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	ItemStack item;
	GiveItemEvent event;
	
	public GiveCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, InvalidItemException, StrNotIntException
	{
		if (args.length == 0) throw new NeAException();
		if (args.length == 1) {
			if (this.isPlayer()) {
				if (this.hasPermission(SUB[0])) {
					try {
						target = (Player) sender;
						item = new ItemStack(Material.valueOf(args[0].toUpperCase()));
						item.setAmount(64); 
						event = new GiveItemEvent(sender, target, item);
					}
					catch (IllegalArgumentException iae) {
						throw new InvalidItemException(args[0]);
					}
				}
				else throw new NoPermException();
			}
			else throw new NotPlayerException();
		}
		else if (args.length == 2) {
			if (this.hasPermission(SUB[0])) {
				try {
					item = new ItemStack(Material.valueOf(args[0].toUpperCase()));
					if (this.isPlayer()) {
						target = (Player) sender;
						item.setAmount(Integer.parseInt(args[1]));
						event = new GiveItemEvent(sender, target, item);
					}
					else throw new NotPlayerException();
				}
				catch (NumberFormatException nfe) {
					throw new StrNotIntException(args[1]);
				}
				catch (IllegalArgumentException iae) {
					target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (this.hasPermission(SUB[1])) {
							try {
								item = new ItemStack(Material.valueOf(args[1].toUpperCase()));
								item.setAmount(64);
								event = new GiveItemEvent(sender, target, item);
							}
							catch (IllegalArgumentException iaex) {
								throw new InvalidItemException(args[1]);
							}
						}
						else throw new NoPermException();
					}
					else throw new InvalidItemException(args[0]);
				}
			}
			else throw new NoPermException();
		}
		else if (args.length == 3) {
			if (this.hasPermission(SUB[1])) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					try {
						item = new ItemStack(Material.valueOf(args[1].toUpperCase()));
						item.setAmount(Integer.parseInt(args[2]));
						event = new GiveItemEvent(sender, target, item);
					}
					catch (NumberFormatException nfe) {
						throw new StrNotIntException(args[2]);
					}
					catch (IllegalArgumentException iae) {
						throw new InvalidItemException(args[1]);
					}
				}
				else throw new NotPlayerException();
			}
			else throw new NoPermException();
		}
		else if (args.length > 3) throw new TmAException();
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute.self", "execute.others" };
	}
}
