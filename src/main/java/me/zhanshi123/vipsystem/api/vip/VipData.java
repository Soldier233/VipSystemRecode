package me.zhanshi123.vipsystem.api.vip;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

import java.util.Date;

public class VipData {
    private String player;
    private String vip;
    private String previous;
    private long start;
    private long duration;

    public VipData(Player player, String vip, long duration) {
        this.player = VipSystemAPI.getInstance().getPlayerName(player);
        this.vip = vip;
        this.previous = Main.getPermission().getPrimaryGroup(player);
        this.start = System.currentTimeMillis();
        this.duration = duration;
    }

    public VipData(String player, String vip, String previous, long duration) {
        this.player = player;
        this.vip = vip;
        this.previous = previous;
        this.start = System.currentTimeMillis();
        this.duration = duration;
    }

    public VipData(String player, String vip, String previous, long start, long duration) {
        this.player = player;
        this.vip = vip;
        this.previous = previous;
        this.start = start;
        this.duration = duration;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTimeToExpire() {
        return (start + duration) - System.currentTimeMillis();
    }

    private Date date = new Date();

    public String getExpireDate() {
        long expire = start + duration;
        date.setTime(expire);
        return Main.getConfigManager().getDateFormat().format(date);
    }

    public float getLeftDays() {
        long expire = start + duration;
        long now = System.currentTimeMillis();
        long left = expire - now;
        float day = (float) left / Float.valueOf(3600 * 24 * 1000);
        day = Math.round(day * 100) / 100;
        return day;
    }

    @Override
    public String toString() {
        return "VipData{" +
                "player='" + player + '\'' +
                ", vip='" + vip + '\'' +
                ", previous='" + previous + '\'' +
                ", start=" + start +
                ", duration=" + duration +
                ", date=" + date +
                '}';
    }
}
