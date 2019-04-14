package me.zhanshi123.vipsystem.api.vip;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.task.CheckVipTask;
import org.bukkit.entity.Player;

public class VipManager {
    /**
     * Get player's VipData.
     * Return null if not exists.
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
        if (Main.getConfigManager().isGlobal()) {
            Main.getPermission().playerAddGroup(player, group);
        } else {
            Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerAddGroup(worldName, player, group));
        }

        new CheckVipTask(player).runTask(Main.getInstance());
    }

    public void removeVip(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        VipData vipData = getVipData(player);
        if (vipData == null) {
            return;
        }
        Main.getCache().removePlayer(name);
        Main.getDataBase().deletePlayer(name);
        if (Main.getConfigManager().isGlobal()) {
            Main.getPermission().playerRemoveGroup(player, vipData.getVip());
        } else {
            Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerRemoveGroup(worldName, player, vipData.getVip()));
        }
    }
}
