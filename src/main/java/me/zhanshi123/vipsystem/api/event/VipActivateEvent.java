package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipActivateEvent extends VipEvent {
    /**
     * 当玩家开通Vip时被调用
     * @param player 开通Vip的玩家
     * @param vipData Vip数据
     */
    public VipActivateEvent(Player player, VipData vipData) {
        super(player, vipData);
    }
}
