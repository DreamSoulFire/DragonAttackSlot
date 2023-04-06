package com.soulflame.dragonattackslot.utils;

import com.soulflame.dragonattackslot.files.SectionsData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class ItemUtil {

    /**
     * 用于检测物品中的识别lore
     * @param item 需要进行识别的物品
     * @return 是否通过检测
     */
    public static boolean isTrueItem(ItemStack item) {
        boolean is_true_item = false;
        if (item == null || Material.AIR.equals(item.getType())) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;
        List<String> lores = meta.getLore();
        String last_lore = lores.get(lores.size() - 1);
        for (String key : config_keys) {
            SectionsData data = config_map.get(key);
            List<String> config_lores = data.getLore();
            String last_config_lore = config_lores.get(config_lores.size() - 1);
            last_config_lore = ChatColor.translateAlternateColorCodes('&', last_config_lore);
            if (!last_lore.equalsIgnoreCase(last_config_lore)) {
                is_true_item = false;
                continue;
            }
            is_true_item = true;
        }
        return is_true_item;
    }

    /**
     * 清除玩家的违规物品
     */
    public static void removeItemInPlayer(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (!isTrueItem(item)) continue;
            item.setType(Material.AIR);
            sendMessage(player, prefix + have_error_item);
        }
    }

    /**
     * 添加识别lore
     * @param itemStack 被添加的物品
     * @param lores 获取的lore
     */
    public static void addCheckLore(ItemStack itemStack, List<String> lores) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        if (!meta.hasLore()) {
            List<String> line = new ArrayList<>();
            for (String lore : lores) {
                lore = ChatColor.translateAlternateColorCodes('&', lore);
                line.add(lore);
            }
            meta.setLore(line);
            itemStack.setItemMeta(meta);
            return;
        }
        List<String> line = meta.getLore();
        for (String lore : lores) {
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            line.add(lore);
        }
        meta.setLore(line);
        itemStack.setItemMeta(meta);
    }
}
