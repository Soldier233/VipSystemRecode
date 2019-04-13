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
        if (vipData.getTimeToExpire() <= 1000 * 60l) {
            return;
        }
        new DelayedRemoveVipTask(player).runTaskLater(Main.getInstance(), vipData.getTimeToExpire() / 1000 * 20);
    }
}
