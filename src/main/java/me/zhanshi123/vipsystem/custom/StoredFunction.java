package me.zhanshi123.vipsystem.custom;

import me.zhanshi123.vipsystem.Main;

import java.util.List;

public class StoredFunction extends CustomFunction {
    private int id;
    private List<CustomArg> mustArgs;
    private List<CustomArg> customizableArgs;

    public StoredFunction(String name, int id, List<CustomArg> mustArgs, List<CustomArg> customizableArgs) {
        super(name);
        this.id = id;
        this.customizableArgs = customizableArgs;
        this.mustArgs = mustArgs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

