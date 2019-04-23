package me.zhanshi123.vipsystem.api.storage;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

import java.util.Set;

public class VipStorageManager {
    public Set<VipStorage> getVipStorage(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getDataBase().getVipStorage(name);
    }

    public VipStorage getVipStorage(int id) {
        return Main.getDataBase().getVipStorage(id);
    }

    public void addVipStorage(VipStorage vipStorage) {
        Main.getDataBase().addVipStorage(vipStorage);
    }

    public void removeVipStorage(int id) {
        Main.getDataBase().removeVipStorage(id);
    }
}
