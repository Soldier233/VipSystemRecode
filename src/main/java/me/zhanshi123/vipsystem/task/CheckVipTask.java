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
        VipData vipData = Main.getCache().getVipData(name);
        if (vipData == null) {
            return;
        }
        if (vipData.getDuration() == -1) {
            return;
        }
        if (vipData.getTimeToExpire() >= 1000 * 60L) {
            return;
        }
        long temp = vipData.getTimeToExpire();
        if (temp < 0) {
            temp = 0;
        }
        new DelayedRemoveVipTask(player).runTaskLater(Main.getInstance(), temp / 1000 * 20);
    }
}
