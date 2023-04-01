package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import com.soulflame.dragonattackslot.SectionsData;
import eos.moe.dragoncore.api.SlotAPI;
import eos.moe.dragoncore.api.event.PlayerSlotUpdateEvent;
import eos.moe.dragoncore.database.IDataBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class CoreEquipEvent implements Listener {

    @EventHandler
    public void equipSlot(PlayerSlotUpdateEvent event) {
        //获取触发事件的玩家
        Player player = event.getPlayer();
        //获取触发事件的龙核槽位
        String dragon_slot = event.getIdentifier();
        //如果龙核槽位为空 则不往后执行
        if (dragon_slot == null) return;
        //获取总配置节点
        ConfigurationSection keys = DragonAttackSlot.getPlugin().getConfig().getConfigurationSection("slot-list");
        //遍历节点
        for (String key : keys.getKeys(false)) {
            //获取节点数据
            SectionsData data = config_map.get(key);
            String slot = data.getDragon_slot();
            //触发事件的龙核槽位与配置中的槽位不符 则不往后执行
            if (!dragon_slot.equalsIgnoreCase(slot)) continue;
            //取得该槽位的物品
            SlotAPI.getSlotItem(player, slot, new IDataBase.Callback<ItemStack>() {
                //获取成功则执行
                @Override
                public void onResult(ItemStack itemStack) {
                    //如果获取的物品为无或者空气
                    if (itemStack == null || Material.AIR.equals(itemStack.getType())) {
                        //则直接将映射槽位设置为空 并不往后执行
                        player.getInventory().setItem(data.getVanilla_slot(), null);
                        return;
                    }
                    //获取物品数据
                    ItemMeta meta = itemStack.getItemMeta();
                    //判断数据不为空
                    if (meta == null) return;
                    //物品没有lore则不往后执行
                    if (!meta.hasLore()) return;
                    //获取物品lore
                    List<String> line = meta.getLore();
                    //获取配置里的lore
                    List<String> lores = data.getLore();
                    //将配置里的lore添加至物品lore中
                    for (String lore : lores) {
                        lore = ChatColor.translateAlternateColorCodes('&', lore);
                        line.add(lore);
                    }
                    //设置lore
                    meta.setLore(line);
                    //设置物品数据
                    itemStack.setItemMeta(meta);
                    //将映射槽位设置为龙核槽位里放入的物品
                    player.getInventory().setItem(data.getVanilla_slot(), itemStack);
                }
                //获取失败
                @Override
                public void onFail() {
                    sendMessage(prefix + get_item_error);
                }
            });
        }
    }
}
