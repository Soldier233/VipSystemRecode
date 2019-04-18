package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipActivateEvent extends VipEvent {
    private boolean isNative = true;

    public VipActivateEvent(Player player, VipData vipData) {
        super(player, vipData);
    }

    /**
     * @param player
     * @param vipData
     * @param isNative if is called by plugin itself
     */
    public VipActivateEvent(Player player, VipData vipData, boolean isNative) {
        super(player, vipData);
        this.isNative = isNative;
    }

    public boolean isNative() {
        return isNative;
    }
}
