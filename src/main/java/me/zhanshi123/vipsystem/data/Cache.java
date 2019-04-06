package me.zhanshi123.vipsystem.data;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        VipData vipData = VipSystemAPI.getInstance().getVipManager().getVipData(player);
        if (vipData == null) {
            return;
        }
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        map.remove(name);
        map.put(name, vipData);
    }
}
