package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.files.SectionsData;
import com.soulflame.dragonattackslot.utils.ItemUtil;
import eos.moe.dragoncore.api.SlotAPI;
import eos.moe.dragoncore.api.event.PlayerSlotUpdateEvent;
import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import eos.moe.dragoncore.database.IDataBase;
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
        Player player = event.getPlayer();
        if (!event.getIdentifier().equals("DragonCore_ClickSlot")) return;
        if (event.getData().size() != 2) return;
        if (player.isOp()) return;
        String identifier = event.getData().get(0);
        for (String key : keys.getKeys(false)) {
            SectionsData data = config_map.get(key);
            String mapping = data.getMapping();
            if (!mapping.contains("<->")) {
                sendMessage(prefix + config_error);
                continue;
            }
            String[] split = mapping.split("<->");
            if (split.length != 2) {
                sendMessage(prefix + config_error);
                continue;
            }
            if (!"core".equalsIgnoreCase(split[0])) continue;
            if (!split[1].equalsIgnoreCase(identifier)) continue;
            event.setCancelled(true);
            sendMessage(player, prefix + cant_take);
        }
    }

    @EventHandler
    public void equipSlot(PlayerSlotUpdateEvent event) {
        Player player = event.getPlayer();
        String dragon_slot = event.getIdentifier();
        if (dragon_slot == null) return;
        for (String key : keys.getKeys(false)) {
            SectionsData data = config_map.get(key);
            String mapped = data.getMapped();
            List<String> lore = data.getLore();
            if (!mapped.contains("<->")) {
                sendMessage(prefix + config_error);
                continue;
            }
            String[] split_mapped = mapped.split("<->");
            if (split_mapped.length != 2) {
                sendMessage(prefix + config_error);
                continue;
            }
            if (!"core".equalsIgnoreCase(split_mapped[0])) continue;
            if (!split_mapped[1].equalsIgnoreCase(dragon_slot)) continue;
            SlotAPI.getSlotItem(player, split_mapped[1], new IDataBase.Callback<ItemStack>() {
                @Override
                public void onResult(ItemStack item) {
                    String mapping = data.getMapping();
                    if (!mapping.contains("<->")) {
                        sendMessage(prefix + config_error);
                        return;
                    }
                    String[] split_mapping = mapping.split("<->");
                    if (split_mapping.length != 2) {
                        sendMessage(prefix + config_error);
                        return;
                    }
                    ItemUtil.addCheckLore(item, lore);
                    if ("vanilla".equalsIgnoreCase(split_mapping[0])) {
                        player.getInventory().setItem(Integer.parseInt(split_mapping[1]), item);
                    }
                    if ("core".equalsIgnoreCase(split_mapping[0])) {
                        SlotAPI.setSlotItem(player, split_mapping[1], item, true);
                    }
                }

                @Override
                public void onFail() {
                    sendMessage(player, prefix + get_item_error);
                }
            });
        }
    }
}
