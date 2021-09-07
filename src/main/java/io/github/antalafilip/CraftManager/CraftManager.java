package io.github.antalafilip.CraftManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftManager extends JavaPlugin implements Listener {
	public static CraftManager craft;
	private Logger lgr = Bukkit.getLogger();
	private PluginManager pmgr = Bukkit.getPluginManager();
	private Messager messager;
	private Executor executor;
	private CraftGUI cgui;
	private CmdHandler cmdHandler;
	private boolean debugMode;
	private boolean bungeeMode;
	private String serverName;
	private Location lobbyLoc;
	private List<Player> onlinePlayers = new ArrayList<Player>();
	private List<Player> vanishedPlayers = new ArrayList<Player>();
	
	
	public void onEnable() {
		this.saveDefaultConfig();
		craft = this;
		messager = new Messager();
		executor = new Executor(craft);
		cgui = new CraftGUI();
		cmdHandler = new CmdHandler(craft);
		debugMode = getConfig().getBoolean("debug", false);
		bungeeMode = getConfig().getBoolean("bungee", false);
		serverName = getConfig().getString("servername");
		LobbyInit();
		SetCmdExecutors();
		pmgr.registerEvents(executor, this);
		pmgr.registerEvents(cgui, this);
		craft.getServer().getMessenger().registerOutgoingPluginChannel(craft, "BungeeCord");
		craft.getServer().getMessenger().registerIncomingPluginChannel(craft, "BungeeCord", executor);
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
	
	public List<Player> getOnlinePlayers() {
		return this.onlinePlayers;
	}
	
	public List<Player> getVanishedPlayers() {
		return this.vanishedPlayers;
	}
	
	public CmdHandler getCmdHandler() {
		return cmdHandler;
	}
	
	static {
		CraftManager.craft = null;
	}
	
	private void LobbyInit() {
		World w = Bukkit.getWorld(getConfig().getString("spawn.world"));
		Double x = getConfig().getDouble("spawn.x");
		Double y = getConfig().getDouble("spawn.y");
		Double z = getConfig().getDouble("spawn.z");
		Float yaw = Float.parseFloat(getConfig().getString("spawn.yaw")); 
		Float pitch = Float.parseFloat(getConfig().getString("spawn.pitch"));
		lobbyLoc = new Location(w,x,y,z,yaw,pitch);
	}
	private void SetCmdExecutors() {
		this.getCommand("fly").setExecutor(cmdHandler);
		this.getCommand("debug").setExecutor(cmdHandler);
		this.getCommand("kill").setExecutor(cmdHandler);
		this.getCommand("heal").setExecutor(cmdHandler);
		this.getCommand("feed").setExecutor(cmdHandler);
		this.getCommand("staffchat").setExecutor(cmdHandler);
		this.getCommand("sc").setExecutor(cmdHandler);
		this.getCommand("spawn").setExecutor(cmdHandler);
		this.getCommand("sudo").setExecutor(cmdHandler);
		this.getCommand("gamemode").setExecutor(cmdHandler);
		this.getCommand("gm").setExecutor(cmdHandler);
		this.getCommand("gms").setExecutor(cmdHandler);
		this.getCommand("gmc").setExecutor(cmdHandler);
		this.getCommand("gma").setExecutor(cmdHandler);
		this.getCommand("gmsp").setExecutor(cmdHandler);
		this.getCommand("vanish").setExecutor(cmdHandler);
		this.getCommand("v").setExecutor(cmdHandler);
		this.getCommand("clearinv").setExecutor(cmdHandler);
		this.getCommand("ci").setExecutor(cmdHandler);
		this.getCommand("give").setExecutor(cmdHandler);
		this.getCommand("item").setExecutor(cmdHandler);
		this.getCommand("i").setExecutor(cmdHandler);
		this.getCommand("invsee").setExecutor(cmdHandler);
		this.getCommand("getmetadata").setExecutor(cmdHandler);
		this.getCommand("setmetadata").setExecutor(cmdHandler);
		this.getCommand("teleport").setExecutor(cmdHandler);
		this.getCommand("tp").setExecutor(cmdHandler);
		this.getCommand("cctp").setExecutor(cmdHandler);
		this.getCommand("speed").setExecutor(cmdHandler);
		this.getCommand("listen").setExecutor(cmdHandler);
		this.getCommand("menu").setExecutor(cmdHandler);
	}

	public String getServerName() {
		return serverName;
	}

	public boolean getBungeeMode() {
		return bungeeMode;
	}
}
