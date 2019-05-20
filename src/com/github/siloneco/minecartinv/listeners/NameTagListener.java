package com.github.siloneco.minecartinv.listeners;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.github.siloneco.minecartinv.MinecartInventory;

import net.md_5.bungee.api.ChatColor;

public class NameTagListener implements Listener {

	private final HashMap<Player, Long> coolTime = new HashMap<>();

	@EventHandler
	public void onPlayerInteractMinecart(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();

		if (!(e.getRightClicked() instanceof Minecart)) {
			return;
		}

		Minecart cart = (Minecart) e.getRightClicked();

		if (!p.isSneaking() || !p.hasPermission("minecartinventory.changecartname")) {
			return;
		}

		if (p.getInventory().getItemInMainHand() == null
				|| p.getInventory().getItemInMainHand().getType() != Material.NAME_TAG) {
			return;
		}

		ItemStack tag = p.getInventory().getItemInMainHand();

		if (!tag.hasItemMeta() || !tag.getItemMeta().hasDisplayName()) {
			return;
		}

		String id = tag.getItemMeta().getDisplayName();

		if (coolTime.getOrDefault(p, 0L) + 500 > System.currentTimeMillis()) {
			return;
		}

		cart.setCustomName(id);
		p.sendMessage(MinecartInventory.getPluginConfig().chatPrefix + ChatColor.GREEN + "名前を設定しました!");

		coolTime.put(p, System.currentTimeMillis());
	}

	@EventHandler
	public void checkMinecartName(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();

		if (!(e.getRightClicked() instanceof Minecart)) {
			return;
		}

		Minecart cart = (Minecart) e.getRightClicked();

		if (!p.isSneaking() || p.getGameMode() != GameMode.CREATIVE
				|| !p.hasPermission("minecartinventory.checkname")) {
			return;
		}
		if (p.getInventory().getItemInMainHand() == null) {
			return;
		}
		if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
			return;
		}
		if (coolTime.getOrDefault(p, 0L) + 500 > System.currentTimeMillis()) {
			return;
		}

		String prefix = MinecartInventory.getPluginConfig().chatPrefix;

		if (cart.getName().equalsIgnoreCase("entity.MinecartRideable.name")) {
			p.sendMessage(prefix + ChatColor.YELLOW + "名前は設定されていませんでした");
		} else {
			p.sendMessage(prefix + ChatColor.YELLOW + "名前: " + ChatColor.GREEN + cart.getName());
		}

		coolTime.put(p, System.currentTimeMillis());
	}
}
