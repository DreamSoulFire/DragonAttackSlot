package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.utils.ItemUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.ItemUtil.checkItem;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class AntiActionEvent implements Listener {

    /**
     * 玩家丢弃物品事件
     * @param event 事件变量
     */
    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        //获取丢弃物品的玩家
        Player player = event.getPlayer();
        //是管理则不往后执行
        if (player.isOp()) return;
        //获取丢弃的物品
        Item item_drop = event.getItemDrop();
        //获取物品叠
        ItemStack item = item_drop.getItemStack();
        ItemUtil.checkItem(player, event, item, prefix + cant_drop);
    }

    /**
     * 背包点击事件
     * @param event 事件变量
     */
    @EventHandler
    public void clickInv(InventoryClickEvent event) {
        //获取点击的实体
        HumanEntity whoClicked = event.getWhoClicked();
        //不为玩家 则不往后执行
        if (!(whoClicked instanceof Player)) return;
        //强转为玩家实体
        Player player = ((Player) whoClicked).getPlayer();
        //是管理则不往后执行
        if (player.isOp()) return;
        InventoryView view = event.getView();
        //获取顶部的背包界面
        Inventory topInventory = view.getTopInventory();
        //不为合成栏 则不往后执行
        if (!InventoryType.CRAFTING.equals(topInventory.getType())) return;
        //获取点击的槽位
        int click_slot = event.getSlot();
        //如果点击的槽位小于零 则不往后执行
        if (click_slot < 0) return;
        //获取数字案件切换物品的数字
        int hotbar = event.getHotbarButton();
        //如果小于等于8 则取消事件
        if (hotbar <= 8 && hotbar >=0) {
            event.setCancelled(true);
            sendMessage(player, prefix + cant_fast_move);
        }
        checkItem(player, event, click_slot, prefix + cant_take);
    }

    /**
     * 切换物品栏事件
     * @param event 事件变量
     */
    @EventHandler
    public void onSwapItem(PlayerItemHeldEvent event) {
        //获取触发事件的玩家
        Player player = event.getPlayer();
        if (player.isOp()) return;
        //获取要切换到的手持物品槽位
        int next = event.getNewSlot();
        checkItem(player, event, next, prefix + cant_swap);
    }
}
