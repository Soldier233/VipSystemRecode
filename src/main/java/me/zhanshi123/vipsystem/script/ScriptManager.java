package me.zhanshi123.vipsystem.script;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.custom.CustomFunction;

import javax.script.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ScriptManager {
    private ScriptEngine nashorn;
    private final Map<File, CompiledScript> cache = Maps.newHashMap();

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
            nashorn = new ScriptEngineManager(ScriptHelper.getInstance().getClass().getClassLoader()).getEngineByName("nashorn");
            if (nashorn == null) {
                Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
                return;
            }
            nashorn.put("helper", ScriptHelper.getInstance());
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
            Main.getInstance().debug("Invoke " + customFunction.getScript().getAbsolutePath() + " function " + function + " with args (" + String.join(",", args) + ")");
            /*
            compiledScript = getCompiledScript(customFunction.getScript());
            return ((Invocable) compiledScript.getEngine()).invokeFunction(function, args);
             */
            nashorn.eval(new BufferedReader(new InputStreamReader(new FileInputStream(customFunction.getScript()), Charsets.UTF_8)));
            return ((Invocable) nashorn).invokeFunction(function, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
