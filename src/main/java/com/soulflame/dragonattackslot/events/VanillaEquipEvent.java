package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.files.SectionsData;
import com.soulflame.dragonattackslot.utils.ItemUtil;
import eos.moe.dragoncore.api.SlotAPI;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class VanillaEquipEvent implements Listener {

    @EventHandler
    public void clickInv(InventoryClickEvent event) {
        HumanEntity whoClicked = event.getWhoClicked();
        if (!(whoClicked instanceof Player)) return;
        Player player = ((Player) whoClicked).getPlayer();
        InventoryView view = event.getView();
        Inventory topInventory = view.getTopInventory();
        int click_slot = event.getSlot();
        if (!InventoryType.CRAFTING.equals(topInventory.getType())) return;
        if (click_slot < 0) return;
        ItemStack cursor = player.getItemOnCursor();
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
            if (!"vanilla".equalsIgnoreCase(split_mapped[0])) continue;
            if (!split_mapped[1].equalsIgnoreCase(String.valueOf(click_slot))) continue;
            if (!event.getClick().equals(ClickType.LEFT)) {
                if (cursor == null || Material.AIR.equals(cursor.getType())) return;
                sendMessage(player, prefix + must_left_click);
                event.setCancelled(true);
                return;
            }
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
            if (ItemUtil.isTrueItem(cursor)) return;
            ItemStack item = new ItemStack(cursor);
            ItemUtil.addCheckLore(item, lore);
            SlotAPI.setSlotItem(player, split_mapping[1], item, true);
        }
    }

}
