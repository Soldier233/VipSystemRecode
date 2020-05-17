package me.zhanshi123.vipsystem;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.command.CommandHandler;
import me.zhanshi123.vipsystem.convert.ConvertManager;
import me.zhanshi123.vipsystem.custom.CustomManager;
import me.zhanshi123.vipsystem.customcommand.CustomCommandManager;
import me.zhanshi123.vipsystem.data.Cache;
import me.zhanshi123.vipsystem.data.Database;
import me.zhanshi123.vipsystem.listener.PlayerListener;
import me.zhanshi123.vipsystem.manager.ConfigManager;
import me.zhanshi123.vipsystem.manager.MessageManager;
import me.zhanshi123.vipsystem.manager.UpdateManager;
import me.zhanshi123.vipsystem.metrics.CStats;
import me.zhanshi123.vipsystem.metrics.Metrics;
import me.zhanshi123.vipsystem.script.ScriptManager;
import me.zhanshi123.vipsystem.task.CheckAllTask;
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
    private static Metrics metrics;
    private static CStats cStats;
    private static Cache cache;
    private static ConvertManager convertManager;
    private static CustomCommandManager customCommandManager;
    private static CustomManager customManager;
    private static ScriptManager scriptManager;

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

    public static Metrics getMetrics() {
        return metrics;
    }

    public static Cache getCache() {
        return cache;
    }

    public static CustomCommandManager getCustomCommandManager() {
        return customCommandManager;
    }

    public static ConvertManager getConvertManager() {
        return convertManager;
    }

    public static CustomManager getCustomManager() {
        return customManager;
    }

    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        convertManager = new ConvertManager();
        customCommandManager = new CustomCommandManager();
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
        scriptManager = new ScriptManager();
        customManager = new CustomManager();
        metrics = new Metrics(instance);
        cStats = new CStats(instance);
        cache = new Cache();
        new CheckAllTask().runTaskTimerAsynchronously(instance, 0L, 20 * 60l);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);
        VipSystemAPI.getInstance().getOnlinePlayers().forEach(player -> cache.cache(player));
    }

    @Override
    public void onDisable() {
        VipSystemAPI.getInstance().getOnlinePlayers().forEach(player -> Main.getCache().deCache(player));
        Bukkit.getScheduler().cancelTasks(Main.getInstance());
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

    public void reload() {
        VipSystemAPI.getInstance().getOnlinePlayers().forEach(player -> Main.getCache().deCache(player));
        Bukkit.getScheduler().cancelTasks(Main.getInstance());
        database.release();
        //disable & enable
        customCommandManager = new CustomCommandManager();
        configManager = new ConfigManager();
        MessageManager.init();
        database = new Database();
        if (!database.isAvailable()) {
            Bukkit.getConsoleSender().sendMessage(MessageManager.getString("fatalError"));
            setEnabled(false);
            return;
        }
        database.prepare();
        commandHandler = new CommandHandler("vipsys");
        cache = new Cache();
        scriptManager = new ScriptManager();
        customManager = new CustomManager();
        new CheckAllTask().runTaskTimerAsynchronously(instance, 0L, 20 * 60L);
        VipSystemAPI.getInstance().getOnlinePlayers().forEach(player -> cache.cache(player));
    }

}
