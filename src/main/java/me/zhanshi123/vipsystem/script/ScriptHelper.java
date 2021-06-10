package me.zhanshi123.vipsystem.script;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScriptHelper {
    public void sendMessage(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }
}
