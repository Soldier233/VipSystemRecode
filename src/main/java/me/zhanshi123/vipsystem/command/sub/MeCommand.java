package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class MeCommand extends SubCommand {

    public MeCommand() {
        super("me", MessageManager.getString("Command.me.usage"), MessageManager.getString("Command.me.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getString("consoleForbid"));
            return true;
        }
        Player player = (Player) sender;
        VipData vipData = Main.getCache().getVipData(VipSystemAPI.getInstance().getPlayerName(player));
        if (vipData == null) {
            player.sendMessage(MessageManager.getString("Command.me.noVip"));
            return true;
        }
        player.sendMessage(MessageFormat.format(MessageManager.getString("Command.me.result"), vipData.getVip(), vipData.getExpireDate(), vipData.getLeftDays()));
        return true;
    }
}
