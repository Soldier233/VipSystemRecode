package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.custom.StoredFunction;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckFunctionTask extends BukkitRunnable {
    private StoredFunction function;

    public CheckFunctionTask(StoredFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        if (function == null) {
            return;
        }
        if (function.getTimeToExpire() >= 1000 * 60L) {
            return;
        }
        if (function.getAwaitingPlayer() != null) {
            if (Bukkit.getPlayer(function.getAwaitingPlayer()) == null) {
                return;
            }
        }
        long temp = function.getTimeToExpire();
        if (temp < 0) {
            temp = 0;
        }
        new DelayedExecuteFunctionTask(function).runTaskLater(Main.getInstance(), temp / 1000 * 20);
    }
}
