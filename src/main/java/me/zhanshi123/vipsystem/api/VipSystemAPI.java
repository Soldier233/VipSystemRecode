package me.zhanshi123.vipsystem.api;

import me.zhanshi123.vipsystem.api.vip.VipManager;

public class VipSystemAPI {
    private static VipSystemAPI instance = new VipSystemAPI();
    private VipManager vipManager;

    public static VipSystemAPI getInstance() {
        return instance;
    }

    public VipManager getVipManager() {
        return vipManager;
    }

    public VipSystemAPI() {
        vipManager = new VipManager();
    }
}
