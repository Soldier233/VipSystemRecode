package me.zhanshi123.vipsystem.customcommand;

import java.util.List;

public class CustomCommand {
    private String vip;
    private List<String> activate;
    private List<String> expire;

    public CustomCommand(String vip, List<String> activate, List<String> expire) {
        this.vip = vip;
        this.activate = activate;
        this.expire = expire;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public List<String> getActivate() {
        return activate;
    }

    public void setActivate(List<String> activate) {
        this.activate = activate;
    }

    public List<String> getExpire() {
        return expire;
    }

    public void setExpire(List<String> expire) {
        this.expire = expire;
    }
}
