package com.github.siloneco.minecartinv;

import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.siloneco.minecartinv.commands.MinecartInventoryCommand;
import com.github.siloneco.minecartinv.listeners.ChangeInventoryListener;

public class MinecartInventory extends JavaPlugin {

	private static PluginConfig config;

	@Override
	public void onEnable() {

		MinecartInventory.config = new PluginConfig(this);
		MinecartInventory.config.loadConfig();

		InventoryManager.init(this);

		Bukkit.getOnlinePlayers().forEach(p -> {
			if (p.getVehicle() != null && (p.getVehicle() instanceof Minecart)) {
				InventoryManager.setInventory(p);
			}
		});

		Bukkit.getPluginCommand("minecartinventory").setExecutor(new MinecartInventoryCommand());

		Bukkit.getPluginManager().registerEvents(new ChangeInventoryListener(), this);

		Bukkit.getLogger().info(getName() + " enabled.");
	}

	@Override
	public void onDisable() {

		InventoryManager.returnAll();

		Bukkit.getLogger().info(getName() + " disabled.");
	}

	public void reloadPluginConfig() {

		this.reloadConfig();

		MinecartInventory.config = new PluginConfig(this);
		MinecartInventory.config.loadConfig();
	}

	public static PluginConfig getPluginConfig() {
		return config;
	}
}
