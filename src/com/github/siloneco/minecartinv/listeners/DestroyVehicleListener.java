package com.github.siloneco.minecartinv.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DestroyVehicleListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Entity vehicle = p.getVehicle();

        if ( vehicle == null ) {
            return;
        }

        if ( !(vehicle instanceof ArmorStand) && !(vehicle instanceof Boat) && !(vehicle instanceof Minecart) ) {
            return;
        }

        vehicle.remove();
    }
}
