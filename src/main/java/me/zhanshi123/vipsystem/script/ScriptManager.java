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
        return cache.computeIfAbsent(script, file -> {
            try {
                Main.getInstance().debug(file.getAbsolutePath() + " 's compiled script not found in cache, compiling!");
                return ((Compilable) nashorn).compile(new BufferedReader(new InputStreamReader(new FileInputStream(script), Charsets.UTF_8)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Main.getInstance().debug(file.getAbsolutePath() + " compile failed");
            return null;
        });
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
            Main.getInstance().debug("Invoke " + customFunction.getScript().getAbsolutePath()+ " function " + function + " with args (" + String.join(",", args) + ")");
            /*
            compiledScript = getCompiledScript(customFunction.getScript());
            compiledScript.eval(bindings);
            ((Invocable) compiledScript.getEngine()).invokeFunction(function, args);
             */
            nashorn.eval(new BufferedReader(new InputStreamReader(new FileInputStream(customFunction.getScript()), Charsets.UTF_8)), bindings);
            return ((Invocable) nashorn).invokeFunction(function, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
