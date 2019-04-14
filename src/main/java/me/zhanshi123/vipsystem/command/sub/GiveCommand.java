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
        if (VipSystemAPI.getInstance().getVipManager().hasVip(player)) {
            sender.sendMessage(MessageManager.getString("Command.give.alreadyHaveVip"));
            return true;
        }
        VipData vipData = new VipData(player, args[2], VipSystemAPI.getInstance().getTimeMillis(args[3]));
        VipSystemAPI.getInstance().getVipManager().addVip(player, vipData);
        sender.sendMessage("Command.give.success");
        return true;
    }
}
