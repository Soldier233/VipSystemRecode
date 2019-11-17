package me.zhanshi123.vipsystem.script;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.custom.CustomFunction;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptManager {
    public ScriptEngine getNashorn() {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        if (nashorn == null) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            return null;
        }
        return nashorn;
    }

    public Object invokeCustomFunction(CustomFunction customFunction, String function, String[] args) {
        try {
            ScriptEngine nashorn = customFunction.getNashorn();
            Invocable invocable = (Invocable) nashorn;
            return invocable.invokeFunction(function, args);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
