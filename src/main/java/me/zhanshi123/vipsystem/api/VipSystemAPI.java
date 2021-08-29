package me.zhanshi123.vipsystem.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.storage.VipStorageManager;
import me.zhanshi123.vipsystem.api.vip.VipManager;
import me.zhanshi123.vipsystem.custom.CustomArg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VipSystemAPI {
    private static final VipSystemAPI instance = new VipSystemAPI();
    private final VipManager vipManager;
    private final VipStorageManager vipStorageManager;
    private final Gson gson = new Gson();

    public static VipSystemAPI getInstance() {
        return instance;
    }

    public VipManager getVipManager() {
        return vipManager;
    }

    public VipStorageManager getVipStorageManager() {
        return vipStorageManager;
    }

    private VipSystemAPI() {
        vipManager = new VipManager();
        vipStorageManager = new VipStorageManager();
    }


    public String getPlayerName(Player player) {
        if (Main.getConfigManager().isUUID()) {
            return player.getUniqueId().toString();
        }
        return player.getName();
    }

    private Pattern dMatchesPattern = Pattern.compile("[0-9]+d");
    private Pattern hMatchesPattern = Pattern.compile("[0-9]+h");
    private Pattern mMatchesPattern = Pattern.compile("[0-9]+m");
    private Pattern sMatchesPattern = Pattern.compile("[0-9]+s");

    public long getTimeMillis(String text) {
        if (text.equalsIgnoreCase("-1")) {
            return -1;
        }
        long d = 0L;
        long h = 0L;
        long m = 0L;
        long s = 0L;
        Matcher ma = dMatchesPattern.matcher(text);
        if (ma.find()) {
            d = getTime(ma.group());
        }
        ma = hMatchesPattern.matcher(text);
        if (ma.find()) {
            h = getTime(ma.group());
        }
        ma = mMatchesPattern.matcher(text);
        if (ma.find()) {
            m = getTime(ma.group());
        }
        ma = sMatchesPattern.matcher(text);
        if (ma.find()) {
            s = getTime(ma.group());
        }
        long time = d * 86400L;
        time += h * 3600L;
        time += m * 60L;
        time += s;
        return time * 1000;
    }

    private Pattern pattern = Pattern.compile("[0-9]+");

    private long getTime(String a) {
        Matcher m = pattern.matcher(a);
        if (m.find()) {
            return Long.parseLong(m.group());
        }
        try {
            return Integer.parseInt(a);
        } catch (Exception ignored) {
        }
        return 0;
    }

    public Collection<Player> getOnlinePlayers() {
        Collection<Player> players = null;
        try {
            Class<?> clazz = Class.forName("org.bukkit.Bukkit");
            Method method = clazz.getMethod("getOnlinePlayers");
            if (method.getReturnType().equals(Collection.class)) {
                players = (Collection<Player>) method.invoke(Bukkit.getServer());
            } else {
                players = Arrays.asList((Player[]) method.invoke(Bukkit.getServer()));
            }
            Main.getInstance().debug("online player count:" + players.size() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public String getJsonForCustomArgs(List<CustomArg> mustArgs, List<CustomArg> customArgs) {
        Map<String, String> tmp = new HashMap<>();
        mustArgs.forEach(customArg -> tmp.put(customArg.getName(), customArg.getValue()));
        JsonObject result = new JsonObject();
        result.add("must", gson.toJsonTree(tmp));
        tmp.clear();
        customArgs.forEach(customArg -> tmp.put(customArg.getName(), customArg.getValue()));
        result.add("custom", gson.toJsonTree(tmp));
        return result.toString();
    }

    public void runAsync(AsyncTask consumer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                consumer.run();
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

}
