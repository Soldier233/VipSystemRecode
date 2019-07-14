package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipRenewEvent extends VipEvent{
    /**
     * 当玩家续费Vip时调用
     * @param player 玩家
     * @param vipData Vip数据
     */
    public VipRenewEvent(Player player, VipData vipData) {
        super(player, vipData);
    }
}
