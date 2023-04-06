package com.soulflame.dragonattackslot.commands;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import com.soulflame.dragonattackslot.utils.ItemUtil;
import com.soulflame.dragonattackslot.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.soulflame.dragonattackslot.files.ConfigFile.*;
import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class MainCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendMessage(sender, help);
            return true;
        }
        if (args.length != 1) {
            switch (args[0]) {
                case "c":
                case "clear":
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        TextUtil.sendMessage(sender, prefix + player_not_online);
                        return true;
                    }
                    ItemUtil.removeItemInPlayer(player);
                    return true;
            }
            return true;
        }
        switch (args[0]) {
            case "h":
            case "help":
                sendMessage(sender, help);
                return true;
            case "r":
            case "reload":
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
        tab.add("r");
        tab.add("clear");
        tab.add("c");
        return tab;
    }

}
