package com.github.siloneco.minecartinv.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.siloneco.minecartinv.InventoryManager;
import com.github.siloneco.minecartinv.MinecartInventory;

import net.md_5.bungee.api.ChatColor;

public class MinecartInventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length <= 0) {
			sender.sendMessage(MinecartInventory.getPluginConfig().chatPrefix + ChatColor.RED
					+ cmd.getUsage().replace("{LABEL}", label));
			return true;
		}

		if (args[0].equalsIgnoreCase("set")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "プレイヤーのみ実行可能です");
				return true;
			}

			Player p = (Player) sender;

			InventoryManager.saveInventoryContents(p.getInventory().getContents().clone());

			p.sendMessage(MinecartInventory.getPluginConfig().chatPrefix + ChatColor.GREEN + "設定しました！");
		} else {
			sender.sendMessage(MinecartInventory.getPluginConfig().chatPrefix + ChatColor.RED
					+ cmd.getUsage().replace("{LABEL}", label));
		}
		return true;
	}
}
