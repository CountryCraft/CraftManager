package io.github.fifcostyle.CraftManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;

import io.github.fifcostyle.CraftManager.events.ClearInvEvent;
import io.github.fifcostyle.CraftManager.events.GetDebugEvent;
import io.github.fifcostyle.CraftManager.events.GiveItemEvent;
import io.github.fifcostyle.CraftManager.events.OpenInvEvent;
import io.github.fifcostyle.CraftManager.events.SetDebugEvent;
import io.github.fifcostyle.CraftManager.events.SetFlyEvent;
import io.github.fifcostyle.CraftManager.events.SetFoodEvent;
import io.github.fifcostyle.CraftManager.events.SetGamemodeEvent;
import io.github.fifcostyle.CraftManager.events.SetHealthEvent;
import io.github.fifcostyle.CraftManager.events.StaffChatEvent;
import io.github.fifcostyle.CraftManager.events.SudoEvent;
import io.github.fifcostyle.CraftManager.events.TeleportEvent;
import io.github.fifcostyle.CraftManager.events.VanishEvent;

public class Executor implements Listener {
	CraftManager craft;
	public Executor(CraftManager craft) {
		this.craft = craft;
		Bukkit.getPluginManager().registerEvents(this, craft);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasMetadata("vanished")) e.getPlayer().hidePlayer(craft, player);
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.setQuitMessage("");
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!e.getPlayer().canSee(player)) e.getPlayer().showPlayer(craft, player);
		}
		if (e.getPlayer().hasMetadata("vanished")) Bukkit.getPluginManager().callEvent(new VanishEvent(e.getPlayer(), false));
	}
	
	@EventHandler
	public void SetFly(SetFlyEvent e) {
		e.getTarget().setAllowFlight(e.getState());
		e.getTarget().sendMessage(craft.getMessager().format("fly.player." + e.getState().toString()));
		//add broadcast
	}
	
	@EventHandler
	public void SetHealth(SetHealthEvent e) {
		e.getTarget().setHealth(e.getHealth());
		e.getTarget().sendMessage(craft.getMessager().format("sethealth.player", e.getHealth()));
		//add broadcast
	}
	
	@EventHandler
	public void SetFood(SetFoodEvent e) {
		e.getTarget().setFoodLevel(e.getFood());
		e.getTarget().sendMessage(craft.getMessager().format("setfood.player", e.getFood()));
		//add broadcast
	}
	
	@EventHandler
	public void SetDebugMode(SetDebugEvent e) {
		craft.setDebugMode(e.getState());
		e.getSender().sendMessage(craft.getMessager().format("debugmode.set.player", e.getState()));
		//add broadcast
	}
	
	@EventHandler
	public void GetDebugMode(GetDebugEvent e) {
		e.getSender().sendMessage(craft.getMessager().format("debugmode.get", craft.getDebugMode()));
	}
	
	@EventHandler
	public void onStaffChatMessageSent(StaffChatEvent e) {
		Bukkit.broadcast(craft.getMessager().format("staffchat", e.getSender(), e.getMessage()), "countrycraft.staffchat.receive");
	}
	
	@EventHandler
	public void ClearInv(ClearInvEvent e) {
		PlayerInventory inv = e.getTarget().getInventory();
		inv.clear();
		e.getTarget().sendMessage(craft.getMessager().format("clearinv.player"));
		//add broadcast
	}
	
	@EventHandler
	public void Teleport(TeleportEvent e) {
		switch (e.getMode()) {
		case "PlayerToPlayer":
			e.getToTp().teleport(e.getTpTo());
			e.getToTp().sendMessage(craft.getMessager().format("teleport.player", e.getTpTo().getName()));
			//add broadcast
			break;
		case "PlayerToLoc":
			e.getToTp().teleport(e.getTpLoc());
			String loc = new String(e.getTpLoc().getWorld().getName() + " " + e.getTpLoc().getX() + " " + e.getTpLoc().getY() + " " + e.getTpLoc().getZ());
			e.getToTp().sendMessage(craft.getMessager().format("teleport.player", loc));
			//add broadcast
			break;
		case "PlayerToLobby":
			e.getToTp().teleport(craft.getLobbyLocation());
			e.getToTp().sendMessage(craft.getMessager().format("teleport.player.lobby"));
			//add broadcast
			break;
		}
	}
	
	@EventHandler
	public void Sudo(SudoEvent e) {
		e.getTarget().performCommand(e.getCommand());
		e.getSender().sendMessage(craft.getMessager().prefix(""));
	}
	
	@EventHandler
	public void SetGamemode(SetGamemodeEvent e) {
		e.getTarget().setGameMode(e.getGM());
		e.getTarget().sendMessage(craft.getMessager().format("gamemode.set.player", e.getGM().toString()));
		//add broadcast
	}
	
	@EventHandler
	public void OpenInventory(OpenInvEvent e) {
		e.getTarget().openInventory(e.getInv());
		e.getTarget().sendMessage(craft.getMessager().format("openinventory.player", e.getInv().getName()));
		//add broadcast
	}
	
	@EventHandler
	public void GiveItem(GiveItemEvent e) {
		e.getTarget().getInventory().addItem(e.getItem());
		e.getTarget().sendMessage(craft.getMessager().format("giveitem.player", e.getItem().getType().toString(), e.getItem().getAmount()));
		//add broadcast
	}
	
	@EventHandler
	public void Vanish(VanishEvent e) {
		if (e.getState()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.hasPermission("countrycraft.vanish.see")) player.hidePlayer(craft, e.getTarget());
			}
		}
		else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.canSee(e.getTarget())) player.showPlayer(craft, e.getTarget());
			}
		}
	}
	
	
}
