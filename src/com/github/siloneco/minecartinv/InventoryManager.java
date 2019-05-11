package com.github.siloneco.minecartinv;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

	private static File file = null;

	private static ItemStack[] content = null;
	private static HashMap<Player, ItemStack[]> playerContents = new HashMap<>();

	protected static void init(MinecartInventory plugin) {
		file = new File(plugin.getDataFolder(), "inventory.yml");
		loadInventoryContents();
	}

	public static void setInventory(Player p) {
		if (content == null) {
			return;
		}

		playerContents.put(p, p.getInventory().getContents().clone());

		p.getInventory().setContents(content.clone());
	}

	public static void returnInventory(Player p) {
		p.getInventory().setContents(playerContents.getOrDefault(p, new ItemStack[36]));

		if (playerContents.containsKey(p)) {
			playerContents.remove(p);
		}
	}

	private static void loadInventoryContents() {

		if (!file.exists()) {
			return;
		}

		content = new ItemStack[36];
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);

		for (int i = 0; i < (9 * 4); i++) {
			if (!conf.isSet(i + "")) {
				continue;
			}

			ItemStack item = conf.getItemStack(i + "");
			content[i] = item;
		}
	}

	public static void saveInventoryContents(ItemStack[] items) {

		content = items.clone();

		YamlConfiguration conf = new YamlConfiguration();

		for (int i = 0; i < (9 * 4); i++) {
			if (items[i] == null) {
				continue;
			}
			if (items[i].getType() == Material.AIR) {
				continue;
			}

			conf.set(i + "", items[i]);
		}

		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void returnAll() {
		for (Player p : playerContents.keySet()) {
			p.getInventory().setContents(playerContents.get(p));
		}
	}
}
