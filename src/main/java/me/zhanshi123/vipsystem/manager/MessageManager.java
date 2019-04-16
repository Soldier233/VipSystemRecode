package me.zhanshi123.vipsystem.manager;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageManager {
    private static FileConfiguration config = new YamlConfiguration();
    private static File f = null;

    private static void writeFile(InputStream input, File file) {
        try {
            int index;
            byte[] bytes = new byte[10240];
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while ((index = input.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, index);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void update() {
        FileConfiguration builtIn = new YamlConfiguration();
        try {
            InputStream inputStream = Main.getInstance().getResource(Main.getConfigManager().getLanguage() + ".yml");
            if (inputStream == null) {
                inputStream = Main.getInstance().getResource("en.yml");
            }
            builtIn.load(new InputStreamReader(inputStream, Charsets.UTF_8));
            long[] count = {0};
            builtIn.getKeys(true).stream()
                    .filter(key -> config.get(key) == null)
                    .forEach(key -> {
                        config.set(key, builtIn.get(key));
                        count[0]++;
                    });
            if (count[0] != 0) {
                config.save(f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            File folder = new File(Main.getInstance().getDataFolder(), "messages");
            folder.mkdirs();
            f = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "messages" + File.separator + Main.getConfigManager().getLanguage() + ".yml");
            if (!f.exists()) {
                InputStream inputStream = Main.getInstance().getResource(Main.getConfigManager().getLanguage() + ".yml");
                if (inputStream != null) {
                    writeFile(inputStream, f);
                } else {
                    inputStream = Main.getInstance().getResource("en.yml");
                    f = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "messages" + File.separator + Main.getConfigManager().getLanguage() + ".yml");
                    writeFile(inputStream, f);
                }
            }
            config.load(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        update();
    }

    public static String getString(String path) {
        String str = config.getString(path);
        if (str == null) {
            return path;
        }
        return str.replace("&", "§");
    }

    public static List<String> getStringList(String path) {
        List<String> array = config.getStringList(path);
        if (array == null) {
            array = Arrays.asList(new String[]{path});
        }
        List<String> temp = new ArrayList<>();
        array.forEach(str -> temp.add(str.replace("&", "§")));
        return temp;
    }
}
