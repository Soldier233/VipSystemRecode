package me.zhanshi123.vipsystem;

import me.zhanshi123.vipsystem.command.command.CommandHandler;
import me.zhanshi123.vipsystem.data.Database;
import me.zhanshi123.vipsystem.manager.ConfigManager;
import me.zhanshi123.vipsystem.manager.MessageManager;
import me.zhanshi123.vipsystem.manager.UpdateManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private static ConfigManager configManager;
    private static UpdateManager updateManager;
    private static Database database;
    private static Permission permission;
    private static CommandHandler commandHandler;

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

    public static Permission getPermission() {
        return permission;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        MessageManager.init();
        updateManager = new UpdateManager();
        updateManager.checkUpdate();
        database = new Database();
        if (!database.isAvailable()) {
            Bukkit.getConsoleSender().sendMessage(MessageManager.getString("fatalError"));
            setEnabled(false);
            return;
        }
        database.prepare();
        setupPermissions();
        commandHandler = new CommandHandler("vipsys");
    }

    @Override
    public void onDisable() {
        database.release();
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}
