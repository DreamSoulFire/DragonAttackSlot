package com.soulflame.dragonattackslot.files;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class ConfigFile {
    public static final Map<String, SectionsData> config_map = new HashMap<>();
    public static Set<String> config_keys = new HashSet<>();
    public static ConfigurationSection keys;
    public static boolean debug;
    public static String prefix;
    public static List<String> help;
    public static String cant_take;
    public static String cant_swap;
    public static String cant_drop;
    public static String cant_fast_move;
    public static String cant_swap_hand;
    public static String get_item_error;
    public static String have_error_item;
    public static String must_left_click;
    public static String config_error;
    public static String player_not_online;
    public static String debug_format;
    public static String reload;

    /**
     * 创建配置文件
     */
    public static void createConfig() {
        //获取配置文件
        File file = new File(DragonAttackSlot.getPlugin().getDataFolder(), "config.yml");
        //如果有配置
        if (file.exists()) {
            sendMessage("&7[&cDragonAttackSlot&7] &a已检测到 config 文件");
            return;
        }
        sendMessage("&7[&cDragonAttackSlot&7] &4未检测到 config 文件,正在生成...");
        DragonAttackSlot.getPlugin().saveDefaultConfig();
    }

    /**
     * 加载配置文件
     */
    public static void loadConfig() {
        config_map.clear();
        FileConfiguration config = DragonAttackSlot.getPlugin().getConfig();
        keys = config.getConfigurationSection("slot-list");
        config_keys = keys.getKeys(false);
        for (String key : config_keys) {
            ConfigurationSection section = config.getConfigurationSection("slot-list." + key);
            String mapped = section.getString("mapped");
            String mapping = section.getString("mapping");
            List<String> lore = section.getStringList("lores");
            SectionsData data = new SectionsData(mapped, mapping, lore);
            config_map.put(key, data);
        }
        //载入配置
        debug = config.getBoolean("debug", false);
        prefix = config.getString("message.prefix", "&7[&cDragonAttackSlot&7] ");
        help = config.getStringList("message.help");
        cant_take = config.getString("message.cant-take", "&4你无法拿取此物品");
        cant_swap = config.getString("message.cant-swap", "&4你无法切换到此物品栏");
        cant_drop = config.getString("message.cant-drop", "&4你无法丢弃此物品");
        cant_fast_move = config.getString("message.cant-fast-move", "&4你无法快速移动此物品");
        cant_swap_hand = config.getString("message. cant-swap-hand", "&4你无法切换此物品到主副手");
        get_item_error = config.getString("message.get-item-error", "&4物品获取失败, 请检查配置是否出错");
        have_error_item = config.getString("message.have-error-item", "&4身上存在违规物品, 已清除");
        must_left_click = config.getString("message.must-left-click", "&4只允许通过左键点击映射物品");
        config_error = config.getString("message.config-error", "&4插件配置错误, 请检查配置");
        player_not_online = config.getString("message.player-not-online", "&4玩家处于离线状态");
        debug_format = config.getString("message.debug-format", "&6你点击了&f: &b<id>");
        reload = config.getString("message.reload", "&a插件重载完成");
    }
}
