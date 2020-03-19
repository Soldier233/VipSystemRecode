package me.zhanshi123.vipsystem.listener;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.task.CheckVipTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                new CheckVipTask(e.getPlayer()).runTask(Main.getInstance());
            }
        }.runTaskLater(Main.getInstance(), 40L);
    }
}
