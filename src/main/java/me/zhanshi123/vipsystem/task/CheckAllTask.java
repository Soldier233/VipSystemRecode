package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckAllTask extends BukkitRunnable {


    @Override
    public void run() {
        VipSystemAPI.getInstance().getOnlinePlayers().stream()
                .forEach(player -> new CheckVipTask(player).runTask(Main.getInstance()));
        Main.getDataBase().getAllFunctions().forEach(
                storedFunction -> new CheckFunctionTask(storedFunction).runTask(Main.getInstance())
        );
    }
}
