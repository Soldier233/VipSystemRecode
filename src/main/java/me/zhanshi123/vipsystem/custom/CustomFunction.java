package me.zhanshi123.vipsystem.custom;

import me.zhanshi123.vipsystem.Main;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomFunction {
    private String name;
    private String[] args;
    private List<String> onStart;
    private List<String> onEnd;
    private File script;
    private List<String> scripts = new ArrayList<>();
    private Map<String, String[]> functions = new HashMap<>();
    private ScriptEngine nashorn;

    public CustomFunction(String name, String[] args, List<String> onStart, List<String> onEnd, File script) {
        this.name = name;
        this.args = args;
        this.onStart = onStart;
        this.onEnd = onEnd;
        this.script = script;
        scripts.addAll(onEnd);
        scripts.addAll(onStart);
        scripts = scripts.stream()
                .filter(str -> str.startsWith("[Script]"))
                .map(str -> str.replace("[Script]", "").trim())
                .collect(Collectors.toList());
        if (scripts.size() != 0) {
            try {
                nashorn = Main.getScriptManager().getNashorn();
                nashorn.eval(new FileReader(script));
                scripts.forEach(function -> {
                    if (function.contains("(") && function.contains(")")) {
                        String functionName = function.substring(0, function.indexOf("("));
                        String arguments = function.substring(function.indexOf("(")).replace("(", "").replace(")", "");
                        String[] argArray;
                        if (arguments.contains(",")) {
                            argArray = arguments.split(",");
                            for (int i = 0; i < argArray.length; i++) {
                                argArray[i] = argArray[i].trim();
                            }
                        } else {
                            argArray = new String[]{arguments};
                        }
                        functions.put(functionName, argArray);
                    }
                });
            } catch (Exception e) {
                Main.getInstance().getLogger().warning("Script resolve error! File path: " + script.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public List<String> getOnStart() {
        return onStart;
    }

    public void setOnStart(List<String> onStart) {
        this.onStart = onStart;
    }

    public List<String> getOnEnd() {
        return onEnd;
    }

    public void setOnEnd(List<String> onEnd) {
        this.onEnd = onEnd;
    }

    public File getScript() {
        return script;
    }

    public void setScript(File script) {
        this.script = script;
    }

    public ScriptEngine getNashorn() {
        return nashorn;
    }

    public String[] getSortedArguments(String functionName) {
        return functions.get(functionName);
    }

    public Object executeFunction(String functionName, String... args) {
        return Main.getScriptManager().invokeCustomFunction(this, functionName, args);
    }

}
