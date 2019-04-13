package me.zhanshi123.vipsystem.api.vip;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.task.CheckVipTask;
import org.bukkit.entity.Player;

public class VipManager {
    /**
     * Get player's VipData
     * return null if not exists
     *
     * @param player Player
     * @return vip data
     */
    public VipData getVipData(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getCache().getVipData(name);
    }

    public boolean hasVip(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getCache().getVipData(name) != null;
    }

    public void addVip(Player player, VipData vipData) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        Main.getCache().addVipData(name, vipData);
        String group = vipData.getVip();
        Main.getPermission().playerAddGroup(player, group);
        new CheckVipTask(player).runTask(Main.getInstance());
    }
}
