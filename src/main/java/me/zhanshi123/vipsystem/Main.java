package me.zhanshi123.vipsystem;

import me.zhanshi123.vipsystem.manager.ConfigManager;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private static ConfigManager configManager;

    public static Main getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        MessageManager.init();
    }

    @Override
    public void onDisable() {

    }
}
