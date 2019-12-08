package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.custom.StoredFunction;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedExecuteFunctionTask extends BukkitRunnable {

    private StoredFunction storedFunction;

    public DelayedExecuteFunctionTask(StoredFunction storedFunction) {
        this.storedFunction = storedFunction;
    }

    @Override
    public void run() {
        if (storedFunction == null) {
            return;
        }
        if (storedFunction.getTimeToExpire() > 0) {
            return;
        }
        //TODO 执行脚本
    }
}
