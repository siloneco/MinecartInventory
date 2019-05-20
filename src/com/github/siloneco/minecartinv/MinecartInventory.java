package com.github.siloneco.minecartinv;

import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.siloneco.minecartinv.commands.MinecartInventoryCommand;
import com.github.siloneco.minecartinv.listeners.ChangeInventoryListener;
import com.github.siloneco.minecartinv.listeners.NameTagListener;

public class MinecartInventory extends JavaPlugin {

	private static MinecartInventory plugin;
	private static PluginConfig config;

	@Override
	public void onEnable() {

		plugin = this;

		MinecartInventory.config = new PluginConfig(this);
		MinecartInventory.config.loadConfig();

		InventoryManager.init();

		Bukkit.getOnlinePlayers().forEach(p -> {
			if (p.getVehicle() != null && p.getVehicle() instanceof Minecart) {
				Minecart cart = (Minecart) p.getVehicle();

				if (cart.getName() == null) {
					InventoryManager.setInventory(p, "default");
				} else {
					InventoryManager.setInventory(p, cart.getName());
				}
			}
		});

		Bukkit.getPluginCommand("minecartinventory").setExecutor(new MinecartInventoryCommand());

		Bukkit.getPluginManager().registerEvents(new ChangeInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new NameTagListener(), this);

		Bukkit.getLogger().info(getName() + " enabled.");
	}

	@Override
	public void onDisable() {

		InventoryManager.returnAll();

		Bukkit.getLogger().info(getName() + " disabled.");
	}

	public void reloadPluginConfig() {

		reloadConfig();

		MinecartInventory.config = new PluginConfig(this);
		MinecartInventory.config.loadConfig();
	}

	public static PluginConfig getPluginConfig() {
		return config;
	}

	public static MinecartInventory getPlugin() {
		return plugin;
	}
}
