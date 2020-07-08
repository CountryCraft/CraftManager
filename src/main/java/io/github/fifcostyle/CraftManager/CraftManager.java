package io.github.fifcostyle.CraftManager;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftManager extends JavaPlugin implements Listener {
	private static CraftManager craft;
	private Logger lgr = Bukkit.getLogger();
	private Messager messager;
	private Executor executor;
	private boolean debugMode;
	private Location lobbyLoc;
	
	public CraftManager() {
		this.messager = null;
	}
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		messager = new Messager();
		executor = new Executor();
		craft = this;
		debugMode = false;
		LobbyInit();
		this.getCommand("fly").setExecutor(new CmdHandler());
		this.getCommand("debug").setExecutor(new CmdHandler());
		this.getCommand("kill").setExecutor(new CmdHandler());
		this.getCommand("heal").setExecutor(new CmdHandler());
		this.getCommand("feed").setExecutor(new CmdHandler());
		this.getCommand("staffchat").setExecutor(new CmdHandler());
		this.getCommand("sc").setExecutor(new CmdHandler());
		lgr.info("CraftManager has been enabled!");
	}
	
	public void onDisable() {
		craft = null;
		messager = null;
		lgr.info("CraftManager has been disabled");
	}
	
	public Messager getMessager() {
		return this.messager;
	}
	
	public Executor getExecutor() {
		return this.executor;
	}
	
	public CraftManager getPlugin() {
		return CraftManager.craft;
	}
	
	public boolean getDebugMode() {
		return this.debugMode;
	}
	
	public void setDebugMode(boolean state) {
		this.debugMode = state;
	}
	
	public Location getLobbyLocation() {
		return this.lobbyLoc;
	}
	
	static {
		CraftManager.craft = null;
	}
	
	private void LobbyInit() {
		World w = Bukkit.getWorld(getConfig().getString("lobby.world"));
		Double x = getConfig().getDouble("lobby.x");
		Double y = getConfig().getDouble("lobby.y");
		Double z = getConfig().getDouble("lobby.z");
		Float yaw = (Float) getConfig().get("lobby.yaw");
		Float pitch = (Float) getConfig().get("lobby.pitch");
		lobbyLoc = new Location(w,x,y,z,yaw,pitch);
	}
	
	@EventHandler
	public void onCommandPreprocess(AsyncPlayerChatEvent evt)
	{
		if (evt.getMessage().startsWith("/")) {
			lgr.info("Command " + evt.getMessage() + " was sent");
		}
	}
}
