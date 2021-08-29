package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedRemoveVipTask extends BukkitRunnable {
    private final Player player;
    private final String name;

    public DelayedRemoveVipTask(Player player) {
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
        long tmp = vipData.getTimeToExpire();
        Main.getInstance().debug(player.getName() + " 's vip is scheduled to remove, time to expire:" + tmp);
        if (tmp >= 1000) {
            return;
        }
        VipSystemAPI.getInstance().getVipManager().removeVip(player);
    }
}
