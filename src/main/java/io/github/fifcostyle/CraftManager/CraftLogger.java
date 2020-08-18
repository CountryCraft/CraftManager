package io.github.fifcostyle.CraftManager;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.fifcostyle.CraftManager.enums.PrefixLevel;
import io.github.fifcostyle.CraftManager.util.MetadataUtils;
import io.github.fifcostyle.CraftManager.util.StringUtils;

public class CraftLogger {
	private static CraftManager craft = CraftManager.craft;
	
	public static void LogCmd(CommandSender sender, String output) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getName().equalsIgnoreCase(sender.getName())) {
				if (MetadataUtils.has(player, "listen.command")) {
					int val = MetadataUtils.get(player, "listen.command").get(0).asInt();
					if (val >= 1) player.sendMessage(output);
				}
			}
		}
	}
	public static void LogCmd(CommandSender sender, Command command, String label, String[] args) {
		CraftLogger.Log("CMD-SUCCESS: " + sender.getName() + " " + command.getName() + " " + StringUtils.stringArrayToString(args, 0));
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getName().equalsIgnoreCase(sender.getName())) {
				if (MetadataUtils.has(player, "listen.command")) {
					int val = MetadataUtils.get(player, "listen.command").get(0).asInt();
					if (val >= 2) player.sendMessage(craft.getMessager().format(PrefixLevel.STAFF, "listen.command.success", sender.getName(), command.getName(), StringUtils.stringArrayToString(args, 0)));
				}
			}
		}
	}
	public static void LogCmd(String failed, CommandSender sender, Command command, String label, String[] args) {
		CraftLogger.Log("CMD-FAIL: " + sender.getName() + " " + command.getName() + " " + StringUtils.stringArrayToString(args, 0) + " " + failed);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.getName().equalsIgnoreCase(sender.getName())) {
				if (MetadataUtils.has(player, "listen.command")) {
					int val = MetadataUtils.get(player, "listen.command").get(0).asInt();
					if (val >= 2) player.sendMessage(craft.getMessager().format(PrefixLevel.STAFF, "listen.command.failed", sender.getName(), command.getName(), StringUtils.stringArrayToString(args, 0), failed));
				}
			}
		}
	}
	
	public static void Log(String message) {
		craft.getLogger().log(Level.INFO, craft.getMessager().prefix(message));
	}
	public static void Log(Level level, String message) {
		craft.getLogger().log(level, craft.getMessager().prefix(message));
	}
}
