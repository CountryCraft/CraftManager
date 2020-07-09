package io.github.fifcostyle.CraftManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.fifcostyle.CraftManager.commands.CMD;
import io.github.fifcostyle.CraftManager.commands.DebugCommand;
import io.github.fifcostyle.CraftManager.commands.FeedCommand;
import io.github.fifcostyle.CraftManager.commands.FlyCommand;
import io.github.fifcostyle.CraftManager.commands.HealCommand;
import io.github.fifcostyle.CraftManager.commands.KillCommand;
import io.github.fifcostyle.CraftManager.commands.LobbyCommand;
import io.github.fifcostyle.CraftManager.commands.StaffChatCommand;
import io.github.fifcostyle.CraftManager.exceptions.NeAException;
import io.github.fifcostyle.CraftManager.exceptions.NoPermException;
import io.github.fifcostyle.CraftManager.exceptions.NotPlayerException;
import io.github.fifcostyle.CraftManager.exceptions.PNOException;
import io.github.fifcostyle.CraftManager.exceptions.PlayerImmuneException;
import io.github.fifcostyle.CraftManager.exceptions.TmAException;

public class CmdHandler implements CommandExecutor {
	CraftManager craft;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CMD cmd = null;
		switch (command.getName().toLowerCase()) {
		case "fly":
			cmd = new FlyCommand(sender);
			break; 
		case "debug":
			cmd = new DebugCommand(sender);
			break;
		case "feed":
			cmd = new FeedCommand(sender);
			break;
		case "heal":
			cmd = new HealCommand(sender);
			break;
		case "kill":
			cmd = new KillCommand(sender);
			break;
		case "staffchat":
			cmd = new StaffChatCommand(sender);
			break;
		case "sc":
			cmd = new StaffChatCommand(sender);
			break;
		case "lobby":
			cmd = new LobbyCommand(sender);
			break;
		case "l":
			cmd = new LobbyCommand(sender);
			break;
		case "hub":
			cmd = new LobbyCommand(sender);
			break;
		case "h":
			cmd = new LobbyCommand(sender);
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
		return false;
	}
}