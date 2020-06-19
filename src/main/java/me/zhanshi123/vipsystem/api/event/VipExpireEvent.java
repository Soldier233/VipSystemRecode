package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipExpireEvent extends VipEvent {
    private boolean natural = false;

    /**
     * 当玩家Vip过期时被调用
     *
     * @param player  玩家
     * @param vipData Vip数据
     */
    public VipExpireEvent(Player player, VipData vipData) {
        super(player, vipData);
    }

    public VipExpireEvent(Player player, VipData vipData, boolean natural) {
        super(player, vipData);
        this.natural = natural;
    }

    public boolean isNatural() {
        return natural;
    }
}
