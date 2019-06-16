package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand implements PermissionCommand {
    public GiveCommand() {
        super("give", MessageManager.getString("Command.give.usage"), MessageManager.getString("Command.give.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(MessageManager.getString("Command.look.notFound"));
            return true;
        }
        long temp = VipSystemAPI.getInstance().getTimeMillis(args[3]);
        if (temp == 0 || (temp >= 1 && temp < 60000)) {
            sender.sendMessage(MessageManager.getString("Command.give.invalidTime"));
            return true;
        }
        VipData vipData = VipSystemAPI.getInstance().getVipManager().getVipData(player);
        if (vipData != null) {
            if (!vipData.getVip().equalsIgnoreCase(args[2])) {
                if (vipData.getDuration() != -1) {
                    VipSystemAPI.getInstance().getVipStorageManager().store(player);
                    vipData = new VipData(player, args[2], temp);
                    VipSystemAPI.getInstance().getVipManager().addVip(player, vipData);
                    sender.sendMessage(MessageManager.getString("Command.give.success"));
                    return true;
                }
                sender.sendMessage(MessageManager.getString("Command.give.alreadyHaveVip"));
                return true;
            }
            VipSystemAPI.getInstance().getVipManager().renewVip(player, temp);
            sender.sendMessage(MessageManager.getString("Command.give.success"));
        } else {
            vipData = new VipData(player, args[2], temp);
            VipSystemAPI.getInstance().getVipManager().addVip(player, vipData);
            sender.sendMessage(MessageManager.getString("Command.give.success"));
        }

        return true;
    }
}
