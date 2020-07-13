package io.github.fifcostyle.CraftManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.fifcostyle.CraftManager.commands.CMD;
import io.github.fifcostyle.CraftManager.commands.DebugCommand;
import io.github.fifcostyle.CraftManager.commands.FeedCommand;
import io.github.fifcostyle.CraftManager.commands.FlyCommand;
import io.github.fifcostyle.CraftManager.commands.GamemodeCommand;
import io.github.fifcostyle.CraftManager.commands.HealCommand;
import io.github.fifcostyle.CraftManager.commands.KillCommand;
import io.github.fifcostyle.CraftManager.commands.LobbyCommand;
import io.github.fifcostyle.CraftManager.commands.StaffChatCommand;
import io.github.fifcostyle.CraftManager.commands.SudoCommand;
import io.github.fifcostyle.CraftManager.exceptions.InvalidItemException;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
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
		}
		try {
			cmd.run(sender, command, label, args);
		}
		catch (TmAException e) {
			sender.sendMessage(craft.getMessager().format("exception.tma"));
		}
		catch (NeAException e) {
			sender.sendMessage(craft.getMessager().format("exception.nea"));
		}
		catch (NoPermException e) {
			sender.sendMessage(craft.getMessager().format("exception.noperm", cmd.getPermission(), cmd.getName()));
		}
		catch (NotPlayerException e) {
			sender.sendMessage(craft.getMessager().format("exception.notplayer"));
		}
		catch (PNOException e) {
			sender.sendMessage(craft.getMessager().format("exception.playernotonline", e.getMessage()));
		}
		catch (PlayerImmuneException e) {
			sender.sendMessage(craft.getMessager().format("exception.playerimmune", e.getMessage()));
		}
		catch (InvalidItemException e) {
			sender.sendMessage(craft.getMessager().format("exception.invaliditem", e.getMessage()));
		}
		catch (StrNotIntException e) {
			sender.sendMessage(craft.getMessager().format("exception.strnotint", e.getMessage()));
		}
		return true;
	}
}