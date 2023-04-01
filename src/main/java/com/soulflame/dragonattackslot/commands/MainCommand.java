package com.soulflame.dragonattackslot.commands;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class MainCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //长度为1时也发送帮助
        if (args.length == 0) sendMessage(sender, help);
        //如果长度不为1则不往后执行
        if (args.length != 1) return true;
        //判断指令参数
        switch (args[0]) {
            case "h":
            case "help":
                //发送插件帮助
                sendMessage(sender, help);
                return true;
            case "r":
            case "reload":
                //重载配置文件并发送信息
                createConfig();
                DragonAttackSlot.getPlugin().reloadConfig();
                loadConfig();
                sendMessage(sender, prefix + reload);
                return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return null;
        List<String> tab = new ArrayList<>();
        tab.add("help");
        tab.add("h");
        tab.add("reload");
        tab.add("re");
        return tab;
    }

}
