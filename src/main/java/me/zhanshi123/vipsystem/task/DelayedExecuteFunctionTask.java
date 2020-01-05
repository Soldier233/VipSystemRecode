package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
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
        storedFunction.executeEnd();
        Main.getDataBase().removeFunction(storedFunction.getId());
    }
}
