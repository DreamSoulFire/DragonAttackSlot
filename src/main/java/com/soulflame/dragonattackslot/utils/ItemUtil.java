package com.soulflame.dragonattackslot.utils;

import com.soulflame.dragonattackslot.SectionsData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;
import static com.soulflame.dragonattackslot.files.ConfigFile.*;

public class ItemUtil {

    /**
     * 用于检测物品中的识别lore
     * @param player 玩家
     * @param event 事件
     * @param item 需要进行识别的物品
     * @param message 发送的信息
     */
    public static void checkItem(Player player, Cancellable event, ItemStack item, String message) {
        //物品为空 则不往后执行
        if (item == null || Material.AIR.equals(item.getType())) return;
        ItemMeta meta = item.getItemMeta();
        //物品没有lore 则不往后执行
        if (!meta.hasLore()) return;
        //获取物品lore
        List<String> lores = meta.getLore();
        //获取lore的最后一行
        String last_lore = lores.get(lores.size() - 1);
        for (String key : config_keys) {
            //获取配置参数
            SectionsData data = config_map.get(key);
            //获取配置中的识别lore
            List<String> config_lores = data.getLore();
            //获取识别lore的最后一行
            String last_config_lore = config_lores.get(config_lores.size() - 1);
            //解析颜色字符
            last_config_lore = ChatColor.translateAlternateColorCodes('&', last_config_lore);
            //如果两者不一致则不往后执行
            if (!last_lore.equalsIgnoreCase(last_config_lore)) continue;
            //一致则取消事件并发送信息
            event.setCancelled(true);
            sendMessage(player, message);
        }
    }

    /**
     * 用于检测物品中的识别lore
     * @param player 玩家
     * @param event 事件
     * @param slot 需要进行识别的物品数字槽位
     * @param message 发送的信息
     */
    public static void checkItem(Player player, Cancellable event, int slot, String message) {
        //获取物品
        ItemStack item = player.getInventory().getItem(slot);
        checkItem(player, event, item, message);
    }
}
