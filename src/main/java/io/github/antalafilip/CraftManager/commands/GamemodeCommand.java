package io.github.antalafilip.CraftManager.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.antalafilip.CraftManager.CraftManager;
import io.github.antalafilip.CraftManager.events.SetGamemodeEvent;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class GamemodeCommand extends CMD {
	
	public static final String NAME = "Gamemode";
	public static final String DESC = "Changes target's gamemode";
	public static final String PERM = "countrycraft.gamemode";
	public static final String USAGE = "/gamemode | /gm [player] {gamemode} || /gm{gamemode prefix} [player]";
	public static final String[] SUB;
	CraftManager craft;
	Player target;
	GameMode gamemode;
	SetGamemodeEvent event;
	
	public GamemodeCommand(CraftManager craft, final CommandSender sender) {
		super(sender, NAME, DESC, PERM, SUB, USAGE);
		this.craft = craft;
	}
	
	public void run(CommandSender sender, Command cmd, String label, String[] args) throws NeAException, TmAException, NoPermException, NotPlayerException, PNOException
	{
		if (cmd.getName().equalsIgnoreCase("gamemode") || cmd.getName().equalsIgnoreCase("gm")) {
			if (args.length == 0) throw new NeAException();
			else if (args.length == 1) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
						if (this.hasPerm(SUB[0])) event = new SetGamemodeEvent(sender, target, GameMode.SURVIVAL);
						else throw new NoPermException();
					}
					else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
						if (this.hasPerm(SUB[1])) event = new SetGamemodeEvent(sender, target, GameMode.CREATIVE);
						else throw new NoPermException();
					}
					else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
						if (this.hasPerm(SUB[2])) event = new SetGamemodeEvent(sender, target, GameMode.ADVENTURE);
						else throw new NoPermException();
					}
					else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
						if (this.hasPerm(SUB[3])) event = new SetGamemodeEvent(sender, target, GameMode.SPECTATOR);
						else throw new NoPermException();
					}
					else sender.sendMessage(craft.getMessager().prefix(args[0] + "is not a valid gamemode"));
				}
				else throw new NotPlayerException();
			}
			else if (args.length == 2) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (args[1].equalsIgnoreCase("survival") || args[1].equalsIgnoreCase("s") || args[1].equalsIgnoreCase("0")) {
						if (this.hasPerm(SUB[4])) event = new SetGamemodeEvent(sender, target, GameMode.SURVIVAL);
						else throw new NoPermException();
					}
					else if (args[1].equalsIgnoreCase("creative") || args[1].equalsIgnoreCase("c") || args[1].equalsIgnoreCase("1")) {
						if (this.hasPerm(SUB[5])) event = new SetGamemodeEvent(sender, target, GameMode.CREATIVE);
						else throw new NoPermException();
					}
					else if (args[1].equalsIgnoreCase("adventure") || args[1].equalsIgnoreCase("a") || args[1].equalsIgnoreCase("2")) {
						if (this.hasPerm(SUB[6])) event = new SetGamemodeEvent(sender, target, GameMode.ADVENTURE);
						else throw new NoPermException();
					}
					else if (args[1].equalsIgnoreCase("spectator") || args[1].equalsIgnoreCase("sp") || args[1].equalsIgnoreCase("3")) {
						if (this.hasPerm(SUB[7])) event = new SetGamemodeEvent(sender, target, GameMode.SPECTATOR);
						else throw new NoPermException();
					}
					else sender.sendMessage(craft.getMessager().prefix(args[0] + "is not a valid gamemode"));
				}
				else throw new PNOException(args[0]);
			}
			else throw new TmAException();
		}
		else if (cmd.getName().equalsIgnoreCase("gms")) {
			if (args.length == 0) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[0])) event = new SetGamemodeEvent(sender, target, GameMode.SURVIVAL);
					else throw new NoPermException();
				}
				else throw new NotPlayerException();
			}
			else if (args.length == 1) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (this.hasPerm(SUB[3])) event = new SetGamemodeEvent(sender, target, GameMode.SURVIVAL);
					else throw new NoPermException();
				}
				else throw new PNOException(args[0]);
			}
			else throw new TmAException();
		}
		else if (cmd.getName().equalsIgnoreCase("gmc")) {
			if (args.length == 0) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[1])) event = new SetGamemodeEvent(sender, target, GameMode.CREATIVE);
					else throw new NoPermException();
				}
				else throw new NotPlayerException();
			}
			else if (args.length == 1) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (this.hasPerm(SUB[4])) event = new SetGamemodeEvent(sender, target, GameMode.CREATIVE);
					else throw new NoPermException();
				}
				else throw new PNOException(args[0]);
			}
			else throw new TmAException();
		}
		else if (cmd.getName().equalsIgnoreCase("gma")) {
			if (args.length == 0) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[2])) event = new SetGamemodeEvent(sender, target, GameMode.ADVENTURE);
					else throw new NoPermException();
				}
				else throw new NotPlayerException();
			}
			else if (args.length == 1) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (this.hasPerm(SUB[5])) event = new SetGamemodeEvent(sender, target, GameMode.ADVENTURE);
					else throw new NoPermException();
				}
				else throw new PNOException(args[0]);
			}
			else throw new TmAException();
		}
		else if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (args.length == 0) {
				if (this.isPlayer()) {
					target = (Player) sender;
					if (this.hasPerm(SUB[3])) event = new SetGamemodeEvent(sender, target, GameMode.SPECTATOR);
					else throw new NoPermException();
				}
				else throw new NotPlayerException();
			}
			else if (args.length == 1) {
				target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (this.hasPerm(SUB[7])) event = new SetGamemodeEvent(sender, target, GameMode.SPECTATOR);
					else throw new NoPermException();
				}
				else throw new PNOException(args[0]);
			}
			else throw new TmAException();
		}
		
		if (event != null) Bukkit.getPluginManager().callEvent(event);
	}
	
	static {
		SUB = new String[] { "execute.self.survival", "execute.self.creative", "execute.self.adventure", "execute.self.spectator", "execute.others.survival", "execute.others.creative", "execute.others.adventure", "execute.others.spectator" };
	}
}
