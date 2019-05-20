package com.github.siloneco.minecartinv.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.siloneco.minecartinv.InventoryManager;
import com.github.siloneco.minecartinv.MinecartInventory;
import com.github.siloneco.minecartinv.util.InventoryData;

import net.md_5.bungee.api.ChatColor;

public class MinecartInventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length <= 0) {
			sender.sendMessage(getUsage(label));
			return true;
		}

		String prefix = MinecartInventory.getPluginConfig().chatPrefix;

		if (args[0].equalsIgnoreCase("set")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(prefix + ChatColor.RED + "プレイヤーのみ実行可能です");
				return true;
			}

			if (args.length <= 1) {
				sender.sendMessage(prefix + ChatColor.RED + "setの後にインベントリ名を指定してください");
				return true;
			}

			Player p = (Player) sender;

			ItemStack[] contents = p.getInventory().getContents().clone();
			ItemStack[] armors = p.getInventory().getArmorContents().clone();
			ItemStack item = p.getInventory().getItemInOffHand();

			InventoryData data = new InventoryData(args[1], contents, armors, item);
			data.save();

			if (data.getKey().equals("default")) {
				p.sendMessage(prefix + ChatColor.GREEN + "デフォルトのインベントリを保存しました！ "
						+ ChatColor.GRAY + "(名前の付いていないEntityに乗った時にこれが適用されます)");
			} else {
				p.sendMessage(prefix + ChatColor.GREEN + "インベントリを "
						+ ChatColor.YELLOW + data.getKey()
						+ ChatColor.GREEN + " という名前で保存しました！");
			}
		} else if (args[0].equalsIgnoreCase("reset")) {

			if (args.length <= 1) {
				sender.sendMessage(prefix + ChatColor.RED + "resetの後にインベントリ名を指定してください");
				return true;
			}

			String key = args[1];

			if (!InventoryManager.isRegistered(key)) {
				sender.sendMessage(prefix + ChatColor.RED + "そのようなデータはありませんでした！");
			}

			boolean success = InventoryManager.deleteInventoryData(key);

			if (success) {
				sender.sendMessage(prefix + ChatColor.GREEN + "正常に削除しました！");
			} else {
				sender.sendMessage(prefix + ChatColor.RED
						+ "削除に失敗しました！サーバーを停止して手動で削除する必要があります！");
			}
		} else if (args[0].equalsIgnoreCase("list")) {
			List<InventoryData> dataList = InventoryManager.getAllInventoryData();
			List<String> messages = new ArrayList<String>(
					Arrays.asList(ChatColor.RED + "" + dataList.size() + "個のデータが見つかりました。"));

			int i = 1;
			for (InventoryData data : dataList) {
				messages.add(ChatColor.YELLOW + "" + i + ", " + data.getKey());
				i++;
			}

			sender.sendMessage(Strings.join(messages, "\n"));
		} else {
			sender.sendMessage(getUsage(label));
		}
		return true;
	}

	private String getUsage(String label) {
		String cmd = ChatColor.RED + "/" + label + " ";
		String line = ChatColor.GRAY + "-";
		List<String> messages = Arrays.asList(
				cmd + "set [Name] " + line + ChatColor.YELLOW + " 指定されたIDでインベントリを登録します",
				cmd + "reset [Name] " + line + ChatColor.YELLOW + "指定されたIDのインベントリを削除します",
				cmd + "list " + line + " 現在登録済みのインベントリリストを表示します");

		return Strings.join(messages, "\n");
	}
}
