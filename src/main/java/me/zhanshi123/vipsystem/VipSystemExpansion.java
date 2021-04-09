package me.zhanshi123.vipsystem;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VipSystemExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "vipsystem";
    }

    @Override
    public String getAuthor() {
        return "Soldier";
    }

    @Override
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }

    @Override
    public String getRequiredPlugin() {
        return "VipSystem";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return null;
        }
        Player player = offlinePlayer.getPlayer();
        VipData vipData = VipSystemAPI.getInstance().getVipManager().getVipData(player);
        if (params.equalsIgnoreCase("vip")) {
            return vipData != null ? vipData.getVip() : "-";
        } else if (params.equalsIgnoreCase("previous")) {
            return vipData != null ? vipData.getPrevious() : "-";
        } else if (params.equalsIgnoreCase("expire")) {
            return vipData != null ? vipData.getDuration() != -1 ? vipData.getExpireDate() : "∞" : "-";
        } else if (params.equalsIgnoreCase("left")) {
            return vipData != null ? vipData.getDuration() != -1 ? String.valueOf(vipData.getLeftDays()) : "∞" : "-";
        }
        return null;
    }
}
