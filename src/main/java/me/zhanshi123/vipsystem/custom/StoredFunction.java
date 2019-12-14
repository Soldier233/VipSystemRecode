package me.zhanshi123.vipsystem.custom;

import me.zhanshi123.vipsystem.Main;
import org.bukkit.Bukkit;

import java.util.List;

public class StoredFunction extends CustomFunction {
    private int id;
    private long activate;
    private List<CustomArg> mustArgs;
    private List<CustomArg> customizableArgs;

    public StoredFunction(String name, long activate, List<CustomArg> mustArgs, List<CustomArg> customizableArgs) {
        super(name);
        this.activate = activate;
        this.customizableArgs = customizableArgs;
        this.mustArgs = mustArgs;
    }

    public StoredFunction(String name, int id, long activate, List<CustomArg> mustArgs, List<CustomArg> customizableArgs) {
        super(name);
        this.id = id;
        this.activate = activate;
        this.customizableArgs = customizableArgs;
        this.mustArgs = mustArgs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getActivate() {
        return activate;
    }

    public void setActivate(long activate) {
        this.activate = activate;
    }

    public List<CustomArg> getCustomizableArgs() {
        return customizableArgs;
    }

    public void setCustomizableArgs(List<CustomArg> customizableArgs) {
        this.customizableArgs = customizableArgs;
    }

    public List<CustomArg> getMustArgs() {
        return mustArgs;
    }

    public CustomArg getMustArg(String name) {
        for (CustomArg mustArg : mustArgs) {
            if (mustArg.getName().equalsIgnoreCase(name)) {
                return mustArg;
            }
        }
        return null;
    }

    public void setMustArgs(List<CustomArg> mustArgs) {
        this.mustArgs = mustArgs;
    }

    public CustomFunction getCustomFunction() {
        return Main.getCustomManager().getCustomFunction(this.getName());
    }

    public long getTimeToExpire() {
        return (activate + this.getDuration()) - System.currentTimeMillis();
    }

    public void executeStart() {
        getOnStart().stream()
                .filter(string -> string.startsWith("[Console]"))
                .map(string -> string = string.replace("[Console]", "").trim())
                .forEach(cmd -> {
                    final String[] tmp = {cmd};
                    getMustArgs().forEach(customArg -> tmp[0] = tmp[0].replace("{" + customArg.getName() + "}", customArg.getValue()));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tmp[0]);
                });
        getOnStart().stream()
                .filter(string -> string.startsWith("[Script]"))
                .map(string -> string = string.replace("[Script]", "").trim())
                .map(string -> string = string.substring(0, string.lastIndexOf("(")))
                .forEach(script -> {
                    String[] array = getFunctions().get(script);
                    String[] arg = new String[array.length];
                    for (int i = 0; i < arg.length; i++) {
                        arg[i] = getMustArg(array[i].replace("{", "").replace("}", "")).getValue();
                    }
                    executeFunction(script, arg);
                });
    }
}

