package com.soulflame.dragonattackslot;

import com.soulflame.dragonattackslot.commands.MainCommand;
import com.soulflame.dragonattackslot.events.AntiActionEvent;
import com.soulflame.dragonattackslot.events.CoreEquipEvent;
import com.soulflame.dragonattackslot.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class DragonAttackSlot extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        long start = System.currentTimeMillis();
        //发送插件启动信息
        sendMessage("&b====================================================");
        sendMessage("&7[&cDragonAttackSlot&7] &a插件已启动, 开始构建插件");
        //创建配置
        ConfigFile.createConfig();
        ConfigFile.loadConfig();
        //如果没有龙核插件就不注册事件
        if (Bukkit.getPluginManager().getPlugin("DragonCore") == null) return;
        sendMessage("&7[&cDragonAttackSlot&7] &6开始注册事件");
        //注册事件
        Bukkit.getPluginManager().registerEvents(new AntiActionEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CoreEquipEvent(), this);
        //注册指令
        sendMessage("&7[&cDragonAttackSlot&7] &6开始注册指令");
        Bukkit.getPluginCommand("dragonattackslot").setExecutor(new MainCommand());
        Bukkit.getPluginCommand("dragonattackslot").setTabCompleter(new MainCommand());
        long finish = System.currentTimeMillis();
        long last = finish - start;
        sendMessage("&7[&cDragonAttackSlot&7] &b构建用时: " + last + "ms");
        sendMessage("&b====================================================");
    }

    @Override
    public void onDisable() {
        //发送插件卸载信息
        sendMessage("&b====================================================");
        sendMessage("&7[&cDragonAttackSlot&7] &4插件已关闭");
        sendMessage("&b====================================================");
    }
}
