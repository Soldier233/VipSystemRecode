package me.zhanshi123.vipsystem.customcommand;

import java.util.HashMap;
import java.util.Map;

public class CustomCommandManager {
    private Map<String, CustomCommand> data = new HashMap<>();

    public void add(CustomCommand customCommand) {
        data.remove(customCommand.getVip());
        data.put(customCommand.getVip(), customCommand);
    }

    public CustomCommand get(String vip) {
        return data.get(vip);
    }
}
