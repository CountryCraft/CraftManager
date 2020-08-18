package io.github.fifcostyle.CraftManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.fifcostyle.CraftManager.enums.CmdListenLevel;
import io.github.fifcostyle.CraftManager.enums.PrefixLevel;
import io.github.fifcostyle.CraftManager.events.ClearInvEvent;
import io.github.fifcostyle.CraftManager.events.GetDebugEvent;
import io.github.fifcostyle.CraftManager.events.GetMetadataEvent;
import io.github.fifcostyle.CraftManager.events.GetSpeedEvent;
import io.github.fifcostyle.CraftManager.events.GiveItemEvent;
import io.github.fifcostyle.CraftManager.events.OpenInvEvent;
import io.github.fifcostyle.CraftManager.events.SetDebugEvent;
import io.github.fifcostyle.CraftManager.events.SetFlyEvent;
import io.github.fifcostyle.CraftManager.events.SetFoodEvent;
import io.github.fifcostyle.CraftManager.events.SetGamemodeEvent;
import io.github.fifcostyle.CraftManager.events.SetHealthEvent;
import io.github.fifcostyle.CraftManager.events.SetMetadataEvent;
import io.github.fifcostyle.CraftManager.events.SetSpeedEvent;
import io.github.fifcostyle.CraftManager.events.StaffChatEvent;
import io.github.fifcostyle.CraftManager.events.SudoEvent;
import io.github.fifcostyle.CraftManager.events.TeleportEvent;
import io.github.fifcostyle.CraftManager.events.ToggleListenEvent;
import io.github.fifcostyle.CraftManager.events.VanishEvent;
import io.github.fifcostyle.CraftManager.util.MetadataUtils;

