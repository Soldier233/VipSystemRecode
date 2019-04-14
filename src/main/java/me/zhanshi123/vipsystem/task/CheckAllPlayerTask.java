package me.zhanshi123.vipsystem.task;

import me.zhanshi123.vipsystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class CheckAllPlayerTask extends BukkitRunnable {

    private Collection<Player> getOnlinePlayers() {
        Collection<Player> players = null;
        try {
            Class<?> clazz = Class.forName("org.bukkit.Bukkit");
            Method method = clazz.getMethod("getOnlinePlayers");
            if (method.getReturnType().equals(Collection.class)) {
                players = (Collection<Player>) method.invoke(Bukkit.getServer());
            } else {
                players = Arrays.asList((Player[]) method.invoke(Bukkit.getServer()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public void run() {
        getOnlinePlayers().stream()
                .forEach(player -> new CheckVipTask(player).runTask(Main.getInstance()));
    }
}
