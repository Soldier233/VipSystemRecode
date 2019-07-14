package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class VipEvent extends Event {
    private Player player;
    private VipData vipData;

    /**
     * 所有Vip事件的抽象类，不会被调用
     * @param player 玩家
     * @param vipData Vip信息
     */
    public VipEvent(Player player, VipData vipData) {
        this.player = player;
        this.vipData = vipData;
    }

    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public VipData getVipData() {
        return vipData;
    }

    public void setVipData(VipData vipData) {
        this.vipData = vipData;
    }
}
