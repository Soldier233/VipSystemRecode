package me.zhanshi123.vipsystem.api.event;

import me.zhanshi123.vipsystem.api.vip.VipData;
import org.bukkit.entity.Player;

public class VipExpireEvent extends VipEvent {
    private boolean executeCommands = true;
    public VipExpireEvent(Player player, VipData vipData) {
        super(player, vipData);
    }

    public VipExpireEvent(Player player, VipData vipData, boolean executeCommands) {
        super(player, vipData);
        this.executeCommands = executeCommands;
    }

    public boolean isExecuteCommands() {
        return executeCommands;
    }

    public void setExecuteCommands(boolean executeCommands) {
        this.executeCommands = executeCommands;
    }
}
