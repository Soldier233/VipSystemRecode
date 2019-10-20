package me.zhanshi123.vipsystem.custom;

import me.zhanshi123.vipsystem.Main;

import java.io.File;

public class CustomManager {
    private File file;

    public CustomManager() {
        file = new File(Main.getInstance().getDataFolder(), "custom");
        file.mkdirs();
        
    }
}
