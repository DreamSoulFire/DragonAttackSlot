package com.soulflame.dragonattackslot.events;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import com.soulflame.dragonattackslot.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //延时4s
        Bukkit.getScheduler().runTaskLater(DragonAttackSlot.getPlugin(), ()-> {
            //清除违规物品
            ItemUtil.removeItemInPlayer(event.getPlayer());
        }, 80);
    }

}