public class Executor implements Listener {
	CraftManager craft;
	Messager msgr;
	public Executor(CraftManager craft) {
		this.craft = CraftManager.craft;
		this.msgr = craft.getMessager();
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'PlayerJoinEvent'");
		e.setJoinMessage("");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasMetadata("vanished") && !e.getPlayer().hasPermission("countrycraft.vanish.see")) e.getPlayer().hidePlayer(craft, player);
		}
		craft.getOnlinePlayers().add(e.getPlayer());
		if (e.getPlayer().hasPermission("countrycraft.listen.command")) {
			if (!MetadataUtils.has(e.getPlayer(), "listen.command")) {
				MetadataUtils.set(e.getPlayer(), "listen.command", 2);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'PlayerQuitEvent'");
		e.setQuitMessage("");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!e.getPlayer().canSee(player)) e.getPlayer().showPlayer(craft, player);
		}
		if (e.getPlayer().hasMetadata("vanished")) Bukkit.getPluginManager().callEvent(new VanishEvent(e.getPlayer(), false));
		craft.getOnlinePlayers().remove(e.getPlayer());
	}
	
	
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetFly(SetFlyEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SetFlyEvent'");
		e.getTarget().setAllowFlight(e.getState());
		e.getTarget().sendMessage(msgr.format("fly.player", e.getState()));
		e.getSender().sendMessage(msgr.format("fly.sender", e.getTarget().getName(), e.getState()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("fly.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getState()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetHealth(SetHealthEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SetHealthEvent'");
		e.getTarget().setHealth(e.getHealth());
		e.getTarget().sendMessage(msgr.format("sethealth.player", e.getHealth()));
		e.getSender().sendMessage(msgr.format("sethealth.sender", e.getTarget().getName(), e.getHealth()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("sethealth.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getHealth()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetFood(SetFoodEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SetFoodEvent'");
		e.getTarget().setFoodLevel(e.getFood());
		e.getTarget().sendMessage(msgr.format("setfood.player", e.getFood()));
		e.getSender().sendMessage(msgr.format("setfood.sender", e.getTarget().getName(), e.getFood()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("setfood.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getFood()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetDebugMode(SetDebugEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SetDebugEvent'");
		craft.setDebugMode(e.getState());
		e.getSender().sendMessage(msgr.format("debugmode.set.sender", e.getState()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("debugmode.set.broadcast", e.getSender().getName(), e.getState()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void GetDebugMode(GetDebugEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'GetDebugEvent'");
		e.getSender().sendMessage(msgr.format("debugmode.get", craft.getDebugMode()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onStaffChatMessageSent(StaffChatEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'StaffChatEvent'");
		Bukkit.broadcast(msgr.format(PrefixLevel.STAFF, "staffchat", e.getSender().getName(), e.getMessage()), "countrycraft.staffchat.receive");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void ClearInv(ClearInvEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'ClearInvEvent'");
		e.getTarget().getInventory().clear();
		e.getTarget().sendMessage(msgr.format("clearinv.player"));
		e.getSender().sendMessage(msgr.format("clearinv.sender", e.getTarget().getName()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("clearinv.broadcast", e.getSender().getName(), e.getTarget().getName()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void Teleport(TeleportEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'TeleportEvent'");
		if (e.isUnsafe() && !e.getOverride()) {
			e.getSender().sendMessage(msgr.format(PrefixLevel.WARNING, "teleport.unsafe"));
		}
		else {
			switch (e.getMode()) {
			case "PlayerToPlayer":
				e.getToTp().teleport(e.getTpTo());
				e.getToTp().sendMessage(msgr.format("teleport.player.generic", e.getTpTo().getName()));
				e.getSender().sendMessage(msgr.format("teleport.sender.generic", e.getToTp().getName(), e.getTpTo().getName()));
				CraftLogger.LogCmd(e.getSender(), msgr.format("teleport.broadcast.generic", e.getSender().getName(), e.getToTp().getName(), e.getTpTo().getName()));
				break;
			case "PlayerToLoc":
				e.getToTp().teleport(e.getTpLoc());
				String loc = new String(e.getTpLoc().getWorld().getName() + " " + e.getTpLoc().getX() + " " + e.getTpLoc().getY() + " " + e.getTpLoc().getZ());
				e.getToTp().sendMessage(msgr.format("teleport.player.generic", loc));
				e.getSender().sendMessage(msgr.format("teleport.sender.generic", e.getToTp().getName(), loc));
				CraftLogger.LogCmd(e.getSender(), msgr.format("teleport.broadcast.generic", e.getSender().getName(), e.getToTp().getName(), loc));
				break;
			case "PlayerToLobby":
				e.getToTp().teleport(craft.getLobbyLocation());
				e.getToTp().sendMessage(msgr.format("teleport.player.lobby"));
				e.getSender().sendMessage(msgr.format("teleport.sender.lobby", e.getToTp().getName()));
				CraftLogger.LogCmd(e.getSender(), msgr.format("teleport.broadcast.lobby", e.getSender().getName(), e.getToTp().getName()));
				break;
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void Sudo(SudoEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SudoEvent'");
		e.getTarget().performCommand(e.getCommand());
		e.getSender().sendMessage(msgr.prefix("Ran command " + e.getCommand() + "as target user"));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetGamemode(SetGamemodeEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'SetGamemodeEvent'");
		e.getTarget().setGameMode(e.getGM());
		e.getTarget().sendMessage(msgr.format("gamemode.set.player", e.getGM().toString()));
		e.getSender().sendMessage(msgr.format("gamemode.set.sender", e.getTarget().getName(), e.getGM().toString()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("gamemode.set.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getGM().toString()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void OpenInventory(OpenInvEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'OpenInvEvent'");
		e.getTarget().openInventory(e.getInv());
		e.getTarget().sendMessage(msgr.format("openinventory.player", e.getInv().getName()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("openinventory.broadcast", e.getTarget().getName(), e.getInv().getName()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void GiveItem(GiveItemEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'GiveItemEvent'");
		e.getTarget().getInventory().addItem(e.getItem());
		e.getTarget().sendMessage(msgr.format("giveitem.player", e.getItem().getType().toString(), e.getItem().getAmount()));
		e.getSender().sendMessage(msgr.format("giveitem.sender", e.getTarget().getName(), e.getItem().getType().toString(), e.getItem().getAmount()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("giveitem.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getItem().getType().toString(), e.getItem().getAmount()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void Vanish(VanishEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'VanishEvent'");
		if (e.getState()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.hasPermission("countrycraft.vanish.see")) {
					if (player.getName() != e.getSender().getName()) player.hidePlayer(CraftManager.craft, e.getTarget());
				}
			}
			craft.getOnlinePlayers().remove(e.getTarget());
			craft.getVanishedPlayers().add(e.getTarget());
			MetadataUtils.set(e.getTarget(), "vanished", true);
		}
		else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.canSee(e.getTarget())) player.showPlayer(CraftManager.craft, e.getTarget());
			}
			craft.getVanishedPlayers().remove(e.getTarget());
			craft.getOnlinePlayers().add(e.getTarget());
			MetadataUtils.rem(e.getTarget(), "vanished");
		}
		e.getTarget().sendMessage(msgr.format("vanish.player", e.getState()));
		e.getSender().sendMessage(msgr.format("vanish.sender", e.getTarget().getName(), e.getState()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("vanish.broadcast", e.getSender().getName(), e.getTarget().getName(), e.getState()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void GetMetadata(GetMetadataEvent e) {
		e.getSender().sendMessage(msgr.format("metadata.get", e.getTarget().getName(), e.getKey(), MetadataUtils.get(e.getTarget(), e.getKey()).toString()));
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetMetadata(SetMetadataEvent e) {
		MetadataUtils.set(e.getTarget(), e.getKey(), e.getValue());
		e.getSender().sendMessage(msgr.format("metadata.set", e.getTarget().getName(), e.getKey(), e.getValue()));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void GetSpeed(GetSpeedEvent e) {
		if (e.getFlying()) {
			e.getSender().sendMessage(msgr.format("speed.get", e.getTarget().getName(), "FLY", e.getTarget().getFlySpeed()*10));
		} else {
			e.getSender().sendMessage(msgr.format("speed.get", e.getTarget().getName(), "WALK", e.getTarget().getWalkSpeed()*10));
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void SetSpeed(SetSpeedEvent e) {
		if (e.getFlying()) {
			e.getTarget().setFlySpeed(e.getSpeed());
			e.getTarget().sendMessage(msgr.format("speed.set.player", "FLY", e.getSpeed()*10));
			e.getSender().sendMessage(msgr.format("speed.set.sender", e.getTarget().getName(), "FLY", e.getSpeed()*10));
			CraftLogger.LogCmd(e.getSender(), msgr.format("speed.set.broadcast", e.getSender().getName(), e.getTarget().getName(), "FLY", e.getSpeed()*10));
		} else {
			e.getTarget().setWalkSpeed(e.getSpeed());
			e.getTarget().sendMessage(msgr.format("speed.set.player", "WALK", e.getSpeed()*10));
			e.getSender().sendMessage(msgr.format("speed.set.sender", e.getTarget().getName(), "WALK", e.getSpeed()*10));
			CraftLogger.LogCmd(e.getSender(), msgr.format("speed.set.broadcast", e.getSender().getName(), e.getTarget().getName(), "WALK", e.getSpeed()*10));
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void ToggleListen(ToggleListenEvent e) {
		if (e.getLevel() == CmdListenLevel.OFF) {
			MetadataUtils.rem(e.getTarget(), "listen.command");
			MetadataUtils.set(e.getTarget(), "listen.command", 0);
		} else if (e.getLevel() == CmdListenLevel.MINIMAL) {
			MetadataUtils.rem(e.getTarget(), "listen.command");
			MetadataUtils.set(e.getTarget(), "listen.command", 1);
		} else if (e.getLevel() == CmdListenLevel.ALL) {
			MetadataUtils.rem(e.getTarget(), "listen.command");
			MetadataUtils.set(e.getTarget(), "listen.command", 2);
		}
		e.getTarget().sendMessage(msgr.format("listen.command.set", "CMD", e.getLevel().toString()));
	}
	
	
	
}
