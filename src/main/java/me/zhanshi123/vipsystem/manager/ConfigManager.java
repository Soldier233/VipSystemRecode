package me.zhanshi123.vipsystem.manager;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.customcommand.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigManager {
    private FileConfiguration config = new YamlConfiguration();
    private File f = null;

    public ConfigManager() {
        load();
    }

    private void load() {
        try {
            Main plugin = Main.getInstance();
            f = new File(plugin.getDataFolder(), "config.yml");
            if (!f.exists()) {
                Files.copy(getConfigStream(), f.getParentFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            config.load(new BufferedReader(new InputStreamReader(new FileInputStream(f), Charsets.UTF_8)));
            dateFormat = new SimpleDateFormat(Objects.requireNonNull(config.getString("dateFormat")));
            ConfigurationSection customCommands = config.getConfigurationSection("customCommands");
            if (customCommands != null) {
                customCommands.getKeys(false).forEach(group -> Main.getCustomCommandManager().add(new CustomCommand(group, customCommands.getStringList(group + ".activate"), customCommands.getStringList(group + ".expire"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private InputStream getConfigStream(){
        if(Bukkit.getPluginManager().isPluginEnabled("PermissionsEx")){
            return Main.getInstance().getResource("config_pex.yml");
        }else if(Bukkit.getPluginManager().isPluginEnabled("LuckPerms")){
            return Main.getInstance().getResource("config_lp.yml");
        }
        return Main.getInstance().getResource("config.yml");
    }

    public String getLanguage() {
        return config.getString("lang");
    }

    public boolean isUsePool() {
        return config.getBoolean("dataBase.usePool", true);
    }

    public boolean isUseMySQL() {
        return config.getBoolean("dataBase.useMySQL", false);
    }

    public List<String> getMySQL() {
        return Arrays.asList(config.getString("dataBase.MySQL.address"),
                config.getString("dataBase.MySQL.user"),
                config.getString("dataBase.MySQL.password"),
                config.getString("dataBase.MySQL.table"));
    }

    public boolean isUUID() {
        return config.getBoolean("uuid", false);
    }

    public boolean isGlobal() {
        return config.getBoolean("isGlobal", true);
    }

    public List<String> getWorlds() {
        List<String> worlds = config.getStringList("worlds");
        if (!worlds.isEmpty()) {
            return worlds;
        }
        worlds = new ArrayList<>();
        List<String> finalWorlds = worlds;
        Bukkit.getWorlds().forEach(world -> finalWorlds.add(world.getName()));
        return finalWorlds;
    }

    private SimpleDateFormat dateFormat;

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public boolean isPreviousGroup() {
        return config.getBoolean("previousGroup");
    }

    public String getDefaultGroup() {
        return config.getString("defaultGroup");
    }

    public boolean isDisableVault() {
        return config.getBoolean("groupCommands.enable", false);
    }

    public String getAddGroupCommand() {
        return config.getString("groupCommands.addGroup", "pex user {0} group add {1}");
    }

    public String getRemoveGroupCommand() {
        return config.getString("groupCommands.removeGroup", "pex user {0} group remove {1}");
    }

    public String getAddWorldGroupCommand() {
        return config.getString("groupCommands.addWorldGroup", "pex user {0} group add {1} {2}");
    }

    public String getRemoveWorldGroupCommand() {
        return config.getString("groupCommands.removeWorldGroup", "pex user {0} group remove {1} {2}");
    }

    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }
}
