package io.github.antalafilip.CraftManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.antalafilip.CraftManager.commands.CMD;
import io.github.antalafilip.CraftManager.commands.ClearInvCommand;
import io.github.antalafilip.CraftManager.commands.DebugCommand;
import io.github.antalafilip.CraftManager.commands.FeedCommand;
import io.github.antalafilip.CraftManager.commands.FlyCommand;
import io.github.antalafilip.CraftManager.commands.GamemodeCommand;
import io.github.antalafilip.CraftManager.commands.GetMetadataCommand;
import io.github.antalafilip.CraftManager.commands.GiveCommand;
import io.github.antalafilip.CraftManager.commands.HealCommand;
import io.github.antalafilip.CraftManager.commands.InvseeCommand;
import io.github.antalafilip.CraftManager.commands.KillCommand;
import io.github.antalafilip.CraftManager.commands.ListenCommand;
import io.github.antalafilip.CraftManager.commands.MenuCommand;
import io.github.antalafilip.CraftManager.commands.MessageCommand;
import io.github.antalafilip.CraftManager.commands.SetMetadataCommand;
import io.github.antalafilip.CraftManager.commands.SpawnCommand;
import io.github.antalafilip.CraftManager.commands.SpeedCommand;
import io.github.antalafilip.CraftManager.commands.StaffChatCommand;
import io.github.antalafilip.CraftManager.commands.SudoCommand;
import io.github.antalafilip.CraftManager.commands.TeleportCommand;
import io.github.antalafilip.CraftManager.commands.VanishCommand;
import io.github.antalafilip.CraftManager.enums.PrefixLevel;
import io.github.antalafilip.CraftManager.exceptions.InvalidArgumentException;
import io.github.antalafilip.CraftManager.exceptions.InvalidItemException;
import io.github.antalafilip.CraftManager.exceptions.InvalidModifierException;
import io.github.antalafilip.CraftManager.exceptions.NeAException;
import io.github.antalafilip.CraftManager.exceptions.NoPermException;
import io.github.antalafilip.CraftManager.exceptions.NotPlayerException;
import io.github.antalafilip.CraftManager.exceptions.NullWorldException;
import io.github.antalafilip.CraftManager.exceptions.PNOException;
import io.github.antalafilip.CraftManager.exceptions.PlayerImmuneException;
import io.github.antalafilip.CraftManager.exceptions.StrNotIntException;
import io.github.antalafilip.CraftManager.exceptions.TmAException;

public class CmdHandler implements CommandExecutor {
	CraftManager craft;
	
	public CmdHandler(CraftManager craft) {
		this.craft = craft;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CMD cmd = null;
		switch (command.getName().toLowerCase()) {
		case "fly":
			cmd = new FlyCommand(craft, sender);
			break; 
		case "debug":
			cmd = new DebugCommand(craft, sender);
			break;
		case "feed":
			cmd = new FeedCommand(craft, sender);
			break;
		case "heal":
			cmd = new HealCommand(craft, sender);
			break;
		case "kill":
			cmd = new KillCommand(craft, sender);
			break;
		case "staffchat":
			cmd = new StaffChatCommand(craft, sender);
			break;
		case "sc":
			cmd = new StaffChatCommand(craft, sender);
			break;
		case "spawn":
			cmd = new SpawnCommand(craft, sender);
			break;
		case "sudo":
			cmd = new SudoCommand(craft, sender);
			break;
		case "gamemode":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "gm":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "gms":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "gmc":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "gma":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "gmsp":
			cmd = new GamemodeCommand(craft, sender);
			break;
		case "vanish":
			cmd = new VanishCommand(craft, sender);
			break;
		case "v":
			cmd = new VanishCommand(craft, sender);
			break;
		case "clearinv":
			cmd = new ClearInvCommand(craft, sender);
			break;
		case "ci":
			cmd = new ClearInvCommand(craft, sender);
			break;
		case "give":
			cmd = new GiveCommand(craft, sender);
			break;
		case "item":
			cmd = new GiveCommand(craft, sender);
			break;
		case "i":
			cmd = new GiveCommand(craft, sender);
			break;
		case "invsee":
			cmd = new InvseeCommand(craft, sender);
			break;
		case "getmetadata":
			cmd = new GetMetadataCommand(craft, sender);
			break;
		case "setmetadata":
			cmd = new SetMetadataCommand(craft, sender);
			break;
		case "teleport":
			cmd = new TeleportCommand(craft, sender);
			break;
		case "tp":
			cmd = new TeleportCommand(craft, sender);
			break;
		case "cctp":
			cmd = new TeleportCommand(craft, sender);
			break;
		case "speed":
			cmd = new SpeedCommand(craft, sender);
			break;
		case "listen":
			cmd = new ListenCommand(sender);
			break;
		case "menu":
			cmd = new MenuCommand(sender);
			break;
		case "message":
			cmd = new MessageCommand(craft, sender);
			break;
		case "msg":
			cmd = new MessageCommand(craft, sender);
			break;
		case "tell":
			cmd = new MessageCommand(craft, sender);
			break;
		case "whisper":
			cmd = new MessageCommand(craft, sender);
			break;
		case "w":
			cmd = new MessageCommand(craft, sender);
			break;
		}
		try {
			cmd.run(sender, command, label, args);
			CraftLogger.LogCmd(sender, command, label, args);
		}
		catch (NeAException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.nea"));
			cmd.sendUsage();
			CraftLogger.LogCmd("Not enough arguments", sender, command, label, args);
		}
		catch (TmAException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.tma"));
			cmd.sendUsage();
			CraftLogger.LogCmd("Too many arguments", sender, command, label, args);
		}
		catch (NoPermException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.noperm", cmd.getPermission(), cmd.getName()));
			CraftLogger.LogCmd("No permission", sender, command, label, args);
		}
		catch (NotPlayerException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.notplayer"));
			CraftLogger.LogCmd("Not player", sender, command, label, args);
		}
		catch (PNOException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.playernotonline", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("Player not online", sender, command, label, args);
		}
		catch (PlayerImmuneException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.playerimmune", e.getMessage()));
			CraftLogger.LogCmd("Player immune", sender, command, label, args);
		}
		catch (InvalidItemException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.invaliditem", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("Invalid item", sender, command, label, args);
		}
		catch (StrNotIntException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.strnotint", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("String not Integer", sender, command, label, args);
		}
		catch (InvalidModifierException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.invalidmodifier", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("Invalid modifier", sender, command, label, args);
		}
		catch (NullWorldException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.nullworld", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("Null world", sender, command, label, args);
		}
		catch (InvalidArgumentException e) {
			sender.sendMessage(craft.getMessager().format(PrefixLevel.ERROR, "exception.invalidargument", e.getMessage()));
			cmd.sendUsage();
			CraftLogger.LogCmd("Invalid argument", sender, command, label, args);
		}
		return true;
	}
}