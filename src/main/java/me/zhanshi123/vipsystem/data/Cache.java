package me.zhanshi123.vipsystem.data;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class Cache implements Listener {
    private Map<String, VipData> map = new HashMap<>();

    public Cache() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        cache(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        deCache(player);
    }

    public void cache(Player player) {
        VipData vipData = VipSystemAPI.getInstance().getVipManager().getVipData(player);
        if (vipData == null) {
            return;
        }
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        map.remove(name);
        map.put(name, vipData);
    }

    public VipData getVipData(String playerName) {
        return map.get(playerName);
    }

    public void deCache(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        VipData vipData = getVipData(name);
        if (vipData == null) {
            return;
        }
        map.remove(name);
        Main.getDataBase().updateVipData(vipData);
    }
}
