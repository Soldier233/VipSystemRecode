package me.zhanshi123.vipsystem.api.storage;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
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

    public boolean store(Player player) {
        VipData vipData = VipSystemAPI.getInstance().getVipManager().getVipData(player);
        if (vipData == null) {
            return false;
        }
        if (vipData.getDuration() == -1) {
            return false;
        }
        long now = System.currentTimeMillis();
        long time = vipData.getDuration() + vipData.getStart() - now;
        VipStorage vipStorage = new VipStorage(VipSystemAPI.getInstance().getPlayerName(player), vipData.getVip(), vipData.getPrevious(), now, time);
        addVipStorage(vipStorage);
        VipSystemAPI.getInstance().getVipManager().removeVipWithoutCommandsInternal(player,false);
        return true;
    }

    public void removeVipStorage(int id) {
        Main.getDataBase().removeVipStorage(id);
    }
}
