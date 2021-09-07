package io.github.antalafilip.CraftManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import io.github.antalafilip.CraftManager.enums.CmdListenLevel;
import io.github.antalafilip.CraftManager.enums.PrefixLevel;
import io.github.antalafilip.CraftManager.events.ClearInvEvent;
import io.github.antalafilip.CraftManager.events.GetDebugEvent;
import io.github.antalafilip.CraftManager.events.GetMetadataEvent;
import io.github.antalafilip.CraftManager.events.GetSpeedEvent;
import io.github.antalafilip.CraftManager.events.GiveItemEvent;
import io.github.antalafilip.CraftManager.events.MessageEvent;
import io.github.antalafilip.CraftManager.events.OpenInvEvent;
import io.github.antalafilip.CraftManager.events.SetDebugEvent;
import io.github.antalafilip.CraftManager.events.SetFlyEvent;
import io.github.antalafilip.CraftManager.events.SetFoodEvent;
import io.github.antalafilip.CraftManager.events.SetGamemodeEvent;
import io.github.antalafilip.CraftManager.events.SetHealthEvent;
import io.github.antalafilip.CraftManager.events.SetMetadataEvent;
import io.github.antalafilip.CraftManager.events.SetSpeedEvent;
import io.github.antalafilip.CraftManager.events.StaffChatEvent;
import io.github.antalafilip.CraftManager.events.SudoEvent;
import io.github.antalafilip.CraftManager.events.TeleportEvent;
import io.github.antalafilip.CraftManager.events.ToggleListenEvent;
import io.github.antalafilip.CraftManager.events.VanishEvent;
import io.github.antalafilip.CraftManager.util.MetadataUtils;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Executor implements Listener, PluginMessageListener {
	CraftManager craft;
	Messager msgr;
	public Executor(CraftManager craft) {
		this.craft = CraftManager.craft;
		this.msgr = craft.getMessager();
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		craft.getLogger().info("Received plugin message on channel: " + channel);
		craft.getLogger().info("With data: " + message.toString());
		if (channel.equals("BungeeCord")) {
			onBungeeMessage(player, message);
		};
		
	}
	
	private void onBungeeMessage(Player player, byte[] message) {
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("StaffChat")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String server = msgin.readUTF();
				String senderName = msgin.readUTF();
				String msg = msgin.readUTF();
				Bukkit.broadcast(msgr.format(PrefixLevel.STAFF, "staffchat", server, senderName, msg), "empiremc.staffchat.receive");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (subchannel.equals("StaffJoin")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String server = msgin.readUTF();
				String playerName = msgin.readUTF();
				Bukkit.broadcast(msgr.format(PrefixLevel.STAFF, "staffjoin", server, playerName), "empiremc.staffchat.receive");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (subchannel.equals("StaffLeave")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);
			
			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String server = msgin.readUTF();
				String playerName = msgin.readUTF();
				Bukkit.broadcast(msgr.format(PrefixLevel.STAFF, "staffleave", server, playerName), "empiremc.staffchat.receive");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'PlayerJoinEvent'");
		e.setJoinMessage(null);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasMetadata("vanished") && !e.getPlayer().hasPermission("countrycraft.vanish.see")) e.getPlayer().hidePlayer(craft, player);
		}
		craft.getOnlinePlayers().add(e.getPlayer());
		if (e.getPlayer().hasPermission("countrycraft.listen.command")) {
			if (!MetadataUtils.has(e.getPlayer(), "listen.command")) {
				MetadataUtils.set(e.getPlayer(), "listen.command", 0);
			}
		}
		
		if (!e.getPlayer().hasPermission("empiremc.staff")) return;
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(craft, new Runnable() {
			@Override
			public void run() {
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				out.writeUTF("StaffJoin");
				ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
				DataOutputStream msgout = new DataOutputStream(msgbytes);
				
				try {
					msgout.writeUTF(craft.getServerName());
					msgout.writeUTF(e.getPlayer().getDisplayName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				out.writeShort(msgbytes.toByteArray().length);
				out.write(msgbytes.toByteArray());
				
				e.getPlayer().sendPluginMessage(craft, "BungeeCord", out.toByteArray());
			}
		}, 2L);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerLeave(final PlayerQuitEvent e) {
		if (craft.getDebugMode()) craft.getLogger().info("Caught 'PlayerQuitEvent'");
		e.setQuitMessage(null);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!e.getPlayer().canSee(player)) e.getPlayer().showPlayer(craft, player);
		}
		if (e.getPlayer().hasMetadata("vanished")) Bukkit.getPluginManager().callEvent(new VanishEvent(e.getPlayer(), false));
		craft.getOnlinePlayers().remove(e.getPlayer());
		
		if (!e.getPlayer().hasPermission("empiremc.staff")) return;
		
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("StaffLeave");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		
		try {
			msgout.writeUTF(craft.getServerName());
			msgout.writeUTF(e.getPlayer().getDisplayName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		
		e.getPlayer().sendPluginMessage(craft, "BungeeCord", out.toByteArray());
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
		Bukkit.broadcast(msgr.format(PrefixLevel.STAFF, "staffchat", craft.getServerName(), e.getSender().getName(), e.getMessage()), "countrycraft.staffchat.receive");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("StaffChat");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(craft.getServerName());
			msgout.writeUTF(e.getSender().getName());
			msgout.writeUTF(e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		if (e.getSender() instanceof Player) {
			((Player) e.getSender()).sendPluginMessage(craft, "BungeeCord", out.toByteArray());
		}
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
		e.getTarget().sendMessage(msgr.format("openinventory.player", e.getInv().toString()));
		CraftLogger.LogCmd(e.getSender(), msgr.format("openinventory.broadcast", e.getTarget().getName(), e.getInv().toString()));
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
			e.getTarget().setCollidable(false);
			craft.getOnlinePlayers().remove(e.getTarget());
			craft.getVanishedPlayers().add(e.getTarget());
			MetadataUtils.set(e.getTarget(), "vanished", true);
		}
		else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.canSee(e.getTarget())) player.showPlayer(CraftManager.craft, e.getTarget());
			}
			e.getTarget().setCollidable(true);
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
	
	@EventHandler
	public void onPlayerMessage(MessageEvent e) {
		ComponentBuilder bld = new ComponentBuilder();
		bld.append("Message from ").append(e.getSender().getName()).append(": ").append(e.getMessage());
		e.getRecipient().spigot().sendMessage(bld.create());
	}
	
	
	
}
