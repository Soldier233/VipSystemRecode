package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.storage.VipStorage;
import me.zhanshi123.vipsystem.api.vip.VipData;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand extends SubCommand {
    public ClaimCommand() {
        super("claim");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getString("consoleForbid"));
            return true;
        }
        if (args.length != 2) {
            return true;
        }
        Player player = (Player) sender;
        int id = Integer.parseInt(args[1]);
        VipStorage vipStorage = VipSystemAPI.getInstance().getVipStorageManager().getVipStorage(id);
        if (vipStorage == null) {
            return true;
        }
        if (!vipStorage.getPlayer().equalsIgnoreCase(VipSystemAPI.getInstance().getPlayerName(player))) {
            return true;
        }
        VipSystemAPI.getInstance().getVipStorageManager().store(player);
        VipSystemAPI.getInstance().getVipManager().addVipWithoutCommands(player, new VipData(VipSystemAPI.getInstance().getPlayerName(player), vipStorage.getVip(), vipStorage.getPrevious(), vipStorage.getLast()));
        VipSystemAPI.getInstance().getVipStorageManager().removeVipStorage(id);
        return true;
    }
}
