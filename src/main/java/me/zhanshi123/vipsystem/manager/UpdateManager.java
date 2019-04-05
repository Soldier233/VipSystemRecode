package me.zhanshi123.vipsystem.manager;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.task.UpdateCheckTask;
import me.zhanshi123.vipsystem.util.Update;

public class UpdateManager {
    private Update update;

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void checkUpdate() {
        new UpdateCheckTask().runTaskLaterAsynchronously(Main.getInstance(), 200L);
    }
}
