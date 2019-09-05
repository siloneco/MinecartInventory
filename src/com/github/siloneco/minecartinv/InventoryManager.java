package com.github.siloneco.minecartinv;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.siloneco.minecartinv.util.InventoryData;

public class InventoryManager {

    private static File folder;

    private static HashMap<String, InventoryData> dataMap = new HashMap<>();
    private static HashMap<Player, InventoryData> playerInventoryMap = new HashMap<>();

    protected static void init() {
        folder = new File(MinecartInventory.getPlugin().getDataFolder(), "Inventories");
    }

    public static boolean setInventory(Player p, String key) {
        if ( !dataMap.containsKey(key) ) {

            InventoryData data = loadInventoryData(key);
            if ( data == null ) {
                return false;
            }

            dataMap.put(key, data);
        }

        InventoryData data = dataMap.get(key);

        InventoryData playerData = new InventoryData(null, p.getInventory().getContents().clone(),
                p.getInventory().getArmorContents().clone(), p.getInventory().getItemInOffHand().clone());
        playerInventoryMap.putIfAbsent(p, playerData);

        if ( data.getContents() != null ) {
            p.getInventory().setContents(data.getContents().clone());
        } else {
            p.getInventory().setContents(new ItemStack[36]);
        }

        if ( data.getArmors() != null ) {
            p.getInventory().setArmorContents(data.getArmors().clone());
        } else {
            p.getInventory().setArmorContents(new ItemStack[4]);
        }

        if ( data.getOffHand() != null ) {
            p.getInventory().setItemInOffHand(data.getOffHand().clone());
        } else {
            p.getInventory().setItemInOffHand(null);
        }

        return true;
    }

    public static boolean returnInventory(Player p) {
        if ( !playerInventoryMap.containsKey(p) ) {
            return false;
        }

        InventoryData data = playerInventoryMap.get(p);

        p.getInventory().setContents(data.getContents());
        p.getInventory().setArmorContents(data.getArmors());
        p.getInventory().setItemInOffHand(data.getOffHand());

        if ( playerInventoryMap.containsKey(p) ) {
            playerInventoryMap.remove(p);
        }

        return true;
    }

    private static InventoryData loadInventoryData(File file) {
        if ( !file.exists() ) {
            return null;
        }

        ItemStack[] contents = new ItemStack[36];
        ItemStack[] armors = new ItemStack[4];
        ItemStack offHand = null;

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);

        for ( int i = 0; i < 9 * 4; i++ ) {
            if ( !conf.isSet("Contents." + i) ) {
                continue;
            }

            ItemStack item = conf.getItemStack("Contents." + i);
            contents[i] = item;
        }

        for ( int i = 0; i < 4; i++ ) {
            if ( !conf.isSet("Armors." + i) ) {
                continue;
            }

            ItemStack item = conf.getItemStack("Armors." + i);
            armors[i] = item;
        }

        if ( conf.isSet("OffHand") ) {
            offHand = conf.getItemStack("OffHand");
        }

        String key = file.getName();
        if ( file.getName().endsWith(".yml") || file.getName().endsWith(".yaml") ) {
            key = file.getName().substring(0, file.getName().lastIndexOf("."));
        }

        InventoryData data = new InventoryData(key, contents, armors, offHand);

        return data;
    }

    private static InventoryData loadInventoryData(String key) {
        File file = new File(folder, key + ".yml");
        return loadInventoryData(file);
    }

    public static boolean deleteInventoryData(String key) {
        if ( !dataMap.containsKey(key) ) {

            InventoryData data = loadInventoryData(key);
            if ( data == null ) {
                return false;
            }
        }

        boolean success = new File(folder, key + ".yml").delete();
        dataMap.remove(key);

        return success;

    }

    public static boolean isRegistered(String key) {
        InventoryData data = loadInventoryData(key);
        return data != null;
    }

    public static List<InventoryData> getAllInventoryData() {
        List<InventoryData> dataList = new ArrayList<>();

        for ( File file : folder.listFiles() ) {
            InventoryData data = loadInventoryData(file);

            if ( data != null ) {
                dataList.add(data);
            }
        }

        return dataList;
    }

    public static boolean isTargetEntity(Entity ent) {
        return targetTypes.contains(ent.getType());
    }

    protected static void returnAll() {
        for ( Player p : playerInventoryMap.keySet() ) {
            InventoryData data = playerInventoryMap.get(p);

            p.getInventory().setContents(data.getContents());
            p.getInventory().setArmorContents(data.getArmors());
            p.getInventory().setItemInOffHand(data.getOffHand());
        }
    }

    private static List<EntityType> targetTypes = Arrays.asList(EntityType.ARROW, EntityType.BAT, EntityType.BLAZE,
            EntityType.BOAT, EntityType.CHICKEN, EntityType.DRAGON_FIREBALL, EntityType.ENDER_DRAGON,
            EntityType.ENDER_PEARL, EntityType.FALLING_BLOCK, EntityType.FIREBALL, EntityType.FIREWORK,
            EntityType.FISHING_HOOK, EntityType.GHAST, EntityType.MINECART, EntityType.SMALL_FIREBALL,
            EntityType.SNOWBALL, EntityType.SPECTRAL_ARROW, EntityType.THROWN_EXP_BOTTLE, EntityType.TIPPED_ARROW,
            EntityType.WITHER, EntityType.WITHER_SKULL);
}
