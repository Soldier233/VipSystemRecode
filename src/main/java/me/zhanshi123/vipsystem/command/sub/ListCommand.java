package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class ListCommand extends SubCommand implements PermissionCommand {
    public ListCommand() {
        super("list", MessageManager.getString("Command.list.usage"), MessageManager.getString("Command.list.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(MessageManager.getString("Command.list.list"));
        Set<VipData> dataSet = VipSystemAPI.getInstance().getVipManager().getVipDatum();
        dataSet.forEach(data -> sender.sendMessage("§7" + data.getPlayer() + "," + data.getVip() + "," + data.getExpireDate()));

        return true;
    }
}
