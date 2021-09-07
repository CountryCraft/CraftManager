package io.github.antalafilip.CraftManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import io.github.antalafilip.CraftManager.events.gui.GUIOpenMainEvent;

public class CraftGUI implements Listener {
	
	private CraftManager craft = CraftManager.craft;
	// Inventories
	private Inventory mainGUI;
	private Inventory staffGUI;
	private Inventory serverSelector;
	
	// Main GUI Items
	
	// Staff GUI Items
	private ItemStack vanishChanger;
	
	// ServerSelector Items
	private ItemStack staffServer;
	
	public CraftGUI() {
		ReloadGUI();
	}

	private void ReloadGUI() {
		LoadMainGUI();
		LoadServerSelector();
	}
	
	private void LoadServerSelector() {
		serverSelector = Bukkit.createInventory(null, 54, "Servers:");
		
		// Staff Server
		staffServer = new ItemStack(Material.EMERALD); 
		ItemMeta staffServerMeta = staffServer.getItemMeta(); 
		staffServerMeta.setDisplayName("Go to the STAFF server"); 
		staffServer.setItemMeta(staffServerMeta);
		
		// Set the items in the inventory
		serverSelector.setItem(11, staffServer);
	}
	
	private void LoadMainGUI() {
		mainGUI = Bukkit.createInventory(null, 54, "CraftManager GUI");
		ItemStack gmMenu = new ItemStack(Material.DIAMOND_PICKAXE); ItemMeta gmMenuMeta = gmMenu.getItemMeta(); gmMenuMeta.setDisplayName("Gamemode menu"); gmMenu.setItemMeta(gmMenuMeta);
		mainGUI.setItem(11, gmMenu);
		//ItemStack flyItem = new ItemStack(Material.FEATHER); ItemMeta flyItemMeta = flyItem.getItemMeta(); flyItemMeta.setDisplayName("Toggle fly"); 
		ItemStack playerMenu = new ItemStack(Material.SKELETON_SKULL, 1); SkullMeta playerMenuMeta = (SkullMeta) playerMenu.getItemMeta(); playerMenuMeta.setDisplayName("Players"); playerMenu.setItemMeta(playerMenuMeta);
		mainGUI.setItem(17, playerMenu);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void OpenMenu(GUIOpenMainEvent e) {
		ReloadGUI();
		e.getTarget().openInventory(mainGUI);
		e.getTarget().sendMessage(craft.getMessager().format("gui.open"));
	}
	
	@EventHandler
	public void onPlayerCompassUse(PlayerInteractEvent e) {
		if (!e.hasItem()) return;
		if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!e.getItem().getType().equals(Material.COMPASS)) return;
		
		e.getPlayer().openInventory(serverSelector);
	}
	
	@EventHandler
	public void onPlayerClickItem(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player == false) return;
		if (!e.getInventory().equals(mainGUI) && !e.getInventory().equals(serverSelector) && !e.getInventory().equals(staffGUI)) return;
		
		Player whoClicked = (Player) e.getWhoClicked();
		e.setCancelled(true);
		
		if (e.getCurrentItem().equals(staffServer)) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("staff");
			whoClicked.sendPluginMessage(craft, "BungeeCord", out.toByteArray());
			craft.getLogger().info("sending");
		}
	}
}
