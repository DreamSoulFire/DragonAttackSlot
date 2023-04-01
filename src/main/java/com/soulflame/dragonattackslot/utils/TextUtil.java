package com.soulflame.dragonattackslot.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TextUtil {
    /**
     * 解析 & 字符的信息发送方法
     * @param sender 发送信息的玩家
     * @param message 发送的信息
     */
    public static void sendMessage(CommandSender sender, List<String> message) {
        message.forEach(msg -> sendMessage(sender, msg));
    }

    /**
     * 解析 & 字符的信息发送方法
     * @param sender 发送信息的玩家
     * @param message 发送的信息
     */
    public static void sendMessage(CommandSender sender, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(message);
    }

    /**
     * 解析 & 字符的后台信息发送方法
     * @param message 发送的信息
     */
    public static void sendMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
