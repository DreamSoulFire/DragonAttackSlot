package com.soulflame.dragonattackslot.files;

import com.soulflame.dragonattackslot.DragonAttackSlot;
import com.soulflame.dragonattackslot.SectionsData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

import static com.soulflame.dragonattackslot.utils.TextUtil.sendMessage;

public class ConfigFile {
    //用于储存配置文件数据
    public static final Map<String, SectionsData> config_map = new HashMap<>();
    //用于储存配置文件多节点
    public static Set<String> config_keys = new HashSet<>();
    public static String prefix;
    public static List<String> help;
    public static String cant_take;
    public static String cant_swap;
    public static String cant_drop;
    public static String cant_fast_move;
    public static String get_item_error;
    public static String reload;

    /**
     * 创建配置文件
     */
    public static void createConfig() {
        //获取配置文件
        File file = new File(DragonAttackSlot.getPlugin().getDataFolder(), "config.yml");
        //如果有配置
        if (file.exists()) {
            //发送信息并返回
            sendMessage("&7[&cDragonAttackSlot&7] &a已检测到 config 文件");
            return;
        }
        //发送信息
        sendMessage("&7[&cDragonAttackSlot&7] &4未检测到 config 文件,正在生成...");
        //生成配置
        DragonAttackSlot.getPlugin().saveDefaultConfig();
    }

    /**
     * 加载配置文件
     */
    public static void loadConfig() {
        config_map.clear();
        FileConfiguration config = DragonAttackSlot.getPlugin().getConfig();
        //获取总配置节点
        ConfigurationSection keys = config.getConfigurationSection("slot-list");
        config_keys = keys.getKeys(false);
        //遍历节点
        for (String key : config_keys) {
            //获取所有子节点
            ConfigurationSection section = config.getConfigurationSection("slot-list." + key);
            //获取配置里的龙核槽位
            String slot = section.getString("dragon-core", "");
            //获取需要映射的原版槽位数字id
            int vanilla = section.getInt("vanilla", 0);
            //获取特殊的识别lore
            List<String> lore = section.getStringList("lores");
            SectionsData data = new SectionsData(slot, vanilla, lore);
            //存入map方便多次使用
            config_map.put(key, data);
        }
        //载入语言设置
        prefix = config.getString("message.prefix", "&7[&cDragonAttackSlot&7] ");
        help = config.getStringList("message.help");
        cant_take = config.getString("message.cant-take", "&4你无法拿取此物品");
        cant_swap = config.getString("message.cant-swap", "&4你无法切换到此物品栏");
        cant_drop = config.getString("message.cant-drop", "&4你无法丢弃此物品");
        cant_fast_move = config.getString("message.cant-fast-move", "&4你无法快速移动此物品");
        get_item_error = config.getString("message.get-item-error", "&4物品获取失败, 请检查配置是否出错");
        reload = config.getString("message.reload", "&a插件重载完成");
    }
}
