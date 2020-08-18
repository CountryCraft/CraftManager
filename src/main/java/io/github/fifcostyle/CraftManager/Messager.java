package io.github.fifcostyle.CraftManager;

import java.io.InputStreamReader;
import java.io.Reader;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.fifcostyle.CraftManager.enums.PrefixLevel;

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
		return this.format(PrefixLevel.INFO, key, args);
	}
	
	public String format(PrefixLevel level, final String key, final Object... args) {
		String message = "";
		if (level == PrefixLevel.INFO) {
			message = (this.get("prefixInfo") + this.get(key));
		} else if (level == PrefixLevel.WARNING) {
			message = (this.get("prefixWarn") + this.get(key));
		} else if (level == PrefixLevel.ERROR) {
			message = (this.get("prefixError") + this.get(key));
		} else if (level == PrefixLevel.STAFF) {
			message = (this.get("prefixStaff") + this.get(key));
		} else if (level == PrefixLevel.DEBUG) {
			message = (this.get("prefixDebug") + this.get(key));
		} else message = (this.get("prefixInfo") + this.get(key));
		
		for (int i = 0; i < args.length; ++i) {
			message = message.replace("{" + i + "}", String.valueOf(args[i]));
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String prefix(final String msg) {
		return this.prefix(PrefixLevel.INFO, msg);
	}
	public String prefix(PrefixLevel level, final String msg) {
		String message = "";
		if (level == PrefixLevel.INFO) message = (this.get("prefixInfo")) + msg;
		else if (level == PrefixLevel.WARNING) message = (this.get("prefixWarn")) + msg;
		else if (level == PrefixLevel.ERROR) message = (this.get("prefixError")) + msg;
		else if (level == PrefixLevel.STAFF) message = (this.get("prefixStaff")) + msg;
		else if (level == PrefixLevel.DEBUG) message = (this.get("prefixDebug")) + msg;
		else message = this.get("prefixInfo") + msg;
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}