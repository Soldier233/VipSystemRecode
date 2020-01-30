package me.zhanshi123.vipsystem.custom;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomManager {
    private Map<String, CustomFunction> functionMap = new HashMap<>();

    private File file;

    public CustomManager() {
        file = new File(Main.getInstance().getDataFolder(), "custom");
        if (!file.exists()) {
            file.mkdirs();
            File folder = new File(file, "Example");
            folder.mkdirs();
            File example = new File(folder, "custom.yml");
            MessageManager.writeFile(Main.getInstance().getResource("custom.yml"), example);
            example = new File(folder, "script.js");
            MessageManager.writeFile(Main.getInstance().getResource("script.js"), example);
        }
        File[] functions = file.listFiles();
        for (File function : functions) {
            try {
                File config = new File(function, "custom.yml");
                if (!config.exists()) {
                    MessageManager.writeFile(Main.getInstance().getResource("custom.yml"), config);
                }
                File script = new File(function, "script.js");
                if (!script.exists()) {
                    MessageManager.writeFile(Main.getInstance().getResource("script.js"), script);
                }
                FileConfiguration yamlConfig = new YamlConfiguration();
                yamlConfig.load(new BufferedReader(new InputStreamReader(new FileInputStream(config), Charsets.UTF_8)));
                List<String> onStart = yamlConfig.getStringList("onStart");
                List<String> onEnd = yamlConfig.getStringList("onEnd");
                List<String> tmp = yamlConfig.getStringList("args");
                String[] array = new String[tmp.size()];
                tmp.toArray(array);
                CustomFunction customFunction = new CustomFunction(function.getName(), yamlConfig.getString("description"), array, yamlConfig.getString("duration"), onStart, onEnd, script);
                functionMap.putIfAbsent(customFunction.getName(), customFunction);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public CustomFunction getCustomFunction(String name) {
        return functionMap.get(name);
    }

    public Map<String, CustomFunction> getFunctionMap() {
        return functionMap;
    }
}
