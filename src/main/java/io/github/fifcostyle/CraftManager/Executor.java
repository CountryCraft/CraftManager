package io.github.fifcostyle.CraftManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;

import io.github.fifcostyle.CraftManager.events.ClearInvEvent;
import io.github.fifcostyle.CraftManager.events.GetDebugEvent;
import io.github.fifcostyle.CraftManager.events.SetDebugEvent;
import io.github.fifcostyle.CraftManager.events.SetFlyEvent;
import io.github.fifcostyle.CraftManager.events.SetFoodEvent;
import io.github.fifcostyle.CraftManager.events.SetHealthEvent;
import io.github.fifcostyle.CraftManager.events.StaffChatEvent;
import io.github.fifcostyle.CraftManager.events.TeleportEvent;

public class Executor implements Listener {
	CraftManager craft;
	public Executor()
	{
		Bukkit.getPluginManager().registerEvents(this, craft);
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
	
	
}
