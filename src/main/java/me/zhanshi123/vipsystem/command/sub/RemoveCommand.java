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

public class RemoveCommand extends SubCommand implements PermissionCommand {
    public RemoveCommand() {
        super("remove", MessageManager.getString("Command.remove.usage"), MessageManager.getString("Command.remove.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if(player==null){
            sender.sendMessage(MessageManager.getString("playerNotFound"));
            return true;
        }
        VipData vipData = Main.getCache().getVipData(VipSystemAPI.getInstance().getPlayerName(player));
        if (vipData == null) {
            sender.sendMessage(MessageManager.getString("Command.remove.noVip"));
            return true;
        }
        VipSystemAPI.getInstance().getVipManager().removeVip(player);
        sender.sendMessage(MessageManager.getString("Command.remove.success"));
        return true;
    }
}
