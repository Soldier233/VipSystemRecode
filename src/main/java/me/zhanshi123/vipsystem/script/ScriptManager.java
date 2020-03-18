package me.zhanshi123.vipsystem.script;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import me.zhanshi123.vipsystem.Main;
import org.bukkit.Bukkit;

import javax.script.*;
import java.util.function.Consumer;

public class ScriptManager {
    private boolean enable = false;

    public ScriptManager() {
        try {
            Class.forName("jdk.nashorn.api.scripting.NashornScriptEngineFactory");
            enable = true;
            factory = new NashornScriptEngineFactory();
        } catch (ClassNotFoundException e) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            e.printStackTrace();
        }
    }

    private ScriptEngineFactory factory;

    public ScriptEngine getNashorn() {
        if (!enable) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            return null;
        }
        ScriptEngine nashorn = ((NashornScriptEngineFactory) factory).getScriptEngine(Main.getInstance().getClass().getClassLoader());
        if (nashorn == null) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            return null;
        }
        Bindings bindings = nashorn.createBindings();
        bindings.put("sendMessage", (Consumer<String>) ScriptHelper::sendMessage);
        bindings.put("getPlayer", (Consumer<String>) Bukkit::getPlayer);
        return nashorn;
    }

    public Object invokeCustomFunction(ScriptEngine nashorn, String function, String[] args) {
        try {
            Invocable invocable = (Invocable) nashorn;
            return invocable.invokeFunction(function, args);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
