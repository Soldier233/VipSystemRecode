package me.zhanshi123.vipsystem.script;

import me.zhanshi123.vipsystem.Main;
import org.bukkit.Bukkit;

import javax.script.*;
import java.util.function.Consumer;

public class ScriptManager {
    private boolean enable = false;

    private ScriptEngine nashorn;

    public ScriptManager() {
        try {
            Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory");
            enable = true;
            nashorn = new ScriptEngineManager(Main.getInstance().getClass().getClassLoader()).getEngineByName("nashorn");
            if (nashorn == null) {
                Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            }
            Bindings bindings = nashorn.createBindings();
            bindings.put("sendMessage", (Consumer<String>) ScriptHelper::sendMessage);
            bindings.put("getPlayer", (Consumer<String>) Bukkit::getPlayer);
        } catch (ClassNotFoundException e) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            e.printStackTrace();
        }
    }

    public ScriptEngine getNashorn() {
        return nashorn;
    }

    public Object invokeCustomFunction(String function, String[] args) {
        try {
            Invocable invocable = (Invocable) nashorn;
            return invocable.invokeFunction(function, args);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
