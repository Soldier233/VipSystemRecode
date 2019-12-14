package me.zhanshi123.vipsystem.script;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.custom.CustomFunction;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

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
