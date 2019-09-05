package com.github.siloneco.minecartinv.listeners;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import com.useful.ucars.ucarDeathEvent;

public class NoDamageExplodeListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void cancelArrowDamage(EntityDamageEvent e) {
        Entity damaged = e.getEntity();

        if ( !(damaged instanceof ArmorStand) && !(damaged instanceof Minecart) && !(damaged instanceof Boat)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void test(VehicleDestroyEvent e) {
        Vehicle vehicle = e.getVehicle();

        if ( !(vehicle instanceof Boat) && !(vehicle instanceof Minecart) ) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void test(ucarDeathEvent e) {
        e.setCancelled(true);
    }
}
