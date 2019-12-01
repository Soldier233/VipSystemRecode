package me.zhanshi123.vipsystem.custom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StoredFunction extends CustomFunction {
    private int id;
    private List<CustomArg> customizableArgs;

    public StoredFunction(String name, String description, String[] args, long duration, List<String> onStart, List<String> onEnd, File script, int id, List<CustomArg> customizableArgs) {
        super(name, description, args, duration, onStart, onEnd, script);
        this.id = id;
        this.customizableArgs = customizableArgs;
    }

    public StoredFunction(String name, String description, String[] args, long duration, List<String> onStart, List<String> onEnd, File script, int id) {
        super(name, description, args, duration, onStart, onEnd, script);
        this.id = id;
        this.customizableArgs = new ArrayList<>();
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
}

