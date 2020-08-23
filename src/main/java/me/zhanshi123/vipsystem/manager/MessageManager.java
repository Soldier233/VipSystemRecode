package me.zhanshi123.vipsystem.manager;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {
    private static FileConfiguration config = new YamlConfiguration();
    private static File f = null;

    public static void writeFile(InputStream input, File file) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void update() {
        FileConfiguration builtIn = new YamlConfiguration();
        try {
            InputStream inputStream = Main.getInstance().getResource(getFileName(Main.getConfigManager().getLanguage()));
            if (inputStream == null) {
                inputStream = Main.getInstance().getResource(getFileName("en"));
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
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(String lang) {
        if (Main.getMinecraftVersion() < 16) {
            return lang + ".yml";
        }
        Main.getInstance().debug("Return 1.16 hex language file");
        return lang + "_16.yml";
    }

    public static void init() {
        try {
            Main.getInstance().debug("Try to init MessageManager");
            File folder = new File(Main.getInstance().getDataFolder(), "messages");
            folder.mkdirs();
            f = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "messages" + File.separator + Main.getConfigManager().getLanguage() + ".yml");
            Main.getInstance().debug("Try to find file " + f.getAbsolutePath());
            if (!f.exists()) {
                Main.getInstance().debug("File not exists, try to generate a new one");
                InputStream inputStream = Main.getInstance().getResource(getFileName(Main.getConfigManager().getLanguage()));
                if (inputStream == null) {
                    Main.getInstance().debug("Language not integrated, copying from English");
                    inputStream = Main.getInstance().getResource(getFileName("en"));
                    f = new File(Main.getInstance().getDataFolder().getAbsolutePath() + File.separator + "messages" + File.separator + Main.getConfigManager().getLanguage() + ".yml");
                }
                writeFile(inputStream, f);
            }
            config.load(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        update();
    }

    public static String getString(String path) {
        String str = config.getString(path);
        if (str == null) {
            return path;
        }
        return ChatColor.translateAlternateColorCodes('&', translateHexColor(str));
    }

    public static List<String> getStringList(String path) {
        return config.getStringList(path)
                .stream()
                .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                .map(MessageManager::translateHexColor)
                .collect(Collectors.toList());
    }

    //匹配16进制颜色
    private static String translateHexColor(String text) {
        if (Main.getMinecraftVersion() < 16) {
            return text;
        }
        char[] chars = text.toCharArray();
//        Main.getInstance().debug("Translate hex: " + text);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '$') { //判断是否有16进制匹配关键词
                /*
                Main.getInstance().debug("i+8:" + (i + 7));
                Main.getInstance().debug("chars.length-1:" + (chars.length - 1));
                 */
                if (i + 7 < chars.length - 1) { //判断是否超长
                    /*
                    Main.getInstance().debug("chars[i+1]: " + chars[i + 1]);
                    Main.getInstance().debug("chars[i+8]: " + chars[i + 8]);
                     */
                    if (chars[i + 1] == '[' && chars[i + 8] == ']') { //判断是否有左右括号匹配
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = i + 2; j < i + 8; j++) {
                            stringBuilder.append(chars[j]); //拼装16进制字符串
                        }
                        String hexColor = stringBuilder.toString();
//                        Main.getInstance().debug("hex: " + hexColor);
                        text = text.replace("$[" + hexColor + "]", ChatColor.of("#" + hexColor).toString());
                    }
                }
            }
        }
        return text;
    }
}
