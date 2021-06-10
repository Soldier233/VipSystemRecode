package me.zhanshi123.vipsystem.script;

import com.google.common.base.Charsets;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.custom.CustomFunction;
import org.bukkit.Bukkit;

import javax.script.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ScriptManager {
    private ScriptEngine nashorn;
    private Bindings bindings;
    private Map<File, CompiledScript> cache = new HashMap();

    public CompiledScript getCompiledScript(File script) {
        cache.computeIfAbsent(script, file -> {
            try {
                return ((Compilable) nashorn).compile(new BufferedReader(new InputStreamReader(new FileInputStream(script), Charsets.UTF_8)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        return null;
    }

    public ScriptManager() {
        try {
            Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory");
            nashorn = new ScriptEngineManager(Main.getInstance().getClass().getClassLoader()).getEngineByName("nashorn");
            if (nashorn == null) {
                Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            }
            bindings = nashorn.createBindings();
            bindings.put("helper", new ScriptHelper());
            bindings.put("getPlayer", (Consumer<String>) Bukkit::getPlayer);
        } catch (ClassNotFoundException e) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            e.printStackTrace();
        }
    }

    public ScriptEngine getNashorn() {
        return nashorn;
    }

    public Object invokeCustomFunction(CustomFunction customFunction, String function, String[] args) {
        CompiledScript compiledScript;
        try {
            compiledScript = getCompiledScript(customFunction.getScript());
            ((Invocable) compiledScript).invokeFunction(function, args, bindings);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
