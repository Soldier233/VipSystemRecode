package me.zhanshi123.vipsystem.api.vip;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import org.bukkit.entity.Player;

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
        return System.currentTimeMillis() - (start + duration);
    }
}
