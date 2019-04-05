package me.zhanshi123.vipsystem;

import me.zhanshi123.vipsystem.data.Database;
import me.zhanshi123.vipsystem.manager.ConfigManager;
import me.zhanshi123.vipsystem.manager.MessageManager;
import me.zhanshi123.vipsystem.manager.UpdateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private static ConfigManager configManager;
    private static UpdateManager updateManager;
    private static Database database;

    public static Main getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static UpdateManager getUpdateManager() {
        return updateManager;
    }

    public static Database getDataBase() {
        return database;
    }

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        MessageManager.init();
        updateManager = new UpdateManager();
        updateManager.checkUpdate();
        database = new Database();
        if (!database.checkAvailable()) {
            Bukkit.getConsoleSender().sendMessage(MessageManager.getString("fatalError"));
            setEnabled(false);
            return;
        }
        database.prepare();
    }

    @Override
    public void onDisable() {
        database.release();
    }
}
