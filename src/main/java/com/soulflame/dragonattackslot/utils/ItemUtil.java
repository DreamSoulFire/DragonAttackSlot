package com.soulflame.dragonattackslot.utils;

import com.soulflame.dragonattackslot.files.SectionsData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        //物品为空 则不往后执行
        if (item == null || Material.AIR.equals(item.getType())) return false;
        ItemMeta meta = item.getItemMeta();
        //物品没有lore 则不往后执行
        if (!meta.hasLore()) return false;
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
            if (!last_lore.equalsIgnoreCase(last_config_lore)) {
                is_true_item = false;
                continue;
            }
            is_true_item = true;
        }
        return is_true_item;
    }

    public static boolean isVanillaNull() {
        boolean isNull = false;
        //遍历节点
        for (String key : keys.getKeys(false)) {
            //获取节点数据
            SectionsData data = config_map.get(key);
            //获取原版槽位
            String vanilla_slot = data.getVanilla_slot();
            //如果为空则不往后执行
            if (vanilla_slot.equalsIgnoreCase("")) {
                isNull = true;
                continue;
            }
            isNull = false;
        }
        return isNull;
    }

    /**
     * 清除在线玩家的违规物品
     */
    public static void removeItemInOnlinePlayer() {
        if (isVanillaNull())
            //遍历在线玩家
            for (Player player : Bukkit.getOnlinePlayers())
                //清除玩家物品
                removeItemInPlayer(player);
    }

    /**
     * 清除玩家的违规物品
     */
    public static void removeItemInPlayer(Player player) {
        if (isVanillaNull())
            //遍历玩家背包
            for (ItemStack item : player.getInventory()) {
                if (!isTrueItem(item)) continue;
                item.setAmount(0);
                sendMessage(player, prefix + have_error_item);
        }
    }

    /**
     * 添加识别lore
     * @param itemStack 被添加的物品
     * @param lores 获取的lore
     */
    public static void addCheckLore(ItemStack itemStack, List<String> lores) {
        //获取物品数据
        ItemMeta meta = itemStack.getItemMeta();
        //判断数据不为空
        if (meta == null) return;
        //物品没有lore则不往后执行
        if (!meta.hasLore()) return;
        //获取物品lore
        List<String> line = meta.getLore();
        //将配置里的lore添加至物品lore中
        for (String lore : lores) {
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            line.add(lore);
        }
        //设置lore
        meta.setLore(line);
        //设置物品数据
        itemStack.setItemMeta(meta);
    }
}
