package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VipEvent extends Event {
    private Player player;
    private VipData vipData;

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
}
