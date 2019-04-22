package me.zhanshi123.vipsystem.api.storage;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

public class VipStorageManager {
    public VipStorage getVipStorage(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return null;
    }
}
