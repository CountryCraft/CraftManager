package io.github.fifcostyle.CraftManager;

import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messager {
	private FileConfiguration config;
	public Messager() {
		this.config = null;
		this.config = (FileConfiguration)YamlConfiguration.loadConfiguration((Reader)new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("messages.yml")));
	}
	public String get(final String key)
	{
		return this.config.getString(key);
	}
	
	public String format(final String key, final Object... args)
	{
		return this.format(true, key, args);
	}
	
	public String format(final boolean prefix, final String key, final Object... args)
	{
		String message = prefix ? (this.get("prefix") + this.get(key)) : this.get(key);
		for (int i = 0; i < args.length; ++i) {
			message = message.replace("{" + i + "}", String.valueOf(args[i]));
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String prefix(final String msg)
	{
		return ChatColor.translateAlternateColorCodes('&', this.get("prefix") + msg);
	}
}
