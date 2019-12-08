package me.zhanshi123.vipsystem.custom;

import me.zhanshi123.vipsystem.Main;

import java.util.List;

public class StoredFunction extends CustomFunction {
    private int id;
    private long activate;
    private List<CustomArg> mustArgs;
    private List<CustomArg> customizableArgs;

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

    public void setMustArgs(List<CustomArg> mustArgs) {
        this.mustArgs = mustArgs;
    }

    public CustomFunction getCustomFunction() {
        return Main.getCustomManager().getCustomFunction(this.getName());
    }

    public long getTimeToExpire() {
        return (activate + this.getDuration()) - System.currentTimeMillis();
    }

    public void execute() {
        this.getOnStart().stream()
                .filter(string->string.startsWith("[Command]"));
    }
}

