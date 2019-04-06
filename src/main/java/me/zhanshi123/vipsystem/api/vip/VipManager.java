package me.zhanshi123.vipsystem.api.vip;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

public class VipManager {
    /**
     * Get player's Vip Data
     * return null if not exists
     * @param player Player
     * @return vip data
     */
    public VipData getVipData(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getDataBase().getVipData(name);
    }


}
