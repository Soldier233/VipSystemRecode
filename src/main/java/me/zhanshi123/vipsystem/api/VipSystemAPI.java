package me.zhanshi123.vipsystem.api;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.vip.VipManager;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VipSystemAPI {
    private static VipSystemAPI instance = new VipSystemAPI();
    private VipManager vipManager;

    public static VipSystemAPI getInstance() {
        return instance;
    }

    public VipManager getVipManager() {
        return vipManager;
    }

    private VipSystemAPI() {
        vipManager = new VipManager();
    }


    public String getPlayerName(Player player) {
        if (Main.getConfigManager().isUUID()) {
            return player.getUniqueId().toString();
        }
        return player.getName();
    }

    private String dMatches = "[0-9]+d";
    private String hMatches = "[0-9]+h";
    private String mMatches = "[0-9]+m";
    private String sMatches = "[0-9]+s";

    public long getTimeMillis(String text) {
        long d = 0L;
        long h = 0L;
        long m = 0L;
        long s = 0L;
        Pattern p = Pattern.compile(dMatches);
        Matcher ma = p.matcher(text);
        if (ma.find()) {
            d = getTime(ma.group());
        }
        p = Pattern.compile(hMatches);
        ma = p.matcher(text);
        if (ma.find()) {
            h = getTime(ma.group());
        }
        p = Pattern.compile(mMatches);
        ma = p.matcher(text);
        if (ma.find()) {
            m = getTime(ma.group());
        }
        p = Pattern.compile(sMatches);
        ma = p.matcher(text);
        if (ma.find()) {
            s = getTime(ma.group());
        }
        long time = d * 86400L;
        time += h * 3600L;
        time += m * 60L;
        time += s * 1L;
        return time * 1000;
    }

    private long getTime(String a) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(a);
        if (m.find()) {
            return Long.parseLong(m.group());
        }
        return 0L;
    }
}
