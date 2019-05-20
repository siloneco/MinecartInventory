package com.github.siloneco.minecartinv.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.github.siloneco.minecartinv.MinecartInventory;

public class InventoryData {

	private String key;
	private ItemStack[] armor, content;
	private ItemStack offhand;

	public InventoryData(String key, ItemStack[] content, ItemStack[] armor, ItemStack offhand) {
		this.key = key;
		this.armor = armor;
		this.content = content;
		this.offhand = offhand;
	}

	public ItemStack[] getArmors() {
		return armor;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public ItemStack[] getContents() {
		return content;
	}

	public void setContent(ItemStack[] content) {
		this.content = content;
	}

	public ItemStack getOffHand() {
		return offhand;
	}

	public void setOffhand(ItemStack offhand) {
		this.offhand = offhand;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void save() {
		File folder = new File(MinecartInventory.getPlugin().getDataFolder(), "Inventories");
		File file = new File(folder, key + ".yml");
		YamlConfiguration conf = new YamlConfiguration();

		for (int i = 0; i < 36; i++) {
			if (content.length <= i) {
				break;
			}

			ItemStack item = content[i];

			if (item == null || item.getType() == Material.AIR) {
				continue;
			}

			conf.set("Contents." + i, item);
		}

		for (int i = 0; i < 4; i++) {
			if (armor.length <= i) {
				break;
			}

			ItemStack item = armor[i];

			if (item == null || item.getType() == Material.AIR) {
				continue;
			}

			conf.set("Armors." + i, item);
		}

		if (offhand != null && offhand.getType() != Material.AIR) {
			conf.set("OffHand", offhand);
		}

		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
