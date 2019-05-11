package com.github.siloneco.minecartinv.listeners;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.github.siloneco.minecartinv.InventoryManager;

public class ChangeInventoryListener implements Listener {

	@EventHandler
	public void onRideMinecard(VehicleEnterEvent e) {
		if (!(e.getEntered() instanceof Player)) {
			return;
		}
		if (!(e.getVehicle() instanceof Minecart)) {
			return;
		}

		Player p = (Player) e.getEntered();

		if (!p.hasPermission("minecartinventory.switchinventory")) {
			return;
		}

		InventoryManager.setInventory(p);
	}

	@EventHandler
	public void onRideMinecard(VehicleExitEvent e) {
		if (!(e.getExited() instanceof Player)) {
			return;
		}
		if (!(e.getVehicle() instanceof Minecart)) {
			return;
		}

		Player p = (Player) e.getExited();

		if (!p.hasPermission("minecartinventory.switchinventory")) {
			return;
		}

		InventoryManager.returnInventory(p);
	}

	@EventHandler
	public void onQuitMinecard(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		InventoryManager.returnInventory(p);
	}
}
