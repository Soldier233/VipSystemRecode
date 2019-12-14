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
    private String description;
    private String[] args;
    private long duration;
    private List<String> onStart;
    private List<String> onEnd;
    private File script;
    private List<String> scripts = new ArrayList<>();
    private Map<String, String[]> functions = new HashMap<>();
    private ScriptEngine nashorn;

    public CustomFunction(String name) {
        this.name = name;
        CustomFunction customFunction = Main.getCustomManager().getCustomFunction(name);
        this.description = customFunction.getDescription();
        this.args = customFunction.getArgs();
        this.duration = customFunction.getDuration();
        this.onStart = customFunction.getOnStart();
        this.onEnd = customFunction.getOnEnd();
        this.script = customFunction.getScript();
        this.functions = customFunction.getFunctions();
        this.nashorn = customFunction.getNashorn();
    }

    public CustomFunction(String name, String description, String[] args, long duration, List<String> onStart, List<String> onEnd, File script) {
        this.name = name;
        this.description = description;
        this.args = args;
        this.duration = duration;
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

    public String getFormattedArgs() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            stringBuffer.append("[");
            stringBuffer.append(args[i]);
            stringBuffer.append("] ");
        }
        String tmp = stringBuffer.toString();
        tmp = tmp.substring(0, tmp.length() - 1);
        return tmp;
    }

    public long getDuration() {
        return duration;
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
        return Main.getScriptManager().invokeCustomFunction(nashorn, functionName, args);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String[]> getFunctions() {
        return functions;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
