package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckVipTask extends BukkitRunnable {
    private Player player;
    private String name;

    public CheckVipTask(Player player) {
        this.player = player;
        name = VipSystemAPI.getInstance().getPlayerName(player);
    }

    @Override
    public void run() {
        if (player == null) {
            return;
        }
        Main.getInstance().debug("Check " + player.getName() + " 's vipData");
        VipData vipData = Main.getCache().getVipData(name);
        if (vipData == null) {
            Main.getInstance().debug(player.getName() + " 's vipData not found, skipping");
            return;
        }
        if (vipData.getDuration() == -1) {
            Main.getInstance().debug(player.getName() + " is permanent vip, skipping");
            return;
        }
        long temp = vipData.getTimeToExpire();
        Main.getInstance().debug(player.getName() + " 's vip has " + temp + " milli seconds remaining.");
        if (temp >= 1000 * 60L) {
            return;
        }
        if (temp < 0) {
            temp = 0;
        }
        Main.getInstance().debug(player.getName() + " ready to run delayed remove task in " + (temp / 1000 * 20) + " ticks");
        new DelayedRemoveVipTask(player).runTaskLater(Main.getInstance(), temp / 1000 * 20);
    }
}
