package me.zhanshi123.vipsystem.api.storage;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class VipStorageManager {
    public List<VipStorage> getVipStorage(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getDataBase().getVipStorage(name);
    }

    public VipStorage getVipStorage(int id) {
        return Main.getDataBase().getVipStorage(id);
    }

}
