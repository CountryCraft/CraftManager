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
		executor = new Executor(craft);
		craft = this;
		debugMode = false;
		LobbyInit();
		this.getCommand("fly").setExecutor(new CmdHandler(craft));
		this.getCommand("debug").setExecutor(new CmdHandler(craft));
		this.getCommand("kill").setExecutor(new CmdHandler(craft));
		this.getCommand("heal").setExecutor(new CmdHandler(craft));
		this.getCommand("feed").setExecutor(new CmdHandler(craft));
		this.getCommand("staffchat").setExecutor(new CmdHandler(craft));
		this.getCommand("sc").setExecutor(new CmdHandler(craft));
		this.getCommand("lobby").setExecutor(new CmdHandler(craft));
		this.getCommand("l").setExecutor(new CmdHandler(craft));
		this.getCommand("hub").setExecutor(new CmdHandler(craft));
		this.getCommand("h").setExecutor(new CmdHandler(craft));
		this.getCommand("sudo").setExecutor(new CmdHandler(craft));
		this.getCommand("gamemode").setExecutor(new CmdHandler(craft));
		this.getCommand("gm").setExecutor(new CmdHandler(craft));
		this.getCommand("gms").setExecutor(new CmdHandler(craft));
		this.getCommand("gmc").setExecutor(new CmdHandler(craft));
		this.getCommand("gma").setExecutor(new CmdHandler(craft));
		this.getCommand("gmsp").setExecutor(new CmdHandler(craft));
		this.getCommand("vanish").setExecutor(new CmdHandler(craft));
		this.getCommand("v").setExecutor(new CmdHandler(craft));
		this.getCommand("clear").setExecutor(new CmdHandler(craft));
		this.getCommand("ci").setExecutor(new CmdHandler(craft));
		this.getCommand("give").setExecutor(new CmdHandler(craft));
		this.getCommand("item").setExecutor(new CmdHandler(craft));
		this.getCommand("i").setExecutor(new CmdHandler(craft));
		this.getCommand("invsee").setExecutor(new CmdHandler(craft));
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
