package me.zhanshi123.vipsystem.api.storage;

public class VipStorage {
    private int id;
    private String player;
    private String vip;
    private String previous;
    private long activate;
    private long left;

    public VipStorage(int id, String player, String vip, String previous, long activate, long left) {
        this.id = id;
        this.player = player;
        this.vip = vip;
        this.previous = previous;
        this.activate = activate;
        this.left = left;
    }

    public VipStorage(String player, String vip, String previous, long activate, long left) {
        this.player = player;
        this.vip = vip;
        this.previous = previous;
        this.activate = activate;
        this.left = left;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getActivate() {
        return activate;
    }

    public void setActivate(long activate) {
        this.activate = activate;
    }

    public long getLeft() {
        return left;
    }

    public void setLeft(long left) {
        this.left = left;
    }
}
