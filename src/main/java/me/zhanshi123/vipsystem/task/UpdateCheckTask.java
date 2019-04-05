package me.zhanshi123.vipsystem.task;

import com.google.gson.Gson;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.manager.MessageManager;
import me.zhanshi123.vipsystem.util.Update;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;

public class UpdateCheckTask extends BukkitRunnable {
    @Override
    public void run() {
        Update update = null;
        try {
            URL url = new URL("http://www.zhanshi123.me/update/index.php?name=VipSystem");
            InputStream in = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String json = br.readLine();
            update = new Gson().fromJson(json, Update.class);
        } catch (Exception e) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getConsoleSender().sendMessage(MessageFormat.format(MessageManager.getString("updateCheckFailure"), e.getMessage()));
                }
            }.runTask(Main.getInstance());
        }
        if (update == null) {
            return;
        }
        Main.getUpdateManager().setUpdate(update);
        Update finalUpdate = update;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (String.valueOf(finalUpdate.getVersion()).equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                    Bukkit.getConsoleSender().sendMessage(MessageManager.getString("latestVersion"));
                } else {
                    Bukkit.getConsoleSender().sendMessage(MessageFormat.format(MessageManager.getString("newUpdate"), finalUpdate.getVersion(), finalUpdate.getMessage()));
                }
            }
        }.runTask(Main.getInstance());
    }
}
