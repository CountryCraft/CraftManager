package io.github.fifcostyle.CraftManager;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.fifcostyle.CraftManager.commands.CMD;
import io.github.fifcostyle.CraftManager.commands.ClearInvCommand;
import io.github.fifcostyle.CraftManager.commands.DebugCommand;
import io.github.fifcostyle.CraftManager.commands.FeedCommand;
import io.github.fifcostyle.CraftManager.commands.FlyCommand;
import io.github.fifcostyle.CraftManager.commands.GamemodeCommand;
import io.github.fifcostyle.CraftManager.commands.GetMetadataCommand;
import io.github.fifcostyle.CraftManager.commands.GiveCommand;
import io.github.fifcostyle.CraftManager.commands.HealCommand;
import io.github.fifcostyle.CraftManager.commands.InvseeCommand;
import io.github.fifcostyle.CraftManager.commands.KillCommand;
import io.github.fifcostyle.CraftManager.commands.ListenCommand;
import io.github.fifcostyle.CraftManager.commands.LobbyCommand;
import io.github.fifcostyle.CraftManager.commands.SetMetadataCommand;
import io.github.fifcostyle.CraftManager.commands.SpeedCommand;
import io.github.fifcostyle.CraftManager.commands.StaffChatCommand;
import io.github.fifcostyle.CraftManager.commands.SudoCommand;
import io.github.fifcostyle.CraftManager.commands.TeleportCommand;
import io.github.fifcostyle.CraftManager.commands.VanishCommand;
import io.github.fifcostyle.CraftManager.exceptions.InvalidItemException;
import io.github.fifcostyle.CraftManager.exceptions.InvalidModifierException;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.NullWorldException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.PlayerImmuneException;
import io.github.fifcostyle.CraftManager.exceptions.StrNotIntException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

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
		case "lobby":
			cmd = new LobbyCommand(craft, sender);
			break;
		case "l":
			cmd = new LobbyCommand(craft, sender);
			break;
		case "hub":
			cmd = new LobbyCommand(craft, sender);
			break;
		case "h":
			cmd = new LobbyCommand(craft, sender);
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
		}
		try {
			cmd.run(sender, command, label, args);
		}
		catch (TmAException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.tma"));
			cmd.sendUsage();
		}
		catch (NeAException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.nea"));
			cmd.sendUsage();
		}
		catch (NoPermException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.noperm", cmd.getPermission(), cmd.getName()));
		}
		catch (NotPlayerException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.notplayer"));
		}
		catch (PNOException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.playernotonline", e.getMessage()));
			cmd.sendUsage();
		}
		catch (PlayerImmuneException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.playerimmune", e.getMessage()));
		}
		catch (InvalidItemException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.invaliditem", e.getMessage()));
			cmd.sendUsage();
		}
		catch (StrNotIntException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.strnotint", e.getMessage()));
			cmd.sendUsage();
		}
		catch (InvalidModifierException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.invalidmodifier", e.getMessage()));
			cmd.sendUsage();
		}
		catch (NullWorldException e) {
			sender.sendMessage(craft.getMessager().format(Level.SEVERE, "exception.nullworld", e.getMessage()));
			cmd.sendUsage();
		}
		return true;
	}
}