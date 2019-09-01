package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class LookCommand extends SubCommand implements PermissionCommand {
    public LookCommand() {
        super("look", MessageManager.getString("Command.look.usage"), MessageManager.getString("Command.look.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = Bukkit.getPlayerExact(args[1]);
        if (player == null) {
            sender.sendMessage(MessageManager.getString("Command.look.notFound"));
            return true;
        }
        VipData vipData = Main.getCache().getVipData(VipSystemAPI.getInstance().getPlayerName(player));
        if (vipData == null) {
            sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.look.noVip"), player.getName()));
            return true;
        }
        if (vipData.getDuration() == -1) {
            sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.look.resultPermanent"), vipData.getVip(), args[1]));
        } else {
            sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.look.result"), vipData.getVip(), vipData.getExpireDate(), String.valueOf(vipData.getLeftDays()), args[1]));
        }
        return true;
    }
}
