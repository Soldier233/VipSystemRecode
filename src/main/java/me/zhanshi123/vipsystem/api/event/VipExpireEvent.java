package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipExpireEvent extends VipEvent {
    public VipExpireEvent(Player player, VipData vipData) {
        super(player, vipData);
    }

}
