package com.github.siloneco.minecartinv;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.siloneco.minecartinv.commands.MinecartInventoryCommand;
import com.github.siloneco.minecartinv.listeners.ChangeInventoryListener;
import com.github.siloneco.minecartinv.listeners.NameTagListener;
import com.github.siloneco.minecartinv.tasks.CheckRideTask;

public class MinecartInventory extends JavaPlugin {

	private static MinecartInventory plugin;
	private static PluginConfig config;

	@Override
	public void onEnable() {

		plugin = this;

		MinecartInventory.config = new PluginConfig(this);
		MinecartInventory.config.loadConfig();

		InventoryManager.init();

		CheckRideTask.run();

		Bukkit.getPluginCommand("minecartinventory").setExecutor(new MinecartInventoryCommand());

		Bukkit.getPluginManager().registerEvents(new ChangeInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new NameTagListener(), this);

		Bukkit.getLogger().info(getName() + " enabled.");
	}

	@Override
	public void onDisable() {

		CheckRideTask.stopTask();
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
