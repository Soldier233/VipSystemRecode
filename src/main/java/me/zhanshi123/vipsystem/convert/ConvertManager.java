package me.zhanshi123.vipsystem.convert;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;

public class ConvertManager {
    private boolean oldVersion = false;

    public boolean isOldVersion() {
        return oldVersion;
    }

    public ConvertManager() {
        oldVersion = detectOldVersion();
        if (oldVersion) {
            convertConfig();
            Main.getInstance().getLogger().info("Configuration from previous version has been converted.");
        }
    }

    public boolean detectOldVersion() {
        File file = new File(Main.getInstance().getDataFolder(), "version.yml");
        boolean old = file.exists();
        if (old) {
            file.delete();
        }
        return old;
    }

    private FileConfiguration oldConfig = new YamlConfiguration();
    private FileConfiguration newConfig = new YamlConfiguration();

    public void convertConfig() {
        try {
            File oldFile = new File(Main.getInstance().getDataFolder(), "config.yml");
            oldConfig.load(new BufferedReader(new InputStreamReader(new FileInputStream(oldFile), Charsets.UTF_8)));
            String lang = oldConfig.getString("Config.language");
            String group = oldConfig.getString("Config.Default");
            boolean previous = false;
            if (group.equalsIgnoreCase("#last")) {
                previous = true;
            }
            boolean uuid = oldConfig.getBoolean("Config.UUID-Mode");
            boolean global = oldConfig.getBoolean("Config.isGlobal");
            List<String> worlds = oldConfig.getStringList("Config.worlds");
            ConfigurationSection customCommands = oldConfig.getConfigurationSection("Config.Commands");
            String dateFormat = oldConfig.getString("Config.DateFormat");
            String prefix = oldConfig.getString("Config.DataBase.prefix");
            String type = oldConfig.getString("Config.DataBase.type");
            String address = oldConfig.getString("Config.DataBase.MySQL.addr");
            String port = oldConfig.getString("Config.DataBase.MySQL.port");
            String database = oldConfig.getString("Config.DataBase.MySQL.base");
            String user = oldConfig.getString("Config.DataBase.MySQL.user");
            String pwd = oldConfig.getString("Config.DataBase.MySQL.pwd");
            String extra = oldConfig.getString("Config.DataBase.MySQL.extra");
            String jdbc = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}{3}", address, port, database, extra);
            oldFile.renameTo(new File(Main.getInstance().getDataFolder(), "config.old.yml"));
            File newFile = new File(Main.getInstance().getDataFolder(), "config.yml");
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            newConfig.load(new BufferedReader(new InputStreamReader(new FileInputStream(newFile), Charsets.UTF_8)));
            newConfig.set("lang", lang);
            newConfig.set("uuid", uuid);
            newConfig.set("previousGroup", previous);
            newConfig.set("dataBase.usePool", true);
            newConfig.set("dataBase.useMySQL", type.equalsIgnoreCase("mysql"));
            newConfig.set("dataBase.MySQL.address", jdbc);
            newConfig.set("dataBase.MySQL.user", user);
            newConfig.set("dataBase.MySQL.password", pwd);
            newConfig.set("dataBase.MySQL.table", prefix);
            newConfig.set("isGlobal", global);
            newConfig.set("worlds", worlds);
            newConfig.set("dateFormat", dateFormat);
            newConfig.set("customCommands", customCommands);
            newConfig.save(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }
}
