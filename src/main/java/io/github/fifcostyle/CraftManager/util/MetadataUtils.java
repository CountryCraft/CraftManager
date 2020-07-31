package io.github.fifcostyle.CraftManager.util;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import io.github.fifcostyle.CraftManager.CraftManager;

public class MetadataUtils {
	
	private static CraftManager craft;
	
	public MetadataUtils(CraftManager craft) {
		MetadataUtils.craft = craft;
	}
	
	public static List<MetadataValue> get(Player target, String key) {
		return target.getMetadata(key);
	}
	
	public static boolean has(Player target, String key) {
		return target.hasMetadata(key);
	}
	
	public static void set(Player target, String key, Object val)  {
		target.setMetadata(key, new FixedMetadataValue(craft, val));
	}
	
	public static void rem(Player target, String key) {
		target.removeMetadata(key, craft);
	}
}
