package io.github.fifcostyle.CraftManager;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messager {
	private FileConfiguration config;
	public Messager() {
		this.config = null;
		this.config = (FileConfiguration)YamlConfiguration.loadConfiguration((Reader)new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("messages.yml")));
	}
	public String get(final String key) {
		return this.config.getString(key);
	}
	
	public String format(final String key, final Object... args) {
		return this.format(Level.INFO, key, args);
	}
	
	public String format(Level level, final String key, final Object... args) {
		String message = "";
		if (level == Level.INFO) {
			message = (this.get("prefixInfo") + this.get(key));
		} else if (level == Level.WARNING) {
			message = (this.get("prefixWarn") + this.get(key));
		} else if (level == Level.SEVERE) {
			message = (this.get("prefixSevere") + this.get(key));
		} else message = (this.get("prefixInfo") + this.get(key));
		
		for (int i = 0; i < args.length; ++i) {
			message = message.replace("{" + i + "}", String.valueOf(args[i]));
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String prefix(final String msg) {
		return ChatColor.translateAlternateColorCodes('&', this.get("prefixInfo") + msg);
	}
}
