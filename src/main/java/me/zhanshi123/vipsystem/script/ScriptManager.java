package me.zhanshi123.vipsystem.script;

import me.zhanshi123.vipsystem.Main;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptManager {
    private ScriptEngine nashorn;

    public ScriptManager() {
        nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        if (nashorn == null) {
            Main.getInstance().getLogger().warning("Cannot load JavaScript engine, custom script disabled");
            return;
        }
        this.nashorn = nashorn;
    }

    public ScriptEngine getNashorn() {
        return nashorn;
    }
}
