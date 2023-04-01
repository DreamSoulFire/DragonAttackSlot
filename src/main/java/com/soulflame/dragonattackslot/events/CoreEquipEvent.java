package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.files.SectionsData;
import com.soulflame.dragonattackslot.utils.ItemUtil;
import eos.moe.dragoncore.api.SlotAPI;
import eos.moe.dragoncore.api.event.PlayerSlotUpdateEvent;
import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import eos.moe.dragoncore.database.IDataBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class CoreEquipEvent implements Listener {

    @EventHandler
    public void clickSlot(CustomPacketEvent event) {
        //获取触发事件的玩家
        Player player = event.getPlayer();
        //如果触发的识别字符不是点击槽位则不往后执行
        if (!event.getIdentifier().equals("DragonCore_ClickSlot")) return;
        //如果数据的大小不为2则不往后执行
        if (event.getData().size() != 2) return;
        //获取槽位id
        String identifier = event.getData().get(0);
        //遍历节点
        for (String key : keys.getKeys(false)) {
            //获取节点数据
            SectionsData data = config_map.get(key);
            //获取节点里的龙核槽位
            String slot = data.getDragon_slot();
            //分割龙核槽位
            String[] split = slot.split("<->");
            //长度小于2则不往后执行
            if (split.length < 2) return;
            //遍历分割后的槽位
            for (int i = 1; i < split.length; i++) {
                //如果不是其他龙核槽位则不往后执行
                if (!split[i].equalsIgnoreCase(identifier)) continue;
                //取消事件并发送信息
                event.setCancelled(true);
                sendMessage(player, prefix + cant_take);
            }
        }
    }

    @EventHandler
    public void equipSlot(PlayerSlotUpdateEvent event) {
        //获取触发事件的玩家
        Player player = event.getPlayer();
        //获取触发事件的龙核槽位
        String dragon_slot = event.getIdentifier();
        //如果龙核槽位为空 则不往后执行
        if (dragon_slot == null) return;
        //遍历节点
        for (String key : keys.getKeys(false)) {
            //获取节点数据
            SectionsData data = config_map.get(key);
            //获取节点里的龙核槽位
            String slot = data.getDragon_slot();
            //获取节点里的lore
            List<String> lores = data.getLore();
            //分割龙核槽位
            String[] split = slot.split("<->");
            //触发事件的龙核槽位与配置中的槽位不符 则不往后执行
            if (!dragon_slot.equalsIgnoreCase(split[0])) continue;
            //取得该槽位的物品
            SlotAPI.getSlotItem(player, split[0], new IDataBase.Callback<ItemStack>() {
                //获取成功则执行
                @Override
                public void onResult(ItemStack itemStack) {
                    //长度大于等于2则不往后执行
                    if (split.length >= 2) {
                        //遍历分割后的槽位
                        for (int i = 1; i < split.length; i++) {
                            //映射至其他槽位
                            if (itemStack == null || Material.AIR.equals(itemStack.getType())) {
                                SlotAPI.setSlotItem(player, split[i], new ItemStack(Material.AIR, 0), true);
                                continue;
                            }
                            //添加识别lore
                            if (!ItemUtil.isTrueItem(itemStack)) ItemUtil.addCheckLore(itemStack, lores);
                            SlotAPI.setSlotItem(player, split[i], itemStack, true);
                        }
                        return;
                    }
                    //获取原版槽位
                    String vanillaSlot = data.getVanilla_slot();
                    //如果为空则不往后执行
                    if (vanillaSlot.equalsIgnoreCase("")) return;
                    int vanilla = Integer.parseInt(vanillaSlot);
                    //如果获取的物品为无或者空气
                    if (itemStack == null || Material.AIR.equals(itemStack.getType())) {
                        //则直接将映射槽位设置为空 并不往后执行
                        player.getInventory().setItem(vanilla, null);
                        return;
                    }
                    //添加识别lore
                    ItemUtil.addCheckLore(itemStack, lores);
                    //将映射槽位设置为龙核槽位里放入的物品
                    player.getInventory().setItem(vanilla, itemStack);
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
