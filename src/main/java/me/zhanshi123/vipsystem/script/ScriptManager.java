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
import java.lang.reflect.Method;
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
        String javaVersion = System.getProperty("java.specification.version");
        Main.getInstance().debug("Your java version: " + javaVersion);
        switch (javaVersion) {
            case "1.8":
                Main.getInstance().debug("Trying to load old nashorn API");
                try {
                    Class factoryClass = Class.forName("jdk.nashorn.api.scripting.NashornScriptEngineFactory");
                    Object factoryObject = factoryClass.getConstructor().newInstance();
                    Method getScriptEngineMethod = factoryClass.getMethod("getScriptEngine", ClassLoader.class);
                    nashorn = (ScriptEngine) getScriptEngineMethod.invoke(factoryObject, Main.getInstance().getClass().getClassLoader());
                } catch (Exception e) {
                    Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
                    e.printStackTrace();
                }
                break;
            default:
                Main.getInstance().debug("Trying to load new nashorn API");
                try {
//            Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory");
                    nashorn = new ScriptEngineManager(Main.getInstance().getClass().getClassLoader()).getEngineByName("nashorn");
                    if (nashorn == null) {
                        Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
                        return;
                    }
                    nashorn.put("helper", ScriptHelper.getInstance());
                } catch (Exception e) {
                    Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
                    e.printStackTrace();
                }
                break;
        }

    }

    public ScriptEngine getNashorn() {
        return nashorn;
    }

    public Object invokeCustomFunction(CustomFunction customFunction, String function, String[] args) {
        try {
            Main.getInstance().debug("Invoke " + customFunction.getScript().getAbsolutePath() + " function " + function + " with args (" + String.join(",", args) + ")");
            /*
            CompiledScript compiledScript = getCompiledScript(customFunction.getScript());
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
