package com.github.siloneco.minecartinv.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.siloneco.minecartinv.InventoryManager;

public class ChangeInventoryListener implements Listener {

    @EventHandler
    public void onQuitMinecart(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if ( p.getVehicle() == null ) {
            return;
        }

        if ( InventoryManager.isTargetEntity(p.getVehicle()) ) {
            InventoryManager.returnInventory(p);
        }
    }
}
