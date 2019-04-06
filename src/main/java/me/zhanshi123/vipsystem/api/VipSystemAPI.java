package me.zhanshi123.vipsystem.api;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.vip.VipManager;
import org.bukkit.entity.Player;

public class VipSystemAPI {
    private static VipSystemAPI instance = new VipSystemAPI();
    private VipManager vipManager;

    public static VipSystemAPI getInstance() {
        return instance;
    }

    public VipManager getVipManager() {
        return vipManager;
    }

    public VipSystemAPI() {
        vipManager = new VipManager();
    }


    public String getPlayerName(Player player) {
        if (Main.getConfigManager().isUUID()) {
            return player.getUniqueId().toString();
        }
        return player.getName();
    }
}
