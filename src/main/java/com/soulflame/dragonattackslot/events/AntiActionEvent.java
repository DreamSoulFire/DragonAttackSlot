package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.utils.ItemUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.ItemUtil.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.*;

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
        if (!isTrueItem(item)) return;
        event.setCancelled(true);
        sendMessage(player, prefix + cant_drop);
    }

    /**
     * 切换副手事件
     * @param event 事件变量
     */
    @EventHandler
    public void changeHand(PlayerSwapHandItemsEvent event) {
        //获取玩家
        Player player = event.getPlayer();
        //获取切换到主手上的物品
        ItemStack item_main = event.getMainHandItem();
        //获取切换到副手上的物品
        ItemStack item_off = event.getOffHandItem();
        if (isTrueItem(item_main)) {
            event.setCancelled(true);
            sendMessage(player, prefix + cant_swap_hand);
        }
        if (isTrueItem(item_off)) {
            event.setCancelled(true);
            sendMessage(player, prefix + cant_swap_hand);
        }
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
        //获取点击的槽位
        int click_slot = event.getSlot();
        if (debug) sendMessage(replaceId(debug_format, click_slot));
        //是管理则不往后执行
        if (player.isOp()) return;
        InventoryView view = event.getView();
        //获取顶部的背包界面
        Inventory topInventory = view.getTopInventory();
        //不为合成栏 则不往后执行
        if (!InventoryType.CRAFTING.equals(topInventory.getType())) return;
        //如果点击的槽位小于零 则不往后执行
        if (click_slot < 0) return;
        PlayerInventory inventory = player.getInventory();
        //点击的种类是数字键时
        if (ClickType.NUMBER_KEY.equals(event.getClick())) {
            //需要判断两种
            //一种是点击的槽位
            //另一种是快捷栏
            if (!isTrueItem(event.getCurrentItem()) && !isTrueItem(inventory.getItem(event.getHotbarButton()))) return;
            event.setCancelled(true);
            sendMessage(player, prefix + cant_fast_move);
            return;
        }
        ItemStack item = inventory.getItem(click_slot);
        if (!ItemUtil.isTrueItem(item)) return;
        event.setCancelled(true);
        sendMessage(player, prefix + cant_take);
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
        ItemStack item = player.getInventory().getItem(next);
        if (!ItemUtil.isTrueItem(item)) return;
        event.setCancelled(true);
        sendMessage(player, prefix + cant_swap);
    }
}
