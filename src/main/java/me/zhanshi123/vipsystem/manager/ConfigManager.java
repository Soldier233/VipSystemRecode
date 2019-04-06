package me.zhanshi123.vipsystem.manager;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

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
                plugin.saveResource("config.yml", false);
            }
            config.load(new BufferedReader(new InputStreamReader(new FileInputStream(f), Charsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return Arrays.asList(new String[]{
                config.getString("dataBase.MySQL.address"),
                config.getString("dataBase.MySQL.user"),
                config.getString("dataBase.MySQL.password"),
                config.getString("dataBase.MySQL.table")
        });
    }

    public boolean isUUID() {
        return config.getBoolean("uuid", false);
    }
}
