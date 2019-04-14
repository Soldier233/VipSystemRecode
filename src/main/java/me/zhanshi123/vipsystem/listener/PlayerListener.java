package me.zhanshi123.vipsystem.listener;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.task.CheckVipTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new CheckVipTask(e.getPlayer()).runTask(Main.getInstance());
    }
}
