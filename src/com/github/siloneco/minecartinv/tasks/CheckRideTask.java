package com.github.siloneco.minecartinv.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github.siloneco.minecartinv.InventoryManager;
import com.github.siloneco.minecartinv.MinecartInventory;

public class CheckRideTask {

	private static BukkitTask task = null;
	private static List<Player> riding = new ArrayList<Player>();

	public static void run() {
		task = new BukkitRunnable() {
			public void run() {
				Bukkit.getOnlinePlayers().forEach(p -> {

					if (p.getVehicle() == null) {
						if (riding.contains(p)) {
							riding.remove(p);
							InventoryManager.returnInventory(p);
						}
						return;
					}

					if (riding.contains(p)) {
						return;
					}

					if (!p.hasPermission("minecartinventory.switchinventory")) {
						return;
					}

					Entity vehicle = p.getVehicle();

					String id = "default";
					if (vehicle.getCustomName() != null) {
						id = vehicle.getCustomName();
					}

					id = ChatColor.stripColor(id);

					InventoryManager.setInventory(p, id);
					riding.add(p);
				});
			}
		}.runTaskTimer(MinecartInventory.getPlugin(), 0, 1);
	}

	public static void stopTask() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}
}
